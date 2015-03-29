package org.diorite.world;

import java.util.UUID;

public interface WorldsManager
{
    World getDefaultWorld();

    World getWorld(UUID uuid);

    World getWorld(String name);
}
