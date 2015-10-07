package org.diorite.material;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import org.diorite.material.blocks.others.CakeBlockMat;
import org.diorite.material.blocks.others.CarpetMat;
import org.diorite.material.blocks.others.FireMat;
import org.diorite.material.blocks.others.FlowerPotBlockMat;
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
import org.diorite.material.blocks.plants.ReedsBlockMat;
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
import org.diorite.material.blocks.tools.CauldronBlockMat;
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
import org.diorite.material.items.block.BannerMat;
import org.diorite.material.items.block.BedMat;
import org.diorite.material.items.block.BrewingStandMat;
import org.diorite.material.items.block.CakeMat;
import org.diorite.material.items.block.CauldronMat;
import org.diorite.material.items.block.FlowerPotMat;
import org.diorite.material.items.block.MelonSeedsMat;
import org.diorite.material.items.block.NetherWartMat;
import org.diorite.material.items.block.PumpkinSeedsMat;
import org.diorite.material.items.block.RedstoneComparatorItemMat;
import org.diorite.material.items.block.RedstoneMat;
import org.diorite.material.items.block.RedstoneRepeaterItemMat;
import org.diorite.material.items.block.ReedsMat;
import org.diorite.material.items.block.SignMat;
import org.diorite.material.items.block.StringMat;
import org.diorite.material.items.block.WheatSeedsMat;
import org.diorite.material.items.block.door.AcaciaDoorItemMat;
import org.diorite.material.items.block.door.BirchDoorItemMat;
import org.diorite.material.items.block.door.DarkOakDoorItemMat;
import org.diorite.material.items.block.door.IronDoorItemMat;
import org.diorite.material.items.block.door.JungleDoorItemMat;
import org.diorite.material.items.block.door.OakDoorItemMat;
import org.diorite.material.items.block.door.SpruceDoorItemMat;
import org.diorite.material.items.entity.ArmorStandMat;
import org.diorite.material.items.entity.boat.BoatMat;
import org.diorite.material.items.entity.EggMat;
import org.diorite.material.items.entity.EnderEyeMat;
import org.diorite.material.items.entity.EnderPearlMat;
import org.diorite.material.items.entity.ExperienceBottleMat;
import org.diorite.material.items.entity.FireworksMat;
import org.diorite.material.items.entity.ItemFrameMat;
import org.diorite.material.items.entity.LeadMat;
import org.diorite.material.items.entity.PaintingMat;
import org.diorite.material.items.entity.SnowballMat;
import org.diorite.material.items.entity.minecart.ChestMinecartMat;
import org.diorite.material.items.entity.minecart.CommandBlockMinecartMat;
import org.diorite.material.items.entity.minecart.FurnaceMinecartMat;
import org.diorite.material.items.entity.minecart.HopperMinecartMat;
import org.diorite.material.items.entity.minecart.MinecartMat;
import org.diorite.material.items.entity.minecart.TntMinecartMat;
import org.diorite.material.items.food.AppleMat;
import org.diorite.material.items.food.BakedPotatoMat;
import org.diorite.material.items.food.BeefMat;
import org.diorite.material.items.food.BreadMat;
import org.diorite.material.items.food.CarrotMat;
import org.diorite.material.items.food.ChickenMat;
import org.diorite.material.items.food.CookedBeefMat;
import org.diorite.material.items.food.CookedChickenMat;
import org.diorite.material.items.food.CookedFishMat;
import org.diorite.material.items.food.CookedMuttonMat;
import org.diorite.material.items.food.CookedPorkchopMat;
import org.diorite.material.items.food.CookedRabbitMat;
import org.diorite.material.items.food.CookieMat;
import org.diorite.material.items.food.FishMat;
import org.diorite.material.items.food.GoldenAppleMat;
import org.diorite.material.items.food.GoldenCarrotMat;
import org.diorite.material.items.food.MelonMat;
import org.diorite.material.items.food.MilkBucketMat;
import org.diorite.material.items.food.MushroomStewMat;
import org.diorite.material.items.food.MuttonMat;
import org.diorite.material.items.food.PoisonousPotatoMat;
import org.diorite.material.items.food.PorkchopMat;
import org.diorite.material.items.food.PotatoMat;
import org.diorite.material.items.food.PumpkinPieMat;
import org.diorite.material.items.food.RabbitMat;
import org.diorite.material.items.food.RabbitStewMat;
import org.diorite.material.items.food.RottenFleshMat;
import org.diorite.material.items.food.SpiderEyeMat;
import org.diorite.material.items.mob.BlazeRodMat;
import org.diorite.material.items.mob.BoneMat;
import org.diorite.material.items.mob.FeatherMat;
import org.diorite.material.items.mob.GhastTearMat;
import org.diorite.material.items.mob.GunpowderMat;
import org.diorite.material.items.mob.LeatherMat;
import org.diorite.material.items.mob.MagmaCreamMat;
import org.diorite.material.items.mob.NetherStarMat;
import org.diorite.material.items.mob.PrismarineCrystalsMat;
import org.diorite.material.items.mob.PrismarineShardMat;
import org.diorite.material.items.mob.RabbitFootMat;
import org.diorite.material.items.mob.RabbitHideMat;
import org.diorite.material.items.mob.SkullMat;
import org.diorite.material.items.mob.SlimeballMat;
import org.diorite.material.items.mob.SpawnEggMat;
import org.diorite.material.items.mob.tool.DiamondHorseArmorMat;
import org.diorite.material.items.mob.tool.GoldenHorseArmorMat;
import org.diorite.material.items.mob.tool.IronHorseArmorMat;
import org.diorite.material.items.mob.tool.NameTagMat;
import org.diorite.material.items.mob.tool.SaddleMat;
import org.diorite.material.items.others.BlazePowderMat;
import org.diorite.material.items.others.BookMat;
import org.diorite.material.items.others.BrickMat;
import org.diorite.material.items.others.ClayBallMat;
import org.diorite.material.items.others.CoalMat;
import org.diorite.material.items.others.DiamondMat;
import org.diorite.material.items.others.DyeMat;
import org.diorite.material.items.others.EmeraldMat;
import org.diorite.material.items.others.FermentedSpiderEyeMat;
import org.diorite.material.items.others.FilledMapMat;
import org.diorite.material.items.others.FireworkChargeMat;
import org.diorite.material.items.others.FlintMat;
import org.diorite.material.items.others.GlowstoneDustMat;
import org.diorite.material.items.others.GoldIngotMat;
import org.diorite.material.items.others.GoldNuggetMat;
import org.diorite.material.items.others.IronIngotMat;
import org.diorite.material.items.others.NetherBrickItemMat;
import org.diorite.material.items.others.PaperMat;
import org.diorite.material.items.others.QuartzMat;
import org.diorite.material.items.others.SpeckledMelonMat;
import org.diorite.material.items.others.StickMat;
import org.diorite.material.items.others.SugarMat;
import org.diorite.material.items.others.WheatMat;
import org.diorite.material.items.record.Record11Mat;
import org.diorite.material.items.record.Record13Mat;
import org.diorite.material.items.record.RecordBlocksMat;
import org.diorite.material.items.record.RecordCatMat;
import org.diorite.material.items.record.RecordChripMat;
import org.diorite.material.items.record.RecordFarMat;
import org.diorite.material.items.record.RecordMallMat;
import org.diorite.material.items.record.RecordMellohiMat;
import org.diorite.material.items.record.RecordStalMat;
import org.diorite.material.items.record.RecordStradMat;
import org.diorite.material.items.record.RecordWaitMat;
import org.diorite.material.items.record.RecordWardMat;
import org.diorite.material.items.tool.armor.ChainmailBootsMat;
import org.diorite.material.items.tool.armor.ChainmailChestplateMat;
import org.diorite.material.items.tool.armor.ChainmailHelmetMat;
import org.diorite.material.items.tool.armor.ChainmailLeggingsMat;
import org.diorite.material.items.tool.armor.DiamondBootsMat;
import org.diorite.material.items.tool.armor.DiamondChestplateMat;
import org.diorite.material.items.tool.armor.DiamondHelmetMat;
import org.diorite.material.items.tool.armor.DiamondLeggingsMat;
import org.diorite.material.items.tool.armor.GoldBootsMat;
import org.diorite.material.items.tool.armor.GoldChestplateMat;
import org.diorite.material.items.tool.armor.GoldHelmetMat;
import org.diorite.material.items.tool.armor.GoldLeggingsMat;
import org.diorite.material.items.tool.armor.IronBootsMat;
import org.diorite.material.items.tool.armor.IronChestplateMat;
import org.diorite.material.items.tool.armor.IronHelmetMat;
import org.diorite.material.items.tool.armor.IronLeggingsMat;
import org.diorite.material.items.tool.armor.LeatherBootsMat;
import org.diorite.material.items.tool.armor.LeatherChestplateMat;
import org.diorite.material.items.tool.armor.LeatherHelmetMat;
import org.diorite.material.items.tool.armor.LeatherLeggingsMat;
import org.diorite.material.items.tool.simple.ArrowMat;
import org.diorite.material.items.tool.simple.BowlMat;
import org.diorite.material.items.tool.simple.BucketMat;
import org.diorite.material.items.tool.simple.ClockMat;
import org.diorite.material.items.tool.simple.CompassMat;
import org.diorite.material.items.tool.simple.EnchantedBookMat;
import org.diorite.material.items.tool.simple.FireChargeMat;
import org.diorite.material.items.tool.simple.GlassBottleMat;
import org.diorite.material.items.tool.simple.LavaBucketMat;
import org.diorite.material.items.tool.simple.MapMat;
import org.diorite.material.items.tool.simple.WaterBucketMat;
import org.diorite.material.items.tool.simple.WritableBookMat;
import org.diorite.material.items.tool.simple.WrittenBookMat;
import org.diorite.utils.SimpleEnum;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;
import org.diorite.utils.math.DioriteMathUtils;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

