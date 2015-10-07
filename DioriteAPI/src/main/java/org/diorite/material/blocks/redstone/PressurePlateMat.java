package org.diorite.material.blocks.redstone;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.PowerableMat;

/**
 * Base abstract class for all pressure plate based blocks.
 */
public abstract class PressurePlateMat extends BlockMaterialData implements PowerableMat
{
    protected final boolean powered;

    protected PressurePlateMat(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final boolean powered, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, typeName, type, hardness, blastResistance);
        this.powered = powered;
    }

    protected PressurePlateMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final boolean powered, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, hardness, blastResistance);
        this.powered = powered;
    }

    @Override
    public boolean isPowered()
    {
        return this.powered;
    }

    @Override
    public abstract PressurePlateMat getType(final int type);

    @Override
    public abstract PressurePlateMat getType(final String type);

    @Override
    public abstract PressurePlateMat[] types();

    @Override
    public abstract PressurePlateMat getPowered(final boolean powered);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("powered", this.powered).toString();
    }
}
