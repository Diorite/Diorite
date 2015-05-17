package org.diorite.utils.collections;

import java.lang.reflect.Array;
import java.util.Iterator;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ReflectArrayIterator implements Iterator<Object>, Iterable<Object>
{
    private final Object array;
    private int currentIndex = 0;

    public ReflectArrayIterator(final Object array)
    {
        if (! array.getClass().isArray())
        {
            throw new IllegalArgumentException("not an array");
        }
        else
        {
            this.array = array;
        }
    }

    @Override
    public boolean hasNext()
    {
        return this.currentIndex < Array.getLength(this.array);
    }

    @SuppressWarnings("IteratorNextCanNotThrowNoSuchElementException")
    @Override
    public Object next()
    {
        return Array.get(this.array, this.currentIndex++);
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
    public Iterator<Object> iterator()
    {
        return this;
    }
}