package org.diorite.material.blocks.stony.ore;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class IronOre extends Ore
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__IRON_ORE__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__IRON_ORE__HARDNESS;

    public static final IronOre IRON_ORE = new IronOre();

    private static final Map<String, IronOre>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SLOW_GROW);
    private static final TByteObjectMap<IronOre> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SLOW_GROW);

    @SuppressWarnings("MagicNumber")
    protected IronOre()
    {
        super("IRON_ORE", 15, "minecraft:iron_ore", "IRON_ORE", (byte) 0x00);
    }

    public IronOre(final String enumName, final int type)
    {
        super(IRON_ORE.name(), IRON_ORE.getId(), IRON_ORE.getMinecraftId(), enumName, (byte) type);
    }

    public IronOre(final int maxStack, final String typeName, final byte type)
    {
        super(IRON_ORE.name(), IRON_ORE.getId(), IRON_ORE.getMinecraftId(), maxStack, typeName, type);
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
    public IronOre getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public IronOre getType(final int id)
    {
        return getByID(id);
    }

    public static IronOre getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static IronOre getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final IronOre element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        IronOre.register(IRON_ORE);
    }
}
