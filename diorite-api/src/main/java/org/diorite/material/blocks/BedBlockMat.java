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
 * Class representing 'Bed Block' block material in minecraft. <br>
 * ID of block: 26 <br>
 * String ID of block: minecraft:bed <br>
 * This block can't be used in inventory, valid material for this block: 'Bed' (minecraft:bed(355):0) <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * HEAD_EAST_OCCUPIED:
 * Type name: 'Head East Occupied' <br>
 * SubID: 15 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * HEAD_NORTH_OCCUPIED:
 * Type name: 'Head North Occupied' <br>
 * SubID: 14 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * HEAD_WEST_OCCUPIED:
 * Type name: 'Head West Occupied' <br>
 * SubID: 13 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * HEAD_SOUTH_OCCUPIED:
 * Type name: 'Head South Occupied' <br>
 * SubID: 12 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * HEAD_EAST:
 * Type name: 'Head East' <br>
 * SubID: 11 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * HEAD_NORTH:
 * Type name: 'Head North' <br>
 * SubID: 10 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * HEAD_WEST:
 * Type name: 'Head West' <br>
 * SubID: 9 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * HEAD_SOUTH:
 * Type name: 'Head South' <br>
 * SubID: 8 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * FOOT_EAST_OCCUPIED:
 * Type name: 'Foot East Occupied' <br>
 * SubID: 7 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * FOOT_NORTH_OCCUPIED:
 * Type name: 'Foot North Occupied' <br>
 * SubID: 6 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * FOOT_WEST_OCCUPIED:
 * Type name: 'Foot West Occupied' <br>
 * SubID: 5 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * FOOT_SOUTH_OCCUPIED:
 * Type name: 'Foot South Occupied' <br>
 * SubID: 4 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * FOOT_EAST:
 * Type name: 'Foot East' <br>
 * SubID: 3 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * FOOT_NORTH:
 * Type name: 'Foot North' <br>
 * SubID: 2 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * FOOT_WEST:
 * Type name: 'Foot West' <br>
 * SubID: 1 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * <li>
 * FOOT_SOUTH:
 * Type name: 'Foot South' <br>
 * SubID: 0 <br>
 * Hardness: 0,2 <br>
 * Blast Resistance 1 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class BedBlockMat extends BlockMaterialData implements DirectionalMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 16;

    public static final byte OCCUPIED_FLAG  = 0x04;
    public static final byte HEAD_PART_FLAG = 0x08; // 1 for head, 0 for foot part

    public static final BedBlockMat BED_FOOT_SOUTH = new BedBlockMat();
    public static final BedBlockMat BED_FOOT_WEST  = new BedBlockMat(BlockFace.WEST, false, false);
    public static final BedBlockMat BED_FOOT_NORTH = new BedBlockMat(BlockFace.NORTH, false, false);
    public static final BedBlockMat BED_FOOT_EAST  = new BedBlockMat(BlockFace.EAST, false, false);

    public static final BedBlockMat BED_FOOT_SOUTH_OCCUPIED = new BedBlockMat(BlockFace.SOUTH, false, true);
    public static final BedBlockMat BED_FOOT_WEST_OCCUPIED  = new BedBlockMat(BlockFace.WEST, false, true);
    public static final BedBlockMat BED_FOOT_NORTH_OCCUPIED = new BedBlockMat(BlockFace.NORTH, false, true);
    public static final BedBlockMat BED_FOOT_EAST_OCCUPIED  = new BedBlockMat(BlockFace.EAST, false, true);

    public static final BedBlockMat BED_HEAD_SOUTH = new BedBlockMat(BlockFace.SOUTH, true, false);
    public static final BedBlockMat BED_HEAD_WEST  = new BedBlockMat(BlockFace.WEST, true, false);
    public static final BedBlockMat BED_HEAD_NORTH = new BedBlockMat(BlockFace.NORTH, true, false);
    public static final BedBlockMat BED_HEAD_EAST  = new BedBlockMat(BlockFace.EAST, true, false);

    public static final BedBlockMat BED_HEAD_SOUTH_OCCUPIED = new BedBlockMat(BlockFace.SOUTH, true, true);
    public static final BedBlockMat BED_HEAD_WEST_OCCUPIED  = new BedBlockMat(BlockFace.WEST, true, true);
    public static final BedBlockMat BED_HEAD_NORTH_OCCUPIED = new BedBlockMat(BlockFace.NORTH, true, true);
    public static final BedBlockMat BED_HEAD_EAST_OCCUPIED  = new BedBlockMat(BlockFace.EAST, true, true);

    private static final Map<String, BedBlockMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<BedBlockMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    private final BlockFace blockFacing;
    private final boolean   isHeadPart;
    private final boolean   isOccupied;

    @SuppressWarnings("MagicNumber")
    protected BedBlockMat()
    {
        super("BED_BLOCK", 26, "minecraft:bed", 1, "FOOT_SOUTH", (byte) 0x00, 0.2f, 1);
        this.blockFacing = BlockFace.SOUTH;
        this.isHeadPart = false;
        this.isOccupied = false;
    }

    protected BedBlockMat(final BlockFace face, final boolean isHeadPart, final boolean isOccupied)
    {
        super(BED_FOOT_SOUTH.name(), BED_FOOT_SOUTH.ordinal(), BED_FOOT_SOUTH.getMinecraftId(), BED_FOOT_SOUTH.getMaxStack(), (isHeadPart ? "HEAD_" : "FOOT_") + face.name() + (isOccupied ? "_OCCUPIED" : ""), combine(face, isHeadPart, isOccupied), BED_FOOT_SOUTH.getHardness(), BED_FOOT_SOUTH.getBlastResistance());
        this.blockFacing = face;
        this.isHeadPart = isHeadPart;
        this.isOccupied = isOccupied;
    }

    protected BedBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace blockFacing, final boolean isHeadPart, final boolean isOccupied, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.blockFacing = blockFacing;
        this.isHeadPart = isHeadPart;
        this.isOccupied = isOccupied;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.BED;
    }

    public boolean isHeadPart()
    {
        return this.isHeadPart;
    }

    public boolean isOccupied()
    {
        return this.isOccupied;
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.blockFacing;
    }

    @Override
    public BedBlockMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.isHeadPart, this.isOccupied));
    }

    public BedBlockMat getHeadPart(final boolean isHeadPart)
    {
        return getByID(combine(this.blockFacing, isHeadPart, this.isOccupied));
    }

    public BedBlockMat getOccuped(final boolean isOccupied)
    {
        return getByID(combine(this.blockFacing, this.isHeadPart, isOccupied));
    }

    @Override
    public BedBlockMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public BedBlockMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("blockFacing", this.blockFacing).append("isHeadPart", this.isHeadPart).append("isOccupied", this.isOccupied).toString();
    }

    public BedBlockMat getType(final BlockFace face, final boolean isHeadPart, final boolean isOccupied)
    {
        return getByID(combine(face, isHeadPart, isOccupied));
    }

    private static byte combine(final BlockFace face, final boolean isHeadPart, final boolean isOccupied)
    {
        byte result = isHeadPart ? HEAD_PART_FLAG : 0x00;
        if (isOccupied)
        {
            result |= OCCUPIED_FLAG;
        }
        switch (face)
        {
            case WEST:
                result |= 0x01;
                break;
            case NORTH:
                result |= 0x02;
                break;
            case EAST:
                result |= 0x03;
                break;
            default:
                return result;
        }
        return result;
    }

    /**
     * Returns one of BedBlock sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of BedBlock or null
     */
    public static BedBlockMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of BedBlock sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of BedBlock or null
     */
    public static BedBlockMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static BedBlockMat getBed(final BlockFace face, final boolean isHeadPart, final boolean isOccupied)
    {
        return getByID(combine(face, isHeadPart, isOccupied));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BedBlockMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public BedBlockMat[] types()
    {
        return BedBlockMat.bedBlockTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static BedBlockMat[] bedBlockTypes()
    {
        return byID.values(new BedBlockMat[byID.size()]);
    }

    static
    {
        BedBlockMat.register(BED_FOOT_SOUTH);
        BedBlockMat.register(BED_FOOT_WEST);
        BedBlockMat.register(BED_FOOT_NORTH);
        BedBlockMat.register(BED_FOOT_EAST);
        BedBlockMat.register(BED_FOOT_SOUTH_OCCUPIED);
        BedBlockMat.register(BED_FOOT_WEST_OCCUPIED);
        BedBlockMat.register(BED_FOOT_NORTH_OCCUPIED);
        BedBlockMat.register(BED_FOOT_EAST_OCCUPIED);
        BedBlockMat.register(BED_HEAD_SOUTH);
        BedBlockMat.register(BED_HEAD_WEST);
        BedBlockMat.register(BED_HEAD_NORTH);
        BedBlockMat.register(BED_HEAD_EAST);
        BedBlockMat.register(BED_HEAD_SOUTH_OCCUPIED);
        BedBlockMat.register(BED_HEAD_WEST_OCCUPIED);
        BedBlockMat.register(BED_HEAD_NORTH_OCCUPIED);
        BedBlockMat.register(BED_HEAD_EAST_OCCUPIED);
    }
}
