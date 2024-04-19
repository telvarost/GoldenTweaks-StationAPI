package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.entity.monster.Creeper;
import net.minecraft.entity.monster.Skeleton;
import net.minecraft.entity.monster.Spider;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerBase.class)
public abstract class PlayerBaseMixin extends Living {
    public PlayerBaseMixin(Level arg) {
        super(arg);
    }

    @Redirect(
            method = "damage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/Living;damage(Lnet/minecraft/entity/EntityBase;I)Z"
            )
    )
    public boolean goldenTweaks_damage(Living living, EntityBase entity, int i) {
        if (living instanceof PlayerBase) {
            PlayerBase player = (PlayerBase) living;

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
        }

        return super.damage(entity, i);
    }

}
