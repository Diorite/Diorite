package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Vine extends Plant
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__VINE__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__VINE__HARDNESS;

    public static final Vine VINE = new Vine();

    private static final Map<String, Vine>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Vine> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Vine()
    {
        super("VINE", 106, "minecraft:vine", "VINE", (byte) 0x00);
    }

    public Vine(final String enumName, final int type)
    {
        super(VINE.name(), VINE.getId(), VINE.getMinecraftId(), enumName, (byte) type);
    }

    public Vine(final int maxStack, final String typeName, final byte type)
    {
        super(VINE.name(), VINE.getId(), VINE.getMinecraftId(), maxStack, typeName, type);
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
    public Vine getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Vine getType(final int id)
    {
        return getByID(id);
    }

    public static Vine getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Vine getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Vine element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Vine.register(VINE);
    }
}