package org.diorite.inventory.item;


import org.diorite.utils.concurrent.AtomicArrayBase;

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
}
