package org.diorite.impl.world.io;

import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.io.requests.ChunkDeleteRequest;
import org.diorite.impl.world.io.requests.ChunkLoadRequest;
import org.diorite.impl.world.io.requests.ChunkSaveRequest;
import org.diorite.impl.world.io.requests.Request;

public interface ChunkIOService
{
    default ChunkLoadRequest queueChunkLoad(final int chunkX, final int chunkZ, final int priority)
    {
        return this.queue(new ChunkLoadRequest(priority, chunkX, chunkZ));
    }

    default ChunkLoadRequest queueChunkLoadAndAwait(final int chunkX, final int chunkZ, final int priority)
    {
        return this.queueAndAwait(new ChunkLoadRequest(priority, chunkX, chunkZ));
    }

    default ChunkImpl queueChunkLoadAndGet(final int chunkX, final int chunkZ, final int priority)
    {
        return this.queueAndGet(new ChunkLoadRequest(priority, chunkX, chunkZ));
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

    ChunkIO getImplementation();
}
