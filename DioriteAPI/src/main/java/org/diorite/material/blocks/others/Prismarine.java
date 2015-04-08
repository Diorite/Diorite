package org.diorite.material.blocks.others;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Prismarine extends BlockMaterialData
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__PRISMARINE__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__PRISMARINE__HARDNESS;

    public static final Prismarine PRISMARINE = new Prismarine();

    private static final Map<String, Prismarine>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Prismarine> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Prismarine()
    {
        super("PRISMARINE", 168, "minecraft:prismarine", "PRISMARINE", (byte) 0x00);
    }

    public Prismarine(final String enumName, final int type)
    {
        super(PRISMARINE.name(), PRISMARINE.getId(), PRISMARINE.getMinecraftId(), enumName, (byte) type);
    }

    public Prismarine(final int maxStack, final String typeName, final byte type)
    {
        super(PRISMARINE.name(), PRISMARINE.getId(), PRISMARINE.getMinecraftId(), maxStack, typeName, type);
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
    public Prismarine getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Prismarine getType(final int id)
    {
        return getByID(id);
    }

    public static Prismarine getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Prismarine getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Prismarine element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Prismarine.register(PRISMARINE);
    }
}