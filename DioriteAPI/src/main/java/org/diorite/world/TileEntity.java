package org.diorite.world;

import java.util.Set;

import org.diorite.BlockLocation;
import org.diorite.inventory.item.ItemStack;
import org.diorite.utils.math.DioriteRandom;

public interface TileEntity
{
    BlockLocation getLocation();

    Block getBlock();

    /**
     * Implementation of this method should simulate real drop from this tile-entity. <br>
     * Method should only add items resulting from being tile-entity, like chest should add
     * all items from it, but should not add a chest item itself. <br>
     * From simple blocks that drops itself first element of drop list should be always
     * item representation of this block. You may edit it from this method.
     *
     * @param rand  random instance, should be used if random number is needed.
     * @param drops drop list, add drops here.
     */
    void simulateDrop(DioriteRandom rand, Set<ItemStack> drops);
}
