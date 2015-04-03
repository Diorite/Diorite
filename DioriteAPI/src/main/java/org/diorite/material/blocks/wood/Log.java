package org.diorite.material.blocks.wood;

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Log extends Wood
{
    public static final byte USED_DATA_VALUES = 24;

    public static final Log LOG_OAK      = new Log();
    public static final Log LOG_SPRUCE   = new Log("SPRUCE", WoodType.SPRUCE, LogFacing.UP_DOWN);
    public static final Log LOG_BIRCH    = new Log("BIRCH", WoodType.BIRCH, LogFacing.UP_DOWN);
    public static final Log LOG_JUNGLE   = new Log("JUNGLE", WoodType.JUNGLE, LogFacing.UP_DOWN);
    public static final Log LOG_ACACIA   = new Log("ACACIA", WoodType.ACACIA, LogFacing.UP_DOWN);
    public static final Log LOG_DARK_OAK = new Log("DARK_OAK", WoodType.DARK_OAK, LogFacing.UP_DOWN);

    public static final Log LOG_OAK_EAST_WEST      = new Log("OAK_EAST_WEST", WoodType.OAK, LogFacing.EAST_WEST);
    public static final Log LOG_SPRUCE_EAST_WEST   = new Log("SPRUCE_EAST_WEST", WoodType.SPRUCE, LogFacing.EAST_WEST);
    public static final Log LOG_BIRCH_EAST_WEST    = new Log("BIRCH_EAST_WEST", WoodType.BIRCH, LogFacing.EAST_WEST);
    public static final Log LOG_JUNGLE_EAST_WEST   = new Log("JUNGLE_EAST_WEST", WoodType.JUNGLE, LogFacing.EAST_WEST);
    public static final Log LOG_ACACIA_EAST_WEST   = new Log("ACACIA_EAST_WEST", WoodType.ACACIA, LogFacing.EAST_WEST);
    public static final Log LOG_DARK_OAK_EAST_WEST = new Log("DARK_OAK_EAST_WEST", WoodType.DARK_OAK, LogFacing.EAST_WEST);

    public static final Log LOG_OAK_NORTH_SOUTH      = new Log("OAK_NORTH_SOUTH", WoodType.OAK, LogFacing.NORTH_SOUTH);
    public static final Log LOG_SPRUCE_NORTH_SOUTH   = new Log("SPRUCE_NORTH_SOUTH", WoodType.SPRUCE, LogFacing.NORTH_SOUTH);
    public static final Log LOG_BIRCH_NORTH_SOUTH    = new Log("BIRCH_NORTH_SOUTH", WoodType.BIRCH, LogFacing.NORTH_SOUTH);
    public static final Log LOG_JUNGLE_NORTH_SOUTH   = new Log("JUNGLE_NORTH_SOUTH", WoodType.JUNGLE, LogFacing.NORTH_SOUTH);
    public static final Log LOG_ACACIA_NORTH_SOUTH   = new Log("ACACIA_NORTH_SOUTH", WoodType.ACACIA, LogFacing.NORTH_SOUTH);
    public static final Log LOG_DARK_OAK_NORTH_SOUTH = new Log("DARK_OAK_NORTH_SOUTH", WoodType.DARK_OAK, LogFacing.NORTH_SOUTH);

    public static final Log LOG_OAK_BARK      = new Log("OAK_BARK", WoodType.OAK, LogFacing.NONE);
    public static final Log LOG_SPRUCE_BARK   = new Log("SPRUCE_BARK", WoodType.SPRUCE, LogFacing.NONE);
    public static final Log LOG_BIRCH_BARK    = new Log("BIRCH_BARK", WoodType.BIRCH, LogFacing.NONE);
    public static final Log LOG_JUNGLE_BARK   = new Log("JUNGLE_BARK", WoodType.JUNGLE, LogFacing.NONE);
    public static final Log LOG_ACACIA_BARK   = new Log("ACACIA_BARK", WoodType.ACACIA, LogFacing.NONE);
    public static final Log LOG_DARK_OAK_BARK = new Log("DARK_OAK_BARK", WoodType.DARK_OAK, LogFacing.NONE);

    private static final Map<String, Log>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SLOW_GROW);
    private static final TByteObjectMap<Log> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SLOW_GROW);

    private final LogFacing facing;

    @SuppressWarnings("MagicNumber")
    protected Log()
    {
        super("LOG", 17, "minecraft:log", "QAK", (byte) 0x00, WoodType.OAK);
        this.facing = LogFacing.UP_DOWN;
    }

    @SuppressWarnings("MagicNumber")
    public Log(final String enumName, final WoodType type, final LogFacing facing)
    {
        super(LOG_OAK.name(), type.isSecondLogID() ? 162 : 17, LOG_OAK.getMinecraftId() + (type.isSecondLogID() ? "2" : ""), enumName, (byte) (type.getLogFlag() | facing.getFlag()), type);
        this.facing = facing;
    }

    @SuppressWarnings("MagicNumber")
    public Log(final int maxStack, final String enumName, final WoodType type, final LogFacing facing)
    {
        super(LOG_OAK.name(), type.isSecondLogID() ? 162 : 17, LOG_OAK.getMinecraftId() + (type.isSecondLogID() ? "2" : ""), maxStack, enumName, (byte) (type.getLogFlag() | facing.getFlag()), type);
        this.facing = facing;
    }

    public LogFacing getFacing()
    {
        return this.facing;
    }

    @Override
    public Log getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Log getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isFlammable()
    {
        return true;
    }

    @Override
    public boolean isBurnable()
    {
        return true;
    }

    @Override
    public float getBlastResistance()
    {
        return MagicNumbers.MATERIAL__LOG__BLAST_RESISTANCE;
    }

    @Override
    public float getHardness()
    {
        return MagicNumbers.MATERIAL__LOG__HARDNESS;
    }

    @Override
    public Log getWoodType(final WoodType woodType)
    {
        return getLog(woodType, this.facing);
    }

    public Log getFacing(final LogFacing facing)
    {
        return getLog(this.woodType, facing);
    }

    public Log getType(final WoodType woodType, final LogFacing facing)
    {
        return getLog(woodType, facing);
    }

    public static Log getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Log getByEnumName(final String name)
    {
        return byName.get(name);
    }

    @SuppressWarnings("MagicNumber")
    public static Log getLog(final WoodType type, final LogFacing facing)
    {
        return getByID((type.getLogFlag() | facing.getFlag()) + (type.isSecondLogID() ? 16 : 0));
    }

    @SuppressWarnings("MagicNumber")
    protected byte getFixedDataValue()
    {
        return (byte) (this.getType() + ((this.getWoodType().isSecondLogID()) ? 16 : 0));
    }

    public static void register(final Log element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Log.register(LOG_OAK);
        Log.register(LOG_SPRUCE);
        Log.register(LOG_BIRCH);
        Log.register(LOG_JUNGLE);
        Log.register(LOG_ACACIA);
        Log.register(LOG_DARK_OAK);
        Log.register(LOG_OAK_EAST_WEST);
        Log.register(LOG_SPRUCE_EAST_WEST);
        Log.register(LOG_BIRCH_EAST_WEST);
        Log.register(LOG_JUNGLE_EAST_WEST);
        Log.register(LOG_ACACIA_EAST_WEST);
        Log.register(LOG_DARK_OAK_EAST_WEST);
        Log.register(LOG_OAK_NORTH_SOUTH);
        Log.register(LOG_SPRUCE_NORTH_SOUTH);
        Log.register(LOG_BIRCH_NORTH_SOUTH);
        Log.register(LOG_JUNGLE_NORTH_SOUTH);
        Log.register(LOG_ACACIA_NORTH_SOUTH);
        Log.register(LOG_DARK_OAK_NORTH_SOUTH);
        Log.register(LOG_OAK_BARK);
        Log.register(LOG_SPRUCE_BARK);
        Log.register(LOG_BIRCH_BARK);
        Log.register(LOG_JUNGLE_BARK);
        Log.register(LOG_ACACIA_BARK);
        Log.register(LOG_DARK_OAK_BARK);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("facing", this.facing).toString();
    }
}
