package org.diorite.event.pipelines;

import org.diorite.event.chunk.ChunkPopulateEvent;
import org.diorite.world.chunk.Chunk;

/**
 * {@link EventPipeline} using {@link ChunkPopulateEvent} as type.
 * <p>
 * Default handlers are: <br>
 * {@link org.diorite.event.EventPriority#LOWEST} <br>
 * {@link org.diorite.event.EventPriority#LOWER} <br>
 * {@link org.diorite.event.EventPriority#LOW} <br>
 * {@link org.diorite.event.EventPriority#BELOW_NORMAL} <br>
 * <b>Diorite|Pop</b> {@literal ->} default chunk populate {@link Chunk#populate()}. <br>
 * {@link org.diorite.event.EventPriority#NORMAL} <br>
 * {@link org.diorite.event.EventPriority#ABOVE_NORMAL} <br>
 * {@link org.diorite.event.EventPriority#HIGH} <br>
 * {@link org.diorite.event.EventPriority#HIGHER} <br>
 * {@link org.diorite.event.EventPriority#HIGHEST} <br>
 */
public interface ChunkPopulatePipeline extends EventPipeline<ChunkPopulateEvent>
{
}
