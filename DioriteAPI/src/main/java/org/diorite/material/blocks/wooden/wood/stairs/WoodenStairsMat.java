package org.diorite.material.blocks.wooden.wood.stairs;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.BlockFace;
import org.diorite.material.blocks.StairsMat;
import org.diorite.material.blocks.wooden.WoodTypeMat;
import org.diorite.material.blocks.wooden.wood.WoodMat;

/**
 * Abstract class for all WoodenStairs-based blocks
 */
public abstract class WoodenStairsMat extends WoodMat implements StairsMat
{
    protected final BlockFace face;
    protected final boolean   upsideDown;

    protected WoodenStairsMat(final String enumName, final int id, final String minecraftId, final WoodTypeMat woodType, final BlockFace face, final boolean upsideDown)
    {
        super(enumName, id, minecraftId, face.name() + (upsideDown ? "_UPSIDE_DOWN" : ""), (byte) 0, woodType);
        this.face = face;
        this.upsideDown = upsideDown;
    }

    protected WoodenStairsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodTypeMat woodType, final BlockFace face, final boolean upsideDown)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType);
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
    public WoodenStairsMat getWoodType(final WoodTypeMat woodType)
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
     * Returns sub-type of {@link WoodTypeMat} WoodenStairs, based on {@link BlockFace} and upside-down state.
     *
     * @param woodType   {@link WoodTypeMat} of WoodenStairs
     * @param face       facing direction of WoodenStairs
     * @param upsideDown if stairs should be upside-down.
     *
     * @return sub-type of {@link WoodTypeMat} WoodenStairs.
     */
    public static WoodenStairsMat getWoodenFenceGate(final WoodTypeMat woodType, final BlockFace face, final boolean upsideDown)
    {
        switch (woodType)
        {
            case OAK:
                return OakStairsMat.getOakStairs(face, upsideDown);
            case SPRUCE:
                return SpruceStairsMat.getSpruceStairs(face, upsideDown);
            case BIRCH:
                return BirchStairsMat.getBirchStairs(face, upsideDown);
            case JUNGLE:
                return JungleStairsMat.getJungleStairs(face, upsideDown);
            case ACACIA:
                return AcaciaStairsMat.getAcaciaStairs(face, upsideDown);
            case DARK_OAK:
                return DarkOakStairsMat.getDarkOakStairs(face, upsideDown);
            default:
                return null;
        }
    }
}
