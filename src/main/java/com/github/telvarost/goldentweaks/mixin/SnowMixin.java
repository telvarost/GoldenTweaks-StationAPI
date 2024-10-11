package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import net.minecraft.block.Block;
import net.minecraft.block.SnowyBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnowyBlock.class)
public class SnowMixin extends Block {
    public SnowMixin(int i, int j) {
        super(i, j, Material.SNOW_LAYER);
    }

    @Inject(at = @At("HEAD"), method = "afterBreak", cancellable = true)
    public void afterBreak(World arg, PlayerEntity player, int i, int j, int k, int l, CallbackInfo ci) {

        if (Config.config.enableGoldShovelSilkTouch) {

            if (  (null != player)
               && (null != player.inventory)
               && (null != player.inventory.getSelectedItem())
               && (Item.GOLDEN_SHOVEL.id == player.inventory.getSelectedItem().itemId)
            ) {
                int var7 = Block.SNOW.id;
                float var8 = 0.7F;
                double var9 = (double)(arg.random.nextFloat() * var8) + (double)(1.0F - var8) * 0.5;
                double var11 = (double)(arg.random.nextFloat() * var8) + (double)(1.0F - var8) * 0.5;
                double var13 = (double)(arg.random.nextFloat() * var8) + (double)(1.0F - var8) * 0.5;
                ItemEntity var15 = new ItemEntity(arg, (double)i + var9, (double)j + var11, (double)k + var13, new ItemStack(var7, 1, 0));
                var15.pickupDelay = 10;
                arg.spawnEntity(var15);
                arg.setBlock(i, j, k, 0);
                player.increaseStat(Stats.MINE_BLOCK[this.id], 1);
                ci.cancel();
            }
        }
    }
}
