package org.diorite.inventory;

import java.util.Collection;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.Validate;

import org.diorite.entity.Player;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.item.ItemStackArray;
import org.diorite.material.Material;
import org.diorite.utils.DioriteUtils;

import gnu.trove.TIntCollection;
import gnu.trove.list.array.TIntArrayList;

/**
 * Represent inventory, contains default implementation for most of methods.
 */
public interface Inventory extends Iterable<ItemStack>
{
    /**
     * @return An array of ItemStacks from the inventory.
     */
    ItemStackArray getContents();

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
    default void setContent(final ItemStackArray items)
    {
        final ItemStackArray content = this.getContents();
        for (int i = 0, size = content.length(); i < size; i++)
        {
            content.set(i, items.getOrNull(i));
        }
    }

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
    default void setContent(final ItemStack[] items)
    {
        final ItemStackArray content = this.getContents();
        for (int i = 0, size = content.length(); i < size; i++)
        {
            content.set(i, (i >= items.length) ? null : items[i]);
        }
    }

    /**
     * Remove first found item matching given one.
     *
     * @param itemStack item to remove.
     *
     * @return slot id of removed item, or -1 if no item was removed.
     */
    default int remove(final ItemStack itemStack)
    {
        final ItemStackArray content = this.getContents();

        if (itemStack == null)
        {
            return - 1;
        }
        for (int i = 0, size = content.length(); i < size; i++)
        {
            final ItemStack item = content.get(i);
            if (Objects.equals(item, itemStack))
            {
                content.compareAndSet(i, item, null);
                return i;
            }
        }
        return - 1;
    }

    /**
     * Remove all items matching given one.
     *
     * @param itemStack item to remove.
     *
     * @return array of slot ids of removed items, empty if no item was removed.
     */
    default int[] removeAll(final ItemStack itemStack)
    {
        final ItemStackArray content = this.getContents();
        if (itemStack == null)
        {
            return DioriteUtils.EMPTY_INT;
        }
        final TIntCollection list = new TIntArrayList(10);
        for (int i = 0, size = content.length(); i < size; i++)
        {
            final ItemStack item = content.get(i);
            if (Objects.equals(item, itemStack))
            {
                content.compareAndSet(i, item, null);
                list.add(i);
            }
        }
        if (list.isEmpty())
        {
            return DioriteUtils.EMPTY_INT;
        }
        return list.toArray();
    }

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
    default int remove(final Material material, final boolean ignoreType)
    {
        final ItemStackArray content = this.getContents();

        if (material == null)
        {
            return - 1;
        }
        for (int i = 0, size = content.length(); i < size; i++)
        {
            final ItemStack item = content.get(i);
            if (item == null)
            {
                continue;
            }
            final Material mat = item.getMaterial();
            if (ignoreType)
            {
                if ((material.ordinal() != mat.ordinal()))
                {
                    continue;
                }
            }
            else if (! Objects.equals(mat, material))
            {
                continue;
            }
            content.compareAndSet(i, item, null);
            return i;
        }
        return - 1;
    }

    /**
     * Remove all items matching given material.
     *
     * @param material   material to remove.
     * @param ignoreType if true, then sub-type of given material will be ignored
     *
     * @return array of slot ids of removed items, empty if no item was removed.
     */
    default int[] removeAll(final Material material, final boolean ignoreType)
    {
        final ItemStackArray content = this.getContents();

        if (material == null)
        {
            return DioriteUtils.EMPTY_INT;
        }
        final TIntCollection list = new TIntArrayList(10);
        for (int i = 0, size = content.length(); i < size; i++)
        {
            final ItemStack item = content.get(i);
            if (item == null)
            {
                continue;
            }
            final Material mat = item.getMaterial();
            if (ignoreType)
            {
                if ((material.ordinal() != mat.ordinal()))
                {
                    continue;
                }
            }
            else if (! Objects.equals(mat, material))
            {
                continue;
            }
            content.compareAndSet(i, item, null);
            list.add(i);
        }
        if (list.isEmpty())
        {
            return DioriteUtils.EMPTY_INT;
        }
        return list.toArray();
    }

    /**
     * Replace first found item matching given one.
     * NOTE: replace is atomic.
     *
     * @param excepted item to replace.
     * @param newItem  replacement.
     *
     * @return slot id of replaced item, or -1 if no item was replaced.
     */
    default int replace(final ItemStack excepted, final ItemStack newItem)
    {
        final ItemStackArray content = this.getContents();
        for (int i = 0, size = content.length(); i < size; i++)
        {
            if (content.compareAndSet(i, excepted, newItem))
            {
                return i;
            }
        }
        return - 1;
    }

    /**
     * Replace all found items matching give one.
     * NOTE: every replace is atomic.
     *
     * @param excepted item to replace.
     * @param newItem  replacement.
     *
     * @return array of slot ids of replaced items, empty if no item was replaced.
     */
    default int[] replaceAll(final ItemStack excepted, final ItemStack newItem)
    {
        final ItemStackArray content = this.getContents();
        final TIntCollection list = new TIntArrayList(10);
        for (int i = 0, size = content.length(); i < size; i++)
        {
            if (content.compareAndSet(i, excepted, newItem))
            {
                list.add(i);
            }
        }
        if (list.isEmpty())
        {
            return DioriteUtils.EMPTY_INT;
        }
        return list.toArray();
    }

    /**
     * Replace item on given slot, only if it matches given item.
     * NOTE: this is atomic operation.
     *
     * @param slot     slot to replace.
     * @param excepted item to replace.
     * @param newItem  replacement.
     *
     * @return true if item was replaced.
     */
    default boolean replace(final int slot, final ItemStack excepted, final ItemStack newItem)
    {
        return this.getContents().compareAndSet(slot, excepted, newItem);
    }

