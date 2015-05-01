package org.diorite.material.blocks.rails;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Rail" and all its subtypes.
 */
public class Rail extends Rails
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 10;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__RAIL__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__RAIL__HARDNESS;

    public static final Rail RAIL_FLAT_NORTH_SOUTH  = new Rail();
    public static final Rail RAIL_FLAT_WEST_EAST    = new Rail(RailType.FLAT_WEST_EAST);
    public static final Rail RAIL_ASCENDING_EAST    = new Rail(RailType.ASCENDING_EAST);
    public static final Rail RAIL_ASCENDING_WEST    = new Rail(RailType.ASCENDING_WEST);
    public static final Rail RAIL_ASCENDING_NORTH   = new Rail(RailType.ASCENDING_NORTH);
    public static final Rail RAIL_ASCENDING_SOUTH   = new Rail(RailType.ASCENDING_SOUTH);
    public static final Rail RAIL_CURVED_SOUTH_EAST = new Rail(RailType.CURVED_SOUTH_EAST);
    public static final Rail RAIL_CURVED_SOUTH_WEST = new Rail(RailType.CURVED_SOUTH_WEST);
    public static final Rail RAIL_CURVED_NORTH_WEST = new Rail(RailType.CURVED_NORTH_WEST);
    public static final Rail RAIL_CURVED_NORTH_EAST = new Rail(RailType.CURVED_NORTH_EAST);

    private static final Map<String, Rail>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Rail> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Rail()
    {
        super("RAIL", 66, "minecraft:rail", "FLAT_NORTH_SOUTH", RailType.FLAT_NORTH_SOUTH, (byte) 0x00);
    }

    public Rail(final RailType type)
    {
        super(RAIL_FLAT_NORTH_SOUTH.name(), RAIL_FLAT_NORTH_SOUTH.getId(), RAIL_FLAT_NORTH_SOUTH.getMinecraftId(), type.name(), type, (byte) 0x0);
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
    public Rail getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Rail getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public Rail getRailType(final RailType railType)
    {
        return getByID(railType.getFlag());
    }

    /**
     * Returns one of Rail sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of Rail or null
     */
    public static Rail getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Rail sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of Rail or null
     */
    public static Rail getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of Rail sub-type based on {@link RailType}.
     * It will never return null.
     *
     * @param railType {@link RailType} of Rail.
     *
     * @return sub-type of Rail
     */
    public static Rail getRail(final RailType railType)
    {
        return getByID(railType.getFlag());
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final Rail element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Rail.register(RAIL_FLAT_NORTH_SOUTH);
        Rail.register(RAIL_FLAT_WEST_EAST);
        Rail.register(RAIL_ASCENDING_EAST);
        Rail.register(RAIL_ASCENDING_WEST);
        Rail.register(RAIL_ASCENDING_NORTH);
        Rail.register(RAIL_ASCENDING_SOUTH);
        Rail.register(RAIL_CURVED_SOUTH_EAST);
        Rail.register(RAIL_CURVED_SOUTH_WEST);
        Rail.register(RAIL_CURVED_NORTH_WEST);
        Rail.register(RAIL_CURVED_NORTH_EAST);
    }
}
