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
import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Iron Trapdoor' block material in minecraft. <br>
 * ID of block: 167 <br>
 * String ID of block: minecraft:iron_trapdoor <br>
 * Hardness: 5 <br>
 * Blast Resistance 25 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * NORTH_TOP_OPEN:
 * Type name: 'North Top Open' <br>
 * SubID: 15 <br>
 * Hardness: 5 <br>
 * Blast Resistance 25 <br>
 * </li>
 * <li>
 * EAST_TOP_OPEN:
 * Type name: 'East Top Open' <br>
 * SubID: 14 <br>
 * Hardness: 5 <br>
 * Blast Resistance 25 <br>
 * </li>
 * <li>
 * SOUTH_TOP_OPEN:
 * Type name: 'South Top Open' <br>
 * SubID: 13 <br>
 * Hardness: 5 <br>
 * Blast Resistance 25 <br>
 * </li>
 * <li>
 * WEST_TOP_OPEN:
 * Type name: 'West Top Open' <br>
 * SubID: 12 <br>
 * Hardness: 5 <br>
 * Blast Resistance 25 <br>
 * </li>
 * <li>
 * NORTH_TOP:
 * Type name: 'North Top' <br>
 * SubID: 11 <br>
 * Hardness: 5 <br>
 * Blast Resistance 25 <br>
 * </li>
 * <li>
 * EAST_TOP:
 * Type name: 'East Top' <br>
 * SubID: 10 <br>
 * Hardness: 5 <br>
 * Blast Resistance 25 <br>
 * </li>
 * <li>
 * SOUTH_TOP:
 * Type name: 'South Top' <br>
 * SubID: 9 <br>
 * Hardness: 5 <br>
 * Blast Resistance 25 <br>
 * </li>
 * <li>
 * WEST_TOP:
 * Type name: 'West Top' <br>
 * SubID: 8 <br>
 * Hardness: 5 <br>
 * Blast Resistance 25 <br>
 * </li>
 * <li>
 * NORTH_BOTTOM_OPEN:
 * Type name: 'North Bottom Open' <br>
 * SubID: 7 <br>
 * Hardness: 5 <br>
 * Blast Resistance 25 <br>
 * </li>
 * <li>
 * EAST_BOTTOM_OPEN:
 * Type name: 'East Bottom Open' <br>
 * SubID: 6 <br>
 * Hardness: 5 <br>
 * Blast Resistance 25 <br>
 * </li>
 * <li>
 * SOUTH_BOTTOM_OPEN:
 * Type name: 'South Bottom Open' <br>
 * SubID: 5 <br>
 * Hardness: 5 <br>
 * Blast Resistance 25 <br>
 * </li>
 * <li>
 * WEST_BOTTOM_OPEN:
 * Type name: 'West Bottom Open' <br>
 * SubID: 4 <br>
 * Hardness: 5 <br>
 * Blast Resistance 25 <br>
 * </li>
 * <li>
 * NORTH_BOTTOM:
 * Type name: 'North Bottom' <br>
 * SubID: 3 <br>
 * Hardness: 5 <br>
 * Blast Resistance 25 <br>
 * </li>
 * <li>
 * EAST_BOTTOM:
 * Type name: 'East Bottom' <br>
 * SubID: 2 <br>
 * Hardness: 5 <br>
 * Blast Resistance 25 <br>
 * </li>
 * <li>
 * SOUTH_BOTTOM:
 * Type name: 'South Bottom' <br>
 * SubID: 1 <br>
 * Hardness: 5 <br>
 * Blast Resistance 25 <br>
 * </li>
 * <li>
 * WEST_BOTTOM:
 * Type name: 'West Bottom' <br>
 * SubID: 0 <br>
 * Hardness: 5 <br>
 * Blast Resistance 25 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class IronTrapdoorMat extends TrapdoorMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 16;

    public static final IronTrapdoorMat IRON_TRAPDOOR_WEST_BOTTOM  = new IronTrapdoorMat();
    public static final IronTrapdoorMat IRON_TRAPDOOR_SOUTH_BOTTOM = new IronTrapdoorMat(BlockFace.SOUTH, false, false);
    public static final IronTrapdoorMat IRON_TRAPDOOR_EAST_BOTTOM  = new IronTrapdoorMat(BlockFace.EAST, false, false);
    public static final IronTrapdoorMat IRON_TRAPDOOR_NORTH_BOTTOM = new IronTrapdoorMat(BlockFace.NORTH, false, false);

    public static final IronTrapdoorMat IRON_TRAPDOOR_WEST_BOTTOM_OEPN  = new IronTrapdoorMat(BlockFace.WEST, true, false);
    public static final IronTrapdoorMat IRON_TRAPDOOR_SOUTH_BOTTOM_OEPN = new IronTrapdoorMat(BlockFace.SOUTH, true, false);
    public static final IronTrapdoorMat IRON_TRAPDOOR_EAST_BOTTOM_OEPN  = new IronTrapdoorMat(BlockFace.EAST, true, false);
    public static final IronTrapdoorMat IRON_TRAPDOOR_NORTH_BOTTOM_OEPN = new IronTrapdoorMat(BlockFace.NORTH, true, false);

    public static final IronTrapdoorMat IRON_TRAPDOOR_WEST_TOP  = new IronTrapdoorMat(BlockFace.WEST, false, true);
    public static final IronTrapdoorMat IRON_TRAPDOOR_SOUTH_TOP = new IronTrapdoorMat(BlockFace.SOUTH, false, true);
    public static final IronTrapdoorMat IRON_TRAPDOOR_EAST_TOP  = new IronTrapdoorMat(BlockFace.EAST, false, true);
    public static final IronTrapdoorMat IRON_TRAPDOOR_NORTH_TOP = new IronTrapdoorMat(BlockFace.NORTH, false, true);

    public static final IronTrapdoorMat IRON_TRAPDOOR_WEST_TOP_OPEN  = new IronTrapdoorMat(BlockFace.WEST, true, true);
    public static final IronTrapdoorMat IRON_TRAPDOOR_SOUTH_TOP_OPEN = new IronTrapdoorMat(BlockFace.SOUTH, true, true);
    public static final IronTrapdoorMat IRON_TRAPDOOR_EAST_TOP_OPEN  = new IronTrapdoorMat(BlockFace.EAST, true, true);
    public static final IronTrapdoorMat IRON_TRAPDOOR_NORTH_TOP_OPEN = new IronTrapdoorMat(BlockFace.NORTH, true, true);

    private static final Map<String, IronTrapdoorMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<IronTrapdoorMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected IronTrapdoorMat()
    {
        super("IRON_TRAPDOOR", 167, "minecraft:iron_trapdoor", BlockFace.WEST, false, false, 5, 25);
    }

    protected IronTrapdoorMat(final BlockFace face, final boolean open, final boolean onTop)
    {
        super(IRON_TRAPDOOR_WEST_BOTTOM.name(), IRON_TRAPDOOR_WEST_BOTTOM.ordinal(), IRON_TRAPDOOR_WEST_BOTTOM.getMinecraftId(), face, open, onTop, IRON_TRAPDOOR_WEST_BOTTOM.getHardness(), IRON_TRAPDOOR_WEST_BOTTOM.getBlastResistance());
    }

    protected IronTrapdoorMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final boolean open, final boolean onTop, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, face, open, onTop, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return IRON_TRAPDOOR;
    }

    @Override
    public IronTrapdoorMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public IronTrapdoorMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public IronTrapdoorMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.open, this.onTop));
    }

    @Override
    public IronTrapdoorMat getOpen(final boolean open)
    {
        return getByID(combine(this.face, open, this.onTop));
    }

    @Override
    public IronTrapdoorMat getOnTop(final boolean onTop)
    {
        return getByID(combine(this.face, this.open, onTop));
    }

    @Override
    public IronTrapdoorMat getType(final BlockFace face, final boolean open, final boolean onTop)
    {
        return getByID(combine(face, open, onTop));
    }

    @Override
    public IronTrapdoorMat getAttachedFace(final BlockFace face)
    {
        return getByID(combine(face.getOppositeFace(), this.open, this.onTop));
    }

    /**
     * Returns one of IronTrapdoor sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of IronTrapdoor or null
     */
    public static IronTrapdoorMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of IronTrapdoor sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of IronTrapdoor or null
     */
    public static IronTrapdoorMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of IronTrapdoor sub-type based on facing direction, open state and on top state.
     * It will never return null.
     *
     * @param blockFace facing direction of trapdoor.
     * @param open      if trapdoor should be open.
     * @param onTop     if trapdoor should be on top of block.
     *
     * @return sub-type of IronTrapdoor
     */
    public static IronTrapdoorMat getIronTrapdoor(final BlockFace blockFace, final boolean open, final boolean onTop)
    {
        return getByID(combine(blockFace, open, onTop));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final IronTrapdoorMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public IronTrapdoorMat[] types()
    {
        return IronTrapdoorMat.ironTrapdoorTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static IronTrapdoorMat[] ironTrapdoorTypes()
    {
        return byID.values(new IronTrapdoorMat[byID.size()]);
    }

    static
    {
        IronTrapdoorMat.register(IRON_TRAPDOOR_WEST_BOTTOM);
        IronTrapdoorMat.register(IRON_TRAPDOOR_SOUTH_BOTTOM);
        IronTrapdoorMat.register(IRON_TRAPDOOR_EAST_BOTTOM);
        IronTrapdoorMat.register(IRON_TRAPDOOR_NORTH_BOTTOM);
        IronTrapdoorMat.register(IRON_TRAPDOOR_WEST_BOTTOM_OEPN);
        IronTrapdoorMat.register(IRON_TRAPDOOR_SOUTH_BOTTOM_OEPN);
        IronTrapdoorMat.register(IRON_TRAPDOOR_EAST_BOTTOM_OEPN);
        IronTrapdoorMat.register(IRON_TRAPDOOR_NORTH_BOTTOM_OEPN);
        IronTrapdoorMat.register(IRON_TRAPDOOR_WEST_TOP);
        IronTrapdoorMat.register(IRON_TRAPDOOR_SOUTH_TOP);
        IronTrapdoorMat.register(IRON_TRAPDOOR_EAST_TOP);
        IronTrapdoorMat.register(IRON_TRAPDOOR_NORTH_TOP);
        IronTrapdoorMat.register(IRON_TRAPDOOR_WEST_TOP_OPEN);
        IronTrapdoorMat.register(IRON_TRAPDOOR_SOUTH_TOP_OPEN);
        IronTrapdoorMat.register(IRON_TRAPDOOR_EAST_TOP_OPEN);
        IronTrapdoorMat.register(IRON_TRAPDOOR_NORTH_TOP_OPEN);
    }
}
