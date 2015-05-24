package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;
import org.diorite.utils.math.DioriteMathUtils;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "IronPressurePlate" and all its subtypes.
 */
@SuppressWarnings("MagicNumber")
public class IronPressurePlateMat extends WeightedPressurePlateMat
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

    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_0  = new IronPressurePlateMat();
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_1  = new IronPressurePlateMat(0x1);
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_2  = new IronPressurePlateMat(0x2);
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_3  = new IronPressurePlateMat(0x3);
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_4  = new IronPressurePlateMat(0x4);
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_5  = new IronPressurePlateMat(0x5);
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_6  = new IronPressurePlateMat(0x6);
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_7  = new IronPressurePlateMat(0x7);
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_8  = new IronPressurePlateMat(0x8);
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_9  = new IronPressurePlateMat(0x9);
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_10 = new IronPressurePlateMat(0xA);
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_11 = new IronPressurePlateMat(0xB);
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_12 = new IronPressurePlateMat(0xC);
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_13 = new IronPressurePlateMat(0xD);
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_14 = new IronPressurePlateMat(0xE);
    public static final IronPressurePlateMat IRON_PRESSURE_PLATE_15 = new IronPressurePlateMat(0xF);

    private static final Map<String, IronPressurePlateMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<IronPressurePlateMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected IronPressurePlateMat()
    {
        super("IRON_PRESSURE_PLATE", 148, "minecraft:heavy_weighted_pressure_plate", "0", (byte) 0x00, false);
    }

    protected IronPressurePlateMat(final int type)
    {
        super(IRON_PRESSURE_PLATE_0.name(), IRON_PRESSURE_PLATE_0.getId(), IRON_PRESSURE_PLATE_0.getMinecraftId(), Integer.toString(type), (byte) type, true);
    }

    protected IronPressurePlateMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final boolean powered, final int power)
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
    public IronPressurePlateMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public IronPressurePlateMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public IronPressurePlateMat getPowerStrength(final int strength)
    {
        return getByID(DioriteMathUtils.getInRange(strength, 0, 15));
    }

    @Override
    public IronPressurePlateMat getPowered(final boolean powered)
    {
        return getByID(powered ? 15 : 0);
    }

    /**
     * Returns one of IronPressurePlate sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of IronPressurePlate or null
     */
    public static IronPressurePlateMat getByID(final int id)
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
    public static IronPressurePlateMat getByEnumName(final String name)
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
    public static IronPressurePlateMat getIronPressurePlate(final int power)
    {
        return getByID(DioriteMathUtils.getInRange(power, 0, 15));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final IronPressurePlateMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    @Override
    public IronPressurePlateMat[] types()
    {
        return IronPressurePlateMat.ironPressurePlateTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static IronPressurePlateMat[] ironPressurePlateTypes()
    {
        return byID.values(new IronPressurePlateMat[byID.size()]);
    }

    static
    {
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_0);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_1);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_2);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_3);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_4);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_5);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_6);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_7);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_8);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_9);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_10);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_11);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_12);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_13);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_14);
        IronPressurePlateMat.register(IRON_PRESSURE_PLATE_15);
    }
}
