package org.diorite.event;

/**
 * Events implementing this interface can be cancelled.
 */
public interface Cancellable
{
    /**
     * @return true if event is cancelled.
     */
    boolean isCancelled();

    /**
     * Set if event should be cancelled.
     *
     * @param bool new cancelled state.
     */
    void setCancelled(boolean bool);

    /**
     * Cancel event using {@link #setCancelled(boolean)} and return true if
     * event wasn't already cancelled.
     *
     * @return true if event wasn't already cancelled.
     *
     * @see #setCancelled(boolean)
     */
    default boolean cancel()
    {
        if (this.isCancelled())
        {
            return false;
        }
        this.setCancelled(true);
        return true;
    }
}
