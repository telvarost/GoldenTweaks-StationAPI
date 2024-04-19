package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import com.github.telvarost.goldentweaks.ModHelper;
import net.minecraft.block.BlockBase;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Item;
import net.minecraft.entity.PrimedTnt;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.tool.Shears;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.client.item.StationRendererItem;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import net.modificationstation.stationapi.api.item.StationFlatteningItem;
import net.modificationstation.stationapi.api.item.StationItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemBase.class)
public class ItemBaseMixin implements StationFlatteningItem, StationItem, StationRendererItem {

    @Shadow public static ItemBase feather;
    @Shadow public static Shears shears;

    @Shadow public static ItemBase goldIngot;

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void goldenTweaks_use(ItemInstance item, Level level, PlayerBase player, CallbackInfoReturnable<ItemInstance> cir) {
        if (!Config.config.enableGoldSwordLooting) {
            return;
        }

        System.out.println("Num kills: " + ModHelper.ModHelperFields.NumberOfKills);
        if (  (null != item)
           && (null != player)
           && (item.itemId == goldIngot.id)
        ) {
            if (10 <= ModHelper.ModHelperFields.NumberOfKills) {
                ModHelper.ModHelperFields.NumberOfKills -= 10;

                if (!level.isServerSide) {
                    Item var24 = new Item(level, player.x, player.y, player.z, new ItemInstance(ItemBase.goldIngot));
                    var24.velocityY = 0.20000000298023224;
                    level.spawnEntity(var24);
                }
            }
        }

        if (  (null != item)
           && (null != player)
           && (item.itemId == BlockBase.GOLD_BLOCK.id)
        ) {
            if (100 <= ModHelper.ModHelperFields.NumberOfKills) {
                ModHelper.ModHelperFields.NumberOfKills -= 100;

                if (!level.isServerSide) {
                    Item var24 = new Item(level, player.x, player.y, player.z, new ItemInstance(BlockBase.GOLD_BLOCK));
                    var24.velocityY = 0.20000000298023224;
                    level.spawnEntity(var24);
                }
            }
        }
    }
}
