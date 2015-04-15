package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Cactus" and all its subtypes.
 */
public class Cactus extends Plant
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__CACTUS__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__CACTUS__HARDNESS;

    public static final Cactus CACTUS = new Cactus();

    private static final Map<String, Cactus>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Cactus> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Cactus()
    {
        super("CACTUS", 81, "minecraft:cactus", "CACTUS", (byte) 0x00);
    }

    public Cactus(final String enumName, final int type)
    {
        super(CACTUS.name(), CACTUS.getId(), CACTUS.getMinecraftId(), enumName, (byte) type);
    }

    public Cactus(final int maxStack, final String typeName, final byte type)
    {
        super(CACTUS.name(), CACTUS.getId(), CACTUS.getMinecraftId(), maxStack, typeName, type);
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
    public Cactus getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Cactus getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of Cactus sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of Cactus or null
     */
    public static Cactus getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Cactus sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of Cactus or null
     */
    public static Cactus getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
    public static void register(final Cactus element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Cactus.register(CACTUS);
    }
}
