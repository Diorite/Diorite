package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "StonePressurePlate" and all its subtypes.
 */
public class StonePressurePlate extends PressurePlate
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 2;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__STONE_PRESSURE_PLATE__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__STONE_PRESSURE_PLATE__HARDNESS;

    public static final StonePressurePlate STONE_PRESSURE_PLATE           = new StonePressurePlate();
    public static final StonePressurePlate STONE_PRESSURE_PLATE_ACTIVATED = new StonePressurePlate("ACTIVATED", 0x1, true);

    private static final Map<String, StonePressurePlate>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<StonePressurePlate> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected StonePressurePlate()
    {
        super("STONE_PRESSURE_PLATE", 70, "minecraft:stone_pressure_plate", "STONE_PRESSURE_PLATE", (byte) 0x00, false);
    }

    public StonePressurePlate(final String enumName, final int type, final boolean activated)
    {
        super(STONE_PRESSURE_PLATE.name(), STONE_PRESSURE_PLATE.getId(), STONE_PRESSURE_PLATE.getMinecraftId(), enumName, (byte) type, activated);
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
    public StonePressurePlate getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public StonePressurePlate getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public StonePressurePlate getActivated(final boolean activated)
    {
        return getByID(activated ? 1 : 0);
    }

    /**
     * Returns one of StonePressurePlate sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of StonePressurePlate or null
     */
    public static StonePressurePlate getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of StonePressurePlate sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of StonePressurePlate or null
     */
    public static StonePressurePlate getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of StonePressurePlate sub-type based on activated state.
     * It will never return null.
     *
     * @param activated if pressure player should be activated.
     *
     * @return sub-type of StonePressurePlate
     */
    public static StonePressurePlate getStonePressurePlate(final boolean activated)
    {
        return getByID(activated ? 1 : 0);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final StonePressurePlate element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        StonePressurePlate.register(STONE_PRESSURE_PLATE);
        StonePressurePlate.register(STONE_PRESSURE_PLATE_ACTIVATED);
    }
}
