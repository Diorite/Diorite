package org.diorite.material.blocks.wooden.wood.fencegate;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

public class OakFenceGate extends WoodenFenceGate
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__OAK_FENCE_GATE__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__OAK_FENCE_GATE__HARDNESS;

    public static final OakFenceGate OAK_FENCE_GATE = new OakFenceGate();

    private static final Map<String, OakFenceGate>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<OakFenceGate> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected OakFenceGate()
    {
        super("OAK_FENCE_GATE", 107, "minecraft:fence_gate", "OAK_FENCE_GATE", WoodType.OAK);
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
    public OakFenceGate getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public OakFenceGate getType(final int id)
    {
        return getByID(id);
    }

    public static OakFenceGate getByID(final int id)
    {
        return byID.get((byte) id);
    }

    public static OakFenceGate getByEnumName(final String name)
    {
        return byName.get(name);
    }

    public static void register(final OakFenceGate element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        OakFenceGate.register(OAK_FENCE_GATE);
    }
}
