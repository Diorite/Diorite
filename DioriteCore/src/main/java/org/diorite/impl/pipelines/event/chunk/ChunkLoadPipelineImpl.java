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

import org.diorite.impl.world.WorldImpl;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.io.ChunkIOService;
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
                evt.setNeedBeGenerated(impl.getChunkManager().getService().queueChunkLoadAndGet(chunk, ChunkIOService.INSTANT_PRIORITY) == null);
            } catch (Exception e)
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
