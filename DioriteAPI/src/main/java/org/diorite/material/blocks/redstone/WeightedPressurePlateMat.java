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

    protected WeightedPressurePlateMat(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final boolean powered, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, typeName, type, powered, hardness, blastResistance);
        this.power = type;
    }

    protected WeightedPressurePlateMat(final String enumName, final int id, final String minecraftId, final int maxStack, final String typeName, final byte type, final boolean powered, final int power, final float hardness, final float blastResistance)
    {
        super(enumName, id, minecraftId, maxStack, typeName, type, powered, hardness, blastResistance);
        this.power = power;
    }

    @Override
    public int getPowerStrength()
    {
        return this.power;
    }

    @Override
    public abstract WeightedPressurePlateMat getType(final int type);

    @Override
    public abstract WeightedPressurePlateMat getType(final String type);

    @Override
    public abstract WeightedPressurePlateMat[] types();

    @Override
    public abstract WeightedPressurePlateMat getPowered(final boolean powered);

    @Override
    public abstract WeightedPressurePlateMat getPowerStrength(final int strength);

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("power", this.power).toString();
    }
}
