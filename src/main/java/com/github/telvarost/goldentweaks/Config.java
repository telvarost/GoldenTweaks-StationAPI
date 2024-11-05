package com.github.telvarost.goldentweaks;

import net.glasslauncher.mods.gcapi3.api.*;

public class Config {

    @ConfigRoot(value = "config", visibleName = "GoldenTweaks")
    public static ConfigFields config = new ConfigFields();

    public static class ConfigFields {

        @ConfigEntry(
                name = "Enable Fishing Rod Luck Of The Sea",
                description = "10% chance to catch a gold ingot with a fish",
                multiplayerSynced = true
        )
        public Boolean enableFishingRodLuckOfTheSea = true;

        @ConfigEntry(
                name = "Enable Gold Apple Cure Fire",
                description = "Cancels fire effect one time when eaten",
                multiplayerSynced = true
        )
        public Boolean enableGoldAppleCureFire = true;

        @ConfigEntry(
                name = "Enable Gold Axe Silk Touch",
                description = "Tool must be effective on block for effect",
                multiplayerSynced = true
        )
        public Boolean enableGoldAxeSilkTouch = true;

        @ConfigEntry(
                name = "Enable Gold Block Herobrine Summoner",
                description = "Converts a gold block into a sponge when lit",
                multiplayerSynced = true
        )
        public Boolean enableGoldBlockHerobrineSummoner = true;

        @ConfigEntry(
                name = "Enable Gold Boots Soul Speed",
                description = "Negates the slow-down effect of soul sand",
                multiplayerSynced = true
        )
        public Boolean enableGoldBootsSoulSpeed = true;

        @ConfigEntry(
                name = "Enable Gold Chestplate Projectile Protection",
                description = "Only reduces skeleton arrow damage",
                multiplayerSynced = true
        )
        public Boolean enableGoldChestplateProjectileProtection = true;

        @ConfigEntry(
                name = "Enable Gold Helmet Blast Protection",
                description = "Only reduces creeper explosion damage",
                multiplayerSynced = true
        )
        public Boolean enableGoldHelmetBlastProtection = true;

        @ConfigEntry(
                name = "Enable Gold Hoe Fortune",
                description = "Fortune is just x2 on crops and flint",
                multiplayerSynced = true
        )
        public Boolean enableGoldHoeFortune = true;

        @ConfigEntry(
                name = "Enable Gold Leggings Thorns",
                description = "Only damages attacking zombies and spiders",
                multiplayerSynced = true
        )
        public Boolean enableGoldLeggingsThorns = true;

        @ConfigEntry(
                name = "Enable Gold Pickaxe Silk Touch",
                description = "Tool must be effective on block for effect",
                multiplayerSynced = true
        )
        public Boolean enableGoldPickaxeSilkTouch = true;

        @ConfigEntry(
                name = "Enable Gold Shovel Silk Touch",
                description = "Tool must be effective on block for effect",
                multiplayerSynced = true
        )
        public Boolean enableGoldShovelSilkTouch = true;

        @ConfigEntry(
                name = "Enable Gold Sword Looting",
                description = "Looting is a simple +1 to mob drops",
                multiplayerSynced = true
        )
        public Boolean enableGoldSwordLooting = true;

        @ConfigEntry(
                name = "Enable Sponge Soaking Up Water",
                description = "Will soak up a 5x5 water cube when placed",
                multiplayerSynced = true
        )
        public Boolean enableSpongeSoaksUpWater = true;
    }
}
