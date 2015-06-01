package org.diorite.impl.world;

import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.Tickable;
import org.diorite.impl.entity.EntityImpl;
import org.diorite.impl.world.chunk.ChunkGroup;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.chunk.ChunkManagerImpl;
import org.diorite.impl.world.io.ChunkIO;
import org.diorite.BlockLocation;
import org.diorite.Difficulty;
import org.diorite.GameMode;
import org.diorite.ImmutableLocation;
import org.diorite.Loc;
import org.diorite.Location;
import org.diorite.Particle;
import org.diorite.cfg.WorldsConfig.WorldConfig;
import org.diorite.entity.Entity;
import org.diorite.material.BlockMaterialData;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.world.Block;
import org.diorite.world.Dimension;
import org.diorite.world.HardcoreSettings;
import org.diorite.world.World;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkPos;
import org.diorite.world.generator.WorldGenerator;
import org.diorite.world.generator.WorldGenerators;

public class WorldImpl implements World, Tickable
{
    protected final String             name;
    protected final WorldGroupImpl     worldGroup;
    protected final ChunkManagerImpl   chunkManager;
    protected final ChunkIO<ChunkImpl> chunkIO;
    protected final Dimension          dimension;
    protected Difficulty       difficulty        = Difficulty.NORMAL;
    protected HardcoreSettings hardcore          = new HardcoreSettings(false);
    protected GameMode         defaultGameMode   = GameMode.SURVIVAL;
    protected int              maxHeight         = Chunk.CHUNK_FULL_HEIGHT - 1;
    protected byte             forceLoadedRadius = 5;
    protected long           seed;
    protected boolean        raining;
    protected boolean        thundering;
    protected int            clearWeatherTime;
    protected int            rainTime;
    protected int            thunderTime;
    protected Loc            spawn;
    protected WorldGenerator generator;
    protected long           time;
    protected       boolean noUpdateMode = true;
    protected final Random  random       = new Random();

    // TODO: world border impl
    // TODO: add some method allowing to set multiple blocks without calling getChunk so often

    public WorldImpl(final ChunkIO<ChunkImpl> chunkIO, final String name, final WorldGroupImpl group, final Dimension dimension, final String generator, final Map<String, Object> generatorOptions)
    {
        this.name = name;
        this.worldGroup = group;
        this.dimension = dimension;
        this.chunkManager = new ChunkManagerImpl(this);
        this.generator = WorldGenerators.getGenerator(generator, this, generatorOptions);
        chunkIO.setWorld(this);
        this.chunkIO = chunkIO;
    }

    public WorldImpl(final ChunkIO<ChunkImpl> chunkIO, final String name, final WorldGroupImpl group, final Dimension dimension, final String generator)
    {
        this.name = name;
        this.worldGroup = group;
        this.dimension = dimension;
        this.chunkManager = new ChunkManagerImpl(this);
        this.generator = WorldGenerators.getGenerator(generator, this, null);
        chunkIO.setWorld(this);
        this.chunkIO = chunkIO;
    }

    public NbtTagCompound writeTo(final NbtTagCompound tag)
    {
        tag.setString("LevelName", this.name);
        tag.setString("generatorName", this.generator.getName());
        tag.setString("generatorOptions", this.generator.getOptions().toString());

        tag.setInt("clearWeatherTime", this.clearWeatherTime);
        tag.setBoolean("raining", this.raining);
        tag.setInt("rainTime", this.rainTime);
        tag.setBoolean("thundering", this.thundering);
        tag.setInt("thunderTime", this.thunderTime);
        tag.setLong("Time", this.time);
        return tag;
    }

    public void loadNBT(final NbtTagCompound tag, final WorldConfig cfg)
    {
        this.clearWeatherTime = tag.getInt("clearWeatherTime", 0);
        this.defaultGameMode = cfg.getGamemode();
        this.difficulty = cfg.getDifficulty();
        this.raining = tag.getBoolean("raining", false);
        this.rainTime = tag.getInt("rainTime", 0);
        this.thundering = tag.getBoolean("thundering", false);
        this.thunderTime = tag.getInt("thunderTime", 0);
        this.seed = cfg.getSeed();
        this.random.setSeed(this.seed);
        this.time = tag.getLong("Time", 0);

        this.hardcore = new HardcoreSettings(cfg.isHardcore(), cfg.getHardcoreAction());
        this.forceLoadedRadius = cfg.getForceLoadedRadius();
        this.spawn = new Location(cfg.getSpawnX(), cfg.getSpawnY(), cfg.getSpawnZ(), cfg.getSpawnYaw(), cfg.getSpawnPitch(), this);

    }

