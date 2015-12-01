package org.diorite.inventory.recipe;

/**
 * RecipeGroup, it is some kind of fake recipe that act like RecipeManager too. <br>
 * It allow grouping related recipes to speed up lookup for valid one. <br>
 * Like we have 6 recipes to change log to planks for every type of wood,
 * if we put some other items to crafting (not a log) without group diorite need check
 * 6 times near this same recipe, a log of some type. <br>
 * With group we first validate group recipe, and check if this is any type of log,
 * so it will be only one simple check to say that recipe is invalid instead of 6.
 */
public interface RecipeGroup
{
    /**
     * Removes all recipes and add only vanilla recipes again.
     */
    void reset();

    /**
     * Removes ALL recipes.
     */
    void clear();
}
