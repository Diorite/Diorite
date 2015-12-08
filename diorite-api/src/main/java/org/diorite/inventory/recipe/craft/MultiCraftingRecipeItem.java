/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.inventory.recipe.craft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.Diorite;
import org.diorite.entity.Player;
import org.diorite.inventory.item.ItemStack;
import org.diorite.nbt.NbtTag;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;

/**
 * Represent recipe item that is valid for multiple items, it check name/lore/etc only if recipe item have custom one.
 */
public class MultiCraftingRecipeItem implements CraftingRecipeItem
{
    /**
     * Used only by {@link #getItem()}
     */
    protected final ItemStack                                   item;
    /**
     * Pattern items of this recipe.
     */
    protected final Object2BooleanMap<ItemStack>                items;
    /**
     * Validators if this recipe item, allowing to check more item data. <br>
     * Player may be null when using.
     */
    protected final Collection<BiPredicate<Player, ItemStack>>  validators;
    /**
     * Basic Replacment item.
     *
     * @see #getReplacement()
     */
    protected final ItemStack                                   replacement;
    /**
     * Replacment function.
     *
     * @see #getReplacement(Player, CraftingGrid)
     */
    protected final BiFunction<Player, CraftingGrid, ItemStack> replacementFunc;

    /**
     * Construct new recipe item with given item as pattern.
     *
     * @param item       pattern item used by {@link #getItem()}.
     * @param items      pattern items.
     * @param validators validators of thic recipe item, allowing to check additional data of item. Player may be null.
     */
    public MultiCraftingRecipeItem(final ItemStack item, final Map<ItemStack, Boolean> items, final Collection<BiPredicate<Player, ItemStack>> validators)
    {
        this.item = item;
        this.items = new Object2BooleanOpenHashMap<>(items);
        this.validators = (validators == null) ? null : new ArrayList<>(validators);
        this.replacement = null;
        this.replacementFunc = null;
    }

    /**
     * Construct new recipe item with given item as pattern.
     *
     * @param item            pattern item used by {@link #getItem()}.
     * @param items           pattern items.
     * @param replacementFunc replacement item function, see {@link #getReplacement(Player, CraftingGrid)}, may by null.
     * @param replacement     replacement item, see {@link #getReplacement()}
     * @param validators      validators of thic recipe item, allowing to check additional data of item. Player may be null.
     */
    public MultiCraftingRecipeItem(final ItemStack item, final Map<ItemStack, Boolean> items, final BiFunction<Player, CraftingGrid, ItemStack> replacementFunc, final ItemStack replacement, final Collection<BiPredicate<Player, ItemStack>> validators)
    {
        this.item = item;
        this.items = new Object2BooleanOpenHashMap<>(items);
        this.validators = (validators == null) ? null : new ArrayList<>(validators);
        this.replacement = replacement;
        this.replacementFunc = replacementFunc;
    }


    @Override
    public ItemStack isValid(final Player player, final ItemStack item)
    {
        if (item == null)
        {
            return null;
        }

        for (final Entry<ItemStack> entry : this.items.object2BooleanEntrySet())
        {
            final ItemStack patItem = entry.getKey();
            final boolean ignoreData = entry.getBooleanValue();
            final boolean valid = (ignoreData ? patItem.getMaterial().isThisSameID(item.getMaterial()) : patItem.getMaterial().equals(item.getMaterial())) && (patItem.getAmount() <= item.getAmount());
            if (! valid)
            {
                continue;
            }
            if (! fuzzyEquals(patItem.getItemMeta().getRawNbtData(), item.getItemMeta().getRawNbtData()))
            {
                continue;
            }
            if ((this.validators != null) && ! this.validators.stream().allMatch(f -> f.test(player, item)))
            {
                continue;
            }
            final ItemStack is = item.clone();
            is.setAmount(patItem.getAmount());
            return is;
        }
        return null;
    }

