package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.Block;
import net.minecraft.block.SpongeBlock;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SpongeBlock.class)
public class SpongeMixin extends Block {
    public SpongeMixin(int i) {
        super(i, Material.SPONGE);
    }

    @WrapOperation(
            method = "onPlaced",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getMaterial(III)Lnet/minecraft/block/material/Material;"
            )
    )
    public Material goldenTweaks_onBlockPlaced(World instance, int x, int y, int z, Operation<Material> original) {
        if (Config.config.enableSpongeSoaksUpWater) {
            if (instance.getMaterial(x, y, z) == Material.WATER) {
                instance.setBlock(x, y, z, 0);
            }
        }

        return original.call(instance, x, y, z);
    }
}
