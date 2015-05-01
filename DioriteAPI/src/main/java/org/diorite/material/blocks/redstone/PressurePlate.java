package org.diorite.material.blocks.redstone;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.material.BlockMaterialData;
import org.diorite.material.blocks.Activatable;

/**
 * Base abstract class for all pressure plate based blocks.
 */
public abstract class PressurePlate extends BlockMaterialData implements Activatable
{
    protected final boolean activated;

    public PressurePlate(final String enumName, final int id, final String minecraftId, final String typeName, final byte type, final boolean activated)
    {
        super(enumName, id, minecraftId, typeName, type);
        this.activated = activated;
    }

    @Override
    public boolean isActivated()
    {
        return this.activated;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("activated", this.activated).toString();
    }
}
