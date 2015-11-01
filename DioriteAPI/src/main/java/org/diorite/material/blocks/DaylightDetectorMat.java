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

import org.diorite.material.FuelMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Daylight Detector' block material in minecraft. <br>
 * ID of block: 151 <br>
 * String ID of block: minecraft:daylight_detector <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * 15:
 * Type name: '15' <br>
 * SubID: 15 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * 14:
 * Type name: '14' <br>
 * SubID: 14 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * 13:
 * Type name: '13' <br>
 * SubID: 13 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * 12:
 * Type name: '12' <br>
 * SubID: 12 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * 11:
 * Type name: '11' <br>
 * SubID: 11 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * 10:
 * Type name: '10' <br>
 * SubID: 10 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * 9:
 * Type name: '9' <br>
 * SubID: 9 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * 8:
 * Type name: '8' <br>
 * SubID: 8 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * 7:
 * Type name: '7' <br>
 * SubID: 7 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * 6:
 * Type name: '6' <br>
 * SubID: 6 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * 5:
 * Type name: '5' <br>
 * SubID: 5 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * 4:
 * Type name: '4' <br>
 * SubID: 4 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * 3:
 * Type name: '3' <br>
 * SubID: 3 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * 2:
 * Type name: '2' <br>
 * SubID: 2 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * 1:
 * Type name: '1' <br>
 * SubID: 1 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * OFF:
 * Type name: 'Off' <br>
 * SubID: 0 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class DaylightDetectorMat extends AbstractDaylightDetectorMat implements FuelMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 16;

    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_OFF = new DaylightDetectorMat();
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_1   = new DaylightDetectorMat(1);
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_2   = new DaylightDetectorMat(2);
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_3   = new DaylightDetectorMat(3);
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_4   = new DaylightDetectorMat(4);
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_5   = new DaylightDetectorMat(5);
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_6   = new DaylightDetectorMat(6);
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_7   = new DaylightDetectorMat(7);
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_8   = new DaylightDetectorMat(8);
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_9   = new DaylightDetectorMat(9);
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_10  = new DaylightDetectorMat(10);
    @SuppressWarnings("MagicNumber")
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_11  = new DaylightDetectorMat(11);
    @SuppressWarnings("MagicNumber")
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_12  = new DaylightDetectorMat(12);
    @SuppressWarnings("MagicNumber")
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_13  = new DaylightDetectorMat(13);
    @SuppressWarnings("MagicNumber")
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_14  = new DaylightDetectorMat(14);
    @SuppressWarnings("MagicNumber")
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_15  = new DaylightDetectorMat(15);

    private static final Map<String, DaylightDetectorMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DaylightDetectorMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected DaylightDetectorMat()
    {
        super("DAYLIGHT_DETECTOR", 151, "minecraft:daylight_detector", 0, 0.2f, 1);
    }

    protected DaylightDetectorMat(final int power)
    {
        super(DAYLIGHT_DETECTOR_OFF.name(), DAYLIGHT_DETECTOR_OFF.ordinal(), DAYLIGHT_DETECTOR_OFF.getMinecraftId(), power, DAYLIGHT_DETECTOR_OFF.getHardness(), DAYLIGHT_DETECTOR_OFF.getBlastResistance());
    }

    protected DaylightDetectorMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final int power, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, power, hardness, blastResistance);
    }

    @Override
    public DaylightDetectorMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DaylightDetectorMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public AbstractDaylightDetectorMat getInverted()
    {
        return DaylightDetectorInvertedMat.getDaylightDetectorInverted(this.power);
    }

    @Override
    public DaylightDetectorMat getPowerStrength(final int strength)
    {
        return DaylightDetectorMat.getDaylightDetector(this.power);
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public DaylightDetectorMat getPowered(final boolean powered)
    {
        return getByID(powered ? 15 : 0);
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public int getFuelPower()
    {
        return 1500;
    }

    /**
     * Returns one of DaylightDetector sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DaylightDetector or null
     */
    public static DaylightDetectorMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of DaylightDetector sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DaylightDetector or null
     */
    public static DaylightDetectorMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of DaylightDetector sub-type based on power strength emited by it.
     * It will never return null.
     *
     * @param strength power of emited signal.
     *
     * @return sub-type of DaylightDetector
     */
    public static DaylightDetectorMat getDaylightDetector(final int strength)
    {
        return getByID(POWER_RANGE.getIn(strength));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DaylightDetectorMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public DaylightDetectorMat[] types()
    {
        return DaylightDetectorMat.daylightDetectorTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DaylightDetectorMat[] daylightDetectorTypes()
    {
        return byID.values(new DaylightDetectorMat[byID.size()]);
    }

    static
    {
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_OFF);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_1);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_2);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_3);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_4);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_5);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_6);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_7);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_8);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_9);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_10);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_11);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_12);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_13);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_14);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_15);
    }
}
