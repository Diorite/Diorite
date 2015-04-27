package org.diorite.material.blocks.wooden.wood.fencegate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.blocks.FenceGate;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.material.blocks.wooden.wood.Wood;

public abstract class WoodenFenceGate extends Wood implements FenceGate
{
    protected final BlockFace face;
    protected final boolean   open;

    public WoodenFenceGate(final String enumName, final int id, final String minecraftId, final String typeName, final WoodType woodType, final BlockFace face, final boolean open)
    {
        super(enumName, id, minecraftId, typeName, FenceGate.combine(face, open), woodType);
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
    public WoodenFenceGate getWoodType(final WoodType woodType)
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
     * Returns sub-type of {@link WoodType} WoodenFenceGate, based on {@link BlockFace} and open state.
     *
     * @param woodType {@link WoodType} of WoodenFenceGate
     * @param face     facing direction of WoodenFenceGate
     * @param open     if gate should be open.
     *
     * @return sub-type of {@link WoodType} WoodenFenceGate.
     */
    public static WoodenFenceGate getWoodenFenceGate(final WoodType woodType, final BlockFace face, final boolean open)
    {
        switch (woodType)
        {
            case OAK:
                return OakFenceGate.getOakFenceGate(face, open);
            case SPRUCE:
                return SpruceFenceGate.getSpruceFenceGate(face, open);
            case BIRCH:
                return BirchFenceGate.getBirchFenceGate(face, open);
            case JUNGLE:
                return JungleFenceGate.getJungleFenceGate(face, open);
            case ACACIA:
                return AcaciaFenceGate.getAcaciaFenceGate(face, open);
            case DARK_OAK:
                return DarkOakFenceGate.getDarkOakFenceGate(face, open);
            default:
                return null;
        }
    }
}
