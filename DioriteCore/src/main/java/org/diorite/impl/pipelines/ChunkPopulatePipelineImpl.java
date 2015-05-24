package org.diorite.impl.pipelines;

import org.diorite.event.EventPriority;
import org.diorite.event.chunk.ChunkPopulateEvent;
import org.diorite.event.pipelines.ChunkPopulatePipeline;
import org.diorite.utils.pipeline.SimpleEventPipeline;

public class ChunkPopulatePipelineImpl extends SimpleEventPipeline<ChunkPopulateEvent> implements ChunkPopulatePipeline
{
    @Override
    public void reset_()
    {
        this.addBefore(EventPriority.NORMAL, "Diorite|Pop", (evt, pipeline) -> {
            if (evt.isCancelled() || evt.getChunk().isPopulated())
            {
                return;
            }
            evt.getChunk().populate();
        });
    }
}
