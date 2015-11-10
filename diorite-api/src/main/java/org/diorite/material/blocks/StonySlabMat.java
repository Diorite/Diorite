/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.material.blocks;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.SlabMat;
import org.diorite.material.SlabTypeMat;

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

    @Override
    public abstract StonySlabMat getType(final int type);

    @Override
    public abstract StonySlabMat getType(final String type);

    @Override
    public abstract StonySlabMat[] types();

    @Override
    public StonySlabMat getUpperPart()
    {
        return this.getSlab(SlabTypeMat.UPPER);
    }

    @Override
    public StonySlabMat getBottomPart()
    {
        return this.getSlab(SlabTypeMat.BOTTOM);
    }

    @Override
    public StonySlabMat getFullSlab()
    {
        return this.getSlab(SlabTypeMat.FULL);
    }

    @Override
    public StonySlabMat getFullSmoothSlab()
    {
        return this.getSlab(SlabTypeMat.SMOOTH_FULL);
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
