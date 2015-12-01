package org.diorite.inventory.recipe;

/**
 * Represent recipe where some items may be placed in any shape, but some must me placed in valid shape. <br>
 * Also this recipe support multiple usages of one ingredient for shapeless part.
 */
public interface SemiShapedRecipe extends ShapelessRecipe, ShapedRecipe
{
    @Override
    long getPriority();
}
