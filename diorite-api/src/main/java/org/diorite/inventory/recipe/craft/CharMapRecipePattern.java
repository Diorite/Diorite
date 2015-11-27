package org.diorite.inventory.recipe.craft;

import org.diorite.inventory.recipe.RecipeItem;

import gnu.trove.map.TCharObjectMap;

/**
 * Represent simple pattern that use map of chars to recipe items as implementation.
 */
public interface CharMapRecipePattern extends RecipePattern
{
    /**
     * Returns pattern string array representation, like [aaa,bbb,ccc] where each letter represent other {@link RecipeItem}
     *
     * @return pattern string array representation, like [aaa,bbb,ccc] where each letter represent other {@link RecipeItem}
     */
    String[] getPattern();

    /**
     * Returns ingredients used by this pattern.
     *
     * @return ingredients used by this pattern.
     */
    TCharObjectMap<RecipeItem> getIngredients();

    /**
     * Returns ingredient for given char, may return null for unknown char.
     *
     * @param c char to get ingredient.
     *
     * @return ingredient for given char, may return null for unknown char.
     */
    RecipeItem getIngredient(char c);
}
