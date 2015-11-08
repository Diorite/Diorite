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

package org.diorite.effect;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

@SuppressWarnings("ClassHasNoToStringMethod")
public class StatusEffectType extends ASimpleEnum<StatusEffectType>
{
    static
    {
        //noinspection MagicNumber
        init(StatusEffectType.class, 27);
    }

    /**
     * Expands FOV Increases walking speed by 20% x level.
     */
    public static final StatusEffectType SPEED           = new StatusEffectType("SPEED", 1, false, 2);
    /**
     * Contracts FOV Decreases walking speed by 15% x level.
     */
    public static final StatusEffectType SLOWNESS        = new StatusEffectType("SLOWNESS", 2, false, 1);
    /**
     * Blocks break faster by 20% x level (arm swings faster), attack speed increases by 10% x level.
     */
    public static final StatusEffectType HASTE           = new StatusEffectType("HASTE", 3, false, 0);
    /**
     * Blocks break slower: speed is 30% of normal, time needed is multiplied by (3 1/3), attack speed decreases by 10% x level.
     */
    public static final StatusEffectType MINING_FATIGUE  = new StatusEffectType("MINING_FATIGUE", 4, false, 0);
    /**
     * Adds (3 × level) damage. (Strength II is +6, Strength III is +9, etc.)
     */
    public static final StatusEffectType STRENGTH        = new StatusEffectType("STRENGTH", 5, false, 2);
    /**
     * Healing is 2 × 2^level, Undead mobs are dealt 3 * 2^level damage.
     */
    public static final StatusEffectType INSTANT_HEAL    = new StatusEffectType("INSTANT_HEAL", 6, true, 0);
    /**
     * Damage is 3 × 2^level, Undead mobs are healed by 3 * 2^level.
     */
    public static final StatusEffectType INSTANT_DAMAGE  = new StatusEffectType("INSTANT_DAMAGE", 7, true, 0);
    /**
     * Increases jump height by about [level]/8+.46 per level. (Jump height is ([level]+4.2)^2/16, ignoring drag). Reduces fall damage by 1 each level.
     */
    public static final StatusEffectType JUMP_BOOST      = new StatusEffectType("JUMP_BOOST", 8, false, 0);
    /**
     * Wobbles and warps the screen.
     */
    public static final StatusEffectType NAUSEA          = new StatusEffectType("NAUSEA", 9, false, 0);
    /**
     * Regenerate 1 over time every 50 ticks/2.5 seconds. Time is divided by 2 and rounded down each level.
     */
    public static final StatusEffectType REGENERATION    = new StatusEffectType("REGENERATION", 10, false, 0);
    /**
     * Reduces damage by 20% with each additional level.
     */
    public static final StatusEffectType RESISTANCE      = new StatusEffectType("RESISTANCE", 11, false, 0);
    /**
     * Immunity to fire, lava and direct impact of blaze fireballs.
     */
    public static final StatusEffectType FIRE_RESISTANCE = new StatusEffectType("FIRE_RESISTANCE", 12, false, 0);
    /**
     * Prevents the oxygen bar from decreasing. Slightly increases visibility while underwater.
     */
    public static final StatusEffectType WATER_BREATHING = new StatusEffectType("WATER_BREATHING", 13, false, 0);
    /**
     * Causes the entity model to disappear. Mobs will not attack/sense the player until the player is much closer than normal.
     */
    public static final StatusEffectType INVISIBILITY    = new StatusEffectType("INVISIBILITY", 14, false, 0);
    /**
     * Creates thick black fog around the player. Prevents sprinting and critical hits.
     */
    public static final StatusEffectType BLINDNESS       = new StatusEffectType("BLINDNESS", 15, false, 0);
    /**
     * Increases brightness to 15 (full) everywhere. Increases ability to see underwater.
     */
    public static final StatusEffectType NIGHT_VISION    = new StatusEffectType("NIGHT_VISION", 16, false, 0);
    /**
     * Food exhaustion is 0.025 × level per tick.
     */
    public static final StatusEffectType HUNGER          = new StatusEffectType("HUNGER", 17, false, 0);
    /**
     * Decreases damage dealt with melee attacks by 4 × level.
     */
    public static final StatusEffectType WEAKNESS        = new StatusEffectType("WEAKNESS", 18, false, 0);
    /**
     * Does 1 damage every 25 ticks/1.25 seconds, but won't reduce health below 1. Time is divided by 2 and rounded down each level.
     */
    public static final StatusEffectType POISON          = new StatusEffectType("POISON", 19, false, 0);
    /**
     * Like potion but it can kill.
     */
    public static final StatusEffectType WITHER          = new StatusEffectType("WITHER", 20, false, 0);
    /**
     * Adds 4 × level base health.
     */
    public static final StatusEffectType HEALTH_BOOST    = new StatusEffectType("HEALTH_BOOST", 21, false, 0);
    /**
     * Adds 4 × level absorption health, that cannot be replenished by natural regeneration or other effects. Absorption health vanish when the effect ends.
     */
    public static final StatusEffectType ABSORPTION      = new StatusEffectType("ABSORPTION", 22, false, 0);
    /**
     * Replenishes 1 hunger × level per tick.
     */
    public static final StatusEffectType SATURATION      = new StatusEffectType("SATURATION", 23, false, 0);
    /**
     * Causes entities to glow with an outline that can be seen through opaque blocks.
     */
    public static final StatusEffectType GLOWING         = new StatusEffectType("GLOWING", 24, false, 0);
    /**
     * Causes entities to involuntarily rise upwards until the effect runs out.
     */
    public static final StatusEffectType LEVITATION      = new StatusEffectType("LEVITATION", 25, false, 0);
    /**
     * Increases chance of getting high-quality loot
     */
    public static final StatusEffectType LUCK            = new StatusEffectType("LUCK", 26, false, 0);
    /**
     * Reduces chance of getting high-quality loot
     */
    public static final StatusEffectType BAD_LUCK        = new StatusEffectType("BAD_LUCK", 27, false, 0);

