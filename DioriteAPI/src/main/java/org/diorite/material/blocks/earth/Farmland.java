package org.diorite.material.blocks.earth;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Farmland extends Earth
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__FARMLAND__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__FARMLAND__HARDNESS;

    public static final Farmland FARMLAND = new Farmland();

    private static final Map<String, Farmland>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Farmland> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Farmland()
    {
        super("FARMLAND", 60, "minecraft:farmland", "FARMLAND", (byte) 0x00);
    }

    public Farmland(final String enumName, final int type)
    {
        super(FARMLAND.name(), FARMLAND.getId(), FARMLAND.getMinecraftId(), enumName, (byte) type);
    }

    public Farmland(final int maxStack, final String typeName, final byte type)
    {
        super(FARMLAND.name(), FARMLAND.getId(), FARMLAND.getMinecraftId(), maxStack, typeName, type);
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
    public Farmland getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Farmland getType(final int id)
    {
        return getByID(id);
    }

    public static Farmland getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Farmland getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Farmland element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Farmland.register(FARMLAND);
    }
}