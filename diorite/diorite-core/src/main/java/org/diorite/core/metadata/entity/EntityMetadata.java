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

package org.diorite.core.metadata.entity;

import org.diorite.core.metadata.entity.entry.*;

public class EntityMetadata
{
    private final EntityMetadataValue<?>[] values;

    public EntityMetadata(final short size)
    {
        this.values = new EntityMetadataValue<?>[size];
    }

    public void store(final EntityMetadataValue<?> value)
    {
        this.values[value.getIndex()] = value;
    }

    public EntityMetadataValue<?> get(final byte index)
    {
        final EntityMetadataValue<?> value = this.values[index];

        if(value == null)
        {
            throw new NullPointerException("Found null metadata value at: " + index);
        }

        return value;
    }

    public byte getByte(final byte index)
    {
        return getByteValue(index).getValue();
    }

    public int getInteger(final byte index)
    {
        return getIntegerValue(index).getValue();
    }

    public String getString(final byte index)
    {
        return getStringValue(index).getValue();
    }

    public boolean getBoolean(final byte index)
    {
        return getBooleanValue(index).getValue();
    }

    public void setByte(final byte index, final byte value)
    {
        getByteValue(index).setValue(value);
    }

    public void setInteger(final byte index, final int value)
    {
        getIntegerValue(index).setValue(value);
    }

    public void setString(final byte index, final String value)
    {
        getStringValue(index).setValue(value);
    }

    public void setBoolean(final byte index, final boolean value)
    {
        getBooleanValue(index).setValue(value);
    }

    private EntityMetadataByteValue getByteValue(final byte index)
    {
        final EntityMetadataValue<?> value = this.get(index);

        if(value.getType() != EntityMetadataValueType.BYTE)
        {
            throw new IllegalArgumentException("Metadata type mismatch: expected byte value, but found: " + value);
        }

        return (EntityMetadataByteValue) value;
    }

    private EntityMetadataIntegerValue getIntegerValue(final byte index)
    {
        final EntityMetadataValue<?> value = this.get(index);

        if(value.getType() != EntityMetadataValueType.INTEGER)
        {
            throw new IllegalArgumentException("Metadata type mismatch: expected integer value, but found: " + value);
        }

        return (EntityMetadataIntegerValue) value;
    }

    private EntityMetadataStringValue getStringValue(final byte index)
    {
        final EntityMetadataValue<?> value = this.get(index);

        if(value.getType() != EntityMetadataValueType.STRING)
        {
            throw new IllegalArgumentException("Metadata type mismatch: expected string value, but found: " + value);
        }

        return (EntityMetadataStringValue) value;
    }

    private EntityMetadataBooleanValue getBooleanValue(final byte index)
    {
        final EntityMetadataValue<?> value = this.get(index);

        if(value.getType() != EntityMetadataValueType.BOOLEAN)
        {
            throw new IllegalArgumentException("Metadata type mismatch: expected boolean value, but found: " + value);
        }

        return (EntityMetadataBooleanValue) value;
    }
}
