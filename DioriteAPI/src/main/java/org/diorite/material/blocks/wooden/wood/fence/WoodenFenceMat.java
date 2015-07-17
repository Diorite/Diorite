package org.diorite.material.blocks.wooden.wood.fence;

import org.diorite.material.WoodTypeMat;
import org.diorite.material.blocks.FenceMat;
import org.diorite.material.blocks.wooden.wood.WoodMat;
import org.diorite.utils.collections.maps.SimpleEnumMap;

/**
 * Abstract class for all WoodenFence-based blocks
 */
public abstract class WoodenFenceMat extends WoodMat implements FenceMat
{
    protected WoodenFenceMat(final String enumName, final int id, final String minecraftId, final String typeName, final WoodTypeMat woodType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, typeName, (byte) 0, woodType, hardness, blastResistance);
    }

    protected WoodenFenceMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodTypeMat woodType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType, hardness, blastResistance);
    }

    private static final SimpleEnumMap<WoodTypeMat, WoodenFenceMat> types = new SimpleEnumMap<>(6, SMALL_LOAD_FACTOR);

    @Override
    public WoodenFenceMat getWoodType(final WoodTypeMat woodType)
    {
        return types.get(woodType);
    }

    /**
     * Returns sub-type of {@link WoodenFenceMat}, based on {@link WoodTypeMat}.
     *
     * @param woodType {@link WoodTypeMat} of WoodenFence
     *
     * @return sub-type of {@link WoodenFenceMat}.
     */
    public static WoodenFenceMat getWoodenFence(final WoodTypeMat woodType)
    {
        return types.get(woodType);
    }

    /**
     * Register new wood type to one of fences, like OAK to OAK_FENCE.
     *
     * @param type type of wood.
     * @param mat  fence material.
     */
    public static void registerWoodType(final WoodTypeMat type, final WoodenFenceMat mat)
    {
        types.put(type, mat);
    }

    static
    {
        registerWoodType(WoodTypeMat.OAK, OAK_FENCE);
        registerWoodType(WoodTypeMat.SPRUCE, SPRUCE_FENCE);
        registerWoodType(WoodTypeMat.BIRCH, BIRCH_FENCE);
        registerWoodType(WoodTypeMat.JUNGLE, JUNGLE_FENCE);
        registerWoodType(WoodTypeMat.ACACIA, ACACIA_FENCE);
        registerWoodType(WoodTypeMat.DARK_OAK, DARK_OAK_FENCE);
    }
}
