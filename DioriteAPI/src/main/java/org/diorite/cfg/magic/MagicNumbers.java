package org.diorite.cfg.magic;

@SuppressWarnings({"MagicNumber"})
public final class MagicNumbers
{
    /*
     * ==================
     * Multithreading
     * ==================
     */
    @MagicNumber("threads.chunk.maxActions")
    public static final int THREADS__CHUNK__MAX_ACTIONS = 50;

    /*
     * ==================
     * ...
     * ==================
     */

    @MagicNumber("items.defaultStackSize")
    public static final int ITEMS__DEFAULT_STACK_SIZE = 64;

    @MagicNumber("player.moveSpeed")
    public static double PLAYER__MOVE_SPEED = 0.10000000149011612;

    /*
     * ==================
     * Material section
     * ==================
     */

    // material/stone
    @MagicNumber("material.stone.hardness")
    public static float MATERIAL__STONE__HARDNESS         = 1.5f;
    @MagicNumber("material.stone.blastResistance")
    public static float MATERIAL__STONE__BLAST_RESISTANCE = 30;

    // material/sand stone
    @MagicNumber("material.sand_stone.hardness")
    public static float MATERIAL__SAND_STONE__HARDNESS         = 0.8f;
    @MagicNumber("material.sand_stone.blastResistance")
    public static float MATERIAL__SAND_STONE__BLAST_RESISTANCE = 4;

    // material/sapling
    @MagicNumber("material.sapling.hardness")
    public static float MATERIAL__SAPLING__HARDNESS         = 0;
    @MagicNumber("material.sapling.blastResistance")
    public static float MATERIAL__SAPLING__BLAST_RESISTANCE = 0;

    // material/planks
    @MagicNumber("material.planks.hardness")
    public static float MATERIAL__PLANKS__HARDNESS         = 2;
    @MagicNumber("material.planks.blastResistance")
    public static float MATERIAL__PLANKS__BLAST_RESISTANCE = 15;

    // material/log
    @MagicNumber("material.log.hardness")
    public static float MATERIAL__LOG__HARDNESS         = 2;
    @MagicNumber("material.log.blastResistance")
    public static float MATERIAL__LOG__BLAST_RESISTANCE = 10;

    // material/leaves
    @MagicNumber("material.leaves.hardness")
    public static float MATERIAL__LEAVES__HARDNESS         = 0.2f;
    @MagicNumber("material.leaves.blastResistance")
    public static float MATERIAL__LEAVES__BLAST_RESISTANCE = 1;

    // material/grass
    @MagicNumber("material.grass.hardness")
    public static float MATERIAL__GRASS__HARDNESS         = 0.6f;
    @MagicNumber("material.grass.blastResistance")
    public static float MATERIAL__GRASS__BLAST_RESISTANCE = 3;

    // material/dirt
    @MagicNumber("material.dirt.hardness")
    public static float MATERIAL__DIRT__HARDNESS         = 0.5f;
    @MagicNumber("material.dirt.blastResistance")
    public static float MATERIAL__DIRT__BLAST_RESISTANCE = 2.5f;

    // material/sand
    @MagicNumber("material.sand.hardness")
    public static float MATERIAL__SAND__HARDNESS         = 0.5f;
    @MagicNumber("material.sand.blastResistance")
    public static float MATERIAL__SAND__BLAST_RESISTANCE = 2.5f;

    // material/bed block
    @MagicNumber("material.bed_block.hardness")
    public static float MATERIAL__BED_BLOCK__HARDNESS         = 0.2f;
    @MagicNumber("material.bed_block.blastResistance")
    public static float MATERIAL__BED_BLOCK__BLAST_RESISTANCE = 1;

    // material/powered rail
    @MagicNumber("material.powered_rail.hardness")
    public static float MATERIAL__POWERED_RAIL__HARDNESS         = 0.7f;
    @MagicNumber("material.powered_rail.blastResistance")
    public static float MATERIAL__POWERED_RAIL__BLAST_RESISTANCE = 3.5f;

    // material/detector rail
    @MagicNumber("material.detector_rail.hardness")
    public static float MATERIAL__DETECTOR_RAIL__HARDNESS         = 0.7f;
    @MagicNumber("material.detector_rail.blastResistance")
    public static float MATERIAL__DETECTOR_RAIL__BLAST_RESISTANCE = 3.5f;

    // material/sponge
    @MagicNumber("material.sponge.hardness")
    public static float MATERIAL__SPONGE__HARDNESS         = 0.6f;
    @MagicNumber("material.sponge.blastResistance")
    public static float MATERIAL__SPONGE__BLAST_RESISTANCE = 3;

    // material/glass
    @MagicNumber("material.glass.hardness")
    public static float MATERIAL__GLASS__HARDNESS         = 0.3f;
    @MagicNumber("material.glass.blastResistance")
    public static float MATERIAL__GLASS__BLAST_RESISTANCE = 1.5f;

    // material/diamond ore
    @MagicNumber("material.diamond_ore.hardness")
    public static float MATERIAL__DIAMOND_ORE__HARDNESS         = 3;
    @MagicNumber("material.diamond_ore.blastResistance")
    public static float MATERIAL__DIAMOND_ORE__BLAST_RESISTANCE = 15;

    // material/clay block
    @MagicNumber("material.clay_block.hardness")
    public static float MATERIAL__CLAY_BLOCK__HARDNESS         = 0.6f;
    @MagicNumber("material.clay_block.blastResistance")
    public static float MATERIAL__CLAY_BLOCK__BLAST_RESISTANCE = 3;

    // material/spruce fence
    @MagicNumber("material.spruce_fence.hardness")
    public static float MATERIAL__SPRUCE_FENCE__HARDNESS         = 2;
    @MagicNumber("material.spruce_fence.blastResistance")
    public static float MATERIAL__SPRUCE_FENCE__BLAST_RESISTANCE = 15;

    // material/birch fence
    @MagicNumber("material.birch_fence.hardness")
    public static float MATERIAL__BIRCH_FENCE__HARDNESS         = 2;
    @MagicNumber("material.birch_fence.blastResistance")
    public static float MATERIAL__BIRCH_FENCE__BLAST_RESISTANCE = 15;

    // material/jungle fence
    @MagicNumber("material.jungle_fence.hardness")
    public static float MATERIAL__JUNGLE_FENCE__HARDNESS         = 2;
    @MagicNumber("material.jungle_fence.blastResistance")
    public static float MATERIAL__JUNGLE_FENCE__BLAST_RESISTANCE = 15;

    // material/dark oak fence
    @MagicNumber("material.dark_oak_fence.hardness")
    public static float MATERIAL__DARK_OAK_FENCE__HARDNESS         = 2;
    @MagicNumber("material.dark_oak_fence.blastResistance")
    public static float MATERIAL__DARK_OAK_FENCE__BLAST_RESISTANCE = 15;

    // material/coal block
    @MagicNumber("material.coal_block.hardness")
    public static float MATERIAL__COAL_BLOCK__HARDNESS         = 5;
    @MagicNumber("material.coal_block.blastResistance")
    public static float MATERIAL__COAL_BLOCK__BLAST_RESISTANCE = 30;

    // material/packed ice
    @MagicNumber("material.packed_ice.hardness")
    public static float MATERIAL__PACKED_ICE__HARDNESS         = 0.5f;
    @MagicNumber("material.packed_ice.blastResistance")
    public static float MATERIAL__PACKED_ICE__BLAST_RESISTANCE = 2.5f;

    // material/quartz ore
    @MagicNumber("material.quartz_ore.hardness")
    public static float MATERIAL__QUARTZ_ORE__HARDNESS         = 3;
    @MagicNumber("material.quartz_ore.blastResistance")
    public static float MATERIAL__QUARTZ_ORE__BLAST_RESISTANCE = 15;

    // material/sea lantren
    @MagicNumber("material.sea_lantren.hardness")
    public static float MATERIAL__SEA_LANTREN__HARDNESS         = 0.3f;
    @MagicNumber("material.sea_lantren.blastResistance")
    public static float MATERIAL__SEA_LANTREN__BLAST_RESISTANCE = 1.5f;

    // material/hardened clay
    @MagicNumber("material.hardened_clay.hardness")
    public static float MATERIAL__HARDENED_CLAY__HARDNESS         = 1.25f;
    @MagicNumber("material.hardened_clay.blastResistance")
    public static float MATERIAL__HARDENED_CLAY__BLAST_RESISTANCE = 30;

