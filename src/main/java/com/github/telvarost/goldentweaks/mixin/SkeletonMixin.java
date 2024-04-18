package com.github.telvarost.goldentweaks.mixin;

import com.github.telvarost.goldentweaks.Config;
import com.github.telvarost.goldentweaks.ModHelper;
import net.minecraft.entity.monster.MonsterBase;
import net.minecraft.entity.monster.Skeleton;
import net.minecraft.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

@Mixin(Skeleton.class)
public class SkeletonMixin extends MonsterBase {
    public SkeletonMixin(Level arg) {
        super(arg);
    }

    @Redirect(
            method = "getDrops",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Random;nextInt(I)I"
            )
    )
    protected int getDrops(Random instance, int dropCount) {
        if (  (Config.config.enableGoldSwordLooting)
           && (0 < ModHelper.ModHelperFields.UsingGoldSword)
        ) {
            ModHelper.ModHelperFields.UsingGoldSword--;
            return instance.nextInt(dropCount) + 1;
        } else {
            return instance.nextInt(dropCount);
        }
    }
}
