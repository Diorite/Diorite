package diorite.impl.entity;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.ImmutableLocation;
import diorite.entity.Entity;
import diorite.impl.ServerImpl;
import diorite.impl.map.world.WorldImpl;

public abstract class EntityImpl implements Entity
{
    protected final ServerImpl server;
    protected final int        id;
    protected       UUID       uniqueID;
    private         WorldImpl  world;
    private         double     x;
    private         double     y;
    private         double     z;
    private         float      yaw;
    private         float      pitch;

    protected EntityImpl(final ServerImpl server, final int id)
    {
        this.server = server;
        this.id = id;
    }

    public WorldImpl getWorld()
    {
        return this.world;
    }

    @Override
    public int getId()
    {
        return this.id;
    }

    @Override
    public UUID getUniqueID()
    {
        return this.uniqueID;
    }

    @Override
    public double getX()
    {
        return this.x;
    }

    @Override
    public double getZ()
    {
        return this.z;
    }

    @Override
    public double getY()
    {
        return this.y;
    }

    @Override
    public ImmutableLocation getLocation()
    {
        return new ImmutableLocation(this.x, this.y, this.z, this.yaw, this.pitch);
    }

    @Override
    public ServerImpl getServer()
    {
        return this.server;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("server", this.server.getServerName()).append("x", this.x).append("y", this.y).append("z", this.z).append("id", this.id).toString();
    }
}
