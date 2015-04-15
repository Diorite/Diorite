package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "RedstoneLampOn" and all its subtypes.
 */
public class RedstoneLampOn extends RedstoneLamp
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__REDSTONE_LAMP_ON__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
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

    /**
     * Returns one of RedstoneLampOn sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of RedstoneLampOn or null
     */
    public static RedstoneLampOn getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of RedstoneLampOn sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of RedstoneLampOn or null
     */
    public static RedstoneLampOn getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
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
