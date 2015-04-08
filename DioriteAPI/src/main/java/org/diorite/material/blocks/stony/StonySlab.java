package org.diorite.material.blocks.stony;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.blocks.Slab;
import org.diorite.material.blocks.SlabType;

public abstract class StonySlab extends Stony implements Slab
{
    protected final SlabType      slabType;
    protected final StoneSlabType stoneType;

    public StonySlab(final String enumName, final int id, final String minecraftId, final String typeName, final SlabType slabType, final StoneSlabType stoneType)
    {
        super(enumName, id, minecraftId, typeName, combine(slabType, stoneType));
        this.slabType = slabType;
        this.stoneType = stoneType;
    }

    public StonySlab(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final SlabType slabType, final StoneSlabType stoneType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, combine(slabType, stoneType));
        this.slabType = slabType;
        this.stoneType = stoneType;
    }

    @SuppressWarnings("MagicNumber")
    protected static byte combine(final SlabType slabType, final StoneSlabType stoneType)
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
    public SlabType getSlabType()
    {
        return this.slabType;
    }

    public StoneSlabType getStoneType()
    {
        return this.stoneType;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("slabType", this.slabType).append("stoneType", this.stoneType).toString();
    }
}
