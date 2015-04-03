package org.diorite.material.blocks;

@SuppressWarnings("MagicNumber")
public enum RotateAxis
{
    UP_DOWN(0b0000),
    EAST_WEST(0b0100),
    NORTH_SOUTH(0b100),
    NONE(0b1100);

    private final byte logFlag;

    RotateAxis(final int logFlag)
    {
        this.logFlag = (byte) logFlag;
    }

    public byte getLogFlag()
    {
        return this.logFlag;
    }

}
