package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;
import org.diorite.utils.math.DioriteMathUtils;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "GoldenPressurePlate" and all its subtypes.
 */
@SuppressWarnings("MagicNumber")
public class GoldenPressurePlateMat extends WeightedPressurePlateMat
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

    public static final GoldenPressurePlateMat GOLDEN_PRESSURE_PLATE_0  = new GoldenPressurePlateMat();
    public static final GoldenPressurePlateMat GOLDEN_PRESSURE_PLATE_1  = new GoldenPressurePlateMat(0x1);
    public static final GoldenPressurePlateMat GOLDEN_PRESSURE_PLATE_2  = new GoldenPressurePlateMat(0x2);
    public static final GoldenPressurePlateMat GOLDEN_PRESSURE_PLATE_3  = new GoldenPressurePlateMat(0x3);
    public static final GoldenPressurePlateMat GOLDEN_PRESSURE_PLATE_4  = new GoldenPressurePlateMat(0x4);
    public static final GoldenPressurePlateMat GOLDEN_PRESSURE_PLATE_5  = new GoldenPressurePlateMat(0x5);
    public static final GoldenPressurePlateMat GOLDEN_PRESSURE_PLATE_6  = new GoldenPressurePlateMat(0x6);
    public static final GoldenPressurePlateMat GOLDEN_PRESSURE_PLATE_7  = new GoldenPressurePlateMat(0x7);
    public static final GoldenPressurePlateMat GOLDEN_PRESSURE_PLATE_8  = new GoldenPressurePlateMat(0x8);
    public static final GoldenPressurePlateMat GOLDEN_PRESSURE_PLATE_9  = new GoldenPressurePlateMat(0x9);
    public static final GoldenPressurePlateMat GOLDEN_PRESSURE_PLATE_10 = new GoldenPressurePlateMat(0xA);
    public static final GoldenPressurePlateMat GOLDEN_PRESSURE_PLATE_11 = new GoldenPressurePlateMat(0xB);
    public static final GoldenPressurePlateMat GOLDEN_PRESSURE_PLATE_12 = new GoldenPressurePlateMat(0xC);
    public static final GoldenPressurePlateMat GOLDEN_PRESSURE_PLATE_13 = new GoldenPressurePlateMat(0xD);
    public static final GoldenPressurePlateMat GOLDEN_PRESSURE_PLATE_14 = new GoldenPressurePlateMat(0xE);
    public static final GoldenPressurePlateMat GOLDEN_PRESSURE_PLATE_15 = new GoldenPressurePlateMat(0xF);

    private static final Map<String, GoldenPressurePlateMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<GoldenPressurePlateMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected GoldenPressurePlateMat()
    {
        super("GOLDEN_PRESSURE_PLATE", 147, "minecraft:light_weighted_pressure_plate", "0", (byte) 0x00, false);
    }

    protected GoldenPressurePlateMat(final int type)
    {
        super(GOLDEN_PRESSURE_PLATE_0.name(), GOLDEN_PRESSURE_PLATE_0.getId(), GOLDEN_PRESSURE_PLATE_0.getMinecraftId(), Integer.toString(type), (byte) type, true);
    }

    protected GoldenPressurePlateMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final boolean powered, final int power)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, powered, power);
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
    public GoldenPressurePlateMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public GoldenPressurePlateMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public GoldenPressurePlateMat getPowerStrength(final int strength)
    {
        return getByID(DioriteMathUtils.getInRange(strength, 0, 15));
    }

    @Override
    public GoldenPressurePlateMat getPowered(final boolean powered)
    {
        return getByID(powered ? 15 : 0);
    }

    /**
     * Returns one of GoldenPressurePlate sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of GoldenPressurePlate or null
     */
    public static GoldenPressurePlateMat getByID(final int id)
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
    public static GoldenPressurePlateMat getByEnumName(final String name)
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
    public static GoldenPressurePlateMat getGoldenPressurePlate(final int power)
    {
        return getByID(DioriteMathUtils.getInRange(power, 0, 15));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final GoldenPressurePlateMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    static
    {
        GoldenPressurePlateMat.register(GOLDEN_PRESSURE_PLATE_0);
        GoldenPressurePlateMat.register(GOLDEN_PRESSURE_PLATE_1);
        GoldenPressurePlateMat.register(GOLDEN_PRESSURE_PLATE_2);
        GoldenPressurePlateMat.register(GOLDEN_PRESSURE_PLATE_3);
        GoldenPressurePlateMat.register(GOLDEN_PRESSURE_PLATE_4);
        GoldenPressurePlateMat.register(GOLDEN_PRESSURE_PLATE_5);
        GoldenPressurePlateMat.register(GOLDEN_PRESSURE_PLATE_6);
        GoldenPressurePlateMat.register(GOLDEN_PRESSURE_PLATE_7);
        GoldenPressurePlateMat.register(GOLDEN_PRESSURE_PLATE_8);
        GoldenPressurePlateMat.register(GOLDEN_PRESSURE_PLATE_9);
        GoldenPressurePlateMat.register(GOLDEN_PRESSURE_PLATE_10);
        GoldenPressurePlateMat.register(GOLDEN_PRESSURE_PLATE_11);
        GoldenPressurePlateMat.register(GOLDEN_PRESSURE_PLATE_12);
        GoldenPressurePlateMat.register(GOLDEN_PRESSURE_PLATE_13);
        GoldenPressurePlateMat.register(GOLDEN_PRESSURE_PLATE_14);
        GoldenPressurePlateMat.register(GOLDEN_PRESSURE_PLATE_15);
    }
}
