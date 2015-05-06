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
    default ItemStack getItemInHand()
    {
        final Player holder = this.getHolder();
        if (holder == null)
        {
            return null;
        }
        return this.getContents().get(holder.getHeldItemSlot());
    }

    /**
     * Sets the item in hand
     *
     * @param stack Stack to set
     *
     * @return previous itemstack in hand.
     */
    default ItemStack setItemInHand(final ItemStack stack)
    {
        final Player holder = this.getHolder();
        if (holder == null)
        {
            return null;
        }
        return this.getContents().getAndSet(holder.getHeldItemSlot(), stack);
    }

    /**
     * Replace the item in hand, if it matches a excepted one.
     *
     * @param excepted excepted item to replace.
     * @param stack    Stack to set
     *
     * @return true if item was replaced.
     */
    default boolean replaceItemInHand(final ItemStack excepted, final ItemStack stack)
    {
        final Player holder = this.getHolder();
        return (holder != null) && this.getContents().compareAndSet(holder.getHeldItemSlot(), excepted, stack);
    }

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
