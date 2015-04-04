package org.diorite.material.blocks.stony.ore;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class CoalOre extends Ore
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__COAL_ORE__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__COAL_ORE__HARDNESS;

    public static final CoalOre COAL_ORE = new CoalOre();

    private static final Map<String, CoalOre>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<CoalOre> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected CoalOre()
    {
        super("COAL_ORE", 16, "minecraft:coal_ore", "COAL_ORE", (byte) 0x00);
    }

    public CoalOre(final String enumName, final int type)
    {
        super(COAL_ORE.name(), COAL_ORE.getId(), COAL_ORE.getMinecraftId(), enumName, (byte) type);
    }

    public CoalOre(final int maxStack, final String typeName, final byte type)
    {
        super(COAL_ORE.name(), COAL_ORE.getId(), COAL_ORE.getMinecraftId(), maxStack, typeName, type);
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
    public CoalOre getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public CoalOre getType(final int id)
    {
        return getByID(id);
    }

    public static CoalOre getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static CoalOre getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final CoalOre element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        CoalOre.register(COAL_ORE);
    }
}
