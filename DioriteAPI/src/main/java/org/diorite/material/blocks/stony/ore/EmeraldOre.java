package org.diorite.material.blocks.stony.ore;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class EmeraldOre extends Ore
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__EMERALD_ORE__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__EMERALD_ORE__HARDNESS;

    public static final EmeraldOre EMERALD_ORE = new EmeraldOre();

    private static final Map<String, EmeraldOre>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<EmeraldOre> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected EmeraldOre()
    {
        super("EMERALD_ORE", 129, "minecraft:emerald_ore", "EMERALD_ORE", (byte) 0x00);
    }

    public EmeraldOre(final String enumName, final int type)
    {
        super(EMERALD_ORE.name(), EMERALD_ORE.getId(), EMERALD_ORE.getMinecraftId(), enumName, (byte) type);
    }

    public EmeraldOre(final int maxStack, final String typeName, final byte type)
    {
        super(EMERALD_ORE.name(), EMERALD_ORE.getId(), EMERALD_ORE.getMinecraftId(), maxStack, typeName, type);
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
    public EmeraldOre getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public EmeraldOre getType(final int id)
    {
        return getByID(id);
    }

    public static EmeraldOre getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static EmeraldOre getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final EmeraldOre element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        EmeraldOre.register(EMERALD_ORE);
    }
}
