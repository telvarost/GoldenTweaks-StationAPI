package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import net.minecraft.block.BlockBase;
import net.minecraft.block.Snow;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Item;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Snow.class)
public class SnowMixin extends BlockBase {
    public SnowMixin(int i, int j) {
        super(i, j, Material.SNOW);
    }

    @Inject(at = @At("HEAD"), method = "afterBreak", cancellable = true)
    public void afterBreak(Level arg, PlayerBase player, int i, int j, int k, int l, CallbackInfo ci) {

        if (Config.config.enableGoldShovelSilkTouch) {

            if (  (null != player)
               && (null != player.inventory)
               && (null != player.inventory.getHeldItem())
               && (ItemBase.goldShovel.id == player.inventory.getHeldItem().itemId)
            ) {
                int var7 = BlockBase.SNOW.id;
                float var8 = 0.7F;
                double var9 = (double)(arg.rand.nextFloat() * var8) + (double)(1.0F - var8) * 0.5;
                double var11 = (double)(arg.rand.nextFloat() * var8) + (double)(1.0F - var8) * 0.5;
                double var13 = (double)(arg.rand.nextFloat() * var8) + (double)(1.0F - var8) * 0.5;
                Item var15 = new Item(arg, (double)i + var9, (double)j + var11, (double)k + var13, new ItemInstance(var7, 1, 0));
                var15.pickupDelay = 10;
                arg.spawnEntity(var15);
                arg.setTile(i, j, k, 0);
                player.increaseStat(Stats.mineBlock[this.id], 1);
                ci.cancel();
            }
        }
    }
}