    /**
     * Try remove all items from given array,
     * if {@code ifContains} is true, items will be removed only
     * if inventory contains them all, and then empty array (of size 0) will be returned.
     * <p>
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
     * Returns the ItemStack found in the slot at the given index
     *
     * @param index The index of the Slot's ItemStack to return
     *
     * @return The ItemStack in the slot
     */
    default ItemStack getItem(final int index)
    {
        return this.getContents().get(index);
    }

    /**
     * Stores the ItemStack at the given index of the inventory.
     *
     * @param index The index where to put the ItemStack
     * @param item  The ItemStack to set
     *
     * @return previous itemstack in this slot.
     */
    default ItemStack setItem(final int index, final ItemStack item)
    {
        return this.getContents().getAndSet(index, item);
    }

    /**
     * Returns a HashMap with all slots and ItemStacks in the inventory with
     * the given Material.
     * <p>
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
     * <p>
     * The HashMap contains entries where, the key is the slot index, and the
     * value is the ItemStack in that slot. If no matching ItemStack with the
     * given Material is found, an empty map is returned.
     *
     * @param material   The material to look for
     * @param ignoreType if true, then sub-type of given material will be ignored
     *
     * @return A HashMap containing the slot index, ItemStack pairs
     */
    default Map<Integer, ? extends ItemStack> all(final Material material, final boolean ignoreType)
    {
        final ItemStackArray content = this.getContents();
        final Map<Integer, ItemStack> slots = new HashMap<>(10);
        for (int i = 0, size = content.length(); i < size; i++)
        {
            final ItemStack item = content.get(i);
            if (item != null)
            {
                if (ignoreType)
                {
                    if (item.getMaterial().isThisSameID(material))
                    {
                        slots.put(i, item);
                    }
                }
                else if (item.getMaterial().equals(material))
                {
                    slots.put(i, item);
                }
            }
        }
        return slots;

    }

    /**
     * Finds all slots in the inventory containing any ItemStacks with the
     * given ItemStack. This will only match slots if both the type and the
     * amount of the stack match
     * <p>
     * The HashMap contains entries where, the key is the slot index, and the
     * value is the ItemStack in that slot. If no matching ItemStack with the
     * given Material is found, an empty map is returned.
     *
     * @param item The ItemStack to match against
     *
     * @return A map from slot indexes to item at index
     */
    default HashMap<Integer, ? extends ItemStack> all(final ItemStack item)
    {
        final ItemStackArray content = this.getContents();

        final HashMap<Integer, ItemStack> slots = new HashMap<>(10);
        if (item != null)
        {
            for (int i = 0, size = content.length(); i < size; i++)
            {
                final ItemStack itemStack = content.get(i);
                if (item.equals(itemStack))
                {
                    slots.put(i, itemStack);
                }
            }
        }
        return slots;
    }

    /**
     * Finds the first slot in the inventory containing an ItemStack with the
     * given material
     *
     * @param material The material to look for
     *
     * @return The slot index of the given Material or -1 if not found
     */
    default int first(final Material material)
    {
        final ItemStackArray content = this.getContents();

        for (int i = 0, size = content.length(); i < size; i++)
        {
            final ItemStack item = content.get(i);
            if ((item != null) && (item.getMaterial().equals(material)))
            {
                return i;
            }
        }
        return - 1;
    }

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
     * Returns the first slot in the inventory containing an ItemStack with
     * the given stack.
     *
     * @param item       The ItemStack to match against
     * @param withAmount if amount of item must match.
     *
     * @return The slot index of the given ItemStack or -1 if not found
     */
    default int first(final ItemStack item, final boolean withAmount)
    {
        final ItemStackArray content = this.getContents();

        if (item == null)
        {
            return - 1;
        }
        for (int i = 0, size = content.length(); i < size; i++)
        {
            final ItemStack itemStack = content.get(i);
            if (itemStack != null)
            {
                if (withAmount ? item.equals(itemStack) : item.isSimilar(itemStack))
                {
                    return i;
                }
            }
        }
        return - 1;
    }

    /**
     * @return The first empty Slot found, or -1 if no empty slots.
     */
    default int firstEmpty()
    {
        final ItemStackArray content = this.getContents();

        for (int i = 0, size = content.length(); i < size; i++)
        {
            final ItemStack itemStack = content.get(i);
            if (itemStack == null)
            {
                return i;
            }
        }
        return - 1;
    }

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
     * <p>
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
     * <p>
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
     * <p>
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
            while (true)
            {
                final int firstPartial = this.first(item, false);
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

                        final ItemStack stack = new ItemStack(item);
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
    default void clear(final int index)
    {
        this.getContents().set(index, null);

    }

    /**
     * Clears out the whole Inventory.
     */
    default void clear()
    {
        final ItemStackArray content = this.getContents();

        for (int i = 0, size = content.length(); i < size; i++)
        {
            content.set(i, null);
        }
    }

    /**
     * @return A collection of players who are viewing this Inventory.
     */
    Collection<Player> getViewers();

    /**
     * @return A String with the title of inventory.
     */
    String getTitle();

    /**
     * Change title of inventory, vanilla minecraft don't supprot this operation,
     * so it must be done by re-sending whole inventory.
     *
     * @param str new title.
     */
    void setTitle(final String str);

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

    int getWindowId();

    /**
     * @return The size of the inventory
     */
    default int size()
    {
        return this.getContents().length();
    }

    @Override
    ListIterator<ItemStack> iterator();
}
