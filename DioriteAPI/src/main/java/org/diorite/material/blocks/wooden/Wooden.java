package org.diorite.material.blocks.wooden;

import org.diorite.material.BlockMaterialData;

public abstract class Wooden extends BlockMaterialData
{
    public Wooden(final String enumName, final int id, final String minecraftId, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    public Wooden(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }
}
