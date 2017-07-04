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

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.lang3.Validate;

import org.diorite.commons.arrays.fastutil.FastutilArrayUtils;

import it.unimi.dsi.fastutil.bytes.ByteBidirectionalIterator;

/**
 * NBT type for byte array values.
 */
public final class NbtTagByteArray extends NbtAbstractTag implements Iterable<Byte>
{
    private static final long serialVersionUID = 0;

    /**
     * Empty array of bytes.
     */
    public static final byte[] EMPTY = new byte[0];

    /**
     * Value of nbt tag.
     */
    private byte[] value;

    /**
     * Construct new NbtTagByteArray without name and empty array as value.
     */
    public NbtTagByteArray()
    {
        this.value = EMPTY;
    }

    /**
     * Construct new NbtTagByteArray with given name and 0 as value.
     *
     * @param name
     *         name to be used.
     */
    public NbtTagByteArray(String name)
    {
        super(name);
        this.value = EMPTY;
    }

    /**
     * Construct new NbtTagByteArray with given name and value.
     *
     * @param name
     *         name to be used.
     * @param value
     *         value to be used.
     */
    public NbtTagByteArray(String name, byte[] value)
    {
        super(name);
        Validate.notNull(value, "array can't be null.");
        this.value = value;
    }

    /**
     * Clone constructor.
     *
     * @param nbtTagByteArray
     *         tag to be cloned.
     */
    private NbtTagByteArray(NbtTagByteArray nbtTagByteArray)
    {
        super(nbtTagByteArray);
        this.value = nbtTagByteArray.value.clone();
    }

    /**
     * Returns value of this nbt tag.
     *
     * @return value of this nbt tag.
     */
    public byte[] getValue()
    {
        return this.value;
    }

    /**
     * Set value of this nbt tag.
     *
     * @param b
     *         value to set.
     */
    public void setValue(byte[] b)
    {
        Validate.notNull(b, "array can't be null.");
        this.value = b;
    }

    @Override
    @Nonnull
    public byte[] getNBTValue()
    {
        return this.value;
    }

    @Override
    public NbtTagType getTagType()
    {
        return NbtTagType.BYTE_ARRAY;
    }

    @Override
    public void write(NbtOutputStream outputStream, boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        outputStream.writeInt(this.value.length);
        outputStream.write(this.value);
    }

    @Override
    public void read(NbtInputStream inputStream, boolean anonymous, NbtLimiter limiter) throws IOException
    {
        super.read(inputStream, anonymous, limiter);
        limiter.incrementElementsCount(1);

        int size = inputStream.readInt();
        byte[] data = new byte[size];
        inputStream.readFully(data);
        this.value = data;
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public NbtTagByteArray clone()
    {
        return new NbtTagByteArray(this);
    }

    @Override
    public ByteBidirectionalIterator iterator()
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
        if (! (o instanceof NbtTagByteArray))
        {
            return false;
        }
        if (! super.equals(o))
        {
            return false;
        }

        NbtTagByteArray bytes = (NbtTagByteArray) o;
        return Arrays.equals(this.value, bytes.value);
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
        byte[] value = this.value;
        int size = value.length;
        StringBuilder sb = new StringBuilder(size * 3);
        sb.append('[');

        for (byte aValue : value)
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