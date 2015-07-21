package org.diorite.material.blocks.plants;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "WaterLily" and all its subtypes.
 */
public class WaterLilyMat extends PlantMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final WaterLilyMat WATER_LILY = new WaterLilyMat();

    private static final Map<String, WaterLilyMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<WaterLilyMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected WaterLilyMat()
    {
        super("WATER_LILY", 111, "minecraft:waterlily", "WATER_LILY", (byte) 0x00, 0.6f, 0);
    }

    protected WaterLilyMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public WaterLilyMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public WaterLilyMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of WaterLily sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of WaterLily or null
     */
    public static WaterLilyMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of WaterLily sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of WaterLily or null
     */
    public static WaterLilyMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final WaterLilyMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public WaterLilyMat[] types()
    {
        return WaterLilyMat.waterLilyTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static WaterLilyMat[] waterLilyTypes()
    {
        return byID.values(new WaterLilyMat[byID.size()]);
    }

    static
    {
        WaterLilyMat.register(WATER_LILY);
    }
}
