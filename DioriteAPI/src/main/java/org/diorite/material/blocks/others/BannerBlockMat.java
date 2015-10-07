package org.diorite.material.blocks.others;

import org.diorite.BlockFace;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.DirectionalMat;

/**
 * Base abstract class for all banner-based blocks
 */
public abstract class BannerBlockMat extends BlockMaterialData implements DirectionalMat
{
    protected BannerBlockMat(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, typeName, type, hardness, blastResistance);
    }

    protected BannerBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public abstract BannerBlockMat getType(final int type);

    @Override
    public abstract BannerBlockMat getType(final String type);

    @Override
    public abstract BannerBlockMat[] types();

    @Override
    public abstract BannerBlockMat getBlockFacing(final BlockFace face);
}
