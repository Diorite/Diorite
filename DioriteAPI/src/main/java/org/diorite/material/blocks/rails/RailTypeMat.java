package org.diorite.material.blocks.rails;

public enum RailTypeMat
{
    FLAT_NORTH_SOUTH(0x00),
    FLAT_WEST_EAST(0x01),
    ASCENDING_EAST(0x02),
    ASCENDING_WEST(0x03),
    ASCENDING_NORTH(0x04),
    ASCENDING_SOUTH(0x05),
    CURVED_SOUTH_EAST(0x06),
    CURVED_SOUTH_WEST(0x07),
    CURVED_NORTH_WEST(0x08),
    CURVED_NORTH_EAST(0x09);

    private final byte flag;

    RailTypeMat(final int flag)
    {
        this.flag = (byte) flag;
    }

    public byte getFlag()
    {
        return this.flag;
    }
}
