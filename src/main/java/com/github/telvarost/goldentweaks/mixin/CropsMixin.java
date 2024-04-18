package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import com.github.telvarost.goldentweaks.ModHelper;
import net.minecraft.block.Crops;
import net.minecraft.block.Plant;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Item;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.stat.Stats;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import org.checkerframework.common.aliasing.qual.Unique;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(Crops.class)
public class CropsMixin extends Plant {
    public CropsMixin(int i, int j) {
        super(i, j);
    }

    @Unique
    private int usingGoldHoe = 0;

    @Override
    public void afterBreak(Level arg, PlayerBase player, int i, int j, int k, int l) {

        if (Config.config.enableGoldHoeFortune) {

            if (  (null != player)
                && (null != player.inventory)
                && (null != player.inventory.getHeldItem())
                && (ItemBase.goldHoe.id == player.inventory.getHeldItem().itemId)
            ) {
                usingGoldHoe += 2;

                /** - Gold hoe normally doesn't take damage, so we have to apply it manually */
                player.inventory.getHeldItem().applyDamage(1, player);
                if (player.inventory.getHeldItem().count <= 0) {
                    player.breakHeldItem();
                }
            }
        }

        player.increaseStat(Stats.mineBlock[this.id], 1);
        this.drop(arg, i, j, k, l);
    }

    @Inject(at = @At("HEAD"), method = "beforeDestroyedByExplosion", cancellable = true)
    private void goldenTweaks_beforeDestroyedByExplosion(Level arg, int i, int j, int k, int l, float f, CallbackInfo ci) {
        if (  (Config.config.enableGoldHoeFortune)
           && (!arg.isServerSide)
           && (0 < usingGoldHoe)
        ) {
            usingGoldHoe--;

            float var8 = 0.7F;
            float var9 = arg.rand.nextFloat() * var8 + (1.0F - var8) * 0.5F;
            float var10 = arg.rand.nextFloat() * var8 + (1.0F - var8) * 0.5F;
            float var11 = arg.rand.nextFloat() * var8 + (1.0F - var8) * 0.5F;
            Item var12 = new Item(arg, (double)((float)i + var9), (double)((float)j + var10), (double)((float)k + var11), new ItemInstance(ItemBase.seeds));
            var12.pickupDelay = 10;
            arg.spawnEntity(var12);
        }
    }

    @Inject(at = @At("HEAD"), method = "getDropCount", cancellable = true)
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
