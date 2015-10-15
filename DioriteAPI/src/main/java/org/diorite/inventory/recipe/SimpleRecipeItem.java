package org.diorite.inventory.recipe;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.inventory.item.ItemStack;

/**
 * Represent simple recipe item, that check name/lore/etc only if recipe item have custom one.
 */
public class SimpleRecipeItem implements RecipeItem
{
    /**
     * Pattern item of this recipe.
     */
    protected final ItemStack item;

    /**
     * Construct new recipe item with given item as pattern.
     *
     * @param item pattern item.
     */
    public SimpleRecipeItem(final ItemStack item)
    {
        this.item = item.clone();
    }

    @Override
    public boolean isValid(final ItemStack item)
    {
        // TODO check other data
        return this.item.getMaterial().equals(item.getMaterial()) && (this.item.getAmount() <= item.getAmount());
    }

    @Override
    public ItemStack getItem()
    {
        return this.item.clone();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("item", this.item).toString();
    }
}
