package org.diorite.impl.block;

import org.diorite.impl.inventory.block.ChestInventoryImpl;
import org.diorite.impl.tileentity.TileEntityChestImpl;
import org.diorite.impl.world.WorldImpl;
import org.diorite.block.Block;
import org.diorite.block.Chest;
import org.diorite.inventory.block.ChestInventory;

public class ChestImpl extends BlockStateImpl implements Chest
{
    private final TileEntityChestImpl chest;
    private final ChestInventory      inventory;

    public ChestImpl(final Block block)
    {
        super(block);

        this.chest = (TileEntityChestImpl) ((WorldImpl) block.getWorld()).getTileEntity(block.getLocation());
        this.inventory = new ChestInventoryImpl(this);
    }

    @Override
    public ChestInventory getInventory()
    {
        return this.inventory;
    }
}
