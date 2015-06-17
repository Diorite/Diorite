package org.diorite.world;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.diorite.BlockLocation;
import org.diorite.Difficulty;
import org.diorite.Diorite;
import org.diorite.GameMode;
import org.diorite.ImmutableLocation;
import org.diorite.Loc;
import org.diorite.Particle;
import org.diorite.entity.Player;
import org.diorite.material.BlockMaterialData;
import org.diorite.world.chunk.ChunkManager;
import org.diorite.world.generator.WorldGenerator;

public interface World
{
//    void submitAction(ChunkPos chunkToSync, Runnable runnable);
//
//    void submitAction(Chunk chunkToSync, Runnable runnable);
//
//    void save();
//

    /**
     * @return folder with world data (if used).
     */
    File getWorldFile();

    WorldGroup getWorldGroup();

    Random getRandom();

    Dimension getDimension();

    String getName();

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

    ImmutableLocation getSpawn();

    void setSpawn(Loc spawn);

    WorldGenerator getGenerator();

    void setGenerator(WorldGenerator generator);

    Block getBlock(int x, int y, int z);

    int getHighestBlockY(int x, int z);

    Block getHighestBlock(int x, int z);

    void setBlock(int x, int y, int z, BlockMaterialData material);

    void setBlock(BlockLocation location, BlockMaterialData material);

    int getMaxHeight();

    long getTime();

    void setTime(long time);

    void showParticle(Particle particle, boolean isLongDistance, int x, int y, int z, int offsetX, int offsetY, int offsetZ, int particleData, int particleCount, int... data);

    default Collection<Player> getPlayersInWorld()
    {
        // TODO: improve
        final Collection<Player> temp = new ArrayList<>(Diorite.getServer().getOnlinePlayers().size());
        Diorite.getServer().getOnlinePlayers().forEach(player -> {
            if (player.getWorld().equals(this))
            {
                temp.add(player);
            }
        });
        return temp;
    }

    Biome getBiome(int x, int y, int z);

    void setBiome(int x, int y, int z, Biome biome); // y is ignored, added for future possible changes.

    boolean isAutoSave();

    void setAutoSave(boolean value);

    void save();

    void save(boolean async);
}
