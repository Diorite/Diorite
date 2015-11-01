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

import org.diorite.utils.collections.maps.CaseInsensitiveMap;
import org.diorite.utils.math.DioriteMathUtils;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Iron Pressure Plate' block material in minecraft. <br>
 * ID of block: 148 <br>
 * String ID of block: minecraft:heavy_weighted_pressure_plate <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * 15:
 * Type name: '15' <br>
 * SubID: 15 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * 14:
 * Type name: '14' <br>
 * SubID: 14 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * 13:
 * Type name: '13' <br>
 * SubID: 13 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * 12:
 * Type name: '12' <br>
 * SubID: 12 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * 11:
 * Type name: '11' <br>
 * SubID: 11 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * 10:
 * Type name: '10' <br>
 * SubID: 10 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * 9:
 * Type name: '9' <br>
 * SubID: 9 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * 8:
 * Type name: '8' <br>
 * SubID: 8 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * 7:
 * Type name: '7' <br>
 * SubID: 7 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * 6:
 * Type name: '6' <br>
 * SubID: 6 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * 5:
 * Type name: '5' <br>
 * SubID: 5 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * 4:
 * Type name: '4' <br>
 * SubID: 4 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * 3:
 * Type name: '3' <br>
 * SubID: 3 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * 2:
 * Type name: '2' <br>
 * SubID: 2 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * 1:
 * Type name: '1' <br>
 * SubID: 1 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * 0:
 * Type name: '0' <br>
 * SubID: 0 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class IronPressurePlateMat extends WeightedPressurePlateMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 16;

    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_0  = new IronPressurePlateMat();
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_1  = new IronPressurePlateMat(0x1);
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_2  = new IronPressurePlateMat(0x2);
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_3  = new IronPressurePlateMat(0x3);
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_4  = new IronPressurePlateMat(0x4);
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_5  = new IronPressurePlateMat(0x5);
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_6  = new IronPressurePlateMat(0x6);
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_7  = new IronPressurePlateMat(0x7);
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_8  = new IronPressurePlateMat(0x8);
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_9  = new IronPressurePlateMat(0x9);
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_10 = new IronPressurePlateMat(0xA);
    @SuppressWarnings("MagicNumber")
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_11 = new IronPressurePlateMat(0xB);
    @SuppressWarnings("MagicNumber")
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_12 = new IronPressurePlateMat(0xC);
    @SuppressWarnings("MagicNumber")
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_13 = new IronPressurePlateMat(0xD);
    @SuppressWarnings("MagicNumber")
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_14 = new IronPressurePlateMat(0xE);
    @SuppressWarnings("MagicNumber")
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_15 = new IronPressurePlateMat(0xF);

    private static final Map<String, IronPressurePlateMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<IronPressurePlateMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected IronPressurePlateMat()
    {
        super("IRON_PRESSURE_PLATE", 148, "minecraft:heavy_weighted_pressure_plate", "0", (byte) 0x00, false, 0.5f, 2.5f);
    }

    protected IronPressurePlateMat(final int type)
    {
        super(IRON_PRESSURE_PLATE_0.name(), IRON_PRESSURE_PLATE_0.ordinal(), IRON_PRESSURE_PLATE_0.getMinecraftId(), Integer.toString(type), (byte) type, true, IRON_PRESSURE_PLATE_0.getHardness(), IRON_PRESSURE_PLATE_0.getBlastResistance());
    }

    protected IronPressurePlateMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final boolean powered, final int power, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, powered, power, hardness, blastResistance);
    }

    @Override
    public IronPressurePlateMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public IronPressurePlateMat getType(final int id)
    {
        return getByID(id);
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public IronPressurePlateMat getPowerStrength(final int strength)
    {
        return getByID(DioriteMathUtils.getInRange(strength, 0, 15));
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public IronPressurePlateMat getPowered(final boolean powered)
    {
        return getByID(powered ? 15 : 0);
    }

    /**
     * Returns one of IronPressurePlate sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of IronPressurePlate or null
     */
    public static IronPressurePlateMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of IronPressurePlate sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of IronPressurePlate or null
     */
    public static IronPressurePlateMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of IronPressurePlate sub-type based on emitted power.
     * It will never return null.
     *
     * @param power power that should be emitted by plate.
     *
     * @return sub-type of IronPressurePlate
     */
    @SuppressWarnings("MagicNumber")
    public static IronPressurePlateMat getIronPressurePlate(final int power)
    {
        return getByID(DioriteMathUtils.getInRange(power, 0, 15));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final IronPressurePlateMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public IronPressurePlateMat[] types()
    {
        return IronPressurePlateMat.ironPressurePlateTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static IronPressurePlateMat[] ironPressurePlateTypes()
    {
        return byID.values(new IronPressurePlateMat[byID.size()]);
    }

    static
    {
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_0);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_1);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_2);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_3);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_4);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_5);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_6);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_7);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_8);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_9);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_10);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_11);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_12);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_13);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_14);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_15);
    }
}
