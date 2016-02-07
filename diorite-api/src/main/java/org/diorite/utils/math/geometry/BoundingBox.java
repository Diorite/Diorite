/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

import javax.vecmath.Vector3d;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BoundingBox implements Cloneable
{
    protected final Vector3d min = new Vector3d();
    protected final Vector3d max = new Vector3d();

    public Vector3d getSize()
    {
        return new Vector3d(this.max.x - this.min.x, this.max.y - this.min.y, this.max.z - this.min.z);
    }

    public Vector3d getMin()
    {
        return this.min;
    }

    public Vector3d getMax()
    {
        return this.max;
    }

    public final boolean intersects(final BoundingBox other)
    {
        return intersects(this, other);
    }

    public static boolean intersects(final BoundingBox a, final BoundingBox b)
    {
        if ((b == null) || (a == null)) // TODO maybe error?
        {
            return false;
        }
        final Vector3d minA = a.min;
        final Vector3d maxA = a.max;
        final Vector3d minB = b.min;
        final Vector3d maxB = b.max;
        return ((maxA.x >= minB.x) && (minA.x <= maxB.x) && (maxA.y >= minB.y) && (minA.y <= maxB.y) && (maxA.z >= minB.z) && (minA.z <= maxB.z));
    }

    public static BoundingBox fromCorners(final Vector3d a, final Vector3d b)
    {
        final BoundingBox box = new BoundingBox();
        box.min.setX(Math.min(a.x, b.x));
        box.min.setY(Math.min(a.y, b.y));
        box.min.setZ(Math.min(a.z, b.z));
        box.max.setX(Math.max(a.x, b.x));
        box.max.setY(Math.max(a.y, b.y));
        box.max.setZ(Math.max(a.z, b.z));
        return box;
    }

    public final BoundingBox grow(final double x, final double y, final double z)
    {
        final BoundingBox bb = new BoundingBox();
        bb.min.setX(this.min.x - x);
        bb.min.setY(this.min.y - y);
        bb.min.setZ(this.min.z - z);
        bb.max.setX(this.max.x + x);
        bb.max.setY(this.max.y + y);
        bb.max.setZ(this.max.z + z);
        return bb;
    }

    public static BoundingBox fromPositionAndSize(final Vector3d pos, final Vector3d size)
    {
        final BoundingBox box = new BoundingBox();
        box.min.set(pos);
        box.max.set(pos.x + size.x, pos.y + size.y, pos.z + size.z);
        return box;
    }

    public static BoundingBox copyOf(final BoundingBox original)
    {
        final BoundingBox box = new BoundingBox();
        box.min.set(original.min);
        box.max.set(original.max);
        return box;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("min", this.min).append("max", this.max).toString();
    }
}
