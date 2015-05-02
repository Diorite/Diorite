package org.diorite.material.blocks.wooden;

import org.diorite.material.BlockMaterialData;

/**
 * Abstract class for all Wooden-base blocks.
 */
public abstract class WoodenMat extends BlockMaterialData
{
    protected WoodenMat(final String enumName, final int id, final String minecraftId, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected WoodenMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }
}
