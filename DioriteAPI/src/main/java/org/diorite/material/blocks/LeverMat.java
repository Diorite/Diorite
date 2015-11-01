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
import org.diorite.material.AttachableMat;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.PowerableMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Lever' block material in minecraft. <br>
 * ID of block: 69 <br>
 * String ID of block: minecraft:lever <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * DOWN_SOUTH_POWERED:
 * Type name: 'Down South Powered' <br>
 * SubID: 15 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * UP_POWERED:
 * Type name: 'Up Powered' <br>
 * SubID: 14 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * UP_SOUTH_POWERED:
 * Type name: 'Up South Powered' <br>
 * SubID: 13 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * NORTH_POWERED:
 * Type name: 'North Powered' <br>
 * SubID: 12 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * SOUTH_POWERED:
 * Type name: 'South Powered' <br>
 * SubID: 11 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * WEST_POWERED:
 * Type name: 'West Powered' <br>
 * SubID: 10 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * EAST_POWERED:
 * Type name: 'East Powered' <br>
 * SubID: 9 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * DOWN_POWERED:
 * Type name: 'Down Powered' <br>
 * SubID: 8 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * DOWN_SOUTH:
 * Type name: 'Down South' <br>
 * SubID: 7 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * UP:
 * Type name: 'Up' <br>
 * SubID: 6 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * UP_SOUTH:
 * Type name: 'Up South' <br>
 * SubID: 5 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * NORTH:
 * Type name: 'North' <br>
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
 * WEST:
 * Type name: 'West' <br>
 * SubID: 2 <br>
 * Hardness: 0,5 <br>
 * Blast Resistance 2,5 <br>
 * </li>
 * <li>
 * EAST:
 * Type name: 'East' <br>
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
public class LeverMat extends BlockMaterialData implements PowerableMat, AttachableMat
{
    /**
     * Bit flag defining if level is powered/activated.
     * If bit is set to 0, then it isn't powered/activated
     */
    public static final byte POWERED_FLAG     = 0x8;
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int  USED_DATA_VALUES = 16;

    public static final LeverMat LEVER_DOWN               = new LeverMat();
    public static final LeverMat LEVER_EAST               = new LeverMat(BlockFace.EAST, false, false);
    public static final LeverMat LEVER_WEST               = new LeverMat(BlockFace.WEST, false, false);
    public static final LeverMat LEVER_SOUTH              = new LeverMat(BlockFace.SOUTH, false, false);
    public static final LeverMat LEVER_NORTH              = new LeverMat(BlockFace.NORTH, false, false);
    public static final LeverMat LEVER_UP_SOUTH           = new LeverMat(BlockFace.UP, true, false);
    public static final LeverMat LEVER_UP                 = new LeverMat(BlockFace.UP, false, false);
    public static final LeverMat LEVER_DOWN_SOUTH         = new LeverMat(BlockFace.DOWN, true, false);
    public static final LeverMat LEVER_DOWN_POWERED       = new LeverMat(BlockFace.DOWN, false, true);
    public static final LeverMat LEVER_EAST_POWERED       = new LeverMat(BlockFace.EAST, false, true);
    public static final LeverMat LEVER_WEST_POWERED       = new LeverMat(BlockFace.WEST, false, true);
    public static final LeverMat LEVER_SOUTH_POWERED      = new LeverMat(BlockFace.SOUTH, false, true);
    public static final LeverMat LEVER_NORTH_POWERED      = new LeverMat(BlockFace.NORTH, false, true);
    public static final LeverMat LEVER_UP_SOUTH_POWERED   = new LeverMat(BlockFace.UP, true, true);
    public static final LeverMat LEVER_UP_POWERED         = new LeverMat(BlockFace.UP, false, true);
    public static final LeverMat LEVER_DOWN_SOUTH_POWERED = new LeverMat(BlockFace.DOWN, true, true);

    private static final Map<String, LeverMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<LeverMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final BlockFace face;
    protected final boolean   rotated;
    protected final boolean   powered;

    @SuppressWarnings("MagicNumber")
    protected LeverMat()
    {
        super("LEVER", 69, "minecraft:lever", "DOWN", (byte) 0x00, 0.5f, 2.5f);
        this.face = BlockFace.DOWN;
        this.rotated = false;
        this.powered = false;
    }

