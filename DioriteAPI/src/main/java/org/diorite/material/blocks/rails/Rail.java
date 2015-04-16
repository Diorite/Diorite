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
    // TODO: auto-generated class, implement other types (sub-ids).	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;
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

    public static final Rail RAIL = new Rail();

    private static final Map<String, Rail>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Rail> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Rail()
    {
        super("RAIL", 66, "minecraft:rail", "RAIL", RailType.FLAT_NORTH_SOUTH, (byte) 0x00);
    }

//    public Rail(final String enumName, final int type)
//    {
//        super(RAIL.name(), RAIL.getId(), RAIL.getMinecraftId(), enumName, (byte) type);
//    }
//
//    public Rail(final int maxStack, final String typeName, final byte type)
//    {
//        super(RAIL.name(), RAIL.getId(), RAIL.getMinecraftId(), maxStack, typeName, type);
//    }

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
    public Rails getRailType(final RailType railType)
    {
        return null; // TODO: implement
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
        Rail.register(RAIL);
    }
}
