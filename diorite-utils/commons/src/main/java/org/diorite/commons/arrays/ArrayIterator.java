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

package org.diorite.commons.arrays;

import java.util.Iterator;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Iterator implementation for array.
 *
 * @param <T>
 *         type of array
 */
public class ArrayIterator<T> implements Iterator<T>, Iterable<T>
{
    private final T[] array;
    private int currentIndex = 0;

    /**
     * Construct new array iterator for given object.
     *
     * @param array
     *         array to use in iterator.
     */
    private ArrayIterator(T[] array)
    {
        this.array = array;
    }

    @Override
    public boolean hasNext()
    {
        return this.currentIndex < this.array.length;
    }

    @SuppressWarnings("IteratorNextCanNotThrowNoSuchElementException")
    @Override
    public T next()
    {
        return this.array[this.currentIndex++];
    }

    @Override
    public void remove()
    {
        throw new UnsupportedOperationException("cannot remove items from an array");
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("array", this.array)
                                                                          .append("currentIndex", this.currentIndex).toString();
    }

    @Override
    public Iterator<T> iterator()
    {
        return new ArrayIterator<>(this.array);
    }

    public static <T> Iterable<T> of(T[] array)
    {
        return new ArrayIterator<>(array);
    }
}