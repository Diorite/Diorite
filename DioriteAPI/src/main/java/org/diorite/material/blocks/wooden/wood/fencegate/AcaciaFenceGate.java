package org.diorite.material.blocks.wooden.wood.fencegate;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "AcaciaFenceGate" and all its subtypes.
 */
public class AcaciaFenceGate extends WoodenFenceGate
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__ACACIA_FENCE_GATE__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__ACACIA_FENCE_GATE__HARDNESS;

    public static final AcaciaFenceGate ACACIA_FENCE_GATE = new AcaciaFenceGate();

    private static final Map<String, AcaciaFenceGate>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<AcaciaFenceGate> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected AcaciaFenceGate()
    {
        super("ACACIA_FENCE_GATE", 187, "minecraft:acacia_fence_gate", "ACACIA_FENCE_GATE", WoodType.ACACIA);
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
    public AcaciaFenceGate getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public AcaciaFenceGate getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of AcaciaFenceGate sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of AcaciaFenceGate or null
     */
    public static AcaciaFenceGate getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of AcaciaFenceGate sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of AcaciaFenceGate or null
     */
    public static AcaciaFenceGate getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
    public static void register(final AcaciaFenceGate element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        AcaciaFenceGate.register(ACACIA_FENCE_GATE);
    }
}
