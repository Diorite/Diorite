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
import org.diorite.material.PowerableMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Redstone Comparator' block material in minecraft. <br>
 * ID of block: 149 <br>
 * String ID of block: minecraft:unpowered_comparator <br>
 * This block can't be used in inventory, valid material for this block: 'Redstone Comparator Item' (minecraft:comparator(404):0) <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * WEST_SUBTRACT_POWERED:
 * Type name: 'West Subtract Powered' <br>
 * SubID: 11 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * SOUTH_SUBTRACT_POWERED:
 * Type name: 'South Subtract Powered' <br>
 * SubID: 10 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * EAST_SUBTRACT_POWERED:
 * Type name: 'East Subtract Powered' <br>
 * SubID: 9 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * NORTH_SUBTRACT_POWERED:
 * Type name: 'North Subtract Powered' <br>
 * SubID: 8 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * WEST:
 * Type name: 'West' <br>
 * SubID: 3 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * SOUTH:
 * Type name: 'South' <br>
 * SubID: 2 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * EAST:
 * Type name: 'East' <br>
 * SubID: 1 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * <li>
 * NORTH:
 * Type name: 'North' <br>
 * SubID: 0 <br>
 * Hardness: 0 <br>
 * Blast Resistance 0 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class RedstoneComparatorMat extends BlockMaterialData implements PowerableMat, DirectionalMat
{
    /**
     * Bit flag defining if comparator is in subtraction mode.
     * If bit is set to 0, then it isn't in subtraction mode.
     */
    public static final byte SUBTRACTION_MODE_FLAG = 0x8;
    /**
     * Bit flag defining if comparator is powered.
     * If bit is set to 0, then it isn't powered
     */
    public static final byte POWERED_FLAG          = 0x8;
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int  USED_DATA_VALUES      = 16;

    public static final RedstoneComparatorMat REDSTONE_COMPARATOR_NORTH = new RedstoneComparatorMat();
    public static final RedstoneComparatorMat REDSTONE_COMPARATOR_EAST  = new RedstoneComparatorMat(BlockFace.EAST, false, false);
    public static final RedstoneComparatorMat REDSTONE_COMPARATOR_SOUTH = new RedstoneComparatorMat(BlockFace.SOUTH, false, false);
    public static final RedstoneComparatorMat REDSTONE_COMPARATOR_WEST  = new RedstoneComparatorMat(BlockFace.WEST, false, false);

    public static final RedstoneComparatorMat REDSTONE_COMPARATOR_NORTH_SUBTRACT = new RedstoneComparatorMat(BlockFace.NORTH, true, false);
    public static final RedstoneComparatorMat REDSTONE_COMPARATOR_EAST_SUBTRACT  = new RedstoneComparatorMat(BlockFace.EAST, true, false);
    public static final RedstoneComparatorMat REDSTONE_COMPARATOR_SOUTH_SUBTRACT = new RedstoneComparatorMat(BlockFace.SOUTH, true, false);
    public static final RedstoneComparatorMat REDSTONE_COMPARATOR_WEST_SUBTRACT  = new RedstoneComparatorMat(BlockFace.WEST, true, false);

    public static final RedstoneComparatorMat REDSTONE_COMPARATOR_NORTH_POWERED = new RedstoneComparatorMat(BlockFace.NORTH, false, true);
    public static final RedstoneComparatorMat REDSTONE_COMPARATOR_EAST_POWERED  = new RedstoneComparatorMat(BlockFace.EAST, false, true);
    public static final RedstoneComparatorMat REDSTONE_COMPARATOR_SOUTH_POWERED = new RedstoneComparatorMat(BlockFace.SOUTH, false, true);
    public static final RedstoneComparatorMat REDSTONE_COMPARATOR_WEST_POWERED  = new RedstoneComparatorMat(BlockFace.WEST, false, true);

    public static final RedstoneComparatorMat REDSTONE_COMPARATOR_NORTH_SUBTRACT_POWERED = new RedstoneComparatorMat(BlockFace.NORTH, true, true);
    public static final RedstoneComparatorMat REDSTONE_COMPARATOR_EAST_SUBTRACT_POWERED  = new RedstoneComparatorMat(BlockFace.EAST, true, true);
    public static final RedstoneComparatorMat REDSTONE_COMPARATOR_SOUTH_SUBTRACT_POWERED = new RedstoneComparatorMat(BlockFace.SOUTH, true, true);
    public static final RedstoneComparatorMat REDSTONE_COMPARATOR_WEST_SUBTRACT_POWERED  = new RedstoneComparatorMat(BlockFace.WEST, true, true);

    private static final Map<String, RedstoneComparatorMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneComparatorMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final BlockFace face;
    protected final boolean   subtractionMode;
    protected final boolean   powered;

    @SuppressWarnings("MagicNumber")
    protected RedstoneComparatorMat()
    {
        super("REDSTONE_COMPARATOR", 149, "minecraft:unpowered_comparator", "NORTH", (byte) 0x00, 0, 0);
        this.face = BlockFace.NORTH;
        this.subtractionMode = false;
        this.powered = false;
    }

    protected RedstoneComparatorMat(final BlockFace face, final boolean subtractionMode, final boolean powered)
    {
        super(REDSTONE_COMPARATOR_NORTH.name(), REDSTONE_COMPARATOR_NORTH.ordinal(), REDSTONE_COMPARATOR_NORTH.getMinecraftId(), face.name() + (subtractionMode ? "_SUBTRACT" : "") + (powered ? "_POWERED" : ""), combine(face, subtractionMode, powered), REDSTONE_COMPARATOR_NORTH.getHardness(), REDSTONE_COMPARATOR_NORTH.getBlastResistance());
        this.face = face;
        this.subtractionMode = subtractionMode;
        this.powered = powered;
    }

    protected RedstoneComparatorMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final boolean subtractionMode, final boolean powered, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.face = face;
        this.subtractionMode = subtractionMode;
        this.powered = powered;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.REDSTONE_COMPARATOR_ITEM;
    }

    private static byte combine(final BlockFace face, final boolean subtractionMode, final boolean powered)
    {
        byte result = subtractionMode ? SUBTRACTION_MODE_FLAG : 0x0;
        switch (face)
        {
            case EAST:
                result |= 0x1;
                break;
            case SOUTH:
                result |= 0x2;
                break;
            case WEST:
                result |= 0x3;
                break;
            default:
                break;
        }
        if (powered)
        {
            result |= POWERED_FLAG;
        }
        return result;
    }

    @Override
    public RedstoneComparatorMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneComparatorMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * @return true if RedstoneComparator is in subtraction mode.
     */
    public boolean isSubtractionMode()
    {
        return this.subtractionMode;
    }

    /**
     * Returns one of RedstoneComparator sub-type based on subtraction mode state.
     *
     * @param subtractionMode if RedstoneComparator should be in subtraction mode.
     *
     * @return sub-type of RedstoneComparator
     */
    public RedstoneComparatorMat getSubtractionMode(final boolean subtractionMode)
    {
        return getByID(combine(this.face, subtractionMode, this.powered));
    }

    @Override
    public boolean isPowered()
    {
        return this.powered;
    }

    @Override
    public RedstoneComparatorMat getPowered(final boolean powered)
    {
        return getByID(combine(this.face, this.subtractionMode, powered));
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public DirectionalMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.subtractionMode, this.powered));
    }

    /**
     * Returns one of RedstoneComparator sub-type based on {@link BlockFace}, subtraction mode and powered state.
     *
     * @param face            facing direction of RedstoneComparator.
     * @param subtractionMode if RedstoneComparator should be in subtraction mode.
     * @param powered         if RedstoneComparator should be powered.
     *
     * @return sub-type of RedstoneComparator
     */
    public RedstoneComparatorMat getType(final BlockFace face, final boolean subtractionMode, final boolean powered)
    {
        return getByID(combine(face, subtractionMode, powered));
    }

    /**
     * Returns one of RedstoneComparator sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RedstoneComparator or null
     */
    public static RedstoneComparatorMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of RedstoneComparator sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RedstoneComparator or null
     */
    public static RedstoneComparatorMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of RedstoneComparator sub-type based on {@link BlockFace}, subtraction mode and powered state.
     * It will never return null.
     *
     * @param face            facing direction of RedstoneComparator.
     * @param subtractionMode if RedstoneComparator should be in subtraction mode.
     * @param powered         if RedstoneComparator should be powered.
     *
     * @return sub-type of RedstoneComparator
     */
    public static RedstoneComparatorMat getRedstoneComparator(final BlockFace face, final boolean subtractionMode, final boolean powered)
    {
        return getByID(combine(face, subtractionMode, powered));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final RedstoneComparatorMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public RedstoneComparatorMat[] types()
    {
        return RedstoneComparatorMat.redstoneComparatorTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static RedstoneComparatorMat[] redstoneComparatorTypes()
    {
        return byID.values(new RedstoneComparatorMat[byID.size()]);
    }

    static
    {
        RedstoneComparatorMat.register(REDSTONE_COMPARATOR_NORTH);
        RedstoneComparatorMat.register(REDSTONE_COMPARATOR_EAST);
        RedstoneComparatorMat.register(REDSTONE_COMPARATOR_SOUTH);
        RedstoneComparatorMat.register(REDSTONE_COMPARATOR_WEST);
        RedstoneComparatorMat.register(REDSTONE_COMPARATOR_NORTH_SUBTRACT);
        RedstoneComparatorMat.register(REDSTONE_COMPARATOR_EAST_SUBTRACT);
        RedstoneComparatorMat.register(REDSTONE_COMPARATOR_SOUTH_SUBTRACT);
        RedstoneComparatorMat.register(REDSTONE_COMPARATOR_WEST_SUBTRACT);
        RedstoneComparatorMat.register(REDSTONE_COMPARATOR_NORTH_POWERED);
        RedstoneComparatorMat.register(REDSTONE_COMPARATOR_EAST_POWERED);
        RedstoneComparatorMat.register(REDSTONE_COMPARATOR_SOUTH_POWERED);
        RedstoneComparatorMat.register(REDSTONE_COMPARATOR_WEST_POWERED);
        RedstoneComparatorMat.register(REDSTONE_COMPARATOR_NORTH_SUBTRACT_POWERED);
        RedstoneComparatorMat.register(REDSTONE_COMPARATOR_EAST_SUBTRACT_POWERED);
        RedstoneComparatorMat.register(REDSTONE_COMPARATOR_SOUTH_SUBTRACT_POWERED);
        RedstoneComparatorMat.register(REDSTONE_COMPARATOR_WEST_SUBTRACT_POWERED);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("subtractionMode", this.subtractionMode).append("powered", this.powered).toString();
    }
}
