package org.diorite.material.blocks;

import org.diorite.BlockFace;
import org.diorite.material.BlockMaterialData;

public interface Directional
{
    BlockFace getBlockFacing();

    BlockMaterialData getBlockFacing(BlockFace face);
}
