package org.diorite.material.blocks.plants;

/**
 * Base abstract class for steam-based blocks
 */
public abstract class PlantStem extends Crops
{
    public PlantStem(final String enumName, final int id, final String minecraftId, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, typeName, type);
    }

    public PlantStem(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
    }
}
