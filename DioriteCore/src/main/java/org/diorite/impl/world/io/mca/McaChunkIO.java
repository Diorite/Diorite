package org.diorite.impl.world.io.mca;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.Main;
import org.diorite.impl.world.io.ChunkIO;
import org.diorite.impl.world.io.RegionProvider;
import org.diorite.impl.world.io.WorldFile;
import org.diorite.world.chunk.Chunk;

@SuppressWarnings({"rawtypes", "unchecked"})
public class McaChunkIO extends ChunkIO
{
    public McaChunkIO(final RegionProvider provider)
    {
        super(provider);
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
    public void saveChunks(final Collection<Chunk> chunksToSave, final WorldFile wf)
    {

        final SaveInfo info = new SaveInfo();
        final int size = chunksToSave.size();

        /*
        Chunks to save are split into region file parts,
        and every that part can be saved in other thread
        without blocking other parts.
         */

        //noinspection MagicNumber
        final Multimap<Long, Chunk> chunks = HashMultimap.create(10, 512);
        //noinspection MagicNumber
        chunksToSave.stream().forEach(chunk -> chunks.put(((((long) (chunk.getX() >> 5)) << 32) | ((chunk.getZ() >> 5) & 0xffffffffL)), chunk));
        final int parts = chunks.keySet().size();
        Main.debug("Save pool: " + parts);
        final CountDownLatch lock = new CountDownLatch(parts);
        final ForkJoinPool pool = new ForkJoinPool(parts);
        for (final Entry<Long, Collection<Chunk>> entry : chunks.asMap().entrySet())
        {
            pool.submit(() -> {
                entry.getValue().forEach(c -> {
                    wf.saveChunk(c);
                    info.counter.incrementAndGet();
                    final long cur = System.currentTimeMillis();
                    if ((cur - info.lastTime) >= TimeUnit.SECONDS.toMillis(1))
                    {
                        //noinspection HardcodedFileSeparator
                        System.out.println("[ChunkSave][" + c.getWorld().getName() + "] Chunk: " + info.counter.get() + "/" + size);
                        info.lastTime = cur;
                    }
                });
                lock.countDown();
            });
        }
        try
        {
            lock.await();
        } catch (final InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void saveChunk(final Chunk chunk, final WorldFile wf)
    {
        wf.saveChunk(chunk);
    }
}
