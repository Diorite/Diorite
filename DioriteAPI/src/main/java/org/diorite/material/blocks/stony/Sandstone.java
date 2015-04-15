package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Sandstone" and all its subtypes.
 */
public class Sandstone extends Stony
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 3;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__SAND_STONE__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__SAND_STONE__HARDNESS;

    public static final Sandstone SANDSTONE          = new Sandstone();
    public static final Sandstone SANDSTONE_CHISELED = new Sandstone("CHISELED", 0x01);
    public static final Sandstone SANDSTONE_SMOOTH   = new Sandstone("SMOOTH", 0x02);

    private static final Map<String, Sandstone>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Sandstone> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Sandstone()
    {
        super("SANDSTONE", 24, "minecraft:sandstone", "SANDSTONE", (byte) 0x00);
    }

    public Sandstone(final String enumName, final int type)
    {
        super(SANDSTONE.name(), SANDSTONE.getId(), SANDSTONE.getMinecraftId(), enumName, (byte) type);
    }

    public Sandstone(final int maxStack, final String typeName, final byte type)
    {
        super(SANDSTONE.name(), SANDSTONE.getId(), SANDSTONE.getMinecraftId(), maxStack, typeName, type);
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
    public Sandstone getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Sandstone getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of Sandstone sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of Sandstone or null
     */
    public static Sandstone getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Sandstone sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of Sandstone or null
     */
    public static Sandstone getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
    public static void register(final Sandstone element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Sandstone.register(SANDSTONE);
        Sandstone.register(SANDSTONE_CHISELED);
        Sandstone.register(SANDSTONE_SMOOTH);
    }
}
