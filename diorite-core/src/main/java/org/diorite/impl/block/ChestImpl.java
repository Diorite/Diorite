package org.diorite.impl.block;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.inventory.InventoryImpl;
import org.diorite.impl.inventory.block.ChestInventoryImpl;
import org.diorite.impl.world.WorldImpl;
import org.diorite.block.Block;
import org.diorite.block.Chest;
import org.diorite.inventory.Inventory;
import org.diorite.tileentity.TileEntityChest;

public class ChestImpl extends BlockStateImpl implements Chest
{
    private final TileEntityChest tileEntity;
    private final InventoryImpl   inventory;

    public ChestImpl(final Block block)
    {
        super(block);

        this.tileEntity = (TileEntityChest) ((WorldImpl) block.getWorld()).getTileEntity(block.getLocation());

        //TODO: return double chest inventory if needed
        inventory = new ChestInventoryImpl(this);
    }

    @Override
    public Inventory getInventory()
    {
        return this.inventory;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("tileEntity", this.tileEntity).append("inventory", this.inventory).toString();
    }
}
