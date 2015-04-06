package org.diorite.material.blocks.stony.ore;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class RedstoneOre extends Ore
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__REDSTONE_ORE__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__REDSTONE_ORE__HARDNESS;

    public static final RedstoneOre REDSTONE_ORE = new RedstoneOre();

    private static final Map<String, RedstoneOre>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<RedstoneOre> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected RedstoneOre()
    {
        super("REDSTONE_ORE", 73, "minecraft:redstone_ore", "REDSTONE_ORE", (byte) 0x00);
    }

    public RedstoneOre(final String enumName, final int type)
    {
        super(REDSTONE_ORE.name(), REDSTONE_ORE.getId(), REDSTONE_ORE.getMinecraftId(), enumName, (byte) type);
    }

    public RedstoneOre(final int maxStack, final String typeName, final byte type)
    {
        super(REDSTONE_ORE.name(), REDSTONE_ORE.getId(), REDSTONE_ORE.getMinecraftId(), maxStack, typeName, type);
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
    public RedstoneOre getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public RedstoneOre getType(final int id)
    {
        return getByID(id);
    }

    public static RedstoneOre getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static RedstoneOre getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final RedstoneOre element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        RedstoneOre.register(REDSTONE_ORE);
    }
}