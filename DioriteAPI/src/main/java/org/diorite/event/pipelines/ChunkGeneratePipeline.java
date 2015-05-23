package org.diorite.event.pipelines;

import org.diorite.event.chunk.ChunkGenerateEvent;

/**
 * {@link EventPipeline} using {@link ChunkGenerateEvent} as type.
 * <p>
 * Default handlers are: <br>
 * <b>Diorite|Gen</b> {@literal ->} generate chunk and set it to event. <br>
 * {@link org.diorite.event.EventPriority#LOWEST} <br>
 * {@link org.diorite.event.EventPriority#LOWER} <br>
 * {@link org.diorite.event.EventPriority#LOW} <br>
 * {@link org.diorite.event.EventPriority#BELOW_NORMAL} <br>
 * {@link org.diorite.event.EventPriority#NORMAL} <br>
 * {@link org.diorite.event.EventPriority#ABOVE_NORMAL} <br>
 * {@link org.diorite.event.EventPriority#HIGH} <br>
 * {@link org.diorite.event.EventPriority#HIGHER} <br>
 * {@link org.diorite.event.EventPriority#HIGHEST} <br>
 */
public interface ChunkGeneratePipeline extends EventPipeline<ChunkGenerateEvent>
{
}