    // material/acacia fence
    @MagicNumber("material.acacia_fence.hardness")
    public static float MATERIAL__ACACIA_FENCE__HARDNESS         = 2;
    @MagicNumber("material.acacia_fence.blastResistance")
    public static float MATERIAL__ACACIA_FENCE__BLAST_RESISTANCE = 15;

    // material/barrier
    @MagicNumber("material.barrier.hardness")
    public static float MATERIAL__BARRIER__HARDNESS         = - 1;
    @MagicNumber("material.barrier.blastResistance")
    public static float MATERIAL__BARRIER__BLAST_RESISTANCE = 18_000_003;

    // material/slime block
    @MagicNumber("material.slime_block.hardness")
    public static float MATERIAL__SLIME_BLOCK__HARDNESS         = 0;
    @MagicNumber("material.slime_block.blastResistance")
    public static float MATERIAL__SLIME_BLOCK__BLAST_RESISTANCE = 0;

    // material/melon block
    @MagicNumber("material.melon_block.hardness")
    public static float MATERIAL__MELON_BLOCK__HARDNESS         = 1;
    @MagicNumber("material.melon_block.blastResistance")
    public static float MATERIAL__MELON_BLOCK__BLAST_RESISTANCE = 5;

    // material/beacon
    @MagicNumber("material.beacon.hardness")
    public static float MATERIAL__BEACON__HARDNESS         = 3;
    @MagicNumber("material.beacon.blastResistance")
    public static float MATERIAL__BEACON__BLAST_RESISTANCE = 15;

    // material/redstone block
    @MagicNumber("material.redstone_block.hardness")
    public static float MATERIAL__REDSTONE_BLOCK__HARDNESS         = 5;
    @MagicNumber("material.redstone_block.blastResistance")
    public static float MATERIAL__REDSTONE_BLOCK__BLAST_RESISTANCE = 30;

    // material/emerald ore
    @MagicNumber("material.emerald_ore.hardness")
    public static float MATERIAL__EMERALD_ORE__HARDNESS         = 3;
    @MagicNumber("material.emerald_ore.blastResistance")
    public static float MATERIAL__EMERALD_ORE__BLAST_RESISTANCE = 15;

    // material/emerald block
    @MagicNumber("material.emerald_block.hardness")
    public static float MATERIAL__EMERALD_BLOCK__HARDNESS         = 5;
    @MagicNumber("material.emerald_block.blastResistance")
    public static float MATERIAL__EMERALD_BLOCK__BLAST_RESISTANCE = 30;

    // material/redstone lamp off
    @MagicNumber("material.redstone_lamp_off.hardness")
    public static float MATERIAL__REDSTONE_LAMP_OFF__HARDNESS         = 0.3f;
    @MagicNumber("material.redstone_lamp_off.blastResistance")
    public static float MATERIAL__REDSTONE_LAMP_OFF__BLAST_RESISTANCE = 1.5f;

    // material/redstone lamp on
    @MagicNumber("material.redstone_lamp_on.hardness")
    public static float MATERIAL__REDSTONE_LAMP_ON__HARDNESS         = 0.3f;
    @MagicNumber("material.redstone_lamp_on.blastResistance")
    public static float MATERIAL__REDSTONE_LAMP_ON__BLAST_RESISTANCE = 1.5f;

    // material/dragon egg
    @MagicNumber("material.dragon_egg.hardness")
    public static float MATERIAL__DRAGON_EGG__HARDNESS         = 3;
    @MagicNumber("material.dragon_egg.blastResistance")
    public static float MATERIAL__DRAGON_EGG__BLAST_RESISTANCE = 45;

    // material/water lily
    @MagicNumber("material.water_lily.hardness")
    public static float MATERIAL__WATER_LILY__HARDNESS         = 0.6f;
    @MagicNumber("material.water_lily.blastResistance")
    public static float MATERIAL__WATER_LILY__BLAST_RESISTANCE = 0;

    // material/nether brick
    @MagicNumber("material.nether_brick.hardness")
    public static float MATERIAL__NETHER_BRICK__HARDNESS         = 2;
    @MagicNumber("material.nether_brick.blastResistance")
    public static float MATERIAL__NETHER_BRICK__BLAST_RESISTANCE = 30;

    // material/end stone
    @MagicNumber("material.end_stone.hardness")
    public static float MATERIAL__END_STONE__HARDNESS         = 3;
    @MagicNumber("material.end_stone.blastResistance")
    public static float MATERIAL__END_STONE__BLAST_RESISTANCE = 45;

    // material/mycelium
    @MagicNumber("material.mycelium.hardness")
    public static float MATERIAL__MYCELIUM__HARDNESS         = 0.6f;
    @MagicNumber("material.mycelium.blastResistance")
    public static float MATERIAL__MYCELIUM__BLAST_RESISTANCE = 2.5f;

    // material/oak fence
    @MagicNumber("material.oak_fence.hardness")
    public static float MATERIAL__OAK_FENCE__HARDNESS         = 2;
    @MagicNumber("material.oak_fence.blastResistance")
    public static float MATERIAL__OAK_FENCE__BLAST_RESISTANCE = 15;

    // material/netherrack
    @MagicNumber("material.netherrack.hardness")
    public static float MATERIAL__NETHERRACK__HARDNESS         = 0.4f;
    @MagicNumber("material.netherrack.blastResistance")
    public static float MATERIAL__NETHERRACK__BLAST_RESISTANCE = 2;

    // material/glass pane
    @MagicNumber("material.glass_pane.hardness")
    public static float MATERIAL__GLASS_PANE__HARDNESS         = 0.3f;
    @MagicNumber("material.glass_pane.blastResistance")
    public static float MATERIAL__GLASS_PANE__BLAST_RESISTANCE = 1.5f;

    // material/iron bars
    @MagicNumber("material.iron_bars.hardness")
    public static float MATERIAL__IRON_BARS__HARDNESS         = 5;
    @MagicNumber("material.iron_bars.blastResistance")
    public static float MATERIAL__IRON_BARS__BLAST_RESISTANCE = 30;

    // material/glowstone
    @MagicNumber("material.glowstone.hardness")
    public static float MATERIAL__GLOWSTONE__HARDNESS         = 0.3f;
    @MagicNumber("material.glowstone.blastResistance")
    public static float MATERIAL__GLOWSTONE__BLAST_RESISTANCE = 1.5f;

    // material/soul sand
    @MagicNumber("material.soul_sand.hardness")
    public static float MATERIAL__SOUL_SAND__HARDNESS         = 0.5f;
    @MagicNumber("material.soul_sand.blastResistance")
    public static float MATERIAL__SOUL_SAND__BLAST_RESISTANCE = 2.5f;

    // material/nether brick fence
    @MagicNumber("material.nether_brick_fence.hardness")
    public static float MATERIAL__NETHER_BRICK_FENCE__HARDNESS         = 2;
    @MagicNumber("material.nether_brick_fence.blastResistance")
    public static float MATERIAL__NETHER_BRICK_FENCE__BLAST_RESISTANCE = 30;

    // material/snow block
    @MagicNumber("material.snow_block.hardness")
    public static float MATERIAL__SNOW_BLOCK__HARDNESS         = 0.2f;
    @MagicNumber("material.snow_block.blastResistance")
    public static float MATERIAL__SNOW_BLOCK__BLAST_RESISTANCE = 1;

    // material/crafting table
    @MagicNumber("material.crafting_table.hardness")
    public static float MATERIAL__CRAFTING_TABLE__HARDNESS         = 2.5f;
    @MagicNumber("material.crafting_table.blastResistance")
    public static float MATERIAL__CRAFTING_TABLE__BLAST_RESISTANCE = 12.5f;

    // material/redstone ore
    @MagicNumber("material.redstone_ore.hardness")
    public static float MATERIAL__REDSTONE_ORE__HARDNESS         = 3;
    @MagicNumber("material.redstone_ore.blastResistance")
    public static float MATERIAL__REDSTONE_ORE__BLAST_RESISTANCE = 15;

    // material/ice
    @MagicNumber("material.ice.hardness")
    public static float MATERIAL__ICE__HARDNESS         = 0.5f;
    @MagicNumber("material.ice.blastResistance")
    public static float MATERIAL__ICE__BLAST_RESISTANCE = 2.5f;