public abstract class Material implements SimpleEnum<Material>
{
    public static final int MATERIALS_SIZE = 385;

    public static final AirMat                      AIR                        = AirMat.AIR;
    public static final StoneMat                    STONE                      = StoneMat.STONE;
    public static final GrassMat                    GRASS                      = GrassMat.GRASS;
    public static final DirtMat                     DIRT                       = DirtMat.DIRT;
    public static final CobblestoneMat              COBBLESTONE                = CobblestoneMat.COBBLESTONE;
    public static final PlanksMat                   PLANKS                     = PlanksMat.PLANKS_OAK;
    public static final SaplingMat                  SAPLING                    = SaplingMat.SAPLING_OAK;
    public static final BedrockMat                  BEDROCK                    = BedrockMat.BEDROCK;
    public static final WaterMat                    WATER                      = WaterMat.WATER_SOURCE;
    public static final WaterMat                    WATER_STILL                = WaterMat.WATER_SOURCE_STILL;
    public static final LavaMat                     LAVA                       = LavaMat.LAVA_SOURCE;
    public static final LavaMat                     LAVA_STILL                 = LavaMat.LAVA_SOURCE_STILL;
    public static final SandMat                     SAND                       = SandMat.SAND;
    public static final GravelMat                   GRAVEL                     = GravelMat.GRAVEL;
    public static final GoldOreMat                  GOLD_ORE                   = GoldOreMat.GOLD_ORE;
    public static final IronOreMat                  IRON_ORE                   = IronOreMat.IRON_ORE;
    public static final CoalOreMat                  COAL_ORE                   = CoalOreMat.COAL_ORE;
    public static final LogMat                      LOG                        = LogMat.LOG_OAK;
    public static final LeavesMat                   LEAVES                     = LeavesMat.LEAVES_OAK;
    public static final SpongeMat                   SPONGE                     = SpongeMat.SPONGE;
    public static final GlassMat                    GLASS                      = GlassMat.GLASS;
    public static final LapisOreMat                 LAPIS_ORE                  = LapisOreMat.LAPIS_ORE;
    public static final LapisBlockMat               LAPIS_BLOCK                = LapisBlockMat.LAPIS_BLOCK;
    public static final DispenserMat                DISPENSER                  = DispenserMat.DISPENSER_DOWN;
    public static final SandstoneMat                SANDSTONE                  = SandstoneMat.SANDSTONE;
    public static final NoteBlockMat                NOTEBLOCK                  = NoteBlockMat.NOTEBLOCK;
    public static final BedBlockMat                 BED_BLOCK                  = BedBlockMat.BED_FOOT_SOUTH;
    public static final PoweredRailMat              POWERED_RAIL               = PoweredRailMat.POWERED_RAIL_NORTH_SOUTH;
    public static final DetectorRailMat             DETECTOR_RAIL              = DetectorRailMat.DETECTOR_RAIL_NORTH_SOUTH;
    public static final PistonStickyMat             PISTON_STICKY              = PistonStickyMat.STICKY_PISTON_DOWN;
    public static final CobwebMat                   WEB                        = CobwebMat.COBWEB;
    public static final TallGrassMat                TALL_GRASS                 = TallGrassMat.TALL_GRASS_SHRUB;
    public static final DeadBushMat                 DEAD_BUSH                  = DeadBushMat.DEAD_BUSH;
    public static final PistonMat                   PISTON                     = PistonMat.PISTON_DOWN;
    public static final PistonExtensionMat          PISTON_EXTENSION           = PistonExtensionMat.PISTON_EXTENSION_DOWN;
    public static final WoolMat                     WOOL                       = WoolMat.WOOL_WHITE;
    public static final PistonHeadMat               PISTON_HEAD                = PistonHeadMat.PISTON_HEAD_DOWN;
    public static final DandelionMat                DANDELION                  = DandelionMat.DANDELION;
    public static final FlowersMat                  FLOWERS                    = FlowersMat.FLOWERS_POPPY;
    public static final MushroomBrownMat            BROWN_MUSHROOM             = MushroomBrownMat.BROWN_MUSHROOM;
    public static final MushroomRedMat              RED_MUSHROOM               = MushroomRedMat.RED_MUSHROOM;
    public static final GoldBlockMat                GOLD_BLOCK                 = GoldBlockMat.GOLD_BLOCK;
    public static final IronBlockMat                IRON_BLOCK                 = IronBlockMat.IRON_BLOCK;
    public static final DoubleStoneSlabMat          DOUBLE_STONE_SLAB          = DoubleStoneSlabMat.DOUBLE_STONE_SLAB_STONE;
    public static final StoneSlabMat                STONE_SLAB                 = StoneSlabMat.STONE_SLAB_STONE;
    public static final BrickBlockMat               BRICK_BLOCK                = BrickBlockMat.BRICK_BLOCK;
    public static final TntMat                      TNT                        = TntMat.TNT;
    public static final BookshelfMat                BOOKSHELF                  = BookshelfMat.BOOKSHELF;
    public static final MossyCobblestoneMat         MOSSY_COBBLESTONE          = MossyCobblestoneMat.MOSSY_COBBLESTONE;
    public static final ObsidianMat                 OBSIDIAN                   = ObsidianMat.OBSIDIAN;
    public static final TorchMat                    TORCH                      = TorchMat.TORCH_EAST;
    public static final FireMat                     FIRE                       = FireMat.FIRE_0;
    public static final MobSpawnerMat               MOB_SPAWNER                = MobSpawnerMat.MOB_SPAWNER;
    public static final OakStairsMat                OAK_STAIRS                 = OakStairsMat.OAK_STAIRS_EAST;
    public static final ChestMat                    CHEST                      = ChestMat.CHEST_NORTH;
    public static final RedstoneWireMat             REDSTONE_WIRE              = RedstoneWireMat.REDSTONE_WIRE_OFF;
    public static final DiamondOreMat               DIAMOND_ORE                = DiamondOreMat.DIAMOND_ORE;
    public static final DiamondBlockMat             DIAMOND_BLOCK              = DiamondBlockMat.DIAMOND_BLOCK;
    public static final CraftingTableMat            CRAFTING_TABLE             = CraftingTableMat.CRAFTING_TABLE;
    public static final WheatBlockMat               WHEAT_BLOCK                = WheatBlockMat.WHEAT_BLOCK_0;
    public static final FarmlandMat                 FARMLAND                   = FarmlandMat.FARMLAND_UNHYDRATED;
    public static final FurnaceMat                  FURNACE                    = FurnaceMat.FURNACE_NORTH;
    public static final BurningFurnaceMat           BURNING_FURNACE            = BurningFurnaceMat.BURNING_FURNACE_NORTH;
    public static final StandingSignMat             STANDING_SIGN              = StandingSignMat.STANDING_SIGN_SOUTH;
    public static final OakDoorMat                  OAK_DOOR                   = OakDoorMat.OAK_DOOR_BOTTOM_EAST;
    public static final LadderMat                   LADDER                     = LadderMat.LADDER_NORTH;
    public static final RailMat                     RAIL                       = RailMat.RAIL_FLAT_NORTH_SOUTH;
    public static final CobblestoneStairsMat        COBBLESTONE_STAIRS         = CobblestoneStairsMat.COBBLESTONE_STAIRS_EAST;
    public static final WallSignMat                 WALL_SIGN                  = WallSignMat.WALL_SIGN_NORTH;
    public static final LeverMat                    LEVER                      = LeverMat.LEVER_DOWN;
    public static final StonePressurePlateMat       STONE_PRESSURE_PLATE       = StonePressurePlateMat.STONE_PRESSURE_PLATE;
    public static final IronDoorMat                 IRON_DOOR                  = IronDoorMat.IRON_DOOR_BOTTOM_EAST;
    public static final WoodenPressurePlateMat      WOODEN_PRESSURE_PLATE      = WoodenPressurePlateMat.WOODEN_PRESSURE_PLATE;
    public static final RedstoneOreMat              REDSTONE_ORE               = RedstoneOreMat.REDSTONE_ORE;
    public static final RedstoneOreGlowingMat       REDSTONE_ORE_GLOWING       = RedstoneOreGlowingMat.REDSTONE_ORE_GLOWING;
    public static final RedstoneTorchOffMat         REDSTONE_TORCH_OFF         = RedstoneTorchOffMat.REDSTONE_TORCH_OFF_WEST;
    public static final RedstoneTorchOnMat          REDSTONE_TORCH_ON          = RedstoneTorchOnMat.REDSTONE_TORCH_ON_WEST;
    public static final StoneButtonMat              STONE_BUTTON               = StoneButtonMat.STONE_BUTTON_DOWN;
    public static final SnowLayerMat                SNOW_LAYER                 = SnowLayerMat.SNOW_LAYER_1;
    public static final IceMat                      ICE                        = IceMat.ICE;
    public static final SnowBlockMat                SNOW_BLOCK                 = SnowBlockMat.SNOW_BLOCK;
    public static final CactusMat                   CACTUS                     = CactusMat.CACTUS_0;
    public static final ClayBlockMat                CLAY_BLOCK                 = ClayBlockMat.CLAY_BLOCK;
    public static final ReedsBlockMat               REEDS_BLOCK                = ReedsBlockMat.REEDS_BLOCK_0;
    public static final JukeboxMat                  JUKEBOX                    = JukeboxMat.JUKEBOX;
    public static final OakFenceMat                 OAK_FENCE                  = OakFenceMat.OAK_FENCE;
    public static final PumpkinMat                  PUMPKIN                    = PumpkinMat.PUMPKIN_SOUTH;
    public static final NetherrackMat               NETHERRACK                 = NetherrackMat.NETHERRACK;
    public static final SoulSandMat                 SOUL_SAND                  = SoulSandMat.SOUL_SAND;
    public static final GlowstoneMat                GLOWSTONE                  = GlowstoneMat.GLOWSTONE;
    public static final NetherPortalMat             NETHER_PORTAL              = NetherPortalMat.NETHER_PORTAL_EAST_WEST;
    public static final PumpkinLanternMat           PUMPKIN_LANTERN            = PumpkinLanternMat.PUMPKIN_LANTERN_SOUTH;
    public static final CakeBlockMat                CAKE_BLOCK                 = CakeBlockMat.CAKE_BLOCK_0;
    public static final RedstoneRepeaterOffMat      REPEATER_OFF               = RedstoneRepeaterOffMat.REDSTONE_REPEATER_OFF_NORTH_1;
    public static final RedstoneRepeaterOnMat       REPEATER_ON                = RedstoneRepeaterOnMat.REDSTONE_REPEATER_ON_NORTH_1;
    public static final StainedGlassMat             STAINED_GLASS              = StainedGlassMat.STAINED_GLASS_WHITE;
    public static final WoodenTrapdoorMat           WOODEN_TRAPDOOR            = WoodenTrapdoorMat.WOODEN_TRAPDOOR_WEST_BOTTOM;
    public static final MonsterEggTrapMat           MONSTER_EGG_TRAP           = MonsterEggTrapMat.MONSTER_EGG_TRAP_STONE;
    public static final StoneBrickMat               STONE_BRICK                = StoneBrickMat.STONE_BRICK;
    public static final BrownMushroomBlockMat       BROWN_MUSHROOM_BLOCK       = BrownMushroomBlockMat.BROWN_MUSHROOM_BLOCK_PORES_FULL;
    public static final RedMushroomBlockMat         RED_MUSHROOM_BLOCK         = RedMushroomBlockMat.RED_MUSHROOM_BLOCK_PORES_FULL;
    public static final IronBarsMat                 IRON_BARS                  = IronBarsMat.IRON_BARS;
    public static final GlassPaneMat                GLASS_PANE                 = GlassPaneMat.GLASS_PANE;
    public static final MelonBlockMat               MELON_BLOCK                = MelonBlockMat.MELON_BLOCK;
    public static final PumpkinStemMat              PUMPKIN_STEM               = PumpkinStemMat.PUMPKIN_STEM_0;
    public static final MelonStemMat                MELON_STEM                 = MelonStemMat.MELON_STEM_0;
    public static final VineMat                     VINE                       = VineMat.VINE;
    public static final OakFenceGateMat             OAK_FENCE_GATE             = OakFenceGateMat.OAK_FENCE_GATE_SOUTH;
    public static final BrickStairsMat              BRICK_STAIRS               = BrickStairsMat.BRICK_STAIRS_EAST;
    public static final StoneBrickStairsMat         STONE_BRICK_STAIRS         = StoneBrickStairsMat.STONE_BRICK_STAIRS_EAST;
    public static final MyceliumMat                 MYCELIUM                   = MyceliumMat.MYCELIUM;
    public static final WaterLilyMat                WATER_LILY                 = WaterLilyMat.WATER_LILY;
    public static final NetherBrickMat              NETHER_BRICK               = NetherBrickMat.NETHER_BRICK;
    public static final NetherBrickFenceMat         NETHER_BRICK_FENCE         = NetherBrickFenceMat.NETHER_BRICK_FENCE;
    public static final NetherBrickStairsMat        NETHER_BRICK_STAIRS        = NetherBrickStairsMat.NETHER_BRICK_STAIRS_EAST;
    public static final NetherWartBlockMat          NETHER_WART_BLOCK          = NetherWartBlockMat.NETHER_WART_BLOCK_0;
    public static final EnchantingTableMat          ENCHANTING_TABLE           = EnchantingTableMat.ENCHANTING_TABLE;
    public static final BrewingStandBlockMat        BREWING_STAND_BLOCK        = BrewingStandBlockMat.BREWING_STAND_BLOCK_EMPTY;
    public static final CauldronBlockMat            CAULDRON_BLOCK             = CauldronBlockMat.CAULDRON_BLOCK_EMPTY;
    public static final EndPortalMat                END_PORTAL                 = EndPortalMat.END_PORTAL;
    public static final EndPortalFrameMat           END_PORTAL_FRAME           = EndPortalFrameMat.END_PORTAL_FRAME_SOUTH;
    public static final EndStoneMat                 END_STONE                  = EndStoneMat.END_STONE;
    public static final DragonEggMat                DRAGON_EGG                 = DragonEggMat.DRAGON_EGG;
    public static final RedstoneLampOffMat          REDSTONE_LAMP_OFF          = RedstoneLampOffMat.REDSTONE_LAMP_OFF;
    public static final RedstoneLampOnMat           REDSTONE_LAMP_ON           = RedstoneLampOnMat.REDSTONE_LAMP_ON;
    public static final DoubleWoodenSlabMat         DOUBLE_WOODEN_SLAB         = DoubleWoodenSlabMat.DOUBLE_WOODEN_SLAB_OAK;
    public static final WoodenSlabMat               WOODEN_SLAB                = WoodenSlabMat.WOODEN_SLAB_OAK;
    public static final CocoaMat                    COCOA                      = CocoaMat.COCOA_NORTH_0;
    public static final SandstoneStairsMat          SANDSTONE_STAIRS           = SandstoneStairsMat.SANDSTONE_STAIRS_EAST;
    public static final EmeraldOreMat               EMERALD_ORE                = EmeraldOreMat.EMERALD_ORE;
    public static final EnderChestMat               ENDER_CHEST                = EnderChestMat.ENDER_CHEST_NORTH;
    public static final TripwireHookMat             TRIPWIRE_HOOK              = TripwireHookMat.TRIPWIRE_HOOK_SOUTH;
    public static final TripwireMat                 TRIPWIRE                   = TripwireMat.TRIPWIRE;
    public static final EmeraldBlockMat             EMERALD_BLOCK              = EmeraldBlockMat.EMERALD_BLOCK;
    public static final SpruceStairsMat             SPRUCE_STAIRS              = SpruceStairsMat.SPRUCE_STAIRS_EAST;
    public static final BirchStairsMat              BIRCH_STAIRS               = BirchStairsMat.BIRCH_STAIRS_EAST;
    public static final JungleStairsMat             JUNGLE_STAIRS              = JungleStairsMat.JUNGLE_STAIRS_EAST;
    public static final CommandBlockMat             COMMAND_BLOCK              = CommandBlockMat.COMMAND_BLOCK;
    public static final BeaconMat                   BEACON                     = BeaconMat.BEACON;
    public static final CobblestoneWallMat          COBBLESTONE_WALL           = CobblestoneWallMat.COBBLESTONE_WALL;
    public static final FlowerPotBlockMat           FLOWER_POT_BLOCK           = FlowerPotBlockMat.FLOWER_POT_BLOCK_EMPTY;
    public static final CarrotsBlockMat             CARROTS_BLOCK              = CarrotsBlockMat.CARROTS_BLOCK_0;
    public static final PotatoesBlockMat            POTATOES_BLOCK             = PotatoesBlockMat.POTATOES_BLOCK_0;
    public static final WoodenButtonMat             WOODEN_BUTTON              = WoodenButtonMat.WOODEN_BUTTON_DOWN;
    public static final SkullBlockMat               SKULL_BLOCK                = SkullBlockMat.SKULL_BLOCK_FLOOR;
    public static final AnvilMat                    ANVIL                      = AnvilMat.ANVIL_NORTH_SOUTH_NEW;
    public static final TrappedChestMat             TRAPPED_CHEST              = TrappedChestMat.TRAPPED_CHEST_NORTH;
    public static final GoldenPressurePlateMat      GOLDEN_PRESSURE_PLATE      = GoldenPressurePlateMat.GOLDEN_PRESSURE_PLATE_0;
    public static final IronPressurePlateMat        IRON_PRESSURE_PLATE        = IronPressurePlateMat.IRON_PRESSURE_PLATE_0;
    public static final RedstoneComparatorMat       REDSTONE_COMPARATOR        = RedstoneComparatorMat.REDSTONE_COMPARATOR_NORTH;
    public static final DaylightDetectorMat         DAYLIGHT_DETECTOR          = DaylightDetectorMat.DAYLIGHT_DETECTOR_OFF;
    public static final RedstoneBlockMat            REDSTONE_BLOCK             = RedstoneBlockMat.REDSTONE_BLOCK;
    public static final QuartzOreMat                QUARTZ_ORE                 = QuartzOreMat.QUARTZ_ORE;
    public static final HopperMat                   HOPPER                     = HopperMat.HOPPER_DOWN;
    public static final QuartzBlockMat              QUARTZ_BLOCK               = QuartzBlockMat.QUARTZ_BLOCK;
    public static final QuartzStairsMat             QUARTZ_STAIRS              = QuartzStairsMat.QUARTZ_STAIRS_EAST;
    public static final ActivatorRailMat            ACTIVATOR_RAIL             = ActivatorRailMat.ACTIVATOR_RAIL_NORTH_SOUTH;
    public static final DropperMat                  DROPPER                    = DropperMat.DROPPER_DOWN;
    public static final StainedHardenedClayMat      STAINED_HARDENED_CLAY      = StainedHardenedClayMat.STAINED_HARDENED_CLAY_WHITE;
    public static final StainedGlassPaneMat         STAINED_GLASS_PANE         = StainedGlassPaneMat.STAINED_GLASS_PANE_WHITE;
    public static final AcaciaStairsMat             ACACIA_STAIRS              = AcaciaStairsMat.ACACIA_STAIRS_EAST;
    public static final DarkOakStairsMat            DARK_OAK_STAIRS            = DarkOakStairsMat.DARK_OAK_STAIRS_EAST;
    public static final SlimeBlockMat               SLIME_BLOCK                = SlimeBlockMat.SLIME_BLOCK;
    public static final BarrierMat                  BARRIER                    = BarrierMat.BARRIER;
    public static final IronTrapdoorMat             IRON_TRAPDOOR              = IronTrapdoorMat.IRON_TRAPDOOR_WEST_BOTTOM;
    public static final PrismarineMat               PRISMARINE                 = PrismarineMat.PRISMARINE;
    public static final SeaLantrenMat               SEA_LANTERN                = SeaLantrenMat.SEA_LANTREN;
    public static final HayBlockMat                 HAY_BLOCK                  = HayBlockMat.HAY_BLOCK_UP_DOWN;
    public static final CarpetMat                   CARPET                     = CarpetMat.CARPET_WHITE;
    public static final HardenedClayMat             HARDENED_CLAY              = HardenedClayMat.HARDENED_CLAY;
    public static final CoalBlockMat                COAL_BLOCK                 = CoalBlockMat.COAL_BLOCK;
    public static final PackedIceMat                PACKED_ICE                 = PackedIceMat.PACKED_ICE;
    public static final DoubleFlowersMat            DOUBLE_FLOWERS             = DoubleFlowersMat.DOUBLE_FLOWERS_SUNFLOWER;
    public static final StandingBannerMat           STANDING_BANNER            = StandingBannerMat.STANDING_BANNER_SOUTH;
    public static final WallBannerMat               WALL_BANNER                = WallBannerMat.WALL_BANNER_NORTH;
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED = DaylightDetectorInvertedMat.DAYLIGHT_DETECTOR_INVERTED_OFF;
    public static final RedSandstoneMat             RED_SANDSTONE              = RedSandstoneMat.RED_SANDSTONE;
    public static final RedSandstoneStairsMat       RED_SANDSTONE_STAIRS       = RedSandstoneStairsMat.RED_SANDSTONE_STAIRS_EAST;
    public static final SpruceFenceGateMat          SPRUCE_FENCE_GATE          = SpruceFenceGateMat.SPRUCE_FENCE_GATE_SOUTH;
    public static final BirchFenceGateMat           BIRCH_FENCE_GATE           = BirchFenceGateMat.BIRCH_FENCE_GATE_SOUTH;
    public static final JungleFenceGateMat          JUNGLE_FENCE_GATE          = JungleFenceGateMat.JUNGLE_FENCE_GATE_SOUTH;
    public static final DarkOakFenceGateMat         DARK_OAK_FENCE_GATE        = DarkOakFenceGateMat.DARK_OAK_FENCE_GATE_SOUTH;
    public static final AcaciaFenceGateMat          ACACIA_FENCE_GATE          = AcaciaFenceGateMat.ACACIA_FENCE_GATE_SOUTH;
    public static final SpruceFenceMat              SPRUCE_FENCE               = SpruceFenceMat.SPRUCE_FENCE;
    public static final BirchFenceMat               BIRCH_FENCE                = BirchFenceMat.BRICH_FENCE;
    public static final JungleFenceMat              JUNGLE_FENCE               = JungleFenceMat.JUNGLE_FENCE;
    public static final DarkOakFenceMat             DARK_OAK_FENCE             = DarkOakFenceMat.DARK_OAK_FENCE;
    public static final AcaciaFenceMat              ACACIA_FENCE               = AcaciaFenceMat.ACACIA_FENCE;
    public static final SpruceDoorMat               SPRUCE_DOOR                = SpruceDoorMat.SPRUCE_DOOR_BOTTOM_EAST;
    public static final BirchDoorMat                BIRCH_DOOR                 = BirchDoorMat.BIRCH_DOOR_BOTTOM_EAST;
    public static final JungleDoorMat               JUNGLE_DOOR                = JungleDoorMat.JUNGLE_DOOR_BOTTOM_EAST;
    public static final AcaciaDoorMat               ACACIA_DOOR                = AcaciaDoorMat.ACACIA_DOOR_BOTTOM_EAST;
    public static final DarkOakDoorMat              DARK_OAK_DOOR              = DarkOakDoorMat.DARK_OAK_DOOR_BOTTOM_EAST;
    // ----- Item Separator -----
    //    public static final  Material                IRON_SPADE           = new Material("IRON_SPADE", 256, 1, 250);
//    public static final  Material                IRON_PICKAXE         = new Material("IRON_PICKAXE", 257, 1, 250);
//    public static final  Material                IRON_AXE             = new Material("IRON_AXE", 258, 1, 250);
//    public static final  Material                FLINT_AND_STEEL      = new Material("FLINT_AND_STEEL", 259, 1, 64);
    public static final AppleMat                    APPLE                      = AppleMat.APPLE;
    //    public static final  Material                BOW                  = new Material("BOW", 261, 1, 384);
    public static final ArrowMat                    ARROW                      = ArrowMat.ARROW;
    public static final CoalMat                     COAL                       = CoalMat.COAL;
    public static final DiamondMat                  DIAMOND                    = DiamondMat.DIAMOND;
    public static final IronIngotMat                IRON_INGOT                 = IronIngotMat.IRON_INGOT;
    public static final GoldIngotMat                GOLD_INGOT                 = GoldIngotMat.GOLD_INGOT;
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
    public static final StickMat                    STICK                      = StickMat.STICK;
    public static final BowlMat                     BOWL                       = BowlMat.BOWL;
    public static final MushroomStewMat             MUSHROOM_STEW              = MushroomStewMat.MUSHROOM_STEW;
    //    public static final  Material                GOLD_SWORD           = new Material("GOLD_SWORD", 283, 1, 32);
//    public static final  Material                GOLD_SPADE           = new Material("GOLD_SPADE", 284, 1, 32);
//    public static final  Material                GOLD_PICKAXE         = new Material("GOLD_PICKAXE", 285, 1, 32);
//    public static final  Material                GOLD_AXE             = new Material("GOLD_AXE", 286, 1, 32);
    public static final StringMat                   STRING                     = StringMat.STRING;
    public static final FeatherMat                  FEATHER                    = FeatherMat.FEATHER;
    public static final GunpowderMat                GUNPOWDER                  = GunpowderMat.GUNPOWDER;
    //    public static final  Material                WOOD_HOE             = new Material("WOOD_HOE", 290, 1, 59);
//    public static final  Material                STONE_HOE            = new Material("STONE_HOE", 291, 1, 131);
//    public static final  Material                IRON_HOE             = new Material("IRON_HOE", 292, 1, 250);
//    public static final  Material                DIAMOND_HOE          = new Material("DIAMOND_HOE", 293, 1, 1561);
//    public static final  Material                GOLD_HOE             = new Material("GOLD_HOE", 294, 1, 32);
    public static final WheatSeedsMat               WHEAT_SEEDS                = WheatSeedsMat.WHEAT_SEEDS;
    public static final WheatMat                    WHEAT                      = WheatMat.WHEAT;
    public static final BreadMat                    BREAD                      = BreadMat.BREAD;
    public static final LeatherHelmetMat            LEATHER_HELMET             = LeatherHelmetMat.LEATHER_HELMET;
    public static final LeatherChestplateMat        LEATHER_CHESTPLATE         = LeatherChestplateMat.LEATHER_CHESTPLATE;
    public static final LeatherLeggingsMat          LEATHER_LEGGINGS           = LeatherLeggingsMat.LEATHER_LEGGINGS;
    public static final LeatherBootsMat             LEATHER_BOOTS              = LeatherBootsMat.LEATHER_BOOTS;
    public static final ChainmailHelmetMat          CHAINMAIL_HELMET           = ChainmailHelmetMat.CHAINMAIL_HELMET;
    public static final ChainmailChestplateMat      CHAINMAIL_CHESTPLATE       = ChainmailChestplateMat.CHAINMAIL_CHESTPLATE;
    public static final ChainmailLeggingsMat        CHAINMAIL_LEGGINGS         = ChainmailLeggingsMat.CHAINMAIL_LEGGINGS;
    public static final ChainmailBootsMat           CHAINMAIL_BOOTS            = ChainmailBootsMat.CHAINMAIL_BOOTS;
    public static final IronHelmetMat               IRON_HELMET                = IronHelmetMat.IRON_HELMET;
    public static final IronChestplateMat           IRON_CHESTPLATE            = IronChestplateMat.IRON_CHESTPLATE;
    public static final IronLeggingsMat             IRON_LEGGINGS              = IronLeggingsMat.IRON_LEGGINGS;
    public static final IronBootsMat                IRON_BOOTS                 = IronBootsMat.IRON_BOOTS;
    public static final DiamondHelmetMat            DIAMOND_HELMET             = DiamondHelmetMat.DIAMOND_HELMET;
    public static final DiamondChestplateMat        DIAMOND_CHESTPLATE         = DiamondChestplateMat.DIAMOND_CHESTPLATE;
    public static final DiamondLeggingsMat          DIAMOND_LEGGINGS           = DiamondLeggingsMat.DIAMOND_LEGGINGS;
    public static final DiamondBootsMat             DIAMOND_BOOTS              = DiamondBootsMat.DIAMOND_BOOTS;
    public static final GoldHelmetMat               GOLD_HELMET                = GoldHelmetMat.GOLD_HELMET;
    public static final GoldChestplateMat           GOLD_CHESTPLATE            = GoldChestplateMat.GOLD_CHESTPLATE;
    public static final GoldLeggingsMat             GOLD_LEGGINGS              = GoldLeggingsMat.GOLD_LEGGINGS;
    public static final GoldBootsMat                GOLD_BOOTS                 = GoldBootsMat.GOLD_BOOTS;
    public static final FlintMat                    FLINT                      = FlintMat.FLINT;
    public static final PorkchopMat                 PORKCHOP                   = PorkchopMat.PORKCHOP;
    public static final CookedPorkchopMat           COOKED_PORKCHOP            = CookedPorkchopMat.COOKED_PORKCHOP;
    public static final PaintingMat                 PAINTING                   = PaintingMat.PAINTING;
    public static final GoldenAppleMat              GOLDEN_APPLE               = GoldenAppleMat.GOLDED_APPLE;
    public static final SignMat                     SIGN                       = SignMat.SIGN;
    public static final OakDoorItemMat              OAK_DOOR_ITEM              = OakDoorItemMat.OAK_DOOR_ITEM;
    public static final BucketMat                   BUCKET                     = BucketMat.BUCKET;
    public static final WaterBucketMat              WATER_BUCKET               = WaterBucketMat.WATER_BUCKET;
    public static final LavaBucketMat               LAVA_BUCKET                = LavaBucketMat.LAVA_BUCKET;
    public static final MinecartMat                 MINECART                   = MinecartMat.MINECART;
    public static final SaddleMat                   SADDLE                     = SaddleMat.SADDLE;
    public static final IronDoorItemMat             IRON_DOOR_ITEM             = IronDoorItemMat.IRON_DOOR_ITEM;
    public static final RedstoneMat                 REDSTONE                   = RedstoneMat.REDSTONE;
    public static final SnowballMat                 SNOW_BALL                  = SnowballMat.SNOWBALL;
    public static final BoatMat                     BOAT                       = BoatMat.BOAT;
    public static final LeatherMat                  LEATHER                    = LeatherMat.LEATHER;
    public static final MilkBucketMat               MILK_BUCKET                = MilkBucketMat.MILK_BUCKET;
    public static final BrickMat                    BRICK                      = BrickMat.BRICK;
    public static final ClayBallMat                 CLAY_BALL                  = ClayBallMat.CLAY_BALL;
    public static final ReedsMat                    REEDS                      = ReedsMat.REEDS;
    public static final PaperMat                    PAPER                      = PaperMat.PAPER;
    public static final BookMat                     BOOK                       = BookMat.BOOK;
    public static final SlimeballMat                SLIMEBALL                  = SlimeballMat.SLIMEBALL;
    public static final ChestMinecartMat            CHEST_MINECART             = ChestMinecartMat.CHEST_MINECART;
    public static final FurnaceMinecartMat          FURNACE_MINECART           = FurnaceMinecartMat.FURNACE_MINECART;
    public static final EggMat                      EGG                        = EggMat.EGG;
    public static final CompassMat                  COMPASS                    = CompassMat.COMPASS;
    //    public static final  Material                FISHING_ROD          = new Material("FISHING_ROD", 346, 1, 64);
    public static final ClockMat                    CLOCK                      = ClockMat.CLOCK;
    public static final GlowstoneDustMat            GLOWSTONE_DUST             = GlowstoneDustMat.GLOWSTONE_DUST;
    public static final FishMat                     FISH                       = FishMat.FISH;
    public static final CookedFishMat               COOKED_FISH                = CookedFishMat.COOKED_FISH;
    public static final DyeMat                      DYE                        = DyeMat.DYE_INK_SAC;
    public static final BoneMat                     BONE                       = BoneMat.BONE;
    public static final SugarMat                    SUGAR                      = SugarMat.SUGAR;
    public static final CakeMat                     CAKE                       = CakeMat.CAKE;
    public static final BedMat                      BED                        = BedMat.BED;
    public static final RedstoneRepeaterItemMat     REDSTONE_REPEATER_ITEM     = RedstoneRepeaterItemMat.REDSTONE_REPEATER_ITEM;
    public static final CookieMat                   COOKIE                     = CookieMat.COOKIE;
    public static final FilledMapMat                FILLED_MAP                 = FilledMapMat.FILLED_MAP;
    //    public static final  Material                SHEARS               = new Material("SHEARS", 359, 1, 238);
    public static final MelonMat                    MELON                      = MelonMat.MELON;
    public static final PumpkinSeedsMat             PUMPKIN_SEEDS              = PumpkinSeedsMat.PUMPKIN_SEEDS;
    public static final MelonSeedsMat               MELON_SEEDS                = MelonSeedsMat.MELON_SEEDS;
    public static final BeefMat                     BEEF                       = BeefMat.BEEF;
    public static final CookedBeefMat               COOKED_BEEF                = CookedBeefMat.COOKED_BEEF;
    public static final ChickenMat                  CHICKEN                    = ChickenMat.CHICKEN;
    public static final CookedChickenMat            COOKED_CHICKEN             = CookedChickenMat.COOKED_CHICKEN;
    public static final RottenFleshMat              ROTTEN_FLESH               = RottenFleshMat.ROTTEN_FLESH;
    public static final EnderPearlMat               ENDER_PEARL                = EnderPearlMat.ENDER_PEARL;
    public static final BlazeRodMat                 BLAZE_ROD                  = BlazeRodMat.BLAZE_ROD;
    public static final GhastTearMat                GHAST_TEAR                 = GhastTearMat.GHAST_TEAR;
    public static final GoldNuggetMat               GOLD_NUGGET                = GoldNuggetMat.GOLD_NUGGET;
    public static final NetherWartMat               NETHER_WART                = NetherWartMat.NETHER_WART;
    //    public static final  Material                POTION               = new Material("POTION", 373, 1);
    public static final GlassBottleMat              GLASS_BOTTLE               = GlassBottleMat.GLASS_BOTTLE;
    public static final SpiderEyeMat                SPIDER_EYE                 = SpiderEyeMat.SPIDER_EYE;
    public static final FermentedSpiderEyeMat       FERMENTED_SPIDER_EYE       = FermentedSpiderEyeMat.FERMENTED_SPIDER_EYE;
    public static final BlazePowderMat              BLAZE_POWDER               = BlazePowderMat.BLAZE_POWDER;
    public static final MagmaCreamMat               MAGMA_CREAM                = MagmaCreamMat.MAGMA_CREAM;
    public static final BrewingStandMat             BREWING_STAND              = BrewingStandMat.BREWING_STAND;
    public static final CauldronMat                 CAULDRON                   = CauldronMat.CAULDRON;
    public static final EnderEyeMat                 ENDER_EYE                  = EnderEyeMat.ENDER_EYE;
    public static final SpeckledMelonMat            SPECKLED_MELON             = SpeckledMelonMat.SPECKLED_MELON;
    public static final SpawnEggMat                 SPAWN_EGG                  = SpawnEggMat.SPAWN_EGG_CREEPER;
    public static final ExperienceBottleMat         EXPERIENCE_BOTTLE          = ExperienceBottleMat.EXPERIENCE_BOTTLE;
    public static final FireChargeMat               FIRE_CHARGE                = FireChargeMat.FIRE_CHARGE;
    public static final WritableBookMat             WRITABLE_BOOK              = WritableBookMat.WRITABLE_BOOK;
    public static final WrittenBookMat              WRITTEN_BOOK               = WrittenBookMat.WRITTEN_BOOK;
    public static final EmeraldMat                  EMERALD                    = EmeraldMat.EMERALD;
    public static final ItemFrameMat                ITEM_FRAME                 = ItemFrameMat.ITEM_FRAME;
    public static final FlowerPotMat                FLOWER_POT                 = FlowerPotMat.FLOWER_POT;
    public static final CarrotMat                   CARROT                     = CarrotMat.CARROT;
    public static final PotatoMat                   POTATO                     = PotatoMat.POTATO;
    public static final BakedPotatoMat              BAKED_POTATO               = BakedPotatoMat.BAKED_POTATO;
    public static final PoisonousPotatoMat          POISONOUS_POTATO           = PoisonousPotatoMat.POISONOUS_POTATO;
    public static final MapMat                      MAP                        = MapMat.MAP;
    public static final GoldenCarrotMat             GOLDEN_CARROT              = GoldenCarrotMat.GOLDEN_CARROT;
    public static final SkullMat                    SKULL                      = SkullMat.SKULL_SKELETON;
    //    public static final  Material                CARROT_STICK         = new Material("CARROT_STICK", 398, 1, 25);
    public static final NetherStarMat               NETHER_STAR                = NetherStarMat.NETHER_STAR;
    public static final PumpkinPieMat               PUMPKIN_PIE                = PumpkinPieMat.PUMPKIN_PIE;
    public static final FireworksMat                FIREWORKS                  = FireworksMat.FIREWORKS;
    public static final FireworkChargeMat           FIREWORK_CHARGE            = FireworkChargeMat.FIREWORK_CHARGE;
    public static final EnchantedBookMat            ENCHANTED_BOOK             = EnchantedBookMat.ENCHANTED_BOOK;
    public static final RedstoneComparatorItemMat   REDSTONE_COMPARATOR_ITEM   = RedstoneComparatorItemMat.REDSTONE_COMPARATOR_ITEM;
    public static final NetherBrickItemMat          NETHER_BRICK_ITEM          = NetherBrickItemMat.NETHER_BRICK_ITEM;
    public static final QuartzMat                   QUARTZ                     = QuartzMat.QUARTZ;
    public static final TntMinecartMat              TNT_MINECART               = TntMinecartMat.TNT_MINECART;
    public static final HopperMinecartMat           HOPPER_MINECART            = HopperMinecartMat.HOPPER_MINECART;
    public static final PrismarineShardMat          PRISMARINE_SHARD           = PrismarineShardMat.PRISMARINE_SHARD;
    public static final PrismarineCrystalsMat       PRISMARINE_CRYSTALS        = PrismarineCrystalsMat.PRISMARINE_CRYSTALS;
    public static final RabbitMat                   RABBIT                     = RabbitMat.RABBIT;
    public static final CookedRabbitMat             COOKED_RABBIT              = CookedRabbitMat.COOKED_RABBIT;
    public static final RabbitStewMat               RABBIT_STEW                = RabbitStewMat.RABBIT_STEW;
    public static final RabbitFootMat               RABBIT_FOOT                = RabbitFootMat.RABBIT_FOOT;
    public static final RabbitHideMat               RABBIT_HIDE                = RabbitHideMat.RABBIT_HIDE;
    public static final ArmorStandMat               ARMOR_STAND                = ArmorStandMat.ARMOR_STAND;
    public static final IronHorseArmorMat           IRON_HORSE_ARMOR           = IronHorseArmorMat.IRON_HORSE_ARMOR;
    public static final GoldenHorseArmorMat         GOLDEN_HORSE_ARMOR         = GoldenHorseArmorMat.GOLDEN_HORSE_ARMOR;
    public static final DiamondHorseArmorMat        DIAMOND_HORSE_ARMOR        = DiamondHorseArmorMat.DIAMOND_HORSE_ARMOR;
    public static final LeadMat                     LEAD                       = LeadMat.LEAD;
    public static final NameTagMat                  NAME_TAG                   = NameTagMat.NAME_TAG;
    public static final CommandBlockMinecartMat     COMMAND_BLOCK_MINECART     = CommandBlockMinecartMat.COMMAND_BLOCK_MINECART;
    public static final MuttonMat                   MUTTON                     = MuttonMat.MUTTON;
    public static final CookedMuttonMat             COOKED_MUTTON              = CookedMuttonMat.COOKED_MUTTON;
    public static final BannerMat                   BANNER                     = BannerMat.BANNER;
    public static final SpruceDoorItemMat           SPRUCE_DOOR_ITEM           = SpruceDoorItemMat.SPRUCE_DOOR_ITEM;
    public static final BirchDoorItemMat            BIRCH_DOOR_ITEM            = BirchDoorItemMat.BIRCH_DOOR_ITEM;
    public static final JungleDoorItemMat           JUNGLE_DOOR_ITEM           = JungleDoorItemMat.JUNGLE_DOOR_ITEM;
    public static final AcaciaDoorItemMat           ACACIA_DOOR_ITEM           = AcaciaDoorItemMat.ACACIA_DOOR_ITEM;
    public static final DarkOakDoorItemMat          DARK_OAK_DOOR_ITEM         = DarkOakDoorItemMat.DARK_OAK_DOOR_ITEM;
    public static final Record13Mat                 RECORD_13                  = Record13Mat.RECORD_13;
    public static final RecordCatMat                RECORD_CAT                 = RecordCatMat.RECORD_CAT;
    public static final RecordBlocksMat             RECORD_BLOCKS              = RecordBlocksMat.RECORD_BLOCKS;
    public static final RecordChripMat              RECORD_CHRIP               = RecordChripMat.RECORD_CHRIP;
    public static final RecordFarMat                RECORD_FAR                 = RecordFarMat.RECORD_FAR;
    public static final RecordMallMat               RECORD_MALL                = RecordMallMat.RECORD_MALL;
    public static final RecordMellohiMat            RECORD_MELLOHI             = RecordMellohiMat.RECORD_MELLOHI;
    public static final RecordStalMat               RECORD_STAL                = RecordStalMat.RECORD_STAL;
    public static final RecordStradMat              RECORD_STRAD               = RecordStradMat.RECORD_STRAD;
    public static final RecordWardMat               RECORD_WARD                = RecordWardMat.RECORD_WARD;
    public static final Record11Mat                 RECORD_11                  = Record11Mat.RECORD_11;
    public static final RecordWaitMat               RECORD_WAIT                = RecordWaitMat.RECORD_WAIT;

