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

import org.diorite.BlockFace;
import org.diorite.material.FuelMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Wooden Trapdoor' block material in minecraft. <br>
 * ID of block: 96 <br>
 * String ID of block: minecraft:trapdoor <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * NORTH_TOP_OPEN:
 * Type name: 'North Top Open' <br>
 * SubID: 15 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * EAST_TOP_OPEN:
 * Type name: 'East Top Open' <br>
 * SubID: 14 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * SOUTH_TOP_OPEN:
 * Type name: 'South Top Open' <br>
 * SubID: 13 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * WEST_TOP_OPEN:
 * Type name: 'West Top Open' <br>
 * SubID: 12 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * NORTH_TOP:
 * Type name: 'North Top' <br>
 * SubID: 11 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * EAST_TOP:
 * Type name: 'East Top' <br>
 * SubID: 10 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * SOUTH_TOP:
 * Type name: 'South Top' <br>
 * SubID: 9 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * WEST_TOP:
 * Type name: 'West Top' <br>
 * SubID: 8 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * NORTH_BOTTOM_OPEN:
 * Type name: 'North Bottom Open' <br>
 * SubID: 7 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * EAST_BOTTOM_OPEN:
 * Type name: 'East Bottom Open' <br>
 * SubID: 6 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * SOUTH_BOTTOM_OPEN:
 * Type name: 'South Bottom Open' <br>
 * SubID: 5 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * WEST_BOTTOM_OPEN:
 * Type name: 'West Bottom Open' <br>
 * SubID: 4 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * NORTH_BOTTOM:
 * Type name: 'North Bottom' <br>
 * SubID: 3 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * EAST_BOTTOM:
 * Type name: 'East Bottom' <br>
 * SubID: 2 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * SOUTH_BOTTOM:
 * Type name: 'South Bottom' <br>
 * SubID: 1 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * WEST_BOTTOM:
 * Type name: 'West Bottom' <br>
 * SubID: 0 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class WoodenTrapdoorMat extends TrapdoorMat implements FuelMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 16;

    public static final WoodenTrapdoorMat WOODEN_TRAPDOOR_WEST_BOTTOM  = new WoodenTrapdoorMat();
    public static final WoodenTrapdoorMat WOODEN_TRAPDOOR_SOUTH_BOTTOM = new WoodenTrapdoorMat(BlockFace.SOUTH, false, false);
    public static final WoodenTrapdoorMat WOODEN_TRAPDOOR_EAST_BOTTOM  = new WoodenTrapdoorMat(BlockFace.EAST, false, false);
    public static final WoodenTrapdoorMat WOODEN_TRAPDOOR_NORTH_BOTTOM = new WoodenTrapdoorMat(BlockFace.NORTH, false, false);

    public static final WoodenTrapdoorMat WOODEN_TRAPDOOR_WEST_BOTTOM_OEPN  = new WoodenTrapdoorMat(BlockFace.WEST, true, false);
    public static final WoodenTrapdoorMat WOODEN_TRAPDOOR_SOUTH_BOTTOM_OEPN = new WoodenTrapdoorMat(BlockFace.SOUTH, true, false);
    public static final WoodenTrapdoorMat WOODEN_TRAPDOOR_EAST_BOTTOM_OEPN  = new WoodenTrapdoorMat(BlockFace.EAST, true, false);
    public static final WoodenTrapdoorMat WOODEN_TRAPDOOR_NORTH_BOTTOM_OEPN = new WoodenTrapdoorMat(BlockFace.NORTH, true, false);

    public static final WoodenTrapdoorMat WOODEN_TRAPDOOR_WEST_TOP  = new WoodenTrapdoorMat(BlockFace.WEST, false, true);
    public static final WoodenTrapdoorMat WOODEN_TRAPDOOR_SOUTH_TOP = new WoodenTrapdoorMat(BlockFace.SOUTH, false, true);
    public static final WoodenTrapdoorMat WOODEN_TRAPDOOR_EAST_TOP  = new WoodenTrapdoorMat(BlockFace.EAST, false, true);
    public static final WoodenTrapdoorMat WOODEN_TRAPDOOR_NORTH_TOP = new WoodenTrapdoorMat(BlockFace.NORTH, false, true);

    public static final WoodenTrapdoorMat WOODEN_TRAPDOOR_WEST_TOP_OPEN  = new WoodenTrapdoorMat(BlockFace.WEST, true, true);
    public static final WoodenTrapdoorMat WOODEN_TRAPDOOR_SOUTH_TOP_OPEN = new WoodenTrapdoorMat(BlockFace.SOUTH, true, true);
    public static final WoodenTrapdoorMat WOODEN_TRAPDOOR_EAST_TOP_OPEN  = new WoodenTrapdoorMat(BlockFace.EAST, true, true);
    public static final WoodenTrapdoorMat WOODEN_TRAPDOOR_NORTH_TOP_OPEN = new WoodenTrapdoorMat(BlockFace.NORTH, true, true);

    private static final Map<String, WoodenTrapdoorMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<WoodenTrapdoorMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected WoodenTrapdoorMat()
    {
        super("WOODEN_TRAPDOOR", 96, "minecraft:trapdoor", BlockFace.WEST, false, false, 3, 15);
    }

    protected WoodenTrapdoorMat(final BlockFace face, final boolean open, final boolean onTop)
    {
        super(WOODEN_TRAPDOOR_WEST_BOTTOM.name(), WOODEN_TRAPDOOR_WEST_BOTTOM.ordinal(), WOODEN_TRAPDOOR_WEST_BOTTOM.getMinecraftId(), face, open, onTop, WOODEN_TRAPDOOR_WEST_BOTTOM.getHardness(), WOODEN_TRAPDOOR_WEST_BOTTOM.getBlastResistance());
    }

    protected WoodenTrapdoorMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final boolean open, final boolean onTop, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, face, open, onTop, hardness, blastResistance);
    }

    @Override
    public WoodenTrapdoorMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public WoodenTrapdoorMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public WoodenTrapdoorMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.open, this.onTop));
    }

    @Override
    public WoodenTrapdoorMat getOpen(final boolean open)
    {
        return getByID(combine(this.face, open, this.onTop));
    }

    @Override
    public WoodenTrapdoorMat getOnTop(final boolean onTop)
    {
        return getByID(combine(this.face, this.open, onTop));
    }

    @Override
    public WoodenTrapdoorMat getType(final BlockFace face, final boolean open, final boolean onTop)
    {
        return getByID(combine(face, open, onTop));
    }

    @Override
    public WoodenTrapdoorMat getAttachedFace(final BlockFace face)
    {
        return getByID(combine(face.getOppositeFace(), this.open, this.onTop));
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public int getFuelPower()
    {
        return 1500;
    }

    /**
     * Returns one of WoodenTrapdoor sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of WoodenTrapdoor or null
     */
    public static WoodenTrapdoorMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of WoodenTrapdoor sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of WoodenTrapdoor or null
     */
    public static WoodenTrapdoorMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of WoodenTrapdoor sub-type based on facing direction, open state and on top state.
     * It will never return null.
     *
     * @param blockFace facing direction of trapdoor.
     * @param open      if trapdoor should be open.
     * @param onTop     if trapdoor should be on top of block.
     *
     * @return sub-type of WoodenTrapdoor
     */
    public static WoodenTrapdoorMat getWoodenTrapdoor(final BlockFace blockFace, final boolean open, final boolean onTop)
    {
        return getByID(combine(blockFace, open, onTop));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final WoodenTrapdoorMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public WoodenTrapdoorMat[] types()
    {
        return WoodenTrapdoorMat.woodenTrapdoorTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static WoodenTrapdoorMat[] woodenTrapdoorTypes()
    {
        return byID.values(new WoodenTrapdoorMat[byID.size()]);
    }

    static
    {
        WoodenTrapdoorMat.register(WOODEN_TRAPDOOR_WEST_BOTTOM);
        WoodenTrapdoorMat.register(WOODEN_TRAPDOOR_SOUTH_BOTTOM);
        WoodenTrapdoorMat.register(WOODEN_TRAPDOOR_EAST_BOTTOM);
        WoodenTrapdoorMat.register(WOODEN_TRAPDOOR_NORTH_BOTTOM);
        WoodenTrapdoorMat.register(WOODEN_TRAPDOOR_WEST_BOTTOM_OEPN);
        WoodenTrapdoorMat.register(WOODEN_TRAPDOOR_SOUTH_BOTTOM_OEPN);
        WoodenTrapdoorMat.register(WOODEN_TRAPDOOR_EAST_BOTTOM_OEPN);
        WoodenTrapdoorMat.register(WOODEN_TRAPDOOR_NORTH_BOTTOM_OEPN);
        WoodenTrapdoorMat.register(WOODEN_TRAPDOOR_WEST_TOP);
        WoodenTrapdoorMat.register(WOODEN_TRAPDOOR_SOUTH_TOP);
        WoodenTrapdoorMat.register(WOODEN_TRAPDOOR_EAST_TOP);
        WoodenTrapdoorMat.register(WOODEN_TRAPDOOR_NORTH_TOP);
        WoodenTrapdoorMat.register(WOODEN_TRAPDOOR_WEST_TOP_OPEN);
        WoodenTrapdoorMat.register(WOODEN_TRAPDOOR_SOUTH_TOP_OPEN);
        WoodenTrapdoorMat.register(WOODEN_TRAPDOOR_EAST_TOP_OPEN);
        WoodenTrapdoorMat.register(WOODEN_TRAPDOOR_NORTH_TOP_OPEN);
    }
}
