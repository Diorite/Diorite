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

package org.diorite.nbt;

import javax.annotation.Nullable;

import java.io.IOException;

/**
 * NBT abstract type for numeric values.
 */
public abstract class NbtAbstractTagNumber extends Number implements NbtTag
{
    private static final long serialVersionUID = 0L;
    /**
     * Name of this nbt tag.
     */
    protected @Nullable String name;
    /**
     * Parent of this nbt tag.
     */
    protected transient @Nullable NbtTagContainer parent = null;

    /**
     * Construct new NbtTagInt without name and 0 as value.
     */
    public NbtAbstractTagNumber()
    {
    }

    /**
     * Construct new NbtTagByte with given name and 0 as value.
     *
     * @param name
     *         name to be used.
     */
    public NbtAbstractTagNumber(@Nullable String name)
    {
        this.setName(name);
    }

    /**
     * Clone constructor.
     *
     * @param nbtAbstractTagNumber
     *         tag to be cloned.
     */
    protected NbtAbstractTagNumber(NbtAbstractTagNumber nbtAbstractTagNumber)
    {
        this.name = nbtAbstractTagNumber.name;
    }

    @Override
    @Nullable
    public String getName()
    {
        return this.name;
    }

    @Override
    public void setName(@Nullable String name)
    {
        if (this.parent != null)
        {
            this.parent.removeTag(this);
        }
        if (name != null)
        {
            name = name.intern();
        }
        this.name = name;
        if (this.parent != null)
        {
            this.parent.addTag(this);
        }
    }

    @Override
    @Nullable
    public NbtTagContainer getParent()
    {
        return this.parent;
    }

    @Override
    public void setParent(@Nullable NbtTagContainer parent)
    {
        if (this.parent != null)
        {
            this.parent.removeTag(this);
        }
        this.parent = parent;
    }

    /**
     * Returns value of this nbt tag.
     *
     * @return value of this nbt tag.
     */
    public abstract Number getNumberValue();

    /**
     * Set value of this nbt tag.
     *
     * @param i
     *         value to set.
     */
    public abstract void setNumberValue(Number i);

    @Override
    public abstract Number getNBTValue();

    @Override
    public void write(NbtOutputStream outputStream, boolean anonymous) throws IOException
    {
        if (! anonymous)
        {
            if (this.name == null)
            {
                throw new IllegalStateException("Can't save tag with name if name is null");
            }
            byte[] name = this.name.getBytes(STRING_CHARSET);
            outputStream.writeShort(name.length);
            outputStream.write(name);
        }
    }

    @Override
    public abstract NbtAbstractTagNumber clone();

    @Override
    public double doubleValue()
    {
        return this.getNumberValue().doubleValue();
    }

    @Override
    public float floatValue()
    {
        return this.getNumberValue().floatValue();
    }

    @Override
    public long longValue()
    {
        return this.getNumberValue().longValue();
    }

    @Override
    public int intValue()
    {
        return this.getNumberValue().intValue();
    }

    @Override
    public byte byteValue()
    {
        return this.getNumberValue().byteValue();
    }

    @Override
    public short shortValue()
    {
        return this.getNumberValue().shortValue();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof NbtAbstractTagNumber))
        {
            return false;
        }

        NbtAbstractTagNumber that = (NbtAbstractTagNumber) o;
        return ! ((this.name != null) ? ! this.name.equals(that.name) : (that.name != null));
    }

    @Override
    public int hashCode()
    {
        return (this.name != null) ? this.name.hashCode() : 0;
    }
}