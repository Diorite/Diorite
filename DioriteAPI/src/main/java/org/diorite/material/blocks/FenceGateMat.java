package org.diorite.material.blocks;

import org.diorite.BlockFace;

/**
 * Representing fence gate blocks.
 */
public interface FenceGateMat extends DirectionalMat, OpenableMat
{
    byte OPEN_FLAG = 0x4;

    /**
     * Returns one of gate sub-type based on facing direction and open state.
     * It will never return null.
     *
     * @param blockFace facing direction of gate.
     * @param open      if gate should be open.
     *
     * @return sub-type of gate
     */
    FenceGateMat getType(BlockFace face, boolean open);

    static byte combine(final BlockFace face, final boolean open)
    {
        byte result = open ? OPEN_FLAG : 0x0;
        switch (face)
        {
            case WEST:
                result |= 0x1;
                break;
            case NORTH:
                result |= 0x2;
                break;
            case EAST:
                result |= 0x3;
                break;
            default:
                break;
        }
        return result;
    }
}
