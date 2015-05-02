package org.diorite.material.blocks.stony;

/**
 * enum will all stone type slabs
 */
public enum StoneSlabTypeMat
{
    STONE(0x00, false),
    SANDSTONE(0x01, false),
    WOODEN(0x02, false),
    COBBLESTONE(0x03, false),
    BRICKS(0x04, false),
    STONE_BRICKS(0x05, false),
    NETHER_BRICKS(0x06, false),
    QUARTZ(0x07, false),
    RED_SANDSTONE(0x00, true);

    private final byte    flag;
    private final boolean isSecondStoneSlabID;

    StoneSlabTypeMat(final int flag, final boolean isFirstId)
    {
        this.isSecondStoneSlabID = isFirstId;
        this.flag = (byte) flag;
    }

    /**
     * @return sub-id flag for that type.
     */
    public byte getFlag()
    {
        return this.flag;
    }

    /**
     * @return is this type is on second slab it.
     */
    public boolean isSecondStoneSlabID()
    {
        return this.isSecondStoneSlabID;
    }
}
