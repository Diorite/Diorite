package org.diorite.block;

import org.diorite.inventory.block.TrappedChestInventory;

/**
 * Represents a trapped chest
 */
public interface TrappedChest extends Chest
{
    /**
     * Returns chest's inventory
     *
     * @return inventory
     */
    @Override
    TrappedChestInventory getInventory();
}
