package org.diorite.scheduler;

import org.diorite.Diorite;
import org.diorite.plugin.PluginMainClass;

/**
 * Represents a task being executed by the scheduler.
 */
public interface DioriteTask
{
    /**
     * @return Unique Task id number.
     */
    int getTaskId();

    /**
     * @return The Plugin that owns the task.
     */
    PluginMainClass getOwner();

    /**
     * @return true if the task isn't run by any of main threads.
     */
    boolean isAsync();

    /**
     * Check if this task is synchronized with given object. <br>
     * Task is synchronized with given object if it is running in this same thread as object is ticking.
     *
     * @param obj object to check.
     *
     * @return true if task is synchronized with given object.
     */
    boolean isSynchronizedTo(Synchronizable obj);

    /**
     * Check if this task is synchronized with given object type. <br>
     * Task is synchronized with given object type if it is synchronized with object of that type.
     *
     * @param obj object to check.
     *
     * @return true if task is synchronized with given object type.
     */
    boolean isSynchronizedTo(Class<? extends Synchronizable> obj);

    /**
     * Check if task is synchronized with {@link org.diorite.Server}.
     *
     * @return true if task is synchronized with {@link org.diorite.Server}.
     *
     * @see #isSynchronizedTo(Synchronizable)
     */
    default boolean isSynchronizdWithServer()
    {
        return this.isSynchronizedTo(Diorite.getServer());
    }

    Synchronizable getSynchronizable();

    String getTaskName();

    /**
     * Will attempt to cancel this task.
     */
    void cancel();
}
