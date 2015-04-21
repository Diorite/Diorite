package org.diorite.material.blocks;

import org.diorite.BlockFace;

public interface Stairs extends Directional
{
    byte UPSIDE_DOWN_FLAG = 0x4;

    boolean isUpsideDown(); // TODO: implement

    Stairs getUpsideDown(final boolean upsideDown);

    static byte combine(final BlockFace face, final boolean upside)
    {
        byte value = upside ? UPSIDE_DOWN_FLAG : 0x0;
        switch (face)
        {
            case WEST:
                value += 0x1;
                break;
            case SOUTH:
                value += 0x2;
                break;
            case NORTH:
                value += 0x3;
                break;
            default:
                break;
        }
        return value;
    }
}
