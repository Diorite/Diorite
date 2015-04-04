package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Stone extends Stony
{
    public static final byte  USED_DATA_VALUES = 7;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__STONE__BLAST_RESISTANCE;
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

    public static Stone getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Stone getByEnumName(final String name)
    {
        return byName.get(name);
    }

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
