package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MonsterEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public abstract class PlayerBaseMixin extends LivingEntity {
    public PlayerBaseMixin(World arg) {
        super(arg);
    }

    @WrapOperation(
            method = "damage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/Entity;I)Z"
            )
    )
    public boolean goldenTweaks_damage(PlayerEntity instance, Entity damageSource, int amount, Operation<Boolean> original) {
        if (instance instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) instance;

            if (Config.config.enableGoldHelmetBlastProtection) {
                if (damageSource instanceof CreeperEntity) {
                    if (  (3 < player.inventory.armor.length)
                       && (null != player.inventory.armor[3])
                       && (Item.GOLDEN_HELMET.id == player.inventory.armor[3].itemId)
                    ) {
                        amount = (int) Math.round(amount * 0.8);
                    }
                }
            }

            if (Config.config.enableGoldChestplateProjectileProtection) {
                if (damageSource instanceof SkeletonEntity) {
                    if (  (2 < player.inventory.armor.length)
                       && (null != player.inventory.armor[2])
                       && (Item.GOLDEN_CHESTPLATE.id == player.inventory.armor[2].itemId)
                    ) {
                        amount = (int) Math.round(amount * 0.8);
                    }
                }
            }

            if (Config.config.enableGoldLeggingsThorns) {
                if (  (damageSource instanceof MonsterEntity)
                   && !(damageSource instanceof CreeperEntity)
                   && !(damageSource instanceof SkeletonEntity)
                ) {
                    if (  (1 < player.inventory.armor.length)
                       && (null != player.inventory.armor[1])
                       && (Item.GOLDEN_LEGGINGS.id == player.inventory.armor[1].itemId)
                    ) {
                        damageSource.damage(player, 2);
                    }
                }
            }
        }

        return original.call(instance, damageSource, amount);
    }

}
