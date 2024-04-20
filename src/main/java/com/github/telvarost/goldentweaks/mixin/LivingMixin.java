package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import com.github.telvarost.goldentweaks.ModHelper;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.entity.monster.Skeleton;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(Living.class)
public abstract class LivingMixin extends EntityBase {
    public LivingMixin(Level arg) {
        super(arg);
    }

    @Inject(at = @At("HEAD"), method = "onKilledBy", cancellable = true)
    public void onKilledBy(EntityBase arg, CallbackInfo ci) {

        if (Config.config.enableGoldSwordLooting) {
            if (arg instanceof PlayerBase) {
                PlayerBase player = (PlayerBase) arg;

                if (  (null != player.inventory)
                   && (null != player.inventory.getHeldItem())
                   && (ItemBase.goldSword.id == player.inventory.getHeldItem().itemId)
                ) {
                    ModHelper.ModHelperFields.UsingGoldSword++;

                    /** - The skeleton drops two items so needs two increments of using gold sword */
                    if ((Object)this instanceof Skeleton) {
                        ModHelper.ModHelperFields.UsingGoldSword++;
                    }

                    /** - Applying an extra damage point because of the bow glitch
                     *    When killing with an arrow it is possible to get looting effect by swapping to gold sword
                     *    I'd rather see the gold sword take extra damage than see this bug abused
                     */
                    player.inventory.getHeldItem().applyDamage(1, player);
                    if (player.inventory.getHeldItem().count <= 0) {
                        player.breakHeldItem();
                    }
                }
            }
        }
    }

    @Redirect(
            method = "getDrops",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Random;nextInt(I)I"
            )
    )
    protected int getDrops(Random instance, int dropCount) {
        if (  (Config.config.enableGoldSwordLooting)
           && (0 < ModHelper.ModHelperFields.UsingGoldSword)
        ) {
            ModHelper.ModHelperFields.UsingGoldSword--;
            return instance.nextInt(dropCount) + 1;
        } else {
            return instance.nextInt(dropCount);
        }
    }
}
