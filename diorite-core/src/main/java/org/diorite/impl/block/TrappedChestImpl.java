package org.diorite.impl.block;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.inventory.block.TrappedChestInventoryImpl;
import org.diorite.impl.world.WorldImpl;
import org.diorite.block.Block;
import org.diorite.block.TrappedChest;
import org.diorite.inventory.block.TrappedChestInventory;
import org.diorite.tileentity.TileEntityTrappedChest;

public class TrappedChestImpl extends ChestImpl implements TrappedChest
{
    private final TileEntityTrappedChest tileEntity;
    private final TrappedChestInventory  inventory;

    public TrappedChestImpl(final Block block)
    {
        super(block);

        this.tileEntity = (TileEntityTrappedChest) ((WorldImpl) block.getWorld()).getTileEntity(block.getLocation());
        this.inventory = new TrappedChestInventoryImpl(this);
    }

    @Override
    public TrappedChestInventory getInventory()
    {
        return this.inventory;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("tileEntity", this.tileEntity).append("inventory", this.inventory).toString();
    }
}
