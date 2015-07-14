package org.diorite.material.blocks.wooden.wood.fence;

import java.util.Map;

import org.diorite.cfg.magic.MagicNumbers;
import org.diorite.material.blocks.wooden.WoodTypeMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "BirchFence" and all its subtypes.
 */
public class BirchFenceMat extends WoodenFenceMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;

    public static final BirchFenceMat BRICH_FENCE = new BirchFenceMat();

    private static final Map<String, BirchFenceMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<BirchFenceMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected BirchFenceMat()
    {
        super("BIRCH_FENCE", 189, "minecraft:birch_fence", "BIRCH_FENCE", WoodTypeMat.BIRCH, 2, 15);
    }

    protected BirchFenceMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodTypeMat woodType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType, hardness, blastResistance);
    }

    @Override
    public BirchFenceMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public BirchFenceMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of BirchFence sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of BirchFence or null
     */
    public static BirchFenceMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of BirchFence sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of BirchFence or null
     */
    public static BirchFenceMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final BirchFenceMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public BirchFenceMat[] types()
    {
        return BirchFenceMat.birchFenceTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static BirchFenceMat[] birchFenceTypes()
    {
        return byID.values(new BirchFenceMat[byID.size()]);
    }

    static
    {
        BirchFenceMat.register(BRICH_FENCE);
    }
}
