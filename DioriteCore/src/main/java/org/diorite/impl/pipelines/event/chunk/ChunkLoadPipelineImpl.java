package org.diorite.impl.pipelines.event.chunk;

import java.io.IOException;

import org.diorite.impl.world.WorldImpl;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.event.chunk.ChunkLoadEvent;
import org.diorite.event.pipelines.event.chunk.ChunkLoadPipeline;
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
            final int x = evt.getChunkPos().getX();
            final int z = evt.getChunkPos().getZ();
            final WorldImpl impl = (WorldImpl) evt.getWorld();
            final ChunkImpl chunk = impl.getChunkManager().getChunk(x, z);
            try
            {
                evt.setNeedBeGenerated(! impl.getChunkManager().getService().read(chunk));
            } catch (IOException e)
            {
                System.err.println("[ChunkIO] Error while loading chunk (" + x + "," + z + ")");
                e.printStackTrace();
                // an error in chunk reading may have left the chunk in an invalid state
                // (i.e. double initialization errors), so it's forcibly unloaded here
                chunk.unload(false, false);
                evt.cancel();
            }
            evt.setLoadedChunk(chunk);
        });
    }
}
