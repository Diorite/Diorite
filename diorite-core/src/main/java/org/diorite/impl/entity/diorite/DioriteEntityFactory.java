/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.impl.entity.diorite;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.impl.entity.IAreaEffectCloud;
import org.diorite.impl.entity.IArmorStand;
import org.diorite.impl.entity.IArrow;
import org.diorite.impl.entity.IBat;
import org.diorite.impl.entity.IBlaze;
import org.diorite.impl.entity.IBoat;
import org.diorite.impl.entity.ICaveSpider;
import org.diorite.impl.entity.IChicken;
import org.diorite.impl.entity.ICow;
import org.diorite.impl.entity.ICreeper;
import org.diorite.impl.entity.IDragonFireball;
import org.diorite.impl.entity.IEgg;
import org.diorite.impl.entity.IEnderCrystal;
import org.diorite.impl.entity.IEnderDragon;
import org.diorite.impl.entity.IEnderman;
import org.diorite.impl.entity.IEndermite;
import org.diorite.impl.entity.IEntity;
import org.diorite.impl.entity.IEntityFactory;
import org.diorite.impl.entity.IEyeOfEnderSignal;
import org.diorite.impl.entity.IFallingBlock;
import org.diorite.impl.entity.IFallingDragonEgg;
import org.diorite.impl.entity.IFireball;
import org.diorite.impl.entity.IFireworksRocket;
import org.diorite.impl.entity.IFishingHook;
import org.diorite.impl.entity.IGhast;
import org.diorite.impl.entity.IGiant;
import org.diorite.impl.entity.IGuardian;
import org.diorite.impl.entity.IHorse;
import org.diorite.impl.entity.IHuman;
import org.diorite.impl.entity.IItem;
import org.diorite.impl.entity.IItemFrame;
import org.diorite.impl.entity.ILaveSlime;
import org.diorite.impl.entity.ILeashKnot;
import org.diorite.impl.entity.IMinecart;
import org.diorite.impl.entity.IMinecartChest;
import org.diorite.impl.entity.IMinecartCommandBlock;
import org.diorite.impl.entity.IMinecartFurnace;
import org.diorite.impl.entity.IMinecartHopper;
import org.diorite.impl.entity.IMinecartTNT;
import org.diorite.impl.entity.IMooshroomCow;
import org.diorite.impl.entity.IOcelot;
import org.diorite.impl.entity.IPig;
import org.diorite.impl.entity.IPigZombie;
import org.diorite.impl.entity.IPlayer;
import org.diorite.impl.entity.IPolarBear;
import org.diorite.impl.entity.IPrimedTNT;
import org.diorite.impl.entity.IRabbit;
import org.diorite.impl.entity.ISheep;
import org.diorite.impl.entity.IShulker;
import org.diorite.impl.entity.IShulkerBullet;
import org.diorite.impl.entity.ISilverfish;
import org.diorite.impl.entity.ISkeleton;
import org.diorite.impl.entity.ISlime;
import org.diorite.impl.entity.ISmallFireball;
import org.diorite.impl.entity.ISnowball;
import org.diorite.impl.entity.ISnowman;
import org.diorite.impl.entity.ISpectralArrow;
import org.diorite.impl.entity.ISpider;
import org.diorite.impl.entity.ISquid;
import org.diorite.impl.entity.IThrownEnderpearl;
import org.diorite.impl.entity.IThrownExpBottle;
import org.diorite.impl.entity.IThrownPotion;
import org.diorite.impl.entity.ITippedArrow;
import org.diorite.impl.entity.IVillager;
import org.diorite.impl.entity.IVillagerGolem;
import org.diorite.impl.entity.IWitch;
import org.diorite.impl.entity.IWither;
import org.diorite.impl.entity.IWitherSkull;
import org.diorite.impl.entity.IWolf;
import org.diorite.impl.entity.IZombie;
import org.diorite.impl.world.WorldImpl;
import org.diorite.ILocation;
import org.diorite.ImmutableLocation;
import org.diorite.Location;
import org.diorite.auth.GameProfile;
import org.diorite.entity.AreaEffectCloud;
import org.diorite.entity.ArmorStand;
import org.diorite.entity.Arrow;
import org.diorite.entity.Bat;
import org.diorite.entity.Blaze;
import org.diorite.entity.Boat;
import org.diorite.entity.CaveSpider;
import org.diorite.entity.Chicken;
import org.diorite.entity.Cow;
import org.diorite.entity.Creeper;
import org.diorite.entity.DragonFireball;
import org.diorite.entity.Egg;
import org.diorite.entity.EnderCrystal;
import org.diorite.entity.EnderDragon;
import org.diorite.entity.Enderman;
import org.diorite.entity.Endermite;
import org.diorite.entity.Entity;
import org.diorite.entity.EntityType;
import org.diorite.entity.EyeOfEnderSignal;
import org.diorite.entity.FallingBlock;
import org.diorite.entity.FallingDragonEgg;
import org.diorite.entity.Fireball;
import org.diorite.entity.FireworksRocket;
import org.diorite.entity.FishingHook;
import org.diorite.entity.Ghast;
import org.diorite.entity.Giant;
import org.diorite.entity.Guardian;
import org.diorite.entity.Horse;
import org.diorite.entity.Item;
import org.diorite.entity.ItemFrame;
import org.diorite.entity.LaveSlime;
import org.diorite.entity.LeashKnot;
import org.diorite.entity.Minecart;
import org.diorite.entity.MinecartChest;
import org.diorite.entity.MinecartCommandBlock;
import org.diorite.entity.MinecartFurnace;
import org.diorite.entity.MinecartHopper;
import org.diorite.entity.MinecartTNT;
import org.diorite.entity.MooshroomCow;
import org.diorite.entity.Ocelot;
import org.diorite.entity.Pig;
import org.diorite.entity.PigZombie;
import org.diorite.entity.PolarBear;
import org.diorite.entity.PrimedTNT;
import org.diorite.entity.Rabbit;
import org.diorite.entity.Sheep;
import org.diorite.entity.Shulker;
import org.diorite.entity.ShulkerBullet;
import org.diorite.entity.Silverfish;
import org.diorite.entity.Skeleton;
import org.diorite.entity.Slime;
import org.diorite.entity.SmallFireball;
import org.diorite.entity.Snowball;
import org.diorite.entity.Snowman;
import org.diorite.entity.SpectralArrow;
import org.diorite.entity.Spider;
import org.diorite.entity.Squid;
import org.diorite.entity.ThrownEnderpearl;
import org.diorite.entity.ThrownExpBottle;
import org.diorite.entity.ThrownPotion;
import org.diorite.entity.TippedArrow;
import org.diorite.entity.Villager;
import org.diorite.entity.VillagerGolem;
import org.diorite.entity.Witch;
import org.diorite.entity.Wither;
import org.diorite.entity.WitherSkull;
import org.diorite.entity.Wolf;
import org.diorite.entity.Zombie;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.nbt.NbtTagDouble;
import org.diorite.nbt.NbtTagFloat;

