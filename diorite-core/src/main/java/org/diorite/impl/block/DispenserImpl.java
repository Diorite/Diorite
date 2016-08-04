package org.diorite.impl.block;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.inventory.block.DispenserInventoryImpl;
import org.diorite.block.Block;
import org.diorite.block.Dispenser;
import org.diorite.inventory.block.DispenserInventory;
import org.diorite.tileentity.TileEntityDispenser;

public class DispenserImpl extends BlockStateImpl implements Dispenser
{
    private final TileEntityDispenser tileEntity;
    private final DispenserInventory  inventory;

    public DispenserImpl(final Block block)
    {
        super(block);
        this.tileEntity = (TileEntityDispenser) block.getWorld().getTileEntity(block.getLocation());
        this.inventory = new DispenserInventoryImpl(this);
    }

    @Override
    public DispenserInventory getInventory()
    {
        return this.inventory;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("tileEntity", this.tileEntity).append("inventory", this.inventory).toString();
    }
}
