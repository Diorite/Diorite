package org.diorite.material.blocks.wooden.wood.fencegate;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "BirchFenceGate" and all its subtypes.
 */
public class BirchFenceGate extends WoodenFenceGate
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__BIRCH_FENCE_GATE__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
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

    /**
     * Returns one of BirchFenceGate sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of BirchFenceGate or null
     */
    public static BirchFenceGate getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of BirchFenceGate sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of BirchFenceGate or null
     */
    public static BirchFenceGate getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
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
