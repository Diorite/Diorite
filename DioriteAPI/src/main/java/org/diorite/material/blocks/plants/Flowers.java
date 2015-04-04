package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class Flowers extends Plant
{
    public static final byte  USED_DATA_VALUES = 9;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__FLOWERS__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__FLOWERS__HARDNESS;

    public static final Flowers FLOWERS_POPPY        = new Flowers();
    public static final Flowers FLOWERS_BLUE_ORCHID  = new Flowers("BLUE_ORCHID", 0x01);
    public static final Flowers FLOWERS_ALLIUM       = new Flowers("ALLIUM", 0x02);
    public static final Flowers FLOWERS_AZURE_BLUET  = new Flowers("AZURE_BLUET", 0x03);
    public static final Flowers FLOWERS_RED_TULIP    = new Flowers("RED_TULIP", 0x04);
    public static final Flowers FLOWERS_ORANGE_TULIP = new Flowers("ORANGE_TULIP", 0x05);
    public static final Flowers FLOWERS_WHITE_TULIP  = new Flowers("WHITE_TULIP", 0x06);
    public static final Flowers FLOWERS_BPINK_TULIP  = new Flowers("PINK_TULIP", 0x07);
    public static final Flowers FLOWERS_OXEYE_DAISY  = new Flowers("OXEYE_DAISY", 0x08);

    private static final Map<String, Flowers>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<Flowers> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected Flowers()
    {
        super("FLOWERS", 38, "minecraft:red_flower", "POPPY", (byte) 0x00);
    }

    public Flowers(final String enumName, final int type)
    {
        super(FLOWERS_POPPY.name(), FLOWERS_POPPY.getId(), FLOWERS_POPPY.getMinecraftId(), enumName, (byte) type);
    }

    public Flowers(final int maxStack, final String typeName, final byte type)
    {
        super(FLOWERS_POPPY.name(), FLOWERS_POPPY.getId(), FLOWERS_POPPY.getMinecraftId(), maxStack, typeName, type);
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
    public Flowers getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public Flowers getType(final int id)
    {
        return getByID(id);
    }

    public static Flowers getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static Flowers getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final Flowers element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        Flowers.register(FLOWERS_POPPY);
        Flowers.register(FLOWERS_BLUE_ORCHID);
        Flowers.register(FLOWERS_ALLIUM);
        Flowers.register(FLOWERS_AZURE_BLUET);
        Flowers.register(FLOWERS_RED_TULIP);
        Flowers.register(FLOWERS_ORANGE_TULIP);
        Flowers.register(FLOWERS_WHITE_TULIP);
        Flowers.register(FLOWERS_BPINK_TULIP);
        Flowers.register(FLOWERS_OXEYE_DAISY);
    }
}
