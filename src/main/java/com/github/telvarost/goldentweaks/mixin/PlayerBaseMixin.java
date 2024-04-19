package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.monster.Creeper;
import net.minecraft.entity.monster.Skeleton;
import net.minecraft.entity.monster.Spider;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerBase.class)
public class PlayerBaseMixin {

    @WrapOperation(
            method = "damage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Living;damage(Lnet/minecraft/entity/EntityBase;I)Z"
            )
    )
    public boolean damage(PlayerBase player, EntityBase entity, int i, Operation<Boolean> operation) {

        if (Config.config.enableGoldHelmetBlastProtection) {
            if (entity instanceof Creeper) {
                if (  (3 < player.inventory.armour.length)
                   && (null != player.inventory.armour[3])
                   && (ItemBase.goldHelmet.id == player.inventory.armour[3].itemId)
                ) {
                    i = (int) Math.round(i * 0.8);
                }
            }
        }

        if (Config.config.enableGoldChestplateProjectileProtection) {
            if (entity instanceof Skeleton) {
                if (  (2 < player.inventory.armour.length)
                   && (null != player.inventory.armour[2])
                   && (ItemBase.goldChestplate.id == player.inventory.armour[2].itemId)
                ) {
                    i = (int) Math.round(i * 0.8);
                }
            }
        }

        if (Config.config.enableGoldLeggingsThorns) {
            if (entity instanceof Spider) {
                if (  (1 < player.inventory.armour.length)
                   && (null != player.inventory.armour[1])
                   && (ItemBase.goldLeggings.id == player.inventory.armour[1].itemId)
                ) {
                    entity.damage(player, 2);
                }
            }
        }

        return operation.call(player, entity, i);
    }

}
