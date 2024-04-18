package com.github.telvarost.goldentweaks;

import blue.endless.jankson.Comment;
import net.glasslauncher.mods.api.gcapi.api.*;

public class Config {

    @GConfig(value = "config", visibleName = "GoldenTweaks")
    public static ConfigFields config = new ConfigFields();

    public static class ConfigFields {

        @ConfigName("Enable Gold Axe Silk Touch")
        @Comment("Tool must be effective on block for effect")
        @MultiplayerSynced
        @ValueOnVanillaServer(booleanValue = TriBoolean.FALSE)
        public Boolean enableGoldAxeSilkTouch = true;

        @ConfigName("Enable Gold Hoe Fortune")
        @Comment("Fortune is just x2 or 1/8 duplication chance")
        @MultiplayerSynced
        @ValueOnVanillaServer(booleanValue = TriBoolean.FALSE)
        public Boolean enableGoldHoeFortune = true;

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
    }
}
