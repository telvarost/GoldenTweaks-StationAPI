package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
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
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
public abstract class PlayerBaseMixin extends LivingEntity {
    public PlayerBaseMixin(World arg) {
        super(arg);
    }

    @Redirect(
            method = "damage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/Entity;I)Z"
            )
    )
    public boolean goldenTweaks_damage(LivingEntity living, Entity entity, int i) {
        if (living instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) living;

            if (Config.config.enableGoldHelmetBlastProtection) {
                if (entity instanceof CreeperEntity) {
                    if (  (3 < player.inventory.armor.length)
                       && (null != player.inventory.armor[3])
                       && (Item.GOLDEN_HELMET.id == player.inventory.armor[3].itemId)
                    ) {
                        i = (int) Math.round(i * 0.8);
                    }
                }
            }

            if (Config.config.enableGoldChestplateProjectileProtection) {
                if (entity instanceof SkeletonEntity) {
                    if (  (2 < player.inventory.armor.length)
                       && (null != player.inventory.armor[2])
                       && (Item.GOLDEN_CHESTPLATE.id == player.inventory.armor[2].itemId)
                    ) {
                        i = (int) Math.round(i * 0.8);
                    }
                }
            }

            if (Config.config.enableGoldLeggingsThorns) {
                if (  (entity instanceof MonsterEntity)
                   && !(entity instanceof CreeperEntity)
                   && !(entity instanceof SkeletonEntity)
                ) {
                    if (  (1 < player.inventory.armor.length)
                       && (null != player.inventory.armor[1])
                       && (Item.GOLDEN_LEGGINGS.id == player.inventory.armor[1].itemId)
                    ) {
                        entity.damage(player, 2);
                    }
                }
            }
        }

        return super.damage(entity, i);
    }

}
