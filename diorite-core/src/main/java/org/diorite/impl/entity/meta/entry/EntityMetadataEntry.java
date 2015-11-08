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

package org.diorite.impl.entity.meta.entry;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.entity.meta.EntityMetadataType;
import org.diorite.utils.others.Dirtable;

public abstract class EntityMetadataEntry<T> implements Dirtable
{
    private final byte    index;
    private       boolean dirty; // if true, will be send to clients on next update tick

    public EntityMetadataEntry(final byte index)
    {
        this.index = index;
    }

    public EntityMetadataEntry(final int index)
    {
        this.index = (byte) index;
    }

    public abstract EntityMetadataType getDataType();

    public byte getIndex()
    {
        return this.index;
    }

    public abstract T getData();

    public abstract void setData(final T data);

    @Override
    public boolean isDirty()
    {
        return this.dirty;
    }

    @Override
    public boolean setDirty(final boolean dirty)
    {
        final boolean b = this.dirty;
        this.dirty = dirty;
        return b;
    }


    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof EntityMetadataEntry))
        {
            return false;
        }

        final EntityMetadataEntry<?> that = (EntityMetadataEntry<?>) o;

        if (this.dirty != that.dirty)
        {
            return false;
        }
        if (this.getDataType() != that.getDataType())
        {
            return false;
        }
        final T data = this.getData();
        final Object otherData = that.getData();
        return ! ((data != null) ? ! data.equals(otherData) : (otherData != null));

    }

    @Override
    public int hashCode()
    {
        int result = this.getDataType().hashCode();
        final T data = this.getData();
        result = (31 * result) + ((data != null) ? data.hashCode() : 0);
        result = (31 * result) + (this.dirty ? 1 : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("dataType", this.getDataType()).append("index", this.index).append("data", this.getData()).toString();
    }
}