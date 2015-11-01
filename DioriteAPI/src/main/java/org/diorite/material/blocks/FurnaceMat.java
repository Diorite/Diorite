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

import org.diorite.BlockFace;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.DirectionalMat;
import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Furnace' block material in minecraft. <br>
 * ID of block: 61 <br>
 * String ID of block: minecraft:furnace <br>
 * Hardness: 3,5 <br>
 * Blast Resistance 17,5 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * EAST:
 * Type name: 'East' <br>
 * SubID: 5 <br>
 * Hardness: 3,5 <br>
 * Blast Resistance 17,5 <br>
 * </li>
 * <li>
 * WEST:
 * Type name: 'West' <br>
 * SubID: 4 <br>
 * Hardness: 3,5 <br>
 * Blast Resistance 17,5 <br>
 * </li>
 * <li>
 * SOUTH:
 * Type name: 'South' <br>
 * SubID: 3 <br>
 * Hardness: 3,5 <br>
 * Blast Resistance 17,5 <br>
 * </li>
 * <li>
 * NORTH:
 * Type name: 'North' <br>
 * SubID: 0 <br>
 * Hardness: 3,5 <br>
 * Blast Resistance 17,5 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class FurnaceMat extends BlockMaterialData implements DirectionalMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 4;

    public static final FurnaceMat FURNACE_NORTH = new FurnaceMat();
    public static final FurnaceMat FURNACE_SOUTH = new FurnaceMat(BlockFace.SOUTH);
    public static final FurnaceMat FURNACE_WEST  = new FurnaceMat(BlockFace.WEST);
    public static final FurnaceMat FURNACE_EAST  = new FurnaceMat(BlockFace.EAST);

    private static final Map<String, FurnaceMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<FurnaceMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final BlockFace face;

    @SuppressWarnings("MagicNumber")
    protected FurnaceMat()
    {
        super("FURNACE", 61, "minecraft:furnace", "NORTH", (byte) 0x00, 3.5f, 17.5f);
        this.face = BlockFace.NORTH;
    }

    protected FurnaceMat(final BlockFace face)
    {
        super(FURNACE_NORTH.name(), FURNACE_NORTH.ordinal(), FURNACE_NORTH.getMinecraftId(), face.name(), combine(face), FURNACE_NORTH.getHardness(), FURNACE_NORTH.getBlastResistance());
        this.face = face;
    }

    protected FurnaceMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.face = face;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.FURNACE;
    }

    @Override
    public FurnaceMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public FurnaceMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).toString();
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public FurnaceMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face));
    }

    private static byte combine(final BlockFace face)
    {
        switch (face)
        {
            case SOUTH:
                return 0x3;
            case WEST:
                return 0x4;
            case EAST:
                return 0x5;
            default:
                return 0x2;
        }
    }

    /**
     * Returns one of Furnace sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Furnace or null
     */
    public static FurnaceMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Furnace sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Furnace or null
     */
    public static FurnaceMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Furnace sub-type based on {@link BlockFace}
     * It will never return null.
     *
     * @param face facing of Furnace.
     *
     * @return sub-type of Furnace
     */
    public static FurnaceMat getFurnace(final BlockFace face)
    {
        return getByID(combine(face));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final FurnaceMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public FurnaceMat[] types()
    {
        return FurnaceMat.furnaceTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static FurnaceMat[] furnaceTypes()
    {
        return byID.values(new FurnaceMat[byID.size()]);
    }

    static
    {
        FurnaceMat.register(FURNACE_NORTH);
        FurnaceMat.register(FURNACE_SOUTH);
        FurnaceMat.register(FURNACE_WEST);
        FurnaceMat.register(FURNACE_EAST);
    }
}
