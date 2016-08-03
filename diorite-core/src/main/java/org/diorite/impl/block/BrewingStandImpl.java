package org.diorite.impl.block;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.block.Block;
import org.diorite.block.BrewingStand;
import org.diorite.inventory.block.BrewingStandInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.tileentity.TileEntityBrewingStand;

public class BrewingStandImpl extends BlockStateImpl implements BrewingStand
{
    private final TileEntityBrewingStand tileEntity;
    private final BrewingStandInventory  inventory;

    public BrewingStandImpl(final Block block, final TileEntityBrewingStand tileEntity, final BrewingStandInventory inventory)
    {
        super(block);
        this.tileEntity = tileEntity;
        this.inventory = inventory;
    }

    @Override
    public ItemStack getLeftInput()
    {
        return this.inventory.getItem(0);
    }

    @Override
    public void setLeftInput(final ItemStack input)
    {
        this.inventory.setItem(0, input);
    }

    @Override
    public ItemStack getMiddleInput()
    {
        return this.inventory.getItem(1);
    }

    @Override
    public void setMiddleInput(final ItemStack input)
    {
        this.inventory.setItem(1, input);
    }

    @Override
    public ItemStack getRightInput()
    {
        return this.inventory.getItem(2);
    }

    @Override
    public void setRightInput(final ItemStack input)
    {
        this.inventory.setItem(2, input);
    }

    @Override
    public ItemStack getIngridient()
    {
        return this.inventory.getItem(3);
    }

    @Override
    public void setIngridient(final ItemStack ingridient)
    {
        this.inventory.setItem(3, ingridient);
    }

    @Override
    public ItemStack getFuel()
    {
        return this.inventory.getItem(4);
    }

    @Override
    public void setFuel(final ItemStack fuel)
    {
        this.inventory.setItem(4, fuel);
    }

    @Override
    public BrewingStandInventory getInventory()
    {
        return this.inventory;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("tileEntity", this.tileEntity).append("inventory", this.inventory).toString();
    }
}
