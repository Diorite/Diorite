/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.impl.inventory.recipe.craft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import org.diorite.inventory.recipe.craft.CraftingRepeatableRecipeItem;
import org.diorite.inventory.recipe.craft.ShapelessCraftingRecipe;

import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;

/**
 * Implementation of shapeless recipe
 */
public class ShapelessCraftingRecipeImpl extends CraftingRecipeImpl implements ShapelessCraftingRecipe
{
    protected final List<CraftingRecipeItem>           ingredients;
    protected final List<CraftingRepeatableRecipeItem> repeatableIngredients;

    public ShapelessCraftingRecipeImpl(final List<CraftingRecipeItem> ingredients, final List<CraftingRepeatableRecipeItem> repeatableIngredients, final ItemStack result, final long priority, final boolean vanilla, final BiFunction<Player, CraftingGrid, ItemStack> resultFunc)
    {
        super(extractResults(result, ingredients, repeatableIngredients), priority, vanilla, resultFunc);
        this.ingredients = new ArrayList<>(ingredients);
        this.repeatableIngredients = new ArrayList<>(repeatableIngredients);
    }

    @Override
    public CraftingRecipeCheckResult isMatching(final GridInventory inventory)
    {
        final Player player = (inventory.getHolder() instanceof Player) ? (Player) inventory.getHolder() : null;
        final Short2ObjectMap<ItemStack> onCraft = new Short2ObjectOpenHashMap<>(2, .5F);

        final int maxInvRow = inventory.getRows(), maxInvCol = inventory.getColumns();
        final LinkedList<CraftingRecipeItem> ingredients = new LinkedList<>(this.getIngredients());
        final Collection<CraftingRepeatableRecipeItem> repeatableIngredients = new LinkedList<>(this.getRepeatableIngredients());
        final Collection<BiConsumer<Player, CraftingGrid>> reps = new ArrayList<>(maxInvCol * maxInvRow);
        final CraftingGrid items = new CraftingGridImpl(maxInvRow, maxInvCol);
        int col = - 1, row = 0;
        ItemStack result;
        if (this.repeatableIngredients.isEmpty())
        {
            for (short i = 1, size = (short) inventory.size(); i < size; i++)
            {
                if (++ col >= maxInvCol)
                {
                    col = 0;
                    if (++ row > maxInvRow)
                    {
                        throw new IllegalStateException("Inventory is larger than excepted.");
                    }
                }
                final ItemStack item = inventory.getItem(i);
                if (item == null)
                {
                    continue;
                }
                boolean matching = false;
                //noinspection Duplicates
                for (final Iterator<CraftingRecipeItem> iterator = ingredients.iterator(); iterator.hasNext(); )
                {
                    final CraftingRecipeItem ingredient = iterator.next();
                    final ItemStack valid = ingredient.isValid(player, item);
                    if (valid != null)
                    {
                        final short icpy = i;
                        reps.add((p, c) -> {
                            final ItemStack repl = ingredient.getReplacement(p, c);
                            if (repl != null)
                            {
                                onCraft.put(icpy, repl);
                            }
                        });
                        items.setItem(row, col, valid);
                        iterator.remove();
                        matching = true;
                        break;
                    }
                }
                if (! matching)
                {
                    return null;
                }
            }
            result = (this.resultFunc == null) ? this.result : this.resultFunc.apply(player, items.clone());
        }
        else
        {
            final Map<CraftingRepeatableRecipeItem, List<ItemStack>> repeatableItems = new IdentityHashMap<>(this.repeatableIngredients.size());
            for (short i = 1, size = (short) inventory.size(); i < size; i++)
            {
                if (++ col >= maxInvCol)
                {
                    col = 0;
                    if (++ row > maxInvRow)
                    {
                        throw new IllegalStateException("Inventory is larger than excepted.");
                    }
                }
                final ItemStack item = inventory.getItem(i);
                if (item == null)
                {
                    continue;
                }
                boolean matching = false;
                if (! ingredients.isEmpty())
                {
                    //noinspection Duplicates
                    for (final Iterator<CraftingRecipeItem> iterator = ingredients.iterator(); iterator.hasNext(); )
                    {
                        final CraftingRecipeItem ingredient = iterator.next();
                        final ItemStack valid = ingredient.isValid(player, item);
                        if (valid != null)
                        {
                            final short icpy = i;
                            reps.add((p, c) -> {
                                final ItemStack repl = ingredient.getReplacement(p, c);
                                if (repl != null)
                                {
                                    onCraft.put(icpy, repl);
                                }
                            });
                            items.setItem(row, col, valid);
                            iterator.remove();
                            matching = true;
                            break;
                        }
                    }
                }
                if (! matching)
                {
                    for (final CraftingRepeatableRecipeItem ingredient : this.repeatableIngredients)
                    {
                        final ItemStack valid = ingredient.isValid(player, item);
                        if (valid != null)
                        {
                            final short icpy = i;
                            reps.add((p, c) -> {
                                final ItemStack repl = ingredient.getReplacement(p, c);
                                if (repl != null)
                                {
                                    onCraft.put(icpy, repl);
                                }
                            });
                            List<ItemStack> repItems = repeatableItems.get(ingredient);
                            if (repItems == null)
                            {
                                repeatableIngredients.remove(ingredient);
                                repItems = new ArrayList<>(10);
                                repeatableItems.put(ingredient, repItems);
                            }
                            repItems.add(valid);
                            items.setItem(row, col, valid);
                            matching = true;
                            break;
                        }
                    }
                }
                if (! matching)
                {
                    return null;
                }
            }
            if (! repeatableIngredients.isEmpty())
            {
                return null;
            }
            result = (this.resultFunc == null) ? this.result : this.resultFunc.apply(player, items.clone());
            for (final Entry<CraftingRepeatableRecipeItem, List<ItemStack>> entry : repeatableItems.entrySet())
            {
                result = entry.getKey().transform(result, entry.getValue());
                result = entry.getKey().transform(result, items);
            }
        }
        reps.forEach(c -> c.accept(player, items));
        return ingredients.isEmpty() ? new CraftingRecipeCheckResultImpl(this, result, items, onCraft) : null;
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("ingredients", this.ingredients).append("repeatableIngredients", this.repeatableIngredients).toString();
    }
}
