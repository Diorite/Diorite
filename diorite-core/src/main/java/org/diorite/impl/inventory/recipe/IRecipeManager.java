package org.diorite.impl.inventory.recipe;

import java.util.Map.Entry;

import org.diorite.inventory.GridInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.RecipeManager;
import org.diorite.inventory.recipe.craft.Recipe;

public interface IRecipeManager extends RecipeManager
{
    void addDefaultRecipes();
}
