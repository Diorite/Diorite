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
 * Class representing 'Reeds Block' block material in minecraft. <br>
 * ID of block: 83 <br>
 * String ID of block: minecraft:reeds <br>
 * This block can't be used in inventory, valid material for this block: 'Reeds' (minecraft:reeds(338):0) <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * 15:
 * Type name: '15' <br>
 * SubID: 15 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 14:
 * Type name: '14' <br>
 * SubID: 14 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 13:
 * Type name: '13' <br>
 * SubID: 13 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 12:
 * Type name: '12' <br>
 * SubID: 12 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 11:
 * Type name: '11' <br>
 * SubID: 11 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 10:
 * Type name: '10' <br>
 * SubID: 10 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 9:
 * Type name: '9' <br>
 * SubID: 9 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 8:
 * Type name: '8' <br>
 * SubID: 8 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 7:
 * Type name: '7' <br>
 * SubID: 7 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 6:
 * Type name: '6' <br>
 * SubID: 6 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 5:
 * Type name: '5' <br>
 * SubID: 5 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 4:
 * Type name: '4' <br>
 * SubID: 4 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 3:
 * Type name: '3' <br>
 * SubID: 3 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 2:
 * Type name: '2' <br>
 * SubID: 2 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 1:
 * Type name: '1' <br>
 * SubID: 1 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * 0:
 * Type name: '0' <br>
 * SubID: 0 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class ReedsBlockMat extends PlantMat implements AgeableBlockMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 16;

    public static final ReedsBlockMat REEDS_BLOCK_0  = new ReedsBlockMat();
    public static final ReedsBlockMat REEDS_BLOCK_1  = new ReedsBlockMat(0x1);
    public static final ReedsBlockMat REEDS_BLOCK_2  = new ReedsBlockMat(0x2);
    public static final ReedsBlockMat REEDS_BLOCK_3  = new ReedsBlockMat(0x3);
    public static final ReedsBlockMat REEDS_BLOCK_4  = new ReedsBlockMat(0x4);
    public static final ReedsBlockMat REEDS_BLOCK_5  = new ReedsBlockMat(0x5);
    public static final ReedsBlockMat REEDS_BLOCK_6  = new ReedsBlockMat(0x6);
    public static final ReedsBlockMat REEDS_BLOCK_7  = new ReedsBlockMat(0x7);
    public static final ReedsBlockMat REEDS_BLOCK_8  = new ReedsBlockMat(0x8);
    public static final ReedsBlockMat REEDS_BLOCK_9  = new ReedsBlockMat(0x9);
    public static final ReedsBlockMat REEDS_BLOCK_10 = new ReedsBlockMat(0xA);
    @SuppressWarnings("MagicNumber")
    public static final ReedsBlockMat REEDS_BLOCK_11 = new ReedsBlockMat(0xB);
    @SuppressWarnings("MagicNumber")
    public static final ReedsBlockMat REEDS_BLOCK_12 = new ReedsBlockMat(0xC);
    @SuppressWarnings("MagicNumber")
    public static final ReedsBlockMat REEDS_BLOCK_13 = new ReedsBlockMat(0xD);
    @SuppressWarnings("MagicNumber")
    public static final ReedsBlockMat REEDS_BLOCK_14 = new ReedsBlockMat(0xE);
    @SuppressWarnings("MagicNumber")
    public static final ReedsBlockMat REEDS_BLOCK_15 = new ReedsBlockMat(0xF);

    private static final Map<String, ReedsBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<ReedsBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected ReedsBlockMat()
    {
        super("REEDS_BLOCK", 83, "minecraft:reeds", "0", (byte) 0x00, 0, 0);
    }

    protected ReedsBlockMat(final int age)
    {
        super(REEDS_BLOCK_0.name(), REEDS_BLOCK_0.ordinal(), REEDS_BLOCK_0.getMinecraftId(), Integer.toString(age), (byte) age, REEDS_BLOCK_0.getHardness(), REEDS_BLOCK_0.getBlastResistance());
    }

    protected ReedsBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return REEDS;
    }

    @Override
    public ReedsBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public ReedsBlockMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public int getAge()
    {
        return this.type;
    }

    @Override
    public ReedsBlockMat getAge(final int age)
    {
        return getByID(age);
    }

    /**
     * Returns one of Reeds sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Reeds or null
     */
    public static ReedsBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Reeds sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Reeds or null
     */
    public static ReedsBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Reeds sub-type based on age.
     * It will never return null.
     *
     * @param age age of Reeds.
     *
     * @return sub-type of Reeds
     */
    public static ReedsBlockMat getReeds(final int age)
    {
        final ReedsBlockMat reedsMat = getByID(age);
        if (reedsMat == null)
        {
            return REEDS_BLOCK_0;
        }
        return reedsMat;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final ReedsBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public ReedsBlockMat[] types()
    {
        return ReedsBlockMat.reedsTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static ReedsBlockMat[] reedsTypes()
    {
        return byID.values(new ReedsBlockMat[byID.size()]);
    }

    static
    {
        ReedsBlockMat.register(REEDS_BLOCK_0);
        ReedsBlockMat.register(REEDS_BLOCK_1);
        ReedsBlockMat.register(REEDS_BLOCK_2);
        ReedsBlockMat.register(REEDS_BLOCK_3);
        ReedsBlockMat.register(REEDS_BLOCK_4);
        ReedsBlockMat.register(REEDS_BLOCK_5);
        ReedsBlockMat.register(REEDS_BLOCK_6);
        ReedsBlockMat.register(REEDS_BLOCK_7);
        ReedsBlockMat.register(REEDS_BLOCK_8);
        ReedsBlockMat.register(REEDS_BLOCK_9);
        ReedsBlockMat.register(REEDS_BLOCK_10);
        ReedsBlockMat.register(REEDS_BLOCK_11);
        ReedsBlockMat.register(REEDS_BLOCK_12);
        ReedsBlockMat.register(REEDS_BLOCK_13);
        ReedsBlockMat.register(REEDS_BLOCK_14);
        ReedsBlockMat.register(REEDS_BLOCK_15);
    }
}
