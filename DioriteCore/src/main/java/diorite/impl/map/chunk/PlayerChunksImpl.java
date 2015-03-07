package diorite.impl.map.chunk;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import diorite.impl.connection.packets.play.out.PacketPlayOutMapChunk;
import diorite.impl.connection.packets.play.out.PacketPlayOutMapChunkBulk;
import diorite.impl.entity.PlayerImpl;
import diorite.map.chunk.ChunkPos;

public class PlayerChunksImpl
{
    public static final int CHUNK_BULK_SIZE = 4;

    private final PlayerImpl player;
    private final Collection<ChunkImpl> loadedChunks  = new HashSet<>(200);
    private final Collection<ChunkImpl> visibleChunks = new HashSet<>(200);

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

    public void update()
    {
        this.visibleChunks.clear();
        final byte render = this.getRenderDistance();
        final byte view = this.getViewDistance();
        final ChunkPos center = this.player.getLocation().getChunkPos();

        final Collection<ChunkImpl> chunksToUnload = new HashSet<>(50);
        final Collection<ChunkImpl> chunksToSent = new HashSet<>(50);

        for (int x = center.getX() - render; x <= (center.getX() + render); x++)
        {
            for (int z = center.getX() - render; z <= (center.getX() + render); z++)
            {
                final ChunkPos chunkPos = new ChunkPos(x, z, center.getWorld());
                if (! chunkPos.isInSphere(center, render))
                {
                    continue;
                }
                final ChunkImpl chunk = this.player.getWorld().getChunkManager().getChunkAt(chunkPos, true);
                if (chunk == null)
                {
                    continue;
                }
                if (! this.loadedChunks.contains(chunk))
                {
                    chunk.addUsage();
                    this.loadedChunks.add(chunk);
                }
                if (chunkPos.isInSphere(center, view))
                {
                    this.visibleChunks.add(chunk);
                    chunksToSent.add(chunk);
                }
            }
        }
        for (final Iterator<ChunkImpl> iterator = this.loadedChunks.iterator(); iterator.hasNext(); )
        {
            final ChunkImpl chunk = iterator.next();
            if (chunk.getPos().isInSphere(center, render))
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
        for (final ChunkImpl chunk:chunksToSent)
        {
            chunkBulks[i / CHUNK_BULK_SIZE][i++ % CHUNK_BULK_SIZE] = chunk;
        }
        for (final ChunkImpl[] chunkBulk:chunkBulks)
        {
            this.player.getNetworkManager().handle(new PacketPlayOutMapChunkBulk(true, chunkBulk));
        }
        for (final ChunkImpl chunk:chunksToUnload)
        {
            this.player.getNetworkManager().handle(PacketPlayOutMapChunk.unload(chunk.getPos()));
        }
    }
}