    private static final Map<String, Material>   byName        = new CaseInsensitiveMap<>(MATERIALS_SIZE, SMALL_LOAD_FACTOR);
    private static final Map<String, Material>   byMinecraftId = new CaseInsensitiveMap<>(MATERIALS_SIZE, SMALL_LOAD_FACTOR);
    private static final TIntObjectMap<Material> byID          = new TIntObjectHashMap<>(MATERIALS_SIZE, SMALL_LOAD_FACTOR);

    private final   String enumName;
    protected final int    id;
    protected final String minecraftId;
    protected final int    maxStack;
    protected final String typeName;
    protected final short  type;

    protected Material(final String enumName, final int id, final String minecraftId, final String typeName, final short type)
    {
        this(enumName, id, minecraftId, MagicNumbers.ITEMS__DEFAULT_STACK_SIZE, typeName, type);
    }

    protected Material(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final short type)
    {
        this.enumName = enumName;
        this.id = id;
        this.minecraftId = minecraftId;
        this.maxStack = maxStack;
        this.typeName = typeName;
        this.type = type;
    }

    @Override
    public String name()
    {
        return this.enumName;
    }

    @Override
    public int ordinal()
    {
        return this.id;
    }

    @Override
    public Material byOrdinal(final int ordinal)
    {
        return byID.get(ordinal);
    }