    // material/redstone ore glowing
    @MagicNumber("material.redstone_ore_glowing.hardness")
    public static float MATERIAL__REDSTONE_ORE_GLOWING__HARDNESS         = 3;
    @MagicNumber("material.redstone_ore_glowing.blastResistance")
    public static float MATERIAL__REDSTONE_ORE_GLOWING__BLAST_RESISTANCE = 15;

    // material/diamond block
    @MagicNumber("material.diamond_block.hardness")
    public static float MATERIAL__DIAMOND_BLOCK__HARDNESS         = 5;
    @MagicNumber("material.diamond_block.blastResistance")
    public static float MATERIAL__DIAMOND_BLOCK__BLAST_RESISTANCE = 30;

    // material/tnt
    @MagicNumber("material.tnt.hardness")
    public static float MATERIAL__TNT__HARDNESS         = 0;
    @MagicNumber("material.tnt.blastResistance")
    public static float MATERIAL__TNT__BLAST_RESISTANCE = 0;

    // material/obsidian
    @MagicNumber("material.obsidian.hardness")
    public static float MATERIAL__OBSIDIAN__HARDNESS         = 50;
    @MagicNumber("material.obsidian.blastResistance")
    public static float MATERIAL__OBSIDIAN__BLAST_RESISTANCE = 6000;

    // material/gravel
    @MagicNumber("material.gravel.hardness")
    public static float MATERIAL__GRAVEL__HARDNESS         = 0.5f;
    @MagicNumber("material.gravel.blastResistance")
    public static float MATERIAL__GRAVEL__BLAST_RESISTANCE = 2.5f;

    // material/mossy cobblestone
    @MagicNumber("material.mossy_cobblestone.hardness")
    public static float MATERIAL__MOSSY_COBBLESTONE__HARDNESS         = 2;
    @MagicNumber("material.mossy_cobblestone.blastResistance")
    public static float MATERIAL__MOSSY_COBBLESTONE__BLAST_RESISTANCE = 30;

    // material/bookshelf
    @MagicNumber("material.bookshelf.hardness")
    public static float MATERIAL__BOOKSHELF__HARDNESS         = 1.5f;
    @MagicNumber("material.bookshelf.blastResistance")
    public static float MATERIAL__BOOKSHELF__BLAST_RESISTANCE = 7.5f;

    // material/brick block
    @MagicNumber("material.brick_block.hardness")
    public static float MATERIAL__BRICK_BLOCK__HARDNESS         = 2;
    @MagicNumber("material.brick_block.blastResistance")
    public static float MATERIAL__BRICK_BLOCK__BLAST_RESISTANCE = 30;

    // material/torch
    @MagicNumber("material.torch.hardness")
    public static float MATERIAL__TORCH__HARDNESS         = 0;
    @MagicNumber("material.torch.blastResistance")
    public static float MATERIAL__TORCH__BLAST_RESISTANCE = 0;

    // material/fire
    @MagicNumber("material.fire.hardness")
    public static float MATERIAL__FIRE__HARDNESS         = 0;
    @MagicNumber("material.fire.blastResistance")
    public static float MATERIAL__FIRE__BLAST_RESISTANCE = 0;

    // material/mob spawner
    @MagicNumber("material.mob_spawner.hardness")
    public static float MATERIAL__MOB_SPAWNER__HARDNESS         = 5;
    @MagicNumber("material.mob_spawner.blastResistance")
    public static float MATERIAL__MOB_SPAWNER__BLAST_RESISTANCE = 25;

    // material/oak stairs
    @MagicNumber("material.oak_stairs.hardness")
    public static float MATERIAL__OAK_STAIRS__HARDNESS         = 2;
    @MagicNumber("material.oak_stairs.blastResistance")
    public static float MATERIAL__OAK_STAIRS__BLAST_RESISTANCE = 15;

    // material/spruce stairs
    @MagicNumber("material.spruce_stairs.hardness")
    public static float MATERIAL__SPRUCE_STAIRS__HARDNESS         = 2;
    @MagicNumber("material.spruce_stairs.blastResistance")
    public static float MATERIAL__SPRUCE_STAIRS__BLAST_RESISTANCE = 15;

    // material/birch stairs
    @MagicNumber("material.birch_stairs.hardness")
    public static float MATERIAL__BIRCH_STAIRS__HARDNESS         = 2;
    @MagicNumber("material.birch_stairs.blastResistance")
    public static float MATERIAL__BIRCH_STAIRS__BLAST_RESISTANCE = 15;

    // material/jungle stairs
    @MagicNumber("material.jungle_stairs.hardness")
    public static float MATERIAL__JUNGLE_STAIRS__HARDNESS         = 2;
    @MagicNumber("material.jungle_stairs.blastResistance")
    public static float MATERIAL__JUNGLE_STAIRS__BLAST_RESISTANCE = 15;

    // material/acacia stairs
    @MagicNumber("material.acacia_stairs.hardness")
    public static float MATERIAL__ACACIA_STAIRS__HARDNESS         = 2;
    @MagicNumber("material.acacia_stairs.blastResistance")
    public static float MATERIAL__ACACIA_STAIRS__BLAST_RESISTANCE = 15;

    // material/dark oak stairs
    @MagicNumber("material.dark_oak_stairs.hardness")
    public static float MATERIAL__DARK_OAK_STAIRS__HARDNESS         = 2;
    @MagicNumber("material.dark_oak_stairs.blastResistance")
    public static float MATERIAL__DARK_OAK_STAIRS__BLAST_RESISTANCE = 15;

    // material/cobblestone stairs
    @MagicNumber("material.cobblestone_stairs.hardness")
    public static float MATERIAL__COBBLESTONE_STAIRS__HARDNESS         = 2;
    @MagicNumber("material.cobblestone_stairs.blastResistance")
    public static float MATERIAL__COBBLESTONE_STAIRS__BLAST_RESISTANCE = 30;

    // material/brick stairs
    @MagicNumber("material.brick_stairs.hardness")
    public static float MATERIAL__BRICK_STAIRS__HARDNESS         = 2;
    @MagicNumber("material.brick_stairs.blastResistance")
    public static float MATERIAL__BRICK_STAIRS__BLAST_RESISTANCE = 30;

    // material/stone brick stairs
    @MagicNumber("material.stone_brick_stairs.hardness")
    public static float MATERIAL__STONE_BRICK_STAIRS__HARDNESS         = 2;
    @MagicNumber("material.stone_brick_stairs.blastResistance")
    public static float MATERIAL__STONE_BRICK_STAIRS__BLAST_RESISTANCE = 30;

    // material/nether brick stairs
    @MagicNumber("material.nether_brick_stairs.hardness")
    public static float MATERIAL__NETHER_BRICK_STAIRS__HARDNESS         = 2;
    @MagicNumber("material.nether_brick_stairs.blastResistance")
    public static float MATERIAL__NETHER_BRICK_STAIRS__BLAST_RESISTANCE = 30;

    // material/sandstone stairs
    @MagicNumber("material.sandstone_stairs.hardness")
    public static float MATERIAL__SANDSTONE_STAIRS__HARDNESS         = 0.8f;
    @MagicNumber("material.sandstone_stairs.blastResistance")
    public static float MATERIAL__SANDSTONE_STAIRS__BLAST_RESISTANCE = 4;

    // material/quartz stairs
    @MagicNumber("material.quartz_stairs.hardness")
    public static float MATERIAL__QUARTZ_STAIRS__HARDNESS         = 0.8f;
    @MagicNumber("material.quartz_stairs.blastResistance")
    public static float MATERIAL__QUARTZ_STAIRS__BLAST_RESISTANCE = 4;

    // material/red sandstone stairs
    @MagicNumber("material.red_sandstone_stairs.hardness")
    public static float MATERIAL__RED_SANDSTONE_STAIRS__HARDNESS         = 0.8f;
    @MagicNumber("material.red_sandstone_stairs.blastResistance")
    public static float MATERIAL__RED_SANDSTONE_STAIRS__BLAST_RESISTANCE = 4;

    // material/chest
    @MagicNumber("material.chest.hardness")
    public static float MATERIAL__CHEST__HARDNESS         = 12.5f;
    @MagicNumber("material.chest.blastResistance")
    public static float MATERIAL__CHEST__BLAST_RESISTANCE = 2.5f;

