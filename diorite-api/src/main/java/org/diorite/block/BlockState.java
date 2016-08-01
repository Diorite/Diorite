package org.diorite.block;

import org.diorite.material.BlockMaterialData;
import org.diorite.world.World;

public interface BlockState
{
    int getX();
    int getY();
    int getZ();

    Block getBlock();

    BlockMaterialData getType();

    void setType(BlockMaterialData type);

    World getWorld();

    BlockLocation getLocation();

    boolean update();
    boolean update(boolean force);
    boolean update(boolean force, boolean applyPhysics);

    boolean isPlaced();
}
