package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "WoodenPressurePlate" and all its subtypes.
 */
public class WoodenPressurePlate extends PressurePlate
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
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__WOODEN_PRESSURE_PLATE__BLAST_RESISTANCE;	
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__WOODEN_PRESSURE_PLATE__HARDNESS;

    public static final WoodenPressurePlate WOODEN_PRESSURE_PLATE = new WoodenPressurePlate();

    private static final Map<String, WoodenPressurePlate>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<WoodenPressurePlate> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected WoodenPressurePlate()
    {
        super("WOODEN_PRESSURE_PLATE", 72, "minecraft:wooden_pressure_plate", "WOODEN_PRESSURE_PLATE", (byte) 0x00);
    }

    public WoodenPressurePlate(final String enumName, final int type)
    {
        super(WOODEN_PRESSURE_PLATE.name(), WOODEN_PRESSURE_PLATE.getId(), WOODEN_PRESSURE_PLATE.getMinecraftId(), enumName, (byte) type);
    }

    public WoodenPressurePlate(final int maxStack, final String typeName, final byte type)
    {
        super(WOODEN_PRESSURE_PLATE.name(), WOODEN_PRESSURE_PLATE.getId(), WOODEN_PRESSURE_PLATE.getMinecraftId(), maxStack, typeName, type);
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
    public WoodenPressurePlate getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public WoodenPressurePlate getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public boolean isActivated()
    {
        return false; // TODO: implement
    }

    /**
     * Returns one of WoodenPressurePlate sub-type based on sub-id, may return null
     * @param id sub-type id
     * @return sub-type of WoodenPressurePlate or null
     */
    public static WoodenPressurePlate getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of WoodenPressurePlate sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     * @param name name of sub-type
     * @return sub-type of WoodenPressurePlate or null
     */
    public static WoodenPressurePlate getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     * @param element sub-type to register
     */
    public static void register(final WoodenPressurePlate element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        WoodenPressurePlate.register(WOODEN_PRESSURE_PLATE);
    }
}
