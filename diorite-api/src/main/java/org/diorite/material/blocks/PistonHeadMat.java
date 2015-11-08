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
 * Class representing 'Piston Head' block material in minecraft. <br>
 * ID of block: 34 <br>
 * String ID of block: minecraft:piston_head <br>
 * This block can't be used in inventory, valid material for this block: 'Piston'/'Down' (minecraft:piston(33):0) <br>
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
public class PistonHeadMat extends PistonBaseMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 12;

    public static final PistonHeadMat PISTON_HEAD_DOWN  = new PistonHeadMat();
    public static final PistonHeadMat PISTON_HEAD_UP    = new PistonHeadMat(BlockFace.UP, false);
    public static final PistonHeadMat PISTON_HEAD_NORTH = new PistonHeadMat(BlockFace.NORTH, false);
    public static final PistonHeadMat PISTON_HEAD_SOUTH = new PistonHeadMat(BlockFace.SOUTH, false);
    public static final PistonHeadMat PISTON_HEAD_WEST  = new PistonHeadMat(BlockFace.WEST, false);
    public static final PistonHeadMat PISTON_HEAD_EAST  = new PistonHeadMat(BlockFace.EAST, false);

    public static final PistonHeadMat PISTON_HEAD_DOWN_EXTENDED  = new PistonHeadMat(BlockFace.DOWN, true);
    public static final PistonHeadMat PISTON_HEAD_UP_EXTENDED    = new PistonHeadMat(BlockFace.UP, true);
    public static final PistonHeadMat PISTON_HEAD_NORTH_EXTENDED = new PistonHeadMat(BlockFace.NORTH, true);
    public static final PistonHeadMat PISTON_HEAD_SOUTH_EXTENDED = new PistonHeadMat(BlockFace.SOUTH, true);
    public static final PistonHeadMat PISTON_HEAD_WEST_EXTENDED  = new PistonHeadMat(BlockFace.WEST, true);
    public static final PistonHeadMat PISTON_HEAD_EAST_EXTENDED  = new PistonHeadMat(BlockFace.EAST, true);

    private static final Map<String, PistonHeadMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PistonHeadMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected PistonHeadMat()
    {
        super("PISTON_HEAD", 34, "minecraft:piston_head", BlockFace.DOWN, false, 0.5f, 2.5f);
    }

    protected PistonHeadMat(final BlockFace face, final boolean extended)
    {
        super(PISTON_HEAD_DOWN.name(), PISTON_HEAD_DOWN.ordinal(), PISTON_HEAD_DOWN.getMinecraftId(), face, extended, PISTON_HEAD_DOWN.getHardness(), PISTON_HEAD_DOWN.getBlastResistance());
    }

    protected PistonHeadMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace facing, final boolean extended, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, facing, extended, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return PISTON;
    }

    @Override
    public PistonHeadMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PistonHeadMat getExtended(final boolean extended)
    {
        return getPistonHead(this.facing, extended);
    }

    @Override
    public PistonHeadMat getType(final BlockFace face, final boolean extended)
    {
        return getPistonHead(face, extended);
    }

    @Override
    public PistonHeadMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public PistonHeadMat getBlockFacing(final BlockFace face)
    {
        return getPistonHead(face, this.extended);
    }

    @Override
    public PistonHeadMat getPowered(final boolean powered)
    {
        return this.getExtended(powered);
    }

    /**
     * Returns one of PistonHead sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of PistonHead or null
     */
    public static PistonHeadMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of PistonHead sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of PistonHead or null
     */
    public static PistonHeadMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of PistonHead sub-type based on {@link BlockFace} and extended state.
     * It will never return null.
     *
     * @param face     facing direction of piston.
     * @param extended if piston should be extended.
     *
     * @return sub-type of PistonHead
     */
    public static PistonHeadMat getPistonHead(final BlockFace face, final boolean extended)
    {
        return getByID(combine(face, extended));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PistonHeadMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public PistonHeadMat[] types()
    {
        return PistonHeadMat.pistonHeadTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static PistonHeadMat[] pistonHeadTypes()
    {
        return byID.values(new PistonHeadMat[byID.size()]);
    }

    static
    {
        PistonHeadMat.register(PISTON_HEAD_DOWN);
        PistonHeadMat.register(PISTON_HEAD_UP);
        PistonHeadMat.register(PISTON_HEAD_NORTH);
        PistonHeadMat.register(PISTON_HEAD_SOUTH);
        PistonHeadMat.register(PISTON_HEAD_WEST);
        PistonHeadMat.register(PISTON_HEAD_EAST);
        PistonHeadMat.register(PISTON_HEAD_DOWN_EXTENDED);
        PistonHeadMat.register(PISTON_HEAD_UP_EXTENDED);
        PistonHeadMat.register(PISTON_HEAD_NORTH_EXTENDED);
        PistonHeadMat.register(PISTON_HEAD_SOUTH_EXTENDED);
        PistonHeadMat.register(PISTON_HEAD_WEST_EXTENDED);
        PistonHeadMat.register(PISTON_HEAD_EAST_EXTENDED);
    }
}
