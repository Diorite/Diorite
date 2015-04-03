package org.diorite.material.blocks.wood;

@SuppressWarnings("MagicNumber")
public enum LogFacing
{
    UP_DOWN(0b0000),
    EAST_WEST(0b0100),
    NORTH_SOUTH(0b100),
    NONE(0b1100);

    private final byte flag;

    LogFacing(final int flag)
    {
        this.flag = (byte) flag;
    }

    public byte getFlag()
    {
        return this.flag;
    }

}
