package org.diorite.inventory.recipe;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.diorite.entity.Player;
import org.diorite.inventory.item.BaseItemStack;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.RecipeBuilder.ShapedRecipeBuilder;
import org.diorite.inventory.recipe.RecipeBuilder.ShapelessRecipeBuilder;
import org.diorite.material.Material;

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
     * Sets pattern item of this recipe item.
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
     * Sets pattern item of this recipe item.
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
     * Sets pattern item of this recipe item.
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
     * Sets pattern item of this recipe item.
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
     * Sets pattern item of this recipe item.
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

    interface ShapelessRecipeItemBuilder extends RecipeItemBuilder<ShapelessRecipeBuilder, ShapelessRecipeItemBuilder>
    {
        /**
         * Build and add this recipe item to recipe builder, and returns new recipe item builder. <br>
         * Builder must contans any item or at least one validator.
         *
         * @return new recipe item builder.
         */
        ShapelessRecipeItemBuilder addIngredient();
    }

    interface ShapedRecipeItemBuilder extends RecipeItemBuilder<ShapedRecipeBuilder, ShapedRecipeItemBuilder>
    {
        /**
         * Build and add this recipe item to recipe builder, and returns new recipe item builder. <br>
         * Builder must contans any item or at least one validator.
         *
         * @param c pattern key of this recipe item.
         *
         * @return new recipe item builder.
         */
        ShapedRecipeItemBuilder addIngredient(char c);
    }
}
