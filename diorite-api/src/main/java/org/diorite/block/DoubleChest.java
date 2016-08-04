package org.diorite.block;

import org.diorite.inventory.Inventory;
import org.diorite.inventory.InventoryHolder;
import org.diorite.inventory.block.DoubleChestInventory;

/**
 * Represents a double chest block.
 */
public class DoubleChest implements InventoryHolder
{
    private final DoubleChestInventory inventory;

    public DoubleChest(DoubleChestInventory inventory)
    {
        this.inventory = inventory;
    }

    public InventoryHolder getLeftSide()
    {
        return this.inventory.getLeftSide().getHolder();
    }

    public InventoryHolder getRightSide()
    {
        return this.inventory.getRightSide().getHolder();
    }

    @Override
    public Inventory getInventory()
    {
        return this.inventory;
    }
}
