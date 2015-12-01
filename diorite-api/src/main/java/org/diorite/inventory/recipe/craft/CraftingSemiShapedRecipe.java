package org.diorite.inventory.recipe.craft;

import org.diorite.inventory.recipe.SemiShapedRecipe;

public interface CraftingSemiShapedRecipe extends SemiShapedRecipe, ShapedCraftingRecipe, ShapelessCraftingRecipe
{
    @Override
    default long getPriority()
    {
        return DEFAULT_RECIPE_PRIORITY;
    }
}
