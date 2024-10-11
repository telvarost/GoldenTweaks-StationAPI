package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import net.minecraft.block.Block;
import net.minecraft.block.SpongeBlock;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SpongeBlock.class)
public class SpongeMixin extends Block {
    public SpongeMixin(int i) {
        super(i, Material.SPONGE);
    }

    @Redirect(
            method = "onPlaced",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getMaterial(III)Lnet/minecraft/block/material/Material;"
            )
    )
    public Material goldenTweaks_onBlockPlaced(World instance, int i, int j, int k) {
        if (Config.config.enableSpongeSoaksUpWater) {
            if (instance.getMaterial(i, j, k) == Material.WATER) {
                instance.setBlock(i, j, k, 0);
            }
        }

        return instance.getMaterial(i, j, k);
    }
}
