package org.diorite.material.blocks;

/**
 * enum with all slab types.
 */
public enum SlabTypeMat
{
    /**
     * Single slab on bottom half of block.
     */
    BOTTOM(false, 0x00),
    /**
     * Single slab on upper half of block.
     */
    UPPER(false, 0x08),
    /**
     * Double slab, full block.
     */
    FULL(true, 0x00),
    /**
     * Double slab, full block, only few slab blocks have this type.
     */
    SMOOTH_FULL(true, 0x08);

    private final boolean fullBlock;
    private final byte    flag;

    SlabTypeMat(final boolean fullBlock, final int flag)
    {
        this.fullBlock = fullBlock;
        this.flag = (byte) flag;
    }

    /**
     * @return if this is one of full block types. {@link #FULL} {@link #SMOOTH_FULL}
     */
    public boolean isFullBlock()
    {
        return this.fullBlock;
    }

    /**
     * @return sub-id flag used by all slab blocks.
     */
    public byte getFlag()
    {
        return this.flag;
    }
}
