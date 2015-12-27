package org.diorite.inventory.recipe.craft;

import java.util.Iterator;

/**
 * Class used to manage all diorite (including vanilla) crafting recipes.
 */
public interface CraftingRecipeManager extends CraftingRecipeGroup
{
    /**
     * Clear all crafting recipes and re-add default ones.
     */
    void resetCraftingRecipes();

    /**
     * Clears all crafting recipes.
     */
    void clearCraftingRecipes();

    /**
     * Returns iterator of crafting recipes, this iterator will flat all groups.
     *
     * @return iterator of crafting recipes, this iterator will flat all groups.
     */
    Iterator<CraftingRecipe> craftingRecipeIterator();
}
