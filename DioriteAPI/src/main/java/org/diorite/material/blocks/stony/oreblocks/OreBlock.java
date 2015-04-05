package org.diorite.material.blocks.stony.oreblocks;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.blocks.stony.Stony;
import org.diorite.material.blocks.stony.ore.Ore;

public abstract class OreBlock extends Stony
{
    // TODO: add related item, like IronBlock -> IronIngot
    private final Ore ore;

    public OreBlock(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final Ore ore)
    {
        super(enumName, id, minecraftId, typeName, type);
        this.ore = ore;
    }

    public OreBlock(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final Ore ore)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type);
        this.ore = ore;
    }

    public Ore getOre()
    {
        return this.ore;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("ore", this.ore).toString();
    }
}
