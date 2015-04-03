package org.diorite.material.blocks.ore;

import org.diorite.material.BlockMaterialData;

public abstract class Ore extends BlockMaterialData
{
    public Ore(final String enumName, final int id, final String minecraftId, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    public Ore(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }
}
