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
 * Class representing 'Hopper' block material in minecraft. <br>
 * ID of block: 154 <br>
 * String ID of block: minecraft:hopper <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * EAST_POWERED:
 * Type name: 'East Powered' <br>
 * SubID: 13 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * WEST_POWERED:
 * Type name: 'West Powered' <br>
 * SubID: 12 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * SOUTH_POWERED:
 * Type name: 'South Powered' <br>
 * SubID: 11 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * NORTH_POWERED:
 * Type name: 'North Powered' <br>
 * SubID: 10 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * DOWN_POWERED:
 * Type name: 'Down Powered' <br>
 * SubID: 8 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * EAST:
 * Type name: 'East' <br>
 * SubID: 5 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * WEST:
 * Type name: 'West' <br>
 * SubID: 4 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * SOUTH:
 * Type name: 'South' <br>
 * SubID: 3 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * NORTH:
 * Type name: 'North' <br>
 * SubID: 2 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * <li>
 * DOWN:
 * Type name: 'Down' <br>
 * SubID: 0 <br>
 * Hardness: 3 <br>
 * Blast Resistance 15 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class HopperMat extends BlockMaterialData implements DirectionalMat, PowerableMat
{
    /**
     * Bit flag defining if Hopper is powered.
     * If bit is set to 0, then it isn't powered
     */
    public static final byte POWERED_FLAG     = 0x8;
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int  USED_DATA_VALUES = 10;

    public static final HopperMat HOPPER_DOWN  = new HopperMat();
    public static final HopperMat HOPPER_NORTH = new HopperMat(BlockFace.NORTH, false);
    public static final HopperMat HOPPER_SOUTH = new HopperMat(BlockFace.SOUTH, false);
    public static final HopperMat HOPPER_WEST  = new HopperMat(BlockFace.WEST, false);
    public static final HopperMat HOPPER_EAST  = new HopperMat(BlockFace.EAST, false);

    public static final HopperMat HOPPER_DOWN_POWERED  = new HopperMat(BlockFace.DOWN, true);
    public static final HopperMat HOPPER_NORTH_POWERED = new HopperMat(BlockFace.NORTH, true);
    public static final HopperMat HOPPER_SOUTH_POWERED = new HopperMat(BlockFace.SOUTH, true);
    public static final HopperMat HOPPER_WEST_POWERED  = new HopperMat(BlockFace.WEST, true);
    public static final HopperMat HOPPER_EAST_POWERED  = new HopperMat(BlockFace.EAST, true);

    private static final Map<String, HopperMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<HopperMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final BlockFace facing;
    protected final boolean   powered;

    @SuppressWarnings("MagicNumber")
    protected HopperMat()
    {
        super("HOPPER", 154, "minecraft:hopper", "DOWN", (byte) 0x00, 3, 15);
        this.facing = BlockFace.DOWN;
        this.powered = false;
    }

    protected HopperMat(final BlockFace facing, final boolean powered)
    {
        super(HOPPER_DOWN.name(), HOPPER_DOWN.ordinal(), HOPPER_DOWN.getMinecraftId(), facing.name() + (powered ? "_POWERED" : ""), combine(facing, powered), HOPPER_DOWN.getHardness(), HOPPER_DOWN.getBlastResistance());
        this.facing = facing;
        this.powered = powered;
    }

    protected HopperMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace facing, final boolean powered, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.facing = facing;
        this.powered = powered;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return Material.HOPPER;
    }

    @Override
    public boolean isPowered()
    {
        return this.powered;
    }

    @Override
    public HopperMat getPowered(final boolean powered)
    {
        return getByID(combine(this.facing, powered));
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.facing;
    }

    @Override
    public HopperMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.powered));
    }

    public HopperMat getType(final BlockFace face, final boolean powered)
    {
        return getByID(combine(face, powered));
    }

    @Override
    public HopperMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public HopperMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("facing", this.facing).append("powered", this.powered).toString();
    }

    private static byte combine(final BlockFace facing, final boolean powered)
    {
        byte result = powered ? POWERED_FLAG : 0x00;
        switch (facing)
        {
            case NORTH:
                result |= 0x02;
                break;
            case SOUTH:
                result |= 0x03;
                break;
            case WEST:
                result |= 0x04;
                break;
            case EAST:
                result |= 0x05;
                break;
            case DOWN:
            default:
                return result;
        }
        return result;
    }

    /**
     * Returns one of Hopper sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Hopper or null
     */
    public static HopperMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Hopper sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Hopper or null
     */
    public static HopperMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Hopper sub-type based on {@link BlockFace} and powered state
     * It will never return null.
     *
     * @param face    facing direction of Hopper.
     * @param powered if Hopper should be powered/
     *
     * @return sub-type of Hopper
     */
    public static HopperMat getHopper(final BlockFace face, final boolean powered)
    {
        return getByID(combine(face, powered));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final HopperMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public HopperMat[] types()
    {
        return HopperMat.hopperTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static HopperMat[] hopperTypes()
    {
        return byID.values(new HopperMat[byID.size()]);
    }

    static
    {
        HopperMat.register(HOPPER_DOWN);
        HopperMat.register(HOPPER_NORTH);
        HopperMat.register(HOPPER_SOUTH);
        HopperMat.register(HOPPER_WEST);
        HopperMat.register(HOPPER_EAST);
        HopperMat.register(HOPPER_DOWN_POWERED);
        HopperMat.register(HOPPER_NORTH_POWERED);
        HopperMat.register(HOPPER_SOUTH_POWERED);
        HopperMat.register(HOPPER_WEST_POWERED);
        HopperMat.register(HOPPER_EAST_POWERED);
    }
}
