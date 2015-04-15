package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "Stone" and all its subtypes.
 */
public class Stone extends Stony
{	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 7;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__STONE__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__STONE__HARDNESS;

    public static final Stone STONE                   = new Stone();
    public static final Stone STONE_GRANITE           = new Stone("GRANITE", 0x01);
    public static final Stone STONE_POLISHED_GRANITE  = new Stone("POLISHED_GRANITE", 0x02);
    public static final Stone STONE_DIORITE           = new Stone("DIORITE", 0x03);
    public static final Stone STONE_POLISHED_DIORITE  = new Stone("POLISHED_DIORITE", 0x04);
    public static final Stone STONE_ANDESITE          = new Stone("ANDESITE", 0x05);
    public static final Stone STONE_POLISHED_ANDESITE = new Stone("POLISHED_ANDESITE", 0x06);

    private static final Map<String, Stone>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Stone> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected Stone()
    {
        super("STONE", 1, "minecraft:stone", "STONE", (byte) 0x00);
    }

    public Stone(final String enumName, final int type)
    {
        super(STONE.name(), STONE.getId(), STONE.getMinecraftId(), enumName, (byte) type);
    }

    public Stone(final int maxStack, final String typeName, final byte type)
    {
        super(STONE.name(), STONE.getId(), STONE.getMinecraftId(), maxStack, typeName, type);
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
    public Stone getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Stone getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of Stone sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of Stone or null
     */
    public static Stone getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of Stone sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of Stone or null
     */
    public static Stone getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
    public static void register(final Stone element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Stone.register(STONE);
        Stone.register(STONE_GRANITE);
        Stone.register(STONE_POLISHED_GRANITE);
        Stone.register(STONE_DIORITE);
        Stone.register(STONE_POLISHED_DIORITE);
        Stone.register(STONE_ANDESITE);
        Stone.register(STONE_POLISHED_ANDESITE);
    }
}
