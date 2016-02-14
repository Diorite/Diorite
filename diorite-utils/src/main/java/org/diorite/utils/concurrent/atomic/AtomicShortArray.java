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
 * Atomic array with short elements.
 */
@SuppressWarnings("MagicNumber")
public class AtomicShortArray implements Serializable
{
    private static final long serialVersionUID = 0;
    private final int                length;
    private final int                backingArraySize;
    private final AtomicIntegerArray backingArray;

    /**
     * Creates an atomic short array of a given length
     *
     * @param length the length of the array
     */
    public AtomicShortArray(final int length)
    {
        this.length = length;
        this.backingArraySize = (length & 1) + (length >> 1);
        this.backingArray = new AtomicIntegerArray(this.backingArraySize);
    }

    /**
     * Creates an atomic short array of a given length that is equal to a given array
     *
     * @param initial the initial array, is allowed to be null
     */
    public AtomicShortArray(final short[] initial)
    {
        this(initial.length, initial);
    }

    /**
     * Creates an atomic short array of a given length that is equal to a given array
     *
     * @param length  the length of the array
     * @param initial the initial array, is allowed to be null
     */
    public AtomicShortArray(final int length, final short[] initial)
    {
        this.length = length;
        this.backingArraySize = (length & 1) + (length >> 1);
        if (initial == null)
        {
            this.backingArray = new AtomicIntegerArray(this.backingArraySize);
            return;
        }
        // Store the initial data in the backing array
        final int[] data = new int[this.backingArraySize];
        int initIndex = 0;
        for (int i = 0; i < this.backingArraySize; i++)
        {
            if ((initIndex + 1) < initial.length)
            {
                // both elements available for squashing
                data[i] = key(initial[initIndex], initial[initIndex + 1]);
            }
            else
            {
                // first element is last element
                data[i] = key(initial[initIndex], (short) 0);
            }
            initIndex += 2;
        }
        this.backingArray = new AtomicIntegerArray(data);
    }

    /**
     * Gets the length of the array
     *
     * @return the length
     */
    public final int length()
    {
        return this.length;
    }

    /**
     * Gets an element from the array at a given index
     *
     * @param index the index
     *
     * @return the element
     */
    public final short get(final int index)
    {
        final int packed = this.getPacked(index);
        return isEven(index) ? key1(packed) : key2(packed);
    }

    /**
     * Sets an element in the array at a given index and returns the old value
     *
     * @param index the index
     * @param value the new value
     *
     * @return the old value
     */
    public final short getAndSet(final int index, final short value)
    {
        boolean success = false;
        short odd;
        short even;
        short oldValue = 0;
        final int backingIndex = index >> 1;
        final boolean evenIndex = isEven(index);
        while (! success)
        {
            final int oldPacked = this.backingArray.get(backingIndex);
            if (evenIndex)
            {
                oldValue = key1(oldPacked);
                even = value;
                odd = key2(oldPacked);
            }
            else
            {
                oldValue = key2(oldPacked);
                even = key1(oldPacked);
                odd = value;
            }
            final int newPacked = key(even, odd);
            success = this.backingArray.compareAndSet(backingIndex, oldPacked, newPacked);
        }
        return oldValue;
    }

    /**
     * Sets two elements in the array at once. The index must be even.
     *
     * @param index the index
     * @param even  the new value for the element at (index)
     * @param odd   the new value for the element at (index + 1)
     */
    public final void set(final int index, final short even, final short odd)
    {
        if ((index & 1) != 0)
        {
            throw new IllegalArgumentException("When setting 2 elements at once, the index must be even");
        }
        this.backingArray.set(index >> 1, key(even, odd));
    }

    /**
     * Sets the element at the given index, but only if the previous value was the expected value.
     *
     * @param index    the index
     * @param expected the expected value
     * @param newValue the new value
     *
     * @return true on success
     */
    public final boolean compareAndSet(final int index, final short expected, final short newValue)
    {
        boolean success = false;
        short odd;
        short even;
        short oldValue;
        final int backingIndex = index >> 1;
        final boolean evenIndex = isEven(index);
        while (! success)
        {
            final int oldPacked = this.backingArray.get(backingIndex);
            if (evenIndex)
            {
                oldValue = key1(oldPacked);
                even = newValue;
                odd = key2(oldPacked);
            }
            else
            {
                oldValue = key2(oldPacked);
                even = key1(oldPacked);
                odd = newValue;
            }
            if (oldValue != expected)
            {
                return false;
            }
            final int newPacked = key(even, odd);
            success = this.backingArray.compareAndSet(backingIndex, oldPacked, newPacked);
        }
        return true;
    }

    private short addAndGet(final int index, final short delta, final boolean old)
    {
        boolean success = false;
        short newValue = 0;
        short oldValue = 0;
        while (! success)
        {
            oldValue = this.get(index);
            newValue = (short) (oldValue + delta);
            success = this.compareAndSet(index, oldValue, newValue);
        }
        return old ? oldValue : newValue;
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
    public short[] getArray(short[] array)
    {
        if ((array == null) || (array.length != this.length))
        {
            array = new short[this.length];
        }
        for (int i = 0; i < this.length; i += 2)
        {
            final int packed = this.getPacked(i);
            array[i] = key1(packed);
            if ((i + 1) < this.length)
            {
                array[i + 1] = key2(packed);
            }
        }
        return array;
    }

    /**
     * The remaining methods use the above methods
     */

    /**
     * Sets an element to the given value
     *
     * @param index the index
     * @param value the new value
     */
    public final void set(final int index, final short value)
    {
        this.getAndSet(index, value);
    }

    /**
     * Sets an element to the given value, but the update may not happen immediately
     *
     * @param index the index
     * @param value the new value
     */
    public final void lazySet(final int index, final short value)
    {
        this.set(index, value);
    }

    /**
     * Sets the element at the given index, but only if the previous value was the expected value. This may fail spuriously.
     *
     * @param index    the index
     * @param expected the expected value
     * @param newValue the new value
     *
     * @return true on success
     */
    public final boolean weakCompareAndSet(final int index, final short expected, final short newValue)
    {
        return this.compareAndSet(index, expected, newValue);
    }

    /**
     * Atomically adds a delta to an element, and gets the new value.
     *
     * @param index the index
     * @param delta the delta to add to the element
     *
     * @return the new value
     */
    public final short addAndGet(final int index, final short delta)
    {
        return this.addAndGet(index, delta, false);
    }

    /**
     * Atomically adds a delta to an element, and gets the old value.
     *
     * @param index the index
     * @param delta the delta to add to the element
     *
     * @return the old value
     */
    public final short getAndAdd(final int index, final short delta)
    {
        return this.addAndGet(index, delta, true);
    }

    /**
     * Atomically increments an element and returns the old value.
     *
     * @param index the index
     *
     * @return the old value
     */
    public final short getAndIncrement(final int index)
    {
        return this.getAndAdd(index, (short) 1);
    }

    /**
     * Atomically decrements an element and returns the old value.
     *
     * @param index the index
     *
     * @return the old value
     */
    public final short getAndDecrement(final int index)
    {
        return this.getAndAdd(index, (short) - 1);
    }

    /**
     * Atomically increments an element and returns the new value.
     *
     * @param index the index
     *
     * @return the new value
     */
    public final short incrementAndGet(final int index)
    {
        return this.addAndGet(index, (short) 1);
    }

    /**
     * Atomically decrements an element and returns the new value.
     *
     * @param index the index
     *
     * @return the new value
     */
    public final short decrementAndGet(final int index)
    {
        return this.addAndGet(index, (short) - 1);
    }

    /**
     * Gets an array containing all the values in the array.
     * <br>
     * The returned values are not guaranteed to be from the same time instant.
     *
     * @return the array
     */
    public short[] getArray()
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
        final short[] array = this.getArray();
        return Arrays.toString(array);
    }

    private int getPacked(final int index)
    {
        return this.backingArray.get(index >> 1);
    }

    private static boolean isEven(final int index)
    {
        return (index & 1) == 0;
    }

    private static int key(final short key1, final short key2)
    {
        return (key1 << 16) | (key2 & 0xFFFF);
    }

    private static short key1(final int composite)
    {
        return (short) ((composite >> 16) & 0xFFFF);
    }

    private static short key2(final int composite)
    {
        return (short) (composite & 0xFFFF);
    }
}