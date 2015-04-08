package org.diorite.material.blocks.wooden.wood.fencegate;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class BirchFenceGate extends WoodenFenceGate
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__BIRCH_FENCE_GATE__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__BIRCH_FENCE_GATE__HARDNESS;

    public static final BirchFenceGate BIRCH_FENCE_GATE = new BirchFenceGate();

    private static final Map<String, BirchFenceGate>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<BirchFenceGate> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected BirchFenceGate()
    {
        super("BIRCH_FENCE_GATE", 184, "minecraft:brich_fence_gate", "BIRCH_FENCE_GATE", WoodType.BIRCH);
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
    public BirchFenceGate getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public BirchFenceGate getType(final int id)
    {
        return getByID(id);
    }

    public static BirchFenceGate getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static BirchFenceGate getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final BirchFenceGate element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        BirchFenceGate.register(BIRCH_FENCE_GATE);
    }
}