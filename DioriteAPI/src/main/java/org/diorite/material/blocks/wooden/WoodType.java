package org.diorite.material.blocks.wooden;

public enum WoodType
{
    OAK(0b0000, 0x00, false),
    SPRUCE(0b0001, 0x01, false),
    BIRCH(0b0010, 0x02, false),
    JUNGLE(0b0011, 0x03, false),
    ACACIA(0b0000, 0x04, true),
    DARK_OAK(0b0001, 0x05, true);
    private final boolean secondLogID;
    private final byte    logFlag;
    private final byte    planksMeta;

    WoodType(final int logFlag, final int planksMeta, final boolean secondLogID)
    {
        this.secondLogID = secondLogID;
        this.planksMeta = (byte) planksMeta;
        this.logFlag = (byte) logFlag;
    }

    public byte getLogFlag()
    {
        return this.logFlag;
    }

    public byte getPlanksMeta()
    {
        return this.planksMeta;
    }

    public boolean isSecondLogID()
    {
        return this.secondLogID;
    }
}