    // material/redstone wire
    @MagicNumber("material.redstone_wire.hardness")
    public static float MATERIAL__REDSTONE_WIRE__HARDNESS         = 0;
    @MagicNumber("material.redstone_wire.blastResistance")
    public static float MATERIAL__REDSTONE_WIRE__BLAST_RESISTANCE = 0;

    // material/wheat block
    @MagicNumber("material.wheat_block.hardness")
    public static float MATERIAL__WHEAT_BLOCK__HARDNESS         = 0;
    @MagicNumber("material.wheat_block.blastResistance")
    public static float MATERIAL__WHEAT_BLOCK__BLAST_RESISTANCE = 0;

    // material/farmland
    @MagicNumber("material.farmland.hardness")
    public static float MATERIAL__FARMLAND__HARDNESS         = 0.6f;
    @MagicNumber("material.farmland.blastResistance")
    public static float MATERIAL__FARMLAND__BLAST_RESISTANCE = 3;

    // material/furnace
    @MagicNumber("material.furnace.hardness")
    public static float MATERIAL__FURNACE__HARDNESS         = 3.5f;
    @MagicNumber("material.furnace.blastResistance")
    public static float MATERIAL__FURNACE__BLAST_RESISTANCE = 17.5f;

    // material/burning furnace
    @MagicNumber("material.burning_furnace.hardness")
    public static float MATERIAL__BURNING_FURNACE__HARDNESS         = 3.5f;
    @MagicNumber("material.burning_furnace.blastResistance")
    public static float MATERIAL__BURNING_FURNACE__BLAST_RESISTANCE = 17.5f;

    // material/standing sign
    @MagicNumber("material.standing_sign.hardness")
    public static float MATERIAL__STANDING_SIGN__HARDNESS         = 1;
    @MagicNumber("material.standing_sign.blastResistance")
    public static float MATERIAL__STANDING_SIGN__BLAST_RESISTANCE = 5;

    // material/oak door
    @MagicNumber("material.oak_door.hardness")
    public static float MATERIAL__OAK_DOOR__HARDNESS         = 3;
    @MagicNumber("material.oak_door.blastResistance")
    public static float MATERIAL__OAK_DOOR__BLAST_RESISTANCE = 15;

    // material/spruce door
    @MagicNumber("material.spruce_door.hardness")
    public static float MATERIAL__SPRUCE_DOOR__HARDNESS         = 3;
    @MagicNumber("material.spruce_door.blastResistance")
    public static float MATERIAL__SPRUCE_DOOR__BLAST_RESISTANCE = 15;

    // material/birch door
    @MagicNumber("material.birch_door.hardness")
    public static float MATERIAL__BIRCH_DOOR__HARDNESS         = 3;
    @MagicNumber("material.birch_door.blastResistance")
    public static float MATERIAL__BIRCH_DOOR__BLAST_RESISTANCE = 15;

    // material/jungle door
    @MagicNumber("material.jungle_door.hardness")
    public static float MATERIAL__JUNGLE_DOOR__HARDNESS         = 3;
    @MagicNumber("material.jungle_door.blastResistance")
    public static float MATERIAL__JUNGLE_DOOR__BLAST_RESISTANCE = 15;

    // material/acacia door
    @MagicNumber("material.acacia_door.hardness")
    public static float MATERIAL__ACACIA_DOOR__HARDNESS         = 3;
    @MagicNumber("material.acacia_door.blastResistance")
    public static float MATERIAL__ACACIA_DOOR__BLAST_RESISTANCE = 15;

    // material/dark oak door
    @MagicNumber("material.dark_oak_door.hardness")
    public static float MATERIAL__DARK_OAK_DOOR__HARDNESS         = 3;
    @MagicNumber("material.dark_oak_door.blastResistance")
    public static float MATERIAL__DARK_OAK_DOOR__BLAST_RESISTANCE = 15;

    // material/iron door
    @MagicNumber("material.iron_door.hardness")
    public static float MATERIAL__IRON_DOOR__HARDNESS         = 5;
    @MagicNumber("material.iron_door.blastResistance")
    public static float MATERIAL__IRON_DOOR__BLAST_RESISTANCE = 25;

    // material/ladder
    @MagicNumber("material.ladder.hardness")
    public static float MATERIAL__LADDER__HARDNESS         = 0.4f;
    @MagicNumber("material.ladder.blastResistance")
    public static float MATERIAL__LADDER__BLAST_RESISTANCE = 2;

    // material/rail
    @MagicNumber("material.rail.hardness")
    public static float MATERIAL__RAIL__HARDNESS         = 0.7f;
    @MagicNumber("material.rail.blastResistance")
    public static float MATERIAL__RAIL__BLAST_RESISTANCE = 3.5f;

    // material/wall sign
    @MagicNumber("material.wall_sign.hardness")
    public static float MATERIAL__WALL_SIGN__HARDNESS         = 1;
    @MagicNumber("material.wall_sign.blastResistance")
    public static float MATERIAL__WALL_SIGN__BLAST_RESISTANCE = 5;

    // material/lever
    @MagicNumber("material.lever.hardness")
    public static float MATERIAL__LEVER__HARDNESS         = 0.5f;
    @MagicNumber("material.lever.blastResistance")
    public static float MATERIAL__LEVER__BLAST_RESISTANCE = 2.5f;

    // material/stone pressure plate
    @MagicNumber("material.stone_pressure_plate.hardness")
    public static float MATERIAL__STONE_PRESSURE_PLATE__HARDNESS         = 0.5f;
    @MagicNumber("material.stone_pressure_plate.blastResistance")
    public static float MATERIAL__STONE_PRESSURE_PLATE__BLAST_RESISTANCE = 2.5f;

    // material/wooden pressure plate
    @MagicNumber("material.wooden_pressure_plate.hardness")
    public static float MATERIAL__WOODEN_PRESSURE_PLATE__HARDNESS         = 0.5f;
    @MagicNumber("material.wooden_pressure_plate.blastResistance")
    public static float MATERIAL__WOODEN_PRESSURE_PLATE__BLAST_RESISTANCE = 2.5f;

    // material/golden pressure plate
    @MagicNumber("material.golden_pressure_plate.hardness")
    public static float MATERIAL__GOLDEN_PRESSURE_PLATE__HARDNESS         = 0.5f;
    @MagicNumber("material.golden_pressure_plate.blastResistance")
    public static float MATERIAL__GOLDEN_PRESSURE_PLATE__BLAST_RESISTANCE = 2.5f;

    // material/iron pressure plate
    @MagicNumber("material.iron_pressure_plate.hardness")
    public static float MATERIAL__IRON_PRESSURE_PLATE__HARDNESS         = 0.5f;
    @MagicNumber("material.iron_pressure_plate.blastResistance")
    public static float MATERIAL__IRON_PRESSURE_PLATE__BLAST_RESISTANCE = 2.5f;

    // material/redstone torch off
    @MagicNumber("material.redstone_torch_off.hardness")
    public static float MATERIAL__REDSTONE_TORCH_OFF__HARDNESS         = 0;
    @MagicNumber("material.redstone_torch_off.blastResistance")
    public static float MATERIAL__REDSTONE_TORCH_OFF__BLAST_RESISTANCE = 0;

    // material/redstone torch on
    @MagicNumber("material.redstone_torch_on.hardness")
    public static float MATERIAL__REDSTONE_TORCH_ON__HARDNESS         = 0;
    @MagicNumber("material.redstone_torch_on.blastResistance")
    public static float MATERIAL__REDSTONE_TORCH_ON__BLAST_RESISTANCE = 0;

    // material/stone button
    @MagicNumber("material.stone_button.hardness")
    public static float MATERIAL__STONE_BUTTON__HARDNESS         = 0.5f;
    @MagicNumber("material.stone_button.blastResistance")
    public static float MATERIAL__STONE_BUTTON__BLAST_RESISTANCE = 2.5f;

    // material/wooden button
    @MagicNumber("material.wooden_button.hardness")
    public static float MATERIAL__WOODEN_BUTTON__HARDNESS         = 0.5f;
    @MagicNumber("material.wooden_button.blastResistance")
    public static float MATERIAL__WOODEN_BUTTON__BLAST_RESISTANCE = 2.5f;

    // material/snow layer
    @MagicNumber("material.snow_layer.hardness")
    public static float MATERIAL__SNOW_LAYER__HARDNESS         = 0.1f;
    @MagicNumber("material.snow_layer.blastResistance")
    public static float MATERIAL__SNOW_LAYER__BLAST_RESISTANCE = 0.5f;