import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;

@SuppressWarnings("unchecked")
public class DioriteEntityFactory implements IEntityFactory
{
    private final Map<EntityType, Function<ImmutableLocation, IEntity>>              typeMap          = new HashMap<>(50);
    private final Map<Class<? extends Entity>, Function<ImmutableLocation, IEntity>> typeClassMap     = new HashMap<>(100);
    private final Int2IntOpenHashMap                                                 networkToEnumEnt = new Int2IntOpenHashMap(100, .2f);
    private final Int2IntOpenHashMap                                                 networkToEnumObj = new Int2IntOpenHashMap(100, .2f);
    private final Int2IntOpenHashMap                                                 enumToNetwork    = new Int2IntOpenHashMap(100, .2f);

    private final DioriteCore core;

    public DioriteEntityFactory(final DioriteCore core)
    {
        this.core = core;
        this.init();
        this.networkToEnumEnt.defaultReturnValue(Integer.MIN_VALUE);
        this.networkToEnumObj.defaultReturnValue(Integer.MIN_VALUE);
        this.enumToNetwork.defaultReturnValue(Integer.MIN_VALUE);
    }

    private void registerID(final EntityType type, final int id)
    {
        if (type.isObject())
        {
            this.networkToEnumObj.put(id, type.ordinal());
        }
        else
        {
            this.networkToEnumEnt.put(id, type.ordinal());
        }
        this.enumToNetwork.put(type.ordinal(), id);
    }

