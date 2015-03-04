package diorite;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.utils.DioriteMathUtils;

public class Location
{
    public static final Location ZERO = new Location(0, 0, 0);
    private double x;
    private double y;
    private double z;
    private float  yaw;
    private float  pitch;

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

    public double getX()
    {
        return this.x;
    }

    public void setX(final double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return this.y;
    }

    public void setY(final double y)
    {
        this.y = y;
    }

    public double getZ()
    {
        return this.z;
    }

    public void setZ(final double z)
    {
        this.z = z;
    }

    public float getPitch()
    {
        return this.pitch;
    }

    public void setPitch(final float pitch)
    {
        this.pitch = pitch;
    }

    public float getYaw()
    {
        return this.yaw;
    }

    public void setYaw(final float yaw)
    {
        this.yaw = yaw;
    }

    public double length()
    {
        return Math.sqrt(this.lengthSquared());
    }

    public double lengthSquared()
    {
        return DioriteMathUtils.square(this.x) + DioriteMathUtils.square(this.y) + DioriteMathUtils.square(this.z);
    }

    public double distance(final double x, final double y, final double z)
    {
        return Math.sqrt(this.distanceSquared(x, y, z));
    }

    public double distanceFromCenter(final double x, final double y, final double z)
    {
        return Math.sqrt(this.distanceSquaredFromCenter(x, y, z));
    }

    public double distance(final Location location)
    {
        return Math.sqrt(this.distanceSquared(location));
    }

    public double distanceSquared(final double x, final double y, final double z)
    {
        final double deltaX = this.x - x;
        final double deltaY = this.y - y;
        final double deltaZ = this.z - z;
        return DioriteMathUtils.square(deltaX) + DioriteMathUtils.square(deltaY) + DioriteMathUtils.square(deltaZ);
    }

    public double distanceSquaredFromCenter(final double x, final double y, final double z)
    {
        final double deltaX = (this.x + 0.5) - x;
        final double deltaY = (this.y + 0.5) - y;
        final double deltaZ = (this.z + 0.5) - z;
        return DioriteMathUtils.square(deltaX) + DioriteMathUtils.square(deltaY) + DioriteMathUtils.square(deltaZ);
    }

    public double distanceSquared(final Location location)
    {
        return this.distanceSquared(location.getX(), location.getY(), location.getZ());
    }

    public Location crossProduct(final Location location)
    {
        return new Location((this.y * location.getZ()) - (this.z * location.getY()), (this.z * location.getX()) - (this.x * location.getZ()), (this.x * location.getY()) - (this.y * location.getX()));
    }

    public boolean isInAABB(final Location min, final Location max)
    {
        return (this.x >= min.x) && (this.x <= max.x) && (this.y >= min.y) && (this.y <= max.y) && (this.z >= min.z) && (this.z <= max.z);
    }

    public boolean isInSphere(final Location origin, final double radius)
    {
        return (DioriteMathUtils.square(origin.x - this.x) + DioriteMathUtils.square(origin.y - this.y) + DioriteMathUtils.square(origin.z - this.z)) <= DioriteMathUtils.square(radius);
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
