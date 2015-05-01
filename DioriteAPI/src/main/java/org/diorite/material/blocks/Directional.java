package org.diorite.material.blocks;

import org.diorite.BlockFace;

public interface Directional
{
    BlockFace getBlockFacing();

    Directional getBlockFacing(BlockFace face);
}
