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

import org.diorite.utils.collections.arrays.primitive.LongIterator;
import org.diorite.utils.collections.arrays.trove.TLongArrayIterator;

/**
 * Additional nbt type, added by diorite, it will break minecraft compatybility, use with caution. <br>
 * NBT type for arrays of long values.
 */
public class NbtTagLongArray extends NbtAbstractTag implements Iterable<Long>
{
    private static final long serialVersionUID = 0;

    /**
     * Empty array of longs.
     */
    public static final long[] EMPTY = new long[0];

    /**
     * Value of nbt tag.
     */
    protected long[] value;

    /**
     * Construct new NbtTagLongArray without name and empty array as value.
     */
    public NbtTagLongArray()
    {
        this.value = EMPTY;
    }

    /**
     * Construct new NbtTagLongArray with given name and 0 as value.
     *
     * @param name name to be used.
     */
    public NbtTagLongArray(final String name)
    {
        super(name);
        this.value = EMPTY;
    }

    /**
     * Construct new NbtTagLongArray with given name and value.
     *
     * @param name  name to be used.
     * @param value value to be used.
     */
    public NbtTagLongArray(final String name, final long[] value)
    {
        super(name);
        Validate.notNull(value, "array can't be null.");
        this.value = value;
    }

    /**
     * Clone constructor.
     *
     * @param nbtTagLongArray tag to be cloned.
     */
    protected NbtTagLongArray(final NbtTagLongArray nbtTagLongArray)
    {
        super(nbtTagLongArray);
        this.value = nbtTagLongArray.value.clone();
    }

    /**
     * Returns value of this nbt tag.
     *
     * @return value of this nbt tag.
     */
    public long[] getValue()
    {
        return this.value;
    }

    /**
     * Set value of this nbt tag.
     *
     * @param l value to set.
     */
    public void setValue(final long[] l)
    {
        Validate.notNull(l, "array can't be null.");
        this.value = l;
    }

    @Override
    public long[] getNBTValue()
    {
        return this.value;
    }

    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.LONG_ARRAY;
    }

    @Override
    public void write(final NbtOutputStream outputStream, final boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        outputStream.writeInt(this.value.length);
        for (final long i : this.value)
        {
            outputStream.writeLong(i);
        }
    }

    @Override
    public void read(final NbtInputStream inputStream, final boolean anonymous, final NbtLimiter limiter) throws IOException
    {
        super.read(inputStream, anonymous, limiter);
        limiter.incrementElementsCount(1);

        final int size = inputStream.readInt();
        final long[] data = new long[size];
        for (int i = 0; i < size; i++)
        {
            data[i] = inputStream.readLong();
        }
        this.value = data;
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public NbtTagLongArray clone()
    {
        return new NbtTagLongArray(this);
    }

    @Override
    public Iterator<Long> iterator()
    {
        return new LongIterator(this.value);
    }

    /**
     * Returns instance of primitive iterator based on trove interface {@link gnu.trove.iterator.TPrimitiveIterator}.
     *
     * @return instance of primitive iterator based on trove interface {@link gnu.trove.iterator.TPrimitiveIterator}.
     */
    public TLongArrayIterator primitiveIterator()
    {
        return new TLongArrayIterator(this.value);
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof NbtTagLongArray))
        {
            return false;
        }
        if (! super.equals(o))
        {
            return false;
        }

        final NbtTagLongArray longs = (NbtTagLongArray) o;

        return Arrays.equals(this.value, longs.value);

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