package org.diorite.material.blocks.stony;

import org.diorite.material.BlockMaterialData;

/**
 * Abstract class for all stony based blocks.
 */
public abstract class StonyMat extends BlockMaterialData
{
    protected StonyMat(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, typeName, type, hardness, blastResistance);
    }

    protected StonyMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }
}
