package org.diorite.world;

import java.io.File;
import java.util.Set;

public interface WorldGroup
{
    Set<? extends World> getWorlds();

    String getName();

    File getDataFolder();

    File getPlayerDataFolder();
}
