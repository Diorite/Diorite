package org.diorite.utils.collections.arrays;

import java.util.Iterator;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Iterator implementation for array.
 *
 * @param <T> type of array
 */
public class ArrayIterator<T> implements Iterator<T>, Iterable<T>
{
    private final T[] array;
    private int currentIndex = 0;

    /**
     * Construct new array iterator for given object.
     *
     * @param array array to use in iterator.
     */
    public ArrayIterator(final T[] array)
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("array", this.array).append("currentIndex", this.currentIndex).toString();
    }

    @Override
    public Iterator<T> iterator()
    {
        return new ArrayIterator<>(this.array);
    }
}