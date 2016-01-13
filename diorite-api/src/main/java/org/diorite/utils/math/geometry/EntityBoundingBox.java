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

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Entity;

public class EntityBoundingBox extends BoundingBox
{
    protected Vector3f size;

    public EntityBoundingBox(final float hSize, final float vSize)
    {
        this.size = new Vector3f(hSize, vSize, hSize);
    }

    public EntityBoundingBox(final double hSize, final double vSize)
    {
        this.size = new Vector3f((float) hSize, (float) vSize, (float) hSize);
    }

    public Vector3f getEntitySize()
    {
        return this.size;
    }

    @Override
    public Vector3d getSize()
    {
        return new Vector3d(this.size);
    }

    public void setCenter(final double x, final double y, final double z)
    {
        this.min.setX(x - (this.size.x / 2));
        this.min.setY(y);
        this.min.setZ(z - (this.size.z / 2));
        this.max.setX(x + (this.size.x / 2));
        this.max.setY(y + this.size.y);
        this.max.setZ(z + (this.size.z / 2));
    }

    public void setCenter(final Entity entity)
    {
        this.setCenter(entity.getX(), entity.getY(), entity.getZ());
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("size", this.size).toString();
    }
}
