package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import net.minecraft.block.BlockBase;
import net.minecraft.block.Stairs;
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

@Mixin(Stairs.class)
public class StairsMixin extends BlockBase {
    public StairsMixin(int i, BlockBase arg) {
        super(i, arg.texture, arg.material);
    }

    @Unique
    private boolean brokenByGoldTool = false;

    @Override
    public void afterBreak(Level arg, PlayerBase player, int i, int j, int k, int l) {

        brokenByGoldTool = false;

        if (Config.config.enableGoldPickaxeSilkTouch) {

            if (  (this.id == BlockBase.COBBLESTONE_STAIRS.id)
                && (null != player)
                && (null != player.inventory)
                && (null != player.inventory.getHeldItem())
                && (ItemBase.goldPickaxe.id == player.inventory.getHeldItem().itemId)
            ) {
                brokenByGoldTool = true;
            }
        }

        if (Config.config.enableGoldAxeSilkTouch) {

            if (  (this.id == BlockBase.WOOD_STAIRS.id)
                && (null != player)
                && (null != player.inventory)
                && (null != player.inventory.getHeldItem())
                && (ItemBase.goldAxe.id == player.inventory.getHeldItem().itemId)
            ) {
                brokenByGoldTool = true;
            }
        }

        player.increaseStat(Stats.mineBlock[this.id], 1);
        this.drop(arg, i, j, k, l);
    }

    @Inject(at = @At("HEAD"), method = "getDropId", cancellable = true)
    public void goldenTweaks_getDropId(int i, Random random, CallbackInfoReturnable<Integer> cir) {
        if (  (Config.config.enableGoldPickaxeSilkTouch)
           || (Config.config.enableGoldAxeSilkTouch)
        ) {

            if (brokenByGoldTool) {
                cir.setReturnValue(id);
                brokenByGoldTool = false;
            }
        }
    }

    @Inject(
            method = "beforeDestroyedByExplosion",
            at = @At("HEAD"),
            cancellable = true
    )
    private void annoyanceFix_beforeDestroyedByExplosion(Level arg, int i, int j, int k, int l, float f, CallbackInfo ci) {
        if (  (Config.config.enableGoldPickaxeSilkTouch)
           || (Config.config.enableGoldAxeSilkTouch)
        ) {
            super.beforeDestroyedByExplosion(arg, i, j, k, l, f);
            ci.cancel();
        }
    }
}