    @Override
    public Material byName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns sub-id of material, for blocks is in 0-15 range, for items it use 2 bytes.
     *
     * @return sub-id of material
     */
    public short getType()
    {
        return this.type;
    }

    /**
     * Returns type name of this type of material.
     *
     * @return type name of this type of material.
     */
    public String getTypeName()
    {
        return this.typeName;
    }

    /**
     * Get subtype of material by its subid/meta.
     *
     * @param type subid of type to get.
     *
     * @return subtype or null if not exist.
     */
    public abstract Material getType(int type);

    /**
     * Get subtype of material by its subid/meta.
     *
     * @param type subid of type to get.
     *
     * @return subtype or null if not exist.
     */
    public abstract Material getType(String type);

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

    /**
     * Returns array contains all other (including current one) sub-types of this material.
     *
     * @return array of sub-types.
     */
    public abstract Material[] types();

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

    public int getId()
    {
        return this.id;
    }

    /**
     * Some item's can't be directly added to player inventory,
     * (they will make client crash, thanks Mojang for that). <br>
     * This method will return other simillar item that can be added
     * to inventory, like water bucket for water block. <br>
     * Or will return this same instance if it already can be added. <br>
     * <br>
     * Overriding methods should not change type of returned value
     * as it may change in nex updates.
     *
     * @return item that can be added to inventory.
     */
    public Material ensureValidInventoryItem()
    {
        return this;
    }

