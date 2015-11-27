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

package org.diorite.inventory;

import java.util.Collection;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import org.diorite.entity.Human;
import org.diorite.entity.Player;
import org.diorite.inventory.item.BaseItemStack;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.slot.Slot;
import org.diorite.material.Material;
import org.diorite.utils.DioriteUtils;

/**
 * Represent inventory, contains default implementation for most of methods.
 */
public interface Inventory extends Iterable<ItemStack>
{
    /**
     * @return copy of array with ItemStacks from the inventory.
     */
    ItemStack[] getContents();

    /**
     * Returns slot properties for given index.
     *
     * @param slot index of slot.
     *
     * @return Slot properties for given index.
     */
    Slot getSlot(int slot);

    /**
     * Completely replaces the inventory's contents. Removes all existing
     * contents and replaces it with the ItemStacks given in the array.
     *
     * @param items A complete replacement for the contents; the length must
     *              be less than or equal to {@link #size()}.
     *
     * @throws IllegalArgumentException If the array has more items than the
     *                                  inventory.
     */
    void setContent(ItemStack[] items);

    /**
     * Remove first found item matching given one.
     *
     * @param itemStack item to remove.
     *
     * @return slot id of removed item, or -1 if no item was removed.
     */
    int remove(ItemStack itemStack);

    /**
     * Remove all items matching given one.
     *
     * @param itemStack item to remove.
     *
     * @return array of slot ids of removed items, empty if no item was removed.
     */
    int[] removeAll(ItemStack itemStack);

    /**
     * Remove first found item matching given material.
     *
     * @param material material to remove.
     *
     * @return slot id of removed item, or -1 if no item was removed.
     */
    default int remove(final Material material)
    {
        return this.remove(material, false);
    }

    /**
     * Remove all items matching given material.
     *
     * @param material material to remove.
     *
     * @return array of slot ids of removed items, empty if no item was removed.
     */
    default int[] removeAll(final Material material)
    {
        return this.removeAll(material, false);
    }

    /**
     * Remove first found item matching given material.
     *
     * @param material   material to remove.
     * @param ignoreType if true, then sub-type of given material will be ignored
     *
     * @return slot id of removed item, or -1 if no item was removed.
     */
    int remove(Material material, boolean ignoreType);

    /**
     * Remove all items matching given material.
     *
     * @param material   material to remove.
     * @param ignoreType if true, then sub-type of given material will be ignored
     *
     * @return array of slot ids of removed items, empty if no item was removed.
     */
    int[] removeAll(Material material, boolean ignoreType);

    /**
     * Remove last found item matching given one.
     *
     * @param itemStack item to remove.
     *
     * @return slot id of removed item, or -1 if no item was removed.
     */
    int removeFromEnd(ItemStack itemStack);

    /**
     * Remove all items matching given one.
     *
     * @param itemStack item to remove.
     *
     * @return array of slot ids of removed items, empty if no item was removed.
     */
    int[] removeAllFromEnd(ItemStack itemStack);

    /**
     * Remove last found item matching given material.
     *
     * @param material material to remove.
     *
     * @return slot id of removed item, or -1 if no item was removed.
     */
    default int removeFromEnd(final Material material)
    {
        return this.remove(material, false);
    }

    /**
     * Remove all items matching given material.
     *
     * @param material material to remove.
     *
     * @return array of slot ids of removed items, empty if no item was removed.
     */
    default int[] removeAllFromEnd(final Material material)
    {
        return this.removeAll(material, false);
    }

    /**
     * Remove last found item matching given material.
     *
     * @param material   material to remove.
     * @param ignoreType if true, then sub-type of given material will be ignored
     *
     * @return slot id of removed item, or -1 if no item was removed.
     */
    int removeFromEnd(Material material, boolean ignoreType);

    /**
     * Remove all items matching given material.
     *
     * @param material   material to remove.
     * @param ignoreType if true, then sub-type of given material will be ignored
     *
     * @return array of slot ids of removed items, empty if no item was removed.
     */
    int[] removeAllFromEnd(Material material, boolean ignoreType);

    /**
     * Replace first found item matching (==) given one.
     *
     * @param excepted item to replace.
     * @param newItem  replacement.
     *
     * @return slot id of replaced item, or -1 if no item was replacement.
     *
     * @throws IllegalArgumentException if excepted item isn't impl version of ItemStack, so it can't be == to any item from inventory.
     */
    int replace(ItemStack excepted, ItemStack newItem) throws IllegalArgumentException;

