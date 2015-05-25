package org.diorite.impl.world.chunk;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.Tickable;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutMapChunk;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutMapChunkBulk;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.utils.collections.WeakCollection;
import org.diorite.utils.collections.sets.ConcurrentSet;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkPos;

public class PlayerChunksImpl implements Tickable
{
    public static final int CHUNK_BULK_SIZE = 4;

    private final PlayerImpl player;
    private final Collection<Chunk> loadedChunks  = WeakCollection.using(new ConcurrentSet<>(500, 0.1f, 4));
    private final Collection<Chunk> visibleChunks = WeakCollection.using(new ConcurrentSet<>(500, 0.1f, 4));
    //    private final Collection<Chunk> chunksToReSend = WeakCollection.using(new ConcurrentSet<>(200, 0.1f, 4));
    private boolean  logout;
    private ChunkPos lastUpdate;
    private byte     lastUpdateR;
    private long lastUnload = System.currentTimeMillis();

    public PlayerChunksImpl(final PlayerImpl player)
    {
        this.player = player;
    }

    public byte getRenderDistance()
    {
        return this.player.getRenderDistance();
    }

    public Collection<Chunk> getVisibleChunks()
    {
        return this.visibleChunks;
    }

    public Collection<Chunk> getLoadedChunks()
    {
        return this.loadedChunks;
    }

    public byte getViewDistance()
    {
        return this.player.getViewDistance();
    }

    public PlayerImpl getPlayer()
    {
        return this.player;
    }

    public void logout()
    {
        this.logout = true;
        this.loadedChunks.parallelStream().forEach(Chunk::removeUsage);
        this.loadedChunks.clear();
        this.visibleChunks.clear();
    }

    public synchronized void checkAndUnload()
    {
        final long curr = System.currentTimeMillis();
        if ((curr - this.lastUnload) < TimeUnit.SECONDS.toMillis(5))
        {
            return;
        }
        this.lastUnload = curr;
        final byte render = this.getRenderDistance();
        for (final Iterator<Chunk> iterator = this.loadedChunks.iterator(); iterator.hasNext(); )
        {
            final Chunk chunk = iterator.next();
            if (chunk.getPos().isInAABB(this.lastUpdate.add(- render, - render), this.lastUpdate.add(render, render)))
            {
//                if (this.visibleChunks.contains(chunk) && ! chunk.isPopulated() && chunk.populate())
//                {
//                    this.chunksToReSend.add(chunk);
//                }
                continue;
            }
            iterator.remove();
            chunk.removeUsage();
            this.player.getNetworkManager().sendPacket(PacketPlayOutMapChunk.unload(chunk.getPos()));
        }
    }

    private void continueUpdate()
    {
        final byte render = this.getRenderDistance();
        final byte view = this.getViewDistance();
        if ((this.lastUpdateR >= view) || (this.lastUpdateR >= render))
        {
            return;
        }
        final Collection<Chunk> chunksToSent = new ConcurrentSet<>();
        final int r = this.lastUpdateR++;
        final ChunkManagerImpl impl = this.player.getWorld().getChunkManager();

        // size is 8 * r, for empty rectangle of radius r (remember about center block)
        // so each side need (2 * r) + 1 units, so for whole rectangle,
        // we have (((2 * r) + 1) * 4) but corners are this same for all sides,
        // so we need subtract them: (((2 * r) + 1) * 4) - 4 == 8 * r
        final CountDownLatch latch = new CountDownLatch((r == 0) ? 1 : (8 * r));

        forChunks(r, this.lastUpdate, chunkPos -> {
            Chunk chunk = impl.getChunkAt(chunkPos, true, false);
            try
            {
                if (chunk == null)
                {
                    return;
                }
//                Main.debug("[Latch;" + latch.hashCode() + "] " + chunk.getPos());
                final boolean isVisible = r <= view;
                if (! this.loadedChunks.contains(chunk))
                {
                    chunk.addUsage();
                    if (isVisible)
                    {
                        chunksToSent.add(chunk);
                    }
                }
                if (isVisible)
                {
                    this.visibleChunks.add(chunk);
                }
            } finally
            {
                latch.countDown();
//                Main.debug("[Latch;" + latch.hashCode() + "] " + latch.getCount());
            }
        });
        try
        {
            latch.await();
        } catch (final InterruptedException e)
        {
            e.printStackTrace();
        }

        for (final Iterator<Chunk> iterator = chunksToSent.iterator(); iterator.hasNext(); )
        {
            final Chunk chunk = iterator.next();
            if (! chunk.isPopulated() && ! chunk.populate())
            {
                iterator.remove();
            }
        }
        final int size = (chunksToSent.size() / CHUNK_BULK_SIZE) + (((chunksToSent.size() % CHUNK_BULK_SIZE) == 0) ? 0 : 1);
        final Chunk[][] chunkBulks = new Chunk[size][CHUNK_BULK_SIZE];
        int i = 0;
        for (final Chunk chunk : chunksToSent)
        {
            this.loadedChunks.add(chunk);
            chunkBulks[i / CHUNK_BULK_SIZE][i++ % CHUNK_BULK_SIZE] = chunk;
        }

        for (final Chunk[] chunkBulk : chunkBulks)
        {
            this.player.getNetworkManager().sendPacket(new PacketPlayOutMapChunkBulk(true, chunkBulk));
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("player", this.player).toString();
    }

    @Override
    public void doTick()
    {
        if (this.logout)
        {
            return;
        }
        final ChunkPos center = this.player.getLocation().getChunkPos();
        if (center.equals(this.lastUpdate))
        {
            this.continueUpdate();
            return;
        }
        this.lastUpdateR = 0;
        this.lastUpdate = center;
        this.continueUpdate();
        this.checkAndUnload();
    }

    static void forChunks(final int r, final ChunkPos center, final Consumer<ChunkPos> action)
    {
        if (r == 0)
        {
            action.accept(center);
            return;
        }
        IntStream.rangeClosed(- r, r).forEach(x -> {
            if ((x == r) || (x == - r))
            {
                IntStream.rangeClosed(- r, r).forEach(z -> action.accept(center.add(x, z)));
                return;
            }
            action.accept(center.add(x, r));
            action.accept(center.add(x, - r));
        });
    }

}
