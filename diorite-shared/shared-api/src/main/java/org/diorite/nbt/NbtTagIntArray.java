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
import java.util.Arrays;

import org.apache.commons.lang3.Validate;

import org.diorite.commons.arrays.fastutil.FastutilArrayUtils;

import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;

/**
 * NBT type for int array values.
 */
public final class NbtTagIntArray extends NbtAbstractTag implements Iterable<Integer>
{
    private static final long serialVersionUID = 0;

    /**
     * Empty array of ints.
     */
    public static final int[] EMPTY = new int[0];

    /**
     * Value of nbt tag.
     */
    private int[] value;

    /**
     * Construct new NbtTagIntArray without name and empty array as value.
     */
    public NbtTagIntArray()
    {
        this.value = EMPTY;
    }

    /**
     * Construct new NbtTagIntArray with given name and 0 as value.
     *
     * @param name
     *         name to be used.
     */
    public NbtTagIntArray(@Nullable String name)
    {
        super(name);
        this.value = EMPTY;
    }

    /**
     * Construct new NbtTagIntArray with given name and value.
     *
     * @param name
     *         name to be used.
     * @param value
     *         value to be used.
     */
    public NbtTagIntArray(@Nullable String name, int[] value)
    {
        super(name);
        Validate.notNull(value, "array can't be null.");
        this.value = value;
    }

    /**
     * Clone constructor.
     *
     * @param nbtTagIntArray
     *         tag to be cloned.
     */
    private NbtTagIntArray(NbtTagIntArray nbtTagIntArray)
    {
        super(nbtTagIntArray);
        this.value = nbtTagIntArray.value.clone();
    }

    /**
     * Returns value of this nbt tag.
     *
     * @return value of this nbt tag.
     */
    public int[] getValue()
    {
        return this.value;
    }

    /**
     * Set value of this nbt tag.
     *
     * @param i
     *         value to set.
     */
    public void setValue(int[] i)
    {
        Validate.notNull(i, "array can't be null.");
        this.value = i;
    }

    @Override
    @Nonnull
    public int[] getNBTValue()
    {
        return this.value;
    }

    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.INTEGER_ARRAY;
    }

    @Override
    public void write(NbtOutputStream outputStream, boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        outputStream.writeInt(this.value.length);
        for (int i : this.value)
        {
            outputStream.writeInt(i);
        }
    }

    @Override
    public void read(NbtInputStream inputStream, boolean anonymous, NbtLimiter limiter) throws IOException
    {
        super.read(inputStream, anonymous, limiter);
        limiter.incrementElementsCount(1);

        int size = inputStream.readInt();
        int[] data = new int[size];
        for (int i = 0; i < size; i++)
        {
            data[i] = inputStream.readInt();
        }
        this.value = data;
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public NbtTagIntArray clone()
    {
        return new NbtTagIntArray(this);
    }

    @Override
    public IntBidirectionalIterator iterator()
    {
        return FastutilArrayUtils.iteratorOf(this.value);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof NbtTagIntArray))
        {
            return false;
        }
        if (! super.equals(o))
        {
            return false;
        }

        NbtTagIntArray integers = (NbtTagIntArray) o;
        return Arrays.equals(this.value, integers.value);
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
        int[] value = this.value;
        int size = value.length;
        StringBuilder sb = new StringBuilder(size * 6);
        sb.append('[');

        for (int aValue : value)
        {
            if (sb.length() != 1)
            {
                sb.append(',');
            }
            sb.append(aValue);
        }

        sb.append(']');
        return sb.toString();
    }
}