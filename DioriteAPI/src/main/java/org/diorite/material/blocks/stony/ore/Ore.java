package org.diorite.material.blocks.stony.ore;

import org.diorite.material.blocks.stony.Stony;

public abstract class Ore extends Stony
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
