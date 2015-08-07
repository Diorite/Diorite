package org.diorite.scheduler;

import org.diorite.Core;
import org.diorite.world.Block;

/**
 * Objects implementing this interface can be used when creating task, to be always synchronized with this object. <br>
 * So if entity teleport to other world, handled by other thread, then all task synchronized to this entity will also move to other thread. <br>
 * <p>
 * Implementing this interface will not make object useful for scheduler, special handler must be created on core side that will handle all opbject of this type. <br>
 * Object supported by default: <br>
 * - {@link org.diorite.entity.Entity} <br>
 * - {@link org.diorite.world.chunk.Chunk} <br>
 * - {@link Core} (default value, task will be executed before world ticking) <br>
 * Also {@link org.diorite.world.Block} is partially supproted by scheduler, but {@link Block#getChunk()} is used.
 */
public interface Synchronizable
{
    /**
     * Method need return last used tick thrad.
     *
     * @return last used tick thread.
     */
    Thread getLastTickThread();

    /**
     * Method should check if task sync to this object should
     * be unregistred, like after player exit.
     *
     * @return true if task don't need to be unregistred.
     */
    boolean isValidSynchronizable();
}
