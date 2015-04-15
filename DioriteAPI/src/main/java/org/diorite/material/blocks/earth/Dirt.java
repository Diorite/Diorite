package org.diorite.material.blocks.earth;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Dirt" and all its subtypes.
 */
public class Dirt extends Earth
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 3;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__DIRT__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__DIRT__HARDNESS;

    public static final Dirt DIRT             = new Dirt();
    public static final Dirt DIRT_COARSE_DIRT = new Dirt("COARSE_DIRT", 0x01);
    public static final Dirt DIRT_PODZOL      = new Dirt("PODZOL", 0x02);

    private static final Map<String, Dirt>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Dirt> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected Dirt()
    {
        super("DIRT", 3, "minecraft:dirt", "DIRT", (byte) 0x00);
    }

    public Dirt(final String enumName, final int type)
    {
        super(DIRT.name(), DIRT.getId(), DIRT.getMinecraftId(), enumName, (byte) type);
    }

    public Dirt(final int maxStack, final String typeName, final byte type)
    {
        super(DIRT.name(), DIRT.getId(), DIRT.getMinecraftId(), maxStack, typeName, type);
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
    public Dirt getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Dirt getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of Dirt sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of Dirt or null
     */
    public static Dirt getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Dirt sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of Dirt or null
     */
    public static Dirt getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
    public static void register(final Dirt element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Dirt.register(DIRT);
        Dirt.register(DIRT_COARSE_DIRT);
        Dirt.register(DIRT_PODZOL);
    }
}
