package org.diorite.impl.inventory.recipe.craft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.Recipe;
import org.diorite.inventory.recipe.craft.CraftingGrid;
import org.diorite.inventory.recipe.craft.CraftingRecipeItem;

abstract class CraftingRecipeImpl extends PriorityCraftingRecipeImpl
{
    protected final ItemStack                                   result;
    protected final List<ItemStack>                             resultList;
    protected final BiFunction<Player, CraftingGrid, ItemStack> resultFunc;
//
//    protected RecipeImpl(final ItemStack result, final long priority, final boolean vanilla, final BiFunction<Player, CraftingGrid, ItemStack> resultFunc)
//    {
//        super(priority, vanilla);
//        Validate.notNull(resultFunc, "Result function can't be null.");
//        Validate.notNull(result, "Result item can't be null.");
//        this.result = result;
//        this.resultFunc = resultFunc;
//        this.resultList = Collections.singletonList(result.clone());
//    }

    protected CraftingRecipeImpl(final List<ItemStack> result, final long priority, final boolean vanilla, final BiFunction<Player, CraftingGrid, ItemStack> resultFunc)
    {
        super(priority, vanilla);
        Validate.notEmpty(result, "Result list can't be empty.");
        this.resultFunc = resultFunc;
        this.result = result.get(0);
        this.resultList = (result.size() == 1) ? Collections.singletonList(this.result) : Collections.unmodifiableList(new ArrayList<>(result));
    }

    protected static List<ItemStack> extractResults(final ItemStack result, final Collection<? extends CraftingRecipeItem> ingredients)
    {
        final List<ItemStack> results = new ArrayList<>(ingredients.size() + 1);
        results.add(result);
        for (final CraftingRecipeItem ingredient : ingredients)
        {
            if (ingredient.getReplacement() == null)
            {
                continue;
            }
            results.add(ingredient.getReplacement());
        }
        return results;
    }

    @SafeVarargs
    protected static List<ItemStack> extractResults(final ItemStack result, final Collection<? extends CraftingRecipeItem>... ingredients)
    {
        final List<ItemStack> results = new ArrayList<>((ingredients.length * 5) + 1);
        results.add(result);
        for (final Collection<? extends CraftingRecipeItem> ingredientsList : ingredients)
        {
            for (final CraftingRecipeItem ingredient : ingredientsList)
            {
                if (ingredient.getReplacement() == null)
                {
                    continue;
                }
                results.add(ingredient.getReplacement());
            }
        }
        return results;
    }

    protected static List<ItemStack> extractResults(final ItemStack result, final CraftingRecipeItem... ingredients)
    {
        final List<ItemStack> results = new ArrayList<>(ingredients.length + 1);
        results.add(result);
        for (final CraftingRecipeItem ingredient : ingredients)
        {
            if (ingredient.getReplacement() == null)
            {
                continue;
            }
            results.add(ingredient.getReplacement());
        }
        return results;
    }

    protected static List<ItemStack> extractResultsFromRecipes(final ItemStack result, final Collection<Recipe> recipes)
    {
        final List<ItemStack> results = new ArrayList<>((recipes.size() << 3) + 1);
        results.add(result);
        results.addAll(recipes.stream().flatMap(r -> r.getResult().stream()).collect(Collectors.toList()));
        return results;
    }

    @Override
    public List<ItemStack> getResult()
    {
        return this.resultList;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("result", this.result).toString();
    }
}
