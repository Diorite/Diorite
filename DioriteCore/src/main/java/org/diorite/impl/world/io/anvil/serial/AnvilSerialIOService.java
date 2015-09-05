package org.diorite.impl.world.io.anvil.serial;

import java.io.File;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.IntConsumer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.io.SerialChunkIOService;
import org.diorite.impl.world.io.anvil.AnvilIO;
import org.diorite.impl.world.io.requests.Request;

public class AnvilSerialIOService extends Thread implements SerialChunkIOService
{
    private final PriorityBlockingQueue<Request<?>> queue = new PriorityBlockingQueue<>(20);
    private final AnvilIO io;

    public AnvilSerialIOService(final File basePath, final String extension, final int maxCacheSize)
    {
        super("AnvilSerialIO");
        this.setDaemon(true);
        this.io = new AnvilSerialIO(basePath, extension, maxCacheSize);
        this.start();
    }

    public AnvilSerialIOService(final File basePath)
    {
        super("AnvilSerialIO");
        this.setDaemon(true);
        this.io = new AnvilSerialIO(basePath);
        this.start();
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

    @Override
    public File getWorldDataFolder()
    {
        return this.io.getWorldDataFolder().getAbsoluteFile().getParentFile();
    }

    @Override
    public void close(final IntConsumer rest)
    {
        this.await(rest);
        this.io.close();
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
        synchronized (this.queue)
        {
            this.queue.wait(timer);
        }
        if (rest != null)
        {
            rest.accept(this.queue.size());
        }
        return true;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run()
    {
        while (true)
        {
            final Request<?> r = this.queue.poll();
            if (r != null)
            {
                r.run(this.io);
                continue;
            }
            try
            {
                synchronized (this.queue)
                {
                    this.queue.wait();
                }
            } catch (final InterruptedException ignored)
            {
            }
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("queue", this.queue).append("io", this.io).toString();
    }
}
