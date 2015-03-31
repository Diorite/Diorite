package org.diorite.world;

import java.util.Collection;
import java.util.UUID;

public interface WorldsManager
{
    Collection<World> getWorlds();

    World getDefaultWorld();

    World getWorld(UUID uuid);

    World getWorld(String name);
}
