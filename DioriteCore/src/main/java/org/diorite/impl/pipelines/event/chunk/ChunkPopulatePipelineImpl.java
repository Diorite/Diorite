/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
