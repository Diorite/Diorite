package org.diorite.material.blocks;

import org.diorite.BlockFace;

/**
 * Representing attachable block,
 */
public interface AttachableMat extends DirectionalMat
{
    /**
     * @return face that this block is attached on
     */
    default BlockFace getAttachedFace()
    {
        return this.getBlockFacing().getOppositeFace();
    }

    /**
     * Returns sub-type of block, based on attached face.
     *
     * @param face face that this block is attached on
     *
     * @return sub-type of block
     */
    AttachableMat getAttachedFace(BlockFace face);

}
