/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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

    public final BoundingBox grow(final double x, final double y, final double z)
    {
        final BoundingBox bb = new BoundingBox();
        bb.min.setX(this.min.getX() - x);
        bb.min.setY(this.min.getY() - y);
        bb.min.setZ(this.min.getZ() - z);
        bb.max.setX(this.max.getX() + x);
        bb.max.setY(this.max.getY() + y);
        bb.max.setZ(this.max.getZ() + z);
        return bb;
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
