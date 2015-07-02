package org.diorite.impl.inventory.item;

import org.diorite.utils.concurrent.atomic.AtomicArray;

/**
 * Atomic itemstack array.
 */
public interface ItemStackImplArray extends AtomicArray<ItemStackImpl>
{
    @Override
    ItemStackImplArray getSubArray(int offset, int length);

    @Override
    default ItemStackImplArray getSubArray(final int offset)
    {
        return this.getSubArray(offset, this.length() - offset);
    }

    default ItemStackImpl getOrNull(final int index)
    {
        if (index >= this.length())
        {
            return null;
        }
        return this.get(index);
    }

    static ItemStackImplArray create(final ItemStackImpl[] items)
    {
        return new ItemStackImplArrayBase(items);
    }

    static ItemStackImplArray create(final int size)
    {
        return new ItemStackImplArrayBase(size);
    }
}
