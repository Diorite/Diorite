package org.diorite.impl.multithreading.map;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.ServerImpl;
import org.diorite.impl.multithreading.ChunkUnloadAction;
import org.diorite.world.chunk.Chunk;

public class ChunkUnloaderThread extends Thread
{
    private static final Queue<ChunkUnloadAction> actions = new ConcurrentLinkedQueue<>();
    private final ServerImpl server;

    public ChunkUnloaderThread(final ServerImpl server)
    {
        super("{Diorite|ChkUnl}");
        this.server = server;
        this.setDaemon(true);
        this.setPriority(Thread.MIN_PRIORITY);
    }

    public static int getActionsSize()
    {
        return actions.size();
    }

    public static void add(final Chunk action)
    {
        add(new ChunkUnloadAction(action));
    }

    public static void add(final ChunkUnloadAction action)
    {
        if (action.getChunk().get() == null)
        {
            return;
        }
        actions.add(action);
        synchronized (actions)
        {
            actions.notifyAll();
        }
    }

    @Override
    public void run()
    {
        while (this.server.isRunning())
        {
            final ChunkUnloadAction action = actions.poll();
            if (action == null)
            {
                try
                {
                    synchronized (actions)
                    {
                        actions.wait();
                    }
                } catch (final InterruptedException e)
                {
                    e.printStackTrace();
                }
                continue;
            }
            final long curr = System.currentTimeMillis();
            final long delta;
            if ((delta = (curr - action.getTime())) < TimeUnit.SECONDS.toMillis(10))
            {
                try
                {
                    synchronized (actions)
                    {
                        actions.wait(delta);
                    }
                } catch (final InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            final Chunk chunk = action.getChunk().get();
            if ((chunk == null) || (chunk.getUsages() > 0))
            {
                continue;
            }
            chunk.getWorld().getChunkManager().unload(chunk);
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("server", this.server).append("actionsSize", actions.size()).toString();
    }

    public static ChunkUnloaderThread start(final ServerImpl server)
    {
        final ChunkUnloaderThread thread = new ChunkUnloaderThread(server);
        thread.start();
        return thread;
    }
}
