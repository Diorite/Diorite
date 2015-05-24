package org.diorite.material;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.AirMat;
import org.diorite.material.blocks.cold.IceMat;
import org.diorite.material.blocks.cold.PackedIceMat;
import org.diorite.material.blocks.cold.SnowBlockMat;
import org.diorite.material.blocks.cold.SnowLayerMat;
import org.diorite.material.blocks.earth.ClayBlockMat;
import org.diorite.material.blocks.earth.DirtMat;
import org.diorite.material.blocks.earth.FarmlandMat;
import org.diorite.material.blocks.earth.GrassMat;
import org.diorite.material.blocks.earth.MyceliumMat;
import org.diorite.material.blocks.end.EndPortalFrameMat;
import org.diorite.material.blocks.end.EndPortalMat;
import org.diorite.material.blocks.end.EndStoneMat;
import org.diorite.material.blocks.liquid.LavaMat;
import org.diorite.material.blocks.liquid.WaterMat;
import org.diorite.material.blocks.loose.GravelMat;
import org.diorite.material.blocks.loose.SandMat;
import org.diorite.material.blocks.nether.GlowstoneMat;
import org.diorite.material.blocks.nether.NetherBrickFenceMat;
import org.diorite.material.blocks.nether.NetherBrickMat;
import org.diorite.material.blocks.nether.NetherBrickStairsMat;
import org.diorite.material.blocks.nether.NetherPortalMat;
import org.diorite.material.blocks.nether.NetherrackMat;
import org.diorite.material.blocks.nether.SoulSandMat;
import org.diorite.material.blocks.others.BrownMushroomBlockMat;
import org.diorite.material.blocks.others.CakeMat;
import org.diorite.material.blocks.others.CarpetMat;
import org.diorite.material.blocks.others.FireMat;
import org.diorite.material.blocks.others.FlowerPotMat;
import org.diorite.material.blocks.others.GlassMat;
import org.diorite.material.blocks.others.GlassPaneMat;
import org.diorite.material.blocks.others.HayBlockMat;
import org.diorite.material.blocks.others.IronBarsMat;
import org.diorite.material.blocks.others.IronDoorMat;
import org.diorite.material.blocks.others.IronTrapdoorMat;
import org.diorite.material.blocks.others.JukeboxMat;
import org.diorite.material.blocks.others.LadderMat;
import org.diorite.material.blocks.others.MelonBlockMat;
import org.diorite.material.blocks.others.MobSpawnerMat;
import org.diorite.material.blocks.others.MonsterEggTrapMat;
import org.diorite.material.blocks.others.PrismarineMat;
import org.diorite.material.blocks.others.RedMushroomBlockMat;
import org.diorite.material.blocks.others.SeaLantrenMat;
import org.diorite.material.blocks.others.SkullBlockMat;
import org.diorite.material.blocks.others.StainedGlassMat;
import org.diorite.material.blocks.others.StainedGlassPaneMat;
import org.diorite.material.blocks.others.StandingBannerMat;
import org.diorite.material.blocks.others.StandingSignMat;
import org.diorite.material.blocks.others.TorchMat;
import org.diorite.material.blocks.others.WallBannerMat;
import org.diorite.material.blocks.others.WallSignMat;
import org.diorite.material.blocks.others.WoodenTrapdoorMat;
import org.diorite.material.blocks.others.WoolMat;
import org.diorite.material.blocks.plants.CactusMat;
import org.diorite.material.blocks.plants.CarrotsBlockMat;
import org.diorite.material.blocks.plants.CocoaMat;
import org.diorite.material.blocks.plants.DandelionMat;
import org.diorite.material.blocks.plants.DeadBushMat;
import org.diorite.material.blocks.plants.DoubleFlowersMat;
import org.diorite.material.blocks.plants.FlowersMat;
import org.diorite.material.blocks.plants.MelonStemMat;
import org.diorite.material.blocks.plants.MushroomBrownMat;
import org.diorite.material.blocks.plants.MushroomRedMat;
import org.diorite.material.blocks.plants.NetherWartBlockMat;
import org.diorite.material.blocks.plants.PotatoesBlockMat;
import org.diorite.material.blocks.plants.PumpkinLanternMat;
import org.diorite.material.blocks.plants.PumpkinMat;
import org.diorite.material.blocks.plants.PumpkinStemMat;
import org.diorite.material.blocks.plants.SugarCaneMat;
import org.diorite.material.blocks.plants.TallGrassMat;
import org.diorite.material.blocks.plants.VineMat;
import org.diorite.material.blocks.plants.WaterLilyMat;
import org.diorite.material.blocks.plants.WheatBlockMat;
import org.diorite.material.blocks.rails.ActivatorRailMat;
import org.diorite.material.blocks.rails.DetectorRailMat;
import org.diorite.material.blocks.rails.PoweredRailMat;
import org.diorite.material.blocks.rails.RailMat;
import org.diorite.material.blocks.redstone.DaylightDetectorInvertedMat;
import org.diorite.material.blocks.redstone.DaylightDetectorMat;
import org.diorite.material.blocks.redstone.GoldenPressurePlateMat;
import org.diorite.material.blocks.redstone.IronPressurePlateMat;
import org.diorite.material.blocks.redstone.LeverMat;
import org.diorite.material.blocks.redstone.RedstoneComparatorMat;
import org.diorite.material.blocks.redstone.RedstoneLampOffMat;
import org.diorite.material.blocks.redstone.RedstoneLampOnMat;
import org.diorite.material.blocks.redstone.RedstoneRepeaterOffMat;
import org.diorite.material.blocks.redstone.RedstoneRepeaterOnMat;
import org.diorite.material.blocks.redstone.RedstoneTorchOffMat;
import org.diorite.material.blocks.redstone.RedstoneTorchOnMat;
import org.diorite.material.blocks.redstone.RedstoneWireMat;
import org.diorite.material.blocks.redstone.StoneButtonMat;
import org.diorite.material.blocks.redstone.StonePressurePlateMat;
import org.diorite.material.blocks.redstone.WoodenButtonMat;
import org.diorite.material.blocks.redstone.WoodenPressurePlateMat;
import org.diorite.material.blocks.redstone.piston.PistonExtensionMat;
import org.diorite.material.blocks.redstone.piston.PistonHeadMat;
import org.diorite.material.blocks.redstone.piston.PistonMat;
import org.diorite.material.blocks.redstone.piston.PistonStickyMat;
import org.diorite.material.blocks.stony.BedrockMat;
import org.diorite.material.blocks.stony.BrickBlockMat;
import org.diorite.material.blocks.stony.BrickStairsMat;
import org.diorite.material.blocks.stony.CobblestoneMat;
import org.diorite.material.blocks.stony.CobblestoneStairsMat;
import org.diorite.material.blocks.stony.CobblestoneWallMat;
import org.diorite.material.blocks.stony.DoubleStoneSlabMat;
import org.diorite.material.blocks.stony.HardenedClayMat;
import org.diorite.material.blocks.stony.MossyCobblestoneMat;
import org.diorite.material.blocks.stony.ObsidianMat;
import org.diorite.material.blocks.stony.QuartzStairsMat;
import org.diorite.material.blocks.stony.RedSandstoneMat;
import org.diorite.material.blocks.stony.RedSandstoneStairsMat;
import org.diorite.material.blocks.stony.SandstoneMat;
import org.diorite.material.blocks.stony.SandstoneStairsMat;
import org.diorite.material.blocks.stony.StainedHardenedClayMat;
import org.diorite.material.blocks.stony.StoneBrickMat;
import org.diorite.material.blocks.stony.StoneBrickStairsMat;
import org.diorite.material.blocks.stony.StoneMat;
import org.diorite.material.blocks.stony.StoneSlabMat;
import org.diorite.material.blocks.stony.ore.CoalOreMat;
import org.diorite.material.blocks.stony.ore.DiamondOreMat;
import org.diorite.material.blocks.stony.ore.EmeraldOreMat;
import org.diorite.material.blocks.stony.ore.GoldOreMat;
import org.diorite.material.blocks.stony.ore.IronOreMat;
import org.diorite.material.blocks.stony.ore.LapisOreMat;
import org.diorite.material.blocks.stony.ore.QuartzOreMat;
import org.diorite.material.blocks.stony.ore.RedstoneOreGlowingMat;
import org.diorite.material.blocks.stony.ore.RedstoneOreMat;
import org.diorite.material.blocks.stony.oreblocks.CoalBlockMat;
import org.diorite.material.blocks.stony.oreblocks.DiamondBlockMat;
import org.diorite.material.blocks.stony.oreblocks.EmeraldBlockMat;
import org.diorite.material.blocks.stony.oreblocks.GoldBlockMat;
import org.diorite.material.blocks.stony.oreblocks.IronBlockMat;
import org.diorite.material.blocks.stony.oreblocks.LapisBlockMat;
import org.diorite.material.blocks.stony.oreblocks.QuartzBlockMat;
import org.diorite.material.blocks.stony.oreblocks.RedstoneBlockMat;
import org.diorite.material.blocks.tools.AnvilMat;
import org.diorite.material.blocks.tools.BarrierMat;
import org.diorite.material.blocks.tools.BeaconMat;
import org.diorite.material.blocks.tools.BedBlockMat;
import org.diorite.material.blocks.tools.BrewingStandBlockMat;
import org.diorite.material.blocks.tools.BurningFurnaceMat;
import org.diorite.material.blocks.tools.CauldronMat;
import org.diorite.material.blocks.tools.ChestMat;
import org.diorite.material.blocks.tools.CobwebMat;
import org.diorite.material.blocks.tools.CommandBlockMat;
import org.diorite.material.blocks.tools.CraftingTableMat;
import org.diorite.material.blocks.tools.DispenserMat;
import org.diorite.material.blocks.tools.DragonEggMat;
import org.diorite.material.blocks.tools.DropperMat;
import org.diorite.material.blocks.tools.EnchantingTableMat;
import org.diorite.material.blocks.tools.EnderChestMat;
import org.diorite.material.blocks.tools.FurnaceMat;
import org.diorite.material.blocks.tools.HopperMat;
import org.diorite.material.blocks.tools.SlimeBlockMat;
import org.diorite.material.blocks.tools.SpongeMat;
import org.diorite.material.blocks.tools.TntMat;
import org.diorite.material.blocks.tools.TrappedChestMat;
import org.diorite.material.blocks.tools.TripwireHookMat;
import org.diorite.material.blocks.tools.TripwireMat;
import org.diorite.material.blocks.wooden.BookshelfMat;
import org.diorite.material.blocks.wooden.NoteBlockMat;
import org.diorite.material.blocks.wooden.wood.DoubleWoodenSlabMat;
import org.diorite.material.blocks.wooden.wood.LeavesMat;
import org.diorite.material.blocks.wooden.wood.LogMat;
import org.diorite.material.blocks.wooden.wood.PlanksMat;
import org.diorite.material.blocks.wooden.wood.SaplingMat;
import org.diorite.material.blocks.wooden.wood.WoodenSlabMat;
import org.diorite.material.blocks.wooden.wood.door.AcaciaDoorMat;
import org.diorite.material.blocks.wooden.wood.door.BirchDoorMat;
import org.diorite.material.blocks.wooden.wood.door.DarkOakDoorMat;
import org.diorite.material.blocks.wooden.wood.door.JungleDoorMat;
import org.diorite.material.blocks.wooden.wood.door.OakDoorMat;
import org.diorite.material.blocks.wooden.wood.door.SpruceDoorMat;
import org.diorite.material.blocks.wooden.wood.fence.AcaciaFenceMat;
import org.diorite.material.blocks.wooden.wood.fence.BirchFenceMat;
import org.diorite.material.blocks.wooden.wood.fence.DarkOakFenceMat;
import org.diorite.material.blocks.wooden.wood.fence.JungleFenceMat;
import org.diorite.material.blocks.wooden.wood.fence.OakFenceMat;
import org.diorite.material.blocks.wooden.wood.fence.SpruceFenceMat;
import org.diorite.material.blocks.wooden.wood.fencegate.AcaciaFenceGateMat;
import org.diorite.material.blocks.wooden.wood.fencegate.BirchFenceGateMat;
import org.diorite.material.blocks.wooden.wood.fencegate.DarkOakFenceGateMat;
import org.diorite.material.blocks.wooden.wood.fencegate.JungleFenceGateMat;
import org.diorite.material.blocks.wooden.wood.fencegate.OakFenceGateMat;
import org.diorite.material.blocks.wooden.wood.fencegate.SpruceFenceGateMat;
import org.diorite.material.blocks.wooden.wood.stairs.AcaciaStairsMat;
import org.diorite.material.blocks.wooden.wood.stairs.BirchStairsMat;
import org.diorite.material.blocks.wooden.wood.stairs.DarkOakStairsMat;
import org.diorite.material.blocks.wooden.wood.stairs.JungleStairsMat;
import org.diorite.material.blocks.wooden.wood.stairs.OakStairsMat;
import org.diorite.material.blocks.wooden.wood.stairs.SpruceStairsMat;
import org.diorite.utils.SimpleEnum;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public abstract class Material implements SimpleEnum<Material>
{
    public static final int MATERIALS_SIZE = 385;

    public static final  AirMat                      AIR                        = AirMat.AIR;
    public static final  StoneMat                    STONE                      = StoneMat.STONE;
    public static final  GrassMat                    GRASS                      = GrassMat.GRASS;
    public static final  DirtMat                     DIRT                       = DirtMat.DIRT;
    public static final  CobblestoneMat              COBBLESTONE                = CobblestoneMat.COBBLESTONE;
    public static final  PlanksMat                   PLANKS                     = PlanksMat.PLANKS_OAK;
    public static final  SaplingMat                  SAPLING                    = SaplingMat.SAPLING_OAK;
    public static final  BedrockMat                  BEDROCK                    = BedrockMat.BEDROCK;
    public static final  WaterMat                    WATER                      = WaterMat.WATER_SOURCE;
    public static final  WaterMat                    WATER_STILL                = WaterMat.WATER_SOURCE_STILL;
    public static final  LavaMat                     LAVA                       = LavaMat.LAVA_SOURCE;
    public static final  LavaMat                     LAVA_STILL                 = LavaMat.LAVA_SOURCE_STILL;
    public static final  SandMat                     SAND                       = SandMat.SAND;
    public static final  GravelMat                   GRAVEL                     = GravelMat.GRAVEL;
    public static final  GoldOreMat                  GOLD_ORE                   = GoldOreMat.GOLD_ORE;
    public static final  IronOreMat                  IRON_ORE                   = IronOreMat.IRON_ORE;
    public static final  CoalOreMat                  COAL_ORE                   = CoalOreMat.COAL_ORE;
    public static final  LogMat                      LOG                        = LogMat.LOG_OAK;
    public static final  LeavesMat                   LEAVES                     = LeavesMat.LEAVES_OAK;
    public static final  SpongeMat                   SPONGE                     = SpongeMat.SPONGE;
    public static final  GlassMat                    GLASS                      = GlassMat.GLASS;
    public static final  LapisOreMat                 LAPIS_ORE                  = LapisOreMat.LAPIS_ORE;
    public static final  LapisBlockMat               LAPIS_BLOCK                = LapisBlockMat.LAPIS_BLOCK;
    public static final  DispenserMat                DISPENSER                  = DispenserMat.DISPENSER_DOWN;
    public static final  SandstoneMat                SANDSTONE                  = SandstoneMat.SANDSTONE;
    public static final  NoteBlockMat                NOTEBLOCK                  = NoteBlockMat.NOTEBLOCK;
    public static final  BedBlockMat                 BED_BLOCK                  = BedBlockMat.BED_FOOT_SOUTH;
    public static final  PoweredRailMat              POWERED_RAIL               = PoweredRailMat.POWERED_RAIL_NORTH_SOUTH;
    public static final  DetectorRailMat             DETECTOR_RAIL              = DetectorRailMat.DETECTOR_RAIL_NORTH_SOUTH;
    public static final  PistonStickyMat             PISTON_STICKY              = PistonStickyMat.STICKY_PISTON_DOWN;
    public static final  CobwebMat                   WEB                        = CobwebMat.COBWEB;
    public static final  TallGrassMat                TALL_GRASS                 = TallGrassMat.TALL_GRASS_SHRUB;
    public static final  DeadBushMat                 DEAD_BUSH                  = DeadBushMat.DEAD_BUSH;
    public static final  PistonMat                   PISTON                     = PistonMat.PISTON_DOWN;
    public static final  PistonExtensionMat          PISTON_EXTENSION           = PistonExtensionMat.PISTON_EXTENSION_DOWN;
    public static final  WoolMat                     WOOL                       = WoolMat.WOOL_WHITE;
    public static final  PistonHeadMat               PISTON_HEAD                = PistonHeadMat.PISTON_HEAD_DOWN;
    public static final  DandelionMat                DANDELION                  = DandelionMat.DANDELION;
    public static final  FlowersMat                  FLOWERS                    = FlowersMat.FLOWERS_POPPY;
    public static final  MushroomBrownMat            BROWN_MUSHROOM             = MushroomBrownMat.BROWN_MUSHROOM;
    public static final  MushroomRedMat              RED_MUSHROOM               = MushroomRedMat.RED_MUSHROOM;
    public static final  GoldBlockMat                GOLD_BLOCK                 = GoldBlockMat.GOLD_BLOCK;
    public static final  IronBlockMat                IRON_BLOCK                 = IronBlockMat.IRON_BLOCK;
    public static final  DoubleStoneSlabMat          DOUBLE_STONE_SLAB          = DoubleStoneSlabMat.DOUBLE_STONE_SLAB_STONE;
    public static final  StoneSlabMat                STONE_SLAB                 = StoneSlabMat.STONE_SLAB_STONE;
    public static final  BrickBlockMat               BRICK_BLOCK                = BrickBlockMat.BRICK_BLOCK;
    public static final  TntMat                      TNT                        = TntMat.TNT;
    public static final  BookshelfMat                BOOKSHELF                  = BookshelfMat.BOOKSHELF;
    public static final  MossyCobblestoneMat         MOSSY_COBBLESTONE          = MossyCobblestoneMat.MOSSY_COBBLESTONE;
    public static final  ObsidianMat                 OBSIDIAN                   = ObsidianMat.OBSIDIAN;
    public static final  TorchMat                    TORCH                      = TorchMat.TORCH_EAST;
    public static final  FireMat                     FIRE                       = FireMat.FIRE_0;
    public static final  MobSpawnerMat               MOB_SPAWNER                = MobSpawnerMat.MOB_SPAWNER;
    public static final  OakStairsMat                OAK_STAIRS                 = OakStairsMat.OAK_STAIRS_EAST;
    public static final  ChestMat                    CHEST                      = ChestMat.CHEST_NORTH;
    public static final  RedstoneWireMat             REDSTONE_WIRE              = RedstoneWireMat.REDSTONE_WIRE_OFF;
    public static final  DiamondOreMat               DIAMOND_ORE                = DiamondOreMat.DIAMOND_ORE;
    public static final  DiamondBlockMat             DIAMOND_BLOCK              = DiamondBlockMat.DIAMOND_BLOCK;
    public static final  CraftingTableMat            CRAFTING_TABLE             = CraftingTableMat.CRAFTING_TABLE;
    public static final  WheatBlockMat               WHEAT_BLOCK                = WheatBlockMat.WHEAT_BLOCK_0;
    public static final  FarmlandMat                 FARMLAND                   = FarmlandMat.FARMLAND_UNHYDRATED;
    public static final  FurnaceMat                  FURNACE                    = FurnaceMat.FURNACE_NORTH;
    public static final  BurningFurnaceMat           BURNING_FURNACE            = BurningFurnaceMat.BURNING_FURNACE_NORTH;
    public static final  StandingSignMat             STANDING_SIGN              = StandingSignMat.STANDING_SIGN_SOUTH;
    public static final  OakDoorMat                  OAK_DOOR                   = OakDoorMat.OAK_DOOR_BOTTOM_EAST;
    public static final  LadderMat                   LADDER                     = LadderMat.LADDER_NORTH;
    public static final  RailMat                     RAIL                       = RailMat.RAIL_FLAT_NORTH_SOUTH;
    public static final  CobblestoneStairsMat        COBBLESTONE_STAIRS         = CobblestoneStairsMat.COBBLESTONE_STAIRS_EAST;
    public static final  WallSignMat                 WALL_SIGN                  = WallSignMat.WALL_SIGN_NORTH;
    public static final  LeverMat                    LEVER                      = LeverMat.LEVER_DOWN;
    public static final  StonePressurePlateMat       STONE_PRESSURE_PLATE       = StonePressurePlateMat.STONE_PRESSURE_PLATE;
    public static final  IronDoorMat                 IRON_DOOR                  = IronDoorMat.IRON_DOOR_BOTTOM_EAST;
    public static final  WoodenPressurePlateMat      WOODEN_PRESSURE_PLATE      = WoodenPressurePlateMat.WOODEN_PRESSURE_PLATE;
    public static final  RedstoneOreMat              REDSTONE_ORE               = RedstoneOreMat.REDSTONE_ORE;
    public static final  RedstoneOreGlowingMat       REDSTONE_ORE_GLOWING       = RedstoneOreGlowingMat.REDSTONE_ORE_GLOWING;
    public static final  RedstoneTorchOffMat         REDSTONE_TORCH_OFF         = RedstoneTorchOffMat.REDSTONE_TORCH_OFF_WEST;
    public static final  RedstoneTorchOnMat          REDSTONE_TORCH_ON          = RedstoneTorchOnMat.REDSTONE_TORCH_ON_WEST;
    public static final  StoneButtonMat              STONE_BUTTON               = StoneButtonMat.STONE_BUTTON_DOWN;
    public static final  SnowLayerMat                SNOW_LAYER                 = SnowLayerMat.SNOW_LAYER_1;
    public static final  IceMat                      ICE                        = IceMat.ICE;
    public static final  SnowBlockMat                SNOW_BLOCK                 = SnowBlockMat.SNOW_BLOCK;
    public static final  CactusMat                   CACTUS                     = CactusMat.CACTUS_0;
    public static final  ClayBlockMat                CLAY_BLOCK                 = ClayBlockMat.CLAY_BLOCK;
    public static final  SugarCaneMat                SUGAR_CANE                 = SugarCaneMat.SUGAR_CANE_0;
    public static final  JukeboxMat                  JUKEBOX                    = JukeboxMat.JUKEBOX;
    public static final  OakFenceMat                 OAK_FENCE                  = OakFenceMat.OAK_FENCE;
    public static final  PumpkinMat                  PUMPKIN                    = PumpkinMat.PUMPKIN_SOUTH;
    public static final  NetherrackMat               NETHERRACK                 = NetherrackMat.NETHERRACK;
    public static final  SoulSandMat                 SOUL_SAND                  = SoulSandMat.SOUL_SAND;
    public static final  GlowstoneMat                GLOWSTONE                  = GlowstoneMat.GLOWSTONE;
    public static final  NetherPortalMat             NETHER_PORTAL              = NetherPortalMat.NETHER_PORTAL_EAST_WEST;
    public static final  PumpkinLanternMat           PUMPKIN_LANTERN            = PumpkinLanternMat.PUMPKIN_LANTERN_SOUTH;
    public static final  CakeMat                     CAKE                       = CakeMat.CAKE_0;
    public static final  RedstoneRepeaterOffMat      REPEATER_OFF               = RedstoneRepeaterOffMat.REDSTONE_REPEATER_OFF_NORTH_1;
    public static final  RedstoneRepeaterOnMat       REPEATER_ON                = RedstoneRepeaterOnMat.REDSTONE_REPEATER_ON_NORTH_1;
    public static final  StainedGlassMat             STAINED_GLASS              = StainedGlassMat.STAINED_GLASS_WHITE;
    public static final  WoodenTrapdoorMat           WOODEN_TRAPDOOR            = WoodenTrapdoorMat.WOODEN_TRAPDOOR_WEST_BOTTOM;
    public static final  MonsterEggTrapMat           MONSTER_EGG_TRAP           = MonsterEggTrapMat.MONSTER_EGG_TRAP_STONE;
    public static final  StoneBrickMat               STONE_BRICK                = StoneBrickMat.STONE_BRICK;
    public static final  BrownMushroomBlockMat       BROWN_MUSHROOM_BLOCK       = BrownMushroomBlockMat.BROWN_MUSHROOM_BLOCK_PORES_FULL;
    public static final  RedMushroomBlockMat         RED_MUSHROOM_BLOCK         = RedMushroomBlockMat.RED_MUSHROOM_BLOCK_PORES_FULL;
    public static final  IronBarsMat                 IRON_BARS                  = IronBarsMat.IRON_BARS;
    public static final  GlassPaneMat                GLASS_PANE                 = GlassPaneMat.GLASS_PANE;
    public static final  MelonBlockMat               MELON_BLOCK                = MelonBlockMat.MELON_BLOCK;
    public static final  PumpkinStemMat              PUMPKIN_STEM               = PumpkinStemMat.PUMPKIN_STEM_0;
    public static final  MelonStemMat                MELON_STEM                 = MelonStemMat.MELON_STEM_0;
    public static final  VineMat                     VINE                       = VineMat.VINE;
    public static final  OakFenceGateMat             OAK_FENCE_GATE             = OakFenceGateMat.OAK_FENCE_GATE_SOUTH;
    public static final  BrickStairsMat              BRICK_STAIRS               = BrickStairsMat.BRICK_STAIRS_EAST;
    public static final  StoneBrickStairsMat         STONE_BRICK_STAIRS         = StoneBrickStairsMat.STONE_BRICK_STAIRS_EAST;
    public static final  MyceliumMat                 MYCELIUM                   = MyceliumMat.MYCELIUM;
    public static final  WaterLilyMat                WATER_LILY                 = WaterLilyMat.WATER_LILY;
    public static final  NetherBrickMat              NETHER_BRICK               = NetherBrickMat.NETHER_BRICK;
    public static final  NetherBrickFenceMat         NETHER_BRICK_FENCE         = NetherBrickFenceMat.NETHER_BRICK_FENCE;
    public static final  NetherBrickStairsMat        NETHER_BRICK_STAIRS        = NetherBrickStairsMat.NETHER_BRICK_STAIRS_EAST;
    public static final  NetherWartBlockMat          NETHER_WART_BLOCK          = NetherWartBlockMat.NETHER_WART_BLOCK_0;
    public static final  EnchantingTableMat          ENCHANTING_TABLE           = EnchantingTableMat.ENCHANTING_TABLE;
    public static final  BrewingStandBlockMat        BREWING_STAND_BLOCK        = BrewingStandBlockMat.BREWING_STAND_BLOCK_EMPTY;
    public static final  CauldronMat                 CAULDRON                   = CauldronMat.CAULDRON_EMPTY;
    public static final  EndPortalMat                END_PORTAL                 = EndPortalMat.END_PORTAL;
    public static final  EndPortalFrameMat           END_PORTAL_FRAME           = EndPortalFrameMat.END_PORTAL_FRAME_SOUTH;
    public static final  EndStoneMat                 END_STONE                  = EndStoneMat.END_STONE;
    public static final  DragonEggMat                DRAGON_EGG                 = DragonEggMat.DRAGON_EGG;
    public static final  RedstoneLampOffMat          REDSTONE_LAMP_OFF          = RedstoneLampOffMat.REDSTONE_LAMP_OFF;
    public static final  RedstoneLampOnMat           REDSTONE_LAMP_ON           = RedstoneLampOnMat.REDSTONE_LAMP_ON;
    public static final  DoubleWoodenSlabMat         DOUBLE_WOODEN_SLAB         = DoubleWoodenSlabMat.DOUBLE_WOODEN_SLAB_OAK;
    public static final  WoodenSlabMat               WOODEN_SLAB                = WoodenSlabMat.WOODEN_SLAB_OAK;
    public static final  CocoaMat                    COCOA                      = CocoaMat.COCOA_NORTH_0;
    public static final  SandstoneStairsMat          SANDSTONE_STAIRS           = SandstoneStairsMat.SANDSTONE_STAIRS_EAST;
    public static final  EmeraldOreMat               EMERALD_ORE                = EmeraldOreMat.EMERALD_ORE;
    public static final  EnderChestMat               ENDER_CHEST                = EnderChestMat.ENDER_CHEST_NORTH;
    public static final  TripwireHookMat             TRIPWIRE_HOOK              = TripwireHookMat.TRIPWIRE_HOOK_SOUTH;
    public static final  TripwireMat                 TRIPWIRE                   = TripwireMat.TRIPWIRE;
    public static final  EmeraldBlockMat             EMERALD_BLOCK              = EmeraldBlockMat.EMERALD_BLOCK;
    public static final  SpruceStairsMat             SPRUCE_STAIRS              = SpruceStairsMat.SPRUCE_STAIRS_EAST;
    public static final  BirchStairsMat              BIRCH_STAIRS               = BirchStairsMat.BIRCH_STAIRS_EAST;
    public static final  JungleStairsMat             JUNGLE_STAIRS              = JungleStairsMat.JUNGLE_STAIRS_EAST;
    public static final  CommandBlockMat             COMMAND_BLOCK              = CommandBlockMat.COMMAND_BLOCK;
    public static final  BeaconMat                   BEACON                     = BeaconMat.BEACON;
    public static final  CobblestoneWallMat          COBBLESTONE_WALL           = CobblestoneWallMat.COBBLESTONE_WALL;
    public static final  FlowerPotMat                FLOWER_POT                 = FlowerPotMat.FLOWER_POT_EMPTY;
    public static final  CarrotsBlockMat             CARROTS_BLOCK              = CarrotsBlockMat.CARROTS_BLOCK_0;
    public static final  PotatoesBlockMat            POTATOES_BLOCK             = PotatoesBlockMat.POTATOES_BLOCK_0;
    public static final  WoodenButtonMat             WOODEN_BUTTON              = WoodenButtonMat.WOODEN_BUTTON_DOWN;
    public static final  SkullBlockMat               SKULL_BLOCK                = SkullBlockMat.SKULL_BLOCK_FLOOR;
    public static final  AnvilMat                    ANVIL                      = AnvilMat.ANVIL_NORTH_SOUTH_NEW;
    public static final  TrappedChestMat             TRAPPED_CHEST              = TrappedChestMat.TRAPPED_CHEST_NORTH;
    public static final  GoldenPressurePlateMat      GOLDEN_PRESSURE_PLATE      = GoldenPressurePlateMat.GOLDEN_PRESSURE_PLATE_0;
    public static final  IronPressurePlateMat        IRON_PRESSURE_PLATE        = IronPressurePlateMat.IRON_PRESSURE_PLATE_0;
    public static final  RedstoneComparatorMat       REDSTONE_COMPARATOR        = RedstoneComparatorMat.REDSTONE_COMPARATOR_NORTH;
    public static final  DaylightDetectorMat         DAYLIGHT_DETECTOR          = DaylightDetectorMat.DAYLIGHT_DETECTOR_OFF;
    public static final  RedstoneBlockMat            REDSTONE_BLOCK             = RedstoneBlockMat.REDSTONE_BLOCK;
    public static final  QuartzOreMat                QUARTZ_ORE                 = QuartzOreMat.QUARTZ_ORE;
    public static final  HopperMat                   HOPPER                     = HopperMat.HOPPER_DOWN;
    public static final  QuartzBlockMat              QUARTZ_BLOCK               = QuartzBlockMat.QUARTZ_BLOCK;
    public static final  QuartzStairsMat             QUARTZ_STAIRS              = QuartzStairsMat.QUARTZ_STAIRS_EAST;
    public static final  ActivatorRailMat            ACTIVATOR_RAIL             = ActivatorRailMat.ACTIVATOR_RAIL_NORTH_SOUTH;
    public static final  DropperMat                  DROPPER                    = DropperMat.DROPPER_DOWN;
    public static final  StainedHardenedClayMat      STAINED_HARDENED_CLAY      = StainedHardenedClayMat.STAINED_HARDENED_CLAY_WHITE;
    public static final  StainedGlassPaneMat         STAINED_GLASS_PANE         = StainedGlassPaneMat.STAINED_GLASS_PANE_WHITE;
    public static final  AcaciaStairsMat             ACACIA_STAIRS              = AcaciaStairsMat.ACACIA_STAIRS_EAST;
    public static final  DarkOakStairsMat            DARK_OAK_STAIRS            = DarkOakStairsMat.DARK_OAK_STAIRS_EAST;
    public static final  SlimeBlockMat               SLIME_BLOCK                = SlimeBlockMat.SLIME_BLOCK;
    public static final  BarrierMat                  BARRIER                    = BarrierMat.BARRIER;
    public static final  IronTrapdoorMat             IRON_TRAPDOOR              = IronTrapdoorMat.IRON_TRAPDOOR_WEST_BOTTOM;
    public static final  PrismarineMat               PRISMARINE                 = PrismarineMat.PRISMARINE;
    public static final  SeaLantrenMat               SEA_LANTERN                = SeaLantrenMat.SEA_LANTREN;
    public static final  HayBlockMat                 HAY_BLOCK                  = HayBlockMat.HAY_BLOCK_UP_DOWN;
    public static final  CarpetMat                   CARPET                     = CarpetMat.CARPET_WHITE;
    public static final  HardenedClayMat             HARDENED_CLAY              = HardenedClayMat.HARDENED_CLAY;
    public static final  CoalBlockMat                COAL_BLOCK                 = CoalBlockMat.COAL_BLOCK;
    public static final  PackedIceMat                PACKED_ICE                 = PackedIceMat.PACKED_ICE;
    public static final  DoubleFlowersMat            DOUBLE_FLOWERS             = DoubleFlowersMat.DOUBLE_FLOWERS_SUNFLOWER;
    public static final  StandingBannerMat           STANDING_BANNER            = StandingBannerMat.STANDING_BANNER_SOUTH;
    public static final  WallBannerMat               WALL_BANNER                = WallBannerMat.WALL_BANNER_NORTH;
    public static final  DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED = DaylightDetectorInvertedMat.DAYLIGHT_DETECTOR_INVERTED_OFF;
    public static final  RedSandstoneMat             RED_SANDSTONE              = RedSandstoneMat.RED_SANDSTONE;
    public static final  RedSandstoneStairsMat       RED_SANDSTONE_STAIRS       = RedSandstoneStairsMat.RED_SANDSTONE_STAIRS_EAST;
    public static final  SpruceFenceGateMat          SPRUCE_FENCE_GATE          = SpruceFenceGateMat.SPRUCE_FENCE_GATE_SOUTH;
    public static final  BirchFenceGateMat           BIRCH_FENCE_GATE           = BirchFenceGateMat.BIRCH_FENCE_GATE_SOUTH;
    public static final  JungleFenceGateMat          JUNGLE_FENCE_GATE          = JungleFenceGateMat.JUNGLE_FENCE_GATE_SOUTH;
    public static final  DarkOakFenceGateMat         DARK_OAK_FENCE_GATE        = DarkOakFenceGateMat.DARK_OAK_FENCE_GATE_SOUTH;
    public static final  AcaciaFenceGateMat          ACACIA_FENCE_GATE          = AcaciaFenceGateMat.ACACIA_FENCE_GATE_SOUTH;
    public static final  SpruceFenceMat              SPRUCE_FENCE               = SpruceFenceMat.SPRUCE_FENCE;
    public static final  BirchFenceMat               BIRCH_FENCE                = BirchFenceMat.BRICH_FENCE;
    public static final  JungleFenceMat              JUNGLE_FENCE               = JungleFenceMat.JUNGLE_FENCE;
    public static final  DarkOakFenceMat             DARK_OAK_FENCE             = DarkOakFenceMat.DARK_OAK_FENCE;
    public static final  AcaciaFenceMat              ACACIA_FENCE               = AcaciaFenceMat.ACACIA_FENCE;
    public static final  SpruceDoorMat               SPRUCE_DOOR                = SpruceDoorMat.SPRUCE_DOOR_BOTTOM_EAST;
    public static final  BirchDoorMat                BIRCH_DOOR                 = BirchDoorMat.BIRCH_DOOR_BOTTOM_EAST;
    public static final  JungleDoorMat               JUNGLE_DOOR                = JungleDoorMat.JUNGLE_DOOR_BOTTOM_EAST;
    public static final  AcaciaDoorMat               ACACIA_DOOR                = AcaciaDoorMat.ACACIA_DOOR_BOTTOM_EAST;
    public static final  DarkOakDoorMat              DARK_OAK_DOOR              = DarkOakDoorMat.DARK_OAK_DOOR_BOTTOM_EAST;
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
//    public static final  Material                SUGAR_CANE_0           = new Material("SUGAR_CANE_0", 338);
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
//    public static final  Material                REDSTONE_COMPARATOR_NORTH  = new Material("REDSTONE_COMPARATOR_NORTH", 404);
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
    private static final Map<String, Material>       byName                     = new CaseInsensitiveMap<>(MATERIALS_SIZE, SMALL_LOAD_FACTOR);
    private static final Map<String, Material>       byMinecraftId              = new CaseInsensitiveMap<>(MATERIALS_SIZE, SMALL_LOAD_FACTOR);
    private static final TIntObjectMap<Material>     byID                       = new TIntObjectHashMap<>(MATERIALS_SIZE, SMALL_LOAD_FACTOR);
    private final   String enumName;
    protected final int    id;
    protected final String minecraftId;
    protected final int    maxStack;
    protected final short  type;

    protected Material(final String enumName, final int id, final String minecraftId, final short type)
    {
        this(enumName, id, minecraftId, MagicNumbers.ITEMS__DEFAULT_STACK_SIZE, type);
    }

    protected Material(final String enumName, final int id, final String minecraftId, final int maxStack, final short type)
    {
        this.enumName = enumName;
        this.id = id;
        this.minecraftId = minecraftId;
        this.maxStack = maxStack;
        this.type = type;
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

    /**
     * @return sub-id of material, for blocks is in 0-15 range, for items it use 2 bytes
     */
    public short getType()
    {
        return this.type;
    }

    /**
     * @return vanilla minecraft id, fro compatybility.
     */
    public String getMinecraftId()
    {
        return this.minecraftId;
    }

    /**
     * @return max size of itemstack, client can hold up to 100 items in one slot, but prefered size is 64.
     */
    public int getMaxStack()
    {
        return this.maxStack;
    }

    /**
     * @return if material is block-type.
     */
    public abstract boolean isBlock();

    public abstract float getBlastResistance();

    public abstract float getHardness();

    public abstract boolean isSolid();

    /*
     * ==========================================
     * May by replaced with interaction pipeline
     * or some property map.
     * ==========================================
     *
     * public abstract boolean isTransparent();
     *
     * public abstract boolean isFlammable();
     *
     * public abstract boolean isBurnable();
     *
     * public abstract boolean isOccluding();
     *
     * public abstract boolean hasGravity();
     *
     * public abstract boolean isEdible();
     *
     * public abstract boolean isReplaceable();
     *
     * public abstract boolean isGlowing();
     *
     * public abstract int getLuminance();
     *
     * public abstract IntRange getExperienceWhenMined();
     *
     * ==========================================
     * May by replaced with interaction pipeline
     * or some property map.
     * ==========================================
     */

    // TODO: drop pipeline?

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

    /**
     * @return all values in array.
     */
    public static Material[] values()
    {
        return byID.values(new Material[byID.size()]);
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
        register(LeavesMat.LEAVES_ACACIA);
        register(LogMat.LOG_ACACIA);
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
//        register(SUGAR_CANE_0);
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
//        register(REDSTONE_COMPARATOR_NORTH);
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
}
