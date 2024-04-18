package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import net.minecraft.block.Gravel;
import net.minecraft.block.Sand;
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

@Mixin(Gravel.class)
public class GravelMixin extends Sand {
    public GravelMixin(int i, int j) {
        super(i, j);
    }

    @Unique
    private boolean brokenByGoldTool = false;

    @Unique
    private Level level = null;

    @Unique
    private int level_x = 0;

    @Unique
    private int level_y = 0;

    @Unique
    private int level_z = 0;

    @Override
    public void afterBreak(Level arg, PlayerBase player, int i, int j, int k, int l) {

        if (Config.config.enableGoldHoeFortune) {
            brokenByGoldTool = false;

            if (  (null != player)
                && (null != player.inventory)
                && (null != player.inventory.getHeldItem())
                && (ItemBase.goldHoe.id == player.inventory.getHeldItem().itemId)
            ) {
                level = arg;
                level_x = i;
                level_y = j;
                level_z = k;
                brokenByGoldTool = true;
            }
        }

        player.increaseStat(Stats.mineBlock[this.id], 1);
        this.drop(arg, i, j, k, l);
    }

    @Inject(at = @At("HEAD"), method = "getDropId", cancellable = true)
    public void getDropId(int i, Random random, CallbackInfoReturnable<Integer> cir) {
        if (  (Config.config.enableGoldHoeFortune)
           && (brokenByGoldTool)
        ) {
            brokenByGoldTool = false;

            int itemId = random.nextInt(10) == 0 ? ItemBase.flint.id : this.id;
            if (ItemBase.flint.id == itemId) {
                this.drop(level, level_x, level_y, level_z, new ItemInstance(itemId, 1, 0));
            }
            cir.setReturnValue(itemId);
        }
    }
}
