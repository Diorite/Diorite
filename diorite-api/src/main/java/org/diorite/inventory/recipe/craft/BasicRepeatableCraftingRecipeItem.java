package org.diorite.inventory.recipe.craft;

import java.util.List;
import java.util.function.BiFunction;

import org.diorite.entity.Player;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.RepeatableRecipeItem;

/**
 * Basic implementation of {@link RepeatableRecipeItem}
 */
public class BasicRepeatableCraftingRecipeItem implements CraftingRepeatableRecipeItem
{
    /**
     * Wrapped recipe item.
     */
    protected final CraftingRecipeItem                                        item;
    /**
     * Transform result func.
     *
     * @see #transform(ItemStack, List)
     */
    protected final BiFunction<ItemStack, List<ItemStack>, ItemStack> transformFunc;

    /**
     * Construct new BasicRepeatableRecipeItem for given recipe item and transform function.
     *
     * @param item          recipe item to wrap.
     * @param transformFunc transform result item function.
     */
    public BasicRepeatableCraftingRecipeItem(final CraftingRecipeItem item, final BiFunction<ItemStack, List<ItemStack>, ItemStack> transformFunc)
    {
        this.item = item;
        this.transformFunc = transformFunc;
    }

    @Override
    public ItemStack transform(final ItemStack currentResult, final List<ItemStack> ingredients)
    {
        return (this.transformFunc == null) ? currentResult : this.transformFunc.apply(currentResult, ingredients);
    }

    @Override
    public ItemStack isValid(final Player player, final ItemStack item)
    {
        return this.item.isValid(player, item);
    }

    @Override
    public BiFunction<Player, ItemStack, ItemStack> asFunction()
    {
        return this.item.asFunction();
    }

    @Override
    public ItemStack getReplacement()
    {
        return this.item.getReplacement();
    }

    @Override
    public ItemStack getItem()
    {
        return this.item.getItem();
    }

    @Override
    public ItemStack getReplacement(final Player player, final CraftingGrid craftingGrid)
    {
        return this.item.getReplacement(player, craftingGrid);
    }
}
