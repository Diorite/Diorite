package org.diorite.inventory.item;

import org.diorite.utils.concurrent.AtomicArray;

/**
 * Atomic itemstack array.
 */
public interface ItemStackArray extends AtomicArray<ItemStack>
{
    @Override
    ItemStackArray getSubArray(int offset, int length);

    @Override
    default ItemStackArray getSubArray(final int offset)
    {
        return this.getSubArray(offset, this.length() - offset);
    }

    default ItemStack getOrNull(final int index)
    {
        if (index >= this.length())
        {
            return null;
        }
        return this.get(index);
    }

    static ItemStackArray create(final ItemStack[] items)
    {
        return new ItemStackArrayBase(items);
    }

    static ItemStackArray create(final int size)
    {
        return new ItemStackArrayBase(size);
    }
}
