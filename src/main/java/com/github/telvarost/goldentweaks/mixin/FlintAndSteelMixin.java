package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import net.minecraft.block.BlockBase;
import net.minecraft.entity.Lightning;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.FlintAndSteel;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlintAndSteel.class)
public class FlintAndSteelMixin extends ItemBase {
    public FlintAndSteelMixin(int i) {
        super(i);
    }

    @Inject(at = @At("HEAD"), method = "useOnTile", cancellable = true)
    public void useOnTile(ItemInstance item, PlayerBase player, Level level, int i, int j, int k, int meta, CallbackInfoReturnable<Boolean> cir) {
        if (Config.config.enableGoldBlockHerobrineSummoner) {
            int blockId = level.getTileId(i, j, k);
            if (blockId == BlockBase.NETHERRACK.id) {
                int blockBelowId = level.getTileId(i, j - 1, k);

                /** Ensure summoner is lit from the top of the netherrack block with a gold block below */
                if (  (meta == 1)
                   && (blockBelowId == BlockBase.GOLD_BLOCK.id)
                ) {
                    int blockId_N = level.getTileId(i, j, k + 1);
                    int blockId_E = level.getTileId(i + 1, j, k);
                    int blockId_S = level.getTileId(i, j, k - 1);
                    int blockId_W = level.getTileId(i - 1, j, k);

                    if (   (blockId_N == BlockBase.REDSTONE_TORCH_LIT.id)
                        && (blockId_E == BlockBase.REDSTONE_TORCH_LIT.id)
                        && (blockId_S == BlockBase.REDSTONE_TORCH_LIT.id)
                        && (blockId_W == BlockBase.REDSTONE_TORCH_LIT.id)
                    ) {
                        int blockIdBelow_0_0 = level.getTileId(i - 1, j - 1, k - 1);
                        int blockIdBelow_0_1 = level.getTileId(i - 1, j - 1, k);
                        int blockIdBelow_0_2 = level.getTileId(i - 1, j - 1, k + 1);
                        int blockIdBelow_1_0 = level.getTileId(i, j - 1, k - 1);
                        int blockIdBelow_1_1 = level.getTileId(i, j - 1, k);
                        int blockIdBelow_1_2 = level.getTileId(i, j - 1, k + 1);
                        int blockIdBelow_2_0 = level.getTileId(i + 1, j - 1, k - 1);
                        int blockIdBelow_2_1 = level.getTileId(i + 1, j - 1, k);
                        int blockIdBelow_2_2 = level.getTileId(i + 1, j - 1, k + 1);

                        if (   (blockIdBelow_0_0 == BlockBase.GOLD_BLOCK.id)
                            && (blockIdBelow_0_1 == BlockBase.GOLD_BLOCK.id)
                            && (blockIdBelow_0_2 == BlockBase.GOLD_BLOCK.id)
                            && (blockIdBelow_1_0 == BlockBase.GOLD_BLOCK.id)
                            && (blockIdBelow_1_1 == BlockBase.GOLD_BLOCK.id)
                            && (blockIdBelow_1_2 == BlockBase.GOLD_BLOCK.id)
                            && (blockIdBelow_2_0 == BlockBase.GOLD_BLOCK.id)
                            && (blockIdBelow_2_1 == BlockBase.GOLD_BLOCK.id)
                            && (blockIdBelow_2_2 == BlockBase.GOLD_BLOCK.id)
                        ) {
                            Lightning lightning = new Lightning(level, i, j, k);
                            level.spawnEntity(lightning);
                            level.setTile(i, j - 1, k, BlockBase.SPONGE.id);
                        }
                    }
                }
            }
        }
    }
}
