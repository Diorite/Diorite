package org.diorite.scheduler;

import org.diorite.plugin.DioritePlugin;

/**
 * Represents a worker thread for the scheduler. This gives information about the Thread object for the task, owner of the task and the taskId. <br>
 * Workers are used to execute async tasks.
 */
public interface DioriteWorker
{
    /**
     * Returns the taskId for the task being executed by this worker.
     *
     * @return Task id number.
     */
    int getTaskId();

    /**
     * Returns the Plugin that owns this task.
     *
     * @return The Plugin that owns the task
     */
    DioritePlugin getOwner();

    /**
     * Returns the thread for the worker.
     *
     * @return The Thread object for the worker
     */
    Thread getThread();
}
