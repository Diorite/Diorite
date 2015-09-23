package org.diorite.inventory;

import org.diorite.entity.Player;

public interface PlayerInventoryPart extends Inventory
{
    /**
     * @return player inventory with this part.
     */
    PlayerInventory getPlayerInventory();

    /**
     * Gets the player belonging to the open inventory
     *
     * @return The holder of the inventory; null if it has no holder.
     */
    @Override
    Player getHolder();

    @Override
    default int getWindowId()
    {
        return this.getPlayerInventory().getWindowId();
    }

    /**
     * Returns offset of player inventory part. <br>
     * Always 0 for root inventory.
     *
     * @return Offset of player inventory part
     */
    int getSlotOffset();

    /**
     * Returns offset of player inventory part relative to given part. <br>
     * May return negative number if used on part that don't contains given part.
     *
     * @param part part to get offset.
     *
     * @return Offset of player inventory part relative to given part
     */
    default int getSlotOffset(final PlayerInventoryPart part)
    {
        return part.getSlotOffset() - this.getSlotOffset();
    }
}
