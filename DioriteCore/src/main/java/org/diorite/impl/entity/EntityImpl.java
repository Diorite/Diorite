package org.diorite.impl.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.ImmutableLocation;
import org.diorite.entity.Entity;
import org.diorite.impl.GameObjectImpl;
import org.diorite.impl.ServerImpl;
import org.diorite.impl.map.world.WorldImpl;

public abstract class EntityImpl extends GameObjectImpl implements Entity
{
    protected final ServerImpl server;
    protected final int        id;
    protected       WorldImpl  world;
    protected       double     x;
    protected       double     y;
    protected       double     z;
    protected       float      yaw;
    protected       float      pitch;
    protected       double     lastX;
    protected       double     lastY;
    protected       double     lastZ;
    protected       float      lastYaw;
    protected       float      lastPitch;

    protected EntityImpl(final ServerImpl server, final int id, final ImmutableLocation location)
    {
        super(null);
        this.server = server;
        this.id = id;
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        this.world = (WorldImpl) location.getWorld();
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

    @Override
    public ServerImpl getServer()
    {
        return this.server;
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

    public void setPositionAndRotation(final double modX, final double modY, final double modZ, final float modYaw, final float modPitch)
    {
        this.setPosition(modX, modY, modZ);
        this.setRotation(modYaw, modPitch);
    }

    public void setPosition(final double modX, final double modY, final double modZ)
    {
        this.lastX = this.x;
        this.lastY = this.y;
        this.lastZ = this.z;
        this.x = modX;
        this.y = modY;
        this.z = modZ;
    }

    public void setRotation(final float modYaw, final float modPitch)
    {
        this.lastYaw = this.yaw;
        this.lastPitch = this.pitch;
        this.yaw = modYaw;
        this.pitch = modPitch;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("server", this.server.getServerName()).append("id", this.id).append("world", this.world).append("x", this.x).append("y", this.y).append("z", this.z).toString();
    }
}
