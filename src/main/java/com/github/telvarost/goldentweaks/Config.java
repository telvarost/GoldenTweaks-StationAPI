package com.github.telvarost.goldentweaks;

import blue.endless.jankson.Comment;
import net.glasslauncher.mods.api.gcapi.api.*;

public class Config {

    @GConfig(value = "config", visibleName = "GoldenTweaks")
    public static ConfigFields config = new ConfigFields();

    public static class ConfigFields {

        @ConfigName("Enable Fishing Rod Luck Of The Sea")
        @Comment("10% chance to catch a gold ingot with a fish")
        @MultiplayerSynced
        @ValueOnVanillaServer(booleanValue = TriBoolean.FALSE)
        public Boolean enableFishingRodLuckOfTheSea = true;

        @ConfigName("Enable Gold Apple Cure Fire")
        @Comment("Cancels fire effect one time when eaten")
        @MultiplayerSynced
        @ValueOnVanillaServer(booleanValue = TriBoolean.FALSE)
        public Boolean enableGoldAppleCureFire = true;

        @ConfigName("Enable Gold Axe Silk Touch")
        @Comment("Tool must be effective on block for effect")
        @MultiplayerSynced
        @ValueOnVanillaServer(booleanValue = TriBoolean.FALSE)
        public Boolean enableGoldAxeSilkTouch = true;

        @ConfigName("Enable Gold Block Herobrine Summoner")
        @Comment("Converts a gold block into a sponge when lit")
        @MultiplayerSynced
        @ValueOnVanillaServer(booleanValue = TriBoolean.FALSE)
        public Boolean enableGoldBlockHerobrineSummoner = true;

        @ConfigName("Enable Gold Boots Soul Speed")
        @Comment("Negates the slow-down effect of soul sand")
        @MultiplayerSynced
        @ValueOnVanillaServer(booleanValue = TriBoolean.FALSE)
        public Boolean enableGoldBootsSoulSpeed = true;

        @ConfigName("Enable Gold Chestplate Projectile Protection")
        @Comment("Only reduces skeleton arrow damage")
        @MultiplayerSynced
        @ValueOnVanillaServer(booleanValue = TriBoolean.FALSE)
        public Boolean enableGoldChestplateProjectileProtection = true;

        @ConfigName("Enable Gold Helmet Blast Protection")
        @Comment("Only reduces creeper explosion damage")
        @MultiplayerSynced
        @ValueOnVanillaServer(booleanValue = TriBoolean.FALSE)
        public Boolean enableGoldHelmetBlastProtection = true;

        @ConfigName("Enable Gold Hoe Fortune")
        @Comment("Fortune is just x2 on crops and flint")
        @MultiplayerSynced
        @ValueOnVanillaServer(booleanValue = TriBoolean.FALSE)
        public Boolean enableGoldHoeFortune = true;

        @ConfigName("Enable Gold Leggings Thorns")
        @Comment("Only damages attacking zombies and spiders")
        @MultiplayerSynced
        @ValueOnVanillaServer(booleanValue = TriBoolean.FALSE)
        public Boolean enableGoldLeggingsThorns = true;

        @ConfigName("Enable Gold Pickaxe Silk Touch")
        @Comment("Tool must be effective on block for effect")
        @MultiplayerSynced
        @ValueOnVanillaServer(booleanValue = TriBoolean.FALSE)
        public Boolean enableGoldPickaxeSilkTouch = true;

        @ConfigName("Enable Gold Shovel Silk Touch")
        @Comment("Tool must be effective on block for effect")
        @MultiplayerSynced
        @ValueOnVanillaServer(booleanValue = TriBoolean.FALSE)
        public Boolean enableGoldShovelSilkTouch = true;

        @ConfigName("Enable Gold Sword Looting")
        @Comment("Looting is a simple +1 to mob drops")
        @MultiplayerSynced
        @ValueOnVanillaServer(booleanValue = TriBoolean.FALSE)
        public Boolean enableGoldSwordLooting = true;

        @ConfigName("Enable Sponge Soaking Up Water")
        @Comment("Will soak up a 5x5 water cube when placed")
        @MultiplayerSynced
        @ValueOnVanillaServer(booleanValue = TriBoolean.FALSE)
        public Boolean enableSpongeSoaksUpWater = true;
    }
}
