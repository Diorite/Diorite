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

package org.diorite.entity;

import org.diorite.utils.SimpleEnum.ASimpleEnum;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

@SuppressWarnings({"ClassHasNoToStringMethod"})
public class EntityType extends ASimpleEnum<EntityType>
{
    static
    {
        init(EntityType.class, 61);
    }

    public static final EntityType PLAYER         = new EntityType("PLAYER", Player.class, "Player", false);
    public static final EntityType CREEPER        = new EntityType("CREEPER", Creeper.class, "Creeper", false);
    public static final EntityType SKELETON       = new EntityType("SKELETON", Skeleton.class, "Skeleton", false);
    public static final EntityType SPIDER         = new EntityType("SPIDER", Spider.class, "Spider", false);
    public static final EntityType GIANT          = new EntityType("GIANT", Giant.class, "Giant", false);
    public static final EntityType ZOMBIE         = new EntityType("ZOMBIE", Zombie.class, "Zombie", false);
    public static final EntityType SLIME          = new EntityType("SLIME", Slime.class, "Slime", false);
    public static final EntityType GHAST          = new EntityType("GHAST", Ghast.class, "Ghast", false);
    public static final EntityType PIG_ZOMBIE     = new EntityType("PIG_ZOMBIE", PigZombie.class, "PigZombie", false);
    public static final EntityType ENDERMAN       = new EntityType("ENDERMAN", Enderman.class, "Enderman", false);
    public static final EntityType CAVE_SPIDER    = new EntityType("CAVE_SPIDER", CaveSpider.class, "CaveSpider", false);
    public static final EntityType SILVERFISH     = new EntityType("SILVERFISH", Silverfish.class, "Silverfish", false);
    public static final EntityType BLAZE          = new EntityType("BLAZE", Blaze.class, "Blaze", false);
    public static final EntityType LAVA_SLIME     = new EntityType("LAVA_SLIME", LaveSlime.class, "LavaSlime", false);
    public static final EntityType ENDER_DRAGON   = new EntityType("ENDER_DRAGON", EnderDragon.class, "EnderDragon", false);
    public static final EntityType WITHER         = new EntityType("WITHER", Wither.class, "WitherBoss", false);
    public static final EntityType BAT            = new EntityType("BAT", Bat.class, "Bat", false);
    public static final EntityType WITCH          = new EntityType("WITCH", Witch.class, "Witch", false);
    public static final EntityType ENDERMITE      = new EntityType("ENDERMITE", Endermite.class, "Endermite", false);
    public static final EntityType GUARDIAN       = new EntityType("GUARDIAN", Guardian.class, "Guardian", false);
    public static final EntityType SHULKER        = new EntityType("SHULKER", Shulker.class, "Shulker", false);
    public static final EntityType PIG            = new EntityType("PIG", Pig.class, "Pig", false);
    public static final EntityType SHEEP          = new EntityType("SHEEP", Sheep.class, "Sheep", false);
    public static final EntityType COW            = new EntityType("COW", Cow.class, "Cow", false);
    public static final EntityType CHICKEN        = new EntityType("CHICKEN", Chicken.class, "Chicken", false);
    public static final EntityType SQUID          = new EntityType("SQUID", Squid.class, "Squid", false);
    public static final EntityType WOLF           = new EntityType("WOLF", Wolf.class, "Wolf", false);
    public static final EntityType MOOSHROOM_COW  = new EntityType("MOOSHROOM_COW", MooshroomCow.class, "MooshroomCow", false);
    public static final EntityType SNOWMAN        = new EntityType("SNOWMAN", Snowman.class, "SnowMan", false);
    public static final EntityType OCELOT         = new EntityType("OCELOT", Ocelot.class, "Ozelot", false);
    public static final EntityType VILLAGER_GOLEM = new EntityType("VILLAGER_GOLEM", VillagerGolem.class, "VillagerGolem", false);
    public static final EntityType HORSE          = new EntityType("HORSE", Horse.class, "EntityHorse", false);
    public static final EntityType RABBIT         = new EntityType("RABBIT", Rabbit.class, "Rabbit", false);
    public static final EntityType VILLAGER       = new EntityType("VILLAGER", Villager.class, "Villager", false);

    public static final EntityType BOAT                   = new EntityType("BOAT", Boat.class, "Boat", true);
    public static final EntityType ITEM                   = new EntityType("ITEM", Item.class, "Item", true);
    public static final EntityType AREA_EFFECT_CLOUD      = new EntityType("AREA_EFFECT_CLOUD", AreaEffectCloud.class, "AreaEffectCloud", true);
    public static final EntityType MINECART               = new EntityType("MINECART", Minecart.class, "Minecart", true);
    public static final EntityType MINECART_CHEST         = new EntityType("MINECART_CHEST", MinecartChest.class, "MinecartChest", true);
    public static final EntityType MINECART_COMMAND_BLOCK = new EntityType("MINECART_COMMAND_BLOCK", MinecartCommandBlock.class, "MinecartCommandBlock", true);
    public static final EntityType MINECART_FURNACE       = new EntityType("MINECART_FURNACE", MinecartFurnace.class, "MinecartFurnace", true);
    public static final EntityType MINECART_HOPPER        = new EntityType("MINECART_HOPPER", MinecartHopper.class, "MinecartHopper", true);
    public static final EntityType MINECART_TNT           = new EntityType("MINECART_TNT", MinecartTNT.class, "MinecartTNT", true);
    public static final EntityType PRIMED_TNT             = new EntityType("PRIMED_TNT", PrimedTNT.class, "PrimedTnt", true);
    public static final EntityType ENDER_CRYSTAL          = new EntityType("ENDER_CRYSTAL", EnderCrystal.class, "EnderCrystal", true);
    public static final EntityType ARROW                  = new EntityType("ARROW", Arrow.class, "Arrow", true);
    public static final EntityType SNOWBALL               = new EntityType("SNOWBALL", Snowball.class, "Snowball", true);
    public static final EntityType EGG                    = new EntityType("EGG", Egg.class, "Egg", true);
    public static final EntityType FIREBALL               = new EntityType("FIREBALL", Fireball.class, "Fireball", true);
    public static final EntityType SMALL_FIREBALL         = new EntityType("SMALL_FIREBALL", SmallFireball.class, "SmallFireball", true);
    public static final EntityType THROWN_ENDERPEARL      = new EntityType("THROWN_ENDERPEARL", ThrownEnderpearl.class, "ThrownEnderpearl", true);
    public static final EntityType WITHER_SKULL           = new EntityType("WITHER_SKULL", WitherSkull.class, "WitherSkull", true);
    public static final EntityType SHULKER_BULLET         = new EntityType("SHULKER_BULLET", ShulkerBullet.class, "ShulkerBullet ", true);
    public static final EntityType FALLING_BLOCK          = new EntityType("FALLING_BLOCK", FallingBlock.class, "FallingSand", true);
    public static final EntityType ITEM_FRAME             = new EntityType("ITEM_FRAME", ItemFrame.class, "ItemFrame", true);
    public static final EntityType EYE_OF_ENDER_SIGNAL    = new EntityType("EYE_OF_ENDER_SIGNAL", EyeOfEnderSignal.class, "EyeOfEnderSignal", true);
    public static final EntityType THROWN_POTION          = new EntityType("THROWN_POTION", ThrownPotion.class, "ThrownPotion", true);
    public static final EntityType FALLING_DRAGON_EGG     = new EntityType("ZOMBIE", FallingDragonEgg.class, "FallingDragonEgg", true);
    public static final EntityType THROWN_EXP_BOTTLE      = new EntityType("THROWN_EXP_BOTTLE", ThrownExpBottle.class, "ThrownExpBottle", true);
    public static final EntityType FIREWORK_ROCKET        = new EntityType("FIREWORK_ROCKET", FireworksRocket.class, "FireworksRocketEntity", true);
    public static final EntityType LEASH_KNOT             = new EntityType("LEASH_KNOT", LeashKnot.class, "LeashKnot", true);
    public static final EntityType ARMOR_STAND            = new EntityType("ARMOR_STAND", ArmorStand.class, "ArmorStand", true);
    public static final EntityType FISHING_HOOK           = new EntityType("FISHING_HOOK", FishingHook.class, "FishingHook", true);
    public static final EntityType SPECTRAL_ARROW         = new EntityType("SPECTRAL_ARROW", SpectralArrow.class, "SpectralArrow", true);
    public static final EntityType TIPPED_ARROW           = new EntityType("TIPPED_ARROW", TippedArrow.class, "TippedArrow", true);
    public static final EntityType DRAGON_FIREBALL        = new EntityType("DRAGON_FIREBALL", DragonFireball.class, "DragonFireball", true);

    private final Class<? extends Entity> dioriteEntityClass;
    private final boolean                 living;
    private final String                  name;
    private final boolean                 isObject;

    public EntityType(final String enumName, final Class<? extends Entity> dioriteEntityClass, final String name, final boolean isObject)
    {
        super(enumName);
        this.dioriteEntityClass = dioriteEntityClass;
        this.isObject = isObject;
        this.living = LivingEntity.class.isAssignableFrom(dioriteEntityClass);
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public boolean isLiving()
    {
        return this.living;
    }

    public boolean isObject()
    {
        return this.isObject;
    }

    public Class<? extends Entity> getDioriteEntityClass()
    {
        return this.dioriteEntityClass;
    }

    /**
     * Register new {@link EntityType} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final EntityType element)
    {
        ASimpleEnum.register(EntityType.class, element);
    }

    /**
     * Get one of {@link EntityType} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static EntityType getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(EntityType.class, ordinal);
    }

    /**
     * Get one of {@link EntityType} entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static EntityType getByEnumName(final String name)
    {
        return getByEnumName(EntityType.class, name);
    }

    /**
     * @return all values in array.
     */
    public static EntityType[] values()
    {
        final Int2ObjectMap<EntityType> map = getByEnumOrdinal(EntityType.class);
        return map.values().toArray(new EntityType[map.size()]);
    }

    static
    {
        EntityType.register(PLAYER);
        EntityType.register(CREEPER);
        EntityType.register(SKELETON);
        EntityType.register(SPIDER);
        EntityType.register(GIANT);
        EntityType.register(ZOMBIE);
        EntityType.register(SLIME);
        EntityType.register(GHAST);
        EntityType.register(PIG_ZOMBIE);
        EntityType.register(ENDERMAN);
        EntityType.register(CAVE_SPIDER);
        EntityType.register(SILVERFISH);
        EntityType.register(BLAZE);
        EntityType.register(LAVA_SLIME);
        EntityType.register(ENDER_DRAGON);
        EntityType.register(WITHER);
        EntityType.register(BAT);
        EntityType.register(WITCH);
        EntityType.register(ENDERMITE);
        EntityType.register(GUARDIAN);
        EntityType.register(SHULKER);
        EntityType.register(PIG);
        EntityType.register(SHEEP);
        EntityType.register(COW);
        EntityType.register(CHICKEN);
        EntityType.register(SQUID);
        EntityType.register(WOLF);
        EntityType.register(MOOSHROOM_COW);
        EntityType.register(SNOWMAN);
        EntityType.register(OCELOT);
        EntityType.register(VILLAGER_GOLEM);
        EntityType.register(HORSE);
        EntityType.register(RABBIT);
        EntityType.register(VILLAGER);
        EntityType.register(BOAT);
        EntityType.register(ITEM);
        EntityType.register(AREA_EFFECT_CLOUD);
        EntityType.register(MINECART);
        EntityType.register(MINECART_CHEST);
        EntityType.register(MINECART_COMMAND_BLOCK);
        EntityType.register(MINECART_FURNACE);
        EntityType.register(MINECART_HOPPER);
        EntityType.register(MINECART_TNT);
        EntityType.register(PRIMED_TNT);
        EntityType.register(ENDER_CRYSTAL);
        EntityType.register(ARROW);
        EntityType.register(SNOWBALL);
        EntityType.register(EGG);
        EntityType.register(FIREBALL);
        EntityType.register(SMALL_FIREBALL);
        EntityType.register(THROWN_ENDERPEARL);
        EntityType.register(WITHER_SKULL);
        EntityType.register(SHULKER_BULLET);
        EntityType.register(FALLING_BLOCK);
        EntityType.register(ITEM_FRAME);
        EntityType.register(EYE_OF_ENDER_SIGNAL);
        EntityType.register(THROWN_POTION);
        EntityType.register(FALLING_DRAGON_EGG);
        EntityType.register(THROWN_EXP_BOTTLE);
        EntityType.register(FIREWORK_ROCKET);
        EntityType.register(LEASH_KNOT);
        EntityType.register(ARMOR_STAND);
        EntityType.register(FISHING_HOOK);
        EntityType.register(SPECTRAL_ARROW);
        EntityType.register(TIPPED_ARROW);
        EntityType.register(DRAGON_FIREBALL);
    }
}