    // material/cactus
    @MagicNumber("material.cactus.hardness")
    public static float MATERIAL__CACTUS__HARDNESS         = 0.4f;
    @MagicNumber("material.cactus.blastResistance")
    public static float MATERIAL__CACTUS__BLAST_RESISTANCE = 2;

    // material/sugar cane
    @MagicNumber("material.sugar_cane.hardness")
    public static float MATERIAL__SUGAR_CANE__HARDNESS         = 0;
    @MagicNumber("material.sugar_cane.blastResistance")
    public static float MATERIAL__SUGAR_CANE__BLAST_RESISTANCE = 0;

    // material/jukebox
    @MagicNumber("material.jukebox.hardness")
    public static float MATERIAL__JUKEBOX__HARDNESS         = 2;
    @MagicNumber("material.jukebox.blastResistance")
    public static float MATERIAL__JUKEBOX__BLAST_RESISTANCE = 30;

    // material/pumpkin
    @MagicNumber("material.pumpkin.hardness")
    public static float MATERIAL__PUMPKIN__HARDNESS         = 1;
    @MagicNumber("material.pumpkin.blastResistance")
    public static float MATERIAL__PUMPKIN__BLAST_RESISTANCE = 5;

    // material/nether portal
    @MagicNumber("material.nether_portal.hardness")
    public static float MATERIAL__NETHER_PORTAL__HARDNESS         = - 1;
    @MagicNumber("material.nether_portal.blastResistance")
    public static float MATERIAL__NETHER_PORTAL__BLAST_RESISTANCE = 18_000_000;

    // material/pumpkin lantern
    @MagicNumber("material.pumpkin_lantern.hardness")
    public static float MATERIAL__PUMPKIN_LANTERN__HARDNESS         = 1;
    @MagicNumber("material.pumpkin_lantern.blastResistance")
    public static float MATERIAL__PUMPKIN_LANTERN__BLAST_RESISTANCE = 5;

    // material/cake
    @MagicNumber("material.cake.hardness")
    public static float MATERIAL__CAKE__HARDNESS         = 0.5f;
    @MagicNumber("material.cake.blastResistance")
    public static float MATERIAL__CAKE__BLAST_RESISTANCE = 2.5f;

    // material/redstone repeater off
    @MagicNumber("material.redstone_repeater_off.hardness")
    public static float MATERIAL__REPEATER_OFF__HARDNESS         = 0;
    @MagicNumber("material.redstone_repeater_off.blastResistance")
    public static float MATERIAL__REPEATER_OFF__BLAST_RESISTANCE = 0;

    // material/redstone repeater on
    @MagicNumber("material.redstone_repeater_on.hardness")
    public static float MATERIAL__REDSTONE_REPEATER_ON__HARDNESS         = 0;
    @MagicNumber("material.redstone_repeater_on.blastResistance")
    public static float MATERIAL__REDSTONE_REPEATER_ON__BLAST_RESISTANCE = 0;

    // material/stained glass
    @MagicNumber("material.stained_glass.hardness")
    public static float MATERIAL__STAINED_GLASS__HARDNESS         = 0.3f;
    @MagicNumber("material.stained_glass.blastResistance")
    public static float MATERIAL__STAINED_GLASS__BLAST_RESISTANCE = 1.5f;

    // material/wooden trapdoor
    @MagicNumber("material.wooden_trapdoor.hardness")
    public static float MATERIAL__WOODEN_TRAPDOOR__HARDNESS         = 3;
    @MagicNumber("material.wooden_trapdoor.blastResistance")
    public static float MATERIAL__WOODEN_TRAPDOOR__BLAST_RESISTANCE = 15;

    // material/iron trapdoor
    @MagicNumber("material.iron_trapdoor.hardness")
    public static float MATERIAL__IRON_TRAPDOOR__HARDNESS         = 5;
    @MagicNumber("material.iron_trapdoor.blastResistance")
    public static float MATERIAL__IRON_TRAPDOOR__BLAST_RESISTANCE = 25;

    // material/monster egg trap
    @MagicNumber("material.monster_egg_trap.hardness")
    public static float MATERIAL__MONSTER_EGG_TRAP__HARDNESS         = 0.75f;
    @MagicNumber("material.monster_egg_trap.blastResistance")
    public static float MATERIAL__MONSTER_EGG_TRAP__BLAST_RESISTANCE = 3.75f;

    // material/stone brick
    @MagicNumber("material.stone_brick.hardness")
    public static float MATERIAL__STONE_BRICK__HARDNESS         = 1.5f;
    @MagicNumber("material.stone_brick.blastResistance")
    public static float MATERIAL__STONE_BRICK__BLAST_RESISTANCE = 30;

    // material/brown mushroom block
    @MagicNumber("material.brown_mushroom_block.hardness")
    public static float MATERIAL__BROWN_MUSHROOM_BLOCK__HARDNESS         = 0.2f;
    @MagicNumber("material.brown_mushroom_block.blastResistance")
    public static float MATERIAL__BROWN_MUSHROOM_BLOCK__BLAST_RESISTANCE = 1;

    // material/red mushroom block
    @MagicNumber("material.red_mushroom_block.hardness")
    public static float MATERIAL__RED_MUSHROOM_BLOCK__HARDNESS         = 0.2f;
    @MagicNumber("material.red_mushroom_block.blastResistance")
    public static float MATERIAL__RED_MUSHROOM_BLOCK__BLAST_RESISTANCE = 1;

    // material/pumpkin stem
    @MagicNumber("material.pumpkin_stem.hardness")
    public static float MATERIAL__PUMPKIN_STEM__HARDNESS         = 0;
    @MagicNumber("material.pumpkin_stem.blastResistance")
    public static float MATERIAL__PUMPKIN_STEM__BLAST_RESISTANCE = 0;

    // material/melon stem
    @MagicNumber("material.melon_stem.hardness")
    public static float MATERIAL__MELON_STEM__HARDNESS         = 0;
    @MagicNumber("material.melon_stem.blastResistance")
    public static float MATERIAL__MELON_STEM__BLAST_RESISTANCE = 0;

    // material/vine
    @MagicNumber("material.vine.hardness")
    public static float MATERIAL__VINE__HARDNESS         = 0.2f;
    @MagicNumber("material.vine.blastResistance")
    public static float MATERIAL__VINE__BLAST_RESISTANCE = 1;

    // material/oak fence gate
    @MagicNumber("material.oak_fence_gate.hardness")
    public static float MATERIAL__OAK_FENCE_GATE__HARDNESS         = 2;
    @MagicNumber("material.oak_fence_gate.blastResistance")
    public static float MATERIAL__OAK_FENCE_GATE__BLAST_RESISTANCE = 15;

    // material/spruce fence gate
    @MagicNumber("material.spruce_fence_gate.hardness")
    public static float MATERIAL__SPRUCE_FENCE_GATE__HARDNESS         = 2;
    @MagicNumber("material.spruce_fence_gate.blastResistance")
    public static float MATERIAL__SPRUCE_FENCE_GATE__BLAST_RESISTANCE = 15;

    // material/birch fence gate
    @MagicNumber("material.birch_fence_gate.hardness")
    public static float MATERIAL__BIRCH_FENCE_GATE__HARDNESS         = 2;
    @MagicNumber("material.birch_fence_gate.blastResistance")
    public static float MATERIAL__BIRCH_FENCE_GATE__BLAST_RESISTANCE = 15;

    // material/jungle fence gate
    @MagicNumber("material.jungle_fence_gate.hardness")
    public static float MATERIAL__JUNGLE_FENCE_GATE__HARDNESS         = 2;
    @MagicNumber("material.jungle_fence_gate.blastResistance")
    public static float MATERIAL__JUNGLE_FENCE_GATE__BLAST_RESISTANCE = 15;

    // material/dark oak fence gate
    @MagicNumber("material.dark_oak_fence_gate.hardness")
    public static float MATERIAL__DARK_OAK_FENCE_GATE__HARDNESS         = 2;
    @MagicNumber("material.dark_oak_fence_gate.blastResistance")
    public static float MATERIAL__DARK_OAK_FENCE_GATE__BLAST_RESISTANCE = 15;

    // material/acacia fence gate
    @MagicNumber("material.acacia_fence_gate.hardness")
    public static float MATERIAL__ACACIA_FENCE_GATE__HARDNESS         = 2;
    @MagicNumber("material.acacia_fence_gate.blastResistance")
    public static float MATERIAL__ACACIA_FENCE_GATE__BLAST_RESISTANCE = 15;

