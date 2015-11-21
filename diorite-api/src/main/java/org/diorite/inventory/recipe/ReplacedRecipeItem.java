package org.diorite.inventory.recipe;

import java.util.Collection;
import java.util.function.BiPredicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.inventory.item.ItemStack;

/**
 * Represent simple recipe item, that check name/lore/etc only if recipe item have custom one,
 * and replace to other item after crafting.
 */
public class ReplacedRecipeItem extends SimpleRecipeItem
{
    /**
     * Replacment item.
     *
     * @see #getReplacement()
     */
    protected final ItemStack replacement;

    /**
     * Construct new recipe item with given item as pattern.
     *
     * @param item        pattern item.
     * @param ignoreData  if pattern item should ignore subtype of material
     * @param replacement replacement item, see {@link #getReplacement()}
     * @param validators  validators of thic recipe item, allowing to check additional data of item. Player may be null.
     */
    public ReplacedRecipeItem(final ItemStack item, final boolean ignoreData, final ItemStack replacement, final Collection<BiPredicate<Player, ItemStack>> validators)
    {
        super(item, ignoreData, validators);
        this.replacement = replacement;
    }

    @Override
    public ItemStack getReplacement()
    {
        return this.replacement.clone();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("replacement", this.replacement).toString();
    }
}
