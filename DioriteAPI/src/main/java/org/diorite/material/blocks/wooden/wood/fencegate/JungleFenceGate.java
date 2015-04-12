package org.diorite.material.blocks.wooden.wood.fencegate;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class JungleFenceGate extends WoodenFenceGate
{
    // TODO: auto-generated class, implement other types (sub-ids).	
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;	
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__JUNGLE_FENCE_GATE__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__JUNGLE_FENCE_GATE__HARDNESS;

    public static final JungleFenceGate JUNGLE_FENCE_GATE = new JungleFenceGate();

    private static final Map<String, JungleFenceGate>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<JungleFenceGate> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected JungleFenceGate()
    {
        super("JUNGLE_FENCE_GATE", 185, "minecraft:jungle_fence_gate", "JUNGLE_FENCE_GATE", WoodType.JUNGLE);
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
    public JungleFenceGate getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public JungleFenceGate getType(final int id)
    {
        return getByID(id);
    }

    public static JungleFenceGate getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static JungleFenceGate getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final JungleFenceGate element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        JungleFenceGate.register(JUNGLE_FENCE_GATE);
    }
}
