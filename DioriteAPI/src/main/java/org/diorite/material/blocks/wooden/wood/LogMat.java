package org.diorite.material.blocks.wooden.wood;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.FuelMat;
import org.diorite.material.Material;
import org.diorite.material.WoodTypeMat;
import org.diorite.material.blocks.RotatableMat;
import org.diorite.material.blocks.RotateAxisMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Log" and all its subtypes.
 */
public class LogMat extends WoodMat implements RotatableMat, FuelMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 24;

    public static final LogMat LOG_OAK      = new LogMat();
    public static final LogMat LOG_SPRUCE   = new LogMat(WoodTypeMat.SPRUCE, RotateAxisMat.UP_DOWN, 2, 10);
    public static final LogMat LOG_BIRCH    = new LogMat(WoodTypeMat.BIRCH, RotateAxisMat.UP_DOWN);
    public static final LogMat LOG_JUNGLE   = new LogMat(WoodTypeMat.JUNGLE, RotateAxisMat.UP_DOWN);
    public static final LogMat LOG_ACACIA   = new Log2(WoodTypeMat.ACACIA, RotateAxisMat.UP_DOWN);
    public static final LogMat LOG_DARK_OAK = new Log2(WoodTypeMat.DARK_OAK, RotateAxisMat.UP_DOWN);

    public static final LogMat LOG_OAK_EAST_WEST      = new LogMat(WoodTypeMat.OAK, RotateAxisMat.EAST_WEST);
    public static final LogMat LOG_SPRUCE_EAST_WEST   = new LogMat(WoodTypeMat.SPRUCE, RotateAxisMat.EAST_WEST);
    public static final LogMat LOG_BIRCH_EAST_WEST    = new LogMat(WoodTypeMat.BIRCH, RotateAxisMat.EAST_WEST);
    public static final LogMat LOG_JUNGLE_EAST_WEST   = new LogMat(WoodTypeMat.JUNGLE, RotateAxisMat.EAST_WEST);
    public static final LogMat LOG_ACACIA_EAST_WEST   = new Log2(WoodTypeMat.ACACIA, RotateAxisMat.EAST_WEST);
    public static final LogMat LOG_DARK_OAK_EAST_WEST = new Log2(WoodTypeMat.DARK_OAK, RotateAxisMat.EAST_WEST);

    public static final LogMat LOG_OAK_NORTH_SOUTH      = new LogMat(WoodTypeMat.OAK, RotateAxisMat.NORTH_SOUTH);
    public static final LogMat LOG_SPRUCE_NORTH_SOUTH   = new LogMat(WoodTypeMat.SPRUCE, RotateAxisMat.NORTH_SOUTH);
    public static final LogMat LOG_BIRCH_NORTH_SOUTH    = new LogMat(WoodTypeMat.BIRCH, RotateAxisMat.NORTH_SOUTH);
    public static final LogMat LOG_JUNGLE_NORTH_SOUTH   = new LogMat(WoodTypeMat.JUNGLE, RotateAxisMat.NORTH_SOUTH);
    public static final LogMat LOG_ACACIA_NORTH_SOUTH   = new Log2(WoodTypeMat.ACACIA, RotateAxisMat.NORTH_SOUTH);
    public static final LogMat LOG_DARK_OAK_NORTH_SOUTH = new Log2(WoodTypeMat.DARK_OAK, RotateAxisMat.NORTH_SOUTH);

    public static final LogMat LOG_OAK_BARK      = new LogMat(WoodTypeMat.OAK, RotateAxisMat.NONE);
    public static final LogMat LOG_SPRUCE_BARK   = new LogMat(WoodTypeMat.SPRUCE, RotateAxisMat.NONE);
    public static final LogMat LOG_BIRCH_BARK    = new LogMat(WoodTypeMat.BIRCH, RotateAxisMat.NONE);
    public static final LogMat LOG_JUNGLE_BARK   = new LogMat(WoodTypeMat.JUNGLE, RotateAxisMat.NONE);
    public static final LogMat LOG_ACACIA_BARK   = new Log2(WoodTypeMat.ACACIA, RotateAxisMat.NONE);
    public static final LogMat LOG_DARK_OAK_BARK = new Log2(WoodTypeMat.DARK_OAK, RotateAxisMat.NONE);

    private static final Map<String, LogMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<LogMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    protected final RotateAxisMat rotateAxis;

    @SuppressWarnings("MagicNumber")
    protected LogMat()
    {
        super("LOG", 17, "minecraft:log", "QAK_UP_DOWN", (byte) 0x00, WoodTypeMat.OAK, 2, 10);
        this.rotateAxis = RotateAxisMat.UP_DOWN;
    }

    @SuppressWarnings("MagicNumber")
    protected LogMat(final WoodTypeMat type, final RotateAxisMat rotateAxis)
    {
        super(LOG_OAK.name() + (type.isSecondLogID() ? "2" : ""), type.isSecondLogID() ? 162 : 17, LOG_OAK.getMinecraftId() + (type.isSecondLogID() ? "2" : ""), type.name() + "_" + rotateAxis.name(), (byte) (type.getLogFlag() | rotateAxis.getFlag()), type, LOG_OAK.hardness, LOG_OAK.getBlastResistance());
        this.rotateAxis = rotateAxis;
    }

    @SuppressWarnings("MagicNumber")
    protected LogMat(final WoodTypeMat type, final RotateAxisMat rotateAxis, final float hardness, final float blastResistance)
    {
        super(LOG_OAK.name() + (type.isSecondLogID() ? "2" : ""), type.isSecondLogID() ? 162 : 17, LOG_OAK.getMinecraftId() + (type.isSecondLogID() ? "2" : ""), type.name() + "_" + rotateAxis.name(), (byte) (type.getLogFlag() | rotateAxis.getFlag()), type, hardness, blastResistance);
        this.rotateAxis = rotateAxis;
    }

    protected LogMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodTypeMat woodType, final RotateAxisMat rotateAxis, final float hardness, final float blastResistance)
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
    public LogMat getWoodType(final WoodTypeMat woodType)
    {
        return getLog(woodType, this.rotateAxis);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("rotateAxis", this.rotateAxis).toString();
    }

    public LogMat getType(final WoodTypeMat woodType, final RotateAxisMat facing)
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
    public static LogMat getLog(final WoodTypeMat type, final RotateAxisMat facing)
    {
        return getByID((type.getLogFlag() | facing.getFlag()) + (type.isSecondLogID() ? 16 : 0));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
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
        protected Log2(final WoodTypeMat type, final RotateAxisMat rotateAxis)
        {
            super(type, rotateAxis);
        }

        protected Log2(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodTypeMat woodType, final RotateAxisMat rotateAxis, final float hardness, final float blastResistance)
        {
            super(enumName, id, minecraftId, maxStack, typeName, type, woodType, rotateAxis, hardness, blastResistance);
        }

        @Override
        public LogMat getType(final int id)
        {
            return getByID(id);
        }

        @SuppressWarnings("MagicNumber")
        /**
         * Returns one of Log sub-type based on sub-id, may return null
         * @param id sub-type id
         * @return sub-type of Log or null
         */ public static LogMat getByID(final int id)
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
