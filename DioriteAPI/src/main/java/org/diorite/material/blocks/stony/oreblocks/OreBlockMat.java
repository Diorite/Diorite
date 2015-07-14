package org.diorite.material.blocks.stony.oreblocks;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.blocks.stony.StonyMat;
import org.diorite.material.blocks.stony.ore.OreMat;

/**
 * Abstract class for all OreBlock-based blocks.
 */
public abstract class OreBlockMat extends StonyMat
{
    // TODO: add related item, like IronBlock -> IronIngot
    private final OreMat ore;

    protected OreBlockMat(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final OreMat ore, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, typeName, type, hardness, blastResistance);
        this.ore = ore;
    }

    protected OreBlockMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final OreMat ore, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.ore = ore;
    }

    public OreMat getOre()
    {
        return this.ore;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("ore", this.ore).toString();
    }
}
