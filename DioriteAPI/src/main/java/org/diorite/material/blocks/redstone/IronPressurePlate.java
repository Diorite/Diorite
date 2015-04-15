package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "IronPressurePlate" and all its subtypes.
 */
public class IronPressurePlate extends WeightedPressurePlate
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__IRON_PRESSURE_PLATE__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__IRON_PRESSURE_PLATE__HARDNESS;

    public static final IronPressurePlate IRON_PRESSURE_PLATE = new IronPressurePlate();

    private static final Map<String, IronPressurePlate>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<IronPressurePlate> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected IronPressurePlate()
    {
        super("IRON_PRESSURE_PLATE", 148, "minecraft:heavy_weighted_pressure_plate", "IRON_PRESSURE_PLATE", (byte) 0x00);
    }

    public IronPressurePlate(final String enumName, final int type)
    {
        super(IRON_PRESSURE_PLATE.name(), IRON_PRESSURE_PLATE.getId(), IRON_PRESSURE_PLATE.getMinecraftId(), enumName, (byte) type);
    }

    public IronPressurePlate(final int maxStack, final String typeName, final byte type)
    {
        super(IRON_PRESSURE_PLATE.name(), IRON_PRESSURE_PLATE.getId(), IRON_PRESSURE_PLATE.getMinecraftId(), maxStack, typeName, type);
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
    public IronPressurePlate getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public IronPressurePlate getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isActivated()
    {
        return false; // TODO: implement
    }

    /**
     * Returns one of IronPressurePlate sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of IronPressurePlate or null
     */
    public static IronPressurePlate getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of IronPressurePlate sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of IronPressurePlate or null
     */
    public static IronPressurePlate getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
    public static void register(final IronPressurePlate element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        IronPressurePlate.register(IRON_PRESSURE_PLATE);
    }
}
