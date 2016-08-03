package org.diorite.inventory.block;

import org.diorite.inventory.Inventory;
import org.diorite.inventory.item.ItemStack;

public interface BrewingStandInventory extends Inventory
{
    ItemStack getLeftInput();

    void setLeftInput(ItemStack input);

    ItemStack getMiddleInput();

    void setMiddleInput(ItemStack input);

    ItemStack getRightInput();

    void setRightInput(ItemStack input);

    ItemStack getIngridient();

    void setIngridient(ItemStack ingridient);

    ItemStack getFuel();

    void setFuel(ItemStack fuel);
}
