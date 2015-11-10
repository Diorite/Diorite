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

import org.diorite.entity.Entity;

public class ImmutableEntityBoundingBox extends EntityBoundingBox
{
    public ImmutableEntityBoundingBox(final float hSize, final float vSize)
    {
        super(hSize, vSize);
    }

    @Override
    public void setCenter(final double x, final double y, final double z)
    {
        throw new UnsupportedOperationException("Can't change center of immutable aabb");
    }

    @Override
    public void setCenter(final Entity entity)
    {
        throw new UnsupportedOperationException("Can't change center of immutable aabb");
    }

    public EntityBoundingBox create(final double x, final double y, final double z)
    {
        final EntityBoundingBox aabb = new EntityBoundingBox(this.hSize, this.vSize);
        aabb.setCenter(x, y, z);
        return aabb;
    }

    public EntityBoundingBox create(final Entity entity)
    {
        return this.create(entity.getX(), entity.getY(), entity.getZ());
    }
}
