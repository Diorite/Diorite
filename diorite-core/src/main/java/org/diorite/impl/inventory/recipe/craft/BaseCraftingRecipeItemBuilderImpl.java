package org.diorite.impl.inventory.recipe.craft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.craft.CraftingRecipeBuilder;
import org.diorite.inventory.recipe.craft.CraftingRecipeItem;
import org.diorite.inventory.recipe.craft.CraftingRecipeItemBuilder;
import org.diorite.inventory.recipe.craft.ReplacedCraftingRecipeItem;
import org.diorite.inventory.recipe.craft.BasicCraftingRecipeItem;
import org.diorite.inventory.recipe.craft.CraftingGrid;

@SuppressWarnings("unchecked")
public abstract class BaseCraftingRecipeItemBuilderImpl<T extends CraftingRecipeBuilder, B extends CraftingRecipeItemBuilder<T, B>> implements CraftingRecipeItemBuilder<T, B>
{
    protected final T                                           builder;
    protected       ItemStack                                   item;
    protected       boolean                                     ignoreType;
    protected       ItemStack                                   replacement;
    protected       BiFunction<Player, CraftingGrid, ItemStack> replacmentFunc;
    protected final Collection<BiPredicate<Player, ItemStack>> validators = new ArrayList<>(4);

    protected BaseCraftingRecipeItemBuilderImpl(final T builder)
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
    public B replacement(final ItemStack basicReplacement, final BiFunction<Player, CraftingGrid, ItemStack> replacement)
    {
        this.replacement = basicReplacement;
        this.replacmentFunc = replacement;
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

    protected CraftingRecipeItem createItem()
    {
        if (this.replacement != null)
        {
            return new ReplacedCraftingRecipeItem(this.item, this.ignoreType, this.replacmentFunc, this.replacement, this.validators);
        }
        return new BasicCraftingRecipeItem(this.item, this.ignoreType, this.validators);
    }

    protected abstract void addItem(CraftingRecipeItem item);

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
