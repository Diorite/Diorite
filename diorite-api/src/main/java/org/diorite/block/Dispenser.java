package org.diorite.block;

import org.diorite.inventory.block.DispenserInventory;

/**
 * Represents a dispenser block.
 */
public interface Dispenser extends BlockState, BlockContainer
{
    /**
     * Returns dispenser's inventory
     *
     * @return inventory
     */
    @Override
    DispenserInventory getInventory();
}