    protected LeverMat(final BlockFace face, final boolean rotated, final boolean powered)
    {
        super(LEVER_DOWN.name(), LEVER_DOWN.ordinal(), LEVER_DOWN.getMinecraftId(), face.name() + (rotated ? "_SOUTH" : "") + (powered ? "_POWERED" : ""), combine(face, rotated, powered), LEVER_DOWN.getHardness(), LEVER_DOWN.getBlastResistance());
        this.face = face;
        this.rotated = rotated;
        this.powered = powered;
    }

    protected LeverMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final BlockFace face, final boolean rotated, final boolean powered, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.face = face;
        this.rotated = rotated;
        this.powered = powered;
    }

    @Override
    public LeverMat getAttachedFace(final BlockFace face)
    {
        return getByID(combine(face.getOppositeFace(), this.rotated, this.powered));
    }

    private static byte combine(final BlockFace face, final boolean rotated, final boolean powered)
    {
        byte result = powered ? POWERED_FLAG : 0x0;
        switch (face)
        {
            case EAST:
                result |= 0x1;
                break;
            case WEST:
                result |= 0x2;
                break;
            case SOUTH:
                result |= 0x3;
                break;
            case NORTH:
                result |= 0x4;
                break;
            case UP:
                if (rotated)
                {
                    result |= 0x5;
                }
                else
                {
                    result |= 0x6;
                }
                break;
            case DOWN:
                if (rotated)
                {
                    result |= 0x7;
                }
                break;
            default:
                break;
        }
        return result;
    }

    @Override
    public LeverMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public LeverMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Level is rotated if block facing is down or up and it's point south when off.
     *
     * @return if level is rotated.
     */
    public boolean isRotated()
    {
        return this.rotated;
    }

    /**
     * Returns one of Lever sub-type based on rotated state.
     * It will never return null;
     *
     * @param rotated if lever should be rotated, apply only for lever facing up or down.
     *
     * @return sub-type of Level
     */
    public LeverMat getRotated(final boolean rotated)
    {
        return getByID(combine(this.face, rotated, this.powered));
    }

    @Override
    public boolean isPowered()
    {
        return this.powered;
    }

    @Override
    public LeverMat getPowered(final boolean powered)
    {
        return getByID(combine(this.face, this.rotated, powered));
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public LeverMat getBlockFacing(final BlockFace face)
    {
        return getByID(combine(face, this.rotated, this.powered));
    }

    public LeverMat getType(final BlockFace face, final boolean rotated, final boolean powered)
    {
        return getByID(combine(face, rotated, powered));
    }

    /**
     * Returns one of Lever sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Lever or null
     */
    public static LeverMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Lever sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Lever or null
     */
    public static LeverMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Lever sub-type based on {@link BlockFace} rotated and powered state.
     * It will never return null;
     *
     * @param face    facing direction of Level.
     * @param rotated if lever should be rotated, apply only for lever facing up or down.
     * @param powered if lever should be powered.
     *
     * @return sub-type of Level
     */
    public static LeverMat getLever(final BlockFace face, final boolean rotated, final boolean powered)
    {
        return getByID(combine(face, rotated, powered));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final LeverMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public LeverMat[] types()
    {
        return LeverMat.leverTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static LeverMat[] leverTypes()
    {
        return byID.values(new LeverMat[byID.size()]);
    }

    static
    {
        LeverMat.register(LEVER_DOWN);
        LeverMat.register(LEVER_EAST);
        LeverMat.register(LEVER_WEST);
        LeverMat.register(LEVER_SOUTH);
        LeverMat.register(LEVER_NORTH);
        LeverMat.register(LEVER_UP_SOUTH);
        LeverMat.register(LEVER_UP);
        LeverMat.register(LEVER_DOWN_SOUTH);
        LeverMat.register(LEVER_DOWN_POWERED);
        LeverMat.register(LEVER_EAST_POWERED);
        LeverMat.register(LEVER_WEST_POWERED);
        LeverMat.register(LEVER_SOUTH_POWERED);
        LeverMat.register(LEVER_NORTH_POWERED);
        LeverMat.register(LEVER_UP_SOUTH_POWERED);
        LeverMat.register(LEVER_UP_POWERED);
        LeverMat.register(LEVER_DOWN_SOUTH_POWERED);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("rotated", this.rotated).append("powered", this.powered).toString();
    }
}