    /**
     * Replace last found item matching (==) given one.
     *
     * @param excepted item to replace.
     * @param newItem  replacement.
     *
     * @return slot id of replaced item, or -1 if no item was replacement.
     *
     * @throws IllegalArgumentException if excepted item isn't impl version of ItemStack, so it can't be == to any item from inventory.
     */
    int replaceFromEnd(ItemStack excepted, ItemStack newItem) throws IllegalArgumentException;

    /**
     * Replace item on given slot, only if it matches (==) given item.
     *
     * @param slot     slot to replace.
     * @param excepted item to replace.
     * @param newItem  replacement.
     *
     * @return true if item was replaced.
     *
     * @throws IllegalArgumentException if excepted item isn't impl version of ItemStack, so it can't be == to any item from inventory.
     */
    boolean replace(int slot, ItemStack excepted, ItemStack newItem) throws IllegalArgumentException;

    /**
     * Try remove all items from given array,
     * if {@code ifContains} is true, items will be removed only
     * if inventory contains them all, and then empty array (of size 0) will be returned.
     * <br>
     * if {@code ifContains} is false, method will try remove as many items as it can,
     * if it will remove all items, then empty array (of size 0) will be returned, but
     * if any item wasn't removed, method will return array of this same size as given,
     * with items that wasn't removed (so it will be null (if it was removed)
     * or this same item, and this same index, but amount of it may change).
     *
     * @param ifContains if true, then items will be only removed if inventory contains them all.
     * @param items      items to remove.
     *
     * @return items that wasn't be removed, or empty array.
     */
    default ItemStack[] removeItems(final boolean ifContains, ItemStack... items)
    {
        if (items == null)
        {
            return DioriteUtils.EMPTY_ITEM_STACK;
        }
        items = DioriteUtils.compact(false, items);
        synchronized (this.getContents())
        {
            if (ifContains)
            {
                for (final ItemStack item : items)
                {
                    if (! this.containsAtLeast(item))
                    {
                        return items;
                    }
                }
            }
            final ItemStack[] leftover = new ItemStack[items.length];
            boolean fully = true;
            for (int i = 0; i < items.length; i++)
            {
                final ItemStack item = items[i];
                int toDelete = item.getAmount();
                do
                {
                    final int first = this.first(item, false);
                    if (first == - 1)
                    {
                        item.setAmount(toDelete);
                        leftover[i] = item;
                        if (ifContains)
                        {
                            throw new RuntimeException("Unexpected change of items!");
                        }
                        fully = false;
                        break;
                    }
                    final ItemStack itemStack = this.getItem(first);
                    final int amount = itemStack.getAmount();
                    if (amount <= toDelete)
                    {
                        toDelete -= amount;

                        this.clear(first);
                    }
                    else
                    {
                        itemStack.setAmount(amount - toDelete);
                        this.setItem(first, itemStack);
                        toDelete = 0;
                    }
                } while (toDelete > 0);
            }
            return fully ? DioriteUtils.EMPTY_ITEM_STACK : leftover;
        }
    }

