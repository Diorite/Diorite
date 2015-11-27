package org.diorite.inventory.recipe;

import java.util.Iterator;

import org.diorite.inventory.GridInventory;
import org.diorite.inventory.recipe.craft.Recipe;
import org.diorite.inventory.recipe.craft.RecipeCheckResult;

/**
 * Class used to manage all diorite (including vanilla) crafting recipes.
 */
public interface RecipeManager
{
    /**
     * Returns new instance of recipe builder.
     *
     * @return new instance of recipe builder.
     */
    RecipeBuilder builder();

    /**
     * Adds new recipe to this manager, returns true if recipe was added.
     *
     * @param recipe recipe to be added.
     *
     * @return true if recipe was added.
     */
    boolean add(Recipe recipe);

    /**
     * Remove given recipe from this manager, return true if any recipe was removed.
     *
     * @param recipe recipe to be added.
     *
     * @return true if any recipe was removed.
     */
    boolean remove(Recipe recipe);

    /**
     * Removes all recipes and add only vanilla recipes again.
     */
    void reset();

    /**
     * Removes ALL recipes.
     */
    void clear();

    /**
     * Returns iterator of recipes, may be used to remove recipes.
     *
     * @return iterator of recipes, may be used to remove recipes.
     */
    Iterator<Recipe> recipeIterator();

    /**
     * Try find possible recipe for given inventory. <br>
     * Returns object that contains matched recipe, crafted itemstack and items to remove or change,
     * or returns null if it didn't find any recipe.
     *
     * @param grid inventory to be used.
     *
     * @return object that contains matched recipe, crafted itemstack and items to remove or change.
     */
    RecipeCheckResult matchRecipe(GridInventory grid);
}
