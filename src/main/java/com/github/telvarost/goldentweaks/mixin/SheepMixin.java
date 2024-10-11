package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import com.github.telvarost.goldentweaks.ModHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SheepEntity.class)
public abstract class SheepMixin extends AnimalEntity {
    public SheepMixin(World arg) {
        super(arg);
    }

    @Shadow public abstract boolean isSheared();

    @Shadow public abstract int getColor();

    @Inject(at = @At("HEAD"), method = "dropItems", cancellable = true)
    protected void getDrops(CallbackInfo ci) {
        if (  (Config.config.enableGoldSwordLooting)
           && (!this.isSheared())
           && (0 < ModHelper.ModHelperFields.UsingGoldSword)
        ) {
            ModHelper.ModHelperFields.UsingGoldSword--;
            this.dropItem(new ItemStack(Block.WOOL.id, 1, this.getColor()), 0.0F);
            this.dropItem(new ItemStack(Block.WOOL.id, 1, this.getColor()), 0.0F);
            ci.cancel();
        }

    }
}
