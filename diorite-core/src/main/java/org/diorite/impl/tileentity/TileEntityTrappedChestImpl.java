package org.diorite.impl.tileentity;

import org.diorite.block.Block;
import org.diorite.tileentity.TileEntityTrappedChest;

public class TileEntityTrappedChestImpl extends TileEntityChestImpl implements TileEntityTrappedChest
{
    public TileEntityTrappedChestImpl(final Block block)
    {
        super(block);
    }

    @Override
    public void doTick(final int tps)
    {
        super.doTick(tps);

        // TODO redstone output?
    }
}
