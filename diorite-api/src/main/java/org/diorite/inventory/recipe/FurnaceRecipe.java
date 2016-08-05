package org.diorite.inventory.recipe;

import java.util.Arrays;
import java.util.List;

import org.diorite.inventory.item.ItemStack;

/**
 * Represents a furnace recipe.
 */
public class FurnaceRecipe implements Recipe
{
    private final ItemStack ingredient;
    private final ItemStack result;

    public FurnaceRecipe(final ItemStack ingredient, final ItemStack result)
    {
        this.ingredient = ingredient;
        this.result = result;
    }

    public ItemStack getIngredient()
    {
        return this.ingredient;
    }

    @Override
    public List<ItemStack> getResult()
    {
        return Arrays.asList(this.result);
    }
}
