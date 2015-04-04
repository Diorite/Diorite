package org.diorite;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.world.World;

public class Location implements Loc
{
    public static final Location ZERO = new Location(0, 0, 0);
    private double x;
    private double y;
    private double z;
    private float  yaw;
    private float  pitch;
    private World  world;

    public Location(final double x, final double z, final double y)
    {
        this.x = x;
        this.z = z;
        this.y = y;
        this.yaw = 0;
        this.pitch = 0;
    }

    public Location(final double x, final double y, final double z, final float yaw, final float pitch)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public Location(final double x, final double y, final double z, final World world)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    public Location(final double x, final double y, final double z, final float yaw, final float pitch, final World world)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.world = world;
    }

    @Override
    public double getX()
    {
        return this.x;
    }

    public void setX(final double x)
    {
        this.x = x;
    }

    @Override
    public double getY()
    {
        return this.y;
    }

    public void setY(final double y)
    {
        this.y = y;
    }

    @Override
    public double getZ()
    {
        return this.z;
    }

    public void setZ(final double z)
    {
        this.z = z;
    }

    @Override
    public float getPitch()
    {
        return this.pitch;
    }

    public void setPitch(final float pitch)
    {
        this.pitch = pitch;
    }

    @Override
    public float getYaw()
    {
        return this.yaw;
    }

    public void setYaw(final float yaw)
    {
        this.yaw = yaw;
    }

    @Override
    public World getWorld()
    {
        return this.world;
    }

    public void setWorld(final World world)
    {
        this.world = world;
    }

    @Override
    public Location crossProduct(final Loc location)
    {
        return new Location((this.y * location.getZ()) - (this.z * location.getY()), (this.z * location.getX()) - (this.x * location.getZ()), (this.x * location.getY()) - (this.y * location.getX()));
    }

    @Override
    public Location crossProduct(final BlockLocation location)
    {
        return new Location((this.y * location.getZ()) - (this.z * location.getY()), (this.z * location.getX()) - (this.x * location.getZ()), (this.x * location.getY()) - (this.y * location.getX()));
    }

    @Override
    public int hashCode()
    {
        int result;
        long temp;
        temp = Double.doubleToLongBits(this.x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.y);
        result = (31 * result) + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(this.z);
        result = (31 * result) + (int) (temp ^ (temp >>> 32));
        result = (31 * result) + ((this.yaw != + 0.0f) ? Float.floatToIntBits(this.yaw) : 0);
        result = (31 * result) + ((this.pitch != + 0.0f) ? Float.floatToIntBits(this.pitch) : 0);
        return result;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof Location))
        {
            return false;
        }

        final Location that = (Location) o;

        return (this.x == that.x) && (this.y == that.y) && (this.z == that.z);

    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("x", this.x).append("y", this.y).append("z", this.z).append("yaw", this.yaw).append("pitch", this.pitch).toString();
    }
}
