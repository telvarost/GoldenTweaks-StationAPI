package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import net.minecraft.block.Block;
import net.minecraft.block.StoneBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.checkerframework.common.aliasing.qual.Unique;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(StoneBlock.class)
public class StoneMixin extends Block {
    public StoneMixin(int i, int j) {
        super(i, j, Material.STONE);
    }

    @Unique
    private boolean brokenByGoldTool = false;

    @Override
    public void afterBreak(World arg, PlayerEntity player, int i, int j, int k, int l) {

        if (Config.config.enableGoldPickaxeSilkTouch) {
            brokenByGoldTool = false;

            if (  (null != player)
                    && (null != player.inventory)
                    && (null != player.inventory.getSelectedItem())
                    && (Item.GOLDEN_PICKAXE.id == player.inventory.getSelectedItem().itemId)
            ) {
                brokenByGoldTool = true;
            }
        }

        player.increaseStat(Stats.MINE_BLOCK[this.id], 1);
        this.dropStacks(arg, i, j, k, l);
    }

    @Inject(at = @At("HEAD"), method = "getDroppedItemId", cancellable = true)
    public void goldenTweaks_getDropId(int i, Random random, CallbackInfoReturnable<Integer> cir) {
        if (!Config.config.enableGoldPickaxeSilkTouch) {
            return;
        }

        if (brokenByGoldTool) {
            cir.setReturnValue(id);
            brokenByGoldTool = false;
        }
    }
}