    /**
     * Method will try to find material by given name, converting it to any possible type of id: <br>
     * <ul>
     * <li>{numericId} {@literal ->} like "1" for stone</li>
     * <li>{enumStringId} {@literal ->} like "STONE"</li>
     * <li>{minecraftStringId} {@literal ->} like "minecraft:stone"</li>
     * <li>minecraft:{shortMinecraftStringId} {@literal ->} like "stone"</li>
     * <li>{numericId}:{numericMeta} {@literal ->} like "1:0"</li>
     * <li>{numericId}:{stringMeta} {@literal ->} like "1:diorite"</li>
     * <li>{enumStringId}:{numericMeta} {@literal ->} like "STONE:0"</li>
     * <li>{enumStringId}:{stringMeta} {@literal ->} like "STONE:diorite"</li>
     * <li>{minecraftStringId}:{numericMeta} {@literal ->} like "minecraft:stone:diorite"</li>
     * <li>minecraft:{shortMinecraftStringId}:{numericMeta} {@literal ->} like "stone:diorite"</li>
     * </ul>
     *
     * @param string material name/id to find.
     *
     * @return material or null if it didn't find any.
     *
     * @see #matchMaterial(String, boolean)
     */
    public static Material matchMaterial(final String string)
    {
        return matchMaterial(string, false);
    }

