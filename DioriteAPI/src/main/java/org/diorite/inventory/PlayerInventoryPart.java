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
}
