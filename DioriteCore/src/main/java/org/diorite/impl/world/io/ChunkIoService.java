package org.diorite.impl.world.io;

import java.io.File;
import java.util.function.IntConsumer;

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