    // material/nether wart block
    @MagicNumber("material.nether_wart_block.hardness")
    public static float MATERIAL__NETHER_WART_BLOCK__HARDNESS         = 0;
    @MagicNumber("material.nether_wart_block.blastResistance")
    public static float MATERIAL__NETHER_WART_BLOCK__BLAST_RESISTANCE = 0;

    // material/enchanting table
    @MagicNumber("material.enchanting_table.hardness")
    public static float MATERIAL__ENCHANTING_TABLE__HARDNESS         = 5;
    @MagicNumber("material.enchanting_table.blastResistance")
    public static float MATERIAL__ENCHANTING_TABLE__BLAST_RESISTANCE = 6_000;

    // material/brewing stand block
    @MagicNumber("material.brewing_stand_block.hardness")
    public static float MATERIAL__BREWING_STAND_BLOCK__HARDNESS         = 0.5f;
    @MagicNumber("material.brewing_stand_block.blastResistance")
    public static float MATERIAL__BREWING_STAND_BLOCK__BLAST_RESISTANCE = 2.5f;

    // material/cauldron
    @MagicNumber("material.cauldron.hardness")
    public static float MATERIAL__CAULDRON__HARDNESS         = 2;
    @MagicNumber("material.cauldron.blastResistance")
    public static float MATERIAL__CAULDRON__BLAST_RESISTANCE = 10;

    // material/end portal
    @MagicNumber("material.end_portal.hardness")
    public static float MATERIAL__END_PORTAL__HARDNESS         = - 1;
    @MagicNumber("material.end_portal.blastResistance")
    public static float MATERIAL__END_PORTAL__BLAST_RESISTANCE = 18_000_000;

    // material/end portal frame
    @MagicNumber("material.end_portal_frame.hardness")
    public static float MATERIAL__END_PORTAL_FRAME__HARDNESS         = - 1;
    @MagicNumber("material.end_portal_frame.blastResistance")
    public static float MATERIAL__END_PORTAL_FRAME__BLAST_RESISTANCE = 18_000_000;

    // material/double wooden slab
    @MagicNumber("material.double_wooden_slab.hardness")
    public static float MATERIAL__DOUBLE_WOODEN_SLAB__HARDNESS         = 2;
    @MagicNumber("material.double_wooden_slab.blastResistance")
    public static float MATERIAL__DOUBLE_WOODEN_SLAB__BLAST_RESISTANCE = 30;

    // material/cocoa
    @MagicNumber("material.cocoa.hardness")
    public static float MATERIAL__COCOA__HARDNESS         = 0.2f;
    @MagicNumber("material.cocoa.blastResistance")
    public static float MATERIAL__COCOA__BLAST_RESISTANCE = 15;

    // material/ender chest
    @MagicNumber("material.ender_chest.hardness")
    public static float MATERIAL__ENDER_CHEST__HARDNESS         = 22.5f;
    @MagicNumber("material.ender_chest.blastResistance")
    public static float MATERIAL__ENDER_CHEST__BLAST_RESISTANCE = 3_000;

    // material/tripwire hook
    @MagicNumber("material.tripwire_hook.hardness")
    public static float MATERIAL__TRIPWIRE_HOOK__HARDNESS         = 0;
    @MagicNumber("material.tripwire_hook.blastResistance")
    public static float MATERIAL__TRIPWIRE_HOOK__BLAST_RESISTANCE = 0;

    // material/tripwire
    @MagicNumber("material.tripwire.hardness")
    public static float MATERIAL__TRIPWIRE__HARDNESS         = 0;
    @MagicNumber("material.tripwire.blastResistance")
    public static float MATERIAL__TRIPWIRE__BLAST_RESISTANCE = 0;

    // material/command block
    @MagicNumber("material.command_block.hardness")
    public static float MATERIAL__COMMAND_BLOCK__HARDNESS         = - 1;
    @MagicNumber("material.command_block.blastResistance")
    public static float MATERIAL__COMMAND_BLOCK__BLAST_RESISTANCE = 18_000_000;

    // material/cobblestone wall
    @MagicNumber("material.cobblestone_wall.hardness")
    public static float MATERIAL__COBBLESTONE_WALL__HARDNESS         = 2;
    @MagicNumber("material.cobblestone_wall.blastResistance")
    public static float MATERIAL__COBBLESTONE_WALL__BLAST_RESISTANCE = 30;

    // material/flower pot
    @MagicNumber("material.flower_pot.hardness")
    public static float MATERIAL__FLOWER_POT__HARDNESS         = 0;
    @MagicNumber("material.flower_pot.blastResistance")
    public static float MATERIAL__FLOWER_POT__BLAST_RESISTANCE = 0;

    // material/carrots block
    @MagicNumber("material.carrots_block.hardness")
    public static float MATERIAL__CARROTS_BLOCK__HARDNESS         = 0;
    @MagicNumber("material.carrots_block.blastResistance")
    public static float MATERIAL__CARROTS_BLOCK__BLAST_RESISTANCE = 0;

    // material/potatoes block
    @MagicNumber("material.potatoes_block.hardness")
    public static float MATERIAL__POTATOES_BLOCK__HARDNESS         = 0;
    @MagicNumber("material.potatoes_block.blastResistance")
    public static float MATERIAL__POTATOES_BLOCK__BLAST_RESISTANCE = 0;

    // material/skull block
    @MagicNumber("material.skull_block.hardness")
    public static float MATERIAL__SKULL_BLOCK__HARDNESS         = 1;
    @MagicNumber("material.skull_block.blastResistance")
    public static float MATERIAL__SKULL_BLOCK__BLAST_RESISTANCE = 5;

    // material/anvil
    @MagicNumber("material.anvil.hardness")
    public static float MATERIAL__ANVIL__HARDNESS         = 5;
    @MagicNumber("material.anvil.blastResistance")
    public static float MATERIAL__ANVIL__BLAST_RESISTANCE = 6_000;

    // material/trapped chest
    @MagicNumber("material.trapped_chest.hardness")
    public static float MATERIAL__TRAPPED_CHEST__HARDNESS         = 2.5f;
    @MagicNumber("material.trapped_chest.blastResistance")
    public static float MATERIAL__TRAPPED_CHEST__BLAST_RESISTANCE = 12.5f;

    // material/redstone comparator
    @MagicNumber("material.redstone_comparator.hardness")
    public static float MATERIAL__REDSTONE_COMPARATOR__HARDNESS         = 0;
    @MagicNumber("material.redstone_comparator.blastResistance")
    public static float MATERIAL__REDSTONE_COMPARATOR__BLAST_RESISTANCE = 0;

    // material/daylight detector
    @MagicNumber("material.daylight_detector.hardness")
    public static float MATERIAL__DAYLIGHT_DETECTOR__HARDNESS         = 0.2f;
    @MagicNumber("material.daylight_detector.blastResistance")
    public static float MATERIAL__DAYLIGHT_DETECTOR__BLAST_RESISTANCE = 1;

    // material/daylight detector inverted
    @MagicNumber("material.daylight_detector_inverted.hardness")
    public static float MATERIAL__DAYLIGHT_DETECTOR_INVERTED__HARDNESS         = 0.2f;
    @MagicNumber("material.daylight_detector_inverted.blastResistance")
    public static float MATERIAL__DAYLIGHT_DETECTOR_INVERTED__BLAST_RESISTANCE = 1;

    // material/hopper
    @MagicNumber("material.hopper.hardness")
    public static float MATERIAL__HOPPER__HARDNESS         = 3;
    @MagicNumber("material.hopper.blastResistance")
    public static float MATERIAL__HOPPER__BLAST_RESISTANCE = 15;

    // material/quartz block
    @MagicNumber("material.quartz_block.hardness")
    public static float MATERIAL__QUARTZ_BLOCK__HARDNESS         = 0.8f;
    @MagicNumber("material.quartz_block.blastResistance")
    public static float MATERIAL__QUARTZ_BLOCK__BLAST_RESISTANCE = 4;

    // material/activator rail
    @MagicNumber("material.activator_rail.hardness")
    public static float MATERIAL__ACTIVATOR_RAIL__HARDNESS         = 0.7f;
    @MagicNumber("material.activator_rail.blastResistance")
    public static float MATERIAL__ACTIVATOR_RAIL__BLAST_RESISTANCE = 3.5f;

