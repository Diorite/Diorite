package org.diorite.material.blocks.wooden.wood.fence;

import java.util.Map;

import org.diorite.material.WoodTypeMat;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;

import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.hash.TByteObjectHashMap;

/**
 * Class representing block "JungleFence" and all its subtypes.
 */
public class JungleFenceMat extends WoodenFenceMat
{
    /**
     * Sub-ids used by diorite/minecraft by default
     */
    public static final byte  USED_DATA_VALUES = 1;

    public static final JungleFenceMat JUNGLE_FENCE = new JungleFenceMat();

    private static final Map<String, JungleFenceMat>    byName = new CaseInsensitiveMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);
    private static final TByteObjectMap<JungleFenceMat> byID   = new TByteObjectHashMap<>(USED_DATA_VALUES, SMALL_LOAD_FACTOR);

    @SuppressWarnings("MagicNumber")
    protected JungleFenceMat()
    {
        super("JUNGLE_FENCE", 190, "minecraft:jungle_fence", "JUNGLE_FENCE", WoodTypeMat.JUNGLE, 2, 15);
    }

    protected JungleFenceMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodTypeMat woodType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType, hardness, blastResistance);
    }

    @Override
    public JungleFenceMat getType(final String name)
    {
        return getByEnumName(name);
    }

    @Override
    public JungleFenceMat getType(final int id)
    {
        return getByID(id);
    }

    /**
     * Returns one of JungleFence sub-type based on sub-id, may return null
     *
     * @param id sub-type id
     *
     * @return sub-type of JungleFence or null
     */
    public static JungleFenceMat getByID(final int id)
    {
        return byID.get((byte) id);
    }

    /**
     * Returns one of JungleFence sub-type based on name (selected by diorite team), may return null
     * If block contains only one type, sub-name of it will be this same as name of material.
     *
     * @param name name of sub-type
     *
     * @return sub-type of JungleFence or null
     */
    public static JungleFenceMat getByEnumName(final String name)
    {
        return byName.get(name);
    }

    /**
     * Register new sub-type, may replace existing sub-types.
     * Should be used only if you know what are you doing, it will not create fully usable material.
     *
     * @param element sub-type to register
     */
    public static void register(final JungleFenceMat element)
    {
        byID.put((byte) element.getType(), element);
        byName.put(element.getTypeName(), element);
    }

    @Override
    public JungleFenceMat[] types()
    {
        return JungleFenceMat.jungleFenceTypes();
    }

    /**
     * @return array that contains all sub-types of this block.
     */
    public static JungleFenceMat[] jungleFenceTypes()
    {
        return byID.values(new JungleFenceMat[byID.size()]);
    }

    static
    {
        JungleFenceMat.register(JUNGLE_FENCE);
    }
}
