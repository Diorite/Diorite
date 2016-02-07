/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.utils.collections.arrays;

import java.util.Arrays;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * An array of nibbles (4-bit values) stored efficiently as a byte array of
 * half the size. The even indices are stored in the least significant nibble
 * and the odd indices in the most significant bits.
 * <br>
 * For example, [1 5 8 15] is stored as [0x51 0xf8].
 */
@SuppressWarnings("MagicNumber")
public class NibbleArray
{

    protected final byte[] data;

    /**
     * Construct a new NibbleArray with the given size in nibbles.
     *
     * @param size The number of nibbles in the array.
     *
     * @throws IllegalArgumentException If size is not positive and even.
     */
    public NibbleArray(final int size)
    {
        Validate.isTrue((size > 0) && ((size % 2) == 0), "size must be positive even number, not " + size);
        this.data = new byte[size / 2];
    }

    /**
     * Construct a new NibbleArray using the given underlying bytes. No copy
     * is created.
     *
     * @param data The raw data to use.
     */
    public NibbleArray(final byte[] data)
    {
        this.data = data;
    }

    /**
     * Get the size in nibbles.
     *
     * @return The size in nibbles.
     */
    public int size()
    {
        return 2 * this.data.length;
    }

    /**
     * Get the size in bytes, one-half the size in nibbles.
     *
     * @return The size in bytes.
     */
    public int byteSize()
    {
        return this.data.length;
    }

    /**
     * Get the nibble at the given index.
     *
     * @param index The nibble index.
     *
     * @return The value of the nibble at that index.
     */
    public byte get(final int index)
    {
        final byte val = this.data[index / 2];
        if ((index % 2) == 0)
        {
            return (byte) (val & 0x0f);
        }
        else
        {
            return (byte) ((val & 0xf0) >> 4);
        }
    }

    /**
     * Set the nibble at the given index to the given value.
     *
     * @param index The nibble index.
     * @param value The new value to store.
     */
    public void set(final int index, byte value)
    {
        value &= 0xf;
        final int half = index / 2;
        final byte previous = this.data[half];
        if ((index % 2) == 0)
        {
            this.data[half] = (byte) ((previous & 0xf0) | value);
        }
        else
        {
            this.data[half] = (byte) ((previous & 0x0f) | (value << 4));
        }
    }

    /**
     * Fill the nibble array with the specified value.
     *
     * @param value The value nibble to fill with.
     */
    public void fill(byte value)
    {
        value &= 0xf;
        Arrays.fill(this.data, (byte) ((value << 4) | value));
    }

    /**
     * Get the raw bytes of this nibble array. Modifying the returned array
     * will modify the internal representation of this nibble array.
     *
     * @return The raw bytes.
     */
    public byte[] getRawData()
    {
        return this.data;
    }

    /**
     * Copies into the raw bytes of this nibble array from the given source.
     *
     * @param source The array to copy from.
     *
     * @throws IllegalArgumentException If source is not the correct length.
     */
    public void setRawData(final byte[] source)
    {
        Validate.isTrue(source.length == this.data.length, "expected byte array of length " + this.data.length + ", not " + source.length);
        System.arraycopy(source, 0, this.data, 0, source.length);
    }

    /**
     * Take a snapshot of this NibbleArray which will not reflect changes.
     *
     * @return The snapshot NibbleArray.
     */
    public NibbleArray snapshot()
    {
        return new NibbleArray(this.data.clone());
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("data", this.data).toString();
    }
}