package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class RedstoneLampOn extends RedstoneLamp
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__REDSTONE_LAMP_ON__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__REDSTONE_LAMP_ON__HARDNESS;

    public static final RedstoneLampOn REDSTONE_LAMP_ON = new RedstoneLampOn();

    private static final Map<String, RedstoneLampOn>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneLampOn> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected RedstoneLampOn()
    {
        super("REDSTONE_LAMP_ON", 124, "minecraft:lit_redstone_lamp", "REDSTONE_LAMP_ON", (byte) 0x00);
    }

    public RedstoneLampOn(final String enumName, final int type)
    {
        super(REDSTONE_LAMP_ON.name(), REDSTONE_LAMP_ON.getId(), REDSTONE_LAMP_ON.getMinecraftId(), enumName, (byte) type);
    }

    public RedstoneLampOn(final int maxStack, final String typeName, final byte type)
    {
        super(REDSTONE_LAMP_ON.name(), REDSTONE_LAMP_ON.getId(), REDSTONE_LAMP_ON.getMinecraftId(), maxStack, typeName, type);
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
    public RedstoneLampOn getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneLampOn getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isActivated()
    {
        return true;
    }

    public static RedstoneLampOn getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static RedstoneLampOn getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final RedstoneLampOn element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        RedstoneLampOn.register(REDSTONE_LAMP_ON);
    }
}