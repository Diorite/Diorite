package org.diorite.block;

import org.diorite.inventory.block.FurnaceInventory;

/**
 * Represents a furnace block.
 */
public interface Furnace extends BlockState, BlockContainer
{
    /**
     * Returns furnace's burn time
     *
     * @return burn time
     */
    short getBurnTime();

    /**
     * Sets furnace's burn time
     *
     * @param burnTime defines burn time
     */
    void setBurnTime(short burnTime);

    /**
     * Returns furnace's cook time
     *
     * @return cook time
     */
    short getCookTime();

    /**
     * Sets furnace's cook time
     *
     * @param cookTime defines cook time
     */
    void setCookTime(short cookTime);

    /**
     * Returns furnace's inventory
     *
     * @return inventory
     */
    FurnaceInventory getInventory();
}
