package org.diorite.impl.tileentity;

import java.util.Set;

import org.diorite.block.Block;
import org.diorite.block.BlockLocation;
import org.diorite.inventory.item.ItemStack;
import org.diorite.tileentity.TileEntityFurnace;
import org.diorite.utils.math.DioriteRandom;

public class TileEntityFurnaceImpl extends TileEntityImpl implements TileEntityFurnace
{
    //TODO
    public TileEntityFurnaceImpl(final BlockLocation location)
    {
        super(location);
    }

    @Override
    public void doTick(final int tps)
    {
        //TODO
    }

    @Override
    public Block getBlock()
    {
        return null;
    }

    @Override
    public void simulateDrop(final DioriteRandom rand, final Set<ItemStack> drops)
    {
        //TODO
    }
}
