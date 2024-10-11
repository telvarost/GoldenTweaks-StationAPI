package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import net.minecraft.block.Block;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FlintAndSteel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlintAndSteel.class)
public class FlintAndSteelMixin extends Item {
    public FlintAndSteelMixin(int i) {
        super(i);
    }

    @Inject(at = @At("HEAD"), method = "useOnBlock", cancellable = true)
    public void useOnTile(ItemStack item, PlayerEntity player, World level, int i, int j, int k, int meta, CallbackInfoReturnable<Boolean> cir) {
        if (Config.config.enableGoldBlockHerobrineSummoner) {
            int blockId = level.getBlockId(i, j, k);
            if (blockId == Block.NETHERRACK.id) {
                int blockBelowId = level.getBlockId(i, j - 1, k);

                /** Ensure summoner is lit from the top of the netherrack block with a gold block below */
                if (  (meta == 1)
                   && (blockBelowId == Block.GOLD_BLOCK.id)
                ) {
                    int blockId_N = level.getBlockId(i, j, k + 1);
                    int blockId_E = level.getBlockId(i + 1, j, k);
                    int blockId_S = level.getBlockId(i, j, k - 1);
                    int blockId_W = level.getBlockId(i - 1, j, k);

                    if (   (blockId_N == Block.LIT_REDSTONE_TORCH.id)
                        && (blockId_E == Block.LIT_REDSTONE_TORCH.id)
                        && (blockId_S == Block.LIT_REDSTONE_TORCH.id)
                        && (blockId_W == Block.LIT_REDSTONE_TORCH.id)
                    ) {
                        int blockIdBelow_0_0 = level.getBlockId(i - 1, j - 1, k - 1);
                        int blockIdBelow_0_1 = level.getBlockId(i - 1, j - 1, k);
                        int blockIdBelow_0_2 = level.getBlockId(i - 1, j - 1, k + 1);
                        int blockIdBelow_1_0 = level.getBlockId(i, j - 1, k - 1);
                        int blockIdBelow_1_1 = level.getBlockId(i, j - 1, k);
                        int blockIdBelow_1_2 = level.getBlockId(i, j - 1, k + 1);
                        int blockIdBelow_2_0 = level.getBlockId(i + 1, j - 1, k - 1);
                        int blockIdBelow_2_1 = level.getBlockId(i + 1, j - 1, k);
                        int blockIdBelow_2_2 = level.getBlockId(i + 1, j - 1, k + 1);

                        if (   (blockIdBelow_0_0 == Block.GOLD_BLOCK.id)
                            && (blockIdBelow_0_1 == Block.GOLD_BLOCK.id)
                            && (blockIdBelow_0_2 == Block.GOLD_BLOCK.id)
                            && (blockIdBelow_1_0 == Block.GOLD_BLOCK.id)
                            && (blockIdBelow_1_1 == Block.GOLD_BLOCK.id)
                            && (blockIdBelow_1_2 == Block.GOLD_BLOCK.id)
                            && (blockIdBelow_2_0 == Block.GOLD_BLOCK.id)
                            && (blockIdBelow_2_1 == Block.GOLD_BLOCK.id)
                            && (blockIdBelow_2_2 == Block.GOLD_BLOCK.id)
                        ) {
                            LightningEntity lightning = new LightningEntity(level, i, j, k);
                            level.spawnEntity(lightning);
                            level.setBlock(i, j - 1, k, Block.SPONGE.id);
                        }
                    }
                }
            }
        }
    }
}
