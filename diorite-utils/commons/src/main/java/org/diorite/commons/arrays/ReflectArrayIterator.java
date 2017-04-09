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

import java.lang.reflect.Array;
import java.util.Iterator;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Iterator implementation for array based on reflections, perfect if you don't know type of array and it can be a primitive type.
 */
public final class ReflectArrayIterator<T> implements Iterator<T>
{
    private final Object array;
    private int currentIndex = 0;

    private ReflectArrayIterator(Object array)
    {
        this.array = array;
    }

    @Override
    public boolean hasNext()
    {
        return this.currentIndex < Array.getLength(this.array);
    }

    @SuppressWarnings({"IteratorNextCanNotThrowNoSuchElementException", "unchecked"})
    @Override
    public T next()
    {
        return (T) Array.get(this.array, this.currentIndex++);
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

    /**
     * Construct new iterator for given array object, if object isn't array {@link IllegalArgumentException} will be thrown.
     *
     * @param array
     *         array to use in iterator.
     *
     * @return created iterator.
     */
    public static <T> Iterator<T> iteratorOf(Object array)
    {
        if (! array.getClass().isArray())
        {
            throw new IllegalArgumentException("not an array");
        }
        return new ReflectArrayIterator<>(array);
    }

    /**
     * Construct new iterable for given array object, if object isn't array {@link IllegalArgumentException} will be thrown.
     *
     * @param array
     *         array to use in iterator.
     *
     * @return created iterable.
     */
    public static <T> Iterable<T> iterableOf(Object array)
    {
        return () -> iteratorOf(array);
    }
}