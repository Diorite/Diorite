package org.diorite.inventory.recipe.craft;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import org.diorite.entity.Player;
import org.diorite.inventory.GridInventory;
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
     * Returns recipe group that created this builder. (master recipe manager by default)
     *
     * @return recipe group that created this builder.
     */
    CraftingRecipeGroup getParent();

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
     * Returns grouped recipe builder.
     *
     * @return grouped recipe builder.
     */
    GroupCraftingRecipeBuilder grouped();

    /**
     * Returns semi-shaped recipe builder. <br>
     * WARN: semi shaped recipes allows for creating ver advanced patterns but they are also much slower than other types.
     * Use with caution, and with valid priority.
     *
     * @return semi-shaped recipe builder.
     */
    SemiShapedCraftingRecipeBuilder semiShaped();

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
     * Builder interface for creating semi-shaped crafting recipes
     */
    interface SemiShapedCraftingRecipeBuilder extends ShapelessCraftingRecipeBuilder, ShapedCraftingRecipeBuilder
    {
        @Override
        SemiShapedCraftingRecipeBuilder result(ItemStack itemStack);

        @Override
        default SemiShapedCraftingRecipeBuilder result(final Material material)
        {
            return this.result(new BaseItemStack(material));
        }

        @Override
        default SemiShapedCraftingRecipeBuilder result(final Material material, final int amount)
        {
            return this.result(new BaseItemStack(material, amount));
        }

        @Override
        default SemiShapedCraftingRecipeBuilder result(final Function<CraftingGrid, ItemStack> itemStack)
        {
            return this.result((player, itemStacks) -> itemStack.apply(itemStacks));
        }

        @Override
        SemiShapedCraftingRecipeBuilder result(BiFunction<Player, CraftingGrid, ItemStack> itemStack);

        @Override
        SemiShapedCraftingRecipeBuilder vanilla(boolean vanilla);

        @Override
        SemiShapedCraftingRecipeBuilder priority(long priority);

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
        default GroupCraftingRecipeBuilder grouped()
        {
            throw new IllegalStateException("You can't change type of builder!");
        }

        @Override
        default SemiShapedCraftingRecipeBuilder semiShaped()
        {
            throw new IllegalStateException("You can't change type of builder!");
        }

        @Override
        SemiShapedCraftingRecipe build() throws RuntimeException;

        @Override
        default SemiShapedCraftingRecipe buildAndAdd() throws RuntimeException
        {
            final SemiShapedCraftingRecipe recipe = this.build();
            this.getParent().add(recipe);
            return recipe;
        }

        @Override
        default SemiShapedCraftingRecipeBuilder addIngredients(final CraftingRecipeItem... items)
        {
            for (final CraftingRecipeItem item : items)
            {
                this.addIngredient(item);
            }
            return this;
        }

        @Override
        ShapelessCraftingRecipeItemBuilder addIngredient();

        @Override
        default SemiShapedCraftingRecipeBuilder addIngredient(final ItemStack itemStack, final boolean ignoreType)
        {
            return this.addIngredient(new SimpleCraftingRecipeItem(itemStack, ignoreType, null));
        }

        @Override
        default SemiShapedCraftingRecipeBuilder addIngredient(final ItemStack itemStack, final ItemStack replacement)
        {
            return this.addIngredient(new SimpleCraftingRecipeItem(itemStack, false, null, replacement, null));
        }

        @Override
        SemiShapedCraftingRecipeBuilder addIngredient(CraftingRecipeItem item);

        @Override
        SemiShapedCraftingRecipeBuilder pattern(String... pattern);

        @Override
        SemiShapedCraftingRecipeBuilder pattern(CraftingRecipePattern pattern);

        @Override
        default SemiShapedCraftingRecipeBuilder pattern(final char[]... pattern)
        {
            final String[] pat = new String[pattern.length];
            int i = 0;
            for (final char[] chars : pattern)
            {
                pat[i++] = new String(chars);
            }
            return this.pattern(pat);
        }

        @Override
        ShapedCraftingRecipeItemBuilder addIngredient(char c);

        @Override
        default SemiShapedCraftingRecipeBuilder addIngredient(final char c, final ItemStack itemStack, final boolean ignoreType)
        {
            return this.addIngredient(c, new SimpleCraftingRecipeItem(itemStack, ignoreType, null));
        }

        @Override
        default SemiShapedCraftingRecipeBuilder addIngredient(final char c, final ItemStack itemStack, final ItemStack replacement)
        {
            return this.addIngredient(c, new SimpleCraftingRecipeItem(itemStack, false, null, replacement, null));
        }

        @Override
        SemiShapedCraftingRecipeBuilder addIngredient(char c, CraftingRecipeItem item);
    }

    /**
     * Builder interface for creating shapeless crafting recipes
     */
    interface GroupCraftingRecipeBuilder extends CraftingRecipeBuilder
    {
        @Override
        GroupCraftingRecipeBuilder result(ItemStack itemStack);

        @Override
        default GroupCraftingRecipeBuilder result(final Material material)
        {
            return this.result(new BaseItemStack(material));
        }

        @Override
        default GroupCraftingRecipeBuilder result(final Material material, final int amount)
        {
            return this.result(new BaseItemStack(material, amount));
        }

        @Override
        default GroupCraftingRecipeBuilder result(final Function<CraftingGrid, ItemStack> itemStack)
        {
            return this.result((player, itemStacks) -> itemStack.apply(itemStacks));
        }

        @Override
        GroupCraftingRecipeBuilder result(BiFunction<Player, CraftingGrid, ItemStack> itemStack);

        @Override
        GroupCraftingRecipeBuilder vanilla(boolean vanilla);

        @Override
        GroupCraftingRecipeBuilder priority(long priority);

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
        default GroupCraftingRecipeBuilder grouped()
        {
            throw new IllegalStateException("You can't change type of builder!");
        }

        @Override
        default SemiShapedCraftingRecipeBuilder semiShaped()
        {
            throw new IllegalStateException("You can't change type of builder!");
        }

        @Override
        CraftingRecipeGroup build() throws RuntimeException;

        @Override
        default CraftingRecipeGroup buildAndAdd() throws RuntimeException
        {
            final CraftingRecipeGroup recipe = this.build();
            this.getParent().add(recipe);
            return recipe;
        }

        /**
         * Adds fast group validator, it should do fast check if any recipe in group may be valid for items in inventory. <br>
         * It don't need return valid value, it may return true even if this group don't contains valid recipe for given eq.
         *
         * @param predicate predicate of inventory, should be fast.
         *
         * @return this same builder for method chains.
         */
        GroupCraftingRecipeBuilder validator(Predicate<GridInventory> predicate);

        /**
         * Adds fast group validator as recipe, it should do fast check if any recipe in group may be valid for items in inventory. <br>
         * It don't need return valid value, it may return true even if this group don't contains valid recipe for given eq.
         *
         * @param predicateRecipe predicate recipe of inventory, should be simple.
         *
         * @return this same builder for method chains.
         */
        default GroupCraftingRecipeBuilder validator(final CraftingRecipe predicateRecipe)
        {
            return this.validator(eq -> predicateRecipe.isMatching(eq) != null);
        }

        /**
         * Adds fast group validator as recipe, it should do fast check if any recipe in group may be valid for items in inventory. <br>
         * It don't need return valid value, it may return true even if this group don't contains valid recipe for given eq.
         *
         * @param predicateRecipe predicate recipe of inventory, should be simple.
         *
         * @return this same builder for method chains.
         */
        default GroupCraftingRecipeBuilder validator(final CraftingRecipeBuilder predicateRecipe)
        {
            return this.validator(predicateRecipe.build());
        }

        /**
         * Adds fast group validator as recipe, it should do fast check if any recipe in group may be valid for items in inventory. <br>
         * It don't need return valid value, it may return true even if this group don't contains valid recipe for given eq.
         *
         * @param predicateRecipe predicate recipe of inventory, should be simple.
         *
         * @return this same builder for method chains.
         */
        default GroupCraftingRecipeBuilder validator(final CraftingRecipeItemBuilder<?, ?> predicateRecipe)
        {
            return this.validator(predicateRecipe.build().build());
        }

        /**
         * Adds recipe to this group, this recipe also can be a group.
         *
         * @param recipe recipe to be added.
         *
         * @return this same builder for method chains.
         */
        GroupCraftingRecipeBuilder addRecipe(CraftingRecipe recipe);

        /**
         * Adds recipe to this group, this recipe also can be a group.
         *
         * @param recipe function to transform builder.
         *
         * @return this same builder for method chains.
         */
        GroupCraftingRecipeBuilder addRecipe(UnaryOperator<CraftingRecipeBuilder> recipe);

        /**
         * Adds shaped recipe to this group.
         *
         * @param recipe function to transform builder.
         *
         * @return this same builder for method chains.
         */
        GroupCraftingRecipeBuilder addShapedRecipe(UnaryOperator<ShapedCraftingRecipeBuilder> recipe);

        /**
         * Adds shapeless recipe to this group.
         *
         * @param recipe function to transform builder.
         *
         * @return this same builder for method chains.
         */
        GroupCraftingRecipeBuilder addShapelessRecipe(UnaryOperator<ShapelessCraftingRecipeBuilder> recipe);

        /**
         * Adds grouped recipe to this group.
         *
         * @param recipe function to transform builder.
         *
         * @return this same builder for method chains.
         */
        GroupCraftingRecipeBuilder addGroupedRecipe(UnaryOperator<GroupCraftingRecipeBuilder> recipe);

        /**
         * Adds semi-shaped recipe to this group.
         *
         * @param recipe function to transform builder.
         *
         * @return this same builder for method chains.
         */
        GroupCraftingRecipeBuilder addSemiShapedRecipe(UnaryOperator<SemiShapedCraftingRecipeBuilder> recipe);

        /**
         * Adds shaped recipe to this group.
         *
         * @param recipe function to transform builder.
         *
         * @return this same builder for method chains.
         */
        GroupCraftingRecipeBuilder addShapedRecipe_(Function<ShapedCraftingRecipeBuilder, ShapedCraftingRecipeItemBuilder> recipe);

        /**
         * Adds shapeless recipe to this group.
         *
         * @param recipe function to transform builder.
         *
         * @return this same builder for method chains.
         */
        GroupCraftingRecipeBuilder addShapelessRecipe_(Function<ShapelessCraftingRecipeBuilder, ShapelessCraftingRecipeItemBuilder> recipe);
    }

    /**
     * Builder interface for creating shapeless crafting recipes
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
        default GroupCraftingRecipeBuilder grouped()
        {
            throw new IllegalStateException("You can't change type of builder!");
        }

        @Override
        default SemiShapedCraftingRecipeBuilder semiShaped()
        {
            throw new IllegalStateException("You can't change type of builder!");
        }

        @Override
        ShapelessCraftingRecipe build() throws RuntimeException;

        @Override
        default ShapelessCraftingRecipe buildAndAdd() throws RuntimeException
        {
            final ShapelessCraftingRecipe recipe = this.build();
            this.getParent().add(recipe);
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
            return this.addIngredient(new SimpleCraftingRecipeItem(itemStack, ignoreType, null));
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
            return this.addIngredient(new SimpleCraftingRecipeItem(itemStack, false, null, replacement, null));
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
     * Builder interface for creating shaped crafting recipes
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
        default GroupCraftingRecipeBuilder grouped()
        {
            throw new IllegalStateException("You can't change type of builder!");
        }

        @Override
        default SemiShapedCraftingRecipeBuilder semiShaped()
        {
            throw new IllegalStateException("You can't change type of builder!");
        }

        @Override
        ShapedCraftingRecipe build();

        @Override
        default ShapedCraftingRecipe buildAndAdd() throws RuntimeException
        {
            final ShapedCraftingRecipe recipe = this.build();
            this.getParent().add(recipe);
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
            return this.addIngredient(c, new SimpleCraftingRecipeItem(itemStack, ignoreType, null));
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
            return this.addIngredient(c, new SimpleCraftingRecipeItem(itemStack, false, null, replacement, null));
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
