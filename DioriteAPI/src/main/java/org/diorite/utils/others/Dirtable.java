package org.diorite.utils.others;

/**
 * Represent object that can be "dirty" - so it need some kind of update.
 */
public interface Dirtable
{
    /**
     * Check if item is dirty.
     *
     * @return true if item is dirty.
     */
    boolean isDirty();

    /**
     * Change dirty state, and return previous one.
     *
     * @param dirty new dirty state
     *
     * @return old dirty state.
     */
    boolean setDirty(final boolean dirty);

    /**
     * Change dirty state to true, and return previous one.
     *
     * @return old dirty state.
     */
    default boolean setDirty()
    {
        return this.setDirty(true);
    }

    /**
     * Change dirty state to false, and return previous one.
     *
     * @return old dirty state.
     */
    default boolean setClean()
    {
        return this.setDirty(false);
    }
}
