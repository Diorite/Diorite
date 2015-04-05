package org.diorite.material.blocks;

public enum SlabType
{
    BOTTOM(false, 0x00),
    UPPER(false, 0x08),
    FULL(true, 0x00),
    SMOOTH_FULL(true, 0x08);

    private final boolean fullBlock;
    private final byte    flag;

    SlabType(final boolean fullBlock, final int flag)
    {
        this.fullBlock = fullBlock;
        this.flag = (byte) flag;
    }

    public boolean isFullBlock()
    {
        return this.fullBlock;
    }

    public byte getFlag()
    {
        return this.flag;
    }
}
