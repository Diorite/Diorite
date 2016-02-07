/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.Material;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import it.unimi.dsi.fastutil.bytes.Byte2ObjectMap;
import it.unimi.dsi.fastutil.bytes.Byte2ObjectOpenHashMap;

/**
 * Class representing 'Vine' block material in minecraft. <br>
 * ID of block: 106 <br>
 * String ID of block: minecraft:vine <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * EAST_NORTH_WEST_SOUTH:
 * Type name: 'East North West South' <br>
 * SubID: 15 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * EAST_NORTH_WEST:
 * Type name: 'East North West' <br>
 * SubID: 14 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * EAST_NORTH_SOUTH:
 * Type name: 'East North South' <br>
 * SubID: 13 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * EAST_NORTH:
 * Type name: 'East North' <br>
 * SubID: 12 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * EAST_WEST_SOUTH:
 * Type name: 'East West South' <br>
 * SubID: 11 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * EAST_WEST:
 * Type name: 'East West' <br>
 * SubID: 10 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * EAST_SOUTH:
 * Type name: 'East South' <br>
 * SubID: 9 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * EAST:
 * Type name: 'East' <br>
 * SubID: 8 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * NORTH_WEST_SOUTH:
 * Type name: 'North West South' <br>
 * SubID: 7 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * NORTH_WEST:
 * Type name: 'North West' <br>
 * SubID: 6 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * NORTH_SOUTH:
 * Type name: 'North South' <br>
 * SubID: 5 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * NORTH:
 * Type name: 'North' <br>
 * SubID: 4 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * SOUTH_WEST:
 * Type name: 'South West' <br>
 * SubID: 3 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * WEST:
 * Type name: 'West' <br>
 * SubID: 2 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * SOUTH:
 * Type name: 'South' <br>
 * SubID: 1 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * NONE:
 * Type name: 'None' <br>
 * SubID: 0 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class VineMat extends PlantMat
{
    /**
     * Bit flag defining if vines are attachment to south face of block.
     * If bit is set to 0, then it's not attachment to south face of block.
     */
    public static final byte SOUTH_FLAG       = 0x1;
    /**
     * Bit flag defining if vines are attachment to west face of block.
     * If bit is set to 0, then it's not attachment to west face of block.
     */
    public static final byte WEST_FLAG        = 0x2;
    /**
     * Bit flag defining if vines are attachment to north face of block.
     * If bit is set to 0, then it's not attachment to north face of block.
     */
    public static final byte NORTH_FLAG       = 0x4;
    /**
     * Bit flag defining if vines are attachment to east face of block.
     * If bit is set to 0, then it's not attachment to east face of block.
     */
    public static final byte EAST_FLAG        = 0x8;
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int  USED_DATA_VALUES = 1;

    public static final VineMat VINE                       = new VineMat();
    public static final VineMat VINE_SOUTH                 = new VineMat(BlockFace.SOUTH);
    public static final VineMat VINE_WEST                  = new VineMat(BlockFace.WEST);
    public static final VineMat VINE_SOUTH_WEST            = new VineMat(BlockFace.SOUTH, BlockFace.WEST);//
    public static final VineMat VINE_NORTH                 = new VineMat(BlockFace.NORTH);
    public static final VineMat VINE_NORTH_SOUTH           = new VineMat(BlockFace.NORTH, BlockFace.SOUTH);
    public static final VineMat VINE_NORTH_WEST            = new VineMat(BlockFace.NORTH, BlockFace.WEST);
    public static final VineMat VINE_NORTH_WEST_SOUTH      = new VineMat(BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH);
    public static final VineMat VINE_EAST                  = new VineMat(BlockFace.EAST);
    public static final VineMat VINE_EAST_SOUTH            = new VineMat(BlockFace.EAST, BlockFace.SOUTH);
    public static final VineMat VINE_EAST_WEST             = new VineMat(BlockFace.EAST, BlockFace.WEST);
    public static final VineMat VINE_EAST_SOUTH_WEST       = new VineMat(BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH);
    public static final VineMat VINE_EAST_NORTH            = new VineMat(BlockFace.EAST, BlockFace.NORTH);
    public static final VineMat VINE_EAST_NORTH_SOUTH      = new VineMat(BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH);
    public static final VineMat VINE_EAST_NORTH_WEST       = new VineMat(BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST);
    public static final VineMat VINE_EAST_NORTH_WEST_SOUTH = new VineMat(BlockFace.EAST, BlockFace.NORTH, BlockFace.WEST, BlockFace.SOUTH);

    private static final Map<String, VineMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final Byte2ObjectMap<VineMat> byID   = new Byte2ObjectOpenHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected final BlockFace[] faces;

    @SuppressWarnings("MagicNumber")
    public VineMat()
    {
        super("VINE", 106, "minecraft:vine", "NONE", (byte) 0x00, 0.2f, 1);
        this.faces = BlockFace.EMPTY;
    }

    public VineMat(final BlockFace... faces)
    {
        super(VINE.name(), VINE.ordinal(), VINE.getMinecraftId(), Arrays.stream(faces).map(Enum::name).reduce((a, b) -> a + "_" + b).orElse("NONE"), combine(faces), VINE.getHardness(), VINE.getBlastResistance());
        this.faces = faces.clone();
    }

    public VineMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace[] faces, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.faces = faces;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.VINE;
    }

    /**
     * @return copy of array of attachment faces.
     */
    public BlockFace[] getBlockFaces()
    {
        return this.faces.clone();
    }

    /**
     * Returns one of Vine sub-type based on {@link BlockFace[]}.
     *
     * @param faces array of attachment faces.
     *
     * @return sib-type of Vine
     */
    public VineMat getBlockFaces(final BlockFace... faces)
    {
        return getByID(combine(faces));
    }

    @Override
    public VineMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public VineMat getType(final int id)
    {
        return getByID(id);
    }

    private static byte combine(final BlockFace... faces)
    {
        byte result = 0x0;
        for (final BlockFace face : faces)
        {
            switch (face)
            {
                case SOUTH:
                    result |= SOUTH_FLAG;
                    break;
                case WEST:
                    result |= WEST_FLAG;
                    break;
                case NORTH:
                    result |= NORTH_FLAG;
                    break;
                case EAST:
                    result |= EAST_FLAG;
                    break;
                default:
                    break;
            }
        }
        return result;
    }

    /**
     * Returns one of Vine sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Vine or null
     */
    public static VineMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Vine sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Vine or null
     */
    public static VineMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Vine sub-type based on {@link BlockFace[]}.
     * It will never return null;
     *
     * @param faces array of attachment faces.
     *
     * @return sub-type of Vine
     */
    public static VineMat getVine(final BlockFace... faces)
    {
        return getByID(combine(faces));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final VineMat element)
    {
        allBlocks.incrementAndGet();
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public VineMat[] types()
    {
        return VineMat.vineTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static VineMat[] vineTypes()
    {
        return byID.values().toArray(new VineMat[byID.size()]);
    }

    static
    {
        VineMat.register(VINE);
        VineMat.register(VINE_SOUTH);
        VineMat.register(VINE_WEST);
        VineMat.register(VINE_SOUTH_WEST);
        VineMat.register(VINE_NORTH);
        VineMat.register(VINE_NORTH_SOUTH);
        VineMat.register(VINE_NORTH_WEST);
        VineMat.register(VINE_NORTH_WEST_SOUTH);
        VineMat.register(VINE_EAST);
        VineMat.register(VINE_EAST_SOUTH);
        VineMat.register(VINE_EAST_WEST);
        VineMat.register(VINE_EAST_SOUTH_WEST);
        VineMat.register(VINE_EAST_NORTH);
        VineMat.register(VINE_EAST_NORTH_SOUTH);
        VineMat.register(VINE_EAST_NORTH_WEST);
        VineMat.register(VINE_EAST_NORTH_WEST_SOUTH);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("faces", this.faces).toString();
    }
}
