package org.diorite.material.blocks.stony.ore;

import org.diorite.material.blocks.stony.StonyMat;

/**
 * Abstract class for all ore-based blocks
 */
public abstract class OreMat extends StonyMat
{
    protected OreMat(final String enumName, final int id, final String minecraftId, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    protected OreMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }
}
