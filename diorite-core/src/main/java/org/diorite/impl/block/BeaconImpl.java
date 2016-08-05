package org.diorite.impl.block;

import org.diorite.impl.inventory.block.BeaconInventoryImpl;
import org.diorite.block.Beacon;
import org.diorite.block.Block;
import org.diorite.inventory.Inventory;
import org.diorite.inventory.block.BeaconInventory;
import org.diorite.tileentity.TileEntityBeacon;

public class BeaconImpl extends BlockStateImpl implements Beacon
{
    private final TileEntityBeacon tileEntity;
    private final BeaconInventory  inventory;

    private BeaconImpl(final Block block)
    {
        super(block);

        this.tileEntity = (TileEntityBeacon) block.getWorld().getTileEntity(block.getLocation());
        this.inventory = new BeaconInventoryImpl(this);
    }

    @Override
    public Inventory getInventory()
    {
        return this.inventory;
    }
}
