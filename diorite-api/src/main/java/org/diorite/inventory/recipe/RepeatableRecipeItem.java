package org.diorite.inventory.recipe;

import java.util.List;

import org.diorite.inventory.item.ItemStack;

/**
 * Represent recipe item that can be used multiple times in recipe.
 */
public interface RepeatableRecipeItem extends RecipeItem
{
    /**
     * Transform result item using list of repeated ingredients.
     *
     * @param currentResult current result item.
     * @param ingredients   list of repeated ingredients.
     *
     * @return transformed result item.
     */
    ItemStack transform(ItemStack currentResult, List<ItemStack> ingredients);
}
