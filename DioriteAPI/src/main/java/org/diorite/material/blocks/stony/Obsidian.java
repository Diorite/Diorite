package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Obsidian" and all its subtypes.
 */
public class Obsidian extends Stony
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__OBSIDIAN__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__OBSIDIAN__HARDNESS;

    public static final Obsidian OBSIDIAN = new Obsidian();

    private static final Map<String, Obsidian>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Obsidian> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Obsidian()
    {
        super("OBSIDIAN", 49, "minecraft:obsidian", "OBSIDIAN", (byte) 0x00);
    }

    public Obsidian(final String enumName, final int type)
    {
        super(OBSIDIAN.name(), OBSIDIAN.getId(), OBSIDIAN.getMinecraftId(), enumName, (byte) type);
    }

    public Obsidian(final int maxStack, final String typeName, final byte type)
    {
        super(OBSIDIAN.name(), OBSIDIAN.getId(), OBSIDIAN.getMinecraftId(), maxStack, typeName, type);
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
    public Obsidian getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Obsidian getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of Obsidian sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of Obsidian or null
     */
    public static Obsidian getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Obsidian sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of Obsidian or null
     */
    public static Obsidian getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
    public static void register(final Obsidian element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Obsidian.register(OBSIDIAN);
    }
}
