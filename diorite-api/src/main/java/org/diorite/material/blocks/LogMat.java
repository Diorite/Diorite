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
import org.diorite.material.FuelMat;
import org.diorite.material.Material;
import org.diorite.material.RotatableMat;
import org.diorite.material.RotateAxisMat;
import org.diorite.material.WoodType;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing 'Log' block material in minecraft. <br>
 * ID of block: 17 <br>
 * String ID of block: minecraft:log <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * <br>
 * Subtypes: <br>
 * <ol>
 * <li>
 * DARK_OAK_NONE:
 * Type name: 'Dark Oak None' <br>
 * SubID: 13 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * <li>
 * ACACIA_NONE:
 * Type name: 'Acacia None' <br>
 * SubID: 12 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * <li>
 * DARK_OAK_NORTH_SOUTH:
 * Type name: 'Dark Oak North South' <br>
 * SubID: 9 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * <li>
 * ACACIA_NORTH_SOUTH:
 * Type name: 'Acacia North South' <br>
 * SubID: 8 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * <li>
 * DARK_OAK_EAST_WEST:
 * Type name: 'Dark Oak East West' <br>
 * SubID: 5 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * <li>
 * ACACIA_EAST_WEST:
 * Type name: 'Acacia East West' <br>
 * SubID: 4 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * <li>
 * DARK_OAK_UP_DOWN:
 * Type name: 'Dark Oak Up Down' <br>
 * SubID: 1 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * <li>
 * ACACIA_UP_DOWN:
 * Type name: 'Acacia Up Down' <br>
 * SubID: 0 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * <li>
 * JUNGLE_NONE:
 * Type name: 'Jungle None' <br>
 * SubID: 15 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * <li>
 * BIRCH_NONE:
 * Type name: 'Birch None' <br>
 * SubID: 14 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * <li>
 * SPRUCE_NONE:
 * Type name: 'Spruce None' <br>
 * SubID: 13 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * <li>
 * OAK_NONE:
 * Type name: 'Oak None' <br>
 * SubID: 12 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * <li>
 * JUNGLE_NORTH_SOUTH:
 * Type name: 'Jungle North South' <br>
 * SubID: 11 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * <li>
 * BIRCH_NORTH_SOUTH:
 * Type name: 'Birch North South' <br>
 * SubID: 10 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * <li>
 * SPRUCE_NORTH_SOUTH:
 * Type name: 'Spruce North South' <br>
 * SubID: 9 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * <li>
 * OAK_NORTH_SOUTH:
 * Type name: 'Oak North South' <br>
 * SubID: 8 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * <li>
 * JUNGLE_EAST_WEST:
 * Type name: 'Jungle East West' <br>
 * SubID: 7 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * <li>
 * BIRCH_EAST_WEST:
 * Type name: 'Birch East West' <br>
 * SubID: 6 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * <li>
 * SPRUCE_EAST_WEST:
 * Type name: 'Spruce East West' <br>
 * SubID: 5 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * <li>
 * OAK_EAST_WEST:
 * Type name: 'Oak East West' <br>
 * SubID: 4 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * <li>
 * JUNGLE_UP_DOWN:
 * Type name: 'Jungle Up Down' <br>
 * SubID: 3 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * <li>
 * BIRCH_UP_DOWN:
 * Type name: 'Birch Up Down' <br>
 * SubID: 2 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * <li>
 * SPRUCE_UP_DOWN:
 * Type name: 'Spruce Up Down' <br>
 * SubID: 1 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * <li>
 * QAK_UP_DOWN:
 * Type name: 'Qak Up Down' <br>
 * SubID: 0 <br>
 * Hardness: 2 <br>
 * Blast Resistance 10 <br>
 * </li>
 * </ol>
 */
