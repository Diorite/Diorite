package org.diorite.material.data.drops;

import java.util.Set;

import org.diorite.entity.Entity;
import org.diorite.inventory.item.ItemStack;
import org.diorite.utils.math.DioriteRandom;
import org.diorite.world.Block;

public class PossibleNoDrop extends PossibleDrop
{
    public PossibleNoDrop()
    {
        super(null);
    }

    @Override
    public void simulateDrop(final Entity entity, final DioriteRandom rand, final Set<ItemStack> drops, final ItemStack usedTool, final Block block)
    {
    }
}
