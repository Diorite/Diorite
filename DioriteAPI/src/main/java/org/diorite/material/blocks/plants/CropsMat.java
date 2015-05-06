package org.diorite.material.blocks.plants;

import org.diorite.material.blocks.AgeableBlockMat;

/**
 * Base abstract class for crops-based blocks
 */
public abstract class CropsMat extends PlantMat implements AgeableBlockMat
{
    protected CropsMat(final String enumName, final int id, final String minecraftId, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected CropsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }
}
