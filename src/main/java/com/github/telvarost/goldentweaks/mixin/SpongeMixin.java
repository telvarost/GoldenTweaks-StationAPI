package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import net.minecraft.block.BlockBase;
import net.minecraft.block.Sponge;
import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Sponge.class)
public class SpongeMixin extends BlockBase {
    public SpongeMixin(int i) {
        super(i, Material.SPONGE);
    }

    @Redirect(
            method = "onBlockPlaced",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/level/Level;getMaterial(III)Lnet/minecraft/block/material/Material;"
            )
    )
    public Material goldenTweaks_onBlockPlaced(Level instance, int i, int j, int k) {
        if (Config.config.enableSpongeSoaksUpWater) {
            if (instance.getMaterial(i, j, k) == Material.WATER) {
                instance.setTile(i, j, k, 0);
            }
        }

        return instance.getMaterial(i, j, k);
    }
}
