package org.diorite.inventory.recipe.craft;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.diorite.Diorite;
import org.diorite.entity.Player;
import org.diorite.inventory.item.BaseItemStack;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.RecipeBuilder;
import org.diorite.inventory.recipe.craft.CraftingRecipeItemBuilder.ShapedCraftingRecipeItemBuilder;
import org.diorite.inventory.recipe.craft.CraftingRecipeItemBuilder.ShapelessCraftingRecipeItemBuilder;
import org.diorite.material.Material;

/**
 * Advanced class for creating crafting recipes
 */
public interface CraftingRecipeBuilder extends RecipeBuilder
{
    /**
     * Set result itemstack for this recipe.
     *
     * @param itemStack result itemstack.
     *
     * @return this same builder for method chains.
     */
    CraftingRecipeBuilder result(ItemStack itemStack);

    /**
     * Set result itemstack for this recipe.
     *
     * @param material result itemstack material.
     *
     * @return this same builder for method chains.
     */
    default CraftingRecipeBuilder result(final Material material)
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
    default CraftingRecipeBuilder result(final Material material, final int amount)
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
    CraftingRecipeBuilder result(BiFunction<Player, CraftingGrid, ItemStack> itemStack);

    /**
     * Set dynamic result function for this recipe. Like coloring armor based on armor and dyes consumed when crafting.<br>
     * By using this method you still need set result using {@link #result(ItemStack)} to basic item stack,
     * like normal leather armor.
     *
     * @param itemStack function that create result item stack based on items that were consumed by recipe.
     *
     * @return this same builder for method chains.
     */
    default CraftingRecipeBuilder result(final Function<CraftingGrid, ItemStack> itemStack)
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
    @Override
    CraftingRecipeBuilder vanilla(boolean vanilla);

    /**
     * Sets priority of this recipe, see {@link CraftingRecipe#getPriority()}
     *
     * @param priority priority of recipe.
     *
     * @return this same builder for method chains.
     *
     * @see CraftingRecipe#getPriority()
     */
    @Override
    CraftingRecipeBuilder priority(long priority);

    /**
     * Returns shapless recipe builder.
     *
     * @return shapless recipe builder.
     */
    ShapelessCraftingRecipeBuilder shapeless();

    /**
     * Returns shaped recipe builder.
     *
     * @return shaped recipe builder.
     */
    ShapedCraftingRecipeBuilder shaped();

    /**
     * Builds and create recipe using all given data.
     *
     * @return created recipe.
     *
     * @throws RuntimeException if creating recipe fail.
     */
    @Override
    CraftingRecipe build() throws RuntimeException;

    /**
     * Builds, create recipe using all given data and adds it to diorite {@link CraftingRecipeManager}.
     *
     * @return created recipe.
     *
     * @throws RuntimeException if creating recipe fail.
     */
    @Override
    CraftingRecipe buildAndAdd() throws RuntimeException;

    /**
     * Advanced class for creating shapeless crafting recipes
     */
    interface ShapelessCraftingRecipeBuilder extends CraftingRecipeBuilder
    {
        @Override
        ShapelessCraftingRecipeBuilder result(ItemStack itemStack);

        @Override
        default ShapelessCraftingRecipeBuilder result(final Material material)
        {
            return this.result(new BaseItemStack(material));
        }

        @Override
        default ShapelessCraftingRecipeBuilder result(final Material material, final int amount)
        {
            return this.result(new BaseItemStack(material, amount));
        }

        @Override
        default ShapelessCraftingRecipeBuilder result(final Function<CraftingGrid, ItemStack> itemStack)
        {
            return this.result((player, itemStacks) -> itemStack.apply(itemStacks));
        }

        @Override
        ShapelessCraftingRecipeBuilder result(BiFunction<Player, CraftingGrid, ItemStack> itemStack);

        @Override
        ShapelessCraftingRecipeBuilder vanilla(boolean vanilla);

        @Override
        ShapelessCraftingRecipeBuilder priority(long priority);

        @Override
        default ShapelessCraftingRecipeBuilder shapeless()
        {
            throw new IllegalStateException("You can't change type of builder!");
        }

        @Override
        default ShapedCraftingRecipeBuilder shaped()
        {
            throw new IllegalStateException("You can't change type of builder!");
        }

        @Override
        ShapelessCraftingRecipe build() throws RuntimeException;

