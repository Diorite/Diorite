package org.diorite.impl.inventory.recipe.craft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.inventory.GridInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.craft.CraftingGrid;
import org.diorite.inventory.recipe.craft.CraftingRecipeCheckResult;
import org.diorite.inventory.recipe.craft.CraftingRecipeItem;
import org.diorite.inventory.recipe.craft.CraftingRecipePattern;
import org.diorite.inventory.recipe.craft.CraftingRepeatableRecipeItem;
import org.diorite.inventory.recipe.craft.SemiShapedCraftingRecipe;

import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;

public class SemiShapedCraftingRecipeImpl extends CraftingRecipeImpl implements SemiShapedCraftingRecipe
{
    protected final CraftingRecipePattern              pattern;
    protected final List<CraftingRecipeItem>           ingredients;
    protected final List<CraftingRepeatableRecipeItem> repeatableIngredients;

    public SemiShapedCraftingRecipeImpl(final CraftingRecipePattern pattern, final List<CraftingRecipeItem> ingredients, final List<CraftingRepeatableRecipeItem> repeatableIngredients, final ItemStack result, final long priority, final boolean vanilla, final BiFunction<Player, CraftingGrid, ItemStack> resultFunc)
    {
        super(extractResults(result, pattern.getRecipeItems(), ingredients, repeatableIngredients), priority, vanilla, resultFunc);
        this.pattern = pattern;
        this.ingredients = new ArrayList<>(ingredients);
        this.repeatableIngredients = new ArrayList<>(repeatableIngredients);
    }

    @Override
    public CraftingRecipeCheckResult isMatching(final GridInventory inventory)
    {
        final Player player = (inventory.getHolder() instanceof Player) ? (Player) inventory.getHolder() : null;
        final Short2ObjectMap<ItemStack> onCraft = new Short2ObjectOpenHashMap<>(2, .5F);

        final CraftingRecipePattern pattern = this.pattern;
        final int maxPatRow = pattern.getRows(), maxPatCol = pattern.getColumns();
        final int maxInvRow = inventory.getRows(), maxInvCol = inventory.getColumns();
        final CraftingGrid items = new CraftingGridImpl(maxInvRow, maxInvCol);
        final Collection<BiConsumer<Player, CraftingGrid>> reps = new ArrayList<>(maxInvCol * maxInvRow);
        final LinkedList<CraftingRecipeItem> ingredients = new LinkedList<>(this.getIngredients());
        final Collection<CraftingRepeatableRecipeItem> repeatableIngredients = new LinkedList<>(this.getRepeatableIngredients());
        final Map<CraftingRepeatableRecipeItem, List<ItemStack>> repeatableItems = new IdentityHashMap<>(this.repeatableIngredients.size());

        // TODO: ugh, no idea how to do this.
        return null;
    }

    @Override
    public CraftingRecipePattern getPattern()
    {
        return this.pattern;
    }

    @Override
    public List<CraftingRecipeItem> getIngredients()
    {
        return new ArrayList<>(this.ingredients);
    }

    @Override
    public List<? extends CraftingRepeatableRecipeItem> getRepeatableIngredients()
    {
        return new ArrayList<>(this.repeatableIngredients);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("pattern", this.pattern).append("ingredients", this.ingredients).append("repeatableIngredients", this.repeatableIngredients).toString();
    }
}
