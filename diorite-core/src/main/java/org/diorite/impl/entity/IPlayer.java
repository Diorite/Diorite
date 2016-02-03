package org.diorite.impl.entity;

import org.diorite.impl.connection.CoreNetworkManager;
import org.diorite.impl.entity.tracker.BaseTracker;
import org.diorite.impl.inventory.PlayerInventoryImpl;
import org.diorite.impl.world.chunk.PlayerChunksImpl;
import org.diorite.entity.Entity;
import org.diorite.entity.Player;

public interface IPlayer extends IHuman, Player
{
    @SuppressWarnings("ObjectEquality")
    void removeEntityFromView(BaseTracker<?> e);

    @SuppressWarnings("ObjectEquality")
    void removeEntityFromView(Entity e);

    CoreNetworkManager getNetworkManager();

    PlayerChunksImpl getPlayerChunks();

    @Override
    PlayerInventoryImpl getInventory();

    boolean isVisibleChunk(int x, int z);

    void setViewDistance(byte viewDistance);

    void onLogout();

    void sendWorldBorderUpdate();
}
