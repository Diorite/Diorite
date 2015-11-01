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
 * Class representing 'Carrots Block' block material in minecraft. <br>
 * ID of block: 141 <br>
 * String ID of block: minecraft:carrots <br>
 * This block can't be used in inventory, valid material for this block: 'Carrot' (minecraft:carrot(391):0) <br>
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
public class CarrotsBlockMat extends CropsMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 8;

    public static final CarrotsBlockMat CARROTS_BLOCK_0    = new CarrotsBlockMat();
    public static final CarrotsBlockMat CARROTS_BLOCK_1    = new CarrotsBlockMat("1", 0x1);
    public static final CarrotsBlockMat CARROTS_BLOCK_2    = new CarrotsBlockMat("2", 0x2);
    public static final CarrotsBlockMat CARROTS_BLOCK_3    = new CarrotsBlockMat("3", 0x3);
    public static final CarrotsBlockMat CARROTS_BLOCK_4    = new CarrotsBlockMat("4", 0x4);
    public static final CarrotsBlockMat CARROTS_BLOCK_5    = new CarrotsBlockMat("5", 0x5);
    public static final CarrotsBlockMat CARROTS_BLOCK_6    = new CarrotsBlockMat("6", 0x6);
    public static final CarrotsBlockMat CARROTS_BLOCK_RIPE = new CarrotsBlockMat("RIPE", 0x7);

    private static final Map<String, CarrotsBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<CarrotsBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final int age;

    @SuppressWarnings("MagicNumber")
    protected CarrotsBlockMat()
    {
        super("CARROTS_BLOCK", 141, "minecraft:carrots", "0", (byte) 0x00, 0, 0);
        this.age = 0;
    }

    protected CarrotsBlockMat(final String enumName, final int age)
    {
        super(CARROTS_BLOCK_0.name(), CARROTS_BLOCK_0.ordinal(), CARROTS_BLOCK_0.getMinecraftId(), enumName, (byte) age, CARROTS_BLOCK_0.getHardness(), CARROTS_BLOCK_0.getBlastResistance());
        this.age = age;
    }

    protected CarrotsBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final int age, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.age = age;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return CARROT;
    }

    @Override
    public CarrotsBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public CarrotsBlockMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("age", this.age).toString();
    }

    @Override
    public int getAge()
    {
        return this.age;
    }

    @Override
    public CarrotsBlockMat getAge(final int age)
    {
        return getByID(age);
    }

    /**
     * Returns one of CarrotsBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of CarrotsBlock or null
     */
    public static CarrotsBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of CarrotsBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of CarrotsBlock or null
     */
    public static CarrotsBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of CarrotsBlock sub-type based on age.
     * It will never return null.
     *
     * @param age age of CarrotsBlock.
     *
     * @return sub-type of CarrotsBlock
     */
    public static CarrotsBlockMat getCarrotsBlock(final int age)
    {
        final CarrotsBlockMat carrotsBlock = getByID(age);
        if (carrotsBlock == null)
        {
            return CARROTS_BLOCK_0;
        }
        return carrotsBlock;
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final CarrotsBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public CarrotsBlockMat[] types()
    {
        return CarrotsBlockMat.carrotsBlockTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static CarrotsBlockMat[] carrotsBlockTypes()
    {
        return byID.values(new CarrotsBlockMat[byID.size()]);
    }

    static
    {
        CarrotsBlockMat.register(CARROTS_BLOCK_0);
        CarrotsBlockMat.register(CARROTS_BLOCK_1);
        CarrotsBlockMat.register(CARROTS_BLOCK_2);
        CarrotsBlockMat.register(CARROTS_BLOCK_3);
        CarrotsBlockMat.register(CARROTS_BLOCK_4);
        CarrotsBlockMat.register(CARROTS_BLOCK_5);
        CarrotsBlockMat.register(CARROTS_BLOCK_6);
        CarrotsBlockMat.register(CARROTS_BLOCK_RIPE);
    }
}
