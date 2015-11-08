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

package org.diorite.impl.world.io.anvil.serial;

import java.io.File;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.IntConsumer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.WorldImpl;
import org.diorite.impl.world.chunk.ChunkManagerImpl.ChunkLock;
import org.diorite.impl.world.io.SerialChunkIOService;
import org.diorite.impl.world.io.anvil.AnvilIO;
import org.diorite.impl.world.io.requests.ChunkSaveRequest;
import org.diorite.impl.world.io.requests.Request;

public class AnvilSerialIOService extends Thread implements SerialChunkIOService
{
    private final PriorityBlockingQueue<Request<?>> queue = new PriorityBlockingQueue<>(20);
    private final AnvilIO   io;
    private       ChunkLock lock;

    public AnvilSerialIOService(final File basePath, final String worldName, final String extension, final int maxCacheSize)
    {
        super("ChunkIO-" + worldName);
        this.setDaemon(true);
        this.io = new AnvilSerialIO(basePath, extension, maxCacheSize);
    }

    public AnvilSerialIOService(final File basePath, final String worldName)
    {
        super("ChunkIO-" + worldName);
        this.setDaemon(true);
        this.io = new AnvilSerialIO(basePath);
    }

    @Override
    public void start(final WorldImpl world)
    {
        this.lock = world.createLock("ChunkIO");
        this.start();
    }

    @Override
    public <OUT, T extends Request<OUT>> T queue(final T request)
    {
        if (request instanceof ChunkSaveRequest)
        {
            final ChunkSaveRequest req = (ChunkSaveRequest) request;
            final long key = req.getData().getPos().asLong();
            this.lock.acquire(key);
            req.addOnEnd(r -> this.lock.release(key));
        }
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
