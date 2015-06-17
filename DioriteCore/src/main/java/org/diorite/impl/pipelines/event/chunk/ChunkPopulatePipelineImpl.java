package org.diorite.impl.pipelines.event.chunk;

import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.chunk.ChunkManagerImpl;
import org.diorite.event.EventPriority;
import org.diorite.event.chunk.ChunkPopulateEvent;
import org.diorite.event.pipelines.event.chunk.ChunkPopulatePipeline;
import org.diorite.utils.pipeline.SimpleEventPipeline;

public class ChunkPopulatePipelineImpl extends SimpleEventPipeline<ChunkPopulateEvent> implements ChunkPopulatePipeline
{
    @Override
    public void reset_()
    {
        this.addBefore(EventPriority.LOWER, "Diorite|Pop", (evt, pipeline) -> {
            if (evt.isCancelled() || evt.getChunk().isPopulated())
            {
                return;
            }
            final int x = evt.getChunkX();
            final int z = evt.getChunkZ();
            final ChunkManagerImpl chunks = (ChunkManagerImpl) evt.getWorld().getChunkManager();
            // cancel out if the 3x3 around it isn't available
            for (int x2 = x - 1; x2 <= (x + 1); ++ x2)
            {
                for (int z2 = z - 1; z2 <= (z + 1); ++ z2)
                {
                    if (! chunks.getChunk(x2, z2).isLoaded() && (! evt.isForce() || ! chunks.loadChunk(x2, z2, true)))
                    {
                        return;
                    }
                }
            }
            final ChunkImpl chunk = (ChunkImpl) evt.getChunk();

            // it might have loaded since before, so check again that it's not already populated
            if (chunk.isPopulated())
            {
                return;
            }
            chunk.populate();

//            Random random = new Random(evt.getWorld().getSeed());
//            long xRand = ((random.nextLong() / 2) << 1) + 1;
//            long zRand = ((random.nextLong() / 2) << 1) + 1;
//            random.setSeed((((long) x * xRand) + ((long) z * zRand)) ^ (evt.getWorld().getSeed());

//            for (BlockPopulator p : this.world.getPopulators())
//            {
//                p.populate(this.world, random, chunk);
//            }
        });
    }
}
