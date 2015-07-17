package org.diorite.material.blocks.wooden.wood;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.WoodTypeMat;
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

    /**
     * Returns wood type of this block.
     *
     * @return wood type of this block.
     */
    public WoodTypeMat getWoodType()
    {
        return this.woodType;
    }

    /**
     * Returns block made of selected wood type related to this block. Like other doors.
     *
     * @param woodType type of wood.
     *
     * @return block made of selected wood type.
     */
    public abstract WoodMat getWoodType(WoodTypeMat woodType);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("woodType", this.woodType).toString();
    }
}
