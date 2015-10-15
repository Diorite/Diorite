package org.diorite.inventory.recipe;

import org.diorite.inventory.Inventory;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.item.ItemStack;

import gnu.trove.map.TShortObjectMap;

/**
 * Represent any recipe, crafting, furnance, etc...
 */
public interface Recipe
{
    /**
     * Check if items in inventory matches pattern, and return crafted item if yes. <br>
     * If items aren't valid, method will return null.
     *
     * @param inv inventory to check.
     *
     * @return crafting result if items in inventory matches pattern, or null.
     */
    ItemStack craft(final Inventory inv);
//    {
//        final TShortObjectMap<SimpleRecipeItem> map = this.getValidItems();
//        final TShortObjectIterator<SimpleRecipeItem> it = map.iterator();
//        while (it.hasNext())
//        {
//            it.advance();
//            if (! it.value().isValid(inv.getItem(it.key())))
//            {
//                return null;
//            }
//        }
//    }

    /**
     * Returns map with valid recipe items, where key is slot id.
     *
     * @return map with valid recipe items, where key is slot id.
     */
    TShortObjectMap<SimpleRecipeItem> getValidItems();

    /**
     * Returns map with valid recipe items, where key is slot id.
     *
     * @return map with valid recipe items, where key is slot id.
     */
    TShortObjectMap<ItemStack> getResultItems();

    /**
     * Returns main result item, on slot 0.
     *
     * @return main result item, on slot 0.
     */
    ItemStack getMainResultItem();

    /**
     * Returns inventory with pattern and result items.
     *
     * @return inventory with pattern and result items.
     */
    Inventory getPatternInventory();

    /**
     * Returns type of inventory for this recipe.
     *
     * @return type of inventory for this recipe.
     */
    InventoryType getCraftingInventoryType();
}
