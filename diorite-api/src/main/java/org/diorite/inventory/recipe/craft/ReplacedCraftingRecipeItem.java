package org.diorite.inventory.recipe.craft;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.inventory.item.ItemStack;

/**
 * Represent simple recipe item, that check name/lore/etc only if recipe item have custom one,
 * and replace to other item after crafting.
 */
public class ReplacedCraftingRecipeItem extends BasicCraftingRecipeItem
{
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
     * @param ignoreData      if pattern item should ignore subtype of material
     * @param replacement     replacement item, see {@link #getReplacement()}
     * @param replacementFunc replacement item function, see {@link #getReplacement(Player, CraftingGrid)}, may by null.
     * @param validators      validators of thic recipe item, allowing to check additional data of item. Player may be null.
     */
    public ReplacedCraftingRecipeItem(final ItemStack item, final boolean ignoreData, final BiFunction<Player, CraftingGrid, ItemStack> replacementFunc, final ItemStack replacement, final Collection<BiPredicate<Player, ItemStack>> validators)
    {
        super(item, ignoreData, validators);
        this.replacement = replacement;
        this.replacementFunc = (replacementFunc == null) ? ((p, c) -> this.replacement) : replacementFunc;
    }

    @Override
    public ItemStack getReplacement(final Player player, final CraftingGrid grid)
    {
        return this.replacementFunc.apply(player, grid).clone();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("replacement", this.replacement).toString();
    }
}
