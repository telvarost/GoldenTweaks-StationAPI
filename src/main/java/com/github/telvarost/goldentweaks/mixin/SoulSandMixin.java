package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import net.minecraft.block.BlockBase;
import net.minecraft.block.SoulSand;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoulSand.class)
public class SoulSandMixin extends BlockBase {
    public SoulSandMixin(int i, int j) {
        super(i, j, Material.SAND);
    }

    @Inject(at = @At("HEAD"), method = "onEntityCollision", cancellable = true)
    public void onEntityCollision(Level arg, int i, int j, int k, EntityBase arg2, CallbackInfo ci) {
        if (!Config.config.enableGoldBootsSoulSpeed) {
            return;
        }

        if (arg2 instanceof PlayerBase) {
            PlayerBase player = (PlayerBase) arg2;
            if (  (0 < player.inventory.armour.length)
               && (null != player.inventory.armour[0])
               && (ItemBase.goldBoots.id == player.inventory.armour[0].itemId)
            ) {
                arg2.velocityX *= 2.5;
                arg2.velocityZ *= 2.5;
            }
        }
    }
}
