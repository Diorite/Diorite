package org.diorite.impl.block;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.inventory.block.FurnaceInventoryImpl;
import org.diorite.impl.tileentity.TileEntityFurnaceImpl;
import org.diorite.block.Block;
import org.diorite.block.Furnace;
import org.diorite.inventory.block.FurnaceInventory;

public class FurnaceImpl extends BlockStateImpl implements Furnace
{
    private final TileEntityFurnaceImpl tileEntity;
    private final FurnaceInventory      inventory;

    public FurnaceImpl(final Block block)
    {
        super(block);

        this.tileEntity = (TileEntityFurnaceImpl) block.getWorld().getTileEntity(block.getLocation());
        this.inventory = new FurnaceInventoryImpl(this);
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
        return this.tileEntity.getBurnTime();
    }

    @Override
    public void setBurnTime(final short burnTime)
    {
        this.tileEntity.setBurnTime(burnTime);
    }

    @Override
    public short getCookTime()
    {
        return this.tileEntity.getCookTime();
    }

    @Override
    public void setCookTime(final short cookTime)
    {
        this.tileEntity.setCookTime(cookTime);
    }

    @Override
    public FurnaceInventory getInventory()
    {
        return this.inventory;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("tileEntity", this.tileEntity).append("inventory", this.inventory).toString();
    }
}
