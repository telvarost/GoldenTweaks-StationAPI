package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import net.minecraft.block.Block;
import net.minecraft.block.SoulSandBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoulSandBlock.class)
public class SoulSandMixin extends Block {
    public SoulSandMixin(int i, int j) {
        super(i, j, Material.SAND);
    }

    @Inject(at = @At("HEAD"), method = "onEntityCollision", cancellable = true)
    public void onEntityCollision(World arg, int i, int j, int k, Entity arg2, CallbackInfo ci) {
        if (!Config.config.enableGoldBootsSoulSpeed) {
            return;
        }

        if (arg2 instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) arg2;
            if (  (0 < player.inventory.armor.length)
               && (null != player.inventory.armor[0])
               && (Item.GOLDEN_BOOTS.id == player.inventory.armor[0].itemId)
            ) {
                arg2.velocityX *= 2.5;
                arg2.velocityZ *= 2.5;
            }
        }
    }
}
