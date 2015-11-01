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

package org.diorite.material.blocks;

import java.util.Map;

import org.diorite.material.AgeableBlockMat;
import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Cactus' block material in minecraft. <br>
 * ID of block: 81 <br>
 * String ID of block: minecraft:cactus <br>
 * Hardness: 0,4 <br>
 * Blast Resistance 2 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * 15:
 * Type name: '15' <br>
 * SubID: 15 <br>
 * Hardness: 0,4 <br>
 * Blast Resistance 2 <br>
 * </li>
 * <li>
 * 14:
 * Type name: '14' <br>
 * SubID: 14 <br>
 * Hardness: 0,4 <br>
 * Blast Resistance 2 <br>
 * </li>
 * <li>
 * 13:
 * Type name: '13' <br>
 * SubID: 13 <br>
 * Hardness: 0,4 <br>
 * Blast Resistance 2 <br>
 * </li>
 * <li>
 * 12:
 * Type name: '12' <br>
 * SubID: 12 <br>
 * Hardness: 0,4 <br>
 * Blast Resistance 2 <br>
 * </li>
 * <li>
 * 11:
 * Type name: '11' <br>
 * SubID: 11 <br>
 * Hardness: 0,4 <br>
 * Blast Resistance 2 <br>
 * </li>
 * <li>
 * 10:
 * Type name: '10' <br>
 * SubID: 10 <br>
 * Hardness: 0,4 <br>
 * Blast Resistance 2 <br>
 * </li>
 * <li>
 * 9:
 * Type name: '9' <br>
 * SubID: 9 <br>
 * Hardness: 0,4 <br>
 * Blast Resistance 2 <br>
 * </li>
 * <li>
 * 8:
 * Type name: '8' <br>
 * SubID: 8 <br>
 * Hardness: 0,4 <br>
 * Blast Resistance 2 <br>
 * </li>
 * <li>
 * 7:
 * Type name: '7' <br>
 * SubID: 7 <br>
 * Hardness: 0,4 <br>
 * Blast Resistance 2 <br>
 * </li>
 * <li>
 * 6:
 * Type name: '6' <br>
 * SubID: 6 <br>
 * Hardness: 0,4 <br>
 * Blast Resistance 2 <br>
 * </li>
 * <li>
 * 5:
 * Type name: '5' <br>
 * SubID: 5 <br>
 * Hardness: 0,4 <br>
 * Blast Resistance 2 <br>
 * </li>
 * <li>
 * 4:
 * Type name: '4' <br>
 * SubID: 4 <br>
 * Hardness: 0,4 <br>
 * Blast Resistance 2 <br>
 * </li>
 * <li>
 * 3:
 * Type name: '3' <br>
 * SubID: 3 <br>
 * Hardness: 0,4 <br>
 * Blast Resistance 2 <br>
 * </li>
 * <li>
 * 2:
 * Type name: '2' <br>
 * SubID: 2 <br>
 * Hardness: 0,4 <br>
 * Blast Resistance 2 <br>
 * </li>
 * <li>
 * 1:
 * Type name: '1' <br>
 * SubID: 1 <br>
 * Hardness: 0,4 <br>
 * Blast Resistance 2 <br>
 * </li>
 * <li>
 * 0:
 * Type name: '0' <br>
 * SubID: 0 <br>
 * Hardness: 0,4 <br>
 * Blast Resistance 2 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class CactusMat extends PlantMat implements AgeableBlockMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 16;

    public static final CactusMat CACTUS_0  = new CactusMat();
    public static final CactusMat CACTUS_1  = new CactusMat(0x1);
    public static final CactusMat CACTUS_2  = new CactusMat(0x2);
    public static final CactusMat CACTUS_3  = new CactusMat(0x3);
    public static final CactusMat CACTUS_4  = new CactusMat(0x4);
    public static final CactusMat CACTUS_5  = new CactusMat(0x5);
    public static final CactusMat CACTUS_6  = new CactusMat(0x6);
    public static final CactusMat CACTUS_7  = new CactusMat(0x7);
    public static final CactusMat CACTUS_8  = new CactusMat(0x8);
    public static final CactusMat CACTUS_9  = new CactusMat(0x9);
    public static final CactusMat CACTUS_10 = new CactusMat(0xA);
    @SuppressWarnings("MagicNumber")
    public static final CactusMat CACTUS_11 = new CactusMat(0xB);
    @SuppressWarnings("MagicNumber")
    public static final CactusMat CACTUS_12 = new CactusMat(0xC);
    @SuppressWarnings("MagicNumber")
    public static final CactusMat CACTUS_13 = new CactusMat(0xD);
    @SuppressWarnings("MagicNumber")
    public static final CactusMat CACTUS_14 = new CactusMat(0xE);
    @SuppressWarnings("MagicNumber")
    public static final CactusMat CACTUS_15 = new CactusMat(0xF);

    private static final Map<String, CactusMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<CactusMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected CactusMat()
    {
        super("CACTUS", 81, "minecraft:cactus", "0", (byte) 0x00, 0.4f, 2);
    }

    protected CactusMat(final int age)
    {
        super(CACTUS_0.name(), CACTUS_0.ordinal(), CACTUS_0.getMinecraftId(), Integer.toString(age), (byte) age, CACTUS_0.getHardness(), CACTUS_0.getBlastResistance());
    }

    protected CactusMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return CACTUS;
    }

    @Override
    public CactusMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public CactusMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public int getAge()
    {
        return this.type;
    }

    @Override
    public CactusMat getAge(final int age)
    {
        return getByID(age);
    }

    /**
     * Returns one of Cactus sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Cactus or null
     */
    public static CactusMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Cactus sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Cactus or null
     */
    public static CactusMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Cactus sub-type based on age.
     * It will never return null.
     *
     * @param age age of Cactus.
     *
     * @return sub-type of Cactus
     */
    public static CactusMat getCactus(final int age)
    {
        final CactusMat cactus = getByID(age);
        if (cactus == null)
        {
            return CACTUS_0;
        }
        return cactus;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CactusMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public CactusMat[] types()
    {
        return CactusMat.cactusTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static CactusMat[] cactusTypes()
    {
        return byID.values(new CactusMat[byID.size()]);
    }

    static
    {
        CactusMat.register(CACTUS_0);
        CactusMat.register(CACTUS_1);
        CactusMat.register(CACTUS_2);
        CactusMat.register(CACTUS_3);
        CactusMat.register(CACTUS_4);
        CactusMat.register(CACTUS_5);
        CactusMat.register(CACTUS_6);
        CactusMat.register(CACTUS_7);
        CactusMat.register(CACTUS_8);
        CactusMat.register(CACTUS_9);
        CactusMat.register(CACTUS_10);
        CactusMat.register(CACTUS_11);
        CactusMat.register(CACTUS_12);
        CactusMat.register(CACTUS_13);
        CactusMat.register(CACTUS_14);
        CactusMat.register(CACTUS_15);
    }
}
