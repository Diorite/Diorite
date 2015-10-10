package org.diorite.material.blocks.plants;

import org.diorite.material.blocks.AgeableBlockMat;

/**
 * Base abstract class for crops-based blocks
 */
@SuppressWarnings("JavaDoc")
public abstract class CropsMat extends PlantMat implements AgeableBlockMat
{
    protected CropsMat(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, typeName, type, hardness, blastResistance);
    }

    protected CropsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public abstract CropsMat getType(final int type);

    @Override
    public abstract CropsMat getType(final String type);

    @Override
    public abstract CropsMat[] types();

    @Override
    public abstract CropsMat getAge(final int age);
}
