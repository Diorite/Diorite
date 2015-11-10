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

package org.diorite.firework;

import org.diorite.utils.SimpleEnum;
import org.diorite.utils.SimpleEnum.ASimpleEnum;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Enum with possible firework types.
 */
@SuppressWarnings("ClassHasNoToStringMethod")
public class FireworkEffectType extends ASimpleEnum<FireworkEffectType>
{
    static
    {
        init(FireworkEffectType.class, 5);
    }

    /**
     * A small ball effect.
     */
    public static final FireworkEffectType BALL       = new FireworkEffectType("BALL", 0);
    /**
     * A large ball effect.
     */
    public static final FireworkEffectType BALL_LARGE = new FireworkEffectType("BALL_LARGE", 1);
    /**
     * A star-shaped effect.
     */
    public static final FireworkEffectType STAR       = new FireworkEffectType("STAR", 2);
    /**
     * A burst effect.
     */
    public static final FireworkEffectType BURST      = new FireworkEffectType("BURST", 3);
    /**
     * A creeper-face effect.
     */
    public static final FireworkEffectType CREEPER    = new FireworkEffectType("CREEPER", 4);

    private static final TByteObjectMap<FireworkEffectType> byTypeID = new TByteObjectHashMap<>(5, SMALL_LOAD_FACTOR, (byte) - 1);

    /**
     * ID for this firework effect type.
     */
    protected final byte typeID;

    /**
     * Construct new FireworkEffectType.
     *
     * @param enumName enum name of type.
     * @param enumId   enum id of type.
     * @param typeID   id of this type.
     */
    public FireworkEffectType(final String enumName, final int enumId, final int typeID)
    {
        super(enumName, enumId);
        this.typeID = (byte) typeID;
    }

    /**
     * Construct new FireworkEffectType.
     *
     * @param enumName enum name of type.
     * @param typeID   id of this type.
     */
    public FireworkEffectType(final String enumName, final int typeID)
    {
        super(enumName);
        this.typeID = (byte) typeID;
    }

    /**
     * Returns id of this firework type.
     *
     * @return id of this firework type.
     */
    public byte getTypeID()
    {
        return this.typeID;
    }

    /**
     * Register new {@link FireworkEffectType} entry in this enum.
     *
     * @param element new element to register.
     */
    public static void register(final FireworkEffectType element)
    {
        ASimpleEnum.register(FireworkEffectType.class, element);
        byTypeID.put(element.typeID, element);
    }

    /**
     * Get one of {@link FireworkEffectType} entry by its ordinal id.
     *
     * @param ordinal ordinal id of entry.
     *
     * @return one of entry or null.
     */
    public static FireworkEffectType getByEnumOrdinal(final int ordinal)
    {
        return getByEnumOrdinal(FireworkEffectType.class, ordinal);
    }

    /**
     * Get one of FireworkEffectType entry by its name.
     *
     * @param name name of entry.
     *
     * @return one of entry or null.
     */
    public static FireworkEffectType getByEnumName(final String name)
    {
        return getByEnumName(FireworkEffectType.class, name);
    }

    /**
     * Get one of FireworkEffectType entry by its type id.
     *
     * @param type type id of entry.
     *
     * @return one of entry or null.
     */
    public static FireworkEffectType getByTypeID(final int type)
    {
        return byTypeID.get((byte) type);
    }

    /**
     * @return all values in array.
     */
    public static FireworkEffectType[] values()
    {
        final TIntObjectMap<SimpleEnum<?>> map = getByEnumOrdinal(FireworkEffectType.class);
        return (FireworkEffectType[]) map.values(new FireworkEffectType[map.size()]);
    }

    static
    {
        FireworkEffectType.register(BALL);
        FireworkEffectType.register(BALL_LARGE);
        FireworkEffectType.register(STAR);
        FireworkEffectType.register(BURST);
        FireworkEffectType.register(CREEPER);
    }
}