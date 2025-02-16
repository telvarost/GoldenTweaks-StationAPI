package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StairsBlock.class)
public class StairsBlockMixin extends Block {

    public StairsBlockMixin(int id, Material material) {
        super(id, material);
    }

    @WrapOperation(
            method = "dropStacks",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/Block;dropStacks(Lnet/minecraft/world/World;IIIIF)V"
            )
    )
    public void dropStacks(Block instance, World world, int x, int y, int z, int meta, float luck, Operation<Void> original) {
        boolean brokenByGoldTool = false;
        PlayerEntity player = world.getClosestPlayer((double)x,(double)y,(double)z, -1.0);

        if (Config.config.enableGoldPickaxeSilkTouch) {
            if (  (this.id == Block.COBBLESTONE_STAIRS.id)
               && (null != player)
               && (null != player.inventory)
               && (null != player.inventory.getSelectedItem())
               && (Item.GOLDEN_PICKAXE.id == player.inventory.getSelectedItem().itemId)
            ) {
                brokenByGoldTool = true;
            }
        }

        if (Config.config.enableGoldAxeSilkTouch) {
            if (  (this.id == Block.WOODEN_STAIRS.id)
               && (null != player)
               && (null != player.inventory)
               && (null != player.inventory.getSelectedItem())
               && (Item.GOLDEN_AXE.id == player.inventory.getSelectedItem().itemId)
            ) {
                brokenByGoldTool = true;
            }
        }

        if (  (brokenByGoldTool)
           && (  (Config.config.enableGoldPickaxeSilkTouch)
              || (Config.config.enableGoldAxeSilkTouch)
              )
        ) {
            if (!world.isRemote) {
                int droppedItemCount = this.getDroppedItemCount(world.random);

                for (int i = 0; i < droppedItemCount; ++i) {
                    if (!(world.random.nextFloat() > luck)) {
                        this.dropStack(world, x, y, z, new ItemStack(id, 1, this.getDroppedItemMeta(meta)));
                    }
                }
            }
        } else {
            original.call(instance, world, x, y, z, meta, luck);
        }
    }
}
