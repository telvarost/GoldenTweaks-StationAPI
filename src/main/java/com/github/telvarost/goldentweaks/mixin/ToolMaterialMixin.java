package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import net.minecraft.item.tool.ToolMaterial;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ToolMaterial.class)
public class ToolMaterialMixin {

    @Shadow @Final private float miningSpeed;

    @Shadow @Final private int durability;

    @Inject(at = @At("HEAD"), method = "getMiningLevel", cancellable = true)
    public void goldenTweaks_getMiningLevel(CallbackInfoReturnable<Integer> cir) {
        if (  (Config.config.enableGoldPickaxeSilkTouch)
           && (12.0F == this.miningSpeed)
           && (32 == this.durability)
        ) {
            cir.setReturnValue(2);
        }
    }
}
