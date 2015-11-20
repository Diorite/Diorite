package org.diorite.inventory.recipe.craft;

import org.diorite.inventory.GridInventory;
import org.diorite.inventory.item.ItemStack;

import gnu.trove.map.TShortObjectMap;

/**
 * Represent results after checking if recipe is valid for given inventory.
 *
 * @see Recipe#isValid(GridInventory)
 */
public interface RecipeCheckResult
{
    /**
     * Returns matched recipe.
     *
     * @return matched recipe.
     */
    Recipe getRecipe();

    /**
     * Returns result itemstack.
     *
     * @return result itemstack.
     */
    ItemStack getResult();

    /**
     * Returns array of items that should be removed from inventory on craft. <br>
     * May contains null elements.
     *
     * @return array of items that should be removed from inventory on craft.
     */
    ItemStack[] getItemsToConsume();

    /**
     * Returns items that should be changed in inventory.
     *
     * @return items that should be changed in inventory.
     */
    TShortObjectMap<ItemStack> getOnCraft();
}
