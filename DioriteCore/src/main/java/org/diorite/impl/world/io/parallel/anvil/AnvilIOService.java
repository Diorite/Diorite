package org.diorite.impl.world.io.parallel.anvil;

import org.diorite.impl.world.io.ChunkIO;
import org.diorite.impl.world.io.parallel.ParallelChunkIOService;
import org.diorite.impl.world.io.requests.Request;

public class AnvilIOService implements ParallelChunkIOService
{
    private int maxThreads = 3;

    @Override
    public int getMaxThreads()
    {
        return this.maxThreads;
    }

    @Override
    public void setMaxThreads(final int threads)
    {
        this.maxThreads = threads;
    }

    @Override
    public <OUT, T extends Request<OUT>> T queue(final T request)
    {
        return null;
    }

    @Override
    public ChunkIO getImplementation()
    {
        return null;
    }
}
