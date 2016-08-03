package org.diorite.impl.block;

import org.diorite.impl.inventory.block.FurnaceInventoryImpl;
import org.diorite.impl.tileentity.TileEntityFurnaceImpl;
import org.diorite.impl.world.WorldImpl;
import org.diorite.block.Block;
import org.diorite.block.Furnace;
import org.diorite.inventory.block.FurnaceInventory;

public class FurnaceImpl extends BlockStateImpl implements Furnace
{
    private final TileEntityFurnaceImpl furnace;
    private final FurnaceInventory      inventory;

    public FurnaceImpl(final Block block)
    {
        super(block);

        this.furnace = (TileEntityFurnaceImpl) ((WorldImpl) block.getWorld()).getTileEntity(block.getLocation());
        this.inventory = new FurnaceInventoryImpl(this, 0); //TODO: Window ID
    }

    @Override
    public boolean update(final boolean force, final boolean applyPhysics)
    {
        final boolean result = super.update(force, applyPhysics);

        if (result)
        {
            //TODO update tentity
        }

        return result;
    }

    @Override
    public short getBurnTime()
    {
        return this.furnace.getBurnTime();
    }

    @Override
    public void setBurnTime(final short burnTime)
    {
        this.furnace.setBurnTime(burnTime);
    }

    @Override
    public short getCookTime()
    {
        return this.furnace.getCookTime();
    }

    @Override
    public void setCookTime(final short cookTime)
    {
        this.furnace.setCookTime(cookTime);
    }

    @Override
    public FurnaceInventory getInventory()
    {
        return this.inventory;
    }
}
