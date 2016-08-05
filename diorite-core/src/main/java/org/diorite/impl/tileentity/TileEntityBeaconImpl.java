package org.diorite.impl.tileentity;

import java.util.Set;

import org.diorite.block.Block;
import org.diorite.inventory.item.ItemStack;
import org.diorite.tileentity.TileEntityBeacon;
import org.diorite.utils.math.DioriteRandom;

public class TileEntityBeaconImpl extends TileEntityImpl implements TileEntityBeacon
{
    private final Block block;

    public TileEntityBeaconImpl(final Block block)
    {
        super(block.getLocation());

        this.block = block;
    }

    @Override
    public void doTick(final int tps)
    {
        //TODO
    }

    @Override
    public Block getBlock()
    {
        return this.block;
    }

    @Override
    public void simulateDrop(final DioriteRandom rand, final Set<ItemStack> drops)
    {
        //TODO
    }
}
