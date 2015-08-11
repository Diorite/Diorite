package org.diorite.impl.pipelines.event.chunk;

import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.event.EventPriority;
import org.diorite.event.chunk.ChunkUnloadEvent;
import org.diorite.event.pipelines.event.chunk.ChunkUnloadPipeline;
import org.diorite.utils.pipeline.SimpleEventPipeline;

public class ChunkUnloadPipelineImpl extends SimpleEventPipeline<ChunkUnloadEvent> implements ChunkUnloadPipeline
{
    @Override
    public void reset_()
    {
        this.addAfter(EventPriority.LOW, "Diorite|Save", (evt, pipeline) -> {
            if (evt.isCancelled() || ! evt.getChunk().isLoaded())
            {
                return;
            }
            final ChunkImpl chunk = (ChunkImpl) evt.getChunk();

            if (evt.isSafe() && chunk.getWorld().getChunkManager().isChunkInUse(chunk.getX(), chunk.getZ()))
            {
                evt.cancel();
                return;
            }
            if (evt.isSafe() && ! chunk.getWorld().getChunkManager().save(chunk))
            {
                evt.cancel();
            }
        });
        this.addAfter(EventPriority.BELOW_NORMAL, "Diorite|Unload", (evt, pipeline) -> {
            if (evt.isCancelled() || ! evt.getChunk().isLoaded())
            {
                return;
            }
            final ChunkImpl chunk = (ChunkImpl) evt.getChunk();
            chunk.setChunkParts(null);
            chunk.setBiomes(null);
            chunk.getTileEntities().clear();
        });

    }
}
