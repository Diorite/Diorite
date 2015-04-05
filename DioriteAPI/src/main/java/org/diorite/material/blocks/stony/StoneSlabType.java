package org.diorite.material.blocks.stony;

public enum StoneSlabType
{
    STONE(0x00),
    SANDSTONE(0x01),
    WOODEN(0x02),
    COBBLESTONE(0x03),
    BRICKS(0x04),
    STONE_BRICKS(0x05),
    NETHER_BRICKS(0x06),
    QUARTZ(0x07);

    private final byte flag;

    StoneSlabType(final int flag)
    {
        this.flag = (byte) flag;
    }

    public byte getFlag()
    {
        return this.flag;
    }
}
