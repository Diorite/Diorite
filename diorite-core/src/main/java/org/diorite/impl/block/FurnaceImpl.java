package org.diorite.impl.block;

import org.diorite.impl.inventory.block.FurnaceInventoryImpl;
import org.diorite.impl.tileentity.TileEntityFurnaceImpl;
import org.diorite.impl.world.WorldImpl;
import org.diorite.block.Block;
import org.diorite.block.Furnace;
import org.diorite.inventory.block.FurnaceInventory;
import org.diorite.inventory.item.ItemStack;

public class FurnaceImpl extends BlockStateImpl implements Furnace
{
    private final TileEntityFurnaceImpl furnace;
    private final FurnaceInventory      inventory;

    public FurnaceImpl(final Block block)
    {
        super(block);

        furnace = (TileEntityFurnaceImpl) ((WorldImpl) block.getWorld()).getTileEntity(block.getLocation());
        inventory = new FurnaceInventoryImpl(this);
    }

    @Override
    public boolean update(boolean force, boolean applyPhysics)
    {
        boolean result = super.update(force, applyPhysics);

        if(result)
        {
            //TODO update tentity
        }

        return result;
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
        return inventory;
    }
}
