package org.diorite.world;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.diorite.cfg.WorldsConfig;
import org.diorite.entity.Player;

public interface WorldsManager
{
    WorldsConfig getConfig();

    Map<String, ? extends World> getWorldsMap();

    Map<String, ? extends WorldGroup> getGroupsMap();

    Collection<? extends World> getWorlds();

    Collection<? extends WorldGroup> getGroups();

    World getDefaultWorld();

    World getWorld(UUID uuid);

    World getWorld(String name);

    Collection<Player> getPlayersInWorld(World world);
}
