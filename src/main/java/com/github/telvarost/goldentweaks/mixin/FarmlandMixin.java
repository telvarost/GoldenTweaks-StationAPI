package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import net.minecraft.block.BlockBase;
import net.minecraft.block.Farmland;
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

@Mixin(Farmland.class)
public class FarmlandMixin extends BlockBase {
    public FarmlandMixin(int i) {
        super(i, Material.DIRT);
    }

    @Unique
    private boolean brokenByGoldTool = false;

    @Override
    public void afterBreak(Level arg, PlayerBase player, int i, int j, int k, int l) {

        if (Config.config.enableGoldShovelSilkTouch) {
            brokenByGoldTool = false;

            if (  (null != player)
                    && (null != player.inventory)
                    && (null != player.inventory.getHeldItem())
                    && (ItemBase.goldShovel.id == player.inventory.getHeldItem().itemId)
            )
            {
                brokenByGoldTool = true;
            }
        }

        player.increaseStat(Stats.mineBlock[this.id], 1);
        this.drop(arg, i, j, k, l);
    }

    @Inject(at = @At("HEAD"), method = "getDropId", cancellable = true)
    public void goldenTweaks_getDropId(int i, Random random, CallbackInfoReturnable<Integer> cir) {
        if (!Config.config.enableGoldShovelSilkTouch) {
            return;
        }

        if (brokenByGoldTool) {
            cir.setReturnValue(id);
            brokenByGoldTool = false;
        }
    }
}
