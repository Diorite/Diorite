package org.diorite.world;

import java.util.UUID;

import org.diorite.BlockLocation;
import org.diorite.Difficulty;
import org.diorite.GameMode;
import org.diorite.Location;
import org.diorite.material.BlockMaterialData;
import org.diorite.world.chunk.ChunkManager;
import org.diorite.world.generator.WorldGenerator;

public interface World
{
    String getName();

    UUID getUuid();

    ChunkManager getChunkManager();

    long getSeed();

    void setSeed(long seed);

    Difficulty getDifficulty();

    void setDifficulty(Difficulty difficulty);

    HardcoreSettings getHardcore();

    void setHardcore(HardcoreSettings hardcore);

    GameMode getDefaultGameMode();

    void setDefaultGameMode(GameMode defaultGameMode);

    boolean isRaining();

    void setRaining(boolean raining);

    boolean isThundering();

    void setThundering(boolean thundering);

    int getClearWeatherTime();

    void setClearWeatherTime(int clearWeatherTime);

    int getRainTime();

    int getThunderTime();

    Location getSpawn();

    void setSpawn(Location spawn);

    WorldGenerator getGenerator();

    void setGenerator(WorldGenerator generator);

    void setBlock(int x, int y, int z, BlockMaterialData material);

    void setBlock(BlockLocation location, BlockMaterialData material);

    int getMaxHeight();
}
