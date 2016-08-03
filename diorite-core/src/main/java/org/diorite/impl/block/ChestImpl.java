package org.diorite.impl.block;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.inventory.block.ChestInventoryImpl;
import org.diorite.impl.world.WorldImpl;
import org.diorite.block.Block;
import org.diorite.block.Chest;
import org.diorite.inventory.block.ChestInventory;
import org.diorite.tileentity.TileEntityChest;

public class ChestImpl extends BlockStateImpl implements Chest
{
    private final TileEntityChest chest;
    private final ChestInventory  inventory;

    public ChestImpl(final Block block)
    {
        super(block);

        this.chest = (TileEntityChest) ((WorldImpl) block.getWorld()).getTileEntity(block.getLocation());
        this.inventory = new ChestInventoryImpl(this);
    }

    @Override
    public ChestInventory getInventory()
    {
        return this.inventory;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("chest", this.chest).append("inventory", this.inventory).toString();
    }
}
