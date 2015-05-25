package org.diorite.impl.world.io.mca;

import java.io.File;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.io.ChunkIO;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.world.World;
import org.diorite.world.chunk.ChunkPos;

@SuppressWarnings({"rawtypes", "unchecked"})
public class McaChunkIO extends ChunkIO<ChunkImpl>
{
    public McaChunkIO(final File worldDir)
    {
        super(worldDir);
        this.provider = new McaRegionProvider();
    }

    public McaChunkIO(final File worldDir, final McaRegionProvider provider)
    {
        super(worldDir);
        this.provider = provider;
    }

    public McaChunkIO(final File worldDir, final World world)
    {
        super(worldDir, world);
        this.provider = new McaRegionProvider();
    }

    public McaChunkIO(final File worldDir, final World world, final McaRegionProvider provider)
    {
        super(worldDir, world);
        this.provider = provider;
    }

    protected final McaRegionProvider provider;

    public McaRegionProvider getProvider()
    {
        return this.provider;
    }

    private static class SaveInfo
    {
        private final AtomicInteger counter  = new AtomicInteger();
        private       long          lastTime = System.currentTimeMillis();

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("counter", this.counter.get()).append("lastTime", this.lastTime).toString();
        }
    }

    @Override
    public ChunkImpl loadChunk(final ChunkPos pos)
    {
        return ChunkImpl.loadFromNBT((pos.getWorld() == null) ? this.world : pos.getWorld(), this.getChunkData(pos.getX(), pos.getZ()));
    }

    @Override
    public ChunkImpl loadChunk(final int x, final int z)
    {
        return ChunkImpl.loadFromNBT(this.world, this.getChunkData(x, z));
    }

    @Override
    public void saveChunk(final ChunkImpl chunk)
    {
        this.saveChunkData(chunk.getX(), chunk.getZ(), chunk.writeTo(chunk.writeTo(new NbtTagCompound("Level"))));
    }

    @Override
    public NbtTagCompound getChunkData(final int chunkX, final int chunkZ)
    {
        final McaChunkProvider r = this.provider.getChunkProvider(this.worldDir, chunkX, chunkZ);
        return r.getChunkData(chunkX, chunkZ);
    }

    @Override
    public void saveChunkData(final int chunkX, final int chunkZ, final NbtTagCompound data)
    {
        final McaChunkProvider r = this.provider.getChunkProvider(this.worldDir, chunkX, chunkZ);
        r.saveChunkData(chunkX, chunkZ, data);
    }

    @Override
    public void saveChunks(final Collection<? extends ChunkImpl> chunksToSave)
    {
        chunksToSave.forEach(this::saveChunk);
//
//        final SaveInfo info = new SaveInfo();
//        final int size = chunksToSave.size();
//
//        /*
//        Chunks to save are split into region file parts,
//        and every that part can be saved in other thread
//        without blocking other parts.
//         */
//
//        //noinspection MagicNumber
//        final Multimap<Long, ChunkImpl> chunks = HashMultimap.create(10, 512);
//        //noinspection MagicNumber
//        chunksToSave.stream().forEach(chunk -> chunks.put(((((long) (chunk.getX() >> 5)) << 32) | ((chunk.getZ() >> 5) & 0xffffffffL)), chunk));
//        final int parts = chunks.keySet().size();
//        Main.debug("Save pool: " + parts);
//        final CountDownLatch lock = new CountDownLatch(parts);
//        final ForkJoinPool pool = new ForkJoinPool(parts);
//        for (final Entry<Long, Collection<ChunkImpl>> entry : chunks.asMap().entrySet())
//        {
//            pool.submit(() -> {
//                entry.getValue().forEach(c -> {
//                    this.saveChunk(c);
//                    info.counter.incrementAndGet();
//                    final long cur = System.currentTimeMillis();
//                    if ((cur - info.lastTime) >= TimeUnit.SECONDS.toMillis(1))
//                    {
//                        //noinspection HardcodedFileSeparator
//                        System.out.println("[ChunkSave][" + c.getWorld().getName() + "] Chunk: " + info.counter.get() + "/" + size);
//                        info.lastTime = cur;
//                    }
//                });
//                lock.countDown();
//            });
//        }
//        try
//        {
//            lock.await();
//        } catch (final InterruptedException e)
//        {
//            e.printStackTrace();
//        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("provider", this.provider).toString();
    }
}