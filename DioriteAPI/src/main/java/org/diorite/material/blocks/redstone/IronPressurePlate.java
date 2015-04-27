package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;
import org.diorite.utils.math.DioriteMathUtils;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "IronPressurePlate" and all its subtypes.
 */
@SuppressWarnings("MagicNumber")
public class IronPressurePlate extends WeightedPressurePlate
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 16;
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

    public static final IronPressurePlate IRON_PRESSURE_PLATE_0  = new IronPressurePlate();
    public static final IronPressurePlate IRON_PRESSURE_PLATE_1  = new IronPressurePlate(0x1);
    public static final IronPressurePlate IRON_PRESSURE_PLATE_2  = new IronPressurePlate(0x2);
    public static final IronPressurePlate IRON_PRESSURE_PLATE_3  = new IronPressurePlate(0x3);
    public static final IronPressurePlate IRON_PRESSURE_PLATE_4  = new IronPressurePlate(0x4);
    public static final IronPressurePlate IRON_PRESSURE_PLATE_5  = new IronPressurePlate(0x5);
    public static final IronPressurePlate IRON_PRESSURE_PLATE_6  = new IronPressurePlate(0x6);
    public static final IronPressurePlate IRON_PRESSURE_PLATE_7  = new IronPressurePlate(0x7);
    public static final IronPressurePlate IRON_PRESSURE_PLATE_8  = new IronPressurePlate(0x8);
    public static final IronPressurePlate IRON_PRESSURE_PLATE_9  = new IronPressurePlate(0x9);
    public static final IronPressurePlate IRON_PRESSURE_PLATE_10 = new IronPressurePlate(0xA);
    public static final IronPressurePlate IRON_PRESSURE_PLATE_11 = new IronPressurePlate(0xB);
    public static final IronPressurePlate IRON_PRESSURE_PLATE_12 = new IronPressurePlate(0xC);
    public static final IronPressurePlate IRON_PRESSURE_PLATE_13 = new IronPressurePlate(0xD);
    public static final IronPressurePlate IRON_PRESSURE_PLATE_14 = new IronPressurePlate(0xE);
    public static final IronPressurePlate IRON_PRESSURE_PLATE_15 = new IronPressurePlate(0xF);

    private static final Map<String, IronPressurePlate>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<IronPressurePlate> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected IronPressurePlate()
    {
        super("IRON_PRESSURE_PLATE", 148, "minecraft:heavy_weighted_pressure_plate", "0", (byte) 0x00, false);
    }

    public IronPressurePlate(final int type)
    {
        super(IRON_PRESSURE_PLATE_0.name(), IRON_PRESSURE_PLATE_0.getId(), IRON_PRESSURE_PLATE_0.getMinecraftId(), Integer.toString(type), (byte) type, true);
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
    public IronPressurePlate getPowerStrength(final int strength)
    {
        return getByID(DioriteMathUtils.getInRange(strength, 0, 15));
    }

    @Override
    public IronPressurePlate getActivated(final boolean activate)
    {
        return getByID(activate ? 15 : 0);
    }

    /**
     * Returns one of IronPressurePlate sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of IronPressurePlate or null
     */
    public static IronPressurePlate getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of IronPressurePlate sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of IronPressurePlate or null
     */
    public static IronPressurePlate getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of IronPressurePlate sub-type based on emitted power.
     * It will never return null.
     *
     * @param power power that should be emitted by plate.
     *
     * @return sub-type of IronPressurePlate
     */
    public static IronPressurePlate getIronPressurePlate(final int power)
    {
        return getByID(DioriteMathUtils.getInRange(power, 0, 15));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final IronPressurePlate element)
    {
        byID.put(element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        IronPressurePlate.register(IRON_PRESSURE_PLATE_0);
        IronPressurePlate.register(IRON_PRESSURE_PLATE_1);
        IronPressurePlate.register(IRON_PRESSURE_PLATE_2);
        IronPressurePlate.register(IRON_PRESSURE_PLATE_3);
        IronPressurePlate.register(IRON_PRESSURE_PLATE_4);
        IronPressurePlate.register(IRON_PRESSURE_PLATE_5);
        IronPressurePlate.register(IRON_PRESSURE_PLATE_6);
        IronPressurePlate.register(IRON_PRESSURE_PLATE_7);
        IronPressurePlate.register(IRON_PRESSURE_PLATE_8);
        IronPressurePlate.register(IRON_PRESSURE_PLATE_9);
        IronPressurePlate.register(IRON_PRESSURE_PLATE_10);
        IronPressurePlate.register(IRON_PRESSURE_PLATE_11);
        IronPressurePlate.register(IRON_PRESSURE_PLATE_12);
        IronPressurePlate.register(IRON_PRESSURE_PLATE_13);
        IronPressurePlate.register(IRON_PRESSURE_PLATE_14);
        IronPressurePlate.register(IRON_PRESSURE_PLATE_15);
    }
}
