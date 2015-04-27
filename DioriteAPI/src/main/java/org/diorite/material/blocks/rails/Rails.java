package org.diorite.material.blocks.rails;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.BlockMaterialData;

public abstract class Rails extends BlockMaterialData
{
    protected final RailType railType;

    public Rails(final String enumName, final int id, final String minecraftId, final String typeName, final RailType railType, final byte flags)
    {
        super(enumName, id, minecraftId, typeName, (byte) (railType.getFlag() | flags));
        this.railType = railType;
    }

    /**
     * @return type of Rails.
     */
    public RailType getRailType()
    {
        return this.railType;
    }

    /**
     * Returns sub-type of Rails based on {@link RailType} state.
     *
     * @param railType {@link RailType} of Rails,
     *
     * @return sub-type of Rails
     */
    public abstract Rails getRailType(RailType railType);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("railType", this.railType).toString();
    }
}