        @Override
        default ShapelessCraftingRecipe buildAndAdd() throws RuntimeException
        {
            final ShapelessCraftingRecipe recipe = this.build();
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
        default ShapelessCraftingRecipeBuilder addIngredients(final CraftingRecipeItem... items)
        {
            for (final CraftingRecipeItem item : items)
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
        ShapelessCraftingRecipeItemBuilder addIngredient();

        /**
         * Add more ingredients to this recipe.
         *
         * @param itemStack  ingredient to be added.
         * @param ignoreType if subtype of ingredient material should be ignored.
         *
         * @return this same builder for method chains.
         */
        default ShapelessCraftingRecipeBuilder addIngredient(final ItemStack itemStack, final boolean ignoreType)
        {
            return this.addIngredient(new BasicCraftingRecipeItem(itemStack, ignoreType, null));
        }

        /**
         * Add more ingredients to this recipe.
         *
         * @param itemStack   ingredient to be added.
         * @param replacement replacement item for this ingredient.
         *
         * @return this same builder for method chains.
         */
        default ShapelessCraftingRecipeBuilder addIngredient(final ItemStack itemStack, final ItemStack replacement)
        {
            return this.addIngredient(new ReplacedCraftingRecipeItem(itemStack, false, null, replacement, null));
        }

        /**
         * Add more ingredients to this recipe.
         *
         * @param item ingredient to be added.
         *
         * @return this same builder for method chains.
         */
        ShapelessCraftingRecipeBuilder addIngredient(CraftingRecipeItem item);
    }

    /**
     * Advanced class for creating shaped crafting recipes
     */
    interface ShapedCraftingRecipeBuilder extends CraftingRecipeBuilder
    {
        @Override
        ShapedCraftingRecipeBuilder result(ItemStack itemStack);

        @Override
        default ShapedCraftingRecipeBuilder result(final Material material)
        {
            return this.result(new BaseItemStack(material));
        }

        @Override
        default ShapedCraftingRecipeBuilder result(final Material material, final int amount)
        {
            return this.result(new BaseItemStack(material, amount));
        }

        @Override
        default ShapedCraftingRecipeBuilder result(final Function<CraftingGrid, ItemStack> itemStack)
        {
            return this.result((player, itemStacks) -> itemStack.apply(itemStacks));
        }

        @Override
        ShapedCraftingRecipeBuilder result(BiFunction<Player, CraftingGrid, ItemStack> itemStack);

        @Override
        ShapedCraftingRecipeBuilder vanilla(boolean vanilla);

        @Override
        ShapedCraftingRecipeBuilder priority(long priority);

        @Override
        default ShapelessCraftingRecipeBuilder shapeless()
        {
            throw new IllegalStateException("You can't change type of builder!");
        }

        @Override
        default ShapedCraftingRecipeBuilder shaped()
        {
            throw new IllegalStateException("You can't change type of builder!");
        }

        @Override
        ShapedCraftingRecipe build();

        @Override
        default ShapedCraftingRecipe buildAndAdd() throws RuntimeException
        {
            final ShapedCraftingRecipe recipe = this.build();
            Diorite.getServerManager().getRecipeManager().add(recipe);
            return recipe;
        }

        /**
         * Set pattern of this recipe, recipe can't use char and {@link CraftingRecipePattern} at once. <br>
         * Each element in string array is one row in inventory, each char in this row is one recipe item.
         *
         * @param pattern pattern to be used.
         *
         * @return this same builder for method chains.
         */
        ShapedCraftingRecipeBuilder pattern(String... pattern);

        /**
         * Set pattern of this recipe, recipe can't use char and {@link CraftingRecipePattern} at once.
         *
         * @param pattern pattern to be used.
         *
         * @return this same builder for method chains.
         */
        ShapedCraftingRecipeBuilder pattern(CraftingRecipePattern pattern);

        /**
         * Set pattern of this recipe, recipe can't use char and {@link CraftingRecipePattern} at once.
         * Each element in 2D char array is one row in inventory, each char in this row is one recipe item.
         *
         * @param pattern pattern to be used.
         *
         * @return this same builder for method chains.
         */
        default ShapedCraftingRecipeBuilder pattern(final char[]... pattern)
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
        ShapedCraftingRecipeItemBuilder addIngredient(char c);

        /**
         * @param c          pattern key of this recipe item.
         * @param itemStack  ingredient to be added.
         * @param ignoreType if subtype of ingredient material should be ignored.
         *
         * @return this same builder for method chains.
         */
        default ShapedCraftingRecipeBuilder addIngredient(final char c, final ItemStack itemStack, final boolean ignoreType)
        {
            return this.addIngredient(c, new BasicCraftingRecipeItem(itemStack, ignoreType, null));
        }

        /**
         * @param c           pattern key of this recipe item.
         * @param itemStack   ingredient to be added.
         * @param replacement replacement item for this ingredient.
         *
         * @return this same builder for method chains.
         */
        default ShapedCraftingRecipeBuilder addIngredient(final char c, final ItemStack itemStack, final ItemStack replacement)
        {
            return this.addIngredient(c, new ReplacedCraftingRecipeItem(itemStack, false, null, replacement, null));
        }

        /**
         * @param c    pattern key of this recipe item.
         * @param item ingredient to be added.
         *
         * @return this same builder for method chains.
         */
        ShapedCraftingRecipeBuilder addIngredient(char c, CraftingRecipeItem item);
    }
}
