package org.diorite.cfg;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.diorite.Difficulty;
import org.diorite.GameMode;
import org.diorite.world.Dimension;
import org.diorite.world.HardcoreSettings.HardcoreAction;
import org.diorite.world.WorldType;

public interface WorldsConfig
{
    File getWorldsDir();

    String getDefaultWorld();

    List<? extends WorldGroupConfig> getGroups();

    interface WorldGroupConfig
    {
        String getName();

        List<? extends WorldConfig> getWorlds();
    }

    interface WorldConfig
    {
        String getName();

        boolean isEnabled();

        GameMode getGamemode();

        boolean isForceGamemode();

        Difficulty getDifficulty();

        boolean isPvp();

        long getSeed();

        Dimension getDimension();

        WorldType getWorldType();

        String getGenerator();

        Map<String, Object> getGeneratorSettings();

        boolean isHardcore();

        HardcoreAction getHardcoreAction();

        byte getForceLoadedRadius();

        int getSpawnX();

        int getSpawnY();

        int getSpawnZ();

        float getSpawnYaw();

        float getSpawnPitch();
    }
}
