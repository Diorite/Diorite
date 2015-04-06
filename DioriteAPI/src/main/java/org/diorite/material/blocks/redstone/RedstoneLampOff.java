package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class RedstoneLampOff extends RedstoneLamp
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__REDSTONE_LAMP_OFF__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__REDSTONE_LAMP_OFF__HARDNESS;

    public static final RedstoneLampOff REDSTONE_LAMP_OFF = new RedstoneLampOff();

    private static final Map<String, RedstoneLampOff>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneLampOff> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected RedstoneLampOff()
    {
        super("REDSTONE_LAMP_OFF", 123, "minecraft:redstone_lamp", "REDSTONE_LAMP_OFF", (byte) 0x00);
    }

    public RedstoneLampOff(final String enumName, final int type)
    {
        super(REDSTONE_LAMP_OFF.name(), REDSTONE_LAMP_OFF.getId(), REDSTONE_LAMP_OFF.getMinecraftId(), enumName, (byte) type);
    }

    public RedstoneLampOff(final int maxStack, final String typeName, final byte type)
    {
        super(REDSTONE_LAMP_OFF.name(), REDSTONE_LAMP_OFF.getId(), REDSTONE_LAMP_OFF.getMinecraftId(), maxStack, typeName, type);
    }

    @Override
    public float getBlastResistance()
    {
        return BLAST_RESISTANCE;
    }

    @Override
    public float getHardness()
    {
        return HARDNESS;
    }

    @Override
    public RedstoneLampOff getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneLampOff getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isActivated()
    {
        return false;
    }

    public static RedstoneLampOff getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static RedstoneLampOff getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final RedstoneLampOff element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        RedstoneLampOff.register(REDSTONE_LAMP_OFF);
    }
}