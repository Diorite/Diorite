package org.diorite.world;

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
import org.diorite.world.chunk.ChunkManager;
import org.diorite.world.generator.WorldGenerator;

public interface World
{
    void save();

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

    Block getHighestBlock(int x, int z);

    void setBlock(int x, int y, int z, BlockMaterialData material);

    void setBlock(BlockLocation location, BlockMaterialData material);

    int getMaxHeight();

    long getTime();

    void setTime(long time);

    void showParticle(Particle particle, boolean isLongDistance, int x, int y, int z, int offsetX, int offsetY, int offsetZ, int particleData, int particleCount, int... data);

    default Collection<Player> getPlayersInWorld()
    {
        final Collection<Player> temp = new ArrayList<>(Diorite.getServer().getOnlinePlayers().size());
        Diorite.getServer().getOnlinePlayers().forEach(player -> {
            if (player.getWorld().equals(this))
            {
                temp.add(player);
            }
        });
        return temp;
    }
}
