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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Pumpkin Stem' block material in minecraft. <br>
 * ID of block: 104 <br>
 * String ID of block: minecraft:pumpkin_stem <br>
 * This block can't be used in inventory, valid material for this block: 'Pumpkin Seeds' (minecraft:pumpkin_seeds(361):0) <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * RIPE:
 * Type name: 'Ripe' <br>
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
public class PumpkinStemMat extends PlantStemMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 8;

    public static final PumpkinStemMat PUMPKIN_STEM_0     = new PumpkinStemMat();
    public static final PumpkinStemMat PUMPKIN_BLOCK_1    = new PumpkinStemMat("1", 0x1);
    public static final PumpkinStemMat PUMPKIN_BLOCK_2    = new PumpkinStemMat("2", 0x2);
    public static final PumpkinStemMat PUMPKIN_BLOCK_3    = new PumpkinStemMat("3", 0x3);
    public static final PumpkinStemMat PUMPKIN_BLOCK_4    = new PumpkinStemMat("4", 0x4);
    public static final PumpkinStemMat PUMPKIN_BLOCK_5    = new PumpkinStemMat("5", 0x5);
    public static final PumpkinStemMat PUMPKIN_BLOCK_6    = new PumpkinStemMat("6", 0x6);
    public static final PumpkinStemMat PUMPKIN_BLOCK_RIPE = new PumpkinStemMat("RIPE", 0x7);

    private static final Map<String, PumpkinStemMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PumpkinStemMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final int age;

    @SuppressWarnings("MagicNumber")
    protected PumpkinStemMat()
    {
        super("PUMPKIN_STEM", 104, "minecraft:pumpkin_stem", "0", (byte) 0x00, 0, 0);
        this.age = 0;
    }

    protected PumpkinStemMat(final String enumName, final int age)
    {
        super(PUMPKIN_STEM_0.name(), PUMPKIN_STEM_0.ordinal(), PUMPKIN_STEM_0.getMinecraftId(), enumName, (byte) age, PUMPKIN_STEM_0.getHardness(), PUMPKIN_STEM_0.getBlastResistance());
        this.age = age;
    }

    protected PumpkinStemMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final int age, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.age = age;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return PUMPKIN_SEEDS;
    }

    @Override
    public int getAge()
    {
        return this.age;
    }

    @Override
    public PumpkinStemMat getAge(final int age)
    {
        return getByID(age);
    }

    @Override
    public PumpkinStemMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PumpkinStemMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("age", this.age).toString();
    }

    /**
     * Returns one of PumpkinStem sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of PumpkinStem or null
     */
    public static PumpkinStemMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of PumpkinStem sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of PumpkinStem or null
     */
    public static PumpkinStemMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of PumpkinStem sub-type based on age.
     * It will never return null.
     *
     * @param age age of PumpkinStem.
     *
     * @return sub-type of PumpkinStem
     */
    public static PumpkinStemMat getPumpkinStem(final int age)
    {
        final PumpkinStemMat pumpkinStem = getByID(age);
        if (pumpkinStem == null)
        {
            return PUMPKIN_STEM_0;
        }
        return pumpkinStem;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PumpkinStemMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public PumpkinStemMat[] types()
    {
        return PumpkinStemMat.pumpkinStemTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static PumpkinStemMat[] pumpkinStemTypes()
    {
        return byID.values(new PumpkinStemMat[byID.size()]);
    }

    static
    {
        PumpkinStemMat.register(PUMPKIN_STEM_0);
        PumpkinStemMat.register(PUMPKIN_BLOCK_1);
        PumpkinStemMat.register(PUMPKIN_BLOCK_2);
        PumpkinStemMat.register(PUMPKIN_BLOCK_3);
        PumpkinStemMat.register(PUMPKIN_BLOCK_4);
        PumpkinStemMat.register(PUMPKIN_BLOCK_5);
        PumpkinStemMat.register(PUMPKIN_BLOCK_6);
        PumpkinStemMat.register(PUMPKIN_BLOCK_RIPE);
    }
}
