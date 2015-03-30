package org.diorite.impl.world.chunk;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.packets.play.out.PacketPlayOutMapChunk;
import org.diorite.impl.connection.packets.play.out.PacketPlayOutMapChunkBulk;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.utils.collections.WeakCollection;
import org.diorite.world.chunk.ChunkPos;

public class PlayerChunksImpl
{
    public static final int CHUNK_BULK_SIZE = 4;

    private final PlayerImpl player;
    private final Collection<ChunkImpl> loadedChunks  = new WeakCollection<>(200);
    private final Collection<ChunkImpl> visibleChunks = new WeakCollection<>(200);
    private boolean logout;
    private boolean wantUpdate = true;

    public PlayerChunksImpl(final PlayerImpl player)
    {
        this.player = player;
    }

    public byte getRenderDistance()
    {
        return this.player.getRenderDistance();
    }

    public Collection<ChunkImpl> getVisibleChunks()
    {
        return this.visibleChunks;
    }

    public Collection<ChunkImpl> getLoadedChunks()
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
        this.loadedChunks.parallelStream().forEach(ChunkImpl::removeUsage);
        this.loadedChunks.clear();
        this.visibleChunks.clear();
    }

    public void wantUpdate()
    {
        this.wantUpdate = true;
    }

    public void update()
    {
        if (this.logout || ! this.wantUpdate)
        {
            return;
        }
        this.wantUpdate = false;
        final byte render = this.getRenderDistance();
        final byte view = this.getViewDistance();
        final ChunkPos center = this.player.getLocation().getChunkPos();

        final Collection<ChunkImpl> chunksToUnload = new HashSet<>(50);
        final Collection<ChunkImpl> chunksToSent = new HashSet<>(50);

        for (int r = 0; r <= render; r++)
        {
            final int copyR = r;
            forChunks(r, center, chunkPos -> {
                final ChunkImpl chunk = (ChunkImpl) this.player.getWorld().getChunkManager().getChunkAt(chunkPos, true);
                if (chunk == null)
                {
                    return;
                }
                final boolean isVisible = copyR <= view;
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
            });
        }
        for (final Iterator<ChunkImpl> iterator = this.loadedChunks.iterator(); iterator.hasNext(); )
        {
            final ChunkImpl chunk = iterator.next();
            if (chunk.getPos().isInAABB(center.add(- render, - render), center.add(render, render)))
            {
                continue;
            }
            chunksToUnload.add(chunk);
            iterator.remove();
            if (chunk.removeUsage() == 0)
            {
                this.player.getWorld().getChunkManager().unload(chunk);
            }
        }

        final int size = (chunksToSent.size() / CHUNK_BULK_SIZE) + (((chunksToSent.size() % CHUNK_BULK_SIZE) == 0) ? 0 : 1);
        final ChunkImpl[][] chunkBulks = new ChunkImpl[size][CHUNK_BULK_SIZE];
        int i = 0;
        for (final ChunkImpl chunk : chunksToSent)
        {
            this.loadedChunks.add(chunk);
            chunkBulks[i / CHUNK_BULK_SIZE][i++ % CHUNK_BULK_SIZE] = chunk;
        }

        for (final ChunkImpl[] chunkBulk : chunkBulks)
        {
            this.player.getNetworkManager().sendPacket(new PacketPlayOutMapChunkBulk(true, chunkBulk));
        }

        for (final ChunkImpl chunk : chunksToUnload)
        {
            this.loadedChunks.remove(chunk);
            this.player.getNetworkManager().sendPacket(PacketPlayOutMapChunk.unload(chunk.getPos()));
        }

    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("player", this.player).toString();
    }

    static void forChunks(final int r, final ChunkPos center, final Consumer<ChunkPos> action)
    {
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

}
