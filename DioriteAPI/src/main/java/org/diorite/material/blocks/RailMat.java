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
import org.diorite.material.blocks.WaterLilyMat.RailTypeMat;
import org.diorite.material.blocks.WaterLilyMat.RailsMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Rail' block material in minecraft. <br>
 * ID of block: 66 <br>
 * String ID of block: minecraft:rail <br>
 * Hardness: 0,7 <br>
 * Blast Resistance 3,5 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * CURVED_NORTH_EAST:
 * Type name: 'Curved North East' <br>
 * SubID: 9 <br>
 * Hardness: 0,7 <br>
 * Blast Resistance 3,5 <br>
 * </li>
 * <li>
 * CURVED_NORTH_WEST:
 * Type name: 'Curved North West' <br>
 * SubID: 8 <br>
 * Hardness: 0,7 <br>
 * Blast Resistance 3,5 <br>
 * </li>
 * <li>
 * CURVED_SOUTH_WEST:
 * Type name: 'Curved South West' <br>
 * SubID: 7 <br>
 * Hardness: 0,7 <br>
 * Blast Resistance 3,5 <br>
 * </li>
 * <li>
 * CURVED_SOUTH_EAST:
 * Type name: 'Curved South East' <br>
 * SubID: 6 <br>
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
 * FLAT_NORTH_SOUTH:
 * Type name: 'Flat North South' <br>
 * SubID: 0 <br>
 * Hardness: 0,7 <br>
 * Blast Resistance 3,5 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class RailMat extends RailsMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 10;

    public static final RailMat RAIL_FLAT_NORTH_SOUTH  = new RailMat();
    public static final RailMat RAIL_FLAT_WEST_EAST    = new RailMat(RailTypeMat.FLAT_WEST_EAST);
    public static final RailMat RAIL_ASCENDING_EAST    = new RailMat(RailTypeMat.ASCENDING_EAST);
    public static final RailMat RAIL_ASCENDING_WEST    = new RailMat(RailTypeMat.ASCENDING_WEST);
    public static final RailMat RAIL_ASCENDING_NORTH   = new RailMat(RailTypeMat.ASCENDING_NORTH);
    public static final RailMat RAIL_ASCENDING_SOUTH   = new RailMat(RailTypeMat.ASCENDING_SOUTH);
    public static final RailMat RAIL_CURVED_SOUTH_EAST = new RailMat(RailTypeMat.CURVED_SOUTH_EAST);
    public static final RailMat RAIL_CURVED_SOUTH_WEST = new RailMat(RailTypeMat.CURVED_SOUTH_WEST);
    public static final RailMat RAIL_CURVED_NORTH_WEST = new RailMat(RailTypeMat.CURVED_NORTH_WEST);
    public static final RailMat RAIL_CURVED_NORTH_EAST = new RailMat(RailTypeMat.CURVED_NORTH_EAST);

    private static final Map<String, RailMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RailMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected RailMat()
    {
        super("RAIL", 66, "minecraft:rail", "FLAT_NORTH_SOUTH", RailTypeMat.FLAT_NORTH_SOUTH, (byte) 0x00, 0.7f, 3.5f);
    }

    protected RailMat(final RailTypeMat type)
    {
        super(RAIL_FLAT_NORTH_SOUTH.name(), RAIL_FLAT_NORTH_SOUTH.ordinal(), RAIL_FLAT_NORTH_SOUTH.getMinecraftId(), type.name(), type, (byte) 0x0, RAIL_FLAT_NORTH_SOUTH.getHardness(), RAIL_FLAT_NORTH_SOUTH.getBlastResistance());
    }

    protected RailMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final RailTypeMat railType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, railType, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.RAIL;
    }

    @Override
    public RailMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RailMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public RailMat getRailType(final RailTypeMat railType)
    {
        return getByID(railType.getFlag());
    }

    /**
     * Returns one of Rail sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Rail or null
     */
    public static RailMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Rail sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Rail or null
     */
    public static RailMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Rail sub-type based on {@link RailTypeMat}.
     * It will never return null.
     *
     * @param railType {@link RailTypeMat} of Rail.
     *
     * @return sub-type of Rail
     */
    public static RailMat getRail(final RailTypeMat railType)
    {
        return getByID(railType.getFlag());
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RailMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RailMat[] types()
    {
        return RailMat.railTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static RailMat[] railTypes()
    {
        return byID.values(new RailMat[byID.size()]);
    }

    static
    {
        RailMat.register(RAIL_FLAT_NORTH_SOUTH);
        RailMat.register(RAIL_FLAT_WEST_EAST);
        RailMat.register(RAIL_ASCENDING_EAST);
        RailMat.register(RAIL_ASCENDING_WEST);
        RailMat.register(RAIL_ASCENDING_NORTH);
        RailMat.register(RAIL_ASCENDING_SOUTH);
        RailMat.register(RAIL_CURVED_SOUTH_EAST);
        RailMat.register(RAIL_CURVED_SOUTH_WEST);
        RailMat.register(RAIL_CURVED_NORTH_WEST);
        RailMat.register(RAIL_CURVED_NORTH_EAST);
    }
}
