package org.diorite.material.blocks.wooden.wood.stairs;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.blocks.Stairs;
import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.material.blocks.wooden.wood.Wood;

public abstract class WoodenStairs extends Wood implements Stairs
{
    protected final BlockFace face;
    protected final boolean   upsideDown;

    public WoodenStairs(final String enumName, final int id, final String minecraftId, final String typeName, final WoodType woodType, final BlockFace face, final boolean upsideDown)
    {
        super(enumName, id, minecraftId, typeName, (byte) 0, woodType);
        this.face = face;
        this.upsideDown = upsideDown;
    }

    @Override
    public BlockFace getBlockFacing()
    {
        return this.face;
    }

    @Override
    public boolean isUpsideDown()
    {
        return this.upsideDown;
    }

    @Override
    public WoodenStairs getWoodType(final WoodType woodType)
    {
        switch (woodType)
        {
            case OAK:
                return OAK_STAIRS;
            case SPRUCE:
                return SPRUCE_STAIRS;
            case BIRCH:
                return BIRCH_STAIRS;
            case JUNGLE:
                return JUNGLE_STAIRS;
            case ACACIA:
                return ACACIA_STAIRS;
            case DARK_OAK:
                return DARK_OAK_STAIRS;
            default:
                return null;
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("face", this.face).append("upsideDown", this.upsideDown).toString();
    }

    /**
     * Returns sub-type of {@link WoodType} WoodenStairs, based on {@link BlockFace} and upside-down state.
     *
     * @param woodType   {@link WoodType} of WoodenStairs
     * @param face       facing direction of WoodenStairs
     * @param upsideDown if stairs should be upside-down.
     *
     * @return sub-type of {@link WoodType} WoodenStairs.
     */
    public static WoodenStairs getWoodenFenceGate(final WoodType woodType, final BlockFace face, final boolean upsideDown)
    {
        switch (woodType)
        {
            case OAK:
                return OakStairs.getOakStairs(face, upsideDown);
            case SPRUCE:
                return SpruceStairs.getSpruceStairs(face, upsideDown);
            case BIRCH:
                return BirchStairs.getBirchStairs(face, upsideDown);
            case JUNGLE:
                return JungleStairs.getJungleStairs(face, upsideDown);
            case ACACIA:
                return AcaciaStairs.getAcaciaStairs(face, upsideDown);
            case DARK_OAK:
                return DarkOakStairs.getDarkOakStairs(face, upsideDown);
            default:
                return null;
        }
    }
}
