package org.diorite.block;

import org.diorite.inventory.block.DropperInventory;

public interface Dropper extends BlockState, BlockContainer
{
    /**
     * Returns dropper's inventory
     *
     * @return inventory
     */
    @Override
    DropperInventory getInventory();
}
