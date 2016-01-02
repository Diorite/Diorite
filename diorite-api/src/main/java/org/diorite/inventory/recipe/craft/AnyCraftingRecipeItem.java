package org.diorite.inventory.recipe.craft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.inventory.item.BaseItemStack;
import org.diorite.inventory.item.ItemStack;
import org.diorite.material.Material;

/**
 * Represent recipe item that can be any item in grid, only validators must match for them.
 */
public class AnyCraftingRecipeItem implements CraftingRecipeItem
{
    /**
     * Pattern item of this recipe used by {@link #getItem()}
     */
    protected final ItemStack                                   item;
    /**
     * Validators if this recipe item, allowing to check more item data. <br>
     * Player may be null when using.
     */
    protected final Collection<BiPredicate<Player, ItemStack>>  validators;
    /**
     * Basic Replacment item.
     *
     * @see #getReplacement()
     */
    protected final ItemStack                                   replacement;
    /**
     * Replacment function.
     *
     * @see #getReplacement(Player, CraftingGrid)
     */
    protected final BiFunction<Player, CraftingGrid, ItemStack> replacementFunc;

    /**
     * Construct new recipe item with given item as pattern.
     *
     * @param item            pattern item.
     * @param replacementFunc replacement item function, see {@link #getReplacement(Player, CraftingGrid)}, may by null.
     * @param replacement     replacement item, see {@link #getReplacement()}
     * @param validators      validators of thic recipe item, allowing to check additional data of item. Player may be null.
     */
    public AnyCraftingRecipeItem(final ItemStack item, final BiFunction<Player, CraftingGrid, ItemStack> replacementFunc, final ItemStack replacement, final Collection<BiPredicate<Player, ItemStack>> validators)
    {
        this.item = (item == null) ? new BaseItemStack(Material.BEDROCK) : item.clone();
        this.validators = (validators == null) ? null : new ArrayList<>(validators);
        this.replacement = replacement;
        this.replacementFunc = replacementFunc;
    }

    /**
     * Construct new recipe item with given item as pattern.
     *
     * @param item       pattern item.
     * @param validators validators of thic recipe item, allowing to check additional data of item. Player may be null.
     */
    public AnyCraftingRecipeItem(final ItemStack item, final Collection<BiPredicate<Player, ItemStack>> validators)
    {
        this.item = (item == null) ? new BaseItemStack(Material.BEDROCK) : item.clone();
        this.validators = (validators == null) ? null : new ArrayList<>(validators);
        this.replacement = null;
        this.replacementFunc = null;
    }

    @Override
    public ItemStack getReplacement(final Player player, final CraftingGrid grid)
    {
        return (this.replacementFunc == null) ? this.replacement : this.replacementFunc.apply(player, grid).clone();
    }

    @Override
    public ItemStack isValid(final Player player, final ItemStack item)
    {
        if (item == null)
        {
            return null;
        }
        if ((this.validators != null) && ! this.validators.stream().allMatch(f -> f.test(player, item)))
        {
            return null;
        }
        final ItemStack is = item.clone();
        is.setAmount(this.item.getAmount());
        return is;
    }

    @Override
    public ItemStack getItem()
    {
        return this.item.clone();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("item", this.item).append("validators", this.validators).append("replacement", this.replacement).append("replacementFunc", this.replacementFunc).toString();
    }
}
