package org.diorite.material.blocks.wooden.wood.fence;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class AcaciaFence extends WoodenFence
{
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__ACACIA_FENCE__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__ACACIA_FENCE__HARDNESS;

    public static final AcaciaFence ACACIA_FENCE = new AcaciaFence();

    private static final Map<String, AcaciaFence>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<AcaciaFence> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected AcaciaFence()
    {
        super("ACACIA_FENCE", 192, "minecraft:acacia_fence", "ACACIA_FENCE", WoodType.ACACIA);
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
    public AcaciaFence getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public AcaciaFence getType(final int id)
    {
        return getByID(id);
    }

    public static AcaciaFence getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static AcaciaFence getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final AcaciaFence element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        AcaciaFence.register(ACACIA_FENCE);
    }
}