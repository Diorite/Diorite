package org.diorite.impl.world.io.serial.anvil;

import java.io.File;
import java.util.PriorityQueue;
import java.util.function.IntConsumer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.io.requests.Request;
import org.diorite.impl.world.io.serial.SerialChunkIOService;

public class AnvilIOService extends Thread implements SerialChunkIOService
{

    private final PriorityQueue<Request<?>> queue = new PriorityQueue<>(20);
    private final AnvilIO io;

    public AnvilIOService(final File basePath, final String extension, final int maxCacheSize, final int regionSize)
    {
        this.io = new AnvilIO(basePath, extension, maxCacheSize, regionSize);
    }

    public AnvilIOService(final File basePath, final String extension, final int maxCacheSize)
    {
        this.io = new AnvilIO(basePath, extension, maxCacheSize, AnvilIO.DEFAULT_REGION_SIZE);
    }

    @Override
    public <OUT, T extends Request<OUT>> T queue(final T request)
    {
        this.queue.add(request);
        synchronized (this.queue)
        {
            this.queue.notifyAll();
        }
        return request;
    }

    @Override
    public void await(final IntConsumer rest, final int timer)
    {
        try
        {
            //noinspection StatementWithEmptyBody
            while (this.await_(rest, timer))
            {
            }
        } catch (final InterruptedException ignored)
        {
            this.await(rest);
        }
    }

    private boolean await_(final IntConsumer rest, final int timer) throws InterruptedException
    {
        if (this.queue.isEmpty())
        {
            if (rest != null)
            {
                rest.accept(0);
            }
            return false;
        }
        this.queue.wait(timer);
        if (rest != null)
        {
            rest.accept(this.queue.size());
        }
        return true;
    }

    @Override
    public void run()
    {
        final Request<?> r = this.queue.poll();
        if (r != null)
        {
            r.run(this.io);
            return;
        }
        try
        {
            this.queue.wait();
        } catch (final InterruptedException ignored)
        {
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("queue", this.queue).append("io", this.io).toString();
    }
}
