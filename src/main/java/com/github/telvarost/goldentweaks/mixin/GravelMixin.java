package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import net.minecraft.block.GravelBlock;
import net.minecraft.block.SandBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.checkerframework.common.aliasing.qual.Unique;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(GravelBlock.class)
public class GravelMixin extends SandBlock {
    public GravelMixin(int i, int j) {
        super(i, j);
    }

    @Unique
    private boolean brokenByGoldTool = false;

    @Unique
    private World level = null;

    @Unique
    private int level_x = 0;

    @Unique
    private int level_y = 0;

    @Unique
    private int level_z = 0;

    @Override
    public void afterBreak(World arg, PlayerEntity player, int i, int j, int k, int l) {

        if (Config.config.enableGoldHoeFortune) {
            brokenByGoldTool = false;

            if (  (null != player)
                && (null != player.inventory)
                && (null != player.inventory.getSelectedItem())
                && (Item.GOLDEN_HOE.id == player.inventory.getSelectedItem().itemId)
            ) {
                level = arg;
                level_x = i;
                level_y = j;
                level_z = k;
                brokenByGoldTool = true;
            }
        }

        player.increaseStat(Stats.MINE_BLOCK[this.id], 1);
        this.dropStacks(arg, i, j, k, l);
    }

    @Inject(at = @At("HEAD"), method = "getDroppedItemId", cancellable = true)
    public void getDropId(int i, Random random, CallbackInfoReturnable<Integer> cir) {
        if (  (Config.config.enableGoldHoeFortune)
           && (brokenByGoldTool)
        ) {
            brokenByGoldTool = false;

            int itemId = random.nextInt(10) == 0 ? Item.FLINT.id : this.id;
            if (Item.FLINT.id == itemId) {
                this.dropStack(level, level_x, level_y, level_z, new ItemStack(itemId, 1, 0));
            }
            cir.setReturnValue(itemId);
        }
    }
}
