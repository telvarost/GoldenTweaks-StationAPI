package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import net.minecraft.block.Glass;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(Glass.class)
public class GlassMixin extends TranslucentBlock {
    public GlassMixin(int i, int j, Material arg, boolean bl) {
        super(i, j, arg, bl);
    }

    @Unique
    private boolean brokenByGoldTool = false;

    @Override
    public void afterBreak(Level arg, PlayerBase player, int i, int j, int k, int l) {

        if (Config.config.enableGoldPickaxeSilkTouch) {
            brokenByGoldTool = false;

            if (  (null != player)
                    && (null != player.inventory)
                    && (null != player.inventory.getHeldItem())
                    && (ItemBase.goldPickaxe.id == player.inventory.getHeldItem().itemId)
            ) {
                brokenByGoldTool = true;
            }

            player.increaseStat(Stats.mineBlock[this.id], 1);
            this.drop(arg, i, j, k, l);
        }
    }

    @Inject(at = @At("HEAD"), method = "getDropCount", cancellable = true)
    public void getDropCount(Random random, CallbackInfoReturnable<Integer> cir) {
        if (!Config.config.enableGoldPickaxeSilkTouch) {
            return;
        }

        if (brokenByGoldTool) {
            cir.setReturnValue(1);
            brokenByGoldTool = false;
        }
    }
}
