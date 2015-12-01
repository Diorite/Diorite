package org.diorite.impl.inventory.recipe;

import org.diorite.impl.inventory.recipe.craft.ICraftingRecipeManager;
import org.diorite.inventory.recipe.RecipeManager;

public interface IRecipeManager extends RecipeManager, ICraftingRecipeManager
{
    default void addDefaultRecipes()
    {
        this.addDefaultCraftingRecipes();
    }
}