    /**
     * Method will try to find material by given name, converting it to any possible type of id: <br>
     * <ul>
     * <li>{numericId} {@literal ->} like "1" for stone</li>
     * <li>{enumStringId} {@literal ->} like "STONE"</li>
     * <li>{minecraftStringId} {@literal ->} like "minecraft:stone"</li>
     * <li>minecraft:{shortMinecraftStringId} {@literal ->} like "stone"</li>
     * <li>{numericId}:{numericMeta} {@literal ->} like "1:0"</li>
     * <li>{numericId}:{stringMeta} {@literal ->} like "1:diorite"</li>
     * <li>{enumStringId}:{numericMeta} {@literal ->} like "STONE:0"</li>
     * <li>{enumStringId}:{stringMeta} {@literal ->} like "STONE:diorite"</li>
     * <li>{minecraftStringId}:{numericMeta} {@literal ->} like "minecraft:stone:diorite"</li>
     * <li>minecraft:{shortMinecraftStringId}:{numericMeta} {@literal ->} like "stone:diorite"</li>
     * </ul>
     * With extended mode it will also scan all materials and looks for sub-material with name equals to given string
     * multiple types may have this same sub-material name, so may not return valid material for types like that.
     *
     * @param string   material name/id to find.
     * @param extended if it should use extended mode. (slower)
     *
     * @return material or null if it didn't find any.
     */
    public static Material matchMaterial(String string, final boolean extended)
    {
        string = StringUtils.replace(string, " ", "_");

        // using simple id
        final Integer i = DioriteMathUtils.asInt(string);
        if (i != null)
        {
            return getByID(i);
        }

        // find in enum by whole string
        Material result = getByEnumName(string);
        if ((result != null) || (((result = getByMinecraftId(string))) != null) || (((result = getByMinecraftId("minecraft:" + string))) != null))
        {
            return result;
        }

        // split to [id, meta], where meta can't contains any ":"
        final int index = string.lastIndexOf(':');
        if (index == - 1)
        {
            if (! extended)
            {
                return string.equalsIgnoreCase("diorite") ? StoneMat.STONE_DIORITE : null;
            }

            for (final Material m : values())
            {
                result = m.getType(string);
                if (result != null)
                {
                    return result;
                }
            }

            // :<
            return null;
        }
        final String idPart = string.substring(0, index);
        final String metaPart = string.substring(index + 1);
        final Integer id = DioriteMathUtils.asInt(idPart);
        final Integer meta = DioriteMathUtils.asInt(metaPart);

        // by numeric id, and numeric or text meta.
        if (id != null)
        {
            return (meta != null) ? getByID(id, meta) : getByID(id, metaPart);
        }

        if (meta != null)
        {
            result = getByEnumName(idPart, meta);
            if ((result != null) || (((result = getByMinecraftId(idPart, meta))) != null) || (((result = getByMinecraftId("minecraft:" + idPart, meta))) != null))
            {
                return result;
            }
        }
        result = getByEnumName(idPart, metaPart);
        if ((result != null) || (((result = getByMinecraftId(idPart, metaPart))) != null) || (((result = getByMinecraftId("minecraft:" + idPart, metaPart))) != null))
        {
            return result;
        }

        // :<
        return null;
    }

