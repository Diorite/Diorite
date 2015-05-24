package org.diorite.impl.world.chunk;

import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.WorldImpl;
import org.diorite.BlockLocation;
import org.diorite.event.EventType;
import org.diorite.event.chunk.ChunkGenerateEvent;
import org.diorite.event.chunk.ChunkLoadEvent;
import org.diorite.event.chunk.ChunkPopulateEvent;
import org.diorite.utils.concurrent.ParallelUtils;
import org.diorite.utils.concurrent.ParallelUtils.NamedForkJoinWorkerFactory;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkManager;
import org.diorite.world.chunk.ChunkPos;

public class ChunkManagerImpl implements ChunkManager
{
    private final WorldImpl world;
    private final Map<Long, Chunk>  chunks     = new ConcurrentHashMap<>(400, .5f, 4); // change to ConcurrentHashMap<Long, ChunkImpl> if needed.
    private final Map<Long, Object> generating = new ConcurrentHashMap<>(10);
    private final ForkJoinPool pool;

    public ChunkManagerImpl(final WorldImpl world)
    {
        this.world = world;
        // TODO: add way to configure max fork join tasks per world
        this.pool = new ForkJoinPool(Math.max(1, Runtime.getRuntime().availableProcessors() / 2), new NamedForkJoinWorkerFactory(world.getName() + "-ChunkLoader"), null, false);
    }

    public ForkJoinPool getPool()
    {
        return this.pool;
    }

    public synchronized void loadBase(final int chunkRadius, final BlockLocation center)
    {
        final LoadInfo info = new LoadInfo();
        final int toLoad = chunkRadius * chunkRadius;
        System.out.println("Loading spawn chunks for world: " + this.world.getName());

        final Deque<Chunk> unpopulatedChunks = new ConcurrentLinkedDeque<>();
        for (int r = 0; r <= chunkRadius; r++)
        {
            final int cr = r;
            this.pool.invoke(ParallelUtils.createSimpleTask(() -> {
                forChunksParallel(cr, center.getChunkPos(), (pos) -> {
                    final Chunk impl = this.getChunkAt(pos, true, false);
                    impl.addUsage();
                    if (! impl.isPopulated())
                    {
                        unpopulatedChunks.add(impl);
                    }
                    if ((info.loadedChunks.incrementAndGet() % 10) == 0)
                    {
                        final long cur = System.currentTimeMillis();
                        if ((cur - info.lastTime) >= TimeUnit.SECONDS.toMillis(5))
                        {
                            //noinspection HardcodedFileSeparator
                            System.out.println("[ChunkLoader][" + this.world.getName() + "] Chunk: " + info.loadedChunks.get() + "/" + toLoad + " Radius " + cr + "/" + chunkRadius);
                            info.lastTime = cur;
                        }
                    }
                });
            }));
            //noinspection HardcodedFileSeparator
            System.out.println("[ChunkLoader][" + this.world.getName() + "] Radius " + r + "/" + chunkRadius);
            info.lastTime = System.currentTimeMillis();
        }

        System.out.println("Loaded " + info.loadedChunks.get() + " spawn chunks for world: " + this.world.getName());
        if (! unpopulatedChunks.isEmpty())
        {
            unpopulatedChunks.stream().forEach(Chunk::populate);
            System.out.println("Populated " + unpopulatedChunks.size() + " spawn chunks for world: " + this.world.getName());
        }
    }

    @Override
    public synchronized void unloadAll()
    {
        synchronized (this.chunks)
        {
            long time = System.currentTimeMillis();
            final int size = this.chunks.values().size();
            int i = 0;
            for (final Chunk chunk : this.chunks.values())
            {
                this.unload(chunk);
                i++;
                final long cur = System.currentTimeMillis();
                if ((cur - time) >= TimeUnit.SECONDS.toMillis(1))
                {
                    //noinspection HardcodedFileSeparator
                    System.out.println("[ChunkUnLoader][" + this.world.getName() + "] Chunk: " + i + "/" + size);
                    time = cur;
                }
            }
        }
    }

    @Override
    public synchronized void saveAll()
    {
        this.world.getWorldFile().getIo().saveChunks(this.chunks.values(), this.world.getWorldFile());
    }

