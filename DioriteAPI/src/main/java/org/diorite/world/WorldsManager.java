package org.diorite.world;

import java.util.Collection;
import java.util.UUID;

import org.diorite.entity.Player;

public interface WorldsManager
{
    Collection<World> getWorlds();

    World getDefaultWorld();

    World getWorld(UUID uuid);

    World getWorld(String name);

    Collection<Player> getPlayersInWorld(World world);
}
