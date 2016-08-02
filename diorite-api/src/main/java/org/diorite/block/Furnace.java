package org.diorite.block;

import org.diorite.inventory.block.FurnaceInventory;

public interface Furnace extends BlockState, BlockContainer
{
    short getBurnTime();

    void setBurnTime(short burnTime);

    short getCookTime();

    void setCookTime(short cookTime);

    FurnaceInventory getInventory();
}
