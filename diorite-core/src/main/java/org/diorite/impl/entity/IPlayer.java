package org.diorite.impl.entity;

import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.impl.world.chunk.PlayerChunksImpl;
import org.diorite.entity.Player;

public interface IPlayer extends IHuman, Player
{
    CoreNetworkManager getNetworkManager();

    PlayerChunksImpl getPlayerChunks();

    boolean isVisibleChunk(int x, int z);

    void setViewDistance(byte viewDistance);

    void onLogout();
}
