package org.diorite.inventory.recipe.craft;

import org.diorite.inventory.recipe.SemiShapedRecipe;

public interface SemiShapedCraftingRecipe extends SemiShapedRecipe, ShapedCraftingRecipe, ShapelessCraftingRecipe
{
    @Override
    default long getPriority()
    {
        return DEFAULT_RECIPE_PRIORITY;
    }
}