    @Override
    public ItemStack getItem()
    {
        return this.item.clone();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("item", this.item).toString();
    }

    @SuppressWarnings("unchecked")
    protected static boolean fuzzyEquals(final List<NbtTag> pattern, final List<NbtTag> check)
    {
        if (pattern == null)
        {
            return true;
        }
        if (check == null)
        {
            return false;
        }
        try
        {
            final List<NbtTag> patternList = new ArrayList<>(pattern);
            final List<NbtTag> list = new ArrayList<>(check);
            final Class<?> type = patternList.isEmpty() ? (list.isEmpty() ? null : list.get(0).getClass()) : patternList.get(0).getClass();
            if (type == null)
            {
                return true;
            }
            if (String.class.isAssignableFrom(type))
            {
                return patternList.equals(list);
            }
            if (Map.class.isAssignableFrom(type))
            {
                for (final Iterator<NbtTag> it = patternList.iterator(); it.hasNext(); )
                {
                    final Map<String, NbtTag> patternMap = (Map<String, NbtTag>) it.next();
                    boolean matches = false;
                    for (final Iterator<NbtTag> it2 = list.iterator(); it2.hasNext(); )
                    {
                        final Map<String, NbtTag> nbtTagMap = (Map<String, NbtTag>) it2.next();
                        if (fuzzyEquals(patternMap, nbtTagMap))
                        {
                            it2.remove();
                            matches = true;
                            break;
                        }
                    }
                    if (! matches)
                    {
                        return false;
                    }
                    it.remove();
                }
            }
            else if (List.class.isAssignableFrom(type))
            {
                for (final Iterator<NbtTag> it = patternList.iterator(); it.hasNext(); )
                {
                    final List<NbtTag> patternList2 = (List<NbtTag>) it.next();
                    boolean matches = false;
                    for (final Iterator<NbtTag> it2 = list.iterator(); it2.hasNext(); )
                    {
                        final List<NbtTag> nbtTagList = (List<NbtTag>) it2.next();
                        if (fuzzyEquals(patternList2, nbtTagList))
                        {
                            it2.remove();
                            matches = true;
                            break;
                        }
                    }
                    if (! matches)
                    {
                        return false;
                    }
                    it.remove();
                }
            }
            else
            {
                for (final Iterator<NbtTag> it = patternList.iterator(); it.hasNext(); )
                {
                    final NbtTag patternTag2 = it.next();

                    if (! list.remove(patternTag2))
                    {
                        return false;
                    }
                    it.remove();
                }
            }
            return patternList.isEmpty();
        } catch (final Exception e)
        {
            if (Diorite.getCore().isDebug())
            {
                e.printStackTrace();
            }
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    protected static boolean fuzzyEquals(final Map<String, NbtTag> pattern, final Map<String, NbtTag> check)
    {
        if (pattern == null)
        {
            return true;
        }
        if (check == null)
        {
            return false;
        }
        try
        {
            for (final Map.Entry<String, NbtTag> entry : pattern.entrySet())
            {
                final NbtTag patternTag = entry.getValue();
                final NbtTag tag = check.get(entry.getKey());
                if (tag == null)
                {
                    return false;
                }
                if (! patternTag.getClass().equals(tag.getClass()))
                {
                    return false;
                }
                if (patternTag instanceof Map)
                {
                    if (! fuzzyEquals((Map<String, NbtTag>) patternTag, (Map<String, NbtTag>) tag))
                    {
                        return false;
                    }
                    continue;
                }
                if (patternTag instanceof List)
                {
                    if (! fuzzyEquals((List<NbtTag>) patternTag, (List<NbtTag>) tag))
                    {
                        return false;
                    }
                    continue;
                }
                if (! patternTag.equals(tag))
                {
                    return false;
                }
            }
            return true;
        } catch (final Exception e)
        {
            if (Diorite.getCore().isDebug())
            {
                e.printStackTrace();
            }
            return false;
        }
    }
}
