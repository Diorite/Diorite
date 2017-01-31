/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.core.protocol.connection.internal;

import org.diorite.commons.function.supplier.Supplier;
import org.diorite.commons.reflections.ConstructorInvoker;
import org.diorite.commons.reflections.DioriteReflectionUtils;
import org.diorite.core.protocol.PacketClass;
import org.diorite.core.protocol.connection.ProtocolDirection;

public class PacketType
{
    private final Class<?>          type;
    private final int               id;
    private final ProtocolDirection direction;
    private final ProtocolState     state;
    private final String            name;
    private final int               minSize;
    private final int               maxSize;
    private       int               preferredSize;

    private final Supplier<? extends Packet<?>> constructor;

    public <T extends Packet<?>> PacketType(Class<T> type, int id, ProtocolDirection direction, ProtocolState state, String name, int minSize, int maxSize,
                                            int preferredSize, Supplier<? extends Packet<?>> constructor)
    {
        this.type = type;
        this.id = id;
        this.direction = direction;
        this.state = state;
        this.name = name;
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.preferredSize = preferredSize;
        this.constructor = constructor;
    }

    public <T extends Packet<?>> PacketType(Class<T> type)
    {
        this.type = type;
        try
        {
            ConstructorInvoker<T> constructor = DioriteReflectionUtils.getConstructor(type);
            this.constructor = constructor::invokeWith;
        }
        catch (Exception e)
        {
            throw new InternalError("Can't create constructor for: " + type, e);
        }
        PacketClass annotation = type.getAnnotation(PacketClass.class);
        this.id = annotation.id();
        this.direction = annotation.direction();
        this.state = annotation.state();
        if (annotation.name().isEmpty())
        {
            this.name = type.getSimpleName().substring(3);
        }
        else
        {
            this.name = annotation.name();
        }
        this.minSize = annotation.minSize();
        this.maxSize = annotation.maxSize();
        this.preferredSize = - 1;
    }

    public <T extends Packet<?>> PacketType(Class<T> type, Supplier<? extends T> constructor)
    {
        this.type = type;
        this.constructor = constructor;
        PacketClass annotation = type.getAnnotation(PacketClass.class);
        this.id = annotation.id();
        this.direction = annotation.direction();
        this.state = annotation.state();
        if (annotation.name().isEmpty())
        {
            this.name = type.getSimpleName().substring(4);
        }
        else
        {
            this.name = annotation.name();
        }
        this.minSize = annotation.minSize();
        this.maxSize = annotation.maxSize();
        this.preferredSize = - 1;
    }

    @SuppressWarnings("unchecked")
    public <T extends Packet<?>> T createPacket()
    {
        try
        {
            return (T) this.constructor.get();
        }
        catch (Exception e)
        {
            throw new InternalError("Can't create constructor for: " + this.type, e);
        }
    }

    public Class<?> getType()
    {
        return this.type;
    }

    public int getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public String getFullName()
    {
        return this.direction.getName() + "/" + this.state.getName() + "/" + this.name + "[0x" + Integer.toHexString(this.id) + "/" + this.id + "]";
    }

    public int getMinSize()
    {
        return this.minSize;
    }

    public int getMaxSize()
    {
        return this.maxSize;
    }

    public int getPreferredSize()
    {
        if (this.preferredSize != - 1)
        {
            return this.preferredSize;
        }
        if (this.minSize == this.maxSize)
        {
            return this.minSize;
        }
        return ((this.minSize + this.maxSize) / 2) + 1;
    }

    public void setPreferredSize(int preferredSize)
    {
        this.preferredSize = preferredSize;
    }

    public ProtocolDirection getDirection()
    {
        return this.direction;
    }

    public ProtocolState getState()
    {
        return this.state;
    }

    @Override
    public String toString()
    {
        return this.type.getName() + "::" + this.getFullName();
    }
}
