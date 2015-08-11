package org.diorite.material.blocks;

/**
 * Representing openable block, like doors/gates.
 */
public interface OpenableMat
{
    /**
     * @return if it's open or closed.
     */
    boolean isOpen();

    /**
     * Get other sub-type based on open state.
     *
     * @param open if it should be an open stete
     *
     * @return sub-type of block
     */
    OpenableMat getOpen(boolean open);
}
