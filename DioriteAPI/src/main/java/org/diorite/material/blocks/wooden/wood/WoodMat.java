package org.diorite.material.blocks.wooden.wood;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.blocks.wooden.WoodTypeMat;
import org.diorite.material.blocks.wooden.WoodenMat;

/**
 * Abstract class for all Wood-based blocks
 */
public abstract class WoodMat extends WoodenMat
{
    protected final WoodTypeMat woodType;

    protected WoodMat(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final WoodTypeMat woodType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, typeName, type, hardness, blastResistance);
        this.woodType = woodType;
    }

    protected WoodMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodTypeMat woodType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.woodType = woodType;
    }

    public WoodTypeMat getWoodType()
    {
        return this.woodType;
    }

    public abstract WoodMat getWoodType(WoodTypeMat woodType);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("woodType", this.woodType).toString();
    }
}
