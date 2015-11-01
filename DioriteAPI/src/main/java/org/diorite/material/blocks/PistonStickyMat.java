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
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Sticky Piston' block material in minecraft. <br>
 * ID of block: 29 <br>
 * String ID of block: minecraft:sticky_piston <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * EAST_EXTENDED:
 * Type name: 'East Extended' <br>
 * SubID: 13 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * WEST_EXTENDED:
 * Type name: 'West Extended' <br>
 * SubID: 12 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * SOUTH_EXTENDED:
 * Type name: 'South Extended' <br>
 * SubID: 11 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * NORTH_EXTENDED:
 * Type name: 'North Extended' <br>
 * SubID: 10 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * UP_EXTENDED:
 * Type name: 'Up Extended' <br>
 * SubID: 9 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * DOWN_EXTENDED:
 * Type name: 'Down Extended' <br>
 * SubID: 8 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * EAST:
 * Type name: 'East' <br>
 * SubID: 5 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * WEST:
 * Type name: 'West' <br>
 * SubID: 4 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * SOUTH:
 * Type name: 'South' <br>
 * SubID: 3 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * NORTH:
 * Type name: 'North' <br>
 * SubID: 2 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * UP:
 * Type name: 'Up' <br>
 * SubID: 1 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * DOWN:
 * Type name: 'Down' <br>
 * SubID: 0 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class PistonStickyMat extends PistonBaseMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 12;

    public static final PistonStickyMat STICKY_PISTON_DOWN  = new PistonStickyMat();
    public static final PistonStickyMat STICKY_PISTON_UP    = new PistonStickyMat(BlockFace.UP, false);
    public static final PistonStickyMat STICKY_PISTON_NORTH = new PistonStickyMat(BlockFace.NORTH, false);
    public static final PistonStickyMat STICKY_PISTON_SOUTH = new PistonStickyMat(BlockFace.SOUTH, false);
    public static final PistonStickyMat STICKY_PISTON_WEST  = new PistonStickyMat(BlockFace.WEST, false);
    public static final PistonStickyMat STICKY_PISTON_EAST  = new PistonStickyMat(BlockFace.EAST, false);

    public static final PistonStickyMat STICKY_PISTON_DOWN_EXTENDED  = new PistonStickyMat(BlockFace.DOWN, true);
    public static final PistonStickyMat STICKY_PISTON_UP_EXTENDED    = new PistonStickyMat(BlockFace.UP, true);
    public static final PistonStickyMat STICKY_PISTON_NORTH_EXTENDED = new PistonStickyMat(BlockFace.NORTH, true);
    public static final PistonStickyMat STICKY_PISTON_SOUTH_EXTENDED = new PistonStickyMat(BlockFace.SOUTH, true);
    public static final PistonStickyMat STICKY_PISTON_WEST_EXTENDED  = new PistonStickyMat(BlockFace.WEST, true);
    public static final PistonStickyMat STICKY_PISTON_EAST_EXTENDED  = new PistonStickyMat(BlockFace.EAST, true);

    private static final Map<String, PistonStickyMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PistonStickyMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected PistonStickyMat()
    {
        super("STICKY_PISTON", 29, "minecraft:sticky_piston", BlockFace.DOWN, false, 0.5f, 2.5f);
    }

    protected PistonStickyMat(final BlockFace face, final boolean extended)
    {
        super(STICKY_PISTON_DOWN.name(), STICKY_PISTON_DOWN.ordinal(), STICKY_PISTON_DOWN.getMinecraftId(), face, extended, STICKY_PISTON_DOWN.getHardness(), STICKY_PISTON_DOWN.getBlastResistance());
    }

    protected PistonStickyMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace facing, final boolean extended, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, facing, extended, hardness, blastResistance);
    }

    @Override
    public PistonStickyMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PistonStickyMat getExtended(final boolean extended)
    {
        return getPistonSticky(this.facing, extended);
    }

    @Override
    public PistonStickyMat getType(final BlockFace face, final boolean extended)
    {
        return getPistonSticky(face, extended);
    }

    @Override
    public PistonStickyMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public PistonStickyMat getBlockFacing(final BlockFace face)
    {
        return getPistonSticky(face, this.extended);
    }

    @Override
    public PistonStickyMat getPowered(final boolean powered)
    {
        return this.getExtended(powered);
    }

    /**
     * Returns one of PistonSticky sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of PistonSticky or null
     */
    public static PistonStickyMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of PistonSticky sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of PistonSticky or null
     */
    public static PistonStickyMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of PistonSticky sub-type based on {@link BlockFace} and extended state.
     * It will never return null.
     *
     * @param face     facing direction of piston.
     * @param extended if piston should be extended.
     *
     * @return sub-type of PistonSticky
     */
    public static PistonStickyMat getPistonSticky(final BlockFace face, final boolean extended)
    {
        return getByID(combine(face, extended));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PistonStickyMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public PistonStickyMat[] types()
    {
        return PistonStickyMat.pistonStickyTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static PistonStickyMat[] pistonStickyTypes()
    {
        return byID.values(new PistonStickyMat[byID.size()]);
    }

    static
    {
        PistonStickyMat.register(STICKY_PISTON_DOWN);
        PistonStickyMat.register(STICKY_PISTON_UP);
        PistonStickyMat.register(STICKY_PISTON_NORTH);
        PistonStickyMat.register(STICKY_PISTON_SOUTH);
        PistonStickyMat.register(STICKY_PISTON_WEST);
        PistonStickyMat.register(STICKY_PISTON_EAST);
        PistonStickyMat.register(STICKY_PISTON_DOWN_EXTENDED);
        PistonStickyMat.register(STICKY_PISTON_UP_EXTENDED);
        PistonStickyMat.register(STICKY_PISTON_NORTH_EXTENDED);
        PistonStickyMat.register(STICKY_PISTON_SOUTH_EXTENDED);
        PistonStickyMat.register(STICKY_PISTON_WEST_EXTENDED);
        PistonStickyMat.register(STICKY_PISTON_EAST_EXTENDED);
    }
}
