package org.diorite.inventory.recipe;

import org.diorite.inventory.item.ItemStack;

/**
 * Represent recipe item, it may use custom validate code.
 */
public interface RecipeItem
{
    /**
     * Check if given item is valid for this recipe item stack.
     *
     * @param item item to check.
     *
     * @return return true if item is valid.
     */
    default boolean isValid(final ItemStack item)
    {
        return this.getItem().isSimilar(item);
    }

    /**
     * Returns valid item for this recipe item stack.
     *
     * @return valid item for this recipe item stack.
     */
    ItemStack getItem();
}
