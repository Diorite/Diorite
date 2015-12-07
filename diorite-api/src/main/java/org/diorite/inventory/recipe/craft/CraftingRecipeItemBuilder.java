package org.diorite.inventory.recipe.craft;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.diorite.entity.Player;
import org.diorite.inventory.item.BaseItemStack;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.RecipeItemBuilder;
import org.diorite.inventory.recipe.craft.CraftingRecipeBuilder.ShapedCraftingRecipeBuilder;
import org.diorite.inventory.recipe.craft.CraftingRecipeBuilder.ShapelessCraftingRecipeBuilder;
import org.diorite.material.Material;

public interface CraftingRecipeItemBuilder<T extends CraftingRecipeBuilder, B extends CraftingRecipeItemBuilder<T, B>> extends RecipeItemBuilder<T, B>
{
    /**
     * Sets replacement item of this recipe item.
     *
     * @param basicReplacement basic replacement item, used by {@link CraftingRecipeItem#getReplacement()}
     * @param replacement      function that create replacement item stack based on items that were consumed by recipe.
     *
     * @return this same builder for method chains.
     */
    B replacement(ItemStack basicReplacement, BiFunction<Player, CraftingGrid, ItemStack> replacement);

    /**
     * Sets replacement item of this recipe item.
     *
     * @param basicReplacement basic replacement item material, used by {@link CraftingRecipeItem#getReplacement()}
     * @param replacement      function that create replacement item stack based on items that were consumed by recipe.
     *
     * @return this same builder for method chains.
     */
    default B replacement(final Material basicReplacement, final BiFunction<Player, CraftingGrid, ItemStack> replacement)
    {
        return this.replacement(new BaseItemStack(basicReplacement), replacement);
    }

    /**
     * Sets replacement item of this recipe item.
     *
     * @param basicReplacement basic replacement item material, used by {@link CraftingRecipeItem#getReplacement()}
     * @param amount           amount of that material.
     * @param replacement      function that create replacement item stack based on items that were consumed by recipe.
     *
     * @return this same builder for method chains.
     */
    default B replacement(final Material basicReplacement, final int amount, final BiFunction<Player, CraftingGrid, ItemStack> replacement)
    {
        return this.replacement(new BaseItemStack(basicReplacement, amount), replacement);
    }


    /**
     * Sets replacement item of this recipe item.
     *
     * @param basicReplacement basic replacement item, used by {@link CraftingRecipeItem#getReplacement()}
     * @param replacement      function that create replacement item stack based on items that were consumed by recipe.
     *
     * @return this same builder for method chains.
     */
    default B replacement(final ItemStack basicReplacement, final Function<CraftingGrid, ItemStack> replacement)
    {
        return this.replacement(basicReplacement, (p, c) -> replacement.apply(c));
    }

    /**
     * Sets replacement item of this recipe item.
     *
     * @param basicReplacement basic replacement item material, used by {@link CraftingRecipeItem#getReplacement()}
     * @param replacement      function that create replacement item stack based on items that were consumed by recipe.
     *
     * @return this same builder for method chains.
     */
    default B replacement(final Material basicReplacement, final Function<CraftingGrid, ItemStack> replacement)
    {
        return this.replacement(new BaseItemStack(basicReplacement), (p, c) -> replacement.apply(c));
    }

    /**
     * Sets replacement item of this recipe item.
     *
     * @param basicReplacement basic replacement item material, used by {@link CraftingRecipeItem#getReplacement()}
     * @param amount           amount of that material.
     * @param replacement      function that create replacement item stack based on items that were consumed by recipe.
     *
     * @return this same builder for method chains.
     */
    default B replacement(final Material basicReplacement, final int amount, final Function<CraftingGrid, ItemStack> replacement)
    {
        return this.replacement(new BaseItemStack(basicReplacement, amount), (p, c) -> replacement.apply(c));
    }

    interface ShapelessCraftingRecipeItemBuilder extends CraftingRecipeItemBuilder<ShapelessCraftingRecipeBuilder, ShapelessCraftingRecipeItemBuilder>, RepeatableRecipeItemBuilder<ShapelessCraftingRecipeBuilder, ShapelessCraftingRecipeItemBuilder>
    {
        /**
         * Build and add this recipe item to recipe builder, and returns new recipe item builder. <br>
         * Builder must contans any item or at least one validator.
         *
         * @return new recipe item builder.
         */
        ShapelessCraftingRecipeItemBuilder addIngredient();

        /**
         * Build and add this recipe item to recipe builder, and returns this same item builder. <br>
         * Builder must contans any item or at least one validator.
         *
         * @return this same recipe item builder.
         */
        default ShapelessCraftingRecipeItemBuilder addClone()
        {
            this.addIngredient();
            return this;
        }
    }

    interface ShapedCraftingRecipeItemBuilder extends CraftingRecipeItemBuilder<ShapedCraftingRecipeBuilder, ShapedCraftingRecipeItemBuilder>
    {
        /**
         * Build and add this recipe item to recipe builder, and returns new recipe item builder. <br>
         * Builder must contans any item or at least one validator.
         *
         * @param c pattern key of this recipe item.
         *
         * @return new recipe item builder.
         */
        ShapedCraftingRecipeItemBuilder addIngredient(char c);
    }
}
