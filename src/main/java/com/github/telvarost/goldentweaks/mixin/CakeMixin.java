package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import net.minecraft.block.BlockBase;
import net.minecraft.block.Cake;
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

@Mixin(Cake.class)
public class CakeMixin extends BlockBase {
    public CakeMixin(int i, int j) {
        super(i, j, Material.CAKE);
    }

    @Unique
    private boolean brokenByGoldToolId = false;

    @Unique
    private boolean brokenByGoldToolCount = false;

    @Override
    public void afterBreak(Level arg, PlayerBase player, int i, int j, int k, int l) {

        if (Config.config.enableGoldSwordLooting) {
            brokenByGoldToolId = false;
            brokenByGoldToolCount = false;

            if (  (null != player)
                && (null != player.inventory)
                && (null != player.inventory.getHeldItem())
                && (ItemBase.goldSword.id == player.inventory.getHeldItem().itemId)
                && (0 == l)
            ) {
                brokenByGoldToolId = true;
                brokenByGoldToolCount = true;
            }
        }

        player.increaseStat(Stats.mineBlock[this.id], 1);
        this.drop(arg, i, j, k, l);
    }

    @Inject(at = @At("HEAD"), method = "getDropId", cancellable = true)
    public void goldenTweaks_getDropId(int i, Random random, CallbackInfoReturnable<Integer> cir) {
        if (!Config.config.enableGoldSwordLooting) {
            return;
        }

        if (brokenByGoldToolId) {
            cir.setReturnValue(ItemBase.cake.id);
            brokenByGoldToolId = false;
        }
    }

    @Inject(at = @At("HEAD"), method = "getDropCount", cancellable = true)
    public void getDropCount(Random random, CallbackInfoReturnable<Integer> cir) {
        if (!Config.config.enableGoldSwordLooting) {
            return;
        }

        if (brokenByGoldToolCount) {
            cir.setReturnValue(1);
            brokenByGoldToolCount = false;
        }
    }
}
