package org.diorite.inventory.block;

import org.diorite.block.Furnace;
import org.diorite.inventory.Inventory;
import org.diorite.inventory.item.ItemStack;

public interface FurnaceInventory extends Inventory
{
    ItemStack getFuel();

    void setFuel(ItemStack fuel);

    ItemStack getSmelting();

    void setSmelting(ItemStack smelting);

    ItemStack getResult();

    void setResult(ItemStack result);

    @Override
    Furnace getHolder();
}
