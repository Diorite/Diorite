package org.diorite.impl.block;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.block.Block;
import org.diorite.block.Dropper;
import org.diorite.inventory.block.DropperInventory;
import org.diorite.tileentity.TileEntityDropper;

public class DropperImpl extends BlockStateImpl implements Dropper
{
    private final TileEntityDropper tileEntity;
    private final DropperInventory  inventory;

    public DropperImpl(final Block block, final TileEntityDropper tileEntity, final DropperInventory inventory)
    {
        super(block);
        this.tileEntity = tileEntity;
        this.inventory = inventory;
    }

    @Override
    public DropperInventory getInventory()
    {
        return this.inventory;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("tileEntity", this.tileEntity).append("inventory", this.inventory).toString();
    }
}
