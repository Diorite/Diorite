package org.diorite.material.blocks;

public interface Slab
{
    SlabType getSlabType();

    Slab getSlab(SlabType type);

    default Slab getUpperPart()
    {
        return this.getSlab(SlabType.UPPER);
    }

    default Slab getBottomPart()
    {
        return this.getSlab(SlabType.BOTTOM);
    }

    default Slab getFullSlab()
    {
        return this.getSlab(SlabType.FULL);
    }

    default Slab getFullSmoothSlab()
    {
        return this.getSlab(SlabType.SMOOTH_FULL);
    }

    default boolean isFullBlock()
    {
        return this.getSlabType().isFullBlock();
    }
}
