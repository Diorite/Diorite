package org.diorite.impl.tileentity;

import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.block.Block;
import org.diorite.inventory.InventoryType;
import org.diorite.inventory.item.ItemStack;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.tileentity.TileEntityBrewingStand;
import org.diorite.utils.math.DioriteRandom;

public class TileEntityBrewingStandImpl extends TileEntityImpl implements TileEntityBrewingStand
{
    private final Block block;
    private ItemStack[] items = new ItemStack[InventoryType.BREWING.getSize()];

    public TileEntityBrewingStandImpl(final Block block)
    {
        super(block.getLocation());
        this.block = block;
    }

    @Override
    public void doTick(final int tps)
    {
        // TODO
    }

    @Override
    public Block getBlock()
    {
        return this.block;
    }

    @Override
    public void simulateDrop(final DioriteRandom rand, final Set<ItemStack> drops)
    {
        // TODO
    }

    @Override
    public void loadFromNbt(final NbtTagCompound nbtTileEntity)
    {
        super.loadFromNbt(nbtTileEntity);

        //TODO
    }

    @Override
    public void saveToNbt(final NbtTagCompound nbtTileEntity)
    {
        super.saveToNbt(nbtTileEntity);

        //TODO
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("block", this.block).toString();
    }
}
