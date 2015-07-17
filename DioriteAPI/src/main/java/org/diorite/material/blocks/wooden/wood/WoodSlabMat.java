package org.diorite.material.blocks.wooden.wood;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.blocks.SlabMat;
import org.diorite.material.blocks.SlabTypeMat;
import org.diorite.material.WoodTypeMat;

/**
 * Abstract class for all WoodSlab-based blocks
 */
public abstract class WoodSlabMat extends WoodMat implements SlabMat
{
    protected final SlabTypeMat slabType;

    protected WoodSlabMat(final String enumName, final int id, final String minecraftId, final String typeName, final WoodTypeMat woodType, final SlabTypeMat slabType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, typeName, combine(woodType, slabType), woodType, hardness, blastResistance);
        this.slabType = slabType;
    }

    protected WoodSlabMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodTypeMat woodType, final SlabTypeMat slabType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, woodType, hardness, blastResistance);
        this.slabType = slabType;
    }

    protected static byte combine(final WoodTypeMat woodType, final SlabTypeMat slabType)
    {
        byte result = woodType.getPlanksMeta();
        if (slabType != SlabTypeMat.SMOOTH_FULL) // there is no wooden smooth full blocks
        {
            result |= slabType.getFlag();
        }
        return result;
    }

    @Override
    public SlabTypeMat getSlabType()
    {
        return this.slabType;
    }

    @Override
    public WoodSlabMat getSlab(final SlabTypeMat type)
    {
        return getWoodSlab(this.woodType, type);
    }

    /**
     * Return one of WoodSlab sub-type, based on {@link WoodTypeMat} and {@link SlabTypeMat}.
     *
     * @param woodType type of wood.
     * @param slabType type of slab.
     *
     * @return sub-type of WoodSlab
     */
    public WoodSlabMat getType(final WoodTypeMat woodType, final SlabTypeMat slabType)
    {
        return getWoodSlab(woodType, slabType);
    }

    /**
     * Return one of WoodSlab sub-type, based on {@link WoodTypeMat} and {@link SlabTypeMat}.
     * It will never return null.
     *
     * @param woodType type of wood.
     * @param slabType type of slab.
     *
     * @return sub-type of WoodSlab
     */
    public static WoodSlabMat getWoodSlab(final WoodTypeMat woodType, final SlabTypeMat slabType)
    {
        switch (slabType)
        {
            case BOTTOM:
            case UPPER:
                return WoodenSlabMat.getByID(combine(woodType, slabType));
            case FULL:
            case SMOOTH_FULL:
                return DoubleWoodenSlabMat.getByID(combine(woodType, slabType));
            default:
                return WoodenSlabMat.getByID(combine(woodType, slabType));
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("slabType", this.slabType).toString();
    }
}
