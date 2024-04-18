package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import com.github.telvarost.goldentweaks.ModHelper;
import net.minecraft.block.BlockBase;
import net.minecraft.entity.animal.AnimalBase;
import net.minecraft.entity.animal.Sheep;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Sheep.class)
public abstract class SheepMixin extends AnimalBase {
    public SheepMixin(Level arg) {
        super(arg);
    }

    @Shadow public abstract boolean isSheared();

    @Shadow public abstract int getColour();

    @Inject(at = @At("HEAD"), method = "getDrops", cancellable = true)
    protected void getDrops(CallbackInfo ci) {
        if (  (Config.config.enableGoldSwordLooting)
           && (!this.isSheared())
           && (0 < ModHelper.ModHelperFields.UsingGoldSword)
        ) {
            ModHelper.ModHelperFields.UsingGoldSword--;
            this.dropItem(new ItemInstance(BlockBase.WOOL.id, 1, this.getColour()), 0.0F);
            this.dropItem(new ItemInstance(BlockBase.WOOL.id, 1, this.getColour()), 0.0F);
            ci.cancel();
        }

    }
}
