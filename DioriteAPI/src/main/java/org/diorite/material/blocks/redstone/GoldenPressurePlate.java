package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "GoldenPressurePlate" and all its subtypes.
 */
public class GoldenPressurePlate extends WeightedPressurePlate
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__GOLDEN_PRESSURE_PLATE__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__GOLDEN_PRESSURE_PLATE__HARDNESS;

    public static final GoldenPressurePlate GOLDEN_PRESSURE_PLATE = new GoldenPressurePlate();

    private static final Map<String, GoldenPressurePlate>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<GoldenPressurePlate> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected GoldenPressurePlate()
    {
        super("GOLDEN_PRESSURE_PLATE", 147, "minecraft:light_weighted_pressure_plate", "GOLDEN_PRESSURE_PLATE", (byte) 0x00);
    }

    public GoldenPressurePlate(final String enumName, final int type)
    {
        super(GOLDEN_PRESSURE_PLATE.name(), GOLDEN_PRESSURE_PLATE.getId(), GOLDEN_PRESSURE_PLATE.getMinecraftId(), enumName, (byte) type);
    }

    public GoldenPressurePlate(final int maxStack, final String typeName, final byte type)
    {
        super(GOLDEN_PRESSURE_PLATE.name(), GOLDEN_PRESSURE_PLATE.getId(), GOLDEN_PRESSURE_PLATE.getMinecraftId(), maxStack, typeName, type);
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
    public GoldenPressurePlate getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public GoldenPressurePlate getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isActivated()
    {
        return false; // TODO: implement
    }

    /**
     * Returns one of GoldenPressurePlate sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of GoldenPressurePlate or null
     */
    public static GoldenPressurePlate getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of GoldenPressurePlate sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of GoldenPressurePlate or null
     */
    public static GoldenPressurePlate getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final GoldenPressurePlate element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        GoldenPressurePlate.register(GOLDEN_PRESSURE_PLATE);
    }
}
