package org.diorite.inventory.recipe;

import org.diorite.inventory.item.ItemStack;

import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;

/**
 * Represent results after checking if recipe is valid for given inventory.
 */
public interface RecipeCheckResult
{
    /**
     * Returns matched recipe.
     *
     * @return matched recipe.
     */
    Recipe getRecipe();

    /**
     * Returns items that should be changed in inventory.
     *
     * @return items that should be changed in inventory.
     */
    Short2ObjectMap<ItemStack> getOnCraft();
}