    /**
     * Method will try to find material by given name, converting it to any possible type of id: <br>
     * <ul>
     * <li>{numericId} {@literal ->} like "1" for stone</li>
     * <li>{enumStringId} {@literal ->} like "STONE"</li>
     * <li>{minecraftStringId} {@literal ->} like "minecraft:stone"</li>
     * <li>minecraft:{shortMinecraftStringId} {@literal ->} like "stone"</li>
     * <li>{numericId}:{numericMeta} {@literal ->} like "1:0"</li>
     * <li>{numericId}:{stringMeta} {@literal ->} like "1:diorite"</li>
     * <li>{enumStringId}:{numericMeta} {@literal ->} like "STONE:0"</li>
     * <li>{enumStringId}:{stringMeta} {@literal ->} like "STONE:diorite"</li>
     * <li>{minecraftStringId}:{numericMeta} {@literal ->} like "minecraft:stone:diorite"</li>
     * <li>minecraft:{shortMinecraftStringId}:{numericMeta} {@literal ->} like "stone:diorite"</li>
     * </ul>
     * With extended mode it will also scan all materials and looks for sub-material with name equals to given string
     * multiple types may have this same sub-material name, so may not return valid material for types like that.
     *
     * @param string                   material name/id to find.
     * @param extended                 if it should use extended mode. (slower)
     * @param ensureValidInventoryItem if it should use {@link #ensureValidInventoryItem()} before returing result.
     *
     * @return material or null if it didn't find any.
     */
    public static Material matchMaterial(final String string, final boolean extended, final boolean ensureValidInventoryItem)
    {
        Material mat = matchMaterial(string, extended);
        if (ensureValidInventoryItem && (mat != null))
        {
            mat = mat.ensureValidInventoryItem();
        }
        return mat;
    }

    /**
     * Method will try to find material by given name, converting it to any possible type of id: <br>
     * <ul>
     * <li>{numericId} {@literal ->} like "1" for stone</li>
     * <li>{enumStringId} {@literal ->} like "STONE"</li>
     * <li>{minecraftStringId} {@literal ->} like "minecraft:stone"</li>
     * <li>minecraft:{shortMinecraftStringId} {@literal ->} like "stone"</li>
     * <li>{numericId}:{numericMeta} {@literal ->} like "1:0"</li>
     * <li>{numericId}:{stringMeta} {@literal ->} like "1:diorite"</li>
     * <li>{enumStringId}:{numericMeta} {@literal ->} like "STONE:0"</li>
     * <li>{enumStringId}:{stringMeta} {@literal ->} like "STONE:diorite"</li>
     * <li>{minecraftStringId}:{numericMeta} {@literal ->} like "minecraft:stone:diorite"</li>
     * <li>minecraft:{shortMinecraftStringId}:{numericMeta} {@literal ->} like "stone:diorite"</li>
     * </ul>
     * With extended mode it will also scan all materials and looks for sub-material with name equals to given string
     * multiple types may have this same sub-material name, so may not return valid material for types like that. <br>
     * <br>
     * This methos will also invoke {@link #ensureValidInventoryItem()} before returing result, so it is good for
     * getting materials for items.
     *
     * @param string material name/id to find.
     *
     * @return material or null if it didn't find any.
     *
     * @see #matchMaterial(String, boolean, boolean)
     */
    public static Material matchValidInventoryMaterial(final String string)
    {
        return matchMaterial(string, false, true);
    }

