package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Cobblestone extends Stony
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__COBBLESTONE__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__COBBLESTONE__HARDNESS;

    public static final Cobblestone COBBLESTONE = new Cobblestone();

    private static final Map<String, Cobblestone>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Cobblestone> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    protected Cobblestone()
    {
        super("COBBLESTONE", 4, "minecraft:cobblestone", "COBBLESTONE", (byte) 0x00);
    }

    public Cobblestone(final String enumName, final int type)
    {
        super(COBBLESTONE.name(), COBBLESTONE.getId(), COBBLESTONE.getMinecraftId(), enumName, (byte) type);
    }

    public Cobblestone(final int maxStack, final String typeName, final byte type)
    {
        super(COBBLESTONE.name(), COBBLESTONE.getId(), COBBLESTONE.getMinecraftId(), maxStack, typeName, type);
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
    public Cobblestone getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Cobblestone getType(final int id)
    {
        return getByID(id);
    }

    public static Cobblestone getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Cobblestone getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Cobblestone element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Cobblestone.register(COBBLESTONE);
    }
}