    // material/dropper
    @MagicNumber("material.dropper.hardness")
    public static float MATERIAL__DROPPER__HARDNESS         = 3.5f;
    @MagicNumber("material.dropper.blastResistance")
    public static float MATERIAL__DROPPER__BLAST_RESISTANCE = 17.5f;

    // material/stained hardened clay
    @MagicNumber("material.stained_hardened_clay.hardness")
    public static float MATERIAL__STAINED_HARDENED_CLAY__HARDNESS         = 1.25f;
    @MagicNumber("material.stained_hardened_clay.blastResistance")
    public static float MATERIAL__STAINED_HARDENED_CLAY__BLAST_RESISTANCE = 30;

    // material/stained glass pane
    @MagicNumber("material.stained_glass_pane.hardness")
    public static float MATERIAL__STAINED_GLASS_PANE__HARDNESS         = 0.3f;
    @MagicNumber("material.stained_glass_pane.blastResistance")
    public static float MATERIAL__STAINED_GLASS_PANE__BLAST_RESISTANCE = 1.5f;

    // material/prismarine
    @MagicNumber("material.prismarine.hardness")
    public static float MATERIAL__PRISMARINE__HARDNESS         = 1.5f;
    @MagicNumber("material.prismarine.blastResistance")
    public static float MATERIAL__PRISMARINE__BLAST_RESISTANCE = 30;

    // material/hay block
    @MagicNumber("material.hay_block.hardness")
    public static float MATERIAL__HAY_BLOCK__HARDNESS         = 0.5f;
    @MagicNumber("material.hay_block.blastResistance")
    public static float MATERIAL__HAY_BLOCK__BLAST_RESISTANCE = 2.5f;

    // material/carpet
    @MagicNumber("material.carpet.hardness")
    public static float MATERIAL__CARPET__HARDNESS         = 0.1f;
    @MagicNumber("material.carpet.blastResistance")
    public static float MATERIAL__CARPET__BLAST_RESISTANCE = 0.5f;

    // material/double flowers
    @MagicNumber("material.double_flowers.hardness")
    public static float MATERIAL__DOUBLE_FLOWERS__HARDNESS         = 0;
    @MagicNumber("material.double_flowers.blastResistance")
    public static float MATERIAL__DOUBLE_FLOWERS__BLAST_RESISTANCE = 0;

    // material/standing banner
    @MagicNumber("material.standing_banner.hardness")
    public static float MATERIAL__STANDING_BANNER__HARDNESS         = 1;
    @MagicNumber("material.standing_banner.blastResistance")
    public static float MATERIAL__STANDING_BANNER__BLAST_RESISTANCE = 5;

    // material/wall banner
    @MagicNumber("material.wall_banner.hardness")
    public static float MATERIAL__WALL_BANNER__HARDNESS         = 1;
    @MagicNumber("material.wall_banner.blastResistance")
    public static float MATERIAL__WALL_BANNER__BLAST_RESISTANCE = 5;

    // material/red sandstone
    @MagicNumber("material.red_sandstone.hardness")
    public static float MATERIAL__RED_SANDSTONE__HARDNESS         = 0.8f;
    @MagicNumber("material.red_sandstone.blastResistance")
    public static float MATERIAL__RED_SANDSTONE__BLAST_RESISTANCE = 4;

    // material/wooden slabs
    @MagicNumber("material.wooden_slab.hardness")
    public static float MATERIAL__WOODEN_SLAB__HARDNESS         = 2;
    @MagicNumber("material.wooden_slab.blastResistance")
    public static float MATERIAL__WOODEN_SLAB__BLAST_RESISTANCE = 15;

    // material/stone slabs
    @MagicNumber("material.stone_slab.hardness")
    public static float MATERIAL__STONE_SLAB__HARDNESS         = 2;
    @MagicNumber("material.stone_slab.blastResistance")
    public static float MATERIAL__STONE_SLAB__BLAST_RESISTANCE = 30;

    // material/gold block
    @MagicNumber("material.gold_block.hardness")
    public static float MATERIAL__GOLD_BLOCK__HARDNESS         = 3;
    @MagicNumber("material.gold_block.blastResistance")
    public static float MATERIAL__GOLD_BLOCK__BLAST_RESISTANCE = 30;

    // material/iron block
    @MagicNumber("material.iron_block.hardness")
    public static float MATERIAL__IRON_BLOCK__HARDNESS         = 5;
    @MagicNumber("material.iron_block.blastResistance")
    public static float MATERIAL__IRON_BLOCK__BLAST_RESISTANCE = 30;

    // material/brown mushroom
    @MagicNumber("material.brown_mushroom.hardness")
    public static float MATERIAL__BROWN_MUSHROOM__HARDNESS         = 0;
    @MagicNumber("material.brown_mushroom.blastResistance")
    public static float MATERIAL__BROWN_MUSHROOM__BLAST_RESISTANCE = 0;

    // material/red mushroom
    @MagicNumber("material.red_mushroom.hardness")
    public static float MATERIAL__RED_MUSHROOM__HARDNESS         = 0;
    @MagicNumber("material.red_mushroom.blastResistance")
    public static float MATERIAL__RED_MUSHROOM__BLAST_RESISTANCE = 0;

    // material/dandelion
    @MagicNumber("material.dandelion.hardness")
    public static float MATERIAL__DANDELION__HARDNESS         = 0;
    @MagicNumber("material.dandelion.blastResistance")
    public static float MATERIAL__DANDELION__BLAST_RESISTANCE = 0;

    // material/flowers (red flower)
    @MagicNumber("material.flowers.hardness")
    public static float MATERIAL__FLOWERS__HARDNESS         = 0;
    @MagicNumber("material.flowers.blastResistance")
    public static float MATERIAL__FLOWERS__BLAST_RESISTANCE = 0;

    // material/wool
    @MagicNumber("material.wool.hardness")
    public static float MATERIAL__WOOL__HARDNESS         = 0.8f;
    @MagicNumber("material.wool.blastResistance")
    public static float MATERIAL__WOOL__BLAST_RESISTANCE = 4;

    // material/dead bush
    @MagicNumber("material.dead_bush.hardness")
    public static float MATERIAL__DEAD_BUSH__HARDNESS         = 0;
    @MagicNumber("material.dead_bush.blastResistance")
    public static float MATERIAL__DEAD_BUSH__BLAST_RESISTANCE = 0;

    // material/tall grass
    @MagicNumber("material.tall_grass.hardness")
    public static float MATERIAL__TALL_GRASS__HARDNESS         = 0;
    @MagicNumber("material.tall_grass.blastResistance")
    public static float MATERIAL__TALL_GRASS__BLAST_RESISTANCE = 0;

    // material/cobweb
    @MagicNumber("material.cobweb.hardness")
    public static float MATERIAL__COBWEB__HARDNESS         = 4f;
    @MagicNumber("material.cobweb.blastResistance")
    public static float MATERIAL__COBWEB__BLAST_RESISTANCE = 20f;

    // material/piston
    @MagicNumber("material.piston.hardness")
    public static float MATERIAL__PISTON__HARDNESS         = 0.5f;
    @MagicNumber("material.piston.blastResistance")
    public static float MATERIAL__PISTON__BLAST_RESISTANCE = 2.5f;

    // material/piston extension
    @MagicNumber("material.piston_extension.hardness")
    public static float MATERIAL__PISTON_EXTENSION__HARDNESS         = 0.5f;
    @MagicNumber("material.piston_extension.blastResistance")
    public static float MATERIAL__PISTON_EXTENSION__BLAST_RESISTANCE = 2.5f;

    // material/piston head
    @MagicNumber("material.piston_head.hardness")
    public static float MATERIAL__PISTON_HEAD__HARDNESS         = 0.5f;
    @MagicNumber("material.piston_head.blastResistance")
    public static float MATERIAL__PISTON_HEAD__BLAST_RESISTANCE = 2.5f;

    // material/sticky piston
    @MagicNumber("material.sticky_piston.hardness")
    public static float MATERIAL__STICKY_PISTON__HARDNESS         = 0.5f;
    @MagicNumber("material.sticky_piston.blastResistance")
    public static float MATERIAL__STICKY_PISTON__BLAST_RESISTANCE = 2.5f;

