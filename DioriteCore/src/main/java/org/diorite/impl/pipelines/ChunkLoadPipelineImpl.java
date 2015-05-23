package org.diorite.impl.pipelines;

import org.diorite.impl.world.WorldImpl;
import org.diorite.event.chunk.ChunkLoadEvent;
import org.diorite.event.pipelines.ChunkLoadPipeline;
import org.diorite.utils.pipeline.SimpleEventPipeline;

public class ChunkLoadPipelineImpl extends SimpleEventPipeline<ChunkLoadEvent> implements ChunkLoadPipeline
{
    @Override
    public void reset_()
    {
        this.addFirst("Diorite|Load", (evt, pipeline) -> {
            if (evt.isCancelled())
            {
                return;
            }
            WorldImpl impl = (WorldImpl) evt.getWorld();
            evt.setLoadedChunk(impl.getWorldFile().loadChunk(evt.getChunkPos()));
        });
    }
}
