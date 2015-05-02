package org.diorite.material.blocks;

/**
 * enum with some common types (variants) of blocks.
 * Block supproting variant may (and mostly it will) supprot only few of them.
 * If block don't supprot given variant, {@link CLASSIC} should be ussed instead.
 */
public enum VariantMat
{
    /**
     * Default variant.
     */
    CLASSIC,
    /**
     * Smooth, polished, variant.
     */
    SMOOTH,
    /**
     * Chiseled variant.
     */
    CHISELED,
    /**
     * Mossy variant.
     */
    MOSSY,
    /**
     * Cracked variant.
     */
    CRACKED,
    /**
     * Red variant, used only by {@link org.diorite.material.blocks.loose.SandMat} at this moment
     */
    RED,
    /**
     * Pillar vertical (up-down), currently used only by {@link org.diorite.material.blocks.stony.oreblocks.QuartzBlockMat}
     */
    PILLAR_VERTICAL,
    /**
     * Pillar north-south, currently used only by {@link org.diorite.material.blocks.stony.oreblocks.QuartzBlockMat}
     */
    PILLAR_NORTH_SOUTH,
    /**
     * Pillar east-west, currently used only by {@link org.diorite.material.blocks.stony.oreblocks.QuartzBlockMat}
     */
    PILLAR_EAST_WEST,
    /**
     * Coarse variant, curranlty used only by {@link org.diorite.material.blocks.earth.DirtMat}
     */
    COARSE,
    /**
     * Podzdol variant, curranlty used only by {@link org.diorite.material.blocks.earth.DirtMat}
     */
    PODZOL;
}
