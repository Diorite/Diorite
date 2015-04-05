package org.diorite.material.blocks.wooden;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Bookshelf extends BlockMaterialData
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__BOOKSHELF__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__BOOKSHELF__HARDNESS;

    public static final Bookshelf BOOKSHELF = new Bookshelf();

    private static final Map<String, Bookshelf>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Bookshelf> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Bookshelf()
    {
        super("BOOKSHELF", 47, "minecraft:bookshelf", "BOOKSHELF", (byte) 0x00);
    }

    public Bookshelf(final String enumName, final int type)
    {
        super(BOOKSHELF.name(), BOOKSHELF.getId(), BOOKSHELF.getMinecraftId(), enumName, (byte) type);
    }

    public Bookshelf(final int maxStack, final String typeName, final byte type)
    {
        super(BOOKSHELF.name(), BOOKSHELF.getId(), BOOKSHELF.getMinecraftId(), maxStack, typeName, type);
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
    public Bookshelf getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Bookshelf getType(final int id)
    {
        return getByID(id);
    }

    public static Bookshelf getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Bookshelf getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Bookshelf element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Bookshelf.register(BOOKSHELF);
    }
}