    /**
     * Try remove all items from given array,
     * if {@code ifContains} is true, items will be removed only
     * if inventory contains them all, and then empty array (of size 0) will be returned.
     * <br>
     * if {@code ifContains} is false, method will try remove as many items as it can,
     * if it will remove all items, then empty array (of size 0) will be returned, but
     * if any item wasn't removed, method will return array of this same size as given,
     * with items that wasn't removed (so it will be null (if it was removed)
     * or this same item, and this same index, but amount of it may change).
     *
     * @param ifContains if true, then items will be only removed if inventory contains them all.
     * @param items      items to remove.
     *
     * @return items that wasn't be removed, or empty array.
     */
    default ItemStack[] removeItemsFromEnd(final boolean ifContains, ItemStack... items)
    {
        if (items == null)
        {
            return DioriteUtils.EMPTY_ITEM_STACK;
        }
        items = DioriteUtils.compact(false, items);
        synchronized (this.getContents())
        {
            if (ifContains)
            {
                for (final ItemStack item : items)
                {
                    if (! this.containsAtLeast(item))
                    {
                        return items;
                    }
                }
            }
            final ItemStack[] leftover = new ItemStack[items.length];
            boolean fully = true;
            for (int i = 0; i < items.length; i++)
            {
                final ItemStack item = items[i];
                int toDelete = item.getAmount();
                do
                {
                    final int last = this.last(item, false);
                    if (last == - 1)
                    {
                        item.setAmount(toDelete);
                        leftover[i] = item;
                        if (ifContains)
                        {
                            throw new RuntimeException("Unexpected change of items!");
                        }
                        fully = false;
                        break;
                    }
                    final ItemStack itemStack = this.getItem(last);
                    final int amount = itemStack.getAmount();
                    if (amount <= toDelete)
                    {
                        toDelete -= amount;

                        this.clear(last);
                    }
                    else
                    {
                        itemStack.setAmount(amount - toDelete);
                        this.setItem(last, itemStack);
                        toDelete = 0;
                    }
                } while (toDelete > 0);
            }
            return fully ? DioriteUtils.EMPTY_ITEM_STACK : leftover;
        }
    }

    /**
     * Returns the ItemStack found in the slot at the given index
     *
     * @param index The index of the Slot's ItemStack to return
     *
     * @return The ItemStack in the slot
     */
    ItemStack getItem(int index);

    /**
     * Stores the ItemStack at the given index of the inventory.
     *
     * @param index The index where to put the ItemStack
     * @param item  The ItemStack to set
     *
     * @return previous itemstack in this slot.
     */
    ItemStack setItem(int index, ItemStack item);

    /**
     * Get and remove the stack at the supplied position in this Inventory.
     *
     * @param index The index of the ItemStack to poll.
     *
     * @return ItemStack at the specified position or null if the slot is empty or out of bounds
     */
    default ItemStack poll(final int index)
    {
        return this.setItem(index, null);
    }

    /**
     * Returns a HashMap with all slots and ItemStacks in the inventory with
     * the given Material.
     * <br>
     * The HashMap contains entries where, the key is the slot index, and the
     * value is the ItemStack in that slot. If no matching ItemStack with the
     * given Material is found, an empty map is returned.
     *
     * @param material The material to look for
     *
     * @return A HashMap containing the slot index, ItemStack pairs
     */
    default Map<Integer, ? extends ItemStack> all(final Material material)
    {
        return this.all(material, false);
    }

    /**
     * Returns a HashMap with all slots and ItemStacks in the inventory with
     * the given Material.
     * <br>
     * The HashMap contains entries where, the key is the slot index, and the
     * value is the ItemStack in that slot. If no matching ItemStack with the
     * given Material is found, an empty map is returned.
     *
     * @param material   The material to look for
     * @param ignoreType if true, then sub-type of given material will be ignored
     *
     * @return A HashMap containing the slot index, ItemStack pairs
     */
    Map<Integer, ? extends ItemStack> all(Material material, boolean ignoreType);

    /**
     * Finds all slots in the inventory containing any ItemStacks with the
     * given ItemStack. This will only match slots if both the type and the
     * amount of the stack match
     * <br>
     * The HashMap contains entries where, the key is the slot index, and the
     * value is the ItemStack in that slot. If no matching ItemStack with the
     * given Material is found, an empty map is returned.
     *
     * @param item The ItemStack to match against
     *
     * @return A map from slot indexes to item at index
     */
    HashMap<Integer, ? extends ItemStack> all(ItemStack item);

    /**
     * Finds the first slot in the inventory containing an ItemStack with the
     * given material
     *
     * @param material The material to look for
     *
     * @return The slot index of the given Material or -1 if not found
     */
    int first(final Material material);

    /**
     * Finds the first slot in the inventory containing an ItemStack with the
     * given material
     *
     * @param material   The material to look for
     * @param startIndex index to start from.
     *
     * @return The slot index of the given Material or -1 if not found
     */
    int first(Material material, int startIndex);

    /**
     * Finds the first not full slot in the inventory containing an ItemStack with the
     * given material
     *
     * @param material The material to look for
     *
     * @return The slot index of the given Material or -1 if not found
     */
    int firstNotFull(Material material);

