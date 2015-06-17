package org.diorite.impl.world.chunk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.Tickable;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutMapChunk;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutMapChunkBulk;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.impl.world.chunk.ChunkManagerImpl.ChunkLock;
import org.diorite.utils.collections.sets.ConcurrentSet;
import org.diorite.world.chunk.ChunkPos;

import gnu.trove.TLongCollection;
import gnu.trove.iterator.TLongIterator;
import gnu.trove.set.TLongSet;
import gnu.trove.set.hash.TLongHashSet;

public class PlayerChunksImpl implements Tickable
{
    public static final int CHUNK_BULK_SIZE = 4;

    private final PlayerImpl player;
    @SuppressWarnings("MagicNumber")
    private final TLongSet visibleChunks = new TLongHashSet(400);
    private final ChunkLock chunkLock;
    private       boolean   logout;
    private       ChunkPos  lastUpdate;
    private       byte      lastUpdateR;
    private long lastUnload = System.currentTimeMillis();

    public PlayerChunksImpl(final PlayerImpl player)
    {
        this.player = player;
        this.chunkLock = player.getWorld().createLock(player.getName());
    }

    public byte getRenderDistance()
    {
        return this.player.getRenderDistance();
    }

    public TLongSet getVisibleChunks()
    {
        return this.visibleChunks;
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
        for (final TLongIterator it = this.visibleChunks.iterator(); it.hasNext(); )
        {
            final long key = it.next();
            this.chunkLock.release(key);
        }
        this.visibleChunks.clear();
    }

    private void checkOld()
    {
        final long curr = System.currentTimeMillis();
        if ((curr - this.lastUnload) < TimeUnit.SECONDS.toMillis(5))
        {
            return;
        }
        this.lastUnload = curr;
        final byte render = this.getRenderDistance();

        for (final TLongIterator it = this.visibleChunks.iterator(); it.hasNext(); )
        {
            final long key = it.next();
            final ChunkPos chunkPos = ChunkPos.fromLong(key);
            if (chunkPos.isInAABB(this.lastUpdate.add(- render, - render), this.lastUpdate.add(render, render)))
            {
                continue;
            }
            it.remove();
            this.chunkLock.release(key);
            this.player.getNetworkManager().sendPacket(PacketPlayOutMapChunk.unload(chunkPos));
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
        final TLongCollection oldChunks = new TLongHashSet(this.visibleChunks);
        final Collection<ChunkImpl> chunksToSent = new ConcurrentSet<>();
        final int r = this.lastUpdateR++;
        final ChunkManagerImpl impl = this.player.getWorld().getChunkManager();

        forChunks(r, this.lastUpdate, chunkPos -> {
            impl.forcePopulation(chunkPos.getX(), chunkPos.getZ());
            long key = chunkPos.asLong();
            if (this.visibleChunks.contains(key))
            {
                oldChunks.remove(key);
            }
            else
            {
                this.visibleChunks.add(key);
                this.chunkLock.acquire(key);
                chunksToSent.add(impl.getChunk(chunkPos));
            }

        });
        if (chunksToSent.isEmpty() && oldChunks.isEmpty())
        {
            return;
        }
        List<PacketPlayOutMapChunk> packets = new ArrayList<>(6);
        int bulkSize = 6;

        for (final ChunkImpl chunk : chunksToSent)
        {
            chunk.load();
            final PacketPlayOutMapChunk packet = new PacketPlayOutMapChunk(true, chunk);
            final int messageSize = PacketPlayOutMapChunkBulk.HEADER_SIZE + packet.getData().getRawData().length;

            // send current data if too big
            if ((bulkSize + messageSize) > PacketPlayOutMapChunkBulk.MAX_SIZE)
            {
                this.player.getNetworkManager().sendPacket(new PacketPlayOutMapChunkBulk(packets, this.player.getWorld()));
                packets = new ArrayList<>(6);
                bulkSize = 6;
            }

            bulkSize += messageSize;
            packets.add(packet);
        }

        // send rest if exist
        if (! packets.isEmpty())
        {
            this.player.getNetworkManager().sendPacket(new PacketPlayOutMapChunkBulk(packets, this.player.getWorld()));
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("player", this.player).toString();
    }

    @Override
    public void doTick(final int tps)
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
        this.checkOld();
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
