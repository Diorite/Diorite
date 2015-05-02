package org.diorite.material.blocks;

/**
 * Enum with possible rotate axis.
 */
@SuppressWarnings("MagicNumber")
public enum RotateAxisMat
{
    /**
     * Y axis
     */
    UP_DOWN(0b0000),
    /**
     * X axis
     */
    EAST_WEST(0b0100),
    /**
     * Z axis
     */
    NORTH_SOUTH(0b1000),
    /**
     * No axis
     */
    NONE(0b1100);

    private final byte flag;

    RotateAxisMat(final int flag)
    {
        this.flag = (byte) flag;
    }

    /**
     * @return byte flag used by some blocks to get sub-type of it.
     */
    public byte getFlag()
    {
        return this.flag;
    }

}
