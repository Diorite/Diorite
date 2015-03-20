package org.diorite.impl.multithreading;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.entity.EntityImpl;
import org.diorite.BlockLocation;

public class BlockBreakAction
{
    private final BlockLocation location;
    private final EntityImpl entity;

    public BlockBreakAction(final BlockLocation location, final EntityImpl entity)
    {
        this.location = location;
        this.entity = entity;
    }

    public BlockLocation getLocation()
    {
        return this.location;
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
        if (! (o instanceof BlockBreakAction))
        {
            return false;
        }

        final BlockBreakAction that = (BlockBreakAction) o;

        return ! ((this.entity != null) ? ! this.entity.equals(that.entity) : (that.entity != null)) && this.location.equals(that.location);
    }

    @Override
    public int hashCode()
    {
        int result = this.location.hashCode();
        result = (31 * result) + ((this.entity != null) ? this.entity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("location", this.location).append("entity", this.entity).toString();
    }
}
