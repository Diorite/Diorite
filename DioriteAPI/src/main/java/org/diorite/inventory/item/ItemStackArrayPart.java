package org.diorite.inventory.item;

import org.apache.commons.lang3.Validate;

import org.diorite.utils.concurrent.atomic.AtomicArray;
import org.diorite.utils.concurrent.atomic.AtomicArrayPart;

class ItemStackArrayPart extends AtomicArrayPart<ItemStack> implements ItemStackArray
{
    protected ItemStackArrayPart(final AtomicArray<ItemStack> base, final int offset, final int length)
    {
        super(base, offset, length);
    }

    @Override
    public ItemStackArray getSubArray(final int offset, final int length)
    {
        if ((offset == 0) && (length == this.length()))
        {
            return this;
        }
        Validate.isTrue(offset >= 0, "offset can't be negative!");
        // base array is used, to avoid creating nested wrappers.
        return new ItemStackArrayPart(this.base, this.offset + offset, length);
    }
}
