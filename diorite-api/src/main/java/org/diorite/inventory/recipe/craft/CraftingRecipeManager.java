package org.diorite.inventory.recipe.craft;

import java.util.Iterator;

/**
 * Class used to manage all diorite (including vanilla) crafting recipes.
 */
public interface CraftingRecipeManager extends CraftingRecipeGroup
{
    void resetCraftingRecipes();

    void clearCraftingRecipes();

    Iterator<CraftingRecipe> craftingRecipeIterator();
}
