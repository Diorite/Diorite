package org.diorite.impl.world.chunk;

import org.diorite.impl.Tickable;

/**
 * One or more chunk files per group, don't split files between multiple groups.
 */
public class ChunkGroup implements Tickable
{

    @Override
    public void doTick(final int tps)
    {
        // TODO
    }
//    @SuppressWarnings("MagicNumber")
//    private final TIntObjectMap<EntityImpl> entities = new TIntObjectHashMap<>(200);
//    private final Collection<ChunkImpl>     chunks   = WeakCollection.using(new ConcurrentSet<>(1000, 0.5f, 4), true);
//    private final ForkJoinPool              pool     = new ForkJoinPool(1);
//    private final ForkJoinPool              savePool = new ForkJoinPool(1);
//
//    public void addEntity(final EntityImpl entity)
//    {
//        this.entities.put(entity.getId(), entity);
//    }
//
//    public void removeEntity(final Entity entity)
//    {
//        this.entities.remove(entity.getId());
//    }
//
//    public void removeEntity(final int id)
//    {
//        this.entities.remove(id);
//    }
//
//    @Override
//    public void doTick(final int tps)
//    {
//        this.pool.submit(this);
//    }
//
//    public ForkJoinPool getSavePool()
//    {
//        return this.savePool;
//    }
//
//    public ForkJoinPool getPool()
//    {
//        return this.pool;
//    }
//
//    public boolean addChunk(final ChunkImpl chunk)
//    {
//        return this.isIn(chunk.getPos()) && this.chunks.add(chunk);
//    }
////
////    public boolean saveChunk(final ChunkImpl chunk)
////    {
////        if (this.chunks.contains(chunk))
////        {
////            this.savePool.submit(() -> chunk.getWorld().getWorldFile().saveChunk(chunk));
////            return true;
////        }
////        return false;
////    }
////
////    public boolean saveChunk(final ChunkImpl chunk, final Runnable pre)
////    {
////        if (this.chunks.contains(chunk))
////        {
////            this.savePool.submit(() -> {
////                if (pre != null)
////                {
////                    pre.run();
////                }
////                chunk.getWorld().getWorldFile().saveChunk(chunk);
////            });
////            return true;
////        }
////        return false;
////    }
////
////    public boolean saveChunk(final ChunkImpl chunk, final Runnable pre, final Runnable after)
////    {
////        if (this.chunks.contains(chunk))
////        {
////            this.savePool.submit(() -> {
////                if (pre != null)
////                {
////                    pre.run();
////                }
////                chunk.getWorld().getWorldFile().saveChunk(chunk);
////                if (after != null)
////                {
////                    after.run();
////                }
////            });
////            return true;
////        }
////        return false;
////    }
//
//    public boolean isIn(final ChunkPos pos)
//    {
//        return true; // TODO
//    }
//
//    @Override
//    public String toString()
//    {
//        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("chunks", this.chunks).toString();
//    }
//
//    @Override
//    public void run()
//    {
//        this.chunks.forEach(c -> c.doTick(tps));
//        this.entities.forEachValue((entity) -> {
//            entity.doTick(tps);
//            return true;
//        });
//    }
//
//    public class ChunkGroupThread extends Thread
//    {
//        public ChunkGroupThread()
//        {
//            super(ChunkGroup.this, "ChunkTick@" + System.identityHashCode(ChunkGroup.this));
//            this.setDaemon(true);
//        }
//    }
//
//    public static void swapEntity(final ChunkGroup from, final ChunkGroup to, final EntityImpl entity)
//    {
//        //noinspection ObjectEquality
//        if (from == to)
//        {
//            return;
//        }
//        from.removeEntity(entity);
//        to.addEntity(entity);
//    }
}
