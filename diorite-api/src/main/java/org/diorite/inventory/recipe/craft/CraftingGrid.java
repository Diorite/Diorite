package org.diorite.inventory.recipe.craft;

import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.ConsumedItems;

/**
 * Represent crafting grid, wrapper for ItemStack array, used in more advanced recipes.
 */
public interface CraftingGrid extends ConsumedItems, Cloneable
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

    /**
     * Returns slot index for given row/column.
     *
     * @param row    row of slot.
     * @param column column of slot.
     *
     * @return slot index for given row/column.
     */
    default int getSlotIndex(final int row, final int column)
    {
        if ((column >= this.getColumns()) || (row >= this.getRows()))
        {
            return - 1;
        }
        return column + (row * this.getColumns());
    }

    /**
     * Clears out a particular slot in the index.
     *
     * @param row    row index.
     * @param column column index.
     */
    default void clear(final int row, final int column)
    {
        final int slot = this.getSlotIndex(row, column);
        if (slot == - 1)
        {
            return;
        }
        this.getItems()[slot] = null;
    }

    /**
     * Returns the ItemStack found in the slot at the given index
     *
     * @param row    row index.
     * @param column column index.
     *
     * @return The ItemStack in the slot
     */
    default ItemStack getItem(final int row, final int column)
    {
        final int slot = this.getSlotIndex(row, column);
        if (slot == - 1)
        {
            return null;
        }
        return this.getItems()[slot];
    }

    /**
     * Returns and removes the ItemStack found in the slot at the given index
     *
     * @param row    row index.
     * @param column column index.
     *
     * @return The ItemStack in the slot
     */
    default ItemStack pullItem(final int row, final int column)
    {
        final int slot = this.getSlotIndex(row, column);
        if (slot == - 1)
        {
            return null;
        }
        final ItemStack[] array = this.getItems();
        final ItemStack item = array[slot];
        if (item == null)
        {
            return null;
        }
        array[slot] = null;
        return item;
    }

    /**
     * Stores the ItemStack at the given index of the inventory.
     *
     * @param row    row index.
     * @param column column index.
     * @param item   The ItemStack to set
     *
     * @return previous itemstack in this slot.
     */
    default ItemStack setItem(final int row, final int column, final ItemStack item)
    {
        final int slot = this.getSlotIndex(row, column);
        if (slot == - 1)
        {
            return null;
        }
        final ItemStack[] array = this.getItems();
        final ItemStack prev = array[slot];
        array[slot] = item;
        return prev;
    }

    /**
     * Returns copy of this inventory grid.
     *
     * @return copy of this inventory grid.
     */
    @Override
    CraftingGrid clone();
}
