package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Dandelion" and all its subtypes.
 */
public class Dandelion extends Plant
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__DANDELION__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__DANDELION__HARDNESS;

    public static final Dandelion DANDELION = new Dandelion();

    private static final Map<String, Dandelion>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Dandelion> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Dandelion()
    {
        super("DANDELION", 37, "minecraft:yellow_flower", "DANDELION", (byte) 0x00);
    }

    public Dandelion(final String enumName, final int type)
    {
        super(DANDELION.name(), DANDELION.getId(), DANDELION.getMinecraftId(), enumName, (byte) type);
    }

    public Dandelion(final int maxStack, final String typeName, final byte type)
    {
        super(DANDELION.name(), DANDELION.getId(), DANDELION.getMinecraftId(), maxStack, typeName, type);
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
    public Dandelion getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Dandelion getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of Dandelion sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of Dandelion or null
     */
    public static Dandelion getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Dandelion sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of Dandelion or null
     */
    public static Dandelion getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
    public static void register(final Dandelion element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Dandelion.register(DANDELION);
    }
}
