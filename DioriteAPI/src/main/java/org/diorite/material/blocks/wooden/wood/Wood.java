package org.diorite.material.blocks.wooden.wood;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.blocks.wooden.WoodType;
import org.diorite.material.blocks.wooden.Wooden;

public abstract class Wood extends Wooden
{
    protected final WoodType woodType;

    public Wood(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final WoodType woodType)
    {
        super(enumName, id, minecraftId, typeName, type);
        this.woodType = woodType;
    }

    public Wood(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodType woodType)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.woodType = woodType;
    }

    public WoodType getWoodType()
    {
        return this.woodType;
    }

    public abstract Wood getWoodType(WoodType woodType);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("woodType", this.woodType).toString();
    }
}
