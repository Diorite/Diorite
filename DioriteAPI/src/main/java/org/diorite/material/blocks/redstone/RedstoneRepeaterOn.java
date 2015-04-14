package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "RedstoneRepeaterOn" and all its subtypes.
 */
public class RedstoneRepeaterOn extends RedstoneRepeater
{
    // TODO: auto-generated class, implement other types (sub-ids).	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__REDSTONE_REPEATER_ON__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__REDSTONE_REPEATER_ON__HARDNESS;

    public static final RedstoneRepeaterOn REDSTONE_REPEATER_ON = new RedstoneRepeaterOn();

    private static final Map<String, RedstoneRepeaterOn>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneRepeaterOn> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected RedstoneRepeaterOn()
    {
        super("REDSTONE_REPEATER_ON", 94, "minecraft:powered_repeater", "REDSTONE_REPEATER_ON", (byte) 0x00);
    }

    public RedstoneRepeaterOn(final String enumName, final int type)
    {
        super(REDSTONE_REPEATER_ON.name(), REDSTONE_REPEATER_ON.getId(), REDSTONE_REPEATER_ON.getMinecraftId(), enumName, (byte) type);
    }

    public RedstoneRepeaterOn(final int maxStack, final String typeName, final byte type)
    {
        super(REDSTONE_REPEATER_ON.name(), REDSTONE_REPEATER_ON.getId(), REDSTONE_REPEATER_ON.getMinecraftId(), maxStack, typeName, type);
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
    public RedstoneRepeaterOn getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneRepeaterOn getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isActivated()
    {
        return true;
    }

    public static RedstoneRepeaterOn getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static RedstoneRepeaterOn getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final RedstoneRepeaterOn element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        RedstoneRepeaterOn.register(REDSTONE_REPEATER_ON);
    }
}
