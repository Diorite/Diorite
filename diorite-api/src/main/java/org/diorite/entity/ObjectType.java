/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
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
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

@SuppressWarnings({"ClassHasNoToStringMethod"})
public class ObjectType extends ASimpleEnum<ObjectType>
{
    static
    {
        init(ObjectType.class, 27);
    }

    public static final ObjectType BOAT                = new ObjectType("BOAT", Boat.class, 1, "Boat");
    public static final ObjectType ITEM                = new ObjectType("ITEM", Item.class, 2, "Item");
    public static final ObjectType AREA_EFFECT_CLOUD   = new ObjectType("AREA_EFFECT_CLOUD", AreaEffectCloud.class, 3, "AreaEffectCloud");
    public static final ObjectType MINECART            = new ObjectType("MINECART", Minecart.class, 10, "Minecart");
    public static final ObjectType PRIMED_TNT          = new ObjectType("PRIMED_TNT", PrimedTNT.class, 50, "PrimedTnt");
    public static final ObjectType ENDER_CRYSTAL       = new ObjectType("ENDER_CRYSTAL", EnderCrystal.class, 51, "EnderCrystal");
    public static final ObjectType ARROW               = new ObjectType("ARROW", Arrow.class, 60, "Arrow");
    public static final ObjectType SNOWBALL            = new ObjectType("SNOWBALL", Snowball.class, 61, "Snowball");
    public static final ObjectType EGG                 = new ObjectType("EGG", Egg.class, 62, "Egg");
    public static final ObjectType FIREBALL            = new ObjectType("FIREBALL", Fireball.class, 63, "Fireball");
    public static final ObjectType SMALL_FIREBALL      = new ObjectType("SMALL_FIREBALL", SmallFireball.class, 64, "SmallFireball");
    public static final ObjectType THROWN_ENDERPEARL   = new ObjectType("THROWN_ENDERPEARL", ThrownEnderpearl.class, 65, "ThrownEnderpearl");
    public static final ObjectType WITHER_SKULL        = new ObjectType("WITHER_SKULL", WitherSkull.class, 66, "WitherSkull");
    public static final ObjectType SHULKER_BULLET      = new ObjectType("SHULKER_BULLET", ShulkerBullet.class, 67, "ShulkerBullet ");
    public static final ObjectType FALLING_BLOCK       = new ObjectType("FALLING_BLOCK", FallingBlock.class, 70, "FallingSand");
    public static final ObjectType ITEM_FRAME          = new ObjectType("ITEM_FRAME", ItemFrame.class, 71, "ItemFrame");
    public static final ObjectType EYE_OF_ENDER_SIGNAL = new ObjectType("EYE_OF_ENDER_SIGNAL", EyeOfEnderSignal.class, 72, "EyeOfEnderSignal");
    public static final ObjectType THROWN_POTION       = new ObjectType("THROWN_POTION", ThrownPotion.class, 73, "ThrownPotion");
    public static final ObjectType FALLING_DRAGON_EGG  = new ObjectType("ZOMBIE", FallingDragonEgg.class, 74, "FallingDragonEgg");
    public static final ObjectType THROWN_EXP_BOTTLE   = new ObjectType("THROWN_EXP_BOTTLE", ThrownExpBottle.class, 75, "ThrownExpBottle");
    public static final ObjectType FIREWORK_ROCKET     = new ObjectType("FIREWORK_ROCKET", FireworksRocket.class, 76, "FireworksRocketEntity");
    public static final ObjectType LEASH_KNOT          = new ObjectType("LEASH_KNOT", LeashKnot.class, 77, "LeashKnot");
    public static final ObjectType ARMOR_STAND         = new ObjectType("ARMOR_STAND", ArmorStand.class, 78, "ArmorStand");
    public static final ObjectType FISHING_HOOK        = new ObjectType("FISHING_HOOK", FishingHook.class, 90, "FishingHook");
    public static final ObjectType SPECTRAL_ARROW      = new ObjectType("SPECTRAL_ARROW", SpectralArrow.class, 91, "SpectralArrow");
    public static final ObjectType TIPPED_ARROW        = new ObjectType("TIPPED_ARROW", TippedArrow.class, 92, "TippedArrow");
    public static final ObjectType DRAGON_FIREBALL     = new ObjectType("DRAGON_FIREBALL", DragonFireball.class, 93, "DragonFireball");

    private static final Int2ObjectMap<ObjectType> byMcId = new Int2ObjectOpenHashMap<>(27, .1f);

    private final Class<? extends ObjectEntity> dioriteEntityClass;
    private final boolean                       living;
    private final int                           mcId;
    private final String                        mcName;

    public ObjectType(final String enumName, final int enumId, final Class<? extends ObjectEntity> dioriteEntityClass, final boolean living, final int mcId, final String mcName)
    {
        super(enumName, enumId);
        this.dioriteEntityClass = dioriteEntityClass;
        this.living = living;
        this.mcId = mcId;
        this.mcName = mcName;
    }

    public ObjectType(final String enumName, final Class<? extends ObjectEntity> dioriteEntityClass, final int mcId, final String mcName)
    {
        super(enumName);
        this.dioriteEntityClass = dioriteEntityClass;
        this.living = LivingEntity.class.isAssignableFrom(dioriteEntityClass);
        this.mcId = mcId;
        this.mcName = mcName;
    }

    public String getMcName()
    {
        return this.mcName;
    }

    public int getMinecraftId()
    {
        return this.mcId;
    }

    public boolean isLiving()
    {
        return this.living;
    }

    public Class<? extends ObjectEntity> getDioriteEntityClass()
    {
        return this.dioriteEntityClass;
    }

    /**
     * Register new {@link ObjectType} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final ObjectType element)
    {
        ASimpleEnum.register(ObjectType.class, element);
        byMcId.put(element.getMinecraftId(), element);
    }

    /**
     * Get one of {@link ObjectType} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static ObjectType getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(ObjectType.class, ordinal);
    }

    /**
     * Get one of {@link ObjectType} entry by its mc id.
     *
     * @param id mc id of entry.
     *
     * @return one of entry or null.
     */
    public static ObjectType getByMinecraftId(final int id)
    {
        return byMcId.get(id);
    }

    /**
     * Get one of {@link ObjectType} entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static ObjectType getByEnumName(final String name)
    {
        return getByEnumName(ObjectType.class, name);
    }

    /**
     * @return all values in array.
     */
    public static ObjectType[] values()
    {
        final Int2ObjectMap<ObjectType> map = getByEnumOrdinal(ObjectType.class);
        return map.values().toArray(new ObjectType[map.size()]);
    }

    static
    {
        ObjectType.register(BOAT);
        ObjectType.register(ITEM);
        ObjectType.register(AREA_EFFECT_CLOUD);
        ObjectType.register(MINECART);
        ObjectType.register(PRIMED_TNT);
        ObjectType.register(ENDER_CRYSTAL);
        ObjectType.register(ARROW);
        ObjectType.register(SNOWBALL);
        ObjectType.register(EGG);
        ObjectType.register(FIREBALL);
        ObjectType.register(SMALL_FIREBALL);
        ObjectType.register(THROWN_ENDERPEARL);
        ObjectType.register(WITHER_SKULL);
        ObjectType.register(SHULKER_BULLET);
        ObjectType.register(FALLING_BLOCK);
        ObjectType.register(ITEM_FRAME);
        ObjectType.register(EYE_OF_ENDER_SIGNAL);
        ObjectType.register(THROWN_POTION);
        ObjectType.register(FALLING_DRAGON_EGG);
        ObjectType.register(THROWN_EXP_BOTTLE);
        ObjectType.register(FIREWORK_ROCKET);
        ObjectType.register(LEASH_KNOT);
        ObjectType.register(ARMOR_STAND);
        ObjectType.register(FISHING_HOOK);
        ObjectType.register(SPECTRAL_ARROW);
        ObjectType.register(TIPPED_ARROW);
        ObjectType.register(DRAGON_FIREBALL);
    }
}
