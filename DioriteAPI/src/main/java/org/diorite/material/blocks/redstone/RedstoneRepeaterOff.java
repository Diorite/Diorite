package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "RedstoneRepeaterOff" and all its subtypes.
 */
public class RedstoneRepeaterOff extends RedstoneRepeater
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__REPEATER_OFF__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
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

    /**
     * Returns one of RedstoneRepeaterOff sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of RedstoneRepeaterOff or null
     */
    public static RedstoneRepeaterOff getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of RedstoneRepeaterOff sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of RedstoneRepeaterOff or null
     */
    public static RedstoneRepeaterOff getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
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
