package org.diorite.material.blocks.stony;

import org.diorite.material.BlockMaterialData;

public abstract class Stony extends BlockMaterialData
{
    public Stony(final String enumName, final int id, final String minecraftId, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    public Stony(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }
}
