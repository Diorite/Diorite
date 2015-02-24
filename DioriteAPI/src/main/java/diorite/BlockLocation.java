package diorite;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.utils.DioriteMathUtils;

public class BlockLocation implements Comparable<BlockLocation>
{
    public static       BlockLocation ZERO = new BlockLocation(0, 0, 0);
    public static final double        HALF = 0.5;
    private final int x;
    private final int y;
    private final int z;

    public BlockLocation(final int x, final int z, final int y)
    {
        this.x = x;
        this.z = z;
        this.y = y;
    }

    public int getX()
    {
        return this.x;
    }

    public int getZ()
    {
        return this.z;
    }

    public int getY()
    {
        return this.y;
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

    public double distance(final BlockLocation location)
    {
        return Math.sqrt(this.distanceSquared(location));
    }

    public double distanceSquared(final double x, final double y, final double z)
    {
        final double deltaX = (double) this.x - x;
        final double deltaY = (double) this.y - y;
        final double deltaZ = (double) this.z - z;
        return DioriteMathUtils.square(deltaX) + DioriteMathUtils.square(deltaY) + DioriteMathUtils.square(deltaZ);
    }

    public double distanceSquaredFromCenter(final double x, final double y, final double z)
    {
        final double deltaX = ((double) this.x + HALF) - x;
        final double deltaY = ((double) this.y + HALF) - y;
        final double deltaZ = ((double) this.z + HALF) - z;
        return DioriteMathUtils.square(deltaX) + DioriteMathUtils.square(deltaY) + DioriteMathUtils.square(deltaZ);
    }

    public double distanceSquared(final BlockLocation location)
    {
        return this.distanceSquared(location.getX(), location.getY(), location.getZ());
    }

    public BlockLocation crossProduct(final BlockLocation location)
    {
        return new BlockLocation((this.y * location.getZ()) - (this.z * location.getY()), (this.z * location.getX()) - (this.x * location.getZ()), (this.x * location.getY()) - (this.y * location.getX()));
    }

    public boolean isInAABB(final BlockLocation min, final BlockLocation max)
    {
        return (this.x >= min.x) && (this.x <= max.x) && (this.y >= min.y) && (this.y <= max.y) && (this.z >= min.z) && (this.z <= max.z);
    }

    public boolean isInSphere(final BlockLocation origin, final double radius)
    {
        return (DioriteMathUtils.square(origin.x - this.x) + DioriteMathUtils.square(origin.y - this.y) + DioriteMathUtils.square(origin.z - this.z)) <= DioriteMathUtils.square(radius);
    }


    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof BlockLocation))
        {
            return false;
        }

        final BlockLocation that = (BlockLocation) o;

        return (this.x == that.x) && (this.y == that.y) && (this.z == that.z);

    }

    @Override
    public int hashCode()
    {
        int result = this.x;
        result = (31 * result) + this.y;
        result = (31 * result) + this.z;
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("x", this.x).append("y", this.y).append("z", this.z).toString();
    }

    @Override
    public int compareTo(final BlockLocation other)
    {
        if (this.y == other.getY())
        {
            if (this.z == other.getZ())
            {
                return this.x - other.getX();
            }
            return this.z - other.getZ();
        }
        return this.y - other.getY();
    }
}
