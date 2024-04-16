package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import net.minecraft.block.Ice;
import net.minecraft.block.TranslucentBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.level.Level;
import net.minecraft.stat.Stats;
import org.checkerframework.common.aliasing.qual.Unique;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(Ice.class)
public class IceMixin extends TranslucentBlock {
    public IceMixin(int i, int j) {
        super(i, j, Material.ICE, false);
    }

    @Unique
    private boolean brokenByGoldTool = false;

    @Inject(at = @At("HEAD"), method = "afterBreak", cancellable = true)
    public void afterBreak(Level arg, PlayerBase player, int i, int j, int k, int l, CallbackInfo ci) {
        if (Config.config.enableGoldPickaxeSilkTouch) {
            brokenByGoldTool = false;

            if (  (null != player)
               && (null != player.inventory)
               && (null != player.inventory.getHeldItem())
               && (ItemBase.goldPickaxe.id == player.inventory.getHeldItem().itemId)
            ) {
                brokenByGoldTool = true;
                player.increaseStat(Stats.mineBlock[this.id], 1);
                this.drop(arg, i, j, k, l);
                ci.cancel();
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "getDropCount", cancellable = true)
    public void getDropCount(Random random, CallbackInfoReturnable<Integer> cir) {
        if (!Config.config.enableGoldAxeSilkTouch) {
            return;
        }

        if (brokenByGoldTool) {
            cir.setReturnValue(1);
            brokenByGoldTool = false;
        }
    }
}
