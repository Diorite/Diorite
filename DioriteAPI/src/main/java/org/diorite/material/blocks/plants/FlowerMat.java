package org.diorite.material.blocks.plants;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Abstract class for flower-based blocks
 */
public abstract class FlowerMat extends PlantMat
{
    protected final FlowerTypeMat flowerType;

    protected FlowerMat(final String enumName, final int id, final String minecraftId, final byte type, final FlowerTypeMat flowerType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, flowerType.name(), type, hardness, blastResistance);
        this.flowerType = flowerType;
    }

    protected FlowerMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final FlowerTypeMat flowerType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.flowerType = flowerType;
    }

    /**
     * @return type of flower.
     */
    public FlowerTypeMat getFlowerType()
    {
        return this.flowerType;
    }

    /**
     * Returns one of flower sub-type based on {@link FlowerTypeMat}
     * If this flower don't supprot given type, it will return default one.
     *
     * @param flowerType type of flower
     *
     * @return sub-type of flower
     */
    public abstract FlowerMat getFlowerType(final FlowerTypeMat flowerType);

    @Override
    public abstract FlowerMat getType(final int type);

    @Override
    public abstract FlowerMat getType(final String type);

    @Override
    public abstract FlowerMat[] types();

    /**
     * Returns one of flower, based on {@link FlowerTypeMat}
     * It will never return null.
     *
     * @param flowerType type of flower.
     *
     * @return flower block type.
     */
    public static FlowerMat getFlower(final FlowerTypeMat flowerType)
    {
        switch (flowerType)
        {
            case POPPY:
                return FlowersMat.FLOWERS_POPPY;
            case BLUE_ORCHID:
                return FlowersMat.FLOWERS_BLUE_ORCHID;
            case ALLIUM:
                return FlowersMat.FLOWERS_ALLIUM;
            case AZURE_BLUET:
                return FlowersMat.FLOWERS_AZURE_BLUET;
            case RED_TULIP:
                return FlowersMat.FLOWERS_RED_TULIP;
            case ORANGE_TULIP:
                return FlowersMat.FLOWERS_ORANGE_TULIP;
            case WHITE_TULIP:
                return FlowersMat.FLOWERS_WHITE_TULIP;
            case PINK_TULIP:
                return FlowersMat.FLOWERS_PINK_TULIP;
            case OXEYE_DAISY:
                return FlowersMat.FLOWERS_OXEYE_DAISY;
            case SUNFLOWER:
                return DoubleFlowersMat.DOUBLE_FLOWERS_SUNFLOWER;
            case LILAC:
                return DoubleFlowersMat.DOUBLE_FLOWERS_LILAC;
            case TALL_GRASS:
                return DoubleFlowersMat.DOUBLE_FLOWERS_TALL_GRASS;
            case TALL_FERN:
                return DoubleFlowersMat.DOUBLE_FLOWERS_TALL_FERN;
            case ROSE_BUSH:
                return DoubleFlowersMat.DOUBLE_FLOWERS_ROSE_BUSH;
            case PEONY:
                return DoubleFlowersMat.DOUBLE_FLOWERS_PEONY;
            case DOUBLE_TOP:
                return DoubleFlowersMat.DOUBLE_FLOWERS_DOUBLE_TOP;
            case GRASS:
                return TallGrassMat.TALL_GRASS_GRASS;
            case SHRUB:
                return TallGrassMat.TALL_GRASS_SHRUB;
            case FERN:
                return TallGrassMat.TALL_GRASS_FERN;
            case DANDELION:
                return DandelionMat.DANDELION;
            case DEAD_BUSH:
                return DeadBushMat.DEAD_BUSH;
            default:
                return DandelionMat.DANDELION;
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("flowerType", this.flowerType).toString();
    }
}
