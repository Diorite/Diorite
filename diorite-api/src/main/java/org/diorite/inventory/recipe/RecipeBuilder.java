package org.diorite.inventory.recipe;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.diorite.Diorite;
import org.diorite.entity.Player;
import org.diorite.inventory.item.BaseItemStack;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.RecipeItemBuilder.ShapedRecipeItemBuilder;
import org.diorite.inventory.recipe.RecipeItemBuilder.ShapelessRecipeItemBuilder;
import org.diorite.inventory.recipe.craft.Recipe;
import org.diorite.inventory.recipe.craft.RecipePattern;
import org.diorite.inventory.recipe.craft.ShapedRecipe;
import org.diorite.inventory.recipe.craft.ShapelessRecipe;
import org.diorite.material.Material;

/**
 * Advanced class for creating crafting recipes
 */
public interface RecipeBuilder
{
    /**
     * Set result itemstack for this recipe.
     *
     * @param itemStack result itemstack.
     *
     * @return this same builder for method chains.
     */
    RecipeBuilder result(ItemStack itemStack);

    /**
     * Set result itemstack for this recipe.
     *
     * @param material result itemstack material.
     *
     * @return this same builder for method chains.
     */
    default RecipeBuilder result(final Material material)
    {
        return this.result(new BaseItemStack(material));
    }

    /**
     * Set result itemstack for this recipe.
     *
     * @param material result itemstack material.
     * @param amount   amount of material.
     *
     * @return this same builder for method chains.
     */
    default RecipeBuilder result(final Material material, final int amount)
    {
        return this.result(new BaseItemStack(material, amount));
    }

    /**
     * Set dynamic result function for this recipe. Like coloring armor based on armor and dyes consumed when crafting.<br>
     * By using this method you still need set result using {@link #result(ItemStack)} to basic item stack,
     * like normal leather armor. <br>
     * Function must handle null player values.
     *
     * @param itemStack function that create result item stack based on items that were consumed by recipe.
     *
     * @return this same builder for method chains.
     */
    RecipeBuilder result(BiFunction<Player, ItemStack[], ItemStack> itemStack);

    /**
     * Set dynamic result function for this recipe. Like coloring armor based on armor and dyes consumed when crafting.<br>
     * By using this method you still need set result using {@link #result(ItemStack)} to basic item stack,
     * like normal leather armor.
     *
     * @param itemStack function that create result item stack based on items that were consumed by recipe.
     *
     * @return this same builder for method chains.
     */
    default RecipeBuilder result(final Function<ItemStack[], ItemStack> itemStack)
    {
        return this.result((player, itemStacks) -> itemStack.apply(itemStacks));
    }

    /**
     * Sets if this recipe is vanilla one, false by default. <br>
     * Non vanilla recipes often create ghost items in inventory, if recipe isn't vanilla, diorite will fix that bugs.
     *
     * @param vanilla if recipe should be vanilla.
     *
     * @return this same builder for method chains.
     */
    RecipeBuilder vanilla(boolean vanilla);

    /**
     * Sets priority of this recipe, see {@link Recipe#getPriority()}
     *
     * @param priority priority of recipe.
     *
     * @return this same builder for method chains.
     *
     * @see Recipe#getPriority()
     */
    RecipeBuilder priority(long priority);

    /**
     * Returns shapless recipe builder.
     *
     * @return shapless recipe builder.
     */
    ShapelessRecipeBuilder shapeless();

    /**
     * Returns shaped recipe builder.
     *
     * @return shaped recipe builder.
     */
    ShapedRecipeBuilder shaped();

    /**
     * Builds and create recipe using all given data.
     *
     * @return created recipe.
     *
     * @throws RuntimeException if creating recipe fail.
     */
    Recipe build() throws RuntimeException;

    /**
     * Builds, create recipe using all given data and adds it to diorite {@link RecipeManager}.
     *
     * @return created recipe.
     *
     * @throws RuntimeException if creating recipe fail.
     */
    Recipe buildAndAdd() throws RuntimeException;

    /**
     * Advanced class for creating shapeless crafting recipes
     */
    interface ShapelessRecipeBuilder extends RecipeBuilder
    {
        @Override
        ShapelessRecipeBuilder result(ItemStack itemStack);

        @Override
        default ShapelessRecipeBuilder result(final Material material)
        {
            return this.result(new BaseItemStack(material));
        }

        @Override
        default ShapelessRecipeBuilder result(final Material material, final int amount)
        {
            return this.result(new BaseItemStack(material, amount));
        }

        @Override
        default ShapelessRecipeBuilder result(final Function<ItemStack[], ItemStack> itemStack)
        {
            return this.result((player, itemStacks) -> itemStack.apply(itemStacks));
        }

        @Override
        ShapelessRecipeBuilder result(BiFunction<Player, ItemStack[], ItemStack> itemStack);

        @Override
        ShapelessRecipeBuilder vanilla(boolean vanilla);

        @Override
        ShapelessRecipeBuilder priority(long priority);

        @Override
        default ShapelessRecipeBuilder shapeless()
        {
            throw new IllegalStateException("You can't change type of builder!");
        }

        @Override
        default ShapedRecipeBuilder shaped()
        {
            throw new IllegalStateException("You can't change type of builder!");
        }

        @Override
        ShapelessRecipe build() throws RuntimeException;

        @Override
        default ShapelessRecipe buildAndAdd() throws RuntimeException
        {
            final ShapelessRecipe recipe = this.build();
            Diorite.getServerManager().getRecipeManager().add(recipe);
            return recipe;
        }

