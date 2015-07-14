package org.diorite.material.blocks.wooden.wood.fencegate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.blocks.FenceGateMat;
import org.diorite.material.blocks.wooden.WoodTypeMat;
import org.diorite.material.blocks.wooden.wood.WoodMat;

/**
 * Abstract class for all WoodenFenceGate-based blocks
 */
public abstract class WoodenFenceGateMat extends WoodMat implements FenceGateMat
{
    protected final BlockFace face;
    protected final boolean   open;

    protected WoodenFenceGateMat(final String enumName, final int id, final String minecraftId, final WoodTypeMat woodType, final BlockFace face, final boolean open, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, face.name() + (open ? "_OPEN" : ""), FenceGateMat.combine(face, open), woodType, hardness, blastResistance);
        this.face = face;
        this.open = open;
    }

    protected WoodenFenceGateMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodTypeMat woodType, final BlockFace face, final boolean open, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType, hardness, blastResistance);
        this.face = face;
        this.open = open;
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public boolean isOpen()
    {
        return this.open;
    }

    @Override
    public WoodenFenceGateMat getWoodType(final WoodTypeMat woodType)
    {
        switch (woodType)
        {
            case OAK:
                return OAK_FENCE_GATE;
            case SPRUCE:
                return SPRUCE_FENCE_GATE;
            case BIRCH:
                return BIRCH_FENCE_GATE;
            case JUNGLE:
                return JUNGLE_FENCE_GATE;
            case ACACIA:
                return ACACIA_FENCE_GATE;
            case DARK_OAK:
                return DARK_OAK_FENCE_GATE;
            default:
                return null;
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("open", this.open).toString();
    }

    /**
     * Returns sub-type of {@link WoodTypeMat} WoodenFenceGate, based on {@link BlockFace} and open state.
     *
     * @param woodType {@link WoodTypeMat} of WoodenFenceGate
     * @param face     facing direction of WoodenFenceGate
     * @param open     if gate should be open.
     *
     * @return sub-type of {@link WoodTypeMat} WoodenFenceGate.
     */
    public static WoodenFenceGateMat getWoodenFenceGate(final WoodTypeMat woodType, final BlockFace face, final boolean open)
    {
        switch (woodType)
        {
            case OAK:
                return OakFenceGateMat.getOakFenceGate(face, open);
            case SPRUCE:
                return SpruceFenceGateMat.getSpruceFenceGate(face, open);
            case BIRCH:
                return BirchFenceGateMat.getBirchFenceGate(face, open);
            case JUNGLE:
                return JungleFenceGateMat.getJungleFenceGate(face, open);
            case ACACIA:
                return AcaciaFenceGateMat.getAcaciaFenceGate(face, open);
            case DARK_OAK:
                return DarkOakFenceGateMat.getDarkOakFenceGate(face, open);
            default:
                return null;
        }
    }
}
