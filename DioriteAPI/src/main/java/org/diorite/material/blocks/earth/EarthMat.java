package org.diorite.material.blocks.earth;

import org.diorite.material.BlockMaterialData;

public abstract class EarthMat extends BlockMaterialData
{
    protected EarthMat(final String enumName, final int id, final String minecraftId, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected EarthMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }
}
