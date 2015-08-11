package org.diorite.material.blocks.earth;

import org.diorite.material.BlockMaterialData;

public abstract class EarthMat extends BlockMaterialData
{
    protected EarthMat(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, typeName, type, hardness, blastResistance);
    }

    protected EarthMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }
}
