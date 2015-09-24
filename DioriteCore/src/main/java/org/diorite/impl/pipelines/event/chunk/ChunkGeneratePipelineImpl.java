package org.diorite.impl.pipelines.event.chunk;

import org.diorite.impl.world.WorldImpl;
import org.diorite.event.chunk.ChunkGenerateEvent;
import org.diorite.event.pipelines.event.chunk.ChunkGeneratePipeline;
import org.diorite.utils.pipeline.SimpleEventPipeline;

public class ChunkGeneratePipelineImpl extends SimpleEventPipeline<ChunkGenerateEvent> implements ChunkGeneratePipeline
{
    @Override
    public void reset_()
    {
        this.addFirst("Diorite|Gen", (evt, pipeline) -> {
            if (evt.isCancelled())
            {
                return;
            }
            try
            {
                ((WorldImpl) evt.getWorld()).getChunkManager().generateChunk(evt.getChunk(), evt.getChunkX(), evt.getChunkZ());
            } catch (Throwable e)
            {
                System.err.println("[ChunkIO] Error while generating chunk (" + evt.getChunkX() + "," + evt.getChunkZ() + ")");
                e.printStackTrace();
                evt.cancel();
            }
//            final ChunkBuilder chunkBuilder = evt.getWorld().getGenerator().generate(new ChunkBuilderImpl(), evt.getChunkPos());
        });
    }
}