        /**
         * Add more ingredients to this recipe.
         *
         * @param items ingredients to be added.
         *
         * @return this same builder for method chains.
         */
        default ShapelessRecipeBuilder addIngredients(final RecipeItem... items)
        {
            for (final RecipeItem item : items)
            {
                this.addIngredient(item);
            }
            return this;
        }

        /**
         * Returns builder of recipe item for this recipe builder.
         *
         * @return builder of recipe item for this recipe builder.
         */
        ShapelessRecipeItemBuilder addIngredient();

        /**
         * Add more ingredients to this recipe.
         *
         * @param itemStack  ingredient to be added.
         * @param ignoreType if subtype of ingredient material should be ignored.
         *
         * @return this same builder for method chains.
         */
        default ShapelessRecipeBuilder addIngredient(final ItemStack itemStack, final boolean ignoreType)
        {
            return this.addIngredient(new SimpleRecipeItem(itemStack, ignoreType, null));
        }

        /**
         * Add more ingredients to this recipe.
         *
         * @param itemStack   ingredient to be added.
         * @param replacement replacement item for this ingredient.
         *
         * @return this same builder for method chains.
         */
        default ShapelessRecipeBuilder addIngredient(final ItemStack itemStack, final ItemStack replacement)
        {
            return this.addIngredient(new ReplacedRecipeItem(itemStack, false, replacement, null));
        }

        /**
         * Add more ingredients to this recipe.
         *
         * @param item ingredient to be added.
         *
         * @return this same builder for method chains.
         */
        ShapelessRecipeBuilder addIngredient(RecipeItem item);
    }

    /**
     * Advanced class for creating shaped crafting recipes
     */
    interface ShapedRecipeBuilder extends RecipeBuilder
    {
        @Override
        ShapedRecipeBuilder result(ItemStack itemStack);

        @Override
        default ShapedRecipeBuilder result(final Material material)
        {
            return this.result(new BaseItemStack(material));
        }

        @Override
        default ShapedRecipeBuilder result(final Material material, final int amount)
        {
            return this.result(new BaseItemStack(material, amount));
        }

        @Override
        default ShapedRecipeBuilder result(final Function<ItemStack[], ItemStack> itemStack)
        {
            return this.result((player, itemStacks) -> itemStack.apply(itemStacks));
        }

        @Override
        ShapedRecipeBuilder result(BiFunction<Player, ItemStack[], ItemStack> itemStack);

        @Override
        ShapedRecipeBuilder vanilla(boolean vanilla);

        @Override
        ShapedRecipeBuilder priority(long priority);

        @Override
        default ShapelessRecipeBuilder shapeless()
        {
            throw new IllegalStateException("You can't change type of builder!");
        }

        @Override
        default ShapedRecipeBuilder shaped()
        {
            throw new IllegalStateException("You can't change type of builder!");
        }

        @Override
        ShapedRecipe build();

        @Override
        default ShapedRecipe buildAndAdd() throws RuntimeException
        {
            final ShapedRecipe recipe = this.build();
            Diorite.getServerManager().getRecipeManager().add(recipe);
            return recipe;
        }

        /**
         * Set pattern of this recipe, recipe can't use char and {@link RecipePattern} at once. <br>
         * Each element in string array is one row in inventory, each char in this row is one recipe item.
         *
         * @param pattern pattern to be used.
         *
         * @return this same builder for method chains.
         */
        ShapedRecipeBuilder pattern(String... pattern);

        /**
         * Set pattern of this recipe, recipe can't use char and {@link RecipePattern} at once.
         *
         * @param pattern pattern to be used.
         *
         * @return this same builder for method chains.
         */
        ShapedRecipeBuilder pattern(RecipePattern pattern);

        /**
         * Set pattern of this recipe, recipe can't use char and {@link RecipePattern} at once.
         * Each element in 2D char array is one row in inventory, each char in this row is one recipe item.
         *
         * @param pattern pattern to be used.
         *
         * @return this same builder for method chains.
         */
        default ShapedRecipeBuilder pattern(final char[]... pattern)
        {
            final String[] pat = new String[pattern.length];
            int i = 0;
            for (final char[] chars : pattern)
            {
                pat[i++] = new String(chars);
            }
            return this.pattern(pat);
        }

        /**
         * Returns builder of recipe item for this recipe builder.
         *
         * @param c pattern key of this recipe item.
         *
         * @return builder of recipe item for this recipe builder.
         */
        ShapedRecipeItemBuilder addIngredient(char c);

        /**
         * @param c          pattern key of this recipe item.
         * @param itemStack  ingredient to be added.
         * @param ignoreType if subtype of ingredient material should be ignored.
         *
         * @return this same builder for method chains.
         */
        default ShapedRecipeBuilder addIngredient(final char c, final ItemStack itemStack, final boolean ignoreType)
        {
            return this.addIngredient(c, new SimpleRecipeItem(itemStack, ignoreType, null));
        }

        /**
         * @param c           pattern key of this recipe item.
         * @param itemStack   ingredient to be added.
         * @param replacement replacement item for this ingredient.
         *
         * @return this same builder for method chains.
         */
        default ShapedRecipeBuilder addIngredient(final char c, final ItemStack itemStack, final ItemStack replacement)
        {
            return this.addIngredient(c, new ReplacedRecipeItem(itemStack, false, replacement, null));
        }

        /**
         * @param c    pattern key of this recipe item.
         * @param item ingredient to be added.
         *
         * @return this same builder for method chains.
         */
        ShapedRecipeBuilder addIngredient(char c, RecipeItem item);
    }
}
