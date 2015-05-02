package org.diorite.material.blocks.loose;

import org.diorite.material.BlockMaterialData;

public abstract class LooseMat extends BlockMaterialData
{
    protected LooseMat(final String enumName, final int id, final String minecraftId, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected LooseMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }
}
