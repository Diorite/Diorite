package org.diorite.material.blocks.redstone;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.blocks.ChangeablePowerElementMat;

/**
 * Base abstract class for all weighted pressure plate based blocks.
 */
public abstract class WeightedPressurePlateMat extends PressurePlateMat implements ChangeablePowerElementMat
{
    protected final int power;

    protected WeightedPressurePlateMat(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final boolean powered)
    {
        super(enumName, id, minecraftId, typeName, type, powered);
        this.power = type;
    }

    protected WeightedPressurePlateMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final boolean powered, final int power)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, powered);
        this.power = power;
    }

    @Override
    public int getPowerStrength()
    {
        return this.power;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("power", this.power).toString();
    }
}
