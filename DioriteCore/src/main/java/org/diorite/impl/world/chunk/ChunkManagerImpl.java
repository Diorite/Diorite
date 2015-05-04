package org.diorite.impl.world.chunk;

import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.WorldImpl;
import org.diorite.impl.world.generator.ChunkBuilderImpl;
import org.diorite.BlockLocation;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkManager;
import org.diorite.world.chunk.ChunkPos;
import org.diorite.world.generator.ChunkBuilder;

public class ChunkManagerImpl implements ChunkManager
{
    private final WorldImpl world;
    private final Map<Long, Chunk>  chunks     = new ConcurrentHashMap<>(400, .5f, 4); // change to ConcurrentHashMap<Long, ChunkImpl> if needed.
    private final Map<Long, Object> generating = new ConcurrentHashMap<>(10);

    public ChunkManagerImpl(final WorldImpl world)
    {
        this.world = world;
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
            PlayerChunksImpl.forChunksParallel(r, center.getChunkPos(), (pos) -> {
                final ChunkImpl impl = ((ChunkImpl) this.getChunkAt(pos, true, false));
                impl.addUsage();
                if (! impl.isPopulated())
                {
                    unpopulatedChunks.add(impl);
                }
                if ((info.loadedChunks++ % 10) == 0)
                {
                    final long cur = System.currentTimeMillis();
                    if ((cur - info.lastTime) >= TimeUnit.SECONDS.toMillis(5))
                    {
                        //noinspection HardcodedFileSeparator
                        System.out.println("[ChunkLoader][" + this.world.getName() + "] Chunk: " + info.loadedChunks + "/" + toLoad + " Radius " + cr + "/" + chunkRadius);
                        info.lastTime = cur;
                    }
                }
            });
            //noinspection HardcodedFileSeparator
            System.out.println("[ChunkLoader][" + this.world.getName() + "] Radius " + r + "/" + chunkRadius);
            info.lastTime = System.currentTimeMillis();
        }

        System.out.println("Loaded " + info.loadedChunks + " spawn chunks for world: " + this.world.getName());
        if (! unpopulatedChunks.isEmpty())
        {
            unpopulatedChunks.parallelStream().forEach(Chunk::populate);
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
        final LoadInfo info = new LoadInfo();
        final int size = this.chunks.values().size();
        this.chunks.values().stream().forEach(chunk ->
        {
            this.world.getWorldFile().saveChunk((ChunkImpl) chunk);
            info.loadedChunks++;
            final long cur = System.currentTimeMillis();
            if ((cur - info.lastTime) >= TimeUnit.SECONDS.toMillis(1))
            {
                //noinspection HardcodedFileSeparator
                System.out.println("[ChunkSave][" + this.world.getName() + "] Chunk: " + info.loadedChunks + "/" + size);
                info.lastTime = cur;
            }
        });
    }

    @Override
    public void unload(final Chunk chunk)
    {
        // TODO: some way to delay chunk save, and unload it after some delay (to preved lags when player jumps between 2 chunks)

        this.world.getWorldFile().saveChunk((ChunkImpl) this.chunks.remove(chunk.getPos().asLong()));
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
        Chunk chunk = this.chunks.get(posLong);
        if ((chunk == null) && generate)
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
            pos = pos.setWorld(this.world);
            chunk = this.world.getWorldFile().loadChunk(pos);
            if (chunk == null)
            {
                final ChunkBuilder chunkBuilder = this.world.getGenerator().generate(new ChunkBuilderImpl(), pos);
                chunk = chunkBuilder.createChunk(pos);
                this.chunks.put(posLong, chunk);
                if (populate)
                {
                    chunk.populate();
                }
            }
            else
            {
                this.chunks.put(posLong, chunk);
            }
            final Object obj = this.generating.remove(posLong);
            if (obj != null)
            {
                //noinspection SynchronizationOnLocalVariableOrMethodParameter
                synchronized (obj)
                {
                    obj.notifyAll();
                }
            }
        }
        if ((chunk != null) && populate)
        {
            chunk.populate();
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

    private static class LoadInfo
    {
        private int  loadedChunks = 0;
        private long lastTime     = System.currentTimeMillis();

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("loadedChunks", this.loadedChunks).append("lastTime", this.lastTime).toString();
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("world", this.world).append("chunks", this.chunks.keySet()).toString();
    }
}
