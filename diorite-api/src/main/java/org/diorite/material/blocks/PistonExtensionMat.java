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
 * Class representing 'Piston Extension' block material in minecraft. <br>
 * ID of block: 36 <br>
 * String ID of block: minecraft:piston_extension <br>
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
public class PistonExtensionMat extends PistonBaseMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 12;

    public static final PistonExtensionMat PISTON_EXTENSION_DOWN  = new PistonExtensionMat();
    public static final PistonExtensionMat PISTON_EXTENSION_UP    = new PistonExtensionMat(BlockFace.UP, false);
    public static final PistonExtensionMat PISTON_EXTENSION_NORTH = new PistonExtensionMat(BlockFace.NORTH, false);
    public static final PistonExtensionMat PISTON_EXTENSION_SOUTH = new PistonExtensionMat(BlockFace.SOUTH, false);
    public static final PistonExtensionMat PISTON_EXTENSION_WEST  = new PistonExtensionMat(BlockFace.WEST, false);
    public static final PistonExtensionMat PISTON_EXTENSION_EAST  = new PistonExtensionMat(BlockFace.EAST, false);

    public static final PistonExtensionMat PISTON_EXTENSION_DOWN_EXTENDED  = new PistonExtensionMat(BlockFace.DOWN, true);
    public static final PistonExtensionMat PISTON_EXTENSION_UP_EXTENDED    = new PistonExtensionMat(BlockFace.UP, true);
    public static final PistonExtensionMat PISTON_EXTENSION_NORTH_EXTENDED = new PistonExtensionMat(BlockFace.NORTH, true);
    public static final PistonExtensionMat PISTON_EXTENSION_SOUTH_EXTENDED = new PistonExtensionMat(BlockFace.SOUTH, true);
    public static final PistonExtensionMat PISTON_EXTENSION_WEST_EXTENDED  = new PistonExtensionMat(BlockFace.WEST, true);
    public static final PistonExtensionMat PISTON_EXTENSION_EAST_EXTENDED  = new PistonExtensionMat(BlockFace.EAST, true);

    private static final Map<String, PistonExtensionMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<PistonExtensionMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected PistonExtensionMat()
    {
        super("PISTON_EXTENSION", 36, "minecraft:piston_extension", BlockFace.DOWN, false, 0.5f, 2.5f);
    }

    protected PistonExtensionMat(final BlockFace face, final boolean extended)
    {
        super(PISTON_EXTENSION_DOWN.name(), PISTON_EXTENSION_DOWN.ordinal(), PISTON_EXTENSION_DOWN.getMinecraftId(), face, extended, PISTON_EXTENSION_DOWN.getHardness(), PISTON_EXTENSION_DOWN.getBlastResistance());
    }

    protected PistonExtensionMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace facing, final boolean extended, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, facing, extended, hardness, blastResistance);
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return PISTON;
    }

    @Override
    public PistonExtensionMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public PistonExtensionMat getExtended(final boolean extended)
    {
        return getPistonExtension(this.facing, extended);
    }

    @Override
    public PistonExtensionMat getType(final BlockFace face, final boolean extended)
    {
        return getPistonExtension(face, extended);
    }

    @Override
    public PistonExtensionMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public PistonExtensionMat getBlockFacing(final BlockFace face)
    {
        return getPistonExtension(face, this.extended);
    }

    @Override
    public PistonExtensionMat getPowered(final boolean powered)
    {
        return this.getExtended(powered);
    }

    /**
     * Returns one of PistonExtension sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of PistonExtension or null
     */
    public static PistonExtensionMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of PistonExtension sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of PistonExtension or null
     */
    public static PistonExtensionMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of PistonExtension sub-type based on {@link BlockFace} and extended state.
     * It will never return null.
     *
     * @param face     facing direction of piston.
     * @param extended if piston should be extended.
     *
     * @return sub-type of PistonExtension
     */
    public static PistonExtensionMat getPistonExtension(final BlockFace face, final boolean extended)
    {
        return getByID(combine(face, extended));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final PistonExtensionMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public PistonExtensionMat[] types()
    {
        return PistonExtensionMat.pistonExtensionTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static PistonExtensionMat[] pistonExtensionTypes()
    {
        return byID.values(new PistonExtensionMat[byID.size()]);
    }

    static
    {
        PistonExtensionMat.register(PISTON_EXTENSION_DOWN);
        PistonExtensionMat.register(PISTON_EXTENSION_UP);
        PistonExtensionMat.register(PISTON_EXTENSION_NORTH);
        PistonExtensionMat.register(PISTON_EXTENSION_SOUTH);
        PistonExtensionMat.register(PISTON_EXTENSION_WEST);
        PistonExtensionMat.register(PISTON_EXTENSION_EAST);
        PistonExtensionMat.register(PISTON_EXTENSION_DOWN_EXTENDED);
        PistonExtensionMat.register(PISTON_EXTENSION_UP_EXTENDED);
        PistonExtensionMat.register(PISTON_EXTENSION_NORTH_EXTENDED);
        PistonExtensionMat.register(PISTON_EXTENSION_SOUTH_EXTENDED);
        PistonExtensionMat.register(PISTON_EXTENSION_WEST_EXTENDED);
        PistonExtensionMat.register(PISTON_EXTENSION_EAST_EXTENDED);
    }
}
