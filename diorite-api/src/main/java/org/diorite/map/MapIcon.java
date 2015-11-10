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

package org.diorite.map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.nbt.NbtSerializable;
import org.diorite.nbt.NbtTagCompound;

/**
 * Represent icon on map.
 */
public class MapIcon implements NbtSerializable
{
    /**
     * String id of icon.
     */
    protected final String      id;
    /**
     * x position on map.
     */
    protected       double      x;
    /**
     * z position on map.
     */
    protected       double      z;
    /**
     * Rotation of icon.
     */
    protected       double      direction;
    /**
     * Type of icon.
     */
    protected       MapIconType type;

    /**
     * Construct new MapIcon.
     *
     * @param id        string id of icon.
     * @param x         x position on map.
     * @param z         z position on map.
     * @param direction rotation of icon.
     * @param type      type of icon.
     */
    public MapIcon(final String id, final double x, final double z, final double direction, final MapIconType type)
    {
        this.id = id;
        this.x = x;
        this.z = z;
        this.direction = direction;
        this.type = type;
    }

    /**
     * Deserialize MapIcon from {@link NbtTagCompound}.
     *
     * @param tag data to deserialize.
     */
    public MapIcon(final NbtTagCompound tag)
    {
        this.id = tag.getString("id", "");
        this.x = tag.getDouble("x");
        this.z = tag.getDouble("z");
        this.direction = tag.getDouble("rot");
        this.type = MapIconType.getByType(tag.getByte("type"));
    }

    /**
     * Returns x position on map.
     *
     * @return x position on map.
     */
    public double getX()
    {
        return this.x;
    }

    /**
     * Set new x position on map.
     *
     * @param x new x position on map.
     */
    public void setX(final double x)
    {
        this.x = x;
    }

    /**
     * Returns z position on map.
     *
     * @return z position on map.
     */
    public double getZ()
    {
        return this.z;
    }

    /**
     * Set new z position on map.
     *
     * @param z new z position on map.
     */
    public void setZ(final double z)
    {
        this.z = z;
    }

    /**
     * Returns rotation of map icon.
     *
     * @return rotation of map icon.
     */
    public double getDirection()
    {
        return this.direction;
    }

    /**
     * Set rotation of map icon.
     *
     * @param direction new rotation of map icon.
     */
    public void setDirection(final double direction)
    {
        this.direction = direction;
    }

    /**
     * Returns type of map icon.
     *
     * @return type of map icon.
     */
    public MapIconType getType()
    {
        return this.type;
    }

    /**
     * Set new type of map icon.
     *
     * @param type new type of map icon.
     */
    public void setType(final MapIconType type)
    {
        this.type = type;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof MapIcon))
        {
            return false;
        }

        final MapIcon mapIcon = (MapIcon) o;

        return this.id.equals(mapIcon.id) && (Double.compare(mapIcon.x, this.x) == 0) && (Double.compare(mapIcon.z, this.z) == 0) && (Double.compare(mapIcon.direction, this.direction) == 0) && this.type.equals(mapIcon.type);
    }

    @Override
    public int hashCode()
    {
        return this.id.hashCode();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("x", this.x).append("z", this.z).append("direction", this.direction).append("type", this.type).toString();
    }

    @Override
    public NbtTagCompound serializeToNBT()
    {
        final NbtTagCompound tag = new NbtTagCompound();
        tag.setString("id", this.id);
        tag.setByte("type", this.type.getType());
        tag.setDouble("x", this.x);
        tag.setDouble("z", this.z);
        tag.setDouble("rot", this.direction);
        return tag;
    }
}
