package org.diorite.utils.collections.arrays;

/**
 * Simple extension for {@link ArrayIterator} with object as generic type. <br>
 * Simpler to use if you don't know type of array.
 */
public class ObjectArrayIterator extends ArrayIterator<Object>
{
    /**
     * Construct new array iterator for given object.
     *
     * @param array array to use in iterator.
     */
    public ObjectArrayIterator(final Object[] array)
    {
        super(array);
    }
}