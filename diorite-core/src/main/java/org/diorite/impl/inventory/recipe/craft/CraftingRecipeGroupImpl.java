package org.diorite.impl.inventory.recipe.craft;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.function.Predicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.inventory.recipe.RecipeComparator;
import org.diorite.inventory.GridInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.craft.CraftingRecipe;
import org.diorite.inventory.recipe.craft.CraftingRecipeBuilder;
import org.diorite.inventory.recipe.craft.CraftingRecipeCheckResult;
import org.diorite.inventory.recipe.craft.CraftingRecipeGroup;

public class CraftingRecipeGroupImpl extends CraftingRecipeImpl implements CraftingRecipeGroup
{
    private final Collection<CraftingRecipe> recipes = new TreeSet<>(RecipeComparator.INSTANCE);
    private final Collection<CraftingRecipe> cpy;
    private final Predicate<GridInventory>   predicate;

    public CraftingRecipeGroupImpl(final Predicate<GridInventory> predicate, final Collection<CraftingRecipe> recipes, final ItemStack result, final long priority, final boolean vanilla)
    {
        super(Collections.singletonList(result), priority, vanilla, null);
        this.predicate = predicate;
        this.recipes.addAll(recipes);
        this.cpy = recipes;
    }

    @Override
    public CraftingRecipeBuilder craftingBuilder()
    {
        return new CraftingRecipeBuilderImpl();
    }

    @Override
    public boolean add(final CraftingRecipe recipe)
    {
        return this.recipes.add(recipe);
    }

    @Override
    public boolean remove(final CraftingRecipe recipe)
    {
        return this.recipes.remove(recipe);
    }

    @Override
    public void reset()
    {
        this.clear();
        this.recipes.addAll(this.cpy);
    }

    @Override
    public void clear()
    {
        this.recipes.clear();
    }

    @Override
    public Iterator<CraftingRecipe> recipeIterator()
    {
        return this.recipes.iterator();
    }

    @Override
    public CraftingRecipeCheckResult matchCraftingRecipe(final GridInventory grid)
    {
        if (! this.predicate.test(grid))
        {
            return null;
        }
        for (final CraftingRecipe recipe : this.recipes)
        {
            final CraftingRecipeCheckResult result = recipe.isMatching(grid);
            if (result != null)
            {
                return result;
            }
        }
        return null;
    }

    @Override
    public CraftingRecipeCheckResult isMatching(final GridInventory inventory)
    {
        return this.matchCraftingRecipe(inventory);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("recipes", this.recipes).append("predicate", this.predicate).toString();
    }
}
