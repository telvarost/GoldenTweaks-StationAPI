package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import org.checkerframework.common.aliasing.qual.Unique;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(Living.class)
public class LivingMixin {

    @Unique
    private boolean killedByPlayer = false;

    @Inject(at = @At("HEAD"), method = "onKilledBy", cancellable = true)
    public void onKilledBy(EntityBase arg, CallbackInfo ci) {

        if (Config.config.enableGoldSwordLooting) {
            if (arg instanceof PlayerBase) {
                PlayerBase player = (PlayerBase) arg;
                killedByPlayer = true;

                if (  (null != player.inventory)
                   && (null != player.inventory.getHeldItem())
                   && (ItemBase.goldSword.id == player.inventory.getHeldItem().itemId)
                ) {
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
           && (true == killedByPlayer)
        ) {
            killedByPlayer = false;
            return instance.nextInt(dropCount) + 1;
        } else {
            return instance.nextInt(dropCount);
        }
    }
}