    private static final TByteObjectMap<StatusEffectType> byTypeID = new TByteObjectHashMap<>(5, SMALL_LOAD_FACTOR, (byte) - 1);

    /**
     * Id of potion effect type.
     */
    protected final byte    typeID;
    /**
     * If potion has instant efect, like heal/damage potions.
     */
    protected final boolean instant;
    /**
     * Max vanilla potion level, 0 if effect can't be crafted in potion.
     */
    protected final int     maxPotionLevel;

    /**
     * Construct new PotionEffectType.
     *
     * @param enumName       enum name of type.
     * @param enumId         enum id of type.
     * @param typeID         id of this type.
     * @param instant        if potion has instant efect, like heal/damage potions.
     * @param maxPotionLevel max vanilla potion level, 0 if effect can't be crafted in potion.
     */
    public StatusEffectType(final String enumName, final int enumId, final int typeID, final boolean instant, final int maxPotionLevel)
    {
        super(enumName, enumId);
        this.typeID = (byte) typeID;
        this.instant = instant;
        this.maxPotionLevel = maxPotionLevel;
    }

    /**
     * Construct new PotionEffectType.
     *
     * @param enumName       enum name of type.
     * @param typeID         id of this type.
     * @param instant        if potion has instant efect, like heal/damage potions.
     * @param maxPotionLevel max vanilla potion level, 0 if effect can't be crafted in potion.
     */
    public StatusEffectType(final String enumName, final int typeID, final boolean instant, final int maxPotionLevel)
    {
        super(enumName);
        this.typeID = (byte) typeID;
        this.instant = instant;
        this.maxPotionLevel = maxPotionLevel;
    }

    /**
     * Returns type id of this status effect type.
     *
     * @return type id of this status effect type.
     */
    public byte getTypeID()
    {
        return this.typeID;
    }

    /**
     * Register new {@link StatusEffectType} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final StatusEffectType element)
    {
        ASimpleEnum.register(StatusEffectType.class, element);
        byTypeID.put(element.typeID, element);
    }

    /**
     * Get one of {@link StatusEffectType} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static StatusEffectType getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(StatusEffectType.class, ordinal);
    }

    /**
     * Get one of PotionEffectType entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static StatusEffectType getByEnumName(final String name)
    {
        return getByEnumName(StatusEffectType.class, name);
    }

    /**
     * Get one of PotionEffectType entry by its type id.
     *
     * @param type type id of entry.
     *
     * @return one of entry or null.
     */
    public static StatusEffectType getByTypeID(final int type)
    {
        return byTypeID.get((byte) type);
    }

    /**
     * @return all values in array.
     */
    public static StatusEffectType[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(StatusEffectType.class);
        return (StatusEffectType[]) map.values(new StatusEffectType[map.size()]);
    }

    static
    {
        StatusEffectType.register(SPEED);
        StatusEffectType.register(SLOWNESS);
        StatusEffectType.register(HASTE);
        StatusEffectType.register(MINING_FATIGUE);
        StatusEffectType.register(STRENGTH);
        StatusEffectType.register(INSTANT_HEAL);
        StatusEffectType.register(INSTANT_DAMAGE);
        StatusEffectType.register(JUMP_BOOST);
        StatusEffectType.register(NAUSEA);
        StatusEffectType.register(REGENERATION);
        StatusEffectType.register(RESISTANCE);
        StatusEffectType.register(FIRE_RESISTANCE);
        StatusEffectType.register(WATER_BREATHING);
        StatusEffectType.register(INVISIBILITY);
        StatusEffectType.register(BLINDNESS);
        StatusEffectType.register(NIGHT_VISION);
        StatusEffectType.register(HUNGER);
        StatusEffectType.register(WEAKNESS);
        StatusEffectType.register(POISON);
        StatusEffectType.register(WITHER);
        StatusEffectType.register(HEALTH_BOOST);
        StatusEffectType.register(ABSORPTION);
        StatusEffectType.register(SATURATION);
        StatusEffectType.register(GLOWING);
        StatusEffectType.register(LEVITATION);
        StatusEffectType.register(BAD_LUCK);
    }
}