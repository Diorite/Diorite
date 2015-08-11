package org.diorite.impl.world;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.GameObjectImpl;
import org.diorite.impl.Tickable;
import org.diorite.BlockLocation;
import org.diorite.world.TileEntity;

public abstract class TileEntityImpl extends GameObjectImpl implements TileEntity, Tickable
{
    private final BlockLocation location;

    public TileEntityImpl(final UUID uuid, final BlockLocation location)
    {
        super(uuid);
        this.location = location;
    }

    public TileEntityImpl(final BlockLocation location)
    {
        super(UUID.randomUUID());
        this.location = location;
    }

    @Override
    public BlockLocation getLocation()
    {
        return this.location;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("location", this.location).toString();
    }
}
