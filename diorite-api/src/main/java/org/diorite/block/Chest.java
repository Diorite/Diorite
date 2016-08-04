package org.diorite.block;

import org.diorite.inventory.Inventory;

/**
 * Represents a chest block.
 */
public interface Chest extends BlockState, BlockContainer
{
    /**
     * Returns chest's inventory
     *
     * @return inventory
     */
    @Override
    Inventory getInventory();
}
