package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import net.minecraft.block.BlockBase;
import net.minecraft.block.Bookshelf;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.stat.Stats;
import org.checkerframework.common.aliasing.qual.Unique;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(Bookshelf.class)
public class BookshelfMixin extends BlockBase {
    public BookshelfMixin(int i, int j) {
        super(i, j, Material.WOOD);
    }

    @Unique
    private boolean brokenByGoldToolId = false;

    @Unique
    private boolean brokenByGoldToolCount = false;

    @Override
    public void afterBreak(Level arg, PlayerBase player, int i, int j, int k, int l) {

        if (Config.config.enableGoldAxeSilkTouch) {
            brokenByGoldToolId = false;
            brokenByGoldToolCount = false;

            if (  (null != player)
                && (null != player.inventory)
                && (null != player.inventory.getHeldItem())
                && (ItemBase.goldAxe.id == player.inventory.getHeldItem().itemId)
            ) {
                brokenByGoldToolId = true;
                brokenByGoldToolCount = true;
            }

            player.increaseStat(Stats.mineBlock[this.id], 1);
            this.drop(arg, i, j, k, l);
        }
    }

    @Override
    public void beforeDestroyedByExplosion(Level arg, int i, int j, int k, int l, float f) {
        if (!arg.isServerSide) {
            int var7 = this.getDropCount(arg.rand);

            for(int var8 = 0; var8 < var7; ++var8) {
                if (!(arg.rand.nextFloat() > f)) {
                    int var9 = this.getDropId(l, arg.rand);

                    if (brokenByGoldToolId) {
                        brokenByGoldToolId = false;
                        var9 = BlockBase.BOOKSHELF.id;
                    }

                    if (var9 > 0) {
                        this.drop(arg, i, j, k, new ItemInstance(var9, 1, this.droppedMeta(l)));
                    }
                }
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "getDropCount", cancellable = true)
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
