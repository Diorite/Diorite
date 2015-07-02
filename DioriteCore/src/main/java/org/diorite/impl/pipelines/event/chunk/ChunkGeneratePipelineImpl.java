package org.diorite.impl.pipelines.event.chunk;

import org.diorite.impl.world.WorldImpl;
import org.diorite.event.chunk.ChunkGenerateEvent;
import org.diorite.event.pipelines.event.chunk.ChunkGeneratePipeline;
import org.diorite.utils.pipeline.SimpleEventPipeline;
import org.diorite.world.chunk.ChunkPos;

public class ChunkGeneratePipelineImpl extends SimpleEventPipeline<ChunkGenerateEvent> implements ChunkGeneratePipeline
{
//    static Set<ChunkPos> gens = new ConcurrentSet<>();
//    static Set<ChunkPos> pops = new ConcurrentSet<>();

    public static void addGen(final ChunkPos pos)
    {
//        if (Main.isEnabledDebug() && ! gens.add(pos))
//        {
//            Main.debug("[CHUNK_ERROR] Chunk generated second time: " + pos);
//        }
    }

    public static void addPops(final ChunkPos pos)
    {
//        if (Main.isEnabledDebug() && ! pops.add(pos))
//        {
//            Main.debug("[CHUNK_ERROR] Chunk populated second time: " + pos);
//        }
    }

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
