package org.diorite.impl.multithreading;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.entity.EntityImpl;
import org.diorite.BlockLocation;
import org.diorite.material.BlockMaterialData;

public class BlockPlaceAction
{
    private final BlockLocation     location;
    private final BlockMaterialData placed;
    private final EntityImpl        entity;

    public BlockPlaceAction(final BlockLocation location, final BlockMaterialData placed, final EntityImpl entity)
    {
        this.location = location;
        this.placed = placed;
        this.entity = entity;
    }

    public BlockLocation getLocation()
    {
        return this.location;
    }

    public BlockMaterialData getPlaced()
    {
        return this.placed;
    }

    public EntityImpl getEntity()
    {
        return this.entity;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof BlockPlaceAction))
        {
            return false;
        }

        final BlockPlaceAction that = (BlockPlaceAction) o;

        return ! ((this.entity != null) ? ! this.entity.equals(that.entity) : (that.entity != null)) && this.location.equals(that.location) && this.placed.equals(that.placed);

    }

    @Override
    public int hashCode()
    {
        int result = this.location.hashCode();
        result = (31 * result) + this.placed.hashCode();
        result = (31 * result) + ((this.entity != null) ? this.entity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("location", this.location).append("placed", this.placed).append("entity", this.entity).toString();
    }
}
