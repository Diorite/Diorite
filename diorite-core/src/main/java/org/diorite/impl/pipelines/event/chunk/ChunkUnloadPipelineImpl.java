/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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
import org.diorite.event.EventPriority;
import org.diorite.event.chunk.ChunkUnloadEvent;
import org.diorite.event.pipelines.event.chunk.ChunkUnloadPipeline;
import org.diorite.utils.pipeline.SimpleEventPipeline;

public class ChunkUnloadPipelineImpl extends SimpleEventPipeline<ChunkUnloadEvent> implements ChunkUnloadPipeline
{
    @Override
    public void reset_()
    {
        this.addAfter(EventPriority.LOW, "Diorite|Save", (evt, pipeline) -> {
            if (evt.isCancelled() || ! evt.getChunk().isLoaded())
            {
                return;
            }
            final ChunkImpl chunk = (ChunkImpl) evt.getChunk();

            if (evt.isSafe() && chunk.getWorld().getChunkManager().isChunkInUse(chunk.getX(), chunk.getZ()))
            {
                evt.cancel();
                return;
            }
            if (evt.isSafe() && ! chunk.getWorld().getChunkManager().save(chunk))
            {
                evt.cancel();
            }
        });
        this.addAfter(EventPriority.BELOW_NORMAL, "Diorite|Unload", (evt, pipeline) -> {
            if (evt.isCancelled() || ! evt.getChunk().isLoaded())
            {
                return;
            }
            final ChunkImpl chunk = (ChunkImpl) evt.getChunk();
            chunk.setChunkParts(null);
            chunk.setBiomes(null);
            chunk.getTileEntities().clear();
        });

    }
}
