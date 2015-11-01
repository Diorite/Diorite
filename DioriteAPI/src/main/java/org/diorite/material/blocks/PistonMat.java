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
 * Class representing 'Piston' block material in minecraft. <br>
 * ID of block: 33 <br>
 * String ID of block: minecraft:piston <br>
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
public class PistonMat extends PistonBaseMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 12;

    public static final PistonMat PISTON_DOWN  = new PistonMat();
    public static final PistonMat PISTON_UP    = new PistonMat(BlockFace.UP, false);
    public static final PistonMat PISTON_NORTH = new PistonMat(BlockFace.NORTH, false);
    public static final PistonMat PISTON_SOUTH = new PistonMat(BlockFace.SOUTH, false);
    public static final PistonMat PISTON_WEST  = new PistonMat(BlockFace.WEST, false);
    public static final PistonMat PISTON_EAST  = new PistonMat(BlockFace.EAST, false);

    public static final PistonMat PISTON_DOWN_EXTENDED  = new PistonMat(BlockFace.DOWN, true);
    public static final PistonMat PISTON_UP_EXTENDED    = new PistonMat(BlockFace.UP, true);
    public static final PistonMat PISTON_NORTH_EXTENDED = new PistonMat(BlockFace.NORTH, true);
    public static final PistonMat PISTON_SOUTH_EXTENDED = new PistonMat(BlockFace.SOUTH, true);
    public static final PistonMat PISTON_WEST_EXTENDED  = new PistonMat(BlockFace.WEST, true);
    public static final PistonMat PISTON_EAST_EXTENDED  = new PistonMat(BlockFace.EAST, true);

    private static final Map<String, PistonMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PistonMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected PistonMat()
    {
        super("PISTON", 33, "minecraft:piston", BlockFace.DOWN, false, 0.5f, 2.5f);
    }

    protected PistonMat(final BlockFace face, final boolean extended)
    {
        super(PISTON_DOWN.name(), PISTON_DOWN.ordinal(), PISTON_DOWN.getMinecraftId(), face, extended, PISTON_DOWN.getHardness(), PISTON_DOWN.getBlastResistance());
    }

    protected PistonMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace facing, final boolean extended, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, facing, extended, hardness, blastResistance);
    }

    @Override
    public PistonMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PistonMat getExtended(final boolean extended)
    {
        return getPiston(this.facing, extended);
    }

    @Override
    public PistonMat getType(final BlockFace face, final boolean extended)
    {
        return getPiston(face, extended);
    }

    @Override
    public PistonMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public PistonMat getBlockFacing(final BlockFace face)
    {
        return getPiston(face, this.extended);
    }

    @Override
    public PistonMat getPowered(final boolean powered)
    {
        return this.getExtended(powered);
    }

    /**
     * Returns one of Piston sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Piston or null
     */
    public static PistonMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Piston sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Piston or null
     */
    public static PistonMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Piston sub-type based on {@link BlockFace} and extended state.
     * It will never return null.
     *
     * @param face     facing direction of piston.
     * @param extended if piston should be extended.
     *
     * @return sub-type of Piston
     */
    public static PistonMat getPiston(final BlockFace face, final boolean extended)
    {
        return getByID(combine(face, extended));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PistonMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public PistonMat[] types()
    {
        return PistonMat.pistonTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static PistonMat[] pistonTypes()
    {
        return byID.values(new PistonMat[byID.size()]);
    }

    static
    {
        PistonMat.register(PISTON_DOWN);
        PistonMat.register(PISTON_UP);
        PistonMat.register(PISTON_NORTH);
        PistonMat.register(PISTON_SOUTH);
        PistonMat.register(PISTON_WEST);
        PistonMat.register(PISTON_EAST);
        PistonMat.register(PISTON_DOWN_EXTENDED);
        PistonMat.register(PISTON_UP_EXTENDED);
        PistonMat.register(PISTON_NORTH_EXTENDED);
        PistonMat.register(PISTON_SOUTH_EXTENDED);
        PistonMat.register(PISTON_WEST_EXTENDED);
        PistonMat.register(PISTON_EAST_EXTENDED);
    }
}
