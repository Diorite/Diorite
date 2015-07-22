package org.diorite.material.blocks.wooden.wood.fence;

import java.util.Map;

import org.diorite.material.WoodTypeMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "AcaciaFence" and all its subtypes.
 */
public class AcaciaFenceMat extends WoodenFenceMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final int USED_DATA_VALUES = 1;

    public static final AcaciaFenceMat ACACIA_FENCE = new AcaciaFenceMat();

    private static final Map<String, AcaciaFenceMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<AcaciaFenceMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR, Byte.MIN_VALUE);

    @SuppressWarnings("MagicNumber")
    protected AcaciaFenceMat()
    {
        super("ACACIA_FENCE", 192, "minecraft:acacia_fence", "ACACIA_FENCE", WoodTypeMat.ACACIA, 2, 15);
    }

    protected AcaciaFenceMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodTypeMat woodType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType, hardness, blastResistance);
    }

    @Override
    public AcaciaFenceMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public AcaciaFenceMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of AcaciaFence sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of AcaciaFence or null
     */
    public static AcaciaFenceMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of AcaciaFence sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of AcaciaFence or null
     */
    public static AcaciaFenceMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final AcaciaFenceMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public AcaciaFenceMat[] types()
    {
        return AcaciaFenceMat.acaciaFenceTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static AcaciaFenceMat[] acaciaFenceTypes()
    {
        return byID.values(new AcaciaFenceMat[byID.size()]);
    }

    static
    {
        AcaciaFenceMat.register(ACACIA_FENCE);
    }
}
