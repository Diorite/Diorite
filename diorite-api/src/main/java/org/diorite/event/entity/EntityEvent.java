package org.diorite.event.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Entity;
import org.diorite.event.Event;

public class EntityEvent extends Event
{
    protected final Entity entity;

    public EntityEvent(final Entity entity)
    {
        this.entity = entity;
    }

    public Entity getEntity()
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
        if (! (o instanceof EntityEvent))
        {
            return false;
        }

        final EntityEvent that = (EntityEvent) o;
        return this.entity.equals(that.entity);
    }

    @Override
    public int hashCode()
    {
        return this.entity.hashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("entity", this.entity).toString();
    }
}
