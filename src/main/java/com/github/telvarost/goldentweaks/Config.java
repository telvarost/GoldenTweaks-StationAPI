package com.github.telvarost.goldentweaks;

import blue.endless.jankson.Comment;
import net.glasslauncher.mods.api.gcapi.api.*;

public class Config {

    @GConfig(value = "config", visibleName = "GoldenTweaks")
    public static ConfigFields config = new ConfigFields();

    public static class ConfigFields {

        @ConfigName("Enable Gold Axe Silk Touch")
        @MultiplayerSynced
        @ValueOnVanillaServer(booleanValue = TriBoolean.FALSE)
        public Boolean enableGoldAxeSilkTouch = true;

        @ConfigName("Enable Gold Hoe Fortune")
        @MultiplayerSynced
        @ValueOnVanillaServer(booleanValue = TriBoolean.FALSE)
        public Boolean enableGoldHoeFortune = true;

        @ConfigName("Enable Gold Pickaxe Silk Touch")
        @MultiplayerSynced
        @ValueOnVanillaServer(booleanValue = TriBoolean.FALSE)
        public Boolean enableGoldPickaxeSilkTouch = true;

        @ConfigName("Enable Gold Shovel Silk Touch")
        @MultiplayerSynced
        @ValueOnVanillaServer(booleanValue = TriBoolean.FALSE)
        public Boolean enableGoldShovelSilkTouch = true;

        @ConfigName("Enable Gold Sword Looting")
        @MultiplayerSynced
        @ValueOnVanillaServer(booleanValue = TriBoolean.FALSE)
        public Boolean enableGoldSwordLooting = true;
    }
}