@SuppressWarnings("JavaDoc")
public class LogMat extends WoodMat implements RotatableMat, FuelMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 24;

    public static final LogMat LOG_OAK      = new LogMat();
    public static final LogMat LOG_SPRUCE   = new LogMat(WoodType.SPRUCE, RotateAxisMat.UP_DOWN, 2, 10);
    public static final LogMat LOG_BIRCH    = new LogMat(WoodType.BIRCH, RotateAxisMat.UP_DOWN);
    public static final LogMat LOG_JUNGLE   = new LogMat(WoodType.JUNGLE, RotateAxisMat.UP_DOWN);
    public static final LogMat LOG_ACACIA   = new Log2(WoodType.ACACIA, RotateAxisMat.UP_DOWN);
    public static final LogMat LOG_DARK_OAK = new Log2(WoodType.DARK_OAK, RotateAxisMat.UP_DOWN);

    public static final LogMat LOG_OAK_EAST_WEST      = new LogMat(WoodType.OAK, RotateAxisMat.EAST_WEST);
    public static final LogMat LOG_SPRUCE_EAST_WEST   = new LogMat(WoodType.SPRUCE, RotateAxisMat.EAST_WEST);
    public static final LogMat LOG_BIRCH_EAST_WEST    = new LogMat(WoodType.BIRCH, RotateAxisMat.EAST_WEST);
    public static final LogMat LOG_JUNGLE_EAST_WEST   = new LogMat(WoodType.JUNGLE, RotateAxisMat.EAST_WEST);
    public static final LogMat LOG_ACACIA_EAST_WEST   = new Log2(WoodType.ACACIA, RotateAxisMat.EAST_WEST);
    public static final LogMat LOG_DARK_OAK_EAST_WEST = new Log2(WoodType.DARK_OAK, RotateAxisMat.EAST_WEST);

    public static final LogMat LOG_OAK_NORTH_SOUTH      = new LogMat(WoodType.OAK, RotateAxisMat.NORTH_SOUTH);
    public static final LogMat LOG_SPRUCE_NORTH_SOUTH   = new LogMat(WoodType.SPRUCE, RotateAxisMat.NORTH_SOUTH);
    public static final LogMat LOG_BIRCH_NORTH_SOUTH    = new LogMat(WoodType.BIRCH, RotateAxisMat.NORTH_SOUTH);
    public static final LogMat LOG_JUNGLE_NORTH_SOUTH   = new LogMat(WoodType.JUNGLE, RotateAxisMat.NORTH_SOUTH);
    public static final LogMat LOG_ACACIA_NORTH_SOUTH   = new Log2(WoodType.ACACIA, RotateAxisMat.NORTH_SOUTH);
    public static final LogMat LOG_DARK_OAK_NORTH_SOUTH = new Log2(WoodType.DARK_OAK, RotateAxisMat.NORTH_SOUTH);

    public static final LogMat LOG_OAK_BARK      = new LogMat(WoodType.OAK, RotateAxisMat.NONE);
    public static final LogMat LOG_SPRUCE_BARK   = new LogMat(WoodType.SPRUCE, RotateAxisMat.NONE);
    public static final LogMat LOG_BIRCH_BARK    = new LogMat(WoodType.BIRCH, RotateAxisMat.NONE);
    public static final LogMat LOG_JUNGLE_BARK   = new LogMat(WoodType.JUNGLE, RotateAxisMat.NONE);
    public static final LogMat LOG_ACACIA_BARK   = new Log2(WoodType.ACACIA, RotateAxisMat.NONE);
    public static final LogMat LOG_DARK_OAK_BARK = new Log2(WoodType.DARK_OAK, RotateAxisMat.NONE);

    private static final Map<String, LogMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<LogMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final RotateAxisMat rotateAxis;

    @SuppressWarnings("MagicNumber")
    protected LogMat()
    {
        super("LOG", 17, "minecraft:log", "QAK_UP_DOWN", (byte) 0x00, WoodType.OAK, 2, 10);
        this.rotateAxis = RotateAxisMat.UP_DOWN;
    }

    @SuppressWarnings("MagicNumber")
    protected LogMat(final WoodType type, final RotateAxisMat rotateAxis)
    {
        super(LOG_OAK.name() + (type.isSecondLogID() ? "2" : ""), type.isSecondLogID() ? 162 : 17, LOG_OAK.getMinecraftId() + (type.isSecondLogID() ? "2" : ""), type.name() + "_" + rotateAxis.name(), (byte) (type.getLogFlag() | rotateAxis.getFlag()), type, LOG_OAK.hardness, LOG_OAK.getBlastResistance());
        this.rotateAxis = rotateAxis;
    }

    @SuppressWarnings("MagicNumber")
    protected LogMat(final WoodType type, final RotateAxisMat rotateAxis, final float hardness, final float blastResistance)
    {
        super(LOG_OAK.name() + (type.isSecondLogID() ? "2" : ""), type.isSecondLogID() ? 162 : 17, LOG_OAK.getMinecraftId() + (type.isSecondLogID() ? "2" : ""), type.name() + "_" + rotateAxis.name(), (byte) (type.getLogFlag() | rotateAxis.getFlag()), type, hardness, blastResistance);
        this.rotateAxis = rotateAxis;
    }

    protected LogMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodType woodType, final RotateAxisMat rotateAxis, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType, hardness, blastResistance);
        this.rotateAxis = rotateAxis;
    }

    @Override
    public Material ensureValidInventoryItem()
    {
        return this.getType(this.woodType, RotateAxisMat.UP_DOWN);
    }

    @Override
    public RotateAxisMat getRotateAxis()
    {
        return this.rotateAxis;
    }

    @Override
    public LogMat getRotated(final RotateAxisMat axis)
    {
        return getLog(this.woodType, axis);
    }

    @Override
    public LogMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public LogMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public LogMat getWoodType(final WoodType woodType)
    {
        return getLog(woodType, this.rotateAxis);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("rotateAxis", this.rotateAxis).toString();
    }

    public LogMat getType(final WoodType woodType, final RotateAxisMat facing)
    {
        return getLog(woodType, facing);
    }

    @SuppressWarnings("MagicNumber")
    protected byte getFixedDataValue()
    {
        return (byte) (this.getType() + ((this.getWoodType().isSecondLogID()) ? 16 : 0));
    }

    @Override
    public LogMat getBlockFacing(final BlockFace face)
    {
        switch (face)
        {
            case NORTH:
            case SOUTH:
                return this.getRotated(RotateAxisMat.NORTH_SOUTH);
            case EAST:
            case WEST:
                return this.getRotated(RotateAxisMat.EAST_WEST);
            case UP:
            case DOWN:
                return this.getRotated(RotateAxisMat.UP_DOWN);
            case SELF:
                return this.getRotated(RotateAxisMat.NONE);
            default:
                return this.getRotated(RotateAxisMat.UP_DOWN);
        }
    }

    @SuppressWarnings("MagicNumber")
    @Override
    public int getFuelPower()
    {
        return 1500;
    }

    /**
     * Returns one of Log sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Log or null
     */
    public static LogMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Log sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Log or null
     */
    public static LogMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    @SuppressWarnings("MagicNumber")
    public static LogMat getLog(final WoodType type, final RotateAxisMat facing)
    {
        return getByID((type.getLogFlag() | facing.getFlag()) + (type.isSecondLogID() ? 16 : 0));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    @SuppressWarnings("MagicNumber")
    public static void register(final LogMat element)
    {
        byID.put((byte) (element.getType() + ((element instanceof Log2) ? 16 : 0)), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public LogMat[] types()
    {
        return LogMat.logTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static LogMat[] logTypes()
    {
        return byID.values(new LogMat[byID.size()]);
    }

    /**
     * Helper class for second log ID
     */
    public static class Log2 extends LogMat
    {
        protected Log2(final WoodType type, final RotateAxisMat rotateAxis)
        {
            super(type, rotateAxis);
        }

        protected Log2(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodType woodType, final RotateAxisMat rotateAxis, final float hardness, final float blastResistance)
        {
            super(enumName, id, minecraftId, maxStack, typeName, type, woodType, rotateAxis, hardness, blastResistance);
        }

        @Override
        public LogMat getType(final int id)
        {
            return getByID(id);
        }

        /**
         * Returns one of Log sub-type based on sub-id, may return null
         *
         * @param id sub-type id
         *
         * @return sub-type of Log or null
         */
        @SuppressWarnings("MagicNumber")
        public static LogMat getByID(final int id)
        {
            return byID.get((byte) (id + 16));
        }
    }

    static
    {
        LogMat.register(LOG_OAK);
        LogMat.register(LOG_SPRUCE);
        LogMat.register(LOG_BIRCH);
        LogMat.register(LOG_JUNGLE);
        LogMat.register(LOG_ACACIA);
        LogMat.register(LOG_DARK_OAK);
        LogMat.register(LOG_OAK_EAST_WEST);
        LogMat.register(LOG_SPRUCE_EAST_WEST);
        LogMat.register(LOG_BIRCH_EAST_WEST);
        LogMat.register(LOG_JUNGLE_EAST_WEST);
        LogMat.register(LOG_ACACIA_EAST_WEST);
        LogMat.register(LOG_DARK_OAK_EAST_WEST);
        LogMat.register(LOG_OAK_NORTH_SOUTH);
        LogMat.register(LOG_SPRUCE_NORTH_SOUTH);
        LogMat.register(LOG_BIRCH_NORTH_SOUTH);
        LogMat.register(LOG_JUNGLE_NORTH_SOUTH);
        LogMat.register(LOG_ACACIA_NORTH_SOUTH);
        LogMat.register(LOG_DARK_OAK_NORTH_SOUTH);
        LogMat.register(LOG_OAK_BARK);
        LogMat.register(LOG_SPRUCE_BARK);
        LogMat.register(LOG_BIRCH_BARK);
        LogMat.register(LOG_JUNGLE_BARK);
        LogMat.register(LOG_ACACIA_BARK);
        LogMat.register(LOG_DARK_OAK_BARK);
    }
}
