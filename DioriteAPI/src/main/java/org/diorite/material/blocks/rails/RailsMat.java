package org.diorite.material.blocks.rails;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.BlockMaterialData;

public abstract class RailsMat extends BlockMaterialData
{
    protected final RailTypeMat railType;

    protected RailsMat(final String enumName, final int id, final String minecraftId, final String typeName, final RailTypeMat railType, final byte flags)
    {
        super(enumName, id, minecraftId, typeName, (byte) (railType.getFlag() | flags));
        this.railType = railType;
    }

    protected RailsMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final RailTypeMat railType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.railType = railType;
    }

    /**
     * @return type of Rails.
     */
    public RailTypeMat getRailType()
    {
        return this.railType;
    }

    /**
     * Returns sub-type of Rails based on {@link RailTypeMat} state.
     *
     * @param railType {@link RailTypeMat} of Rails,
     *
     * @return sub-type of Rails
     */
    public abstract RailsMat getRailType(RailTypeMat railType);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("railType", this.railType).toString();
    }
}
