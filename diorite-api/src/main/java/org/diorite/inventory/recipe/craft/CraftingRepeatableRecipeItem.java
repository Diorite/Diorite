package org.diorite.inventory.recipe.craft;

import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.RepeatableRecipeItem;

public interface CraftingRepeatableRecipeItem extends RepeatableRecipeItem, CraftingRecipeItem
{
    /**
     * Transform result item using grid of all ingredients.
     *
     * @param currentResult current result item.
     * @param ingredients   grid of all ingredients.
     *
     * @return transformed result item.
     */
    ItemStack transform(ItemStack currentResult, CraftingGrid ingredients);
}

