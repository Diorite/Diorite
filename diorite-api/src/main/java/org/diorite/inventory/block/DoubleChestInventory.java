package org.diorite.inventory.block;

import org.diorite.block.DoubleChest;
import org.diorite.inventory.Inventory;

public interface DoubleChestInventory extends Inventory
{
    Inventory getLeftSide();
    Inventory getRightSide();
}
