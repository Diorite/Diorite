package org.diorite.material.blocks.stony.ore;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class QuartzOre extends Ore
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__QUARTZ_ORE__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__QUARTZ_ORE__HARDNESS;

    public static final QuartzOre QUARTZ_ORE = new QuartzOre();

    private static final Map<String, QuartzOre>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<QuartzOre> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected QuartzOre()
    {
        super("QUARTZ_ORE", 153, "minecraft:quartz_ore", "QUARTZ_ORE", (byte) 0x00);
    }

    public QuartzOre(final String enumName, final int type)
    {
        super(QUARTZ_ORE.name(), QUARTZ_ORE.getId(), QUARTZ_ORE.getMinecraftId(), enumName, (byte) type);
    }

    public QuartzOre(final int maxStack, final String typeName, final byte type)
    {
        super(QUARTZ_ORE.name(), QUARTZ_ORE.getId(), QUARTZ_ORE.getMinecraftId(), maxStack, typeName, type);
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
    public QuartzOre getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public QuartzOre getType(final int id)
    {
        return getByID(id);
    }

    public static QuartzOre getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static QuartzOre getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final QuartzOre element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        QuartzOre.register(QUARTZ_ORE);
    }
}