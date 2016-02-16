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
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import org.diorite.utils.DioriteUtils;

import sun.misc.Unsafe;

/**
 * Copy of {@link AtomicReferenceArray} with non-final methods and offset param.
 * <br>
 * An array of object references in which elements may be updated
 * atomically.
 *
 * @param <E> The base class of elements held in this array
 *
 * @author Doug Lea
 */
public class AtomicArrayBase<E> implements Serializable, AtomicArray<E>
{
    private static final long   serialVersionUID = 0;
    private static final Unsafe unsafe           = DioriteUtils.getUnsafe();

    private static final int      base;
    private static final int      shift;
    private static final long     arrayFieldOffset;
    private final        Object[] array; // must have exact type Object[]

    /**
     * Creates a new AtomicArrayBase of the given length, with all
     * elements initially null.
     *
     * @param length the length of the array
     */
    public AtomicArrayBase(final int length)
    {
        this.array = new Object[length];
    }

    /**
     * Creates a new AtomicArrayBase with the same length as, and
     * all elements copied from, the given array.
     *
     * @param array the array to copy elements from
     *
     * @throws NullPointerException if array is null
     */
    public AtomicArrayBase(final E[] array)
    {
        this.array = Arrays.copyOf(array, array.length, Object[].class);
    }

    protected long checkedByteOffset(final int i)
    {
        if ((i < 0) || (i >= this.array.length))
        {
            throw new IndexOutOfBoundsException("index " + i);
        }

        return byteOffset(i);
    }

    @Override
    public int length()
    {
        return this.array.length;
    }

    @Override
    public int offset()
    {
        return 0;
    }

    @Override
    public E get(final int i)
    {
        return this.getRaw(this.checkedByteOffset(i));
    }

    @SuppressWarnings("unchecked")
    protected E getRaw(final long offset)
    {
        return (E) unsafe.getObjectVolatile(this.array, offset);
    }

    @Override
    public void set(final int i, final E newValue)
    {
        unsafe.putObjectVolatile(this.array, this.checkedByteOffset(i), newValue);
    }

    @Override
    public void lazySet(final int i, final E newValue)
    {
        unsafe.putOrderedObject(this.array, this.checkedByteOffset(i), newValue);
    }

    @Override
    @SuppressWarnings("unchecked")
    public E getAndSet(final int i, final E newValue)
    {
        return (E) unsafe.getAndSetObject(this.array, this.checkedByteOffset(i), newValue);
    }

    @Override
    public boolean compareAndSet(final int i, final E expect, final E update)
    {
        return this.compareAndSetRaw(this.checkedByteOffset(i), expect, update);
    }

    protected boolean compareAndSetRaw(final long offset, final E expect, final E update)
    {
        return unsafe.compareAndSwapObject(this.array, offset, expect, update);
    }

    @Override
    public boolean weakCompareAndSet(final int i, final E expect, final E update)
    {
        return this.compareAndSet(i, expect, update);
    }

    @Override
    public E getAndUpdate(final int i, final UnaryOperator<E> updateFunction)
    {
        final long offset = this.checkedByteOffset(i);
        E prev, next;
        do
        {
            prev = this.getRaw(offset);
            next = updateFunction.apply(prev);
        } while (! this.compareAndSetRaw(offset, prev, next));
        return prev;
    }

    @Override
    public E updateAndGet(final int i, final UnaryOperator<E> updateFunction)
    {
        final long offset = this.checkedByteOffset(i);
        E prev, next;
        do
        {
            prev = this.getRaw(offset);
            next = updateFunction.apply(prev);
        } while (! this.compareAndSetRaw(offset, prev, next));
        return next;
    }

    @Override
    public E getAndAccumulate(final int i, final E x, final BinaryOperator<E> accumulatorFunction)
    {
        final long offset = this.checkedByteOffset(i);
        E prev, next;
        do
        {
            prev = this.getRaw(offset);
            next = accumulatorFunction.apply(prev, x);
        } while (! this.compareAndSetRaw(offset, prev, next));
        return prev;
    }

    @Override
    public E accumulateAndGet(final int i, final E x, final BinaryOperator<E> accumulatorFunction)
    {
        final long offset = this.checkedByteOffset(i);
        E prev, next;
        do
        {
            prev = this.getRaw(offset);
            next = accumulatorFunction.apply(prev, x);
        } while (! this.compareAndSetRaw(offset, prev, next));
        return next;
    }

    @Override
    public AtomicArray<E> getSubArray(final int offset, final int length)
    {
        if ((offset == 0) && (length == this.length()))
        {
            return this;
        }
        return new AtomicArrayPart<>(this, offset, length);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(final T[] a)
    {
        final int l = Math.min(a.length, this.length());
        for (int i = 0; i < l; i++)
        {
            a[i] = (T) this.get(i);
        }
        return a;
    }

    @Override
    public Object[] toArray()
    {
        final Object[] array = new Object[this.length()];
        for (int i = 0; i < array.length; i++)
        {
            array[i] = this.get(i);
        }
        return array;
    }

    @Override
    public String toString()
    {
        final int iMax = this.array.length - 1;
        if (iMax == - 1)
        {
            return "[]";
        }

        final StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++)
        {
            b.append(this.getRaw(byteOffset(i)));
            if (i == iMax)
            {
                return b.append(']').toString();
            }
            b.append(',').append(' ');
        }
    }

    /**
     * Reconstitutes the instance from a stream (that is, deserializes it).
     *
     * @param s input stream
     *
     * @throws java.io.IOException    io exception from input stream.
     * @throws ClassNotFoundException if one of class don't exist.
     */
    private void readObject(final java.io.ObjectInputStream s) throws java.io.IOException, ClassNotFoundException
    {
        // Note: This must be changed if any additional fields are defined
        Object a = s.readFields().get("array", null);
        if ((a == null) || ! a.getClass().isArray())
        {
            throw new java.io.InvalidObjectException("Not array type");
        }
        //noinspection ObjectEquality
        if (a.getClass() != Object[].class)
        {
            a = Arrays.copyOf((Object[]) a, Array.getLength(a), Object[].class);
        }
        unsafe.putObjectVolatile(this, arrayFieldOffset, a);
    }

    protected static long byteOffset(final int i)
    {
        return ((long) i << shift) + base;
    }

    static
    {
        try
        {
            arrayFieldOffset = unsafe.objectFieldOffset(AtomicReferenceArray.class.getDeclaredField("array"));
            base = unsafe.arrayBaseOffset(Object[].class);
            final int scale = unsafe.arrayIndexScale(Object[].class);
            if ((scale & (scale - 1)) != 0)
            {
                throw new Error("data type scale not a power of two");
            }
            shift = SHIFT_BASE - Integer.numberOfLeadingZeros(scale);
        } catch (final Exception e)
        {
            throw new Error(e);
        }
    }
}
