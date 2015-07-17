package org.diorite.material.blocks.wooden.wood.fence;

import java.util.Map;

import org.diorite.material.WoodTypeMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "OakFence" and all its subtypes.
 */
public class OakFenceMat extends WoodenFenceMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;

    public static final OakFenceMat OAK_FENCE = new OakFenceMat();

    private static final Map<String, OakFenceMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<OakFenceMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected OakFenceMat()
    {
        super("OAK_FENCE", 85, "minecraft:fence", "OAK_FENCE", WoodTypeMat.OAK, 2, 15);
    }

    protected OakFenceMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodTypeMat woodType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType, hardness, blastResistance);
    }

    @Override
    public OakFenceMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public OakFenceMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of OakFence sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of OakFence or null
     */
    public static OakFenceMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of OakFence sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of OakFence or null
     */
    public static OakFenceMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final OakFenceMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public OakFenceMat[] types()
    {
        return OakFenceMat.oakFenceTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static OakFenceMat[] oakFenceTypes()
    {
        return byID.values(new OakFenceMat[byID.size()]);
    }

    static
    {
        OakFenceMat.register(OAK_FENCE);
    }
}
