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
import org.diorite.material.DirectionalMat;
import org.diorite.material.Material;
import org.diorite.material.PowerableMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Dropper' block material in minecraft. <br>
 * ID of block: 158 <br>
 * String ID of block: minecraft:dropper <br>
 * Hardness: 3,5 <br>
 * Blast Resistance 17,5 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * EAST_POWERED:
 * Type name: 'East Powered' <br>
 * SubID: 13 <br>
 * Hardness: 3,5 <br>
 * Blast Resistance 17,5 <br>
 * </li>
 * <li>
 * WEST_POWERED:
 * Type name: 'West Powered' <br>
 * SubID: 12 <br>
 * Hardness: 3,5 <br>
 * Blast Resistance 17,5 <br>
 * </li>
 * <li>
 * SOUTH_POWERED:
 * Type name: 'South Powered' <br>
 * SubID: 11 <br>
 * Hardness: 3,5 <br>
 * Blast Resistance 17,5 <br>
 * </li>
 * <li>
 * NORTH_POWERED:
 * Type name: 'North Powered' <br>
 * SubID: 10 <br>
 * Hardness: 3,5 <br>
 * Blast Resistance 17,5 <br>
 * </li>
 * <li>
 * UP_POWERED:
 * Type name: 'Up Powered' <br>
 * SubID: 9 <br>
 * Hardness: 3,5 <br>
 * Blast Resistance 17,5 <br>
 * </li>
 * <li>
 * DOWN_POWERED:
 * Type name: 'Down Powered' <br>
 * SubID: 8 <br>
 * Hardness: 3,5 <br>
 * Blast Resistance 17,5 <br>
 * </li>
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
 * SubID: 2 <br>
 * Hardness: 3,5 <br>
 * Blast Resistance 17,5 <br>
 * </li>
 * <li>
 * UP:
 * Type name: 'Up' <br>
 * SubID: 1 <br>
 * Hardness: 3,5 <br>
 * Blast Resistance 17,5 <br>
 * </li>
 * <li>
 * DOWN:
 * Type name: 'Down' <br>
 * SubID: 0 <br>
 * Hardness: 3,5 <br>
 * Blast Resistance 17,5 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class DropperMat extends StonyMat implements DirectionalMat, PowerableMat
{
    /**
     * Bit flag defining if Dropper is powered.
     * If bit is set to 0, then it isn't powered
     */
    public static final byte POWERED_FLAG     = 0x8;
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int  USED_DATA_VALUES = 12;

    public static final DropperMat DROPPER_DOWN  = new DropperMat();
    public static final DropperMat DROPPER_UP    = new DropperMat(BlockFace.UP, false);
    public static final DropperMat DROPPER_NORTH = new DropperMat(BlockFace.NORTH, false);
    public static final DropperMat DROPPER_SOUTH = new DropperMat(BlockFace.SOUTH, false);
    public static final DropperMat DROPPER_WEST  = new DropperMat(BlockFace.WEST, false);
    public static final DropperMat DROPPER_EAST  = new DropperMat(BlockFace.EAST, false);

    public static final DropperMat DROPPER_DOWN_POWERED  = new DropperMat(BlockFace.DOWN, true);
    public static final DropperMat DROPPER_UP_POWERED    = new DropperMat(BlockFace.UP, true);
    public static final DropperMat DROPPER_NORTH_POWERED = new DropperMat(BlockFace.NORTH, true);
    public static final DropperMat DROPPER_SOUTH_POWERED = new DropperMat(BlockFace.SOUTH, true);
    public static final DropperMat DROPPER_WEST_POWERED  = new DropperMat(BlockFace.WEST, true);
    public static final DropperMat DROPPER_EAST_POWERED  = new DropperMat(BlockFace.EAST, true);

    private static final Map<String, DropperMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DropperMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final BlockFace facing;
    protected final boolean   powered;

    @SuppressWarnings("MagicNumber")
    protected DropperMat()
    {
        super("DROPPER", 158, "minecraft:dropper", "DOWN", (byte) 0x00, 3.5f, 17.5f);
        this.facing = BlockFace.DOWN;
        this.powered = false;
    }

    protected DropperMat(final BlockFace facing, final boolean powered)
    {
        super(DROPPER_DOWN.name(), DROPPER_DOWN.ordinal(), DROPPER_DOWN.getMinecraftId(), facing.name() + (powered ? "_POWERED" : ""), combine(facing, powered), DROPPER_DOWN.getHardness(), DROPPER_DOWN.getBlastResistance());
        this.facing = facing;
        this.powered = powered;
    }

    protected DropperMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace facing, final boolean powered, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.facing = facing;
        this.powered = powered;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return DROPPER;
    }

    @Override
    public boolean isPowered()
    {
        return this.powered;
    }

    @Override
    public DropperMat getPowered(final boolean powered)
    {
        return getByID(combine(this.facing, powered));
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.facing;
    }

    @Override
    public DropperMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.powered));
    }

    public DropperMat getType(final BlockFace face, final boolean powered)
    {
        return getByID(combine(face, powered));
    }

    @Override
    public DropperMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DropperMat getType(final int id)
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
            case UP:
                result |= 0x01;
                break;
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
     * Returns one of Dropper sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Dropper or null
     */
    public static DropperMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Dropper sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Dropper or null
     */
    public static DropperMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Dropper sub-type based on {@link BlockFace} and powered state
     * It will never return null.
     *
     * @param face    facing direction of Dropper.
     * @param powered if Dropper should be powered/
     *
     * @return sub-type of Dropper
     */
    public static DropperMat getDropper(final BlockFace face, final boolean powered)
    {
        return getByID(combine(face, powered));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DropperMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public DropperMat[] types()
    {
        return DropperMat.dropperTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DropperMat[] dropperTypes()
    {
        return byID.values(new DropperMat[byID.size()]);
    }

    static
    {
        DropperMat.register(DROPPER_DOWN);
        DropperMat.register(DROPPER_UP);
        DropperMat.register(DROPPER_NORTH);
        DropperMat.register(DROPPER_SOUTH);
        DropperMat.register(DROPPER_WEST);
        DropperMat.register(DROPPER_EAST);
        DropperMat.register(DROPPER_DOWN_POWERED);
        DropperMat.register(DROPPER_UP_POWERED);
        DropperMat.register(DROPPER_NORTH_POWERED);
        DropperMat.register(DROPPER_SOUTH_POWERED);
        DropperMat.register(DROPPER_WEST_POWERED);
        DropperMat.register(DROPPER_EAST_POWERED);
    }
}
