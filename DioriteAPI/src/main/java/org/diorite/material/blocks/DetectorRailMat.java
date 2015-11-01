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
import org.diorite.material.PowerableMat;
import org.diorite.material.blocks.WaterLilyMat.RailTypeMat;
import org.diorite.material.blocks.WaterLilyMat.RailsMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Detector Rail' block material in minecraft. <br>
 * ID of block: 28 <br>
 * String ID of block: minecraft:detector_rail <br>
 * Hardness: 0,7 <br>
 * Blast Resistance 3,5 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * ASCENDING_SOUTH_POWERED:
 * Type name: 'Ascending South Powered' <br>
 * SubID: 13 <br>
 * Hardness: 0,7 <br>
 * Blast Resistance 3,5 <br>
 * </li>
 * <li>
 * ASCENDING_NORTH_POWERED:
 * Type name: 'Ascending North Powered' <br>
 * SubID: 12 <br>
 * Hardness: 0,7 <br>
 * Blast Resistance 3,5 <br>
 * </li>
 * <li>
 * ASCENDING_WEST_POWERED:
 * Type name: 'Ascending West Powered' <br>
 * SubID: 11 <br>
 * Hardness: 0,7 <br>
 * Blast Resistance 3,5 <br>
 * </li>
 * <li>
 * ASCENDING_EAST_POWERED:
 * Type name: 'Ascending East Powered' <br>
 * SubID: 10 <br>
 * Hardness: 0,7 <br>
 * Blast Resistance 3,5 <br>
 * </li>
 * <li>
 * FLAT_WEST_EAST_POWERED:
 * Type name: 'Flat West East Powered' <br>
 * SubID: 9 <br>
 * Hardness: 0,7 <br>
 * Blast Resistance 3,5 <br>
 * </li>
 * <li>
 * ASCENDING_SOUTH:
 * Type name: 'Ascending South' <br>
 * SubID: 5 <br>
 * Hardness: 0,7 <br>
 * Blast Resistance 3,5 <br>
 * </li>
 * <li>
 * ASCENDING_NORTH:
 * Type name: 'Ascending North' <br>
 * SubID: 4 <br>
 * Hardness: 0,7 <br>
 * Blast Resistance 3,5 <br>
 * </li>
 * <li>
 * ASCENDING_WEST:
 * Type name: 'Ascending West' <br>
 * SubID: 3 <br>
 * Hardness: 0,7 <br>
 * Blast Resistance 3,5 <br>
 * </li>
 * <li>
 * ASCENDING_EAST:
 * Type name: 'Ascending East' <br>
 * SubID: 2 <br>
 * Hardness: 0,7 <br>
 * Blast Resistance 3,5 <br>
 * </li>
 * <li>
 * FLAT_WEST_EAST:
 * Type name: 'Flat West East' <br>
 * SubID: 1 <br>
 * Hardness: 0,7 <br>
 * Blast Resistance 3,5 <br>
 * </li>
 * <li>
 * NORTH_SOUTH:
 * Type name: 'North South' <br>
 * SubID: 0 <br>
 * Hardness: 0,7 <br>
 * Blast Resistance 3,5 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class DetectorRailMat extends RailsMat implements PowerableMat
{
    /**
     * Bit flag defining if rail is powered.
     * If bit is set to 0, then it isn't powered
     */
    public static final byte POWERED_FLAG     = 0x8;
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int  USED_DATA_VALUES = 12;

    public static final DetectorRailMat DETECTOR_RAIL_NORTH_SOUTH             = new DetectorRailMat();
    public static final DetectorRailMat DETECTOR_RAIL_WEST_EAST               = new DetectorRailMat(RailTypeMat.FLAT_WEST_EAST, false);
    public static final DetectorRailMat DETECTOR_RAIL_ASCENDING_EAST          = new DetectorRailMat(RailTypeMat.ASCENDING_EAST, false);
    public static final DetectorRailMat DETECTOR_RAIL_ASCENDING_WEST          = new DetectorRailMat(RailTypeMat.ASCENDING_WEST, false);
    public static final DetectorRailMat DETECTOR_RAIL_ASCENDING_NORTH         = new DetectorRailMat(RailTypeMat.ASCENDING_NORTH, false);
    public static final DetectorRailMat DETECTOR_RAIL_ASCENDING_SOUTH         = new DetectorRailMat(RailTypeMat.ASCENDING_SOUTH, false);
    public static final DetectorRailMat DETECTOR_RAIL_NORTH_SOUTH_POWERED     = new DetectorRailMat(RailTypeMat.FLAT_WEST_EAST, true);
    public static final DetectorRailMat DETECTOR_RAIL_WEST_EAST_POWERED       = new DetectorRailMat(RailTypeMat.FLAT_WEST_EAST, true);
    public static final DetectorRailMat DETECTOR_RAIL_ASCENDING_EAST_POWERED  = new DetectorRailMat(RailTypeMat.ASCENDING_EAST, true);
    public static final DetectorRailMat DETECTOR_RAIL_ASCENDING_WEST_POWERED  = new DetectorRailMat(RailTypeMat.ASCENDING_WEST, true);
    public static final DetectorRailMat DETECTOR_RAIL_ASCENDING_NORTH_POWERED = new DetectorRailMat(RailTypeMat.ASCENDING_NORTH, true);
    public static final DetectorRailMat DETECTOR_RAIL_ASCENDING_SOUTH_POWERED = new DetectorRailMat(RailTypeMat.ASCENDING_SOUTH, true);

    private static final Map<String, DetectorRailMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DetectorRailMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final boolean powered;

    @SuppressWarnings("MagicNumber")
    protected DetectorRailMat()
    {
        super("DETECTOR_RAIL", 28, "minecraft:detector_rail", "NORTH_SOUTH", RailTypeMat.FLAT_NORTH_SOUTH, (byte) 0x00, 0.7f, 3.5f);
        this.powered = false;
    }

    protected DetectorRailMat(final RailTypeMat type, final boolean powered)
    {
        super(DETECTOR_RAIL_NORTH_SOUTH.name(), DETECTOR_RAIL_NORTH_SOUTH.ordinal(), DETECTOR_RAIL_NORTH_SOUTH.getMinecraftId(), type.name() + (powered ? "_POWERED" : ""), type, powered ? POWERED_FLAG : 0x00, DETECTOR_RAIL_NORTH_SOUTH.getHardness(), DETECTOR_RAIL_NORTH_SOUTH.getBlastResistance());
        this.powered = powered;
    }

    protected DetectorRailMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final RailTypeMat railType, final boolean powered, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, railType, hardness, blastResistance);
        this.powered = powered;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.DETECTOR_RAIL;
    }

    @Override
    public boolean isPowered()
    {
        return this.powered;
    }

    @Override
    public DetectorRailMat getPowered(final boolean powered)
    {
        return getDetectorRail(this.railType, powered);
    }

    @Override
    public DetectorRailMat getRailType(final RailTypeMat railType)
    {
        return getDetectorRail(railType, this.powered);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("powered", this.powered).toString();
    }

    @Override
    public DetectorRailMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DetectorRailMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns sub-type of DetectorRail based on {@link RailTypeMat} and powered state.
     * It will never return null.
     *
     * @param railType type of rails
     * @param powered  if rails should be powered.
     *
     * @return sub-type of DetectorRail
     */
    public DetectorRailMat getType(final RailTypeMat railType, final boolean powered)
    {
        return getDetectorRail(railType, powered);
    }

    /**
     * Returns one of DetectorRail sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DetectorRail or null
     */
    public static DetectorRailMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of DetectorRail sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DetectorRail or null
     */
    public static DetectorRailMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns sub-type of DetectorRail based on {@link RailTypeMat} and powered state.
     * It will never return null.
     *
     * @param type      type of rails
     * @param isPowered if rails should be powered.
     *
     * @return sub-type of DetectorRail
     */
    public static DetectorRailMat getDetectorRail(final RailTypeMat type, final boolean isPowered)
    {
        byte flag = type.getFlag();
        if (flag >= POWERED_FLAG)
        {
            flag = RailTypeMat.FLAT_NORTH_SOUTH.getFlag();
        }
        return getByID(flag | (isPowered ? POWERED_FLAG : 0x00));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DetectorRailMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public DetectorRailMat[] types()
    {
        return DetectorRailMat.detectorRailTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DetectorRailMat[] detectorRailTypes()
    {
        return byID.values(new DetectorRailMat[byID.size()]);
    }

    static
    {
        DetectorRailMat.register(DETECTOR_RAIL_NORTH_SOUTH);
        DetectorRailMat.register(DETECTOR_RAIL_WEST_EAST);
        DetectorRailMat.register(DETECTOR_RAIL_ASCENDING_EAST);
        DetectorRailMat.register(DETECTOR_RAIL_ASCENDING_WEST);
        DetectorRailMat.register(DETECTOR_RAIL_ASCENDING_NORTH);
        DetectorRailMat.register(DETECTOR_RAIL_ASCENDING_SOUTH);
        DetectorRailMat.register(DETECTOR_RAIL_NORTH_SOUTH_POWERED);
        DetectorRailMat.register(DETECTOR_RAIL_WEST_EAST_POWERED);
        DetectorRailMat.register(DETECTOR_RAIL_ASCENDING_EAST_POWERED);
        DetectorRailMat.register(DETECTOR_RAIL_ASCENDING_WEST_POWERED);
        DetectorRailMat.register(DETECTOR_RAIL_ASCENDING_NORTH_POWERED);
        DetectorRailMat.register(DETECTOR_RAIL_ASCENDING_SOUTH_POWERED);
    }
}
