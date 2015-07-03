package org.diorite.impl.inventory.item;


import org.diorite.utils.concurrent.atomic.AtomicArrayBase;

class ItemStackImplArrayBase extends AtomicArrayBase<ItemStackImpl> implements ItemStackImplArray
{
    private static final long serialVersionUID = 0;

    ItemStackImplArrayBase(final int length)
    {
        super(length);
    }

    ItemStackImplArrayBase(final ItemStackImpl[] array)
    {
        super(array);
    }

    @Override
    public ItemStackImplArray getSubArray(final int offset, final int length)
    {
        if ((offset == 0) && (length == this.length()))
        {
            return this;
        }
        return new ItemStackImplArrayPart(this, offset, length);
    }
}
