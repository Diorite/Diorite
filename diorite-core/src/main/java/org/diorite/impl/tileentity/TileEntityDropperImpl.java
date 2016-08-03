package org.diorite.impl.tileentity;

import org.diorite.block.Block;
import org.diorite.block.BlockLocation;
import org.diorite.tileentity.TileEntityDropper;

public class TileEntityDropperImpl extends TileEntityDispenserImpl implements TileEntityDropper
{
    public TileEntityDropperImpl(final BlockLocation location, final Block block)
    {
        super(location, block);
    }

    @Override
    public void doTick(final int tps)
    {
        // TODO
    }
}
