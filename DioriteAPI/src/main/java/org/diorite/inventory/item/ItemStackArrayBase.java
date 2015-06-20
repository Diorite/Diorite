package org.diorite.inventory.item;


import java.util.Iterator;

import org.diorite.utils.concurrent.atomic.AtomicArrayBase;

class ItemStackArrayBase extends AtomicArrayBase<ItemStack> implements ItemStackArray
{
    ItemStackArrayBase(final int length)
    {
        super(length);
    }

    ItemStackArrayBase(final ItemStack[] array)
    {
        super(array);
    }

    @Override
    public ItemStackArray getSubArray(final int offset, final int length)
    {
        if ((offset == 0) && (length == this.length()))
        {
            return this;
        }
        return new ItemStackArrayPart(this, offset, length);
    }

    @Override
    public Iterator<ItemStack> iterator()
    {
        return new Iterator<ItemStack>()
        {
            private int currentIndex = 0;

            @Override
            public boolean hasNext()
            {
                return (this.currentIndex < ItemStackArrayBase.this.length()) && (ItemStackArrayBase.this.get(this.currentIndex) != null);
            }

            @Override
            public ItemStack next()
            {
                return ItemStackArrayBase.this.get(this.currentIndex++);
            }

            @Override
            public void remove()
            {
                throw new UnsupportedOperationException();
            }
        };
    }
}
