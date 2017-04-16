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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.io.IOException;

/**
 * NBT type for int values.
 */
public final class NbtTagInt extends NbtAbstractTagNumber
{
    private static final long serialVersionUID = 0;

    /**
     * Value of nbt tag.
     */
    private int value;

    /**
     * Construct new NbtTagInt without name and 0 as value.
     */
    public NbtTagInt()
    {
    }

    /**
     * Construct new NbtTagByte with given name and 0 as value.
     *
     * @param name
     *         name to be used.
     */
    public NbtTagInt(@Nullable String name)
    {
        super(name);
    }

    /**
     * Construct new NbtTagByte with given name and value.
     *
     * @param name
     *         name to be used.
     * @param value
     *         value to be used.
     */
    public NbtTagInt(@Nullable String name, int value)
    {
        super(name);
        this.value = value;
    }

    /**
     * Clone constructor.
     *
     * @param nbtTagInt
     *         tag to be cloned.
     */
    private NbtTagInt(NbtTagInt nbtTagInt)
    {
        super(nbtTagInt);
        this.value = nbtTagInt.value;
    }

    /**
     * Returns value of this nbt tag.
     *
     * @return value of this nbt tag.
     */
    public int getValue()
    {
        return this.value;
    }

    /**
     * Set value of this nbt tag.
     *
     * @param i
     *         value to set.
     */
    public void setValue(int i)
    {
        this.value = i;
    }

    @Override
    public Integer getNumberValue()
    {
        return this.value;
    }

    @Override
    public void setNumberValue(Number i)
    {
        this.value = i.intValue();
    }

    @Override
    @Nonnull
    public Integer getNBTValue()
    {
        return this.value;
    }

    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.INTEGER;
    }

    @Override
    public void write(NbtOutputStream outputStream, boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        outputStream.writeInt(this.value);
    }

    @Override
    public void read(NbtInputStream inputStream, boolean anonymous, NbtLimiter limiter) throws IOException
    {
        super.read(inputStream, anonymous, limiter);
        limiter.incrementElementsCount(1);

        this.value = inputStream.readInt();
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public NbtTagInt clone()
    {
        return new NbtTagInt(this);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof NbtTagInt))
        {
            return false;
        }
        if (! super.equals(o))
        {
            return false;
        }

        NbtTagInt nbtTagInt = (NbtTagInt) o;
        return this.value == nbtTagInt.value;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = (31 * result) + this.value;
        return result;
    }

    @Override
    public String toString()
    {
        return Integer.toString(this.value);
    }
}