package org.diorite.inventory.recipe.craft;

import org.diorite.inventory.GridInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.RecipeCheckResult;

/**
 * Represent results after checking if recipe is valid for given inventory.
 *
 * @see CraftingRecipe#isMatching(GridInventory)
 */
public interface CraftingRecipeCheckResult extends RecipeCheckResult
{
    /**
     * Returns matched recipe.
     *
     * @return matched recipe.
     */
    @Override
    CraftingRecipe getRecipe();

    /**
     * Returns result itemstack.
     *
     * @return result itemstack.
     */
    ItemStack getResult();

    /**
     * Returns crafting grid of items that should be removed from inventory on craft. <br>
     * Grid should match crafting slots.
     *
     * @return crafting grid of items that should be removed from inventory on craft.
     */
    CraftingGrid getItemsToConsume();
}
