package org.diorite.impl.inventory.recipe.craft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.RecipeItem;

abstract class SimpleRecipeImpl extends PriorityRecipeImpl
{
    protected final ItemStack       result;
    protected final List<ItemStack> resultList;

    protected SimpleRecipeImpl(final ItemStack result, final long priority, final boolean vanilla)
    {
        super(priority, vanilla);
        this.result = result;
        this.resultList = Collections.singletonList(result.clone());
    }

    protected SimpleRecipeImpl(final List<ItemStack> result, final long priority, final boolean vanilla)
    {
        super(priority, vanilla);
        Validate.notEmpty(result, "Result list can't be empty.");
        this.result = result.get(0);
        this.resultList = (result.size() == 1) ? Collections.singletonList(this.result) : Collections.unmodifiableList(new ArrayList<>(result));
    }

    protected static List<ItemStack> extractResults(final ItemStack result, final Collection<RecipeItem> ingredients)
    {
        final List<ItemStack> results = new ArrayList<>(ingredients.size() + 1);
        results.add(result);
        for (final RecipeItem ingredient : ingredients)
        {
            if (ingredient.getReplacement() == null)
            {
                continue;
            }
            results.add(ingredient.getReplacement());
        }
        return results;
    }

    protected static List<ItemStack> extractResults(final ItemStack result, final RecipeItem... ingredients)
    {
        final List<ItemStack> results = new ArrayList<>(ingredients.length + 1);
        results.add(result);
        for (final RecipeItem ingredient : ingredients)
        {
            if (ingredient.getReplacement() == null)
            {
                continue;
            }
            results.add(ingredient.getReplacement());
        }
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
