package org.diorite.impl;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.GameObject;

public abstract class GameObjectImpl implements GameObject
{
    protected final UUID uniqueID;

    public GameObjectImpl(final UUID uuid)
    {
        this.uniqueID = uuid;
    }

    @Override
    public UUID getUniqueID()
    {
        return this.uniqueID;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof GameObjectImpl))
        {
            return false;
        }

        final GameObjectImpl that = (GameObjectImpl) o;

        return this.uniqueID.equals(that.uniqueID);

    }

    @Override
    public int hashCode()
    {
        return this.uniqueID.hashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("uniqueID", this.uniqueID).toString();
    }
}