    @Override
    public void submitAction(final ChunkPos chunkToSync, final Runnable runnable)
    {
        this.chunkManager.submitAction(chunkToSync, runnable);
    }

    @Override
    public void submitAction(final Chunk chunkToSync, final Runnable runnable)
    {
        this.chunkManager.submitAction(chunkToSync, runnable);
    }

    public ChunkGroup getChunkGroup(final ChunkPos pos)
    {
        return this.chunkManager.getChunkGroup(pos);
    }

    public void submitAction(final ChunkGroup groupToSync, final Runnable runnable)
    {
        this.chunkManager.submitAction(groupToSync, runnable);
    }

    @Override
    public void save()
    {
        System.out.println("Saving chunks for world: " + this.name);
        this.chunkManager.saveAll();
        System.out.println("Saved chunks for world: " + this.name);
    }

    @Override
    public WorldGroupImpl getWorldGroup()
    {
        return this.worldGroup;
    }

    @Override
    public Random getRandom()
    {
        return this.random;
    }

    @Override
    public Dimension getDimension()
    {
        return this.dimension;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public ChunkManagerImpl getChunkManager()
    {
        return this.chunkManager;
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
        this.random.setSeed(this.seed);
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
    public ImmutableLocation getSpawn()
    {
        return this.spawn.toImmutableLocation();
    }

    @Override
    public void setSpawn(final Loc spawn)
    {
        this.spawn = spawn.toImmutableLocation();
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
    public Block getBlock(final int x, final int y, final int z)
    {
        if (y > this.maxHeight)
        {
            return null;
        }
        return this.chunkManager.getChunkAt(ChunkPos.fromWorldPos(x, z, this), true, false).getBlock((x & (Chunk.CHUNK_SIZE - 1)), y, (z & (Chunk.CHUNK_SIZE - 1)));
    }

    @Override
    public int getHighestBlockY(final int x, final int z)
    {
        return this.chunkManager.getChunkAt(ChunkPos.fromWorldPos(x, z, this), true, false).getHighestBlockY((x & (Chunk.CHUNK_SIZE - 1)), (z & (Chunk.CHUNK_SIZE - 1)));
    }

    @Override
    public Block getHighestBlock(final int x, final int z)
    {
        return this.chunkManager.getChunkAt(ChunkPos.fromWorldPos(x, z, this), true, false).getHighestBlock((x & (Chunk.CHUNK_SIZE - 1)), (z & (Chunk.CHUNK_SIZE - 1)));
    }

    @Override
    public void setBlock(final int x, final int y, final int z, final BlockMaterialData material)
    {
        if (y > this.maxHeight)
        {
            return;
        }
        this.chunkManager.getChunkAt(ChunkPos.fromWorldPos(x, z, this), true, false).setBlock((x & (Chunk.CHUNK_SIZE - 1)), y, (z & (Chunk.CHUNK_SIZE - 1)), material.getId(), material.getType());
    }

    @Override
    public void setBlock(final BlockLocation location, final BlockMaterialData material)
    {
        this.setBlock(location.getX(), location.getY(), location.getZ(), material);
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
    public long getTime()
    {
        return this.time;
    }

    @Override
    public void setTime(final long time)
    {
        this.time = time;
    }

    @Override
    public void showParticle(final Particle particle, final boolean isLongDistance, final int x, final int y, final int z, final int offsetX, final int offsetY, final int offsetZ, final int particleData, final int particleCount, final int... data)
    {
        this.getPlayersInWorld().forEach(player -> player.showParticle(particle, isLongDistance, x, y, x, offsetX, offsetY, offsetZ, particleData, particleCount, data));
    }

    public boolean hasSkyLight()
    {
        return this.dimension.hasSkyLight();
    }

    public boolean isNoUpdateMode()
    {
        return this.noUpdateMode;
    }

    public void setNoUpdateMode(final boolean noUpdateMode)
    {
        this.noUpdateMode = noUpdateMode;
    }

    public byte getForceLoadedRadius()
    {
        return this.forceLoadedRadius;
    }

    public void setForceLoadedRadius(final byte forceLoadedRadius)
    {
        this.forceLoadedRadius = forceLoadedRadius;
    }

    public ChunkIO<ChunkImpl> getWorldFile()
    {
        return this.chunkIO;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).toString();
    }

    @Override
    public void doTick()
    {
        this.chunkManager.doTick();
    }

    public void addEntity(final EntityImpl entity)
    {
        this.getChunkGroup(entity.getLocation().getChunkPos()).addEntity(entity);
    }

    public void removeEntity(final Entity entity)
    {
        this.getChunkGroup(entity.getLocation().getChunkPos()).removeEntity(entity);
    }
}
