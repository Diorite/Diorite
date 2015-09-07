package org.diorite.impl.world.io.anvil.parallel;

import java.io.File;
import java.util.function.IntConsumer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.WorldImpl;
import org.diorite.impl.world.io.ParallelChunkIOService;
import org.diorite.impl.world.io.requests.Request;

public class AnvilParallelIOService implements ParallelChunkIOService
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
    public void start(final WorldImpl world)
    {

    }

    @Override
    public <OUT, T extends Request<OUT>> T queue(final T request)
    {
        return null;
    }

    @Override
    public void await(final IntConsumer rest, final int timer)
    {
        // TODO
    }

    @Override
    public File getWorldDataFolder()
    {
        return null;
    }

    @Override
    public void close(final IntConsumer rest)
    {

    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("maxThreads", this.maxThreads).toString();
    }
}
