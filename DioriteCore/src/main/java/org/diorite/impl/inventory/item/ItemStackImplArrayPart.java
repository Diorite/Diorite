package org.diorite.impl.inventory.item;

import org.apache.commons.lang3.Validate;

import org.diorite.utils.concurrent.atomic.AtomicArray;
import org.diorite.utils.concurrent.atomic.AtomicArrayPart;

class ItemStackImplArrayPart extends AtomicArrayPart<ItemStackImpl> implements ItemStackImplArray
{
    protected ItemStackImplArrayPart(final AtomicArray<ItemStackImpl> base, final int offset, final int length)
    {
        super(base, offset, length);
    }

    @Override
    public ItemStackImplArray getSubArray(final int offset, final int length)
    {
        if ((offset == 0) && (length == this.length()))
        {
            return this;
        }
        Validate.isTrue(offset >= 0, "offset can't be negative!");
        // base array is used, to avoid creating nested wrappers.
        return new ItemStackImplArrayPart(this.base, this.offset + offset, length);
    }
}
