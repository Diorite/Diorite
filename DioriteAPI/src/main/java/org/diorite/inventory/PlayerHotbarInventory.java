package org.diorite.inventory;

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
    ItemStack setItemInHand(ItemStack stack);

    /**
     * Replace the item in hand, if it matches a excepted one.
     *
     * @param excepted excepted item to replace.
     * @param stack    Stack to set
     *
     * @return true if item was replaced.
     */
    boolean replaceItemInHand(ItemStack excepted, ItemStack stack);

    /**
     * Get the slot number of the currently held item
     *
     * @return Held item slot number
     */
    int getHeldItemSlot();

    /**
     * Set the slot number of the currently held item.
     *
     * @param slot The new slot number
     */
    void setHeldItemSlot(int slot);

    @Override
    default InventoryType getType()
    {
        return InventoryType.PLAYER_HOTBAR;
    }
}
