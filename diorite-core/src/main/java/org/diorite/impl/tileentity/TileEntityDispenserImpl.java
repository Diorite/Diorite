package org.diorite.impl.tileentity;

import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.block.Block;
import org.diorite.block.BlockLocation;
import org.diorite.inventory.item.ItemStack;
import org.diorite.tileentity.TileEntityDispenser;
import org.diorite.utils.math.DioriteRandom;

public class TileEntityDispenserImpl extends TileEntityImpl implements TileEntityDispenser
{
    private final Block block;

    public TileEntityDispenserImpl(final BlockLocation location, final Block block)
    {
        super(location);
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
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("block", this.block).toString();
    }
}