    @SuppressWarnings("MagicNumber")
    private void init()
    {
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new CreeperImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.CREEPER, func);
            this.typeClassMap.put(Creeper.class, func);
            this.typeClassMap.put(ICreeper.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new ItemImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.ITEM, func);
            this.typeClassMap.put(Item.class, func);
            this.typeClassMap.put(IItem.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new ZombieImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.ZOMBIE, func);
            this.typeClassMap.put(Zombie.class, func);
            this.typeClassMap.put(IZombie.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new AreaEffectCloudImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.AREA_EFFECT_CLOUD, func);
            this.typeClassMap.put(AreaEffectCloud.class, func);
            this.typeClassMap.put(IAreaEffectCloud.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new ArmorStandImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.ARMOR_STAND, func);
            this.typeClassMap.put(ArmorStand.class, func);
            this.typeClassMap.put(IArmorStand.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new ArrowImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.ARROW, func);
            this.typeClassMap.put(Arrow.class, func);
            this.typeClassMap.put(IArrow.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new BatImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.BAT, func);
            this.typeClassMap.put(Bat.class, func);
            this.typeClassMap.put(IBat.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new BlazeImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.BLAZE, func);
            this.typeClassMap.put(Blaze.class, func);
            this.typeClassMap.put(IBlaze.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new BoatImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.BOAT, func);
            this.typeClassMap.put(Boat.class, func);
            this.typeClassMap.put(IBoat.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new CaveSpiderImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.CAVE_SPIDER, func);
            this.typeClassMap.put(CaveSpider.class, func);
            this.typeClassMap.put(ICaveSpider.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new ChickenImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.CHICKEN, func);
            this.typeClassMap.put(Chicken.class, func);
            this.typeClassMap.put(IChicken.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new CowImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.COW, func);
            this.typeClassMap.put(Cow.class, func);
            this.typeClassMap.put(ICow.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new DragonFireballImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.DRAGON_FIREBALL, func);
            this.typeClassMap.put(DragonFireball.class, func);
            this.typeClassMap.put(IDragonFireball.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new EggImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.EGG, func);
            this.typeClassMap.put(Egg.class, func);
            this.typeClassMap.put(IEgg.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new EnderCrystalImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.ENDER_CRYSTAL, func);
            this.typeClassMap.put(EnderCrystal.class, func);
            this.typeClassMap.put(IEnderCrystal.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new EnderDragonImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.ENDER_DRAGON, func);
            this.typeClassMap.put(EnderDragon.class, func);
            this.typeClassMap.put(IEnderDragon.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new EndermanImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.ENDERMAN, func);
            this.typeClassMap.put(Enderman.class, func);
            this.typeClassMap.put(IEnderman.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new EndermiteImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.ENDERMITE, func);
            this.typeClassMap.put(Endermite.class, func);
            this.typeClassMap.put(IEndermite.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new EyeOfEnderSignalImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.EYE_OF_ENDER_SIGNAL, func);
            this.typeClassMap.put(EyeOfEnderSignal.class, func);
            this.typeClassMap.put(IEyeOfEnderSignal.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new FallingBlockImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.FALLING_BLOCK, func);
            this.typeClassMap.put(FallingBlock.class, func);
            this.typeClassMap.put(IFallingBlock.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new FallingDragonEggImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.FALLING_DRAGON_EGG, func);
            this.typeClassMap.put(FallingDragonEgg.class, func);
            this.typeClassMap.put(IFallingDragonEgg.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new FireballImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.FIREBALL, func);
            this.typeClassMap.put(Fireball.class, func);
            this.typeClassMap.put(IFireball.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new FireworksRocketImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.FIREWORK_ROCKET, func);
            this.typeClassMap.put(FireworksRocket.class, func);
            this.typeClassMap.put(IFireworksRocket.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new FishingHookImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.FISHING_HOOK, func);
            this.typeClassMap.put(FishingHook.class, func);
            this.typeClassMap.put(IFishingHook.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new GhastImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.GHAST, func);
            this.typeClassMap.put(Ghast.class, func);
            this.typeClassMap.put(IGhast.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new GiantImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.GIANT, func);
            this.typeClassMap.put(Giant.class, func);
            this.typeClassMap.put(IGiant.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new GuardianImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.GUARDIAN, func);
            this.typeClassMap.put(Guardian.class, func);
            this.typeClassMap.put(IGuardian.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new HorseImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.HORSE, func);
            this.typeClassMap.put(Horse.class, func);
            this.typeClassMap.put(IHorse.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new ItemFrameImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.ITEM_FRAME, func);
            this.typeClassMap.put(ItemFrame.class, func);
            this.typeClassMap.put(IItemFrame.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new LaveSlimeImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.LAVA_SLIME, func);
            this.typeClassMap.put(LaveSlime.class, func);
            this.typeClassMap.put(ILaveSlime.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new LeashKnotImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.LEASH_KNOT, func);
            this.typeClassMap.put(LeashKnot.class, func);
            this.typeClassMap.put(ILeashKnot.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new MinecartImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.MINECART, func);
            this.typeClassMap.put(Minecart.class, func);
            this.typeClassMap.put(IMinecart.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new MinecartChestImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.MINECART_CHEST, func);
            this.typeClassMap.put(MinecartChest.class, func);
            this.typeClassMap.put(IMinecartChest.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new MinecartCommandBlockImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.MINECART_COMMAND_BLOCK, func);
            this.typeClassMap.put(MinecartCommandBlock.class, func);
            this.typeClassMap.put(IMinecartCommandBlock.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new MinecartFurnaceImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.MINECART_FURNACE, func);
            this.typeClassMap.put(MinecartFurnace.class, func);
            this.typeClassMap.put(IMinecartFurnace.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new MinecartHopperImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.MINECART_HOPPER, func);
            this.typeClassMap.put(MinecartHopper.class, func);
            this.typeClassMap.put(IMinecartHopper.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new MinecartTNTImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.MINECART_TNT, func);
            this.typeClassMap.put(MinecartTNT.class, func);
            this.typeClassMap.put(IMinecartTNT.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new MooshroomCowImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.MOOSHROOM_COW, func);
            this.typeClassMap.put(MooshroomCow.class, func);
            this.typeClassMap.put(IMooshroomCow.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new OcelotImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.OCELOT, func);
            this.typeClassMap.put(Ocelot.class, func);
            this.typeClassMap.put(IOcelot.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new PigImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.PIG, func);
            this.typeClassMap.put(Pig.class, func);
            this.typeClassMap.put(IPig.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new PolarBearImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.POLAR_BEAR, func);
            this.typeClassMap.put(PolarBear.class, func);
            this.typeClassMap.put(IPolarBear.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new PigZombieImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.PIG_ZOMBIE, func);
            this.typeClassMap.put(PigZombie.class, func);
            this.typeClassMap.put(IPigZombie.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new PrimedTNTImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.PRIMED_TNT, func);
            this.typeClassMap.put(PrimedTNT.class, func);
            this.typeClassMap.put(IPrimedTNT.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new RabbitImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.RABBIT, func);
            this.typeClassMap.put(Rabbit.class, func);
            this.typeClassMap.put(IRabbit.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new SheepImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.SHEEP, func);
            this.typeClassMap.put(Sheep.class, func);
            this.typeClassMap.put(ISheep.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new ShulkerImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.SHULKER, func);
            this.typeClassMap.put(Shulker.class, func);
            this.typeClassMap.put(IShulker.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new ShulkerBulletImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.SHULKER_BULLET, func);
            this.typeClassMap.put(ShulkerBullet.class, func);
            this.typeClassMap.put(IShulkerBullet.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new SilverfishImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.SILVERFISH, func);
            this.typeClassMap.put(Silverfish.class, func);
            this.typeClassMap.put(ISilverfish.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new SkeletonImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.SKELETON, func);
            this.typeClassMap.put(Skeleton.class, func);
            this.typeClassMap.put(ISkeleton.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new SlimeImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.SLIME, func);
            this.typeClassMap.put(Slime.class, func);
            this.typeClassMap.put(ISlime.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new SmallFireballImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.SMALL_FIREBALL, func);
            this.typeClassMap.put(SmallFireball.class, func);
            this.typeClassMap.put(ISmallFireball.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new SnowballImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.SNOWBALL, func);
            this.typeClassMap.put(Snowball.class, func);
            this.typeClassMap.put(ISnowball.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new SnowmanImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.SNOWMAN, func);
            this.typeClassMap.put(Snowman.class, func);
            this.typeClassMap.put(ISnowman.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new SpectralArrowImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.SPECTRAL_ARROW, func);
            this.typeClassMap.put(SpectralArrow.class, func);
            this.typeClassMap.put(ISpectralArrow.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new SpiderImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.SPIDER, func);
            this.typeClassMap.put(Spider.class, func);
            this.typeClassMap.put(ISpider.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new SquidImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.SQUID, func);
            this.typeClassMap.put(Squid.class, func);
            this.typeClassMap.put(ISquid.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new ThrownEnderpearlImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.THROWN_ENDERPEARL, func);
            this.typeClassMap.put(ThrownEnderpearl.class, func);
            this.typeClassMap.put(IThrownEnderpearl.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new ThrownExpBottleImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.THROWN_EXP_BOTTLE, func);
            this.typeClassMap.put(ThrownExpBottle.class, func);
            this.typeClassMap.put(IThrownExpBottle.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new ThrownPotionImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.THROWN_POTION, func);
            this.typeClassMap.put(ThrownPotion.class, func);
            this.typeClassMap.put(IThrownPotion.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new TippedArrowImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.TIPPED_ARROW, func);
            this.typeClassMap.put(TippedArrow.class, func);
            this.typeClassMap.put(ITippedArrow.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new VillagerImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.VILLAGER, func);
            this.typeClassMap.put(Villager.class, func);
            this.typeClassMap.put(IVillager.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new VillagerGolemImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.VILLAGER_GOLEM, func);
            this.typeClassMap.put(VillagerGolem.class, func);
            this.typeClassMap.put(IVillagerGolem.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new WitchImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.WITCH, func);
            this.typeClassMap.put(Witch.class, func);
            this.typeClassMap.put(IWitch.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new WitherImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.WITHER, func);
            this.typeClassMap.put(Wither.class, func);
            this.typeClassMap.put(IWither.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new WitherSkullImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.WITHER_SKULL, func);
            this.typeClassMap.put(WitherSkull.class, func);
            this.typeClassMap.put(IWitherSkull.class, func);
        }
        {
            final Function<ImmutableLocation, IEntity> func = (l) -> new WolfImpl(UUID.randomUUID(), this.core, IEntity.getNextEntityID(), l);
            this.typeMap.put(EntityType.WOLF, func);
            this.typeClassMap.put(Wolf.class, func);
            this.typeClassMap.put(IWolf.class, func);
        }
        this.registerID(EntityType.BOAT, 1);
        this.registerID(EntityType.ITEM, 2);
        this.registerID(EntityType.AREA_EFFECT_CLOUD, 3);
        this.registerID(EntityType.MINECART, 10);
        this.registerID(EntityType.PRIMED_TNT, 50);
        this.registerID(EntityType.ENDER_CRYSTAL, 51);
        this.registerID(EntityType.ARROW, 60);
        this.registerID(EntityType.SNOWBALL, 61);
        this.registerID(EntityType.EGG, 62);
        this.registerID(EntityType.FIREBALL, 63);
        this.registerID(EntityType.SMALL_FIREBALL, 64);
        this.registerID(EntityType.THROWN_ENDERPEARL, 65);
        this.registerID(EntityType.WITHER_SKULL, 66);
        this.registerID(EntityType.SHULKER_BULLET, 67);
        this.registerID(EntityType.FALLING_BLOCK, 70);
        this.registerID(EntityType.ITEM_FRAME, 71);
        this.registerID(EntityType.EYE_OF_ENDER_SIGNAL, 72);
        this.registerID(EntityType.THROWN_POTION, 73);
        this.registerID(EntityType.FALLING_DRAGON_EGG, 74);
        this.registerID(EntityType.THROWN_EXP_BOTTLE, 75);
        this.registerID(EntityType.FIREWORK_ROCKET, 76);
        this.registerID(EntityType.LEASH_KNOT, 77);
        this.registerID(EntityType.ARMOR_STAND, 78);
        this.registerID(EntityType.FISHING_HOOK, 90);
        this.registerID(EntityType.SPECTRAL_ARROW, 91);
        this.registerID(EntityType.TIPPED_ARROW, 92);
        this.registerID(EntityType.DRAGON_FIREBALL, 93);
        this.registerID(EntityType.PLAYER, - 1);
        this.registerID(EntityType.CREEPER, 50);
        this.registerID(EntityType.SKELETON, 51);
        this.registerID(EntityType.SPIDER, 52);
        this.registerID(EntityType.GIANT, 53);
        this.registerID(EntityType.ZOMBIE, 54);
        this.registerID(EntityType.SLIME, 55);
        this.registerID(EntityType.GHAST, 56);
        this.registerID(EntityType.PIG_ZOMBIE, 57);
        this.registerID(EntityType.ENDERMAN, 58);
        this.registerID(EntityType.CAVE_SPIDER, 59);
        this.registerID(EntityType.SILVERFISH, 60);
        this.registerID(EntityType.BLAZE, 61);
        this.registerID(EntityType.LAVA_SLIME, 62);
        this.registerID(EntityType.ENDER_DRAGON, 63);
        this.registerID(EntityType.WITHER, 64);
        this.registerID(EntityType.BAT, 65);
        this.registerID(EntityType.WITCH, 66);
        this.registerID(EntityType.ENDERMITE, 67);
        this.registerID(EntityType.GUARDIAN, 68);
        this.registerID(EntityType.SHULKER, 69);
        this.registerID(EntityType.PIG, 90);
        this.registerID(EntityType.SHEEP, 91);
        this.registerID(EntityType.COW, 92);
        this.registerID(EntityType.CHICKEN, 93);
        this.registerID(EntityType.SQUID, 94);
        this.registerID(EntityType.WOLF, 95);
        this.registerID(EntityType.MOOSHROOM_COW, 96);
        this.registerID(EntityType.SNOWMAN, 97);
        this.registerID(EntityType.OCELOT, 98);
        this.registerID(EntityType.VILLAGER_GOLEM, 99);
        this.registerID(EntityType.HORSE, 100);
        this.registerID(EntityType.RABBIT, 101);
        this.registerID(EntityType.POLAR_BEAR, 102);
        this.registerID(EntityType.VILLAGER, 120);
    }

    @Override
    public IEntity createEntity(final EntityType type, final ILocation location)
    {
        final Function<ImmutableLocation, IEntity> func = this.typeMap.get(type);
        if (func == null)
        {
            return null;
        }
        return func.apply(location.toImmutableLocation());
    }

    @Override
    public IEntity createEntity(final NbtTagCompound nbt, final WorldImpl world)
    {
        final Iterator<NbtTagDouble> pos = nbt.getList("Pos", NbtTagDouble.class).iterator();
        final Iterator<NbtTagFloat> rotation = nbt.getList("Rotation", NbtTagFloat.class).iterator();
        final ILocation entityLocation = new Location(pos.next().getValue(), pos.next().getValue(), pos.next().getValue(), rotation.next().getValue(), rotation.next().getValue(), world);
        final EntityType entityType = EntityType.getByEntityName(nbt.getString("id"));

        final IEntity entity = this.createEntity(entityType, entityLocation);
        entity.loadFromNbt(nbt);

        return entity;
    }

    @Override
    public <T extends Entity> T createEntity(final Class<T> clazz, final ILocation location)
    {
        final Function<ImmutableLocation, IEntity> func = this.typeClassMap.get(clazz);
        if (func == null)
        {
            return null;
        }
        return (T) func.apply(location.toImmutableLocation());
    }


    @Override
    public IHuman createHuman(final GameProfile profile, final ILocation location)
    {
        return new HumanImpl(this.core, profile, IEntity.getNextEntityID(), location.toImmutableLocation());
    }

    @Override
    public IPlayer createPlayer(final GameProfile profile, final CoreNetworkManager networkManager, final ILocation location)
    {
        return new PlayerImpl(this.core, profile, networkManager, IEntity.getNextEntityID(), location.toImmutableLocation());
    }

    @Override
    public int getEntityNetworkID(final EntityType type)
    {
        return this.enumToNetwork.get(type.ordinal());
    }

    @Override
    public EntityType getEntityTypeByNetworkID(final int id, final boolean isObject)
    {
        return EntityType.getByEnumOrdinal(isObject ? this.networkToEnumObj.get(id) : this.networkToEnumEnt.get(id));
    }
}
