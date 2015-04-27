package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.SimpleStringHashMap;
import org.diorite.utils.math.DioriteMathUtils;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "GoldenPressurePlate" and all its subtypes.
 */
@SuppressWarnings("MagicNumber")
public class GoldenPressurePlate extends WeightedPressurePlate
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 16;
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

    public static final GoldenPressurePlate GOLDEN_PRESSURE_PLATE_0  = new GoldenPressurePlate();
    public static final GoldenPressurePlate GOLDEN_PRESSURE_PLATE_1  = new GoldenPressurePlate(0x1);
    public static final GoldenPressurePlate GOLDEN_PRESSURE_PLATE_2  = new GoldenPressurePlate(0x2);
    public static final GoldenPressurePlate GOLDEN_PRESSURE_PLATE_3  = new GoldenPressurePlate(0x3);
    public static final GoldenPressurePlate GOLDEN_PRESSURE_PLATE_4  = new GoldenPressurePlate(0x4);
    public static final GoldenPressurePlate GOLDEN_PRESSURE_PLATE_5  = new GoldenPressurePlate(0x5);
    public static final GoldenPressurePlate GOLDEN_PRESSURE_PLATE_6  = new GoldenPressurePlate(0x6);
    public static final GoldenPressurePlate GOLDEN_PRESSURE_PLATE_7  = new GoldenPressurePlate(0x7);
    public static final GoldenPressurePlate GOLDEN_PRESSURE_PLATE_8  = new GoldenPressurePlate(0x8);
    public static final GoldenPressurePlate GOLDEN_PRESSURE_PLATE_9  = new GoldenPressurePlate(0x9);
    public static final GoldenPressurePlate GOLDEN_PRESSURE_PLATE_10 = new GoldenPressurePlate(0xA);
    public static final GoldenPressurePlate GOLDEN_PRESSURE_PLATE_11 = new GoldenPressurePlate(0xB);
    public static final GoldenPressurePlate GOLDEN_PRESSURE_PLATE_12 = new GoldenPressurePlate(0xC);
    public static final GoldenPressurePlate GOLDEN_PRESSURE_PLATE_13 = new GoldenPressurePlate(0xD);
    public static final GoldenPressurePlate GOLDEN_PRESSURE_PLATE_14 = new GoldenPressurePlate(0xE);
    public static final GoldenPressurePlate GOLDEN_PRESSURE_PLATE_15 = new GoldenPressurePlate(0xF);

    private static final Map<String, GoldenPressurePlate>    byName = new SimpleStringHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<GoldenPressurePlate> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected GoldenPressurePlate()
    {
        super("GOLDEN_PRESSURE_PLATE", 147, "minecraft:light_weighted_pressure_plate", "0", (byte) 0x00, false);
    }

    public GoldenPressurePlate(final int type)
    {
        super(GOLDEN_PRESSURE_PLATE_0.name(), GOLDEN_PRESSURE_PLATE_0.getId(), GOLDEN_PRESSURE_PLATE_0.getMinecraftId(), Integer.toString(type), (byte) type, true);
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
    public GoldenPressurePlate getPowerStrength(final int strength)
    {
        return getByID(DioriteMathUtils.getInRange(strength, 0, 15));
    }

    @Override
    public GoldenPressurePlate getActivated(final boolean activate)
    {
        return getByID(activate ? 15 : 0);
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
     * Returns one of GoldenPressurePlate sub-type based on emitted power.
     * It will never return null.
     *
     * @param power power that should be emitted by plate.
     *
     * @return sub-type of GoldenPressurePlate
     */
    public static GoldenPressurePlate getGoldenPressurePlate(final int power)
    {
        return getByID(DioriteMathUtils.getInRange(power, 0, 15));
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
        GoldenPressurePlate.register(GOLDEN_PRESSURE_PLATE_0);
        GoldenPressurePlate.register(GOLDEN_PRESSURE_PLATE_1);
        GoldenPressurePlate.register(GOLDEN_PRESSURE_PLATE_2);
        GoldenPressurePlate.register(GOLDEN_PRESSURE_PLATE_3);
        GoldenPressurePlate.register(GOLDEN_PRESSURE_PLATE_4);
        GoldenPressurePlate.register(GOLDEN_PRESSURE_PLATE_5);
        GoldenPressurePlate.register(GOLDEN_PRESSURE_PLATE_6);
        GoldenPressurePlate.register(GOLDEN_PRESSURE_PLATE_7);
        GoldenPressurePlate.register(GOLDEN_PRESSURE_PLATE_8);
        GoldenPressurePlate.register(GOLDEN_PRESSURE_PLATE_9);
        GoldenPressurePlate.register(GOLDEN_PRESSURE_PLATE_10);
        GoldenPressurePlate.register(GOLDEN_PRESSURE_PLATE_11);
        GoldenPressurePlate.register(GOLDEN_PRESSURE_PLATE_12);
        GoldenPressurePlate.register(GOLDEN_PRESSURE_PLATE_13);
        GoldenPressurePlate.register(GOLDEN_PRESSURE_PLATE_14);
        GoldenPressurePlate.register(GOLDEN_PRESSURE_PLATE_15);
    }
}
