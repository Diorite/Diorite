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
 * NBT type for double values.
 */
public final class NbtTagDouble extends NbtAbstractTagNumber
{
    private static final long serialVersionUID = 0;

    /**
     * Value of nbt tag.
     */
    private double value;

    /**
     * Construct new NbtTagDouble without name and 0 as value.
     */
    public NbtTagDouble()
    {
    }

    /**
     * Construct new NbtTagDouble with given name and 0 as value.
     *
     * @param name
     *         name to be used.
     */
    public NbtTagDouble(@Nullable String name)
    {
        super(name);
    }

    /**
     * Construct new NbtTagDouble with given name and value.
     *
     * @param name
     *         name to be used.
     * @param value
     *         value to be used.
     */
    public NbtTagDouble(@Nullable String name, double value)
    {
        super(name);
        this.value = value;
    }

    /**
     * Clone constructor.
     *
     * @param nbtTagDouble
     *         tag to be cloned.
     */
    private NbtTagDouble(NbtTagDouble nbtTagDouble)
    {
        super(nbtTagDouble);
        this.value = nbtTagDouble.value;
    }

    /**
     * Returns value of this nbt tag.
     *
     * @return value of this nbt tag.
     */
    public double getValue()
    {
        return this.value;
    }

    /**
     * Set value of this nbt tag.
     *
     * @param d
     *         value to set.
     */
    public void setValue(double d)
    {
        this.value = d;
    }

    @Override
    public Double getNumberValue()
    {
        return this.value;
    }

    @Override
    public void setNumberValue(Number i)
    {
        this.value = i.doubleValue();
    }

    @Override
    @Nonnull
    public Double getNBTValue()
    {
        return this.value;
    }

    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.DOUBLE;
    }

    @Override
    public void write(NbtOutputStream outputStream, boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        outputStream.writeDouble(this.value);
    }

    @Override
    public void read(NbtInputStream inputStream, boolean anonymous, NbtLimiter limiter) throws IOException
    {
        super.read(inputStream, anonymous, limiter);
        limiter.incrementElementsCount(1);

        this.value = inputStream.readDouble();
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public NbtTagDouble clone()
    {
        return new NbtTagDouble(this);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof NbtTagDouble))
        {
            return false;
        }
        if (! super.equals(o))
        {
            return false;
        }

        NbtTagDouble that = (NbtTagDouble) o;
        return Double.compare(that.value, this.value) == 0;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        long temp = Double.doubleToLongBits(this.value);
        result = (31 * result) + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString()
    {
        return this.value + "d";
    }
}