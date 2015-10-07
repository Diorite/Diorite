package org.diorite.material.blocks.others;

import org.diorite.BlockFace;
import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.DirectionalMat;

/**
 * Base abstract class for all sign-based blocks.
 */
public abstract class SignBlockMat extends BlockMaterialData implements DirectionalMat
{
    protected SignBlockMat(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, typeName, type, hardness, blastResistance);
    }

    protected SignBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public abstract SignBlockMat getType(final int type);

    @Override
    public abstract SignBlockMat getType(final String type);

    @Override
    public abstract SignBlockMat[] types();

    @Override
    public abstract SignBlockMat getBlockFacing(final BlockFace face);
}
