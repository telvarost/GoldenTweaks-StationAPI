package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.food.FoodBase;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FoodBase.class)
public class FoodBaseMixin extends ItemBase {
    public FoodBaseMixin(int i, int j, boolean bl) {
        super(i);
    }

    @Inject(at = @At("HEAD"), method = "use", cancellable = true)
    public void goldenTweaks_use(ItemInstance arg, Level arg2, PlayerBase arg3, CallbackInfoReturnable<ItemInstance> cir) {
        if (Config.config.enableGoldAppleCureFire) {
            if (arg.itemId == ItemBase.goldenApple.id) {
                arg3.fire = 0;
            }
        }
    }
}
