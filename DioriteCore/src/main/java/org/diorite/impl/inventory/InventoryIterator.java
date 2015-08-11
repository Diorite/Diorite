package org.diorite.impl.inventory;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.Inventory;
import org.diorite.inventory.item.ItemStack;

public class InventoryIterator implements ListIterator<ItemStack>
{
    private final Inventory inventory;
    private       int       nextIndex;
    private       Boolean   lastDirection;

    InventoryIterator(final Inventory inventory)
    {
        this.inventory = inventory;
        this.nextIndex = 0;
    }

    InventoryIterator(final Inventory inventory, final int index)
    {
        this.inventory = inventory;
        this.nextIndex = index;
    }

    @Override
    public boolean hasNext()
    {
        return this.nextIndex < this.inventory.size();
    }

    @Override
    public ItemStack next()
    {
        this.lastDirection = true;
        if ((this.nextIndex >= this.inventory.size()) || (this.nextIndex < 0))
        {
            throw new NoSuchElementException("Index out of bounds, " + this.nextIndex + " from " + this.inventory.size());
        }
        return this.inventory.getItem(this.nextIndex++);
    }

    @Override
    public boolean hasPrevious()
    {
        return this.nextIndex > 0;
    }

    @Override
    public ItemStack previous()
    {
        this.lastDirection = false;
        return this.inventory.getItem(-- this.nextIndex);
    }

    @Override
    public int nextIndex()
    {
        return this.nextIndex;
    }

    @Override
    public int previousIndex()
    {
        return this.nextIndex - 1;
    }

    @Override
    public void remove()
    {
        throw new UnsupportedOperationException("Can't change the size of an inventory!");
    }

    @Override
    public void set(final ItemStack item)
    {
        if (this.lastDirection == null)
        {
            throw new IllegalStateException("No current item!");
        }
        final int i = this.lastDirection ? (this.nextIndex - 1) : this.nextIndex;
        this.inventory.setItem(i, item);
    }

    @Override
    public void add(final ItemStack item)
    {
        throw new UnsupportedOperationException("Can't change the size of an inventory!");
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("inventory", this.inventory).append("nextIndex", this.nextIndex).append("lastDirection", this.lastDirection).toString();
    }
}
