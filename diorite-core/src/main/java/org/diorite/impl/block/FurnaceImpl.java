package org.diorite.impl.block;

import org.diorite.impl.tileentity.TileEntityFurnaceImpl;
import org.diorite.block.Block;
import org.diorite.block.Furnace;
import org.diorite.inventory.block.FurnaceInventory;

public class FurnaceImpl extends BlockStateImpl implements Furnace
{
    private final TileEntityFurnaceImpl furnace;

    public FurnaceImpl(final Block block)
    {
        super(block);

        furnace = null; //TODO: entity getter
    }

    //TODO: all methods below
    @Override
    public short getBurnTime()
    {
        return 0;
    }

    @Override
    public void setBurnTime(final short burnTime)
    {

    }

    @Override
    public short getCookTime()
    {
        return 0;
    }

    @Override
    public void setCookTime(final short cookTime)
    {

    }

    @Override
    public FurnaceInventory getInventory()
    {
        return null;
    }
}