    /**
     * Finds the first not full slot in the inventory containing an ItemStack with the
     * given material
     *
     * @param material   The material to look for
     * @param startIndex index to start from.
     *
     * @return The slot index of the given Material or -1 if not found
     */
    int firstNotFull(Material material, int startIndex);

    /**
     * Returns the first slot in the inventory containing an ItemStack with
     * the given stack. This will only match a slot if both the type and the
     * amount of the stack match
     *
     * @param item The ItemStack to match against
     *
     * @return The slot index of the given ItemStack or -1 if not found
     */
    default int first(final ItemStack item)
    {
        return this.first(item, true);
    }

    /**
     * Returns the first not full slot in the inventory containing an ItemStack with
     * the given stack. This will only match a slot if both the type and the
     * amount of the stack match
     *
     * @param item The ItemStack to match against
     *
     * @return The slot index of the given ItemStack or -1 if not found
     */
    default int firstNotFull(final ItemStack item)
    {
        return this.firstNotFull(item, true);
    }

    /**
     * Returns the first slot in the inventory containing an ItemStack with
     * the given stack. This will only match a slot if both the type and the
     * amount of the stack match
     *
     * @param item       The ItemStack to match against
     * @param startIndex index to start from.
     *
     * @return The slot index of the given ItemStack or -1 if not found
     */
    default int firstNotFull(final ItemStack item, final int startIndex)
    {
        return this.firstNotFull(item, startIndex, true);
    }

    /**
     * Returns the first not full slot in the inventory containing an ItemStack with
     * the given stack. This will only match a slot if both the type and the
     * amount of the stack match
     *
     * @param item       The ItemStack to match against
     * @param withAmount if amount of item must match.
     *
     * @return The slot index of the given ItemStack or -1 if not found
     */
    int firstNotFull(ItemStack item, boolean withAmount);

    /**
     * Returns the first slot in the inventory containing an ItemStack with
     * the given stack. This will only match a slot if both the type and the
     * amount of the stack match
     *
     * @param item       The ItemStack to match against
     * @param startIndex index to start from.
     * @param withAmount if amount of item must match.
     *
     * @return The slot index of the given ItemStack or -1 if not found
     */
    int firstNotFull(ItemStack item, int startIndex, boolean withAmount);

    /**
     * Returns the first slot in the inventory containing an ItemStack with
     * the given stack.
     *
     * @param item       The ItemStack to match against
     * @param withAmount if amount of item must match.
     *
     * @return The slot index of the given ItemStack or -1 if not found
     */
    int first(ItemStack item, boolean withAmount);

    /**
     * Returns the first slot in the inventory containing an ItemStack with
     * the given stack. This will only match a slot if both the type and the
     * amount of the stack match
     *
     * @param item       The ItemStack to match against
     * @param startIndex index to start from.
     *
     * @return The slot index of the given ItemStack or -1 if not found
     */
    default int first(final ItemStack item, final int startIndex)
    {
        return this.first(item, startIndex, true);
    }

    /**
     * Returns the first slot in the inventory containing an ItemStack with
     * the given stack.
     *
     * @param item       The ItemStack to match against
     * @param startIndex index to start from.
     * @param withAmount if amount of item must match.
     *
     * @return The slot index of the given ItemStack or -1 if not found
     */
    int first(ItemStack item, int startIndex, boolean withAmount);

    /**
     * @return The first empty Slot found, or -1 if no empty slots.
     */
    int firstEmpty();

    /**
     * Finds the last slot in the inventory containing an ItemStack with the
     * given material
     *
     * @param material The material to look for
     *
     * @return The slot index of the given Material or -1 if not found
     */
    int last(final Material material);

    /**
     * Finds the last slot in the inventory containing an ItemStack with the
     * given material
     *
     * @param material   The material to look for
     * @param startIndex index to start from.
     *
     * @return The slot index of the given Material or -1 if not found
     */
    int last(Material material, int startIndex);

    /**
     * Finds the last not full slot in the inventory containing an ItemStack with the
     * given material
     *
     * @param material The material to look for
     *
     * @return The slot index of the given Material or -1 if not found
     */
    int lastNotFull(Material material);

    /**
     * Finds the last not full slot in the inventory containing an ItemStack with the
     * given material
     *
     * @param material   The material to look for
     * @param startIndex index to start from.
     *
     * @return The slot index of the given Material or -1 if not found
     */
    int lastNotFull(Material material, int startIndex);

