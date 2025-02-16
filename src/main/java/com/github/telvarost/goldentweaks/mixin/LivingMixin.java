package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import com.github.telvarost.goldentweaks.ModHelper;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class LivingMixin extends Entity {
    public LivingMixin(World arg) {
        super(arg);
    }

    @Inject(at = @At("HEAD"), method = "onKilledBy", cancellable = true)
    public void onKilledBy(Entity arg, CallbackInfo ci) {

        if (Config.config.enableGoldSwordLooting) {
            if (arg instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) arg;

                if (  (null != player.inventory)
                   && (null != player.inventory.getSelectedItem())
                   && (Item.GOLDEN_SWORD.id == player.inventory.getSelectedItem().itemId)
                ) {
                    ModHelper.ModHelperFields.UsingGoldSword++;

                    /** - The skeleton drops two items so needs two increments of using gold sword */
                    if ((Object)this instanceof SkeletonEntity) {
                        ModHelper.ModHelperFields.UsingGoldSword++;
                    }

                    /** - Applying an extra damage point because of the bow glitch
                     *    When killing with an arrow it is possible to get looting effect by swapping to gold sword
                     *    I'd rather see the gold sword take extra damage than see this bug abused
                     */
                    player.inventory.getSelectedItem().damage(1, player);
                    if (player.inventory.getSelectedItem().count <= 0) {
                        player.clearStackInHand();
                    }
                }
            }
        }
    }

    @WrapOperation(
            method = "dropItems",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Random;nextInt(I)I"
            )
    )
    protected int getDrops(Random instance, int dropCount, Operation<Integer> original) {
        if (  (Config.config.enableGoldSwordLooting)
           && (0 < ModHelper.ModHelperFields.UsingGoldSword)
        ) {
            ModHelper.ModHelperFields.UsingGoldSword--;
            return original.call(instance, dropCount) + 1;
        } else {
            return original.call(instance, dropCount);
        }
    }
}
