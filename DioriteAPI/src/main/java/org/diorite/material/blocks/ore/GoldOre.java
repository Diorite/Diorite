package org.diorite.material.blocks.ore;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class GoldOre extends Ore
{
    public static final byte USED_DATA_VALUES = 1;

    public static final GoldOre GOLD_ORE = new GoldOre();

    private static final Map<String, GoldOre>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SLOW_GROW);
    private static final TByteObjectMap<GoldOre> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SLOW_GROW);

    @SuppressWarnings("MagicNumber")
    protected GoldOre()
    {
        super("GOLD_ORE", 14, "minecraft:gold_ore", "GOLD_ORE", (byte) 0x00);
    }

    public GoldOre(final String enumName, final int type)
    {
        super(GOLD_ORE.name(), GOLD_ORE.getId(), GOLD_ORE.getMinecraftId(), enumName, (byte) type);
    }

    public GoldOre(final int maxStack, final String typeName, final byte type)
    {
        super(GOLD_ORE.name(), GOLD_ORE.getId(), GOLD_ORE.getMinecraftId(), maxStack, typeName, type);
    }

    @Override
    public float getBlastResistance()
    {
        return MagicNumbers.MATERIAL__GOLD_ORE__BLAST_RESISTANCE;
    }

    @Override
    public float getHardness()
    {
        return MagicNumbers.MATERIAL__GOLD_ORE__HARDNESS;
    }

    @Override
    public GoldOre getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public GoldOre getType(final int id)
    {
        return getByID(id);
    }

    public static GoldOre getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static GoldOre getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final GoldOre element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        GoldOre.register(GOLD_ORE);
    }
}
