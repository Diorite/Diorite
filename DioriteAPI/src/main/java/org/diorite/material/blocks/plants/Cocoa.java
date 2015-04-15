package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Cocoa" and all its subtypes.
 */
public class Cocoa extends Plant
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__COCOA__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__COCOA__HARDNESS;

    public static final Cocoa COCOA = new Cocoa();

    private static final Map<String, Cocoa>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Cocoa> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Cocoa()
    {
        super("COCOA", 127, "minecraft:cocoa", "COCOA", (byte) 0x00);
    }

    public Cocoa(final String enumName, final int type)
    {
        super(COCOA.name(), COCOA.getId(), COCOA.getMinecraftId(), enumName, (byte) type);
    }

    public Cocoa(final int maxStack, final String typeName, final byte type)
    {
        super(COCOA.name(), COCOA.getId(), COCOA.getMinecraftId(), maxStack, typeName, type);
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
    public Cocoa getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Cocoa getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of Cocoa sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of Cocoa or null
     */
    public static Cocoa getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Cocoa sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of Cocoa or null
     */
    public static Cocoa getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
    public static void register(final Cocoa element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Cocoa.register(COCOA);
    }
}
