package org.diorite.material;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.Air;
import org.diorite.material.blocks.cold.Ice;
import org.diorite.material.blocks.cold.PackedIce;
import org.diorite.material.blocks.cold.SnowBlock;
import org.diorite.material.blocks.cold.SnowLayer;
import org.diorite.material.blocks.earth.ClayBlock;
import org.diorite.material.blocks.earth.Dirt;
import org.diorite.material.blocks.earth.Farmland;
import org.diorite.material.blocks.earth.Grass;
import org.diorite.material.blocks.earth.Mycelium;
import org.diorite.material.blocks.end.EndPortal;
import org.diorite.material.blocks.end.EndPortalFrame;
import org.diorite.material.blocks.end.EndStone;
import org.diorite.material.blocks.liquid.Lava;
import org.diorite.material.blocks.liquid.Water;
import org.diorite.material.blocks.loose.Gravel;
import org.diorite.material.blocks.loose.Sand;
import org.diorite.material.blocks.nether.Glowstone;
import org.diorite.material.blocks.nether.NetherBrick;
import org.diorite.material.blocks.nether.NetherBrickFence;
import org.diorite.material.blocks.nether.NetherBrickStairs;
import org.diorite.material.blocks.nether.NetherPortal;
import org.diorite.material.blocks.nether.Netherrack;
import org.diorite.material.blocks.nether.SoulSand;
import org.diorite.material.blocks.others.*;
import org.diorite.material.blocks.plants.*;
import org.diorite.material.blocks.rails.ActivatorRail;
import org.diorite.material.blocks.rails.DetectorRail;
import org.diorite.material.blocks.rails.PoweredRail;
import org.diorite.material.blocks.rails.Rail;
import org.diorite.material.blocks.redstone.*;
import org.diorite.material.blocks.redstone.piston.Piston;
import org.diorite.material.blocks.redstone.piston.PistonExtension;
import org.diorite.material.blocks.redstone.piston.PistonHead;
import org.diorite.material.blocks.redstone.piston.PistonSticky;
import org.diorite.material.blocks.stony.*;
import org.diorite.material.blocks.stony.ore.CoalOre;
import org.diorite.material.blocks.stony.ore.DiamondOre;
import org.diorite.material.blocks.stony.ore.EmeraldOre;
import org.diorite.material.blocks.stony.ore.GoldOre;
import org.diorite.material.blocks.stony.ore.IronOre;
import org.diorite.material.blocks.stony.ore.LapisOre;
import org.diorite.material.blocks.stony.ore.QuartzOre;
import org.diorite.material.blocks.stony.ore.RedstoneOre;
import org.diorite.material.blocks.stony.ore.RedstoneOreGlowing;
import org.diorite.material.blocks.stony.oreblocks.CoalBlock;
import org.diorite.material.blocks.stony.oreblocks.DiamondBlock;
import org.diorite.material.blocks.stony.oreblocks.EmeraldBlock;
import org.diorite.material.blocks.stony.oreblocks.GoldBlock;
import org.diorite.material.blocks.stony.oreblocks.IronBlock;
import org.diorite.material.blocks.stony.oreblocks.LapisBlock;
import org.diorite.material.blocks.stony.oreblocks.QuartzBlock;
import org.diorite.material.blocks.stony.oreblocks.RedstoneBlock;
import org.diorite.material.blocks.tools.*;
import org.diorite.material.blocks.wooden.Bookshelf;
import org.diorite.material.blocks.wooden.NoteBlock;
import org.diorite.material.blocks.wooden.wood.Leaves;
import org.diorite.material.blocks.wooden.wood.Log;
import org.diorite.material.blocks.wooden.wood.Planks;
import org.diorite.material.blocks.wooden.wood.Sapling;
import org.diorite.material.blocks.wooden.wood.door.AcaciaDoor;
import org.diorite.material.blocks.wooden.wood.door.BirchDoor;
import org.diorite.material.blocks.wooden.wood.door.DarkOakDoor;
import org.diorite.material.blocks.wooden.wood.door.JungleDoor;
import org.diorite.material.blocks.wooden.wood.door.OakDoor;
import org.diorite.material.blocks.wooden.wood.door.SpruceDoor;
import org.diorite.material.blocks.wooden.wood.fence.AcaciaFence;
import org.diorite.material.blocks.wooden.wood.fence.BirchFence;
import org.diorite.material.blocks.wooden.wood.fence.DarkOakFence;
import org.diorite.material.blocks.wooden.wood.fence.JungleFence;
import org.diorite.material.blocks.wooden.wood.fence.OakFence;
import org.diorite.material.blocks.wooden.wood.fence.SpruceFence;
import org.diorite.material.blocks.wooden.wood.fencegate.AcaciaFenceGate;
import org.diorite.material.blocks.wooden.wood.fencegate.BirchFenceGate;
import org.diorite.material.blocks.wooden.wood.fencegate.DarkOakFenceGate;
import org.diorite.material.blocks.wooden.wood.fencegate.JungleFenceGate;
import org.diorite.material.blocks.wooden.wood.fencegate.OakFenceGate;
import org.diorite.material.blocks.wooden.wood.fencegate.SpruceFenceGate;
import org.diorite.material.blocks.wooden.wood.stairs.AcaciaStairs;
import org.diorite.material.blocks.wooden.wood.stairs.BirchStairs;
import org.diorite.material.blocks.wooden.wood.stairs.DarkOakStairs;
import org.diorite.material.blocks.wooden.wood.stairs.JungleStairs;
import org.diorite.material.blocks.wooden.wood.stairs.OakStairs;
import org.diorite.material.blocks.wooden.wood.stairs.SpruceStairs;
import org.diorite.utils.SimpleEnum;
import org.diorite.utils.collections.SimpleStringHashMap;
import org.diorite.utils.math.IntRange;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public abstract class Material implements SimpleEnum<Material>
{
    public static final int MATERIALS_SIZE = 385;

    public static final  Air                      AIR                        = Air.AIR;
    public static final  Stone                    STONE                      = Stone.STONE;
    public static final  Grass                    GRASS                      = Grass.GRASS;
    public static final  Dirt                     DIRT                       = Dirt.DIRT;
    public static final  Cobblestone              COBBLESTONE                = Cobblestone.COBBLESTONE;
    public static final  Planks                   PLANKS                     = Planks.PLANKS_OAK;
    public static final  Sapling                  SAPLING                    = Sapling.SAPLING_OAK;
    public static final  Bedrock                  BEDROCK                    = Bedrock.BEDROCK;
    public static final  Water                    WATER                      = Water.WATER_SOURCE;
    public static final  Water                    WATER_STILL                = Water.WATER_SOURCE_STILL;
    public static final  Lava                     LAVA                       = Lava.LAVA_SOURCE;
    public static final  Lava                     LAVA_STILL                 = Lava.LAVA_SOURCE_STILL;
    public static final  Sand                     SAND                       = Sand.SAND;
    public static final  Gravel                   GRAVEL                     = Gravel.GRAVEL;
    public static final  GoldOre                  GOLD_ORE                   = GoldOre.GOLD_ORE;
    public static final  IronOre                  IRON_ORE                   = IronOre.IRON_ORE;
    public static final  CoalOre                  COAL_ORE                   = CoalOre.COAL_ORE;
    public static final  Log                      LOG                        = Log.LOG_OAK;
    public static final  Leaves                   LEAVES                     = Leaves.LEAVES_OAK;
    public static final  Sponge                   SPONGE                     = Sponge.SPONGE;
    public static final  Glass                    GLASS                      = Glass.GLASS;
    public static final  LapisOre                 LAPIS_ORE                  = LapisOre.LAPIS_ORE;
    public static final  LapisBlock               LAPIS_BLOCK                = LapisBlock.LAPIS_BLOCK;
    public static final  Dispenser                DISPENSER                  = Dispenser.DISPENSER_DOWN;
    public static final  Sandstone                SANDSTONE                  = Sandstone.SANDSTONE;
    public static final  NoteBlock                NOTEBLOCK                  = NoteBlock.NOTEBLOCK;
    public static final  BedBlock                 BED_BLOCK                  = BedBlock.BED_FOOT_SOUTH;
    public static final  PoweredRail              POWERED_RAIL               = PoweredRail.POWERED_RAIL_NORTH_SOUTH;
    public static final  DetectorRail             DETECTOR_RAIL              = DetectorRail.DETECTOR_RAIL_NORTH_SOUTH;
    public static final  PistonSticky             PISTON_STICKY              = PistonSticky.STICKY_PISTON_DOWN;
    public static final  Cobweb                   WEB                        = Cobweb.COBWEB;
    public static final  TallGrass                TALL_GRASS                 = TallGrass.TALL_GRASS_SHRUB;
    public static final  DeadBush                 DEAD_BUSH                  = DeadBush.DEAD_BUSH;
    public static final  Piston                   PISTON                     = Piston.PISTON_DOWN;
    public static final  PistonExtension          PISTON_EXTENSION           = PistonExtension.PISTON_EXTENSION_DOWN;
    public static final  Wool                     WOOL                       = Wool.WOOL_WHITE;
    public static final  PistonHead               PISTON_HEAD                = PistonHead.PISTON_HEAD_DOWN;
    public static final  Dandelion                DANDELION                  = Dandelion.DANDELION;
    public static final  Flowers                  FLOWERS                    = Flowers.FLOWERS_POPPY;
    public static final  MushroomBrown            BROWN_MUSHROOM             = MushroomBrown.BROWN_MUSHROOM;
    public static final  MushroomRed              RED_MUSHROOM               = MushroomRed.RED_MUSHROOM;
    public static final  GoldBlock                GOLD_BLOCK                 = GoldBlock.GOLD_BLOCK;
    public static final  IronBlock                IRON_BLOCK                 = IronBlock.IRON_BLOCK;
    public static final  DoubleStoneSlab          DOUBLE_STONE_SLAB          = DoubleStoneSlab.DOUBLE_STONE_SLAB_STONE;
    public static final  StoneSlab                STONE_SLAB                 = StoneSlab.STONE_SLAB_STONE;
    public static final  BrickBlock               BRICK_BLOCK                = BrickBlock.BRICK_BLOCK;
    public static final  Tnt                      TNT                        = Tnt.TNT;
    public static final  Bookshelf                BOOKSHELF                  = Bookshelf.BOOKSHELF;
    public static final  MossyCobblestone         MOSSY_COBBLESTONE          = MossyCobblestone.MOSSY_COBBLESTONE;
    public static final  Obsidian                 OBSIDIAN                   = Obsidian.OBSIDIAN;
    public static final  Torch                    TORCH                      = Torch.TORCH_EAST;
    public static final  Fire                     FIRE                       = Fire.FIRE_0;
    public static final  MobSpawner               MOB_SPAWNER                = MobSpawner.MOB_SPAWNER;
    public static final  OakStairs                OAK_STAIRS                 = OakStairs.OAK_STAIRS; // TODO: edit after editing auto-generated class
    public static final  Chest                    CHEST                      = Chest.CHEST; // TODO: edit after editing auto-generated class
    public static final  RedstoneWire             REDSTONE_WIRE              = RedstoneWire.REDSTONE_WIRE_OFF;
    public static final  DiamondOre               DIAMOND_ORE                = DiamondOre.DIAMOND_ORE;
    public static final  DiamondBlock             DIAMOND_BLOCK              = DiamondBlock.DIAMOND_BLOCK;
    public static final  CraftingTable            CRAFTING_TABLE             = CraftingTable.CRAFTING_TABLE;
    public static final  WheatBlock               WHEAT_BLOCK                = WheatBlock.WHEAT_BLOCK; // TODO: edit after editing auto-generated class
    public static final  Farmland                 FARMLAND                   = Farmland.FARMLAND_UNHYDRATED;
    public static final  Furnace                  FURNACE                    = Furnace.FURNACE; // TODO: edit after editing auto-generated class
    public static final  BurningFurnace           BURNING_FURNACE            = BurningFurnace.BURNING_FURNACE; // TODO: edit after editing auto-generated class
    public static final  StandingSign             STANDING_SIGN              = StandingSign.STANDING_SIGN_SOUTH;
    public static final  OakDoor                  OAK_DOOR                   = OakDoor.OAK_DOOR_BOTTOM_EAST;
    public static final  Ladder                   LADDER                     = Ladder.LADDER_NORTH;
    public static final  Rail                     RAIL                       = Rail.RAIL; // TODO: edit after editing auto-generated class
    public static final  CobblestoneStairs        COBBLESTONE_STAIRS         = CobblestoneStairs.COBBLESTONE_STAIRS; // TODO: edit after editing auto-generated class
    public static final  WallSign                 WALL_SIGN                  = WallSign.WALL_SIGN_NORTH;
    public static final  Lever                    LEVER                      = Lever.LEVER; // TODO: edit after editing auto-generated class
    public static final  StonePressurePlate       STONE_PRESSURE_PLATE       = StonePressurePlate.STONE_PRESSURE_PLATE; // TODO: edit after editing auto-generated class
    public static final  IronDoor                 IRON_DOOR                  = IronDoor.IRON_DOOR_BOTTOM_EAST;
    public static final  WoodenPressurePlate      WOODEN_PRESSURE_PLATE      = WoodenPressurePlate.WOODEN_PRESSURE_PLATE; // TODO: edit after editing auto-generated class
    public static final  RedstoneOre              REDSTONE_ORE               = RedstoneOre.REDSTONE_ORE;
    public static final  RedstoneOreGlowing       REDSTONE_ORE_GLOWING       = RedstoneOreGlowing.REDSTONE_ORE_GLOWING;
    public static final  RedstoneTorchOff         REDSTONE_TORCH_OFF         = RedstoneTorchOff.REDSTONE_TORCH_OFF; // TODO: edit after editing auto-generated class
    public static final  RedstoneTorchOn          REDSTONE_TORCH_ON          = RedstoneTorchOn.REDSTONE_TORCH_ON; // TODO: edit after editing auto-generated class
    public static final  StoneButton              STONE_BUTTON               = StoneButton.STONE_BUTTON; // TODO: edit after editing auto-generated class
    public static final  SnowLayer                SNOW_LAYER                 = SnowLayer.SNOW_LAYER_1;
    public static final  Ice                      ICE                        = Ice.ICE;
    public static final  SnowBlock                SNOW_BLOCK                 = SnowBlock.SNOW_BLOCK;
    public static final  Cactus                   CACTUS                     = Cactus.CACTUS; // TODO: edit after editing auto-generated class
    public static final  ClayBlock                CLAY_BLOCK                 = ClayBlock.CLAY_BLOCK;
    public static final  SugarCane                SUGAR_CANE                 = SugarCane.SUGAR_CANE; // TODO: edit after editing auto-generated class
    public static final  Jukebox                  JUKEBOX                    = Jukebox.JUKEBOX;
    public static final  OakFence                 OAK_FENCE                  = OakFence.OAK_FENCE;
    public static final  Pumpkin                  PUMPKIN                    = Pumpkin.PUMPKIN; // TODO: edit after editing auto-generated class
    public static final  Netherrack               NETHERRACK                 = Netherrack.NETHERRACK;
    public static final  SoulSand                 SOUL_SAND                  = SoulSand.SOUL_SAND;
    public static final  Glowstone                GLOWSTONE                  = Glowstone.GLOWSTONE;
    public static final  NetherPortal             NETHER_PORTAL              = NetherPortal.NETHER_PORTAL_EAST_WEST;
    public static final  PumpkinLantern           PUMPKIN_LANTERN            = PumpkinLantern.PUMPKIN_LANTERN; // TODO: edit after editing auto-generated class
    public static final  Cake                     CAKE                       = Cake.CAKE_0;
    public static final  RedstoneRepeaterOff      REPEATER_OFF               = RedstoneRepeaterOff.REDSTONE_REPEATER_OFF; // TODO: edit after editing auto-generated class
    public static final  RedstoneRepeaterOn       REPEATER_ON                = RedstoneRepeaterOn.REDSTONE_REPEATER_ON; // TODO: edit after editing auto-generated class
    public static final  StainedGlass             STAINED_GLASS              = StainedGlass.STAINED_GLASS_WHITE;
    public static final  WoodenTrapdoor           WOODEN_TRAPDOOR            = WoodenTrapdoor.WOODEN_TRAPDOOR_WEST_BOTTOM;
    public static final  MonsterEggTrap           MONSTER_EGG_TRAP           = MonsterEggTrap.MONSTER_EGG_TRAP_STONE;
    public static final  StoneBrick               STONE_BRICK                = StoneBrick.STONE_BRICK;
    public static final  BrownMushroomBlock       BROWN_MUSHROOM_BLOCK       = BrownMushroomBlock.BROWN_MUSHROOM_BLOCK_PORES_FULL;
    public static final  RedMushroomBlock         RED_MUSHROOM_BLOCK         = RedMushroomBlock.RED_MUSHROOM_BLOCK_PORES_FULL;
    public static final  IronBars                 IRON_BARS                  = IronBars.IRON_BARS;
    public static final  GlassPane                GLASS_PANE                 = GlassPane.GLASS_PANE;
    public static final  MelonBlock               MELON_BLOCK                = MelonBlock.MELON_BLOCK;
    public static final  PumpkinStem              PUMPKIN_STEM               = PumpkinStem.PUMPKIN_STEM; // TODO: edit after editing auto-generated class
    public static final  MelonStem                MELON_STEM                 = MelonStem.MELON_STEM; // TODO: edit after editing auto-generated class
    public static final  Vine                     VINE                       = Vine.VINE; // TODO: edit after editing auto-generated class
    public static final  OakFenceGate             OAK_FENCE_GATE             = OakFenceGate.OAK_FENCE_GATE; // TODO: edit after editing auto-generated class
    public static final  BrickStairs              BRICK_STAIRS               = BrickStairs.BRICK_STAIRS; // TODO: edit after editing auto-generated class
    public static final  StoneBrickStairs         STONE_BRICK_STAIRS         = StoneBrickStairs.STONE_BRICK_STAIRS; // TODO: edit after editing auto-generated class
    public static final  Mycelium                 MYCELIUM                   = Mycelium.MYCELIUM;
    public static final  WaterLily                WATER_LILY                 = WaterLily.WATER_LILY;
    public static final  NetherBrick              NETHER_BRICK               = NetherBrick.NETHER_BRICK;
    public static final  NetherBrickFence         NETHER_BRICK_FENCE         = NetherBrickFence.NETHER_BRICK_FENCE;
    public static final  NetherBrickStairs        NETHER_BRICK_STAIRS        = NetherBrickStairs.NETHER_BRICK_STAIRS_EAST;
    public static final  NetherWartBlock          NETHER_WART_BLOCK          = NetherWartBlock.NETHER_WART_BLOCK; // TODO: edit after editing auto-generated class
    public static final  EnchantingTable          ENCHANTING_TABLE           = EnchantingTable.ENCHANTING_TABLE; // TODO: edit after editing auto-generated class
    public static final  BrewingStandBlock        BREWING_STAND_BLOCK        = BrewingStandBlock.BREWING_STAND_BLOCK; // TODO: edit after editing auto-generated class
    public static final  Cauldron                 CAULDRON                   = Cauldron.CAULDRON; // TODO: edit after editing auto-generated class
    public static final  EndPortal                END_PORTAL                 = EndPortal.END_PORTAL;
    public static final  EndPortalFrame           END_PORTAL_FRAME           = EndPortalFrame.END_PORTAL_FRAME_SOUTH;
    public static final  EndStone                 END_STONE                  = EndStone.END_STONE;
    public static final  DragonEgg                DRAGON_EGG                 = DragonEgg.DRAGON_EGG;
    public static final  RedstoneLampOff          REDSTONE_LAMP_OFF          = RedstoneLampOff.REDSTONE_LAMP_OFF;
    public static final  RedstoneLampOn           REDSTONE_LAMP_ON           = RedstoneLampOn.REDSTONE_LAMP_ON;
    //    public static final  DoubleWoodenSlab         DOUBLE_WOODEN_SLAB         = DoubleWoodenSlab.DOUBLE_WOODEN_SLAB; // TODO: edit after editing auto-generated class
//    public static final  WoodenSlab               WOODEN_SLAB                = WoodenSlab.WOODEN_SLAB; // TODO: edit after editing auto-generated class
    public static final  Cocoa                    COCOA                      = Cocoa.COCOA; // TODO: edit after editing auto-generated class
    public static final  SandstoneStairs          SANDSTONE_STAIRS           = SandstoneStairs.SANDSTONE_STAIRS; // TODO: edit after editing auto-generated class
    public static final  EmeraldOre               EMERALD_ORE                = EmeraldOre.EMERALD_ORE;
    public static final  EnderChest               ENDER_CHEST                = EnderChest.ENDER_CHEST; // TODO: edit after editing auto-generated class
    public static final  TripwireHook             TRIPWIRE_HOOK              = TripwireHook.TRIPWIRE_HOOK; // TODO: edit after editing auto-generated class
    public static final  Tripwire                 TRIPWIRE                   = Tripwire.TRIPWIRE; // TODO: edit after editing auto-generated class
    public static final  EmeraldBlock             EMERALD_BLOCK              = EmeraldBlock.EMERALD_BLOCK;
    public static final  SpruceStairs             SPRUCE_STAIRS              = SpruceStairs.SPRUCE_STAIRS; // TODO: edit after editing auto-generated class
    public static final  BirchStairs              BIRCH_STAIRS               = BirchStairs.BIRCH_STAIRS; // TODO: edit after editing auto-generated class
    public static final  JungleStairs             JUNGLE_STAIRS              = JungleStairs.JUNGLE_STAIRS; // TODO: edit after editing auto-generated class
    public static final  CommandBlock             COMMAND_BLOCK              = CommandBlock.COMMAND_BLOCK; // TODO: edit after editing auto-generated class
    public static final  Beacon                   BEACON                     = Beacon.BEACON;
    public static final  CobblestoneWall          COBBLESTONE_WALL           = CobblestoneWall.COBBLESTONE_WALL; // TODO: edit after editing auto-generated class
    public static final  FlowerPot                FLOWER_POT                 = FlowerPot.FLOWER_POT_EMPTY;
    public static final  CarrotsBlock             CARROTS_BLOCK              = CarrotsBlock.CARROTS_BLOCK; // TODO: edit after editing auto-generated class
    public static final  PotatoesBlock            POTATOES_BLOCK             = PotatoesBlock.POTATOES_BLOCK; // TODO: edit after editing auto-generated class
    public static final  WoodenButton             WOODEN_BUTTON              = WoodenButton.WOODEN_BUTTON; // TODO: edit after editing auto-generated class
    public static final  SkullBlock               SKULL_BLOCK                = SkullBlock.SKULL_BLOCK; // TODO: edit after editing auto-generated class
    public static final  Anvil                    ANVIL                      = Anvil.ANVIL; // TODO: edit after editing auto-generated class
    public static final  TrappedChest             TRAPPED_CHEST              = TrappedChest.TRAPPED_CHEST; // TODO: edit after editing auto-generated class
    public static final  GoldenPressurePlate      GOLDEN_PRESSURE_PLATE      = GoldenPressurePlate.GOLDEN_PRESSURE_PLATE; // TODO: edit after editing auto-generated class
    public static final  IronPressurePlate        IRON_PRESSURE_PLATE        = IronPressurePlate.IRON_PRESSURE_PLATE; // TODO: edit after editing auto-generated class
    public static final  RedstoneComparator       REDSTONE_COMPARATOR        = RedstoneComparator.REDSTONE_COMPARATOR; // TODO: edit after editing auto-generated class
    public static final  DaylightDetector         DAYLIGHT_DETECTOR          = DaylightDetector.DAYLIGHT_DETECTOR; // TODO: edit after editing auto-generated class
    public static final  RedstoneBlock            REDSTONE_BLOCK             = RedstoneBlock.REDSTONE_BLOCK;
    public static final  QuartzOre                QUARTZ_ORE                 = QuartzOre.QUARTZ_ORE;
    public static final  Hopper                   HOPPER                     = Hopper.HOPPER; // TODO: edit after editing auto-generated class
    public static final  QuartzBlock              QUARTZ_BLOCK               = QuartzBlock.QUARTZ_BLOCK; // TODO: edit after editing auto-generated class
    public static final  QuartzStairs             QUARTZ_STAIRS              = QuartzStairs.QUARTZ_STAIRS; // TODO: edit after editing auto-generated class
    public static final  ActivatorRail            ACTIVATOR_RAIL             = ActivatorRail.ACTIVATOR_RAIL_NORTH_SOUTH; // TODO: edit after editing auto-generated class
    public static final  Dropper                  DROPPER                    = Dropper.DROPPER; // TODO: edit after editing auto-generated class
    public static final  StainedHardenedClay      STAINED_HARDENED_CLAY      = StainedHardenedClay.STAINED_HARDENED_CLAY_WHITE;
    public static final  StainedGlassPane         STAINED_GLASS_PANE         = StainedGlassPane.STAINED_GLASS_PANE_WHITE;
    public static final  AcaciaStairs             ACACIA_STAIRS              = AcaciaStairs.ACACIA_STAIRS; // TODO: edit after editing auto-generated class
    public static final  DarkOakStairs            DARK_OAK_STAIRS            = DarkOakStairs.DARK_OAK_STAIRS; // TODO: edit after editing auto-generated class
    public static final  SlimeBlock               SLIME_BLOCK                = SlimeBlock.SLIME_BLOCK;
    public static final  Barrier                  BARRIER                    = Barrier.BARRIER;
    public static final  IronTrapdoor             IRON_TRAPDOOR              = IronTrapdoor.IRON_TRAPDOOR_WEST_BOTTOM;
    public static final  Prismarine               PRISMARINE                 = Prismarine.PRISMARINE;
    public static final  SeaLantren               SEA_LANTERN                = SeaLantren.SEA_LANTREN;
    public static final  HayBlock                 HAY_BLOCK                  = HayBlock.HAY_BLOCK_UP_DOWN;
    public static final  Carpet                   CARPET                     = Carpet.CARPET_WHITE;
    public static final  HardenedClay             HARDENED_CLAY              = HardenedClay.HARDENED_CLAY;
    public static final  CoalBlock                COAL_BLOCK                 = CoalBlock.COAL_BLOCK;
    public static final  PackedIce                PACKED_ICE                 = PackedIce.PACKED_ICE;
    public static final  DoubleFlowers            DOUBLE_FLOWERS             = DoubleFlowers.DOUBLE_FLOWERS; // TODO: edit after editing auto-generated class
    public static final  StandingBanner           STANDING_BANNER            = StandingBanner.STANDING_BANNER_SOUTH;
    public static final  WallBanner               WALL_BANNER                = WallBanner.WALL_BANNER_NORTH;
    public static final  DaylightDetectorInverted DAYLIGHT_DETECTOR_INVERTED = DaylightDetectorInverted.DAYLIGHT_DETECTOR_INVERTED; // TODO: edit after editing auto-generated class
    public static final  RedSandstone             RED_SANDSTONE              = RedSandstone.RED_SANDSTONE; // TODO: edit after editing auto-generated class
    public static final  RedSandstoneStairs       RED_SANDSTONE_STAIRS       = RedSandstoneStairs.RED_SANDSTONE_STAIRS; // TODO: edit after editing auto-generated class
    public static final  SpruceFenceGate          SPRUCE_FENCE_GATE          = SpruceFenceGate.SPRUCE_FENCE_GATE; // TODO: edit after editing auto-generated class
    public static final  BirchFenceGate           BIRCH_FENCE_GATE           = BirchFenceGate.BIRCH_FENCE_GATE; // TODO: edit after editing auto-generated class
    public static final  JungleFenceGate          JUNGLE_FENCE_GATE          = JungleFenceGate.JUNGLE_FENCE_GATE; // TODO: edit after editing auto-generated class
    public static final  DarkOakFenceGate         DARK_OAK_FENCE_GATE        = DarkOakFenceGate.DARK_OAK_FENCE_GATE; // TODO: edit after editing auto-generated class
    public static final  AcaciaFenceGate          ACACIA_FENCE_GATE          = AcaciaFenceGate.ACACIA_FENCE_GATE; // TODO: edit after editing auto-generated class
    public static final  SpruceFence              SPRUCE_FENCE               = SpruceFence.SPRUCE_FENCE;
    public static final  BirchFence               BIRCH_FENCE                = BirchFence.BRICH_FENCE;
    public static final  JungleFence              JUNGLE_FENCE               = JungleFence.JUNGLE_FENCE;
    public static final  DarkOakFence             DARK_OAK_FENCE             = DarkOakFence.DARK_OAK_FENCE;
    public static final  AcaciaFence              ACACIA_FENCE               = AcaciaFence.ACACIA_FENCE;
    public static final  SpruceDoor               SPRUCE_DOOR                = SpruceDoor.SPRUCE_DOOR_BOTTOM_EAST;
    public static final  BirchDoor                BIRCH_DOOR                 = BirchDoor.BIRCH_DOOR_BOTTOM_EAST;
    public static final  JungleDoor               JUNGLE_DOOR                = JungleDoor.JUNGLE_DOOR_BOTTOM_EAST;
    public static final  AcaciaDoor               ACACIA_DOOR                = AcaciaDoor.ACACIA_DOOR_BOTTOM_EAST;
    public static final  DarkOakDoor              DARK_OAK_DOOR              = DarkOakDoor.DARK_OAK_DOOR_BOTTOM_EAST;
    // ----- Item Separator -----
    //    public static final  Material                IRON_SPADE           = new Material("IRON_SPADE", 256, 1, 250);
//    public static final  Material                IRON_PICKAXE         = new Material("IRON_PICKAXE", 257, 1, 250);
//    public static final  Material                IRON_AXE             = new Material("IRON_AXE", 258, 1, 250);
//    public static final  Material                FLINT_AND_STEEL      = new Material("FLINT_AND_STEEL", 259, 1, 64);
//    public static final  Material                APPLE                = new Material("APPLE", 260);
//    public static final  Material                BOW                  = new Material("BOW", 261, 1, 384);
//    public static final  Material                ARROW                = new Material("ARROW", 262);
//    public static final  Material                COAL                 = new Material("COAL", 263);
//    public static final  Material                DIAMOND              = new Material("DIAMOND", 264);
//    public static final  Material                IRON_INGOT           = new Material("IRON_INGOT", 265);
//    public static final  Material                GOLD_INGOT           = new Material("GOLD_INGOT", 266);
//    public static final  Material                IRON_SWORD           = new Material("IRON_SWORD", 267, 1, 250);
//    public static final  Material                WOOD_SWORD           = new Material("WOOD_SWORD", 268, 1, 59);
//    public static final  Material                WOOD_SPADE           = new Material("WOOD_SPADE", 269, 1, 59);
//    public static final  Material                WOOD_PICKAXE         = new Material("WOOD_PICKAXE", 270, 1, 59);
//    public static final  Material                WOOD_AXE             = new Material("WOOD_AXE", 271, 1, 59);
//    public static final  Material                STONE_SWORD          = new Material("STONE_SWORD", 272, 1, 131);
//    public static final  Material                STONE_SPADE          = new Material("STONE_SPADE", 273, 1, 131);
//    public static final  Material                STONE_PICKAXE        = new Material("STONE_PICKAXE", 274, 1, 131);
//    public static final  Material                STONE_AXE            = new Material("STONE_AXE", 275, 1, 131);
//    public static final  Material                DIAMOND_SWORD        = new Material("DIAMOND_SWORD", 276, 1, 1561);
//    public static final  Material                DIAMOND_SPADE        = new Material("DIAMOND_SPADE", 277, 1, 1561);
//    public static final  Material                DIAMOND_PICKAXE      = new Material("DIAMOND_PICKAXE", 278, 1, 1561);
//    public static final  Material                DIAMOND_AXE          = new Material("DIAMOND_AXE", 279, 1, 1561);
//    public static final  Material                STICK                = new Material("STICK", 280);
//    public static final  Material                BOWL                 = new Material("BOWL", 281);
//    public static final  Material                MUSHROOM_SOUP        = new Material("MUSHROOM_SOUP", 282, 1);
//    public static final  Material                GOLD_SWORD           = new Material("GOLD_SWORD", 283, 1, 32);
//    public static final  Material                GOLD_SPADE           = new Material("GOLD_SPADE", 284, 1, 32);
//    public static final  Material                GOLD_PICKAXE         = new Material("GOLD_PICKAXE", 285, 1, 32);
//    public static final  Material                GOLD_AXE             = new Material("GOLD_AXE", 286, 1, 32);
//    public static final  Material                STRING               = new Material("STRING", 287);
//    public static final  Material                FEATHER              = new Material("FEATHER", 288);
//    public static final  Material                SULPHUR              = new Material("SULPHUR", 289);
//    public static final  Material                WOOD_HOE             = new Material("WOOD_HOE", 290, 1, 59);
//    public static final  Material                STONE_HOE            = new Material("STONE_HOE", 291, 1, 131);
//    public static final  Material                IRON_HOE             = new Material("IRON_HOE", 292, 1, 250);
//    public static final  Material                DIAMOND_HOE          = new Material("DIAMOND_HOE", 293, 1, 1561);
//    public static final  Material                GOLD_HOE             = new Material("GOLD_HOE", 294, 1, 32);
//    public static final  Material                SEEDS                = new Material("SEEDS", 295);
//    public static final  Material                WHEAT                = new Material("WHEAT", 296);
//    public static final  Material                BREAD                = new Material("BREAD", 297);
//    public static final  Material                LEATHER_HELMET       = new Material("LEATHER_HELMET", 298, 1, 55);
//    public static final  Material                LEATHER_CHESTPLATE   = new Material("LEATHER_CHESTPLATE", 299, 1, 80);
//    public static final  Material                LEATHER_LEGGINGS     = new Material("LEATHER_LEGGINGS", 300, 1, 75);
//    public static final  Material                LEATHER_BOOTS        = new Material("LEATHER_BOOTS", 301, 1, 65);
//    public static final  Material                CHAINMAIL_HELMET     = new Material("CHAINMAIL_HELMET", 302, 1, 165);
//    public static final  Material                CHAINMAIL_CHESTPLATE = new Material("CHAINMAIL_CHESTPLATE", 303, 1, 240);
//    public static final  Material                CHAINMAIL_LEGGINGS   = new Material("CHAINMAIL_LEGGINGS", 304, 1, 225);
//    public static final  Material                CHAINMAIL_BOOTS      = new Material("CHAINMAIL_BOOTS", 305, 1, 195);
//    public static final  Material                IRON_HELMET          = new Material("IRON_HELMET", 306, 1, 165);
//    public static final  Material                IRON_CHESTPLATE      = new Material("IRON_CHESTPLATE", 307, 1, 240);
//    public static final  Material                IRON_LEGGINGS        = new Material("IRON_LEGGINGS", 308, 1, 225);
//    public static final  Material                IRON_BOOTS           = new Material("IRON_BOOTS", 309, 1, 195);
//    public static final  Material                DIAMOND_HELMET       = new Material("DIAMOND_HELMET", 310, 1, 363);
//    public static final  Material                DIAMOND_CHESTPLATE   = new Material("DIAMOND_CHESTPLATE", 311, 1, 528);
//    public static final  Material                DIAMOND_LEGGINGS     = new Material("DIAMOND_LEGGINGS", 312, 1, 495);
//    public static final  Material                DIAMOND_BOOTS        = new Material("DIAMOND_BOOTS", 313, 1, 429);
//    public static final  Material                GOLD_HELMET          = new Material("GOLD_HELMET", 314, 1, 77);
//    public static final  Material                GOLD_CHESTPLATE      = new Material("GOLD_CHESTPLATE", 315, 1, 112);
//    public static final  Material                GOLD_LEGGINGS        = new Material("GOLD_LEGGINGS", 316, 1, 105);
//    public static final  Material                GOLD_BOOTS           = new Material("GOLD_BOOTS", 317, 1, 91);
//    public static final  Material                FLINT                = new Material("FLINT", 318);
//    public static final  Material                PORK                 = new Material("PORK", 319);
//    public static final  Material                GRILLED_PORK         = new Material("GRILLED_PORK", 320);
//    public static final  Material                PAINTING             = new Material("PAINTING", 321);
//    public static final  Material                GOLDEN_APPLE         = new Material("GOLDEN_APPLE", 322);
//    public static final  Material                SIGN                 = new Material("SIGN", 323, 16);
//    public static final  Material                WOOD_DOOR            = new Material("WOOD_DOOR", 324, 64);
//    public static final  Material                BUCKET               = new Material("BUCKET", 325, 16);
//    public static final  Material                WATER_BUCKET         = new Material("WATER_BUCKET", 326, 1);
//    public static final  Material                LAVA_BUCKET          = new Material("LAVA_BUCKET", 327, 1);
//    public static final  Material                MINECART             = new Material("MINECART", 328, 1);
//    public static final  Material                SADDLE               = new Material("SADDLE", 329, 1);
//    public static final  Material                IRON_DOOR_BOTTOM_EAST            = new Material("IRON_DOOR_BOTTOM_EAST", 330, 64);
//    public static final  Material                REDSTONE             = new Material("REDSTONE", 331);
//    public static final  Material                SNOW_BALL            = new Material("SNOW_BALL", 332, 16);
//    public static final  Material                BOAT                 = new Material("BOAT", 333, 1);
//    public static final  Material                LEATHER              = new Material("LEATHER", 334);
//    public static final  Material                MILK_BUCKET          = new Material("MILK_BUCKET", 335, 1);
//    public static final  Material                CLAY_BRICK           = new Material("CLAY_BRICK", 336);
//    public static final  Material                CLAY_BALL            = new Material("CLAY_BALL", 337);
//    public static final  Material                SUGAR_CANE           = new Material("SUGAR_CANE", 338);
//    public static final  Material                PAPER                = new Material("PAPER", 339);
//    public static final  Material                BOOK                 = new Material("BOOK", 340);
//    public static final  Material                SLIME_BALL           = new Material("SLIME_BALL", 341);
//    public static final  Material                STORAGE_MINECART     = new Material("STORAGE_MINECART", 342, 1);
//    public static final  Material                POWERED_MINECART     = new Material("POWERED_MINECART", 343, 1);
//    public static final  Material                EGG                  = new Material("EGG", 344, 16);
//    public static final  Material                COMPASS              = new Material("COMPASS", 345);
//    public static final  Material                FISHING_ROD          = new Material("FISHING_ROD", 346, 1, 64);
//    public static final  Material                WATCH                = new Material("WATCH", 347);
//    public static final  Material                GLOWSTONE_DUST       = new Material("GLOWSTONE_DUST", 348);
//    public static final  Material                RAW_FISH             = new Material("RAW_FISH", 349);
//    public static final  Material                COOKED_FISH          = new Material("COOKED_FISH", 350);
//    public static final  Material                INK_SACK             = new Material("INK_SACK", 351);
//    public static final  Material                BONE                 = new Material("BONE", 352);
//    public static final  Material                SUGAR                = new Material("SUGAR", 353);
//    public static final  Material                CAKE_0                 = new Material("CAKE_0", 354, 1);
//    public static final  Material                BED_BLOCK                  = new Material("BED_BLOCK", 355, 1);
//    public static final  Material                DIODE                = new Material("DIODE", 356);
//    public static final  Material                COOKIE               = new Material("COOKIE", 357);
//    public static final  Material                MAP                  = new Material("MAP", 358);
//    public static final  Material                SHEARS               = new Material("SHEARS", 359, 1, 238);
//    public static final  Material                MELON                = new Material("MELON", 360);
//    public static final  Material                PUMPKIN_SEEDS        = new Material("PUMPKIN_SEEDS", 361);
//    public static final  Material                MELON_SEEDS          = new Material("MELON_SEEDS", 362);
//    public static final  Material                RAW_BEEF             = new Material("RAW_BEEF", 363);
//    public static final  Material                COOKED_BEEF          = new Material("COOKED_BEEF", 364);
//    public static final  Material                RAW_CHICKEN          = new Material("RAW_CHICKEN", 365);
//    public static final  Material                COOKED_CHICKEN       = new Material("COOKED_CHICKEN", 366);
//    public static final  Material                ROTTEN_FLESH         = new Material("ROTTEN_FLESH", 367);
//    public static final  Material                ENDER_PEARL          = new Material("ENDER_PEARL", 368, 16);
//    public static final  Material                BLAZE_ROD            = new Material("BLAZE_ROD", 369);
//    public static final  Material                GHAST_TEAR           = new Material("GHAST_TEAR", 370);
//    public static final  Material                GOLD_NUGGET          = new Material("GOLD_NUGGET", 371);
//    public static final  Material                NETHER_STALK         = new Material("NETHER_STALK", 372);
//    public static final  Material                POTION               = new Material("POTION", 373, 1);
//    public static final  Material                GLASS_BOTTLE         = new Material("GLASS_BOTTLE", 374);
//    public static final  Material                SPIDER_EYE           = new Material("SPIDER_EYE", 375);
//    public static final  Material                FERMENTED_SPIDER_EYE = new Material("FERMENTED_SPIDER_EYE", 376);
//    public static final  Material                BLAZE_POWDER         = new Material("BLAZE_POWDER", 377);
//    public static final  Material                MAGMA_CREAM          = new Material("MAGMA_CREAM", 378);
//    public static final  Material                BREWING_STAND_ITEM   = new Material("BREWING_STAND_ITEM", 379);
//    public static final  Material                CAULDRON_ITEM        = new Material("CAULDRON_ITEM", 380);
//    public static final  Material                EYE_OF_ENDER         = new Material("EYE_OF_ENDER", 381);
//    public static final  Material                SPECKLED_MELON       = new Material("SPECKLED_MELON", 382);
//    public static final  Material                MONSTER_EGG          = new Material("MONSTER_EGG", 383, 64);
//    public static final  Material                EXP_BOTTLE           = new Material("EXP_BOTTLE", 384, 64);
//    public static final  Material                FIREBALL             = new Material("FIREBALL", 385, 64);
//    public static final  Material                BOOK_AND_QUILL       = new Material("BOOK_AND_QUILL", 386, 1);
//    public static final  Material                WRITTEN_BOOK         = new Material("WRITTEN_BOOK", 387, 16);
//    public static final  Material                EMERALD              = new Material("EMERALD", 388, 64);
//    public static final  Material                ITEM_FRAME           = new Material("ITEM_FRAME", 389);
//    public static final  Material                FLOWER_POT_ITEM      = new Material("FLOWER_POT_ITEM", 390);
//    public static final  Material                CARROT_ITEM          = new Material("CARROT_ITEM", 391);
//    public static final  Material                POTATO_ITEM          = new Material("POTATO_ITEM", 392);
//    public static final  Material                BAKED_POTATO         = new Material("BAKED_POTATO", 393);
//    public static final  Material                POISONOUS_POTATO     = new Material("POISONOUS_POTATO", 394);
//    public static final  Material                EMPTY_MAP            = new Material("EMPTY_MAP", 395);
//    public static final  Material                GOLDEN_CARROT        = new Material("GOLDEN_CARROT", 396);
//    public static final  Material                SKULL_ITEM           = new Material("SKULL_ITEM", 397);
//    public static final  Material                CARROT_STICK         = new Material("CARROT_STICK", 398, 1, 25);
//    public static final  Material                NETHER_STAR          = new Material("NETHER_STAR", 399);
//    public static final  Material                PUMPKIN_PIE          = new Material("PUMPKIN_PIE", 400);
//    public static final  Material                FIREWORK             = new Material("FIREWORK", 401);
//    public static final  Material                FIREWORK_CHARGE      = new Material("FIREWORK_CHARGE", 402);
//    public static final  Material                ENCHANTED_BOOK       = new Material("ENCHANTED_BOOK", 403, 1);
//    public static final  Material                REDSTONE_COMPARATOR  = new Material("REDSTONE_COMPARATOR", 404);
//    public static final  Material                NETHER_BRICK_ITEM    = new Material("NETHER_BRICK_ITEM", 405);
//    public static final  Material                QUARTZ               = new Material("QUARTZ", 406);
//    public static final  Material                EXPLOSIVE_MINECART   = new Material("EXPLOSIVE_MINECART", 407, 1);
//    public static final  Material                HOPPER_MINECART      = new Material("HOPPER_MINECART", 408, 1);
//    public static final  Material                PRISMARINE_SHARD     = new Material("PRISMARINE_SHARD", 409);
//    public static final  Material                PRISMARINE_CRYSTALS  = new Material("PRISMARINE_CRYSTALS", 410);
//    public static final  Material                RABBIT               = new Material("RABBIT", 411);
//    public static final  Material                COOKED_RABBIT        = new Material("COOKED_RABBIT", 412);
//    public static final  Material                RABBIT_STEW          = new Material("RABBIT_STEW", 413, 1);
//    public static final  Material                RABBIT_FOOT          = new Material("RABBIT_FOOT", 414);
//    public static final  Material                RABBIT_HIDE          = new Material("RABBIT_HIDE", 415);
//    public static final  Material                ARMOR_STAND          = new Material("ARMOR_STAND", 416, 16);
//    public static final  Material                IRON_BARDING         = new Material("IRON_BARDING", 417, 1);
//    public static final  Material                GOLD_BARDING         = new Material("GOLD_BARDING", 418, 1);
//    public static final  Material                DIAMOND_BARDING      = new Material("DIAMOND_BARDING", 419, 1);
//    public static final  Material                LEASH                = new Material("LEASH", 420);
//    public static final  Material                NAME_TAG             = new Material("NAME_TAG", 421);
//    public static final  Material                COMMAND_MINECART     = new Material("COMMAND_MINECART", 422, 1);
//    public static final  Material                MUTTON               = new Material("MUTTON", 423);
//    public static final  Material                COOKED_MUTTON        = new Material("COOKED_MUTTON", 424);
//    public static final  Material                BANNER               = new Material("BANNER", 425, 16);
//    public static final  Material                SPRUCE_DOOR_ITEM     = new Material("SPRUCE_DOOR_ITEM", 427);
//    public static final  Material                BIRCH_DOOR_ITEM      = new Material("BIRCH_DOOR_ITEM", 428);
//    public static final  Material                JUNGLE_DOOR_ITEM     = new Material("JUNGLE_DOOR_ITEM", 429);
//    public static final  Material                ACACIA_DOOR_ITEM     = new Material("ACACIA_DOOR_ITEM", 430);
//    public static final  Material                DARK_OAK_DOOR_ITEM   = new Material("DARK_OAK_DOOR_ITEM", 431);
//    public static final  Material                GOLD_RECORD          = new Material("GOLD_RECORD", 2256, 1);
//    public static final  Material                GREEN_RECORD         = new Material("GREEN_RECORD", 2257, 1);
//    public static final  Material                RECORD_3             = new Material("RECORD_3", 2258, 1);
//    public static final  Material                RECORD_4             = new Material("RECORD_4", 2259, 1);
//    public static final  Material                RECORD_5             = new Material("RECORD_5", 2260, 1);
//    public static final  Material                RECORD_6             = new Material("RECORD_6", 2261, 1);
//    public static final  Material                RECORD_7             = new Material("RECORD_7", 2262, 1);
//    public static final  Material                RECORD_8             = new Material("RECORD_8", 2263, 1);
//    public static final  Material                RECORD_9             = new Material("RECORD_9", 2264, 1);
//    public static final  Material                RECORD_10            = new Material("RECORD_10", 2265, 1);
//    public static final  Material                RECORD_11            = new Material("RECORD_11", 2266, 1);
//    public static final  Material                RECORD_12            = new Material("RECORD_12", 2267, 1);
    private static final Map<String, Material>    byName                     = new SimpleStringHashMap<>(MATERIALS_SIZE, SMALL_LOAD_FACTOR);
    private static final Map<String, Material>    byMinecraftId              = new SimpleStringHashMap<>(MATERIALS_SIZE, SMALL_LOAD_FACTOR);
    private static final TIntObjectMap<Material>  byID                       = new TIntObjectHashMap<>(MATERIALS_SIZE, SMALL_LOAD_FACTOR);
    private final String enumName;
    private final int    id;
    private final String minecraftId;
    private final int    maxStack;

    public Material(final String enumName, final int id, final String minecraftId)
    {
        this(enumName, id, minecraftId, MagicNumbers.ITEMS__DEFAULT_STACK_SIZE);
    }

    public Material(final String enumName, final int id, final String minecraftId, final int maxStack)
    {
        this.enumName = enumName;
        this.id = id;
        this.minecraftId = minecraftId;
        this.maxStack = maxStack;
    }

    @Override
    public String name()
    {
        return this.enumName;
    }

    @Override
    public int getId()
    {
        return this.id;
    }

    public String getMinecraftId()
    {
        return this.minecraftId;
    }

    @Override
    public Material byId(final int id)
    {
        return byID.get(id);
    }

    @Override
    public Material byName(final String name)
    {
        return byName.get(name);
    }

    public int getMaxStack()
    {
        return this.maxStack;
    }

    public abstract boolean isBlock();

    public abstract boolean isSolid();

    public abstract boolean isTransparent();

    public abstract boolean isFlammable();

    public abstract boolean isBurnable();

    public abstract boolean isOccluding();

    public abstract boolean hasGravity();

    public abstract boolean isEdible();

    public abstract boolean isReplaceable();

    public abstract boolean isGlowing();

    public abstract int getLuminance();

    public abstract float getBlastResistance();

    public abstract float getHardness();

    public abstract IntRange getExperienceWhenMined();

    // TODO: method to get possible drops

    public boolean isThisSameID(final Material mat)
    {
        return this.enumName.equals(mat.enumName);
    }

    public final boolean simpleEquals(final Object o)
    {
        //noinspection ObjectEquality
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof Material))
        {
            return false;
        }

        final Material material = (Material) o;

        return this.enumName.equals(material.enumName);
    }

    @Override
    public int hashCode()
    {
        return this.enumName.hashCode();
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof Material))
        {
            return false;
        }

        final Material material = (Material) o;

        return this.enumName.equals(material.enumName);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("enumName", this.enumName).append("id", this.id).append("maxStack", this.maxStack).toString();
    }

    public static Material getByID(final int id)
    {
        return byID.get(id);
    }

    public static Material getByID(final int id, final int meta)
    {
        final Material mat = byID.get(id);
        if (mat instanceof BlockMaterialData)
        {
            return ((BlockMaterialData) mat).getType(meta);
        }
        return mat;
    }

    public static Material getByID(final int id, final String meta)
    {
        final Material mat = byID.get(id);
        if (mat instanceof BlockMaterialData)
        {
            return ((BlockMaterialData) mat).getType(meta);
        }
        return mat;
    }

    public static Material getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static Material getByEnumName(final String name, final int meta)
    {
        final Material mat = byName.get(name);
        if (mat instanceof BlockMaterialData)
        {
            return ((BlockMaterialData) mat).getType(meta);
        }
        return mat;
    }

    public static Material getByEnumName(final String name, final String meta)
    {
        final Material mat = byName.get(name);
        if (mat instanceof BlockMaterialData)
        {
            return ((BlockMaterialData) mat).getType(meta);
        }
        return mat;
    }

    public static Material getByMinecraftId(final String name)
    {
        return byMinecraftId.get(name);
    }

    public static void register(final Material element)
    {
        byID.put(element.getId(), element);
        byName.put(element.name(), element);
        byMinecraftId.put(element.getMinecraftId(), element);
    }

    static
    {
        register(AIR);
        register(STONE);
        register(GRASS);
        register(DIRT);
        register(COBBLESTONE);
        register(PLANKS);
        register(SAPLING);
        register(BEDROCK);
        register(WATER);
        register(WATER_STILL);
        register(LAVA);
        register(LAVA_STILL);
        register(SAND);
        register(GRAVEL);
        register(GOLD_ORE);
        register(IRON_ORE);
        register(COAL_ORE);
        register(LOG);
        register(LEAVES);
        register(SPONGE);
        register(GLASS);
        register(LAPIS_ORE);
        register(LAPIS_BLOCK);
        register(DISPENSER);
        register(SANDSTONE);
        register(NOTEBLOCK);
        register(BED_BLOCK);
        register(POWERED_RAIL);
        register(DETECTOR_RAIL);
        register(PISTON_STICKY);
        register(WEB);
        register(TALL_GRASS);
        register(DEAD_BUSH);
        register(PISTON);
        register(PISTON_EXTENSION);
        register(WOOL);
        register(PISTON_HEAD);
        register(DANDELION);
        register(FLOWERS);
        register(BROWN_MUSHROOM);
        register(RED_MUSHROOM);
        register(GOLD_BLOCK);
        register(IRON_BLOCK);
        register(DOUBLE_STONE_SLAB);
        register(STONE_SLAB);
        register(BRICK_BLOCK);
        register(TNT);
        register(BOOKSHELF);
        register(MOSSY_COBBLESTONE);
        register(OBSIDIAN);
        register(TORCH);
        register(FIRE);
        register(MOB_SPAWNER);
        register(OAK_STAIRS);
        register(CHEST);
        register(REDSTONE_WIRE);
        register(DIAMOND_ORE);
        register(DIAMOND_BLOCK);
        register(CRAFTING_TABLE);
        register(WHEAT_BLOCK);
        register(FARMLAND);
        register(FURNACE);
        register(BURNING_FURNACE);
        register(STANDING_SIGN);
        register(OAK_DOOR);
        register(LADDER);
        register(RAIL);
        register(COBBLESTONE_STAIRS);
        register(WALL_SIGN);
        register(LEVER);
        register(STONE_PRESSURE_PLATE);
        register(IRON_DOOR);
        register(WOODEN_PRESSURE_PLATE);
        register(REDSTONE_ORE);
        register(REDSTONE_ORE_GLOWING);
        register(REDSTONE_TORCH_OFF);
        register(REDSTONE_TORCH_ON);
        register(STONE_BUTTON);
        register(SNOW_LAYER);
        register(ICE);
        register(SNOW_BLOCK);
        register(CACTUS);
        register(CLAY_BLOCK);
        register(SUGAR_CANE);
        register(JUKEBOX);
        register(OAK_FENCE);
        register(PUMPKIN);
        register(NETHERRACK);
        register(SOUL_SAND);
        register(GLOWSTONE);
        register(NETHER_PORTAL);
        register(PUMPKIN_LANTERN);
        register(CAKE);
        register(REPEATER_OFF);
        register(REPEATER_ON);
        register(STAINED_GLASS);
        register(WOODEN_TRAPDOOR);
        register(MONSTER_EGG_TRAP);
        register(STONE_BRICK);
        register(BROWN_MUSHROOM_BLOCK);
        register(RED_MUSHROOM_BLOCK);
        register(IRON_BARS);
        register(GLASS_PANE);
        register(MELON_BLOCK);
        register(PUMPKIN_STEM);
        register(MELON_STEM);
        register(VINE);
        register(OAK_FENCE_GATE);
        register(BRICK_STAIRS);
        register(STONE_BRICK_STAIRS);
        register(MYCELIUM);
        register(WATER_LILY);
        register(NETHER_BRICK);
        register(NETHER_BRICK_FENCE);
        register(NETHER_BRICK_STAIRS);
        register(NETHER_WART_BLOCK);
        register(ENCHANTING_TABLE);
        register(BREWING_STAND_BLOCK);
        register(CAULDRON);
        register(END_PORTAL);
        register(END_PORTAL_FRAME);
        register(END_STONE);
        register(DRAGON_EGG);
//        register(DOUBLE_WOODEN_SLAB);
//        register(WOODEN_SLAB);
        register(COCOA);
        register(SANDSTONE_STAIRS);
        register(EMERALD_ORE);
        register(ENDER_CHEST);
        register(TRIPWIRE_HOOK);
        register(TRIPWIRE);
        register(EMERALD_BLOCK);
        register(SPRUCE_STAIRS);
        register(BIRCH_STAIRS);
        register(JUNGLE_STAIRS);
        register(COMMAND_BLOCK);
        register(BEACON);
        register(COBBLESTONE_WALL);
        register(FLOWER_POT);
        register(CARROTS_BLOCK);
        register(POTATOES_BLOCK);
        register(WOODEN_BUTTON);
        register(SKULL_BLOCK);
        register(ANVIL);
        register(TRAPPED_CHEST);
        register(GOLDEN_PRESSURE_PLATE);
        register(IRON_PRESSURE_PLATE);
        register(REDSTONE_COMPARATOR);
        register(DAYLIGHT_DETECTOR);
        register(REDSTONE_BLOCK);
        register(QUARTZ_ORE);
        register(HOPPER);
        register(QUARTZ_BLOCK);
        register(QUARTZ_STAIRS);
        register(ACTIVATOR_RAIL);
        register(DROPPER);
        register(STAINED_HARDENED_CLAY);
        register(STAINED_GLASS_PANE);
        register(Leaves.LEAVES_ACACIA);
        register(Log.LOG_ACACIA);
        register(ACACIA_STAIRS);
        register(DARK_OAK_STAIRS);
        register(SLIME_BLOCK);
        register(BARRIER);
        register(IRON_TRAPDOOR);
        register(PRISMARINE);
        register(SEA_LANTERN);
        register(HAY_BLOCK);
        register(CARPET);
        register(HARDENED_CLAY);
        register(COAL_BLOCK);
        register(PACKED_ICE);
        register(DOUBLE_FLOWERS);
        register(STANDING_BANNER);
        register(WALL_BANNER);
        register(DAYLIGHT_DETECTOR_INVERTED);
        register(RED_SANDSTONE);
        register(RED_SANDSTONE_STAIRS);
        register(SPRUCE_FENCE_GATE);
        register(BIRCH_FENCE_GATE);
        register(JUNGLE_FENCE_GATE);
        register(DARK_OAK_FENCE_GATE);
        register(ACACIA_FENCE_GATE);
        register(SPRUCE_FENCE);
        register(BIRCH_FENCE);
        register(JUNGLE_FENCE);
        register(DARK_OAK_FENCE);
        register(ACACIA_FENCE);
        register(SPRUCE_DOOR);
        register(BIRCH_DOOR);
        register(JUNGLE_DOOR);
        register(ACACIA_DOOR);
        register(DARK_OAK_DOOR);
//        register(IRON_SPADE);
//        register(IRON_PICKAXE);
//        register(IRON_AXE);
//        register(FLINT_AND_STEEL);
//        register(APPLE);
//        register(BOW);
//        register(ARROW);
//        register(COAL);
//        register(DIAMOND);
//        register(IRON_INGOT);
//        register(GOLD_INGOT);
//        register(IRON_SWORD);
//        register(WOOD_SWORD);
//        register(WOOD_SPADE);
//        register(WOOD_PICKAXE);
//        register(WOOD_AXE);
//        register(STONE_SWORD);
//        register(STONE_SPADE);
//        register(STONE_PICKAXE);
//        register(STONE_AXE);
//        register(DIAMOND_SWORD);
//        register(DIAMOND_SPADE);
//        register(DIAMOND_PICKAXE);
//        register(DIAMOND_AXE);
//        register(STICK);
//        register(BOWL);
//        register(MUSHROOM_SOUP);
//        register(GOLD_SWORD);
//        register(GOLD_SPADE);
//        register(GOLD_PICKAXE);
//        register(GOLD_AXE);
//        register(STRING);
//        register(FEATHER);
//        register(SULPHUR);
//        register(WOOD_HOE);
//        register(STONE_HOE);
//        register(IRON_HOE);
//        register(DIAMOND_HOE);
//        register(GOLD_HOE);
//        register(SEEDS);
//        register(WHEAT);
//        register(BREAD);
//        register(LEATHER_HELMET);
//        register(LEATHER_CHESTPLATE);
//        register(LEATHER_LEGGINGS);
//        register(LEATHER_BOOTS);
//        register(CHAINMAIL_HELMET);
//        register(CHAINMAIL_CHESTPLATE);
//        register(CHAINMAIL_LEGGINGS);
//        register(CHAINMAIL_BOOTS);
//        register(IRON_HELMET);
//        register(IRON_CHESTPLATE);
//        register(IRON_LEGGINGS);
//        register(IRON_BOOTS);
//        register(DIAMOND_HELMET);
//        register(DIAMOND_CHESTPLATE);
//        register(DIAMOND_LEGGINGS);
//        register(DIAMOND_BOOTS);
//        register(GOLD_HELMET);
//        register(GOLD_CHESTPLATE);
//        register(GOLD_LEGGINGS);
//        register(GOLD_BOOTS);
//        register(FLINT);
//        register(PORK);
//        register(GRILLED_PORK);
//        register(PAINTING);
//        register(GOLDEN_APPLE);
//        register(SIGN);
//        register(WOOD_DOOR);
//        register(BUCKET);
//        register(WATER_BUCKET);
//        register(LAVA_BUCKET);
//        register(MINECART);
//        register(SADDLE);
//        register(IRON_DOOR_BOTTOM_EAST);
//        register(REDSTONE);
//        register(SNOW_BALL);
//        register(BOAT);
//        register(LEATHER);
//        register(MILK_BUCKET);
//        register(CLAY_BRICK);
//        register(CLAY_BALL);
//        register(SUGAR_CANE);
//        register(PAPER);
//        register(BOOK);
//        register(SLIME_BALL);
//        register(STORAGE_MINECART);
//        register(POWERED_MINECART);
//        register(EGG);
//        register(COMPASS);
//        register(FISHING_ROD);
//        register(WATCH);
//        register(GLOWSTONE_DUST);
//        register(RAW_FISH);
//        register(COOKED_FISH);
//        register(INK_SACK);
//        register(BONE);
//        register(SUGAR);
//        register(CAKE_0);
//        register(BED_BLOCK);
//        register(DIODE);
//        register(COOKIE);
//        register(MAP);
//        register(SHEARS);
//        register(MELON);
//        register(PUMPKIN_SEEDS);
//        register(MELON_SEEDS);
//        register(RAW_BEEF);
//        register(COOKED_BEEF);
//        register(RAW_CHICKEN);
//        register(COOKED_CHICKEN);
//        register(ROTTEN_FLESH);
//        register(ENDER_PEARL);
//        register(BLAZE_ROD);
//        register(GHAST_TEAR);
//        register(GOLD_NUGGET);
//        register(NETHER_STALK);
//        register(POTION);
//        register(GLASS_BOTTLE);
//        register(SPIDER_EYE);
//        register(FERMENTED_SPIDER_EYE);
//        register(BLAZE_POWDER);
//        register(MAGMA_CREAM);
//        register(BREWING_STAND_ITEM);
//        register(CAULDRON_ITEM);
//        register(EYE_OF_ENDER);
//        register(SPECKLED_MELON);
//        register(MONSTER_EGG);
//        register(EXP_BOTTLE);
//        register(FIREBALL);
//        register(BOOK_AND_QUILL);
//        register(WRITTEN_BOOK);
//        register(EMERALD);
//        register(ITEM_FRAME);
//        register(FLOWER_POT_ITEM);
//        register(CARROT_ITEM);
//        register(POTATO_ITEM);
//        register(BAKED_POTATO);
//        register(POISONOUS_POTATO);
//        register(EMPTY_MAP);
//        register(GOLDEN_CARROT);
//        register(SKULL_ITEM);
//        register(CARROT_STICK);
//        register(NETHER_STAR);
//        register(PUMPKIN_PIE);
//        register(FIREWORK);
//        register(FIREWORK_CHARGE);
//        register(ENCHANTED_BOOK);
//        register(REDSTONE_COMPARATOR);
//        register(NETHER_BRICK_ITEM);
//        register(QUARTZ);
//        register(EXPLOSIVE_MINECART);
//        register(HOPPER_MINECART);
//        register(PRISMARINE_SHARD);
//        register(PRISMARINE_CRYSTALS);
//        register(RABBIT);
//        register(COOKED_RABBIT);
//        register(RABBIT_STEW);
//        register(RABBIT_FOOT);
//        register(RABBIT_HIDE);
//        register(ARMOR_STAND);
//        register(IRON_BARDING);
//        register(GOLD_BARDING);
//        register(DIAMOND_BARDING);
//        register(LEASH);
//        register(NAME_TAG);
//        register(COMMAND_MINECART);
//        register(MUTTON);
//        register(COOKED_MUTTON);
//        register(BANNER);
//        register(SPRUCE_DOOR_ITEM);
//        register(BIRCH_DOOR_ITEM);
//        register(JUNGLE_DOOR_ITEM);
//        register(ACACIA_DOOR_ITEM);
//        register(DARK_OAK_DOOR_ITEM);
//        register(GOLD_RECORD);
//        register(GREEN_RECORD);
//        register(RECORD_3);
//        register(RECORD_4);
//        register(RECORD_5);
//        register(RECORD_6);
//        register(RECORD_7);
//        register(RECORD_8);
//        register(RECORD_9);
//        register(RECORD_10);
//        register(RECORD_11);
//        register(RECORD_12);
    }

    public static TIntObjectMap<Material> getByID()
    {
        return byID;
    }

    public static Map<String, Material> getByName()
    {
        return byName;
    }
}
