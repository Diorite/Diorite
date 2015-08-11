package org.diorite.event.pipelines.event.chunk;

import org.diorite.event.chunk.ChunkLoadEvent;
import org.diorite.event.chunk.ChunkUnloadEvent;
import org.diorite.event.pipelines.EventPipeline;

/**
 * {@link EventPipeline} using {@link ChunkLoadEvent} as type.
 * <p>
 * Default handlers are: <br>
 * {@link org.diorite.event.EventPriority#LOWEST} <br>
 * {@link org.diorite.event.EventPriority#LOWER} <br>
 * {@link org.diorite.event.EventPriority#LOW} <br>
 * <b>Diorite|Save</b> {@literal ->} save chunk <br>
 * {@link org.diorite.event.EventPriority#BELOW_NORMAL} <br>
 * <b>Diorite|Unload</b> {@literal ->} reset chunk data <br>
 * {@link org.diorite.event.EventPriority#NORMAL} <br>
 * {@link org.diorite.event.EventPriority#ABOVE_NORMAL} <br>
 * {@link org.diorite.event.EventPriority#HIGH} <br>
 * {@link org.diorite.event.EventPriority#HIGHER} <br>
 * {@link org.diorite.event.EventPriority#HIGHEST} <br>
 */
public interface ChunkUnloadPipeline extends EventPipeline<ChunkUnloadEvent>
{
}
