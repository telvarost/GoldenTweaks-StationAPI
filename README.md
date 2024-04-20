# GoldenTweaks StationAPI Edition for Minecraft Beta 1.7.3

Gives gold tools special properties such as silk touch, fortune, or looting.
Gives gold armor some special properties as well (limited mostly to certain mob types).

**This version of the mod now supports multiplayer!**
- Use GlassConfigAPI version 2.0+ to set up the config.

## List of changes

### Gold Tools
* Gold Axe Silk Touch For:
  * Bookshelves
  * Wood Stairs
* Gold Shovel Silk Touch For:
  * Clay Blocks
  * Farmland Blocks
  * Grass Blocks
  * Snow Blocks
  * Snow Layers
* Gold Pickaxe Silk Touch For:
  * Cobblestone Stairs
  * Glass Blocks
  * Glowstone Blocks
  * Ice Blocks
  * Ore Blocks
    * Coal, Redstone, Lapis, and Diamond
    * To be able to mine some of these ores the mining level of the gold pickaxe was raised from 0 to 2
  * Stone Blocks
* Gold Hoe Fortune For:
  * Wheat Crops
    * x2 wheat when full-grown
    * +1 seeds drop
  * Gravel Blocks
    * x2 flint
* Gold Sword Looting For:
  * All mobs
    * +1 drop for each item type the mob drops
* Gold Sword Silk Touch For:
  * Cake when cake is full/uneaten

### Gold Armor
* Gold Helmet Blast Protection:
  * Reduces explosion damage from creepers by 20%
* Gold Chestplate Projectile Protection:
  * Reduces skeleton arrow damage by 20%
* Gold Leggings Thorns:
  * Spiders that attack the player receive 1 heart of damage per attack
* Gold Boots Soul Speed:
  * Negates the effects of soul sand on player walking speed
    * Player walking speed on soul sand increased from 40% back to 100%

### Gold Misc.
* Gold Apple Cure Fire:
  * Golden apples will now cure fire effect on player when eaten
    * Note that this does nothing if in lava or very little if standing in flames
    * It is simply to put out the fire when you have escaped it and water is not available
* Fishing Rod Luck Of The Sea:
  * 10% chance to catch a gold ingot along with the fish on a successful catch
* Gold Block Herobrine Summoner:
  * Summons lightning when lit and converts a single gold block into a sponge block
    * Sponge block soaks up a 5x5 cube of water when placed
    * Summoner is built using 9 gold blocks, 4 redstone torches, and 1 netherrack
      * Base is 3x3 square of gold blocks
      * On top of the base place 4 redstone torches in each cardinal direction with one netherrack in the middle

## Installation using Prism Launcher

1. Download an instance of Babric for Prism Launcher: https://github.com/babric/prism-instance
2. Install Java 17, set the instance to use it, and disable compatibility checks on the instance: https://adoptium.net/temurin/releases/
3. Add StationAPI to the mod folder for the instance: https://modrinth.com/mod/stationapi
4. (Optional) Add Mod Menu to the mod folder for the instance: https://modrinth.com/mod/modmenu-beta
5. (Optional) Add GlassConfigAPI 2.0+ to the mod folder for the instance: https://modrinth.com/mod/glass-config-api
6. Add this mod to the mod folder for the instance: https://github.com/telvarost/GoldenTweaks-StationAPI/releases
7. Run and enjoy! 👍

## Feedback

Got any suggestions on what should be added next? Feel free to share it by [creating an issue](https://github.com/telvarost/GoldenTweaks-StationAPI/issues/new). Know how to code and want to do it yourself? Then look below on how to get started.

## Contributing

Thanks for considering contributing! To get started fork this repository, make your changes, and create a PR. 

If you are new to StationAPI consider watching the following videos on Babric/StationAPI Minecraft modding: https://www.youtube.com/watch?v=9-sVGjnGJ5s&list=PLa2JWzyvH63wGcj5-i0P12VkJG7PDyo9T
