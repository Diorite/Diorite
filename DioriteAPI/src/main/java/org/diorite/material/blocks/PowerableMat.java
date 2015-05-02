package org.diorite.material.blocks;

/**
 * Representing powerable block, powerable block can be powered or can give power, or both.
 */
public interface PowerableMat
{
    /**
     * @return if block is powered/is sending power.
     */
    boolean isPowered();

    /**
     * Returns sub-type of block, based on powered state.
     *
     * @param powered if block should be powered.
     *
     * @return sub-type of block
     */
    PowerableMat getPowered(boolean powered);
}