    /**
     * Returns the last slot in the inventory containing an ItemStack with
     * the given stack. This will only match a slot if both the type and the
     * amount of the stack match
     *
     * @param item The ItemStack to match against
     *
     * @return The slot index of the given ItemStack or -1 if not found
     */
    default int last(final ItemStack item)
    {
        return this.last(item, true);
    }

    /**
     * Returns the last not full slot in the inventory containing an ItemStack with
     * the given stack. This will only match a slot if both the type and the
     * amount of the stack match
     *
     * @param item The ItemStack to match against
     *
     * @return The slot index of the given ItemStack or -1 if not found
     */
    default int lastNotFull(final ItemStack item)
    {
        return this.lastNotFull(item, true);
    }

    /**
     * Returns the last slot in the inventory containing an ItemStack with
     * the given stack. This will only match a slot if both the type and the
     * amount of the stack match
     *
     * @param item       The ItemStack to match against
     * @param startIndex index to start from.
     *
     * @return The slot index of the given ItemStack or -1 if not found
     */
    default int lastNotFull(final ItemStack item, final int startIndex)
    {
        return this.lastNotFull(item, startIndex, true);
    }

    /**
     * Returns the last not full slot in the inventory containing an ItemStack with
     * the given stack. This will only match a slot if both the type and the
     * amount of the stack match
     *
     * @param item       The ItemStack to match against
     * @param withAmount if amount of item must match.
     *
     * @return The slot index of the given ItemStack or -1 if not found
     */
    int lastNotFull(ItemStack item, boolean withAmount);

    /**
     * Returns the last slot in the inventory containing an ItemStack with
     * the given stack. This will only match a slot if both the type and the
     * amount of the stack match
     *
     * @param item       The ItemStack to match against
     * @param startIndex index to start from.
     * @param withAmount if amount of item must match.
     *
     * @return The slot index of the given ItemStack or -1 if not found
     */
    int lastNotFull(ItemStack item, int startIndex, boolean withAmount);

    /**
     * Returns the last slot in the inventory containing an ItemStack with
     * the given stack.
     *
     * @param item       The ItemStack to match against
     * @param withAmount if amount of item must match.
     *
     * @return The slot index of the given ItemStack or -1 if not found
     */
    int last(ItemStack item, boolean withAmount);

    /**
     * Returns the last slot in the inventory containing an ItemStack with
     * the given stack. This will only match a slot if both the type and the
     * amount of the stack match
     *
     * @param item       The ItemStack to match against
     * @param startIndex index to start from.
     *
     * @return The slot index of the given ItemStack or -1 if not found
     */
    default int last(final ItemStack item, final int startIndex)
    {
        return this.last(item, startIndex, true);
    }

    /**
     * Returns the last slot in the inventory containing an ItemStack with
     * the given stack.
     *
     * @param item       The ItemStack to match against
     * @param startIndex index to start from.
     * @param withAmount if amount of item must match.
     *
     * @return The slot index of the given ItemStack or -1 if not found
     */
    int last(ItemStack item, int startIndex, boolean withAmount);

    /**
     * @return The last empty Slot found, or -1 if no empty slots.
     */
    int lastEmpty();

    /**
     * Checks if the inventory contains any ItemStacks with the given
     * material.
     *
     * @param material The material to check for
     *
     * @return true if an ItemStack is found with the given Material
     */
    default boolean contains(final Material material)
    {
        return this.contains(material, false);
    }

