package org.diorite.material.blocks.earth;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Mycelium extends Earth
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__MYCELIUM__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__MYCELIUM__HARDNESS;

    public static final Mycelium MYCELIUM = new Mycelium();

    private static final Map<String, Mycelium>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Mycelium> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Mycelium()
    {
        super("MYCELIUM", 110, "minecraft:mycelium", "MYCELIUM", (byte) 0x00);
    }

    public Mycelium(final String enumName, final int type)
    {
        super(MYCELIUM.name(), MYCELIUM.getId(), MYCELIUM.getMinecraftId(), enumName, (byte) type);
    }

    public Mycelium(final int maxStack, final String typeName, final byte type)
    {
        super(MYCELIUM.name(), MYCELIUM.getId(), MYCELIUM.getMinecraftId(), maxStack, typeName, type);
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
    public Mycelium getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Mycelium getType(final int id)
    {
        return getByID(id);
    }

    public static Mycelium getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Mycelium getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Mycelium element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Mycelium.register(MYCELIUM);
    }
}