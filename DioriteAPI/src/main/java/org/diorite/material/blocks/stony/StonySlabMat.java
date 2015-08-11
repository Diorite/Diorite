package org.diorite.material.blocks.stony;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.blocks.SlabMat;
import org.diorite.material.blocks.SlabTypeMat;

public abstract class StonySlabMat extends StonyMat implements SlabMat
{
    protected final SlabTypeMat      slabType;
    protected final StoneSlabTypeMat stoneType;

    protected StonySlabMat(final String enumName, final int id, final String minecraftId, final String typeName, final SlabTypeMat slabType, final StoneSlabTypeMat stoneType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, typeName, combine(slabType, stoneType), hardness, blastResistance);
        this.slabType = slabType;
        this.stoneType = stoneType;
    }

    protected StonySlabMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final SlabTypeMat slabType, final StoneSlabTypeMat stoneType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.slabType = slabType;
        this.stoneType = stoneType;
    }

    @SuppressWarnings("MagicNumber")
    protected static byte combine(final SlabTypeMat slabType, final StoneSlabTypeMat stoneType)
    {
        byte result = slabType.getFlag();
        result |= stoneType.getFlag();
        if (stoneType.isSecondStoneSlabID())
        {
            result += 16;
        }
        return result;
    }

    @Override
    public SlabTypeMat getSlabType()
    {
        return this.slabType;
    }

    /**
     * @return {@link StoneSlabTypeMat} type of slab.
     */
    public StoneSlabTypeMat getStoneType()
    {
        return this.stoneType;
    }

    /**
     * Return one of StonySlab sub-type, based on {@link SlabTypeMat} and {@link StoneSlabTypeMat}.
     * It will never return null.
     *
     * @param slabType  type of slab.
     * @param stoneType type of stone slab.
     *
     * @return sub-type of StonySlab
     */
    public StonySlabMat getType(final SlabTypeMat slabType, final StoneSlabTypeMat stoneType)
    {
        return getStonySlab(slabType, stoneType);
    }

    @Override
    public StonySlabMat getSlab(final SlabTypeMat type)
    {
        switch (type)
        {
            case BOTTOM:
            case UPPER:
                return StoneSlabMat.getByID(combine(type, this.stoneType));
            case FULL:
            case SMOOTH_FULL:
                return DoubleStoneSlabMat.getByID(combine(type, this.stoneType));
            default:
                return StoneSlabMat.getByID(combine(type, this.stoneType));
        }
    }

    /**
     * Return one of StonySlab sub-type, based on {@link SlabTypeMat} and {@link StoneSlabTypeMat}.
     * It will never return null.
     *
     * @param slabType  type of slab.
     * @param stoneType type of stone slab.
     *
     * @return sub-type of StonySlab
     */
    public static StonySlabMat getStonySlab(final SlabTypeMat slabType, final StoneSlabTypeMat stoneType)
    {
        switch (slabType)
        {
            case BOTTOM:
            case UPPER:
                return StoneSlabMat.getByID(combine(slabType, stoneType));
            case FULL:
            case SMOOTH_FULL:
                return DoubleStoneSlabMat.getByID(combine(slabType, stoneType));
            default:
                return StoneSlabMat.getByID(combine(slabType, stoneType));
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("slabType", this.slabType).append("stoneType", this.stoneType).toString();
    }
}