    /**
     * Method will try to find material by given name, converting it to any possible type of id: <br>
     * <ul>
     * <li>{numericId} {@literal ->} like "1" for stone</li>
     * <li>{enumStringId} {@literal ->} like "STONE"</li>
     * <li>{minecraftStringId} {@literal ->} like "minecraft:stone"</li>
     * <li>minecraft:{shortMinecraftStringId} {@literal ->} like "stone"</li>
     * <li>{numericId}:{numericMeta} {@literal ->} like "1:0"</li>
     * <li>{numericId}:{stringMeta} {@literal ->} like "1:diorite"</li>
     * <li>{enumStringId}:{numericMeta} {@literal ->} like "STONE:0"</li>
     * <li>{enumStringId}:{stringMeta} {@literal ->} like "STONE:diorite"</li>
     * <li>{minecraftStringId}:{numericMeta} {@literal ->} like "minecraft:stone:diorite"</li>
     * <li>minecraft:{shortMinecraftStringId}:{numericMeta} {@literal ->} like "stone:diorite"</li>
     * </ul>
     * With extended mode it will also scan all materials and looks for sub-material with name equals to given string
     * multiple types may have this same sub-material name, so may not return valid material for types like that. <br>
     * <br>
     * This methos will also invoke {@link #ensureValidInventoryItem()} before returing result, so it is good for
     * getting materials for items.
     *
     * @param string   material name/id to find.
     * @param extended if it should use extended mode. (slower)
     *
     * @return material or null if it didn't find any.
     *
     * @see #matchMaterial(String, boolean, boolean)
     */
    public static Material matchValidInventoryMaterial(final String string, final boolean extended)
    {
        return matchMaterial(string, extended, true);
    }

    public static Material getByID(final int id)
    {
        return byID.get(id);
    }

    public static Material getByID(final int id, final int meta)
    {
        final Material mat = byID.get(id);
        if (mat != null)
        {
            return mat.getType(meta);
        }
        return null;
    }

    public static Material getByID(final int id, final String meta)
    {
        final Material mat = byID.get(id);
        if (mat != null)
        {
            return mat.getType(meta);
        }
        return null;
    }

    public static Material getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static Material getByEnumName(final String name, final int meta)
    {
        final Material mat = byName.get(name);
        if (mat != null)
        {
            return mat.getType(meta);
        }
        return null;
    }

    public static Material getByEnumName(final String name, final String meta)
    {
        final Material mat = byName.get(name);
        if (mat != null)
        {
            return mat.getType(meta);
        }
        return null;
    }

    public static Material getByMinecraftId(final String name)
    {
        return byMinecraftId.get(name);
    }

    public static Material getByMinecraftId(final String name, final int meta)
    {
        final Material mat = byMinecraftId.get(name);
        if (mat != null)
        {
            return mat.getType(meta);
        }
        return null;
    }

    public static Material getByMinecraftId(final String name, final String meta)
    {
        final Material mat = byMinecraftId.get(name);
        if (mat != null)
        {
            return mat.getType(meta);
        }
        return null;
    }

    public static void register(final Material element)
    {
        byID.put(element.ordinal(), element);
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
        register(REEDS_BLOCK);
        register(JUKEBOX);
        register(OAK_FENCE);
        register(PUMPKIN);
        register(NETHERRACK);
        register(SOUL_SAND);
        register(GLOWSTONE);
        register(NETHER_PORTAL);
        register(PUMPKIN_LANTERN);
        register(CAKE_BLOCK);
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
        register(CAULDRON_BLOCK);
        register(END_PORTAL);
        register(END_PORTAL_FRAME);
        register(END_STONE);
        register(DRAGON_EGG);
        register(REDSTONE_LAMP_OFF);
        register(REDSTONE_LAMP_ON);
        register(DOUBLE_WOODEN_SLAB);
        register(WOODEN_SLAB);
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
        register(FLOWER_POT_BLOCK);
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
        register(APPLE);
//        register(BOW);
        register(ARROW);
        register(COAL);
        register(DIAMOND);
        register(IRON_INGOT);
        register(GOLD_INGOT);
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
        register(STICK);
        register(BOWL);
        register(MUSHROOM_STEW);
//        register(GOLD_SWORD);
//        register(GOLD_SPADE);
//        register(GOLD_PICKAXE);
//        register(GOLD_AXE);
        register(STRING);
        register(FEATHER);
        register(GUNPOWDER);
//        register(WOOD_HOE);
//        register(STONE_HOE);
//        register(IRON_HOE);
//        register(DIAMOND_HOE);
//        register(GOLD_HOE);
        register(WHEAT_SEEDS);
        register(WHEAT);
        register(BREAD);
        register(LEATHER_HELMET);
        register(LEATHER_CHESTPLATE);
        register(LEATHER_LEGGINGS);
        register(LEATHER_BOOTS);
        register(CHAINMAIL_HELMET);
        register(CHAINMAIL_CHESTPLATE);
        register(CHAINMAIL_LEGGINGS);
        register(CHAINMAIL_BOOTS);
        register(IRON_HELMET);
        register(IRON_CHESTPLATE);
        register(IRON_LEGGINGS);
        register(IRON_BOOTS);
        register(DIAMOND_HELMET);
        register(DIAMOND_CHESTPLATE);
        register(DIAMOND_LEGGINGS);
        register(DIAMOND_BOOTS);
        register(GOLD_HELMET);
        register(GOLD_CHESTPLATE);
        register(GOLD_LEGGINGS);
        register(GOLD_BOOTS);
        register(FLINT);
        register(PORKCHOP);
        register(COOKED_PORKCHOP);
        register(PAINTING);
        register(GOLDEN_APPLE);
        register(SIGN);
        register(OAK_DOOR_ITEM);
        register(BUCKET);
        register(WATER_BUCKET);
        register(LAVA_BUCKET);
        register(MINECART);
        register(SADDLE);
        register(IRON_DOOR_ITEM);
        register(REDSTONE);
        register(SNOW_BALL);
        register(BOAT);
        register(LEATHER);
        register(MILK_BUCKET);
        register(BRICK);
        register(CLAY_BALL);
        register(REEDS);
        register(PAPER);
        register(BOOK);
        register(SLIMEBALL);
        register(CHEST_MINECART);
        register(FURNACE_MINECART);
        register(EGG);
        register(COMPASS);
//        register(FISHING_ROD);
        register(CLOCK);
        register(GLOWSTONE_DUST);
        register(FISH);
        register(COOKED_FISH);
        register(DYE);
        register(BONE);
        register(SUGAR);
        register(CAKE);
        register(BED);
        register(REDSTONE_REPEATER_ITEM);
        register(COOKIE);
        register(FILLED_MAP);
//        register(SHEARS);
        register(MELON);
        register(PUMPKIN_SEEDS);
        register(MELON_SEEDS);
        register(BEEF);
        register(COOKED_BEEF);
        register(CHICKEN);
        register(COOKED_CHICKEN);
        register(ROTTEN_FLESH);
        register(ENDER_PEARL);
        register(BLAZE_ROD);
        register(GHAST_TEAR);
        register(GOLD_NUGGET);
        register(NETHER_WART);
//        register(POTION);
        register(GLASS_BOTTLE);
        register(SPIDER_EYE);
        register(FERMENTED_SPIDER_EYE);
        register(BLAZE_POWDER);
        register(MAGMA_CREAM);
        register(BREWING_STAND);
        register(CAULDRON);
        register(ENDER_EYE);
        register(SPECKLED_MELON);
        register(SPAWN_EGG);
        register(EXPERIENCE_BOTTLE);
        register(FIRE_CHARGE);
        register(WRITABLE_BOOK);
        register(WRITTEN_BOOK);
        register(EMERALD);
        register(ITEM_FRAME);
        register(FLOWER_POT);
        register(CARROT);
        register(POTATO);
        register(BAKED_POTATO);
        register(POISONOUS_POTATO);
        register(MAP);
        register(GOLDEN_CARROT);
        register(SKULL);
//        register(CARROT_STICK);
        register(NETHER_STAR);
        register(PUMPKIN_PIE);
        register(FIREWORKS);
        register(FIREWORK_CHARGE);
        register(ENCHANTED_BOOK);
        register(REDSTONE_COMPARATOR_ITEM);
        register(NETHER_BRICK_ITEM);
        register(QUARTZ);
        register(TNT_MINECART);
        register(HOPPER_MINECART);
        register(PRISMARINE_SHARD);
        register(PRISMARINE_CRYSTALS);
        register(RABBIT);
        register(COOKED_RABBIT);
        register(RABBIT_STEW);
        register(RABBIT_FOOT);
        register(RABBIT_HIDE);
        register(ARMOR_STAND);
        register(IRON_HORSE_ARMOR);
        register(GOLDEN_HORSE_ARMOR);
        register(DIAMOND_HORSE_ARMOR);
        register(LEAD);
        register(NAME_TAG);
        register(COMMAND_BLOCK_MINECART);
        register(MUTTON);
        register(COOKED_MUTTON);
        register(BANNER);
        register(SPRUCE_DOOR_ITEM);
        register(BIRCH_DOOR_ITEM);
        register(JUNGLE_DOOR_ITEM);
        register(ACACIA_DOOR_ITEM);
        register(DARK_OAK_DOOR_ITEM);
        register(RECORD_13);
        register(RECORD_CAT);
        register(RECORD_BLOCKS);
        register(RECORD_CHRIP);
        register(RECORD_FAR);
        register(RECORD_MALL);
        register(RECORD_MELLOHI);
        register(RECORD_STAL);
        register(RECORD_STRAD);
        register(RECORD_WARD);
        register(RECORD_11);
        register(RECORD_WAIT);
    }
}
