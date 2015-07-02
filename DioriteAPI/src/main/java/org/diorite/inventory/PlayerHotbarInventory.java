package org.diorite.inventory;

import org.diorite.entity.Player;
import org.diorite.inventory.item.ItemStack;

public interface PlayerHotbarInventory extends Inventory, PlayerInventoryPart
{
    /**
     * Returns the ItemStack currently hold
     *
     * @return The currently held ItemStack
     */
    ItemStack getItemInHand();

    /**
     * Sets the item in hand
     *
     * @param stack Stack to set
     *
     * @return previous itemstack in hand.
     */
    ItemStack setItemInHand(final ItemStack stack);

    /**
     * Replace the item in hand, if it matches a excepted one.
     *
     * @param excepted excepted item to replace.
     * @param stack    Stack to set
     *
     * @return true if item was replaced.
     *
     * @throws IllegalArgumentException if excepted item isn't impl version of ItemStack, so it can't be == to any item from inventory.
     */
    boolean replaceItemInHand(final ItemStack excepted, final ItemStack stack) throws IllegalArgumentException;

    /**
     * Get the slot number of the currently held item
     *
     * @return Held item slot number
     */
    default int getHeldItemSlot()
    {
        final Player holder = this.getHolder();
        if (holder == null)
        {
            return - 1;
        }
        return holder.getHeldItemSlot();
    }

    /**
     * Set the slot number of the currently held item.
     *
     * @param slot The new slot number
     */
    default void setHeldItemSlot(final int slot)
    {
        final Player holder = this.getHolder();
        if (holder == null)
        {
            return;
        }
        holder.setHeldItemSlot(slot);
    }

    @Override
    default InventoryType getType()
    {
        return InventoryType.PLAYER_HOTBAR;
    }
}
