package diorite.impl.map.chunk;

import java.util.Collection;

import diorite.impl.entity.PlayerImpl;
import diorite.utils.collections.WeakCollection;

public class PlayerChunksImpl
{
    private final PlayerImpl player;
    private final Collection<ChunkImpl> loadedChunks = WeakCollection.usingHashSet(100);

    public PlayerChunksImpl(final PlayerImpl player)
    {
        this.player = player;
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
        final byte view = this.getViewDistance();
        // TODO: finish
    }
}
