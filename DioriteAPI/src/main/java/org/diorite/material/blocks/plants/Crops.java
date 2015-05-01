package org.diorite.material.blocks.plants;

import org.diorite.material.blocks.AgeableBlock;

/**
 * Base abstract class for crops-based blocks
 */
public abstract class Crops extends Plant implements AgeableBlock
{
    public Crops(final String enumName, final int id, final String minecraftId, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    public Crops(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }
}