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

import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Daylight Detector Inverted' block material in minecraft. <br>
 * ID of block: 178 <br>
 * String ID of block: minecraft:daylight_detector_inverted <br>
 * This block can't be used in inventory, valid material for this block: 'Daylight Detector'/'Off' (minecraft:daylight_detector(151):0) <br>
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
public class DaylightDetectorInvertedMat extends AbstractDaylightDetectorMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 16;

    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_OFF = new DaylightDetectorInvertedMat();
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_1   = new DaylightDetectorInvertedMat(1);
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_2   = new DaylightDetectorInvertedMat(2);
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_3   = new DaylightDetectorInvertedMat(3);
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_4   = new DaylightDetectorInvertedMat(4);
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_5   = new DaylightDetectorInvertedMat(5);
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_6   = new DaylightDetectorInvertedMat(6);
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_7   = new DaylightDetectorInvertedMat(7);
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_8   = new DaylightDetectorInvertedMat(8);
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_9   = new DaylightDetectorInvertedMat(9);
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_10  = new DaylightDetectorInvertedMat(10);
    @SuppressWarnings("MagicNumber")
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_11  = new DaylightDetectorInvertedMat(11);
    @SuppressWarnings("MagicNumber")
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_12  = new DaylightDetectorInvertedMat(12);
    @SuppressWarnings("MagicNumber")
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_13  = new DaylightDetectorInvertedMat(13);
    @SuppressWarnings("MagicNumber")
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_14  = new DaylightDetectorInvertedMat(14);
    @SuppressWarnings("MagicNumber")
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_15  = new DaylightDetectorInvertedMat(15);

    private static final Map<String, DaylightDetectorInvertedMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DaylightDetectorInvertedMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected DaylightDetectorInvertedMat()
    {
        super("DAYLIGHT_DETECTOR_INVERTED", 178, "minecraft:daylight_detector_inverted", 0, 0.2f, 1);
    }

    protected DaylightDetectorInvertedMat(final int power)
    {
        super(DAYLIGHT_DETECTOR_INVERTED_OFF.name(), DAYLIGHT_DETECTOR_INVERTED_OFF.ordinal(), DAYLIGHT_DETECTOR_INVERTED_OFF.getMinecraftId(), power, DAYLIGHT_DETECTOR_INVERTED_OFF.getHardness(), DAYLIGHT_DETECTOR_INVERTED_OFF.getBlastResistance());
    }

    protected DaylightDetectorInvertedMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final int power, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, power, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return DAYLIGHT_DETECTOR;
    }

    @Override
    public DaylightDetectorInvertedMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DaylightDetectorInvertedMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public AbstractDaylightDetectorMat getInverted()
    {
        return DaylightDetectorMat.getDaylightDetector(this.power);
    }

    @Override
    public DaylightDetectorInvertedMat getPowerStrength(final int strength)
    {
        return getByID(POWER_RANGE.getIn(strength));
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public DaylightDetectorInvertedMat getPowered(final boolean powered)
    {
        return getByID(powered ? 15 : 0);
    }

    /**
     * Returns one of DaylightDetectorInverted sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DaylightDetectorInverted or null
     */
    public static DaylightDetectorInvertedMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of DaylightDetectorInverted sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DaylightDetectorInverted or null
     */
    public static DaylightDetectorInvertedMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of DaylightDetectorInverted sub-type based on power strength emited by it.
     * It will never return null.
     *
     * @param strength power of emited signal.
     *
     * @return sub-type of DaylightDetectorInverted
     */
    public static DaylightDetectorInvertedMat getDaylightDetectorInverted(final int strength)
    {
        return getByID(POWER_RANGE.getIn(strength));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DaylightDetectorInvertedMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public DaylightDetectorInvertedMat[] types()
    {
        return DaylightDetectorInvertedMat.daylightDetectorInvertedTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DaylightDetectorInvertedMat[] daylightDetectorInvertedTypes()
    {
        return byID.values(new DaylightDetectorInvertedMat[byID.size()]);
    }

    static
    {
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_OFF);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_1);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_2);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_3);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_4);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_5);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_6);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_7);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_8);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_9);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_10);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_11);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_12);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_13);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_14);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_15);
    }
}
