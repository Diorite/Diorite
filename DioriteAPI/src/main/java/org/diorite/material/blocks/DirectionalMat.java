package org.diorite.material.blocks;

import org.diorite.BlockFace;

/**
 * Representing directional (facing in some direction) block.
 */
public interface DirectionalMat
{
    /**
     * @return facing direction.
     */
    BlockFace getBlockFacing();

    /**
     * Returns sub-type of block, based on facing direction.
     *
     * @param face facing of that block.
     *
     * @return sub-type of block
     */
    DirectionalMat getBlockFacing(BlockFace face);
}
