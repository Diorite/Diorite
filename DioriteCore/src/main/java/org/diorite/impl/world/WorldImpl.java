package org.diorite.impl.world;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.world.chunk.ChunkManagerImpl;
import org.diorite.BlockLocation;
import org.diorite.Difficulty;
import org.diorite.GameMode;
import org.diorite.Location;
import org.diorite.material.BlockMaterialData;
import org.diorite.world.HardcoreSettings;
import org.diorite.world.World;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkPos;
import org.diorite.world.generator.WorldGenerator;
import org.diorite.world.generator.WorldGenerators;

public class WorldImpl implements World
{
    private final String           name;
    private final UUID             uuid;
    private final ChunkManagerImpl chunkManager;
    private Difficulty       difficulty      = Difficulty.NORMAL;
    private HardcoreSettings hardcore        = new HardcoreSettings(false);
    private GameMode         defaultGameMode = GameMode.SURVIVAL;
    private int maxHeight = 255;
    private long seed;
    private boolean        raining;
    private boolean        thundering;
    private int            clearWeatherTime;
    private int            rainTime;
    private int            thunderTime;
    private Location       spawn;
    private WorldGenerator generator;

    // TODO: world border impl

    public WorldImpl(final String name, final UUID uuid, final String generator)
    {
        this.name = name;
        this.uuid = uuid;
        this.chunkManager = new ChunkManagerImpl(this);
        this.generator = WorldGenerators.getGenerator(generator, this, null);
    }

    public WorldImpl(final String name, final UUID uuid, final String generator, final String generatorOptions)
    {
        this.name = name;
        this.uuid = uuid;
        this.chunkManager = new ChunkManagerImpl(this);
        this.generator = WorldGenerators.getGenerator(generator, this, generatorOptions);
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public UUID getUuid()
    {
        return this.uuid;
    }

    @Override
    public ChunkManagerImpl getChunkManager()
    {
        return this.chunkManager;
    }

    @Override
    public int getMaxHeight()
    {
        return this.maxHeight;
    }

    public void setMaxHeight(final int maxHeight)
    {
        this.maxHeight = maxHeight;
    }

    @Override
    public long getSeed()
    {
        return this.seed;
    }

    @Override
    public void setSeed(final long seed)
    {
        this.seed = seed;
    }

    @Override
    public Difficulty getDifficulty()
    {
        return this.difficulty;
    }

    @Override
    public void setDifficulty(final Difficulty difficulty)
    {
        this.difficulty = difficulty;
    }

    @Override
    public HardcoreSettings getHardcore()
    {
        return this.hardcore;
    }

    @Override
    public void setHardcore(final HardcoreSettings hardcore)
    {
        this.hardcore = hardcore;
    }

    @Override
    public GameMode getDefaultGameMode()
    {
        return this.defaultGameMode;
    }

    @Override
    public void setDefaultGameMode(final GameMode defaultGameMode)
    {
        this.defaultGameMode = defaultGameMode;
    }

    @Override
    public boolean isRaining()
    {
        return this.raining;
    }

    @Override
    public void setRaining(final boolean raining)
    {
        this.raining = raining;
    }

    @Override
    public boolean isThundering()
    {
        return this.thundering;
    }

    @Override
    public void setThundering(final boolean thundering)
    {
        this.thundering = thundering;
    }

    @Override
    public int getClearWeatherTime()
    {
        return this.clearWeatherTime;
    }

    @Override
    public void setClearWeatherTime(final int clearWeatherTime)
    {
        this.clearWeatherTime = clearWeatherTime;
    }

    @Override
    public int getRainTime()
    {
        return this.rainTime;
    }

    public void setRainTime(final int rainTime)
    {
        this.rainTime = rainTime;
    }

    @Override
    public int getThunderTime()
    {
        return this.thunderTime;
    }

    public void setThunderTime(final int thunderTime)
    {
        this.thunderTime = thunderTime;
    }

    @Override
    public Location getSpawn()
    {
        return this.spawn;
    }

    @Override
    public void setSpawn(final Location spawn)
    {
        this.spawn = spawn;
    }

    @Override
    public WorldGenerator getGenerator()
    {
        return this.generator;
    }

    @Override
    public void setGenerator(final WorldGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public void setBlock(final int x, final int y, final int z, final BlockMaterialData material)
    {
        this.chunkManager.getChunkAt(ChunkPos.fromWorldPos(x, z, this)).setBlock((x & (Chunk.CHUNK_SIZE - 1)), y, (z & (Chunk.CHUNK_SIZE - 1)), material.getId(), material.getType());
    }

    @Override
    public void setBlock(final BlockLocation location, final BlockMaterialData material)
    {
        this.setBlock(location.getX(), location.getY(), location.getZ(), material);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).append("uuid", this.uuid).toString();
    }
}
