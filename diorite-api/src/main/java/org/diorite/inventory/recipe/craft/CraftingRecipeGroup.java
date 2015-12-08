package org.diorite.inventory.recipe.craft;

import java.util.Iterator;

import org.diorite.inventory.GridInventory;
import org.diorite.inventory.recipe.RecipeGroup;

/**
 * RecipeGroup for crafting recipes.
 *
 * @see org.diorite.inventory.recipe.RecipeGroup
 */
public interface CraftingRecipeGroup extends RecipeGroup, CraftingRecipe
{
    /**
     * Returns new instance of crafting recipe builder.
     *
     * @return new instance of crafting recipe builder.
     */
    CraftingRecipeBuilder craftingBuilder();

    /**
     * Adds new crafting recipe to this recipe group, returns true if recipe was added.
     *
     * @param recipe recipe to be added.
     *
     * @return true if recipe was added.
     */
    boolean add(CraftingRecipe recipe);

    /**
     * Remove given crafting recipe from this recipe group, return true if any recipe was removed.
     *
     * @param recipe recipe to be added.
     *
     * @return true if any recipe was removed.
     */
    boolean remove(CraftingRecipe recipe);

    /**
     * Returns iterator of recipes, may be used to remove recipes.
     *
     * @return iterator of recipes, may be used to remove recipes.
     */
    @Override
    Iterator<? extends CraftingRecipe> recipeIterator();

    /**
     * Try find possible recipe for given inventory. <br>
     * Returns object that contains matched recipe, crafted itemstack and items to remove or change,
     * or returns null if it didn't find any recipe.
     *
     * @param grid inventory to be used.
     *
     * @return object that contains matched recipe, crafted itemstack and items to remove or change.
     */
    CraftingRecipeCheckResult matchCraftingRecipe(GridInventory grid);
}
