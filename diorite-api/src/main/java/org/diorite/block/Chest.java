package org.diorite.block;

import org.diorite.inventory.block.ChestInventory;

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
    ChestInventory getInventory();
}
