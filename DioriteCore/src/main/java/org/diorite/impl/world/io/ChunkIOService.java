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

package org.diorite.impl.world.io;

import java.io.File;
import java.util.function.IntConsumer;

import org.diorite.impl.world.WorldImpl;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.io.requests.ChunkDeleteRequest;
import org.diorite.impl.world.io.requests.ChunkLoadRequest;
import org.diorite.impl.world.io.requests.ChunkSaveRequest;
import org.diorite.impl.world.io.requests.Request;

public interface ChunkIOService
{
    int DEFAULT_REST_TIMER = 2000;
    int LOW_PRIORITY       = 1_000_000;
    int MEDIUM_PRIORITY    = 5_000_000;
    int HIGH_PRIORITY      = 10_000_000;
    int INSTANT_PRIORITY   = Integer.MAX_VALUE;

    default ChunkLoadRequest queueChunkLoad(final ChunkImpl chunk, final int priority)
    {
        return this.queue(new ChunkLoadRequest(priority, chunk, chunk.getX(), chunk.getZ()));
    }

    default ChunkLoadRequest queueChunkLoadAndAwait(final ChunkImpl chunk, final int priority)
    {
        return this.queueAndAwait(new ChunkLoadRequest(priority, chunk, chunk.getX(), chunk.getZ()));
    }

    default ChunkImpl queueChunkLoadAndGet(final ChunkImpl chunk, final int priority)
    {
        return this.queueAndGet(new ChunkLoadRequest(priority, chunk, chunk.getX(), chunk.getZ()));
    }

    default ChunkSaveRequest queueChunkSave(final ChunkImpl chunk, final int priority)
    {
        return this.queue(new ChunkSaveRequest(priority, chunk));
    }

    default ChunkSaveRequest queueChunkSaveAndAwait(final ChunkImpl chunk, final int priority)
    {
        return this.queueAndAwait(new ChunkSaveRequest(priority, chunk));
    }

    default ChunkDeleteRequest queueChunkDelete(final int chunkX, final int chunkZ, final int priority)
    {
        return this.queue(new ChunkDeleteRequest(priority, chunkX, chunkZ));
    }

    default ChunkDeleteRequest queueChunkDeleteAndAwait(final int chunkX, final int chunkZ, final int priority)
    {
        return this.queueAndAwait(new ChunkDeleteRequest(priority, chunkX, chunkZ));
    }

    default ChunkDeleteRequest queueChunkDelete(final ChunkImpl chunk, final int priority)
    {
        return this.queue(new ChunkDeleteRequest(priority, chunk.getX(), chunk.getZ()));
    }

    default ChunkDeleteRequest queueChunkDeleteAndAwait(final ChunkImpl chunk, final int priority)
    {
        return this.queueAndAwait(new ChunkDeleteRequest(priority, chunk.getX(), chunk.getZ()));
    }

    default boolean queueChunkDeleteAndGet(final ChunkImpl chunk, final int priority)
    {
        return this.queueAndGet(new ChunkDeleteRequest(priority, chunk.getX(), chunk.getZ()));
    }

    default <OUT, T extends Request<OUT>> T queueAndAwait(T request)
    {
        request = this.queue(request);
        request.await();
        return request;
    }

    default <OUT, T extends Request<OUT>> OUT queueAndGet(T request)
    {
        request = this.queue(request);
        return request.await();
    }

    void start(WorldImpl world);

    <OUT, T extends Request<OUT>> T queue(T request);

    default void await()
    {
        this.await(null);
    }

    default void await(final IntConsumer rest)
    {
        this.await(rest, DEFAULT_REST_TIMER);
    }

    /**
     * Await for all requests.
     *
     * @param rest  invoked every few seconds with current amount of left requests.
     * @param timer delay between rest consumer runs.
     */
    void await(IntConsumer rest, int timer);

    File getWorldDataFolder();

    void close(final IntConsumer rest);
}
