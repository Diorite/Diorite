package diorite.impl.entity;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.entity.Entity;
import diorite.impl.ServerImpl;

public abstract class EntityImpl implements Entity
{
    protected final ServerImpl server;
    protected final int      id;
    protected       UUID     uniqueID;
    private       double x;
    private       double y;
    private       double z;

    protected EntityImpl(final ServerImpl server, final int id)
    {
        this.server = server;
        this.id = id;
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
