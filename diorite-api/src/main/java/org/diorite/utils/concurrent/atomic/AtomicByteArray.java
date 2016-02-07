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

package org.diorite.utils.concurrent.atomic;

import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Atomic array with byte elements.
 */
public class AtomicByteArray implements Serializable
{
    private static final long serialVersionUID = 0;
    private final AtomicIntegerArray array;
    private final int                length;

    /**
     * Creates a new AtomicByteArray of the given length, with all
     * elements initially zero.
     *
     * @param length the length of the array
     */
    public AtomicByteArray(final int length)
    {
        this.length = length;
        this.array = new AtomicIntegerArray((length + 3) / 4);
    }

    /**
     * Sets the element at position {@code i} to the given value.
     *
     * @param i        the index
     * @param newValue the new value
     */
    public void set(final int i, final byte newValue)
    {
        final int idx = i >>> 2;
        final int shift = (i & 3) << 3;
        final int mask = 0xFF << shift;
        final int val2 = (newValue & 0xff) << shift;

        while (true)
        {
            final int num = this.array.get(idx);
            final int num2 = (num & ~ mask) | val2;
            if ((num == num2) || this.array.compareAndSet(idx, num, num2))
            {
                return;
            }
        }
    }

    /**
     * Atomically sets the element at position {@code i} to the given
     * updated value if the current value {@code ==} the expected value.
     *
     * @param i      the index
     * @param expect the expected value
     * @param update the new value
     *
     * @return true if successful. False return indicates that
     * the actual value was not equal to the expected value.
     */
    public boolean compareAndSet(final int i, final byte expect, final byte update)
    {
        final int idx = i >>> 2;
        final int shift = (i & 3) << 3;
        final int mask = 0xFF << shift;
        final int expected2 = (expect & 0xff) << shift;
        final int val2 = (update & 0xff) << shift;

        while (true)
        {
            final int num = this.array.get(idx);
            // Check that the read byte is what we expected
            if ((num & mask) != expected2)
            {
                return false;
            }

            // If we complete successfully, all is good
            final int num2 = (num & ~ mask) | val2;
            if ((num == num2) || this.array.compareAndSet(idx, num, num2))
            {
                return true;
            }
        }
    }


    /**
     * Atomically increments by one the element at index {@code i}.
     *
     * @param i the index
     *
     * @return the previous value
     */
    public final byte getAndIncrement(final int i)
    {
        return this.getAndAdd(i, 1);
    }

    /**
     * Atomically decrements by one the element at index {@code i}.
     *
     * @param i the index
     *
     * @return the previous value
     */
    public final byte getAndDecrement(final int i)
    {
        return this.getAndAdd(i, - 1);
    }

    /**
     * Atomically adds the given value to the element at index {@code i}.
     *
     * @param i     the index
     * @param delta the value to add
     *
     * @return the previous value
     */
    public final byte getAndAdd(final int i, final int delta)
    {
        while (true)
        {
            final byte current = this.get(i);
            final byte next = (byte) (current + delta);
            if (this.compareAndSet(i, current, next))
            {
                return current;
            }
        }
    }

    /**
     * Atomically increments by one the element at index {@code i}.
     *
     * @param i the index
     *
     * @return the updated value
     */
    public final byte incrementAndGet(final int i)
    {
        return this.addAndGet(i, 1);
    }

    /**
     * Atomically decrements by one the element at index {@code i}.
     *
     * @param i the index
     *
     * @return the updated value
     */
    public final byte decrementAndGet(final int i)
    {
        return this.addAndGet(i, - 1);
    }

    /**
     * Atomically adds the given value to the element at index {@code i}.
     *
     * @param i     the index
     * @param delta the value to add
     *
     * @return the updated value
     */
    public final byte addAndGet(final int i, final int delta)
    {
        while (true)
        {
            final byte current = this.get(i);
            final byte next = (byte) (current + delta);
            if (this.compareAndSet(i, current, next))
            {
                return next;
            }
        }
    }

    /**
     * Gets an array containing all the values in the array. The returned values are not guaranteed to be from the same time instant.
     * <br>
     * If an array is provided and it is the correct length, then that array will be used as the destination array.
     *
     * @param array the provided array
     *
     * @return an array containing the values in the array
     */
    public byte[] getArray(byte[] array)
    {
        if ((array == null) || (array.length != this.length))
        {
            array = new byte[this.length];
        }
        for (int i = 0; i < this.length; i++)
        {
            array[i] = this.get(i);
        }
        return array;
    }

    /**
     * Gets an array containing all the values in the array.
     * <br>
     * The returned values are not guaranteed to be from the same time instant.
     *
     * @return the array
     */
    public byte[] getArray()
    {
        return this.getArray(null);
    }

    /**
     * Returns a string representation of the array.
     * <br>
     * The returned values are not guaranteed to be from the same time instant.
     *
     * @return the String
     */
    @Override
    public String toString()
    {
        return Arrays.toString(this.getArray());
    }

    /**
     * Gets the current value at position {@code i}.
     *
     * @param i the index
     *
     * @return the current value
     */
    public byte get(final int i)
    {
        return (byte) (this.array.get(i >>> 2) >> ((i & 3) << 3));
    }

    /**
     * Returns the length of the array.
     *
     * @return the length of the array
     */
    public int length()
    {
        return this.length;
    }
}
