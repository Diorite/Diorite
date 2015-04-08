package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class RedstoneRepeaterOff extends RedstoneRepeater
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__REPEATER_OFF__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__REPEATER_OFF__HARDNESS;

    public static final RedstoneRepeaterOff REDSTONE_REPEATER_OFF = new RedstoneRepeaterOff();

    private static final Map<String, RedstoneRepeaterOff>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneRepeaterOff> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected RedstoneRepeaterOff()
    {
        super("REDSTONE_REPEATER_OFF", 93, "minecraft:unpowered_repeater", "REDSTONE_REPEATER_OFF", (byte) 0x00);
    }

    public RedstoneRepeaterOff(final String enumName, final int type)
    {
        super(REDSTONE_REPEATER_OFF.name(), REDSTONE_REPEATER_OFF.getId(), REDSTONE_REPEATER_OFF.getMinecraftId(), enumName, (byte) type);
    }

    public RedstoneRepeaterOff(final int maxStack, final String typeName, final byte type)
    {
        super(REDSTONE_REPEATER_OFF.name(), REDSTONE_REPEATER_OFF.getId(), REDSTONE_REPEATER_OFF.getMinecraftId(), maxStack, typeName, type);
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
    public RedstoneRepeaterOff getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneRepeaterOff getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isActivated()
    {
        return false;
    }

    public static RedstoneRepeaterOff getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static RedstoneRepeaterOff getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final RedstoneRepeaterOff element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        RedstoneRepeaterOff.register(REDSTONE_REPEATER_OFF);
    }
}