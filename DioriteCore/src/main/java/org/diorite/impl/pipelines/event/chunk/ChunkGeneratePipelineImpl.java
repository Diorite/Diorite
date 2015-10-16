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
