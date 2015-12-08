package org.diorite.inventory.recipe.craft;

import it.unimi.dsi.fastutil.chars.Char2ObjectMap;

/**
 * Represent simple pattern that use map of chars to recipe items as implementation.
 */
public interface CharMapCraftingRecipePattern extends CraftingRecipePattern
{
    /**
     * Returns pattern string array representation, like [aaa,bbb,ccc] where each letter represent other {@link CraftingRecipeItem}
     *
     * @return pattern string array representation, like [aaa,bbb,ccc] where each letter represent other {@link CraftingRecipeItem}
     */
    String[] getPattern();

    /**
     * Returns ingredients used by this pattern.
     *
     * @return ingredients used by this pattern.
     */
    Char2ObjectMap<CraftingRecipeItem> getIngredients();

    /**
     * Returns ingredient for given char, may return null for unknown char.
     *
     * @param c char to get ingredient.
     *
     * @return ingredient for given char, may return null for unknown char.
     */
    CraftingRecipeItem getIngredient(char c);
}
