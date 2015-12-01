package org.diorite.inventory.recipe;

import java.util.List;

import com.google.common.collect.Lists;

import org.diorite.inventory.item.ItemStack;

/**
 * Used in more advanced recipes to generate result item based on consumed items.
 */
public interface ConsumedItems extends Cloneable
{
    /**
     * Retruns raw array of items that you can edit.
     *
     * @return raw array of items that you can edit.
     */
    ItemStack[] getItems();

    /**
     * Returns list of items without null values.
     *
     * @return list of items without null values.
     */
    default List<ItemStack> getItemsList()
    {
        final List<ItemStack> list = Lists.newArrayList(this.getItems());
        list.removeIf(i -> i == null);
        return list;
    }

    /**
     * Returns copy of this consumed items.
     *
     * @return copy of this consumed items.
     */
    ConsumedItems clone();
}
