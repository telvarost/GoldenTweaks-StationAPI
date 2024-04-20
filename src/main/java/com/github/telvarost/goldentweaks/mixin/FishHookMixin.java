package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.FishHook;
import net.minecraft.entity.Item;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.util.maths.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishHook.class)
public abstract class FishHookMixin extends EntityBase {
    @Shadow public PlayerBase field_1067;

    public FishHookMixin(Level arg) {
        super(arg);
    }

    @Inject(
            method = "method_956",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerBase;increaseStat(Lnet/minecraft/stat/Stat;I)V"
            )
    )
    public void method_956(CallbackInfoReturnable<Integer> cir) {
        if (Config.config.enableFishingRodLuckOfTheSea) {
            if (0 == rand.nextInt(10)) {
                Item var13 = new Item(this.level, this.x, this.y, this.z, new ItemInstance(ItemBase.goldIngot));
                double var3 = this.field_1067.x - this.x;
                double var5 = this.field_1067.y - this.y;
                double var7 = this.field_1067.z - this.z;
                double var9 = (double) MathHelper.sqrt(var3 * var3 + var5 * var5 + var7 * var7);
                double var11 = 0.1;
                var13.velocityX = var3 * var11;
                var13.velocityY = var5 * var11 + (double) MathHelper.sqrt(var9) * 0.075;
                var13.velocityZ = var7 * var11;
                this.level.spawnEntity(var13);
            }
        }
    }
}
