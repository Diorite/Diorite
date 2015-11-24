package org.diorite.impl.inventory.recipe.craft;

import java.util.Arrays;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.GridInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.craft.Recipe;
import org.diorite.inventory.recipe.craft.RecipeCheckResult;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

/**
 * Represent results after checking if recipe is valid for given inventory.
 *
 * @see Recipe#isMatching(GridInventory)
 */
public class RecipeCheckResultImpl implements RecipeCheckResult
{
    private final Recipe                     recipe;
    private final ItemStack                  result;
    private final ItemStack[][]              itemsToConsume;
    private final TShortObjectMap<ItemStack> onCraft;

    /**
     * Construct new CraftResult for given recipe and items.
     *
     * @param recipe         matched recipe.
     * @param result         result item stack.
     * @param itemsToConsume array of items that should be removed from inventory on craft.
     * @param onCraft        map of items that should be changed in inventory
     */
    public RecipeCheckResultImpl(final Recipe recipe, final ItemStack result, final ItemStack[][] itemsToConsume, final TShortObjectMap<ItemStack> onCraft)
    {
        this.recipe = recipe;
        this.result = result;
        this.itemsToConsume = itemsToConsume;
        this.onCraft = onCraft;
    }

    /**
     * Construct new CraftResult for given recipe and items.
     *
     * @param recipe         matched recipe.
     * @param result         result item stack.
     * @param itemsToConsume array of items that should be removed from inventory on craft.
     */
    public RecipeCheckResultImpl(final Recipe recipe, final ItemStack result, final ItemStack[][] itemsToConsume)
    {
        this.recipe = recipe;
        this.result = result;
        this.itemsToConsume = itemsToConsume;
        this.onCraft = new TShortObjectHashMap<>(2, .5F, Short.MIN_VALUE);
    }

    @Override
    public Recipe getRecipe()
    {
        return this.recipe;
    }

    @Override
    public ItemStack getResult()
    {
        return this.result;
    }

    @Override
    public ItemStack[][] getItemsToConsume()
    {
        return this.itemsToConsume;
    }

    @Override
    public TShortObjectMap<ItemStack> getOnCraft()
    {
        return this.onCraft;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof RecipeCheckResultImpl))
        {
            return false;
        }

        final RecipeCheckResultImpl that = (RecipeCheckResultImpl) o;

        return this.recipe.equals(that.recipe) && this.result.equals(that.result) && Arrays.equals(this.itemsToConsume, that.itemsToConsume) && ! ((this.onCraft != null) ? ! this.onCraft.equals(that.onCraft) : (that.onCraft != null));

    }

    @Override
    public int hashCode()
    {
        int result1 = this.recipe.hashCode();
        result1 = (31 * result1) + this.result.hashCode();
        result1 = (31 * result1) + Arrays.hashCode(this.itemsToConsume);
        result1 = (31 * result1) + ((this.onCraft != null) ? this.onCraft.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("recipe", this.recipe).append("result", this.result).append("itemsToConsume", this.itemsToConsume).append("onCraft", this.onCraft).toString();
    }
}
