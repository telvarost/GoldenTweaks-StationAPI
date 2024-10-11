package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FoodItem.class)
public class FoodBaseMixin extends Item {
    public FoodBaseMixin(int i, int j, boolean bl) {
        super(i);
    }

    @Inject(at = @At("HEAD"), method = "use", cancellable = true)
    public void goldenTweaks_use(ItemStack arg, World arg2, PlayerEntity arg3, CallbackInfoReturnable<ItemStack> cir) {
        if (Config.config.enableGoldAppleCureFire) {
            if (arg.itemId == Item.GOLDEN_APPLE.id) {
                arg3.fireTicks = 0;
            }
        }
    }
}
