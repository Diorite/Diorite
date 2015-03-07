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
    protected          WorldImpl  world;
    protected          double     x;
    protected          double     y;
    protected          double     z;
    protected          float      yaw;
    protected          float      pitch;
    protected          double     lastX;
    protected          double     lastY;
    protected          double     lastZ;
    protected          float      lastYaw;
    protected          float      lastPitch;

    protected EntityImpl(final ServerImpl server, final int id, final ImmutableLocation location)
    {
        this.server = server;
        this.id = id;
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        this.world = (WorldImpl) location.getWorld();
        this.move(0,0,0,0,0);
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
        return new ImmutableLocation(this.x, this.y, this.z, this.yaw, this.pitch, this.world);
    }

    public ImmutableLocation getLastLocation()
    {
        return new ImmutableLocation(this.lastX, this.lastY, this.lastZ, this.lastYaw, this.lastPitch);
    }

    public void move(final double modX, final double modY, final double modZ, final float modYaw, final float modPitch)
    {
        this.lastX = this.x;
        this.lastY = this.y;
        this.lastZ = this.z;
        this.lastYaw = this.yaw;
        this.lastPitch = this.pitch;
        this.x += modX;
        this.y += modY;
        this.z += modZ;
        this.yaw += modYaw;
        this.pitch += modPitch;
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
