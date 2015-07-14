package org.diorite.material.blocks.wooden.wood.fence;

import org.diorite.material.blocks.FenceMat;
import org.diorite.material.blocks.wooden.WoodTypeMat;
import org.diorite.material.blocks.wooden.wood.WoodMat;

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

    @Override
    public WoodenFenceMat getWoodType(final WoodTypeMat woodType)
    {
        switch (woodType)
        {
            case OAK:
                return OAK_FENCE;
            case SPRUCE:
                return SPRUCE_FENCE;
            case BIRCH:
                return BIRCH_FENCE;
            case JUNGLE:
                return JUNGLE_FENCE;
            case ACACIA:
                return ACACIA_FENCE;
            case DARK_OAK:
                return DARK_OAK_FENCE;
            default:
                return null;
        }
    }
}
