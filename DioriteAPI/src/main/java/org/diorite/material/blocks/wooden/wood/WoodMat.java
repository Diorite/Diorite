package org.diorite.material.blocks.wooden.wood;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.WoodType;
import org.diorite.material.blocks.wooden.WoodenMat;

/**
 * Abstract class for all Wood-based blocks
 */
public abstract class WoodMat extends WoodenMat
{
    protected final WoodType woodType;

    protected WoodMat(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final WoodType woodType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, typeName, type, hardness, blastResistance);
        this.woodType = woodType;
    }

    protected WoodMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final WoodType woodType, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.woodType = woodType;
    }

    /**
     * Returns wood type of this block.
     *
     * @return wood type of this block.
     */
    public WoodType getWoodType()
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
    public abstract WoodMat getWoodType(WoodType woodType);

    @Override
    public abstract WoodMat getType(final int type);

    @Override
    public abstract WoodMat getType(final String type);

    @Override
    public abstract WoodMat[] types();

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("woodType", this.woodType).toString();
    }
}
