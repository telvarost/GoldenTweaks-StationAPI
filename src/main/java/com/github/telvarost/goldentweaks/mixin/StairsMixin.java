package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.checkerframework.common.aliasing.qual.Unique;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(StairsBlock.class)
public class StairsMixin extends Block {
    public StairsMixin(int i, Block arg) {
        super(i, arg.textureId, arg.material);
    }

    @Unique
    private boolean brokenByGoldTool = false;

    @Override
    public void afterBreak(World arg, PlayerEntity player, int i, int j, int k, int l) {

        brokenByGoldTool = false;

        if (Config.config.enableGoldPickaxeSilkTouch) {

            if (  (this.id == Block.COBBLESTONE_STAIRS.id)
                && (null != player)
                && (null != player.inventory)
                && (null != player.inventory.getSelectedItem())
                && (Item.GOLDEN_PICKAXE.id == player.inventory.getSelectedItem().itemId)
            ) {
                brokenByGoldTool = true;
            }
        }

        if (Config.config.enableGoldAxeSilkTouch) {

            if (  (this.id == Block.WOODEN_STAIRS.id)
                && (null != player)
                && (null != player.inventory)
                && (null != player.inventory.getSelectedItem())
                && (Item.GOLDEN_AXE.id == player.inventory.getSelectedItem().itemId)
            ) {
                brokenByGoldTool = true;
            }
        }

        player.increaseStat(Stats.MINE_BLOCK[this.id], 1);
        this.dropStacks(arg, i, j, k, l);
    }

    @Inject(at = @At("HEAD"), method = "getDroppedItemId", cancellable = true)
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
            method = "dropStacks",
            at = @At("HEAD"),
            cancellable = true
    )
    private void annoyanceFix_beforeDestroyedByExplosion(World arg, int i, int j, int k, int l, float f, CallbackInfo ci) {
        if (  (Config.config.enableGoldPickaxeSilkTouch)
           || (Config.config.enableGoldAxeSilkTouch)
        ) {
            super.dropStacks(arg, i, j, k, l, f);
            ci.cancel();
        }
    }
}
