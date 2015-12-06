package org.diorite.inventory.recipe;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.diorite.entity.Player;
import org.diorite.inventory.item.BaseItemStack;
import org.diorite.inventory.item.ItemStack;
import org.diorite.material.Material;

/**
 * Represent basic recipe item builder.
 *
 * @param <T> type of recipe builder.
 * @param <B> type of item builder.
 */
public interface RecipeItemBuilder<T extends RecipeBuilder, B extends RecipeItemBuilder<T, B>>
{
    /**
     * Build and add this recipe item to recipe builder, and returns recipe builder. <br>
     * Builder must contans any item or at least one validator.
     *
     * @return recipe builder for method chains.
     */
    T build();

    /**
     * Sets replacement item of this recipe item.
     *
     * @param replacement replacement item to be used.
     *
     * @return this same builder for method chains.
     */
    B replacement(ItemStack replacement);

    /**
     * Sets replacement item of this recipe item.
     *
     * @param material replacement item to be used.
     *
     * @return this same builder for method chains.
     */
    default B replacement(final Material material)
    {
        return this.replacement(new BaseItemStack(material));
    }

    /**
     * Sets replacement item of this recipe item.
     *
     * @param material replacement item to be used.
     * @param amount   amount of this item.
     *
     * @return this same builder for method chains.
     */
    default B replacement(final Material material, final int amount)
    {
        return this.replacement(new BaseItemStack(material, amount));
    }

    /**
     * Sets pattern item of this recipe item.
     *
     * @param pattern pattern item.
     *
     * @return this same builder for method chains.
     */
    default B item(final Material pattern)
    {
        return this.item(new BaseItemStack(pattern, 1), false);
    }

    /**
     * Sets pattern item of this recipe item. <br>
     * If you use this multiple times, multiple possible items will be added, but they will share all other settings.
     *
     * @param pattern    pattern item.
     * @param ignoreType if subtype of material should be ignored.
     *
     * @return this same builder for method chains.
     */
    default B item(final Material pattern, final boolean ignoreType)
    {
        return this.item(new BaseItemStack(pattern, 1), ignoreType);
    }

    /**
     * Sets pattern item of this recipe item. <br>
     * If you use this multiple times, multiple possible items will be added, but they will share all other settings.
     *
     * @param pattern pattern item.
     * @param amount  amount of this item.
     *
     * @return this same builder for method chains.
     */
    default B item(final Material pattern, final int amount)
    {
        return this.item(new BaseItemStack(pattern, amount), false);
    }

    /**
     * Sets pattern item of this recipe item. <br>
     * If you use this multiple times, multiple possible items will be added, but they will share all other settings.
     *
     * @param pattern    pattern item.
     * @param amount     amount of this item.
     * @param ignoreType if subtype of material should be ignored.
     *
     * @return this same builder for method chains.
     */
    default B item(final Material pattern, final int amount, final boolean ignoreType)
    {
        return this.item(new BaseItemStack(pattern, amount), ignoreType);
    }

    /**
     * Sets pattern item of this recipe item. <br>
     * If you use this multiple times, multiple possible items will be added, but they will share all other settings.
     *
     * @param pattern pattern item.
     *
     * @return this same builder for method chains.
     */
    default B item(final ItemStack pattern)
    {
        return this.item(pattern, false);
    }

    /**
     * Sets pattern item of this recipe item. <br>
     * If you use this multiple times, multiple possible items will be added, but they will share all other settings.
     *
     * @param pattern    pattern item.
     * @param ignoreType if subtype of material should be ignored.
     *
     * @return this same builder for method chains.
     */
    B item(ItemStack pattern, boolean ignoreType);

    /**
     * Sets pattern item validator, may be used with {@link #item(ItemStack, boolean)}
     *
     * @param validator validator of item stack.
     *
     * @return this same builder for method chains.
     */
    default B simpleValidator(final Predicate<ItemStack> validator)
    {
        return this.validator((player, itemStack) -> validator.test(itemStack));
    }

    /**
     * Sets pattern item validator, may be used with {@link #item(ItemStack, boolean)}
     *
     * @param validator validator of item stack. Must handle null player values.
     *
     * @return this same builder for method chains.
     */
    B validator(final BiPredicate<Player, ItemStack> validator);

    /**
     * Represent recipe item builder that can create {@link RepeatableRecipeItem} too.
     *
     * @param <T> type of recipe builder.
     * @param <B> type of item builder.
     */
    interface RepeatableRecipeItemBuilder<T extends RecipeBuilder, B extends RecipeItemBuilder<T, B>> extends RecipeItemBuilder<T, B>
    {
        /**
         * Make this recipe item repeatable, so it can be used 1 or more time in crafting grid.
         *
         * @return this same builder for method chains.
         */
        B repeatable();

        /**
         * Make this recipe item repeatable, so it can be used 1 or more time in crafting grid.
         *
         * @param transformFunc Transform function that transform result item using list of repeated ingredients.
         *
         * @return this same builder for method chains.
         */
        B repeatable(BiFunction<ItemStack, List<ItemStack>, ItemStack> transformFunc);
    }
}
