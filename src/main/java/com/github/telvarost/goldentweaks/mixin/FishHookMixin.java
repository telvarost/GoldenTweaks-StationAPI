package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FishingBobberEntity.class)
public abstract class FishHookMixin extends Entity {
    @Shadow public PlayerEntity owner;

    public FishHookMixin(World arg) {
        super(arg);
    }

    @Inject(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;increaseStat(Lnet/minecraft/stat/Stat;I)V"
            )
    )
    public void method_956(CallbackInfoReturnable<Integer> cir) {
        if (Config.config.enableFishingRodLuckOfTheSea) {
            if (0 == random.nextInt(10)) {
                ItemEntity var13 = new ItemEntity(this.world, this.x, this.y, this.z, new ItemStack(Item.GOLD_INGOT));
                double var3 = this.owner.x - this.x;
                double var5 = this.owner.y - this.y;
                double var7 = this.owner.z - this.z;
                double var9 = (double) MathHelper.sqrt(var3 * var3 + var5 * var5 + var7 * var7);
                double var11 = 0.1;
                var13.velocityX = var3 * var11;
                var13.velocityY = var5 * var11 + (double) MathHelper.sqrt(var9) * 0.075;
                var13.velocityZ = var7 * var11;
                this.world.spawnEntity(var13);
            }
        }
    }
}
