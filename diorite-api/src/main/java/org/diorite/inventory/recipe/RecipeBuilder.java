package org.diorite.inventory.recipe;

import org.diorite.inventory.recipe.craft.CraftingRecipe;

/**
 * Advanced class for creating recipes
 */
public interface RecipeBuilder
{
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
     * Sets priority of this recipe, see {@link CraftingRecipe#getPriority()}
     *
     * @param priority priority of recipe.
     *
     * @return this same builder for method chains.
     *
     * @see CraftingRecipe#getPriority()
     */
    RecipeBuilder priority(long priority);

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
}
