package org.diorite.impl.pipelines;

import org.diorite.event.pipelines.ChunkPreLoadPipeline;
import org.diorite.event.world.ChunkPreLoadEvent;
import org.diorite.utils.pipeline.SimpleEventPipeline;

public class ChunkPreLoadPipelineImpl extends SimpleEventPipeline<ChunkPreLoadEvent> implements ChunkPreLoadPipeline
{
    @Override
    public void reset_()
    { // TODO: finish
        this.addFirst("Diorite|Start", (evt, pipeline) -> {
        });
    }
}