    @Override
    public void unload(final Chunk chunk)
    {
        // TODO: some way to delay chunk save, and unload it after some delay (to preved lags when player jumps between 2 chunks)

        //noinspection unchecked
        this.world.getWorldFile().getIo().saveChunk(this.chunks.remove(chunk.getPos().asLong()), this.world.getWorldFile());
    }

    @Override
    public Chunk getChunkAt(final ChunkPos pos)
    {
        return this.getChunkAt(pos, true, true);
    }

    @Override
    public Chunk getChunkAt(final int x, final int z)
    {
        return this.getChunkAt(new ChunkPos(x, z, this.world), true);
    }

    @Override
    public Chunk getChunkAt(ChunkPos pos, final boolean generate, final boolean populate)
    {
        final long posLong = pos.asLong();
//        final Object lock = new Object();
//        final Object oldLock = this.generating.putIfAbsent(posLong, lock);
        Chunk chunk = this.chunks.get(posLong);
        if ((chunk == null) || (populate && ! chunk.isPopulated()))
        {
            try
            {
                final Object lock = this.generating.get(posLong);
                if (lock != null)
                {
                    try
                    {
                        //noinspection SynchronizationOnLocalVariableOrMethodParameter
                        synchronized (lock)
                        {
                            lock.wait();
                        }
                    } catch (final InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    return this.chunks.get(posLong);
                }
                this.generating.put(posLong, new Object());

                if (chunk == null)
                {
                    pos = pos.setWorld(this.world);
                    final ChunkLoadEvent loadEvt = new ChunkLoadEvent(pos);
                    loadEvt.call();
                    if (loadEvt.isCancelled())
                    {
                        return new DumbChunk(pos);
                    }
                    chunk = loadEvt.getLoadedChunk();
                    if (chunk == null)
                    {
                        if (generate)
                        {
                            final ChunkGenerateEvent genEvt = new ChunkGenerateEvent(pos);
                            genEvt.call();
                            if (genEvt.isCancelled() || (genEvt.getGeneratedChunk() == null))
                            {
                                return new DumbChunk(pos);
                            }
                            chunk = genEvt.getGeneratedChunk();
                            this.chunks.put(posLong, chunk);
                        }
                    }
                    else
                    {
                        this.chunks.put(posLong, chunk);
                    }
                }
                if ((chunk != null) && populate && ! chunk.isPopulated())
                {
                    EventType.callEvent(new ChunkPopulateEvent(chunk));
                }
            } finally
            {
                final Object lockObj = this.generating.remove(posLong);
                if (lockObj != null)
                {
                    //noinspection SynchronizationOnLocalVariableOrMethodParameter
                    synchronized (lockObj)
                    {
                        lockObj.notifyAll();
                    }
                }
            }
        }
        return chunk;
    }

    @Override
    public Chunk getChunkAt(final ChunkPos pos, final boolean generate)
    {
        return this.getChunkAt(pos, generate, generate);
    }

    @Override
    public Chunk getChunkAt(final int x, final int z, final boolean generate)
    {
        return this.getChunkAt(new ChunkPos(x, z, this.world), generate);
    }

    public void submitActionOnChunkAt(final ChunkPos pos, final boolean generate, final boolean populate, final Consumer<Chunk> consumer)
    {
        this.pool.submit(() -> consumer.accept(this.getChunkAt(pos, generate, populate)));
    }

    static void forChunksParallel(final int r, final ChunkPos center, final Consumer<ChunkPos> action)
    {
        if (r == 0)
        {
            action.accept(center);
            return;
        }
        IntStream.rangeClosed(- r, r).parallel().forEach(x -> {
            if ((x == r) || (x == - r))
            {
                IntStream.rangeClosed(- r, r).parallel().forEach(z -> action.accept(center.add(x, z)));
                return;
            }
            action.accept(center.add(x, r));
            action.accept(center.add(x, - r));
        });
    }

    private static class LoadInfo
    {
        private final AtomicInteger loadedChunks = new AtomicInteger();
        private       long          lastTime     = System.currentTimeMillis();

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("loadedChunks", this.loadedChunks.get()).append("lastTime", this.lastTime).toString();
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("world", this.world).append("chunks", this.chunks.keySet()).toString();
    }
}