    /**
     * Checks if the inventory contains any ItemStacks with the given
     * material.
     *
     * @param material   The material to check for
     * @param ignoreType if true, then sub-type of given material will be ignored
     *
     * @return true if an ItemStack is found with the given Material
     */
    default boolean contains(final Material material, final boolean ignoreType)
    {
        for (final ItemStack item : this)
        {
            if (item != null)
            {
                if (ignoreType)
                {
                    if (item.getMaterial().isThisSameID(material))
                    {
                        return true;
                    }
                }
                else if (item.getMaterial().equals(material))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the inventory contains any ItemStacks matching the given
     * ItemStack.
     * <br>
     * This will only return true if both the type and the amount of the stack
     * match.
     *
     * @param item The ItemStack to match against
     *
     * @return false if item is null, true if any exactly matching ItemStacks
     * were found
     */
    default boolean contains(final ItemStack item)
    {
        if (item == null)
        {
            return false;
        }
        for (final ItemStack itemStack : this)
        {
            if (item.equals(itemStack))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the inventory contains any ItemStacks with the given
     * material, adding to at least the minimum amount specified.
     *
     * @param material The material to check for
     * @param amount   The minimum amount
     *
     * @return true if amount is less than 1, true if enough ItemStacks were
     * found to add to the given amount
     */
    default boolean contains(final Material material, int amount)
    {
        if (amount <= 0)
        {
            return true;
        }
        for (final ItemStack item : this)
        {
            if ((item != null) && (item.getMaterial().equals(material)) && ((amount -= item.getAmount()) <= 0))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the inventory contains at least the minimum amount specified
     * of exactly matching ItemStacks.
     * <br>
     * An ItemStack only counts if both the type and the amount of the stack
     * match.
     *
     * @param item   the ItemStack to match against
     * @param amount how many identical stacks to check for
     *
     * @return false if item is null, true if amount less than 1, true if
     * amount of exactly matching ItemStacks were found
     *
     * @see #containsAtLeast(ItemStack, int)
     */
    default boolean contains(final ItemStack item, int amount)
    {
        if (item == null)
        {
            return false;
        }
        if (amount <= 0)
        {
            return true;
        }
        for (final ItemStack itemStack : this)
        {
            if (item.equals(itemStack))
            {
                amount--;
                if (amount <= 0)
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the inventory contains ItemStacks matching the given
     * ItemStack whose amounts sum to at least the minimum amount specified.
     *
     * @param item   the ItemStack to match against
     * @param amount the minimum amount
     *
     * @return false if item is null, true if amount less than 1, true if
     * enough ItemStacks were found to add to the given amount
     */
    default boolean containsAtLeast(final ItemStack item, int amount)
    {
        if (item == null)
        {
            return false;
        }
        if (amount <= 0)
        {
            return true;
        }
        for (final ItemStack itemStack : this)
        {
            if ((item.isSimilar(itemStack)) && ((amount -= itemStack.getAmount()) <= 0))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the inventory contains ItemStacks matching the given
     * ItemStack whose amounts sum to at least the minimum amount specified.
     *
     * @param item the ItemStack to match against
     *
     * @return false if item is null, true if
     * enough ItemStacks were found to add to the given amount
     */
    default boolean containsAtLeast(final ItemStack item)
    {
        return this.containsAtLeast(item, 1);
    }

    /**
     * Stores the given ItemStacks in the inventory. This will try to fill
     * existing stacks and empty slots as well as it can.
     * <br>
     * The returned array contains what it couldn't store, if all items fit
     * to the inventory, then returned array is empty (size 0), otherwise
     * it will be array of this same size as given one, contains ItemStacks that
     * didn't fit.
     *
     * @param items The ItemStacks to add
     *
     * @return A array containing items that didn't fit.
     */
    default ItemStack[] add(final ItemStack... items)
    {
        Validate.noNullElements(items, "Item cannot be null");

        final ItemStack[] leftover = new ItemStack[items.length];
        boolean fully = true;
        for (int i = 0; i < items.length; i++)
        {
            final ItemStack item = items[i];
            int firstPartial = - 1;
            while (true)
            {
                firstPartial = this.firstNotFull(item, firstPartial + 1, false);
                if (firstPartial == - 1)
                {
                    final int firstFree = this.firstEmpty();
                    if (firstFree == - 1)
                    {
                        leftover[i] = item;
                        fully = false;
                        break;
                    }
                    if (item.getAmount() > item.getMaterial().getMaxStack())
                    {

                        final ItemStack stack = new BaseItemStack(item);
                        stack.setAmount(item.getMaterial().getMaxStack());
                        this.setItem(firstFree, stack);
                        item.setAmount(item.getAmount() - item.getMaterial().getMaxStack());
                    }
                    else
                    {
                        this.setItem(firstFree, item);
                        break;
                    }
                }
                else
                {
                    final ItemStack itemStack = this.getItem(firstPartial);

                    if (itemStack.getAmount() >= itemStack.getMaterial().getMaxStack())
                    {
                        continue;
                    }

                    final int amount = item.getAmount();
                    final int partialAmount = itemStack.getAmount();
                    final int maxAmount = itemStack.getMaterial().getMaxStack();
                    if ((amount + partialAmount) <= maxAmount)
                    {
                        itemStack.setAmount(amount + partialAmount);
                        break;
                    }
                    itemStack.setAmount(maxAmount);
                    item.setAmount((amount + partialAmount) - maxAmount);
                }
            }
        }
        return fully ? DioriteUtils.EMPTY_ITEM_STACK : leftover;
    }

    /**
     * Stores the given ItemStacks in the inventory. This will try to fill
     * existing stacks and empty slots as well as it can.
     * <br>
     * The returned array contains what it couldn't store, if all items fit
     * to the inventory, then returned array is empty (size 0), otherwise
     * it will be array of this same size as given one, contains ItemStacks that
     * didn't fit.
     *
     * @param items The ItemStacks to add
     *
     * @return A array containing items that didn't fit.
     */
    default ItemStack[] addFromEnd(final ItemStack... items)
    {
        Validate.noNullElements(items, "Item cannot be null");

        final ItemStack[] leftover = new ItemStack[items.length];
        boolean fully = true;
        for (int i = 0; i < items.length; i++)
        {
            final ItemStack item = items[i];
            int lastPartial = this.size();
            while (true)
            {
                lastPartial = this.lastNotFull(item, lastPartial - 1, false);
                if (lastPartial == - 1)
                {
                    final int lastFree = this.lastEmpty();
                    if (lastFree == - 1)
                    {
                        leftover[i] = item;
                        fully = false;
                        break;
                    }
                    if (item.getAmount() > item.getMaterial().getMaxStack())
                    {

                        final ItemStack stack = new BaseItemStack(item);
                        stack.setAmount(item.getMaterial().getMaxStack());
                        this.setItem(lastFree, stack);
                        item.setAmount(item.getAmount() - item.getMaterial().getMaxStack());
                    }
                    else
                    {
                        this.setItem(lastFree, item);
                        break;
                    }
                }
                else
                {
                    final ItemStack itemStack = this.getItem(lastPartial);

                    if (itemStack.getAmount() >= itemStack.getMaterial().getMaxStack())
                    {
                        continue;
                    }

                    final int amount = item.getAmount();
                    final int partialAmount = itemStack.getAmount();
                    final int maxAmount = itemStack.getMaterial().getMaxStack();
                    if ((amount + partialAmount) <= maxAmount)
                    {
                        itemStack.setAmount(amount + partialAmount);
                        break;
                    }
                    itemStack.setAmount(maxAmount);
                    item.setAmount((amount + partialAmount) - maxAmount);
                }
            }
        }
        return fully ? DioriteUtils.EMPTY_ITEM_STACK : leftover;
    }

    /**
     * Clears out a particular slot in the index.
     *
     * @param index The index to empty.
     */
    void clear(int index);

    /**
     * Clears out the whole Inventory.
     */
    void clear();

    /**
     * Returns A collection of humans who are viewing this Inventory.
     *
     * @return A collection of humans who are viewing this Inventory.
     */
    Collection<? extends Human> getViewers();

    /**
     * Returns string with the title of inventory.
     *
     * @return A String with the title of inventory.
     */
    String getTitle();

    /**
     * Change title of inventory, vanilla minecraft don't supprot this operation,
     * so it must be done by re-sending whole inventory.
     *
     * @param str new title.
     */
    void setTitle(String str);

    /**
     * Force re-send inventory contents to given player.
     * This player must be one of viewer.
     *
     * @param player player to send updated inventory.
     *
     * @throws IllegalArgumentException if player isn't viewer of inventory.
     */
    void update(Player player) throws IllegalArgumentException;

    /**
     * Force re-send inventory contents to all viewers.
     */
    void update();

    /**
     * @return The {@link InventoryType} representing the type of inventory.
     */
    InventoryType getType();

    /**
     * Gets the block or entity belonging to the open inventory
     *
     * @return The holder of the inventory; null if it has no holder.
     */
    InventoryHolder getHolder();

    /**
     * Returns id of this inventory window.
     *
     * @return id of this inventory window.
     */
    int getWindowId();

    /**
     * Returns size of this inventory.
     *
     * @return The size of the inventory
     */
    int size();

    @Override
    ListIterator<ItemStack> iterator();
}
