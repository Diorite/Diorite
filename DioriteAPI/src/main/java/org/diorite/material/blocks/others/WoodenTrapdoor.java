package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class WoodenTrapdoor extends Trapdoor
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__WOODEN_TRAPDOOR__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__WOODEN_TRAPDOOR__HARDNESS;

    public static final WoodenTrapdoor WOODEN_TRAPDOOR = new WoodenTrapdoor();

    private static final Map<String, WoodenTrapdoor>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<WoodenTrapdoor> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected WoodenTrapdoor()
    {
        super("WOODEN_TRAPDOOR", 96, "minecraft:trapdoor", "WOODEN_TRAPDOOR", (byte) 0x00);
    }

    public WoodenTrapdoor(final String enumName, final int type)
    {
        super(WOODEN_TRAPDOOR.name(), WOODEN_TRAPDOOR.getId(), WOODEN_TRAPDOOR.getMinecraftId(), enumName, (byte) type);
    }

    public WoodenTrapdoor(final int maxStack, final String typeName, final byte type)
    {
        super(WOODEN_TRAPDOOR.name(), WOODEN_TRAPDOOR.getId(), WOODEN_TRAPDOOR.getMinecraftId(), maxStack, typeName, type);
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
    public WoodenTrapdoor getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public WoodenTrapdoor getType(final int id)
    {
        return getByID(id);
    }

    public static WoodenTrapdoor getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static WoodenTrapdoor getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final WoodenTrapdoor element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        WoodenTrapdoor.register(WOODEN_TRAPDOOR);
    }
}