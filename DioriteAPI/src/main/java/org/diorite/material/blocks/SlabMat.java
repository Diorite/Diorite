package org.diorite.material.blocks;

/**
 * Representing slab-type block.
 */
public interface SlabMat
{
    /**
     * @return {@link SlabTypeMat} type of slab.
     */
    SlabTypeMat getSlabType();

    /**
     * Returns sub-type of block, based on {@link SlabTypeMat}.
     *
     * @param type type of slab..
     *
     * @return sub-type of block
     */
    SlabMat getSlab(SlabTypeMat type);

    /**
     * @return {@link SlabTypeMat.UPPER} type of slab.
     *
     * @see SlabMat#getSlab(SlabTypeMat)
     */
    default SlabMat getUpperPart()
    {
        return this.getSlab(SlabTypeMat.UPPER);
    }

    /**
     * @return {@link SlabTypeMat.BOTTOM} type of slab.
     *
     * @see SlabMat#getSlab(SlabTypeMat)
     */
    default SlabMat getBottomPart()
    {
        return this.getSlab(SlabTypeMat.BOTTOM);
    }

    /**
     * @return {@link SlabTypeMat.FULL} type of slab.
     *
     * @see SlabMat#getSlab(SlabTypeMat)
     */
    default SlabMat getFullSlab()
    {
        return this.getSlab(SlabTypeMat.FULL);
    }

    /**
     * @return {@link SlabTypeMat.SMOOTH_FULL} type of slab.
     *
     * @see SlabMat#getSlab(SlabTypeMat)
     */
    default SlabMat getFullSmoothSlab()
    {
        return this.getSlab(SlabTypeMat.SMOOTH_FULL);
    }

    /**
     * @return true if this is one of double-slab block type.
     */
    default boolean isFullBlock()
    {
        return this.getSlabType().isFullBlock();
    }
}
