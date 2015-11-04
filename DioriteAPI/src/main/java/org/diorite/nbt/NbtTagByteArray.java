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

import org.diorite.utils.collections.arrays.primitive.ByteIterator;
import org.diorite.utils.collections.arrays.trove.TByteArrayIterator;

/**
 * NBT type for byte array values.
 */
public class NbtTagByteArray extends NbtAbstractTag implements Iterable<Byte>
{
    private static final long serialVersionUID = 0;

    /**
     * Empty array of bytes.
     */
    public static final byte[] EMPTY = new byte[0];

    /**
     * Value of nbt tag.
     */
    protected byte[] value;

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
     * @param name name to be used.
     */
    public NbtTagByteArray(final String name)
    {
        super(name);
        this.value = EMPTY;
    }

    /**
     * Construct new NbtTagByteArray with given name and value.
     *
     * @param name  name to be used.
     * @param value value to be used.
     */
    public NbtTagByteArray(final String name, final byte[] value)
    {
        super(name);
        Validate.notNull(value, "array can't be null.");
        this.value = value;
    }

    /**
     * Clone constructor.
     *
     * @param nbtTagByteArray tag to be cloned.
     */
    protected NbtTagByteArray(final NbtTagByteArray nbtTagByteArray)
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
     * @param b value to set.
     */
    public void setValue(final byte[] b)
    {
        Validate.notNull(b, "array can't be null.");
        this.value = b;
    }

    @Override
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
    public void write(final NbtOutputStream outputStream, final boolean anonymous) throws IOException
    {
        super.write(outputStream, anonymous);
        outputStream.writeInt(this.value.length);
        outputStream.write(this.value);
    }

    @Override
    public void read(final NbtInputStream inputStream, final boolean anonymous, final NbtLimiter limiter) throws IOException
    {
        super.read(inputStream, anonymous, limiter);
        limiter.incrementElementsCount(1);

        final int size = inputStream.readInt();
        final byte[] data = new byte[size];
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
    public Iterator<Byte> iterator()
    {
        return new ByteIterator(this.value);
    }

    /**
     * Returns instance of primitive iterator based on trove interface {@link gnu.trove.iterator.TPrimitiveIterator}.
     *
     * @return instance of primitive iterator based on trove interface {@link gnu.trove.iterator.TPrimitiveIterator}.
     */
    public TByteArrayIterator primitiveIterator()
    {
        return new TByteArrayIterator(this.value);
    }

    @Override
    public boolean equals(final Object o)
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

        final NbtTagByteArray bytes = (NbtTagByteArray) o;
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).toString();
    }
}