package org.diorite.impl.inventory.recipe.craft;

import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.craft.CraftingGrid;

public class CraftingGridImpl implements CraftingGrid
{
    private final int         rows;
    private final int         columns;
    private final ItemStack[] items;

    public CraftingGridImpl(final int rows, final int columns, final ItemStack[] items)
    {
        if (items.length != (rows * columns))
        {
            throw new IllegalArgumentException("items length don't match row*columns: items: " + items.length + ", rows: " + rows + ", columns: " + columns);
        }
        this.rows = rows;
        this.columns = columns;
        this.items = items;
    }

    public CraftingGridImpl(final int rows, final int columns)
    {
        this.rows = rows;
        this.columns = columns;
        this.items = new ItemStack[rows * columns];
    }

    @Override
    public int getColumns()
    {
        return this.columns;
    }

    @Override
    public int getRows()
    {
        return this.rows;
    }

    @Override
    public ItemStack[] getItems()
    {
        return this.items;
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public CraftingGrid clone()
    {
        final ItemStack[] array = new ItemStack[this.rows * this.columns];
        int i = 0;
        for (final ItemStack item : this.items)
        {
            array[i++] = (item == null) ? null : item.clone();
        }
        return new CraftingGridImpl(this.rows, this.columns, array);
    }
}
