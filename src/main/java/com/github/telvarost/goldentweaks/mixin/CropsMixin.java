package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import com.github.telvarost.goldentweaks.ModHelper;
import net.minecraft.block.CropBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import org.checkerframework.common.aliasing.qual.Unique;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(CropBlock.class)
public class CropsMixin extends PlantBlock {
    public CropsMixin(int i, int j) {
        super(i, j);
    }

    @Unique
    private int usingGoldHoe = 0;

    @Override
    public void afterBreak(World arg, PlayerEntity player, int i, int j, int k, int l) {

        if (Config.config.enableGoldHoeFortune) {

            if (  (null != player)
                && (null != player.inventory)
                && (null != player.inventory.getSelectedItem())
                && (Item.GOLDEN_HOE.id == player.inventory.getSelectedItem().itemId)
            ) {
                usingGoldHoe += 2;

                /** - Gold hoe normally doesn't take damage, so we have to apply it manually */
                player.inventory.getSelectedItem().damage(1, player);
                if (player.inventory.getSelectedItem().count <= 0) {
                    player.clearStackInHand();
                }
            }
        }

        player.increaseStat(Stats.MINE_BLOCK[this.id], 1);
        this.dropStacks(arg, i, j, k, l);
    }

    @Inject(at = @At("HEAD"), method = "dropStacks", cancellable = true)
    private void goldenTweaks_beforeDestroyedByExplosion(World arg, int i, int j, int k, int l, float f, CallbackInfo ci) {
        if (  (Config.config.enableGoldHoeFortune)
           && (!arg.isRemote)
           && (0 < usingGoldHoe)
        ) {
            usingGoldHoe--;

            float var8 = 0.7F;
            float var9 = arg.random.nextFloat() * var8 + (1.0F - var8) * 0.5F;
            float var10 = arg.random.nextFloat() * var8 + (1.0F - var8) * 0.5F;
            float var11 = arg.random.nextFloat() * var8 + (1.0F - var8) * 0.5F;
            ItemEntity var12 = new ItemEntity(arg, (double)((float)i + var9), (double)((float)j + var10), (double)((float)k + var11), new ItemStack(Item.SEEDS));
            var12.pickupDelay = 10;
            arg.spawnEntity(var12);
        }
    }

    @Inject(at = @At("HEAD"), method = "getDroppedItemCount", cancellable = true)
    public void goldenTweaks_getDropCount(Random random, CallbackInfoReturnable<Integer> cir) {
        if (!Config.config.enableGoldHoeFortune) {
            return;
        }

        if (0 < usingGoldHoe) {
            usingGoldHoe--;
            cir.setReturnValue(2);
        }
    }
}
