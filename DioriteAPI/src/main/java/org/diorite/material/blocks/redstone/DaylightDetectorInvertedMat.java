package org.diorite.material.blocks.redstone;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "DaylightDetectorInverted" and all its subtypes.
 */
@SuppressWarnings("MagicNumber")
public class DaylightDetectorInvertedMat extends AbstractDaylightDetectorMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 16;
    /**
     * Blast resistance of block, can be changed only before server start.
     * Final copy of blast resistance from {@link MagicNumbers} class.
     */
    public static final float BLAST_RESISTANCE = MagicNumbers.MATERIAL__DAYLIGHT_DETECTOR_INVERTED__BLAST_RESISTANCE;
    /**
     * Hardness of block, can be changed only before server start.
     * Final copy of hardness from {@link MagicNumbers} class.
     */
    public static final float HARDNESS         = MagicNumbers.MATERIAL__DAYLIGHT_DETECTOR_INVERTED__HARDNESS;

    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_OFF = new DaylightDetectorInvertedMat();
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_1   = new DaylightDetectorInvertedMat(1);
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_2   = new DaylightDetectorInvertedMat(2);
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_3   = new DaylightDetectorInvertedMat(3);
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_4   = new DaylightDetectorInvertedMat(4);
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_5   = new DaylightDetectorInvertedMat(5);
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_6   = new DaylightDetectorInvertedMat(6);
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_7   = new DaylightDetectorInvertedMat(7);
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_8   = new DaylightDetectorInvertedMat(8);
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_9   = new DaylightDetectorInvertedMat(9);
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_10  = new DaylightDetectorInvertedMat(10);
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_11  = new DaylightDetectorInvertedMat(11);
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_12  = new DaylightDetectorInvertedMat(12);
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_13  = new DaylightDetectorInvertedMat(13);
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_14  = new DaylightDetectorInvertedMat(14);
    public static final DaylightDetectorInvertedMat DAYLIGHT_DETECTOR_INVERTED_15  = new DaylightDetectorInvertedMat(15);

    private static final Map<String, DaylightDetectorInvertedMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<DaylightDetectorInvertedMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected DaylightDetectorInvertedMat()
    {
        super("DAYLIGHT_DETECTOR_INVERTED", 178, "minecraft:daylight_detector_inverted", 0);
    }

    protected DaylightDetectorInvertedMat(final int power)
    {
        super(DAYLIGHT_DETECTOR_INVERTED_OFF.name(), DAYLIGHT_DETECTOR_INVERTED_OFF.getId(), DAYLIGHT_DETECTOR_INVERTED_OFF.getMinecraftId(), power);
    }

    protected DaylightDetectorInvertedMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final int power)
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
    public DaylightDetectorInvertedMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public DaylightDetectorInvertedMat getType(final int id)
    {
        return getByID(id);
    }

    @Override
    public AbstractDaylightDetectorMat getInverted()
    {
        return DaylightDetectorMat.getDaylightDetector(this.power);
    }

    @Override
    public DaylightDetectorInvertedMat getPowerStrength(final int strength)
    {
        return getByID(POWER_RANGE.getIn(strength));
    }

    @Override
    public DaylightDetectorInvertedMat getPowered(final boolean powered)
    {
        return getByID(powered ? 15 : 0);
    }

    /**
     * Returns one of DaylightDetectorInverted sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of DaylightDetectorInverted or null
     */
    public static DaylightDetectorInvertedMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of DaylightDetectorInverted sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of DaylightDetectorInverted or null
     */
    public static DaylightDetectorInvertedMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Returns one of DaylightDetectorInverted sub-type based on power strength emited by it.
     * It will never return null.
     *
     * @param strength power of emited signal.
     *
     * @return sub-type of DaylightDetectorInverted
     */
    public static DaylightDetectorInvertedMat getDaylightDetectorInverted(final int strength)
    {
        return getByID(POWER_RANGE.getIn(strength));
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final DaylightDetectorInvertedMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.name(), element);
    }

    @Override
    public DaylightDetectorInvertedMat[] types()
    {
        return DaylightDetectorInvertedMat.daylightDetectorInvertedTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static DaylightDetectorInvertedMat[] daylightDetectorInvertedTypes()
    {
        return byID.values(new DaylightDetectorInvertedMat[byID.size()]);
    }

    static
    {
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_OFF);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_1);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_2);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_3);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_4);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_5);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_6);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_7);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_8);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_9);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_10);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_11);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_12);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_13);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_14);
        DaylightDetectorInvertedMat.register(DAYLIGHT_DETECTOR_INVERTED_15);
    }
}
