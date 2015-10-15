package org.diorite.inventory;

import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.slot.Slot;

/**
 * An GridInventory is an {@link Inventory}
 * which is ordered into a coherent grid format, meaning that its slots can be
 * referred to by X-Y coordinates as well as single indices.
 */
public interface GridInventory extends Inventory
{

    /**
     * Gets the number of columns in the inventory.
     *
     * @return The width of this ItemGrid.
     */
    int getColumns();

    /**
     * Gets the number of rows in the inventory.
     *
     * @return The height of this ItemGrid.
     */
    int getRows();

    default int getSlotIndex(final int x, final int y)
    {
        if ((x >= this.getColumns()) || (y >= this.getRows()))
        {
            return - 1;
        }
        return x + (y * this.getColumns());
    }

    /**
     * Replace item on given slot, only if it matches (==) given item.
     * NOTE: this is atomic operation.
     *
     * @param x        x coordinate
     * @param y        y coordinate
     * @param excepted item to replace.
     * @param newItem  replacement.
     *
     * @return true if item was replaced.
     *
     * @throws IllegalArgumentException if excepted item isn't impl version of IItemStack, so it can't be == to any item from inventory.
     */
    default boolean atomicReplace(final int x, final int y, final ItemStack excepted, final ItemStack newItem) throws IllegalArgumentException
    {
        final int slot = this.getSlotIndex(x, y);
        return (slot != - 1) && this.atomicReplace(slot, excepted, newItem);
    }

    /**
     * Clears out a particular slot in the index.
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    default void clear(final int x, final int y)
    {
        final int slot = this.getSlotIndex(x, y);
        if (slot == - 1)
        {
            return;
        }
        this.clear(slot);
    }

    /**
     * Returns the IItemStack found in the slot at the given index
     *
     * @param x x coordinate
     * @param y y coordinate
     *
     * @return The IItemStack in the slot
     */
    default ItemStack getItem(final int x, final int y)
    {
        final int slot = this.getSlotIndex(x, y);
        if (slot == - 1)
        {
            return null;
        }
        return this.getItem(slot);
    }

    /**
     * Stores the IItemStack at the given index of the inventory.
     *
     * @param x    x coordinate
     * @param y    y coordinate
     * @param item The IItemStack to set
     *
     * @return previous itemstack in this slot.
     */
    default ItemStack setItem(final int x, final int y, final ItemStack item)
    {
        final int slot = this.getSlotIndex(x, y);
        if (slot == - 1)
        {
            return null;
        }
        return this.setItem(slot, item);
    }

    /**
     * Get and remove the stack at the supplied position in this Inventory.
     *
     * @param x x coordinate
     * @param y y coordinate
     *
     * @return ItemStack at the specified position or null if the slot is empty or out of bounds
     *
     * @see Inventory#poll(int)
     */
    default ItemStack poll(final int x, final int y)
    {
        final int slot = this.getSlotIndex(x, y);
        if (slot == - 1)
        {
            return null;
        }
        return this.poll(slot);
    }

    /**
     * Get the {@link Slot} at the specified position.
     *
     * @param x x coordinate
     * @param y y coordinate
     *
     * @return {@link Slot} at the specified position or null if the coordinates are out of bounds
     */
    default Slot getSlot(final int x, final int y)
    {
        final int slot = this.getSlotIndex(x, y);
        if (slot == - 1)
        {
            return null;
        }
        return this.getSlot(slot);
    }

}