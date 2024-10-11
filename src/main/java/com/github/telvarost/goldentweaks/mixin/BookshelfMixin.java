package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BookshelfBlock;
import net.minecraft.block.material.Material;
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

@Mixin(BookshelfBlock.class)
public class BookshelfMixin extends Block {
    public BookshelfMixin(int i, int j) {
        super(i, j, Material.WOOD);
    }

    @Unique
    private boolean brokenByGoldToolId = false;

    @Unique
    private boolean brokenByGoldToolCount = false;

    @Override
    public void afterBreak(World arg, PlayerEntity player, int i, int j, int k, int l) {

        if (Config.config.enableGoldAxeSilkTouch) {
            brokenByGoldToolId = false;
            brokenByGoldToolCount = false;

            if (  (null != player)
                && (null != player.inventory)
                && (null != player.inventory.getSelectedItem())
                && (Item.GOLDEN_AXE.id == player.inventory.getSelectedItem().itemId)
            ) {
                brokenByGoldToolId = true;
                brokenByGoldToolCount = true;
            }

            player.increaseStat(Stats.MINE_BLOCK[this.id], 1);
            this.dropStacks(arg, i, j, k, l);
        }
    }

    @Override
    public void dropStacks(World arg, int i, int j, int k, int l, float f) {
        if (!arg.isRemote) {
            int var7 = this.getDroppedItemCount(arg.random);

            for(int var8 = 0; var8 < var7; ++var8) {
                if (!(arg.random.nextFloat() > f)) {
                    int var9 = this.getDroppedItemId(l, arg.random);

                    if (brokenByGoldToolId) {
                        brokenByGoldToolId = false;
                        var9 = Block.BOOKSHELF.id;
                    }

                    if (var9 > 0) {
                        this.dropStack(arg, i, j, k, new ItemStack(var9, 1, this.getDroppedItemMeta(l)));
                    }
                }
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "getDroppedItemCount", cancellable = true)
    public void getDropCount(Random random, CallbackInfoReturnable<Integer> cir) {
        if (!Config.config.enableGoldAxeSilkTouch) {
            return;
        }

        if (brokenByGoldToolCount) {
            cir.setReturnValue(1);
            brokenByGoldToolCount = false;
        }
    }
}
