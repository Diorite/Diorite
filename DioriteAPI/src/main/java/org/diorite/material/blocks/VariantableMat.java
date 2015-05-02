package org.diorite.material.blocks;

/**
 * Representing "variantable" block, it's block that have few common variants of it.
 *
 * @see VariantMat
 */
public interface VariantableMat
{
    /**
     * @return variant of block.
     */
    VariantMat getVariant();

    /**
     * Returns sub-type of block, based on {@link VariantMat}.
     * If block don't supprot given variant, {@link VariantMat.CLASSIC} one will be returned.
     *
     * @param variant variant of block.
     *
     * @return sub-type of block
     */
    VariantableMat getVariant(VariantMat variant);
}