    // material/gold ore
    @MagicNumber("material.gold_ore.hardness")
    public static float MATERIAL__GOLD_ORE__HARDNESS         = 3;
    @MagicNumber("material.gold_ore.blastResistance")
    public static float MATERIAL__GOLD_ORE__BLAST_RESISTANCE = 15;

    // material/iron ore
    @MagicNumber("material.iron_ore.hardness")
    public static float MATERIAL__IRON_ORE__HARDNESS         = 3;
    @MagicNumber("material.iron_ore.blastResistance")
    public static float MATERIAL__IRON_ORE__BLAST_RESISTANCE = 15;

    // material/coal ore
    @MagicNumber("material.coal_ore.hardness")
    public static float MATERIAL__COAL_ORE__HARDNESS         = 3;
    @MagicNumber("material.coal_ore.blastResistance")
    public static float MATERIAL__COAL_ORE__BLAST_RESISTANCE = 15;

    // material/lapis ore
    @MagicNumber("material.lapis_ore.hardness")
    public static float MATERIAL__LAPIS_ORE__HARDNESS         = 3;
    @MagicNumber("material.lapis_ore.blastResistance")
    public static float MATERIAL__LAPIS_ORE__BLAST_RESISTANCE = 15;

    // material/lapis block
    @MagicNumber("material.lapis_block.hardness")
    public static float MATERIAL__LAPIS_BLOCK__HARDNESS         = 3;
    @MagicNumber("material.lapis_block.blastResistance")
    public static float MATERIAL__LAPIS_BLOCK__BLAST_RESISTANCE = 15;

    // material/cobblestone
    @MagicNumber("material.cobblestone.hardness")
    public static float MATERIAL__COBBLESTONE__HARDNESS         = 2;
    @MagicNumber("material.cobblestone.blastResistance")
    public static float MATERIAL__COBBLESTONE__BLAST_RESISTANCE = 30;

    // material/noteblock
    @MagicNumber("material.noteblock.hardness")
    public static float MATERIAL__NOTEBLOCK__HARDNESS         = 0.8f;
    @MagicNumber("material.noteblock.blastResistance")
    public static float MATERIAL__NOTEBLOCK__BLAST_RESISTANCE = 4;

    // material/dispenser
    @MagicNumber("material.dispenser.hardness")
    public static float MATERIAL__DISPENSER__HARDNESS         = 3.5f;
    @MagicNumber("material.dispenser.blastResistance")
    public static float MATERIAL__DISPENSER__BLAST_RESISTANCE = 17.5f;

    // material/bedrock
    @MagicNumber("material.bedrock.hardness")
    public static float MATERIAL__BEDROCK__HARDNESS         = - 1;
    @MagicNumber("material.bedrock.blastResistance")
    public static float MATERIAL__BEDROCK__BLAST_RESISTANCE = 18_000_000;

    // material/air
    @MagicNumber("material.air.hardness")
    public static float MATERIAL__AIR__HARDNESS         = 0;
    @MagicNumber("material.air.blastResistance")
    public static float MATERIAL__AIR__BLAST_RESISTANCE = 0;

    // material/water
    @MagicNumber("material.water.hardness")
    public static float MATERIAL__WATER__HARDNESS               = 100;
    @MagicNumber("material.water.blastResistance")
    public static float MATERIAL__WATER__BLAST_RESISTANCE       = 500;
    @MagicNumber("material.water.still.hardness")
    public static float MATERIAL__WATER_STILL__HARDNESS         = 100;
    @MagicNumber("material.water.still.blastResistance")
    public static float MATERIAL__WATER_STILL__BLAST_RESISTANCE = 500;

    // material/lava
    @MagicNumber("material.lava.hardness")
    public static float MATERIAL__LAVA__HARDNESS               = 100;
    @MagicNumber("material.lava.blastResistance")
    public static float MATERIAL__LAVA__BLAST_RESISTANCE       = 0;
    @MagicNumber("material.lava.still.hardness")
    public static float MATERIAL__LAVA_STILL__HARDNESS         = 100;
    @MagicNumber("material.lava.still.blastResistance")
    public static float MATERIAL__LAVA_STILL__BLAST_RESISTANCE = 500;

    /*
     * ==================
     * Attributes section
     * ==================
     */

    @MagicNumber("attributes.modifiers.sprint")
    public static double ATTRIBUTES__MODIFIERS__SPRINT = 0.30000001192092896;

    // attributes/generic.maxHealth
    @MagicNumber("attributes.types.generic.maxHealth.default")
    public static double ATTRIBUTES__GENERIC_MAX_HEALTH__DEFAULT = 20;
    @MagicNumber("attributes.types.generic.maxHealth.min")
    public static double ATTRIBUTES__GENERIC_MAX_HEALTH__MIN     = 0;
    @MagicNumber("attributes.types.generic.maxHealth.max")
    public static double ATTRIBUTES__GENERIC_MAX_HEALTH__MAX     = Integer.MAX_VALUE;

    // attributes/generic.followRange
    @MagicNumber("attributes.types.generic.followRange.default")
    public static double ATTRIBUTES__GENERIC_FOLLOW_RANGE__DEFAULT = 32;
    @MagicNumber("attributes.types.generic.followRange.min")
    public static double ATTRIBUTES__GENERIC_FOLLOW_RANGE__MIN     = 0;
    @MagicNumber("attributes.types.generic.followRange.max")
    public static double ATTRIBUTES__GENERIC_FOLLOW_RANGE__MAX     = 2048;

    // attributes/generic.knockbackResistance
    @MagicNumber("attributes.types.generic.knockbackResistance.default")
    public static double ATTRIBUTES__GENERIC_KNOCKBACK_RESISTANCE__DEFAULT = 0;
    @MagicNumber("attributes.types.generic.knockbackResistance.min")
    public static double ATTRIBUTES__GENERIC_KNOCKBACK_RESISTANCE__MIN     = 0;
    @MagicNumber("attributes.types.generic.knockbackResistance.max")
    public static double ATTRIBUTES__GENERIC_KNOCKBACK_RESISTANCE__MAX     = 1;

    // attributes/generic.movementSpeed
    @MagicNumber("attributes.types.generic.movementSpeed.default")
    public static double ATTRIBUTES__GENERIC_MOVEMENT_SPEED__DEFAULT = 0.699999988079071;
    @MagicNumber("attributes.types.generic.movementSpeed.min")
    public static double ATTRIBUTES__GENERIC_MOVEMENT_SPEED__MIN     = 0;
    @MagicNumber("attributes.types.generic.movementSpeed.max")
    public static double ATTRIBUTES__GENERIC_MOVEMENT_SPEED__MAX     = Double.MAX_VALUE;

    // attributes/generic.attackDamage
    @MagicNumber("attributes.types.generic.attackDamage.default")
    public static double ATTRIBUTES__GENERIC_ATTACK_DAMAGE__DEFAULT = 2;
    @MagicNumber("attributes.types.generic.attackDamage.min")
    public static double ATTRIBUTES__GENERIC_ATTACK_DAMAGE__MIN     = 0;
    @MagicNumber("attributes.types.generic.attackDamage.max")
    public static double ATTRIBUTES__GENERIC_ATTACK_DAMAGE__MAX     = Double.MAX_VALUE;

    // attributes/horse.jumpStrength
    @MagicNumber("attributes.types.horse.jumpStrength.default")
    public static double ATTRIBUTES__HORSE_JUMP_STRENGTH__DEFAULT = 0.7;
    @MagicNumber("attributes.types.horse.jumpStrength.min")
    public static double ATTRIBUTES__HORSE_JUMP_STRENGTH__MIN     = 0;
    @MagicNumber("attributes.types.horse.jumpStrength.max")
    public static double ATTRIBUTES__HORSE_JUMP_STRENGTH__MAX     = 2;

    // attributes/zombie.spawnReinforcements
    @MagicNumber("attributes.types.zombie.spawnReinforcements.default")
    public static double ATTRIBUTES__ZOMBIE_SPAWN_REINFORCMENTS__DEFAULT = 0;
    @MagicNumber("attributes.types.zombie.spawnReinforcements.min")
    public static double ATTRIBUTES__ZOMBIE_SPAWN_REINFORCMENTS__MIN     = 0;
    @MagicNumber("attributes.types.zombie.spawnReinforcements.max")
    public static double ATTRIBUTES__ZOMBIE_SPAWN_REINFORCMENTS__MAX     = 1;


    private MagicNumbers()
    {
    }
}
