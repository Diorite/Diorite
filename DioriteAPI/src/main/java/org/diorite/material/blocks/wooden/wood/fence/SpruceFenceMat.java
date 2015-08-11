package org.diorite.material.blocks.wooden.wood.fence;

import java.util.Map;

import org.diorite.material.WoodTypeMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "SpruceFence" and all its subtypes.
 */
public class SpruceFenceMat extends WoodenFenceMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final SpruceFenceMat SPRUCE_FENCE = new SpruceFenceMat();

    private static final Map<String, SpruceFenceMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<SpruceFenceMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected SpruceFenceMat()
    {
        super("SPRUCE_FENCE", 188, "minecraft:spruce_fence", "SPRUCE_FENCE", WoodTypeMat.SPRUCE, 2, 15);
    }

    protected SpruceFenceMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodTypeMat woodType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType, hardness, blastResistance);
    }

    @Override
    public SpruceFenceMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public SpruceFenceMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of SpruceFence sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of SpruceFence or null
     */
    public static SpruceFenceMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of SpruceFence sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of SpruceFence or null
     */
    public static SpruceFenceMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final SpruceFenceMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public SpruceFenceMat[] types()
    {
        return SpruceFenceMat.spruceFenceTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static SpruceFenceMat[] spruceFenceTypes()
    {
        return byID.values(new SpruceFenceMat[byID.size()]);
    }

    static
    {
        SpruceFenceMat.register(SPRUCE_FENCE);
    }
}
