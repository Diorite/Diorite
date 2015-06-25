package org.diorite.utils.math.geometry;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BoundingBox implements Cloneable
{
    protected final Vector min = new Vector();
    protected final Vector max = new Vector();

    public Vector getSize()
    {
        return this.max.clone().subtract(this.min);
    }

    public final boolean intersects(final BoundingBox other)
    {
        return intersects(this, other);
    }

    public static boolean intersects(final BoundingBox a, final BoundingBox b)
    {
        final Vector minA = a.min;
        final Vector maxA = a.max;
        final Vector minB = b.min;
        final Vector maxB = b.max;
        return ((maxA.getX() >= minB.getX()) && (minA.getX() <= maxB.getX()) && (maxA.getY() >= minB.getY()) && (minA.getY() <= maxB.getY()) && (maxA.getZ() >= minB.getZ()) && (minA.getZ() <= maxB.getZ()));
    }

    public static BoundingBox fromCorners(final Vector a, final Vector b)
    {
        final BoundingBox box = new BoundingBox();
        box.min.setX(Math.min(a.getX(), b.getX()));
        box.min.setY(Math.min(a.getY(), b.getY()));
        box.min.setZ(Math.min(a.getZ(), b.getZ()));
        box.max.setX(Math.max(a.getX(), b.getX()));
        box.max.setY(Math.max(a.getY(), b.getY()));
        box.max.setZ(Math.max(a.getZ(), b.getZ()));
        return box;
    }

    public static BoundingBox fromPositionAndSize(final Vector pos, final Vector size)
    {
        final BoundingBox box = new BoundingBox();
        box.min.copy(pos);
        box.max.copy(pos.clone().add(size));
        return box;
    }

    public static BoundingBox copyOf(final BoundingBox original)
    {
        final BoundingBox box = new BoundingBox();
        box.min.copy(original.min);
        box.max.copy(original.max);
        return box;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("min", this.min).append("max", this.max).toString();
    }
}
