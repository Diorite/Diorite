package org.diorite.material.blocks.wooden.wood.fencegate;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class SpruceFenceGate extends WoodenFenceGate
{
    // TODO: auto-generated class, implement other types (sub-ids).
    public static final byte  USED_DATA_VALUES = 1;
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__SPRUCE_FENCE_GATE__BLAST_RESISTANCE;
    public static final float HARDNESS         = MagicNumbers.MATERIAL__SPRUCE_FENCE_GATE__HARDNESS;

    public static final SpruceFenceGate SPRUCE_FENCE_GATE = new SpruceFenceGate();

    private static final Map<String, SpruceFenceGate>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SpruceFenceGate> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected SpruceFenceGate()
    {
        super("SPRUCE_FENCE_GATE", 183, "minecraft:spruce_fence_gate", "SPRUCE_FENCE_GATE", WoodType.SPRUCE);
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
    public SpruceFenceGate getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SpruceFenceGate getType(final int id)
    {
        return getByID(id);
    }

    public static SpruceFenceGate getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static SpruceFenceGate getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final SpruceFenceGate element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        SpruceFenceGate.register(SPRUCE_FENCE_GATE);
    }
}