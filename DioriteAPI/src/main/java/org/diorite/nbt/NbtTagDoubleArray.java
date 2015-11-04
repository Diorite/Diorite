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

package org.diorite.nbt;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.utils.collections.arrays.primitive.DoubleIterator;
import org.diorite.utils.collections.arrays.trove.TDoubleArrayIterator;

/**
 * Additional nbt type, added by diorite, it will break minecraft compatybility, use with caution. <br>
 * NBT type for arrays of double values.
 */
public class NbtTagDoubleArray extends NbtAbstractTag implements Iterable<Double>
{
    private static final long serialVersionUID = 0;

    /**
     * Empty array of doubles.
     */
    public static final double[] EMPTY = new double[0];

    /**
     * Value of nbt tag.
     */
    protected double[] value;

    /**
     * Construct new NbtTagDoubleArray without name and empty array as value.
     */
    public NbtTagDoubleArray()
    {
        this.value = EMPTY;
    }

    /**
     * Construct new NbtTagDoubleArray with given name and 0 as value.
     *
     * @param name name to be used.
     */
    public NbtTagDoubleArray(final String name)
    {
        super(name);
        this.value = EMPTY;
    }

    /**
     * Construct new NbtTagDoubleArray with given name and value.
     *
     * @param name  name to be used.
     * @param value value to be used.
     */
    public NbtTagDoubleArray(final String name, final double[] value)
    {
        super(name);
        Validate.notNull(value, "array can't be null.");
        this.value = value;
    }

    /**
     * Clone constructor.
     *
     * @param nbtTagDoubleArray tag to be cloned.
     */
    protected NbtTagDoubleArray(final NbtTagDoubleArray nbtTagDoubleArray)
    {
        super(nbtTagDoubleArray);
        this.value = nbtTagDoubleArray.value.clone();
    }

    /**
     * Returns value of this nbt tag.
     *
     * @return value of this nbt tag.
     */
    public double[] getValue()
    {
        return this.value;
    }

    /**
     * Set value of this nbt tag.
     *
     * @param d value to set.
     */
    public void setValue(final double[] d)
    {
        Validate.notNull(d, "array can't be null.");
        this.value = d;
    }

    @Override
    public double[] getNBTValue()
    {
        return this.value;
    }

    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.DOUBLE_ARRAY;
    }

    @Override
    public void write(final NbtOutputStream outputStream, final boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        outputStream.writeInt(this.value.length);
        for (final double i : this.value)
        {
            outputStream.writeDouble(i);
        }
    }

    @Override
    public void read(final NbtInputStream inputStream, final boolean anonymous, final NbtLimiter limiter) throws IOException
    {
        super.read(inputStream, anonymous, limiter);
        limiter.incrementElementsCount(1);

        final int size = inputStream.readInt();
        final double[] data = new double[size];
        for (int i = 0; i < size; i++)
        {
            data[i] = inputStream.readDouble();
        }
        this.value = data;
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public NbtTagDoubleArray clone()
    {
        return new NbtTagDoubleArray(this);
    }

    @Override
    public Iterator<Double> iterator()
    {
        return new DoubleIterator(this.value);
    }

    /**
     * Returns instance of primitive iterator based on trove interface {@link gnu.trove.iterator.TPrimitiveIterator}.
     *
     * @return instance of primitive iterator based on trove interface {@link gnu.trove.iterator.TPrimitiveIterator}.
     */
    public TDoubleArrayIterator primitiveIterator()
    {
        return new TDoubleArrayIterator(this.value);
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof NbtTagDoubleArray))
        {
            return false;
        }
        if (! super.equals(o))
        {
            return false;
        }

        final NbtTagDoubleArray doubles = (NbtTagDoubleArray) o;
        return Arrays.equals(this.value, doubles.value);
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = (31 * result) + Arrays.hashCode(this.value);
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).toString();
    }
}