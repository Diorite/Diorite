package org.diorite.block;

import org.diorite.inventory.block.BrewingStandInventory;
import org.diorite.inventory.item.ItemStack;

/**
 * Represents a brewing stand.
 */
public interface BrewingStand extends BlockState, BlockContainer
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

    /**
     * Returns brewing stand's inventory
     *
     * @return inventory
     */
    @Override
    BrewingStandInventory getInventory();
}
