package org.diorite.material.blocks.plants;

/**
 * Base abstract class for steam-based blocks
 */
public abstract class PlantStemMat extends CropsMat
{
    protected PlantStemMat(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, typeName, type, hardness, blastResistance);
    }

    protected PlantStemMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
    }

    @Override
    public abstract PlantStemMat getType(final int type);

    @Override
    public abstract PlantStemMat getType(final String type);

    @Override
    public abstract PlantStemMat[] types();

    @Override
    public abstract PlantStemMat getAge(final int age);
}
