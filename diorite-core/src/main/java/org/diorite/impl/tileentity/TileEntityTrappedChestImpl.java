package org.diorite.impl.tileentity;

import org.diorite.block.Block;
import org.diorite.nbt.NbtTagCompound;
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
}
