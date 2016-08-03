package org.diorite.impl.tileentity;

import java.util.Set;

import org.diorite.block.Block;
import org.diorite.inventory.item.ItemStack;
import org.diorite.tileentity.TileEntityChest;
import org.diorite.utils.math.DioriteRandom;

public class TileEntityChestImpl extends TileEntityImpl implements TileEntityChest
{
    private final Block block;

    public TileEntityChestImpl(final Block block)
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
        return block;
    }

    @Override
    public void simulateDrop(final DioriteRandom rand, final Set<ItemStack> drops)
    {
        //TODO
    }
}
