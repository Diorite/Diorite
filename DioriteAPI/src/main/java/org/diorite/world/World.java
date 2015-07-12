package org.diorite.world;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.diorite.BlockLocation;
import org.diorite.Difficulty;
import org.diorite.Diorite;
import org.diorite.GameMode;
import org.diorite.ImmutableLocation;
import org.diorite.Loc;
import org.diorite.Particle;
import org.diorite.entity.Player;
import org.diorite.material.BlockMaterialData;
import org.diorite.utils.math.DioriteRandom;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkManager;
import org.diorite.world.chunk.ChunkPos;
import org.diorite.world.generator.WorldGenerator;

public interface World
{
    void loadChunk(Chunk chunk);

    void loadChunk(int x, int z);

    void loadChunk(ChunkPos pos);

    boolean loadChunk(int x, int z, boolean generate);

    boolean unloadChunk(Chunk chunk);

    boolean unloadChunk(int x, int z);

    boolean unloadChunk(int x, int z, boolean save);

    boolean unloadChunk(int x, int z, boolean save, boolean safe);

    boolean regenerateChunk(int x, int z);

    boolean refreshChunk(int x, int z);

    boolean isChunkLoaded(Chunk chunk);

    boolean isChunkLoaded(int x, int z);

    boolean isChunkInUse(int x, int z);

    /**
     * @return folder with world data (if used).
     */
    File getWorldFile();

    WorldGroup getWorldGroup();

    DioriteRandom getRandom();

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

    Chunk getChunkAt(int x, int z);

    Chunk getChunkAt(ChunkPos pos);

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

    boolean hasSkyLight();

    boolean isNoUpdateMode();

    void setNoUpdateMode(boolean noUpdateMode);

    byte getForceLoadedRadius();

    void setForceLoadedRadius(byte forceLoadedRadius);

    boolean isAutoSave();

    void setAutoSave(boolean value);

    void save();

    void save(boolean async);
}
