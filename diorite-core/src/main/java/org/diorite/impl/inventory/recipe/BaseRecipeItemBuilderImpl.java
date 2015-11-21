package org.diorite.impl.inventory.recipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.BiPredicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.RecipeBuilder;
import org.diorite.inventory.recipe.RecipeItem;
import org.diorite.inventory.recipe.RecipeItemBuilder;
import org.diorite.inventory.recipe.ReplacedRecipeItem;
import org.diorite.inventory.recipe.SimpleRecipeItem;

@SuppressWarnings("unchecked")
public abstract class BaseRecipeItemBuilderImpl<T extends RecipeBuilder, B extends RecipeItemBuilder<T, B>> implements RecipeItemBuilder<T, B>
{
    protected final T         builder;
    protected       ItemStack item;
    protected       boolean   ignoreType;
    protected       ItemStack replacement;
    protected Collection<BiPredicate<Player, ItemStack>> validators = new ArrayList<>(4);

    protected BaseRecipeItemBuilderImpl(final T builder)
    {
        this.builder = builder;
    }

    @Override
    public B replacement(final ItemStack replacement)
    {
        this.replacement = replacement;
        return (B) this;
    }

    @Override
    public B item(final ItemStack pattern, final boolean ignoreType)
    {
        this.item = pattern;
        this.ignoreType = ignoreType;
        return (B) this;
    }

    @Override
    public B validator(final BiPredicate<Player, ItemStack> validator)
    {
        this.validators.add(validator);
        return (B) this;
    }

    protected RecipeItem createItem()
    {
        if (this.replacement != null)
        {
            return new ReplacedRecipeItem(this.item, this.ignoreType, this.replacement, this.validators);
        }
        return new SimpleRecipeItem(this.item, this.ignoreType, this.validators);
    }

    protected abstract void addItem(RecipeItem item);

    @Override
    public T build()
    {
        if ((this.item == null) && this.validators.isEmpty())
        {
            throw new RuntimeException("Can't create recipe item without any pattern data.");
        }
        this.addItem(this.createItem());
        return this.builder;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("builder", this.builder).toString();
    }
}
