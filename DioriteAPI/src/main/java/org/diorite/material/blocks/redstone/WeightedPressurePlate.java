package org.diorite.material.blocks.redstone;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.blocks.ACPowerSource;

/**
 * Base abstract class for all weighted pressure plate based blocks.
 */
public abstract class WeightedPressurePlate extends PressurePlate implements ACPowerSource
{
    protected final int power;

    public WeightedPressurePlate(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final boolean activated)
    {
        super(enumName, id, minecraftId, typeName, type, activated);
        this.power = type;
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
