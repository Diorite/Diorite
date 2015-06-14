package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "DaylightDetector" and all its subtypes.
 */
@SuppressWarnings("MagicNumber")
public class DaylightDetectorMat extends AbstractDaylightDetectorMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 16;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__DAYLIGHT_DETECTOR__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__DAYLIGHT_DETECTOR__HARDNESS;

    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_OFF = new DaylightDetectorMat();
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_1   = new DaylightDetectorMat(1);
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_2   = new DaylightDetectorMat(2);
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_3   = new DaylightDetectorMat(3);
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_4   = new DaylightDetectorMat(4);
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_5   = new DaylightDetectorMat(5);
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_6   = new DaylightDetectorMat(6);
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_7   = new DaylightDetectorMat(7);
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_8   = new DaylightDetectorMat(8);
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_9   = new DaylightDetectorMat(9);
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_10  = new DaylightDetectorMat(10);
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_11  = new DaylightDetectorMat(11);
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_12  = new DaylightDetectorMat(12);
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_13  = new DaylightDetectorMat(13);
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_14  = new DaylightDetectorMat(14);
    public static final DaylightDetectorMat DAYLIGHT_DETECTOR_15  = new DaylightDetectorMat(15);

    private static final Map<String, DaylightDetectorMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DaylightDetectorMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected DaylightDetectorMat()
    {
        super("DAYLIGHT_DETECTOR", 151, "minecraft:daylight_detector", 0);
    }

    protected DaylightDetectorMat(final int power)
    {
        super(DAYLIGHT_DETECTOR_OFF.name(), DAYLIGHT_DETECTOR_OFF.ordinal(), DAYLIGHT_DETECTOR_OFF.getMinecraftId(), power);
    }

    protected DaylightDetectorMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final int power)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, power);
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
    public DaylightDetectorMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DaylightDetectorMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public AbstractDaylightDetectorMat getInverted()
    {
        return DaylightDetectorInvertedMat.getDaylightDetectorInverted(this.power);
    }

    @Override
    public DaylightDetectorMat getPowerStrength(final int strength)
    {
        return DaylightDetectorMat.getDaylightDetector(this.power);
    }

    @Override
    public DaylightDetectorMat getPowered(final boolean powered)
    {
        return getByID(powered ? 15 : 0);
    }

    /**
     * Returns one of DaylightDetector sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DaylightDetector or null
     */
    public static DaylightDetectorMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of DaylightDetector sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DaylightDetector or null
     */
    public static DaylightDetectorMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of DaylightDetector sub-type based on power strength emited by it.
     * It will never return null.
     *
     * @param strength power of emited signal.
     *
     * @return sub-type of DaylightDetector
     */
    public static DaylightDetectorMat getDaylightDetector(final int strength)
    {
        return getByID(POWER_RANGE.getIn(strength));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DaylightDetectorMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    @Override
    public DaylightDetectorMat[] types()
    {
        return DaylightDetectorMat.daylightDetectorTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DaylightDetectorMat[] daylightDetectorTypes()
    {
        return byID.values(new DaylightDetectorMat[byID.size()]);
    }

    static
    {
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_OFF);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_1);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_2);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_3);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_4);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_5);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_6);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_7);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_8);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_9);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_10);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_11);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_12);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_13);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_14);
        DaylightDetectorMat.register(DAYLIGHT_DETECTOR_15);
    }
}
