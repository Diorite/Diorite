package org.diorite.material.blocks.earth;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Dirt extends Earth
{
    public static final byte  USED_DATA_VALUES = 3;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__DIRT__BLAST_RESISTANCE;
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

    public static Dirt getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Dirt getByEnumName(final String name)
    {
        return byName.get(name);
    }

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
