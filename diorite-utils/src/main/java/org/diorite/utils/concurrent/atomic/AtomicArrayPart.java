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

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represent part of {@link AtomicArrayBase} or other {@link AtomicArray}.
 *
 * @param <E> type of array.
 */
public class AtomicArrayPart<E> implements AtomicArray<E>
{
    /**
     * Reference to base array.
     */
    protected final AtomicArray<E> base;
    /**
     * Offset between this array and base array.
     */
    protected final int            offset;
    /**
     * Length of this array.
     */
    protected final int            length;

    /**
     * Construct new atomic array part for given base array, offset and length.
     *
     * @param base   reference to base array.
     * @param offset offset between this array and base array.
     * @param length length of this array.
     */
    protected AtomicArrayPart(final AtomicArray<E> base, final int offset, final int length)
    {
        Validate.isTrue(offset >= 0, "offset can't be negative!");
        Validate.isTrue(length > 0, "length must be positive!");
        Validate.isTrue(offset < base.length(), "offset can't be bigger than array. (offset: " + offset + ", base: " + base.length() + ")");
        Validate.isTrue((length + offset) <= base.length(), "base array is too small for this offset and length. (offset: " + offset + ", length: " + length + ", base: " + base.length() + ")");
        this.base = base;
        this.offset = offset;
        this.length = length;
    }

    /**
     * Construct new atomic array part for given base array and offset, length will be set to maximum possible value.
     *
     * @param base   reference to base array.
     * @param offset offset between this array and base array.
     */
    protected AtomicArrayPart(final AtomicArray<E> base, final int offset)
    {
        this(base, offset, base.length() - offset);
    }

    /**
     * Returns base array for this part of array.
     *
     * @return base array for this part of array.
     */
    protected AtomicArray<E> getBase()
    {
        return this.base;
    }

    /**
     * Returns index of element in base array.
     *
     * @param i index to add offset to.
     *
     * @return index of element in base array.
     */
    protected int addOffset(final int i)
    {
        if (i >= this.length)
        {
            throw new IndexOutOfBoundsException("Index out of bounds, index: " + i + ", length: " + this.length);
        }
        return this.offset + i;
    }

    @Override
    public int length()
    {
        return this.length;
    }

    @Override
    public int offset()
    {
        return this.offset;
    }

    @Override
    public E get(final int i)
    {
        return this.base.get(this.addOffset(i));
    }

    @Override
    public void set(final int i, final E newValue)
    {
        this.base.set(this.addOffset(i), newValue);
    }

    @Override
    public void lazySet(final int i, final E newValue)
    {
        this.base.lazySet(this.addOffset(i), newValue);
    }

    @Override
    public E getAndSet(final int i, final E newValue)
    {
        return this.base.getAndSet(this.addOffset(i), newValue);
    }

    @Override
    public boolean compareAndSet(final int i, final E expect, final E update)
    {
        return this.base.compareAndSet(this.addOffset(i), expect, update);
    }

    @Override
    public boolean weakCompareAndSet(final int i, final E expect, final E update)
    {
        return this.base.weakCompareAndSet(this.addOffset(i), expect, update);
    }

    @Override
    public E getAndUpdate(final int i, final UnaryOperator<E> updateFunction)
    {
        return this.base.getAndUpdate(this.addOffset(i), updateFunction);
    }

    @Override
    public E updateAndGet(final int i, final UnaryOperator<E> updateFunction)
    {
        return this.base.updateAndGet(this.addOffset(i), updateFunction);
    }

    @Override
    public E getAndAccumulate(final int i, final E x, final BinaryOperator<E> accumulatorFunction)
    {
        return this.base.getAndAccumulate(this.addOffset(i), x, accumulatorFunction);
    }

    @Override
    public E accumulateAndGet(final int i, final E x, final BinaryOperator<E> accumulatorFunction)
    {
        return this.base.accumulateAndGet(this.addOffset(i), x, accumulatorFunction);
    }

    @Override
    public AtomicArray<E> getSubArray(final int offset, final int length)
    {
        if ((offset == 0) && (length == this.length()))
        {
            return this;
        }
        Validate.isTrue(offset >= 0, "offset can't be negative!");
        // base array is used, to avoid creating nested wrappers.
        return new AtomicArrayPart<>(this.base, this.offset + offset, length);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(final T[] a)
    {
        final int l = Math.min(a.length, this.length);
        for (int i = 0; i < l; i++)
        {
            a[i] = (T) this.get(i);
        }
        return a;
    }

    @Override
    public Object[] toArray()
    {
        final Object[] array = new Object[this.length];
        for (int i = 0; i < array.length; i++)
        {
            array[i] = this.get(i);
        }
        return array;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("base", this.base).append("offset", this.offset).append("length", this.length).toString();
    }
}
