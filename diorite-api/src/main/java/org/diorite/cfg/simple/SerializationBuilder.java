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

package org.diorite.cfg.simple;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.ILocation;

public class SerializationBuilder
{
    private final Map<String, Object> data;

    private SerializationBuilder(final int size)
    {
        this.data = new LinkedHashMap<>(size);
    }

    public SerializationBuilder append(final Object str, final Enum<?> object)
    {
        this.data.put(str.toString(), object.name());
        return this;
    }

    public SerializationBuilder append(final String str, final Enum<?> object)
    {
        this.data.put(str, object.name());
        return this;
    }

    public SerializationBuilder append(final Object str, final Object object)
    {
        if (object instanceof Enum)
        {
            return this.append(str, (Enum<?>) object);
        }
        this.data.put(str.toString(), object);
        return this;
    }

    public SerializationBuilder append(final String str, final Object object)
    {
        if (object instanceof Enum)
        {
            return this.append(str, (Enum<?>) object);
        }
        this.data.put(str, object);
        return this;
    }

    public SerializationBuilder append(final String str, final SerializationBuilder object)
    {
        this.data.put(str, object.data);
        return this;
    }

    public SerializationBuilder append(final Object str, final SerializationBuilder object)
    {
        this.data.put(str.toString(), object.data);
        return this;
    }

    public SerializationBuilder appendLoc(final String str, final ILocation location)
    {
        return this.append(str, start(6).append("x", location.getX()).append("y", location.getY()).append("z", location.getZ()).append("world", (location.getWorld() == null) ? null : location.getWorld().getName()).append("pitch", location.getPitch()).append("yaw", location.getYaw()).build());
    }

    public SerializationBuilder append(final Map<String, Object> object)
    {
        this.data.putAll(object);
        return this;
    }

    public Map<String, Object> build()
    {
        return this.data;
    }

    public static SerializationBuilder start(final int size)
    {
        return new SerializationBuilder(size);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("data", this.data).toString();
    }
}
