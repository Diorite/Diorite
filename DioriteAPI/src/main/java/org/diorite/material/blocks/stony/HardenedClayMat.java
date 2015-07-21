package org.diorite.material.blocks.stony;

import java.util.Map;

import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "HardenedClay" and all its subtypes.
 */
public class HardenedClayMat extends StonyMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final HardenedClayMat HARDENED_CLAY = new HardenedClayMat();

    private static final Map<String, HardenedClayMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<HardenedClayMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected HardenedClayMat()
    {
        super("HARDENED_CLAY", 172, "minecraft:hardened_clay", "HARDENED_CLAY", (byte) 0x00, 1.25f, 30);
    }

    protected HardenedClayMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public HardenedClayMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public HardenedClayMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of HardenedClay sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of HardenedClay or null
     */
    public static HardenedClayMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of HardenedClay sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of HardenedClay or null
     */
    public static HardenedClayMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final HardenedClayMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public HardenedClayMat[] types()
    {
        return HardenedClayMat.hardenedClayTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static HardenedClayMat[] hardenedClayTypes()
    {
        return byID.values(new HardenedClayMat[byID.size()]);
    }

    static
    {
        HardenedClayMat.register(HARDENED_CLAY);
    }
}
