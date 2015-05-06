package org.diorite.inventory;

import java.util.Collection;
import java.util.HashMap;
import java.util.ListIterator;

import org.diorite.entity.Player;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.item.ItemStackArray;
import org.diorite.material.Material;

public interface Inventory extends Iterable<ItemStack>
{
    /**
     * @return An array of ItemStacks from the inventory.
     */
    ItemStackArray getContent();

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
    void setContent(ItemStackArray items);

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
        this.setContent(ItemStackArray.create(items));
    }

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
    int remove(Material material);

    /**
     * Remove all items matching given material.
     *
     * @param material material to remove.
     *
     * @return array of slot ids of removed items, empty if no item was removed.
     */
    int[] removeAll(Material material);

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
     * Replace first found item matching given one.
     * NOTE: replace is atomic.
     *
     * @param excepted item to replace.
     * @param newItem  replacement.
     *
     * @return slot id of replaced item, or -1 if no item was replaced.
     */
    int replace(ItemStack excepted, ItemStack newItem);

    /**
     * Replace all found items matching give one.
     * NOTE: every replace is atomic.
     *
     * @param excepted item to replace.
     * @param newItem  replacement.
     *
     * @return array of slot ids of replaced items, empty if no item was replaced.
     */
    int[] replaceAll(ItemStack excepted, ItemStack newItem);

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
    boolean replace(int slot, ItemStack excepted, ItemStack newItem);

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
     * @param itmes      items to remove.
     *
     * @return items that wasn't be removed, or empty array.
     */
    ItemStack[] removeItems(boolean ifContains, ItemStack... itmes);

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
    HashMap<Integer, ? extends ItemStack> all(Material material);

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
    HashMap<Integer, ? extends ItemStack> all(ItemStack item);

    /**
     * Finds the first slot in the inventory containing an ItemStack with the
     * given material
     *
     * @param material The material to look for
     *
     * @return The slot index of the given Material or -1 if not found
     */
    int first(Material material);

    /**
     * Returns the first slot in the inventory containing an ItemStack with
     * the given stack. This will only match a slot if both the type and the
     * amount of the stack match
     *
     * @param item The ItemStack to match against
     *
     * @return The slot index of the given ItemStack or -1 if not found
     */
    int first(ItemStack item);

    /**
     * @return The first empty Slot found, or -1 if no empty slots.
     */
    int firstEmpty();

    /**
     * Checks if the inventory contains any ItemStacks with the given
     * material.
     *
     * @param material The material to check for
     *
     * @return true if an ItemStack is found with the given Material
     */
    boolean contains(Material material);

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
    boolean contains(ItemStack item);

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
    boolean contains(Material material, int amount);

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
    boolean contains(ItemStack item, int amount);

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
    boolean containsAtLeast(ItemStack item, int amount);

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
    ItemStack[] add(ItemStack... itemStack);

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

    /**
     * @return The size of the inventory
     */
    default int size()
    {
        return this.getContent().length();
    }

    @Override
    ListIterator<ItemStack> iterator();
}
