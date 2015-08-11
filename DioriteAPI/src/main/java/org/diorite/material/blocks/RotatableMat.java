package org.diorite.material.blocks;

import org.diorite.BlockFace;

/**
 * Represent blocks that can be placed in one axis, like NORTH-SOUTH
 */
public interface RotatableMat extends DirectionalMat
{
    /**
     * @return rotate axis of block.
     */
    RotateAxisMat getRotateAxis();

    /**
     * Returns sub-type of block, based on {@link RotateAxisMat}.
     *
     * @param axis rotate axis of block.
     *
     * @return sub-type of block
     */
    RotatableMat getRotated(RotateAxisMat axis);

    @Override
    default BlockFace getBlockFacing()
    {
        switch (this.getRotateAxis())
        {
            case UP_DOWN:
                return BlockFace.UP;
            case EAST_WEST:
                return BlockFace.EAST;
            case NORTH_SOUTH:
                return BlockFace.NORTH;
            case NONE:
                return BlockFace.SELF;
            default:
                return null;
        }
    }
}
