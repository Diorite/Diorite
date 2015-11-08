/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.impl.world;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.IntStream;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.Tickable;
import org.diorite.impl.entity.EntityImpl;
import org.diorite.impl.entity.PlayerImpl;
import org.diorite.impl.entity.tracker.BaseTracker;
import org.diorite.impl.entity.tracker.EntityTrackers;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.impl.world.chunk.ChunkManagerImpl;
import org.diorite.impl.world.chunk.ChunkManagerImpl.ChunkLock;
import org.diorite.impl.world.io.ChunkIOService;
import org.diorite.impl.world.io.requests.Request;
import org.diorite.BlockLocation;
import org.diorite.Difficulty;
import org.diorite.GameMode;
import org.diorite.ImmutableLocation;
import org.diorite.Loc;
import org.diorite.Location;
import org.diorite.Particle;
import org.diorite.cfg.WorldsConfig.WorldConfig;
import org.diorite.entity.Player;
import org.diorite.material.BlockMaterialData;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.utils.math.DioriteMathUtils;
import org.diorite.utils.math.DioriteRandom;
import org.diorite.utils.math.DioriteRandomUtils;
import org.diorite.utils.math.endian.BigEndianUtils;
import org.diorite.world.Biome;
import org.diorite.world.Block;
import org.diorite.world.Dimension;
import org.diorite.world.HardcoreSettings;
import org.diorite.world.World;
import org.diorite.world.WorldType;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkPos;
import org.diorite.world.generator.WorldGenerator;
import org.diorite.world.generator.WorldGenerators;

import gnu.trove.TLongCollection;
import gnu.trove.set.hash.TLongHashSet;

public class WorldImpl implements World, Tickable
{
    private static final int CHUNK_FLAG            = (Chunk.CHUNK_SIZE - 1);
    public static final  int DEFAULT_AUTOSAVE_TIME = 20 * 60 * 5; // 5 min, TODO: load it from config

    protected final String           name;
    protected final WorldGroupImpl   worldGroup;
    protected final ChunkManagerImpl chunkManager;
    protected final Dimension        dimension;
    protected final WorldType        worldType;
    protected final EntityTrackers   entityTrackers;
    protected     boolean          vanillaCompatible = false;
    protected     Difficulty       difficulty        = Difficulty.NORMAL;
    protected     HardcoreSettings hardcore          = new HardcoreSettings(false);
    protected     GameMode         defaultGameMode   = GameMode.SURVIVAL;
    protected     int              maxHeight         = Chunk.CHUNK_FULL_HEIGHT - 1;
    protected     byte             forceLoadedRadius = 5;
    private final TLongCollection  activeChunks      = new TLongHashSet(1000);
    protected       long           seed;
    protected       boolean        raining;
    protected       boolean        thundering;
    protected       int            clearWeatherTime;
    protected       int            rainTime;
    protected       int            thunderTime;
    protected       Loc            spawn;
    protected       WorldGenerator generator;
    protected       long           time;
    protected final ChunkLock      spawnLock;
    protected       boolean       noUpdateMode = true;
    protected final DioriteRandom random       = DioriteRandomUtils.newRandom();
    protected       int           saveTimer    = DEFAULT_AUTOSAVE_TIME;
    protected       boolean       autosave     = true;


    // TODO: world border impl
    // TODO: add some method allowing to set multiple blocks without calling getChunk so often

    public WorldImpl(final ChunkIOService chunkIO, final String name, final WorldGroupImpl group, final Dimension dimension, final WorldType worldType, final String generator, final Map<String, Object> generatorOptions)
    {
        this.name = name;
        this.worldGroup = group;
        this.dimension = dimension;
        this.worldType = worldType;
        this.generator = WorldGenerators.getGenerator(generator, this, generatorOptions);
        this.chunkManager = new ChunkManagerImpl(this, chunkIO, WorldGenerators.getGenerator(generator, this, generatorOptions));

        this.spawnLock = this.createLock("spawn loader");
        this.entityTrackers = new EntityTrackers(this);

        chunkIO.start(this);
    }

    public WorldImpl(final ChunkIOService chunkIO, final String name, final WorldGroupImpl group, final Dimension dimension, final WorldType worldType, final String generator)
    {
        this(chunkIO, name, group, dimension, worldType, generator, null);
    }

    public EntityTrackers getEntityTrackers()
    {
        return this.entityTrackers;
    }

    static void forChunksParallel(final int r, final ChunkPos center, final Consumer<ChunkPos> action)
    {
        if (r == 0)
        {
            action.accept(center);
            return;
        }
        IntStream.rangeClosed(- r, r).parallel().forEach(x -> {
            if ((x == r) || (x == - r))
            {
                IntStream.rangeClosed(- r, r).parallel().forEach(z -> action.accept(center.add(x, z)));
                return;
            }
            action.accept(center.add(x, r));
            action.accept(center.add(x, - r));
        });
    }

    private static class LoadInfo
    {
        private final AtomicInteger loadedChunks = new AtomicInteger();
        private       long          lastTime     = System.currentTimeMillis();

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("loadedChunks", this.loadedChunks.get()).append("lastTime", this.lastTime).toString();
        }
    }

    public ChunkLock createLock(final String desc)
    {
        return new ChunkLock(this.chunkManager, this.name + ": " + desc);
    }

    @SuppressWarnings("MagicNumber")
    public void initSpawn()
    {
        this.spawn = this.generator.getSpawnLocation();
        if (this.spawn == null)
        {
            int spawnX = DioriteRandomUtils.getRandInt(- 64, 64);
            int spawnZ = DioriteRandomUtils.getRandInt(- 64, 64);
            final ChunkImpl chunk = this.getChunkAt(spawnX >> 4, spawnZ >> 4);
            chunk.load(true);
            final int spawnY = this.getHighestBlockY(spawnX, spawnZ);
            for (int tries = 0; (tries < 1000) && ! this.generator.canSpawn(spawnX, spawnY, spawnZ); ++ tries)
            {
                spawnX += DioriteRandomUtils.getRandInt(- 64, 64);
                spawnZ += DioriteRandomUtils.getRandInt(- 64, 64);
            }
            this.spawn = new ImmutableLocation(spawnX, spawnY, spawnZ);
        }
    }

    public synchronized void loadBase(final int chunkRadius, final BlockLocation center)
    {
        final int toLoad = DioriteMathUtils.square((chunkRadius << 1) + 1);
        System.out.println("[WorldLoader] Loading " + toLoad + " spawn chunks for world: " + this.name);
        final LoadInfo info = new LoadInfo();
        this.spawnLock.clear();
        if (chunkRadius > 0)
        {
//            final CountDownLatch latch = new CountDownLatch(toLoad);
//            final ForkJoinPool pool = new ForkJoinPool();
            int i = toLoad;
            final int sx = center.getX() - chunkRadius;
            final int ex = center.getX() + chunkRadius;
            final int sz = center.getZ() - chunkRadius;
            final int ez = center.getZ() + chunkRadius;
            for (int x = sx; x <= ex; x++)
            {
                for (int z = sz; z <= ez; z++)
                {
//                    final int cx = x;
//                    final int cz = z;
//                    pool.submit(() -> {
                    final ChunkPos pos = new ChunkPos(x, z, this);
                    this.loadChunk(pos);
                    this.spawnLock.acquire(pos.asLong());
                    final int chunkNum = info.loadedChunks.incrementAndGet();
                    if ((chunkNum % 10) == 0)
                    {
                        final long cur = System.currentTimeMillis();
                        if ((cur - info.lastTime) >= TimeUnit.SECONDS.toMillis(1))
                        {
                            //noinspection HardcodedFileSeparator
                            info.lastTime = cur;
                            System.out.println("[ChunkLoader][" + this.name + "] Chunk: " + chunkNum + "/" + toLoad + ", (" + i + " left)");
                        }
                    }
                    i--;
//                        latch.countDown();
//                    });
                }
            }
//            try
//            {
//                latch.await();
//            } catch (final InterruptedException e)
//            {
//                throw new RuntimeException("World loading was interrupted!", e);
//            }
            //noinspection HardcodedFileSeparator
            info.lastTime = System.currentTimeMillis();
        }
        System.out.println("[WorldLoader] Loaded " + info.loadedChunks.get() + " spawn chunks for world: " + this.name);
    }

    public NbtTagCompound writeTo(final NbtTagCompound tag)
    {
        tag.setString("LevelName", this.name);
        if (this.generator == null)
        {
            this.generator = WorldGenerators.getGenerator("diorite:default", this, null);
            assert this.generator != null;
        }
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
        this.vanillaCompatible = cfg.isVanillaCompatible();

        this.hardcore = new HardcoreSettings(cfg.isHardcore(), cfg.getHardcoreAction());
        this.forceLoadedRadius = cfg.getForceLoadedRadius();
        this.spawn = new Location(cfg.getSpawnX(), cfg.getSpawnY(), cfg.getSpawnZ(), cfg.getSpawnYaw(), cfg.getSpawnPitch(), this);
    }

    public WorldType getWorldType()
    {
        return this.worldType;
    }

    @Override
    public boolean isVanillaCompatible()
    {
        return this.vanillaCompatible;
    }

    @Override
    public void loadChunk(final Chunk chunk)
    {
        chunk.load();
    }

    @Override
    public void loadChunk(final int x, final int z)
    {
        this.getChunkAt(x, z).load();
    }

    @Override
    public void loadChunk(final ChunkPos pos)
    {
        this.getChunkAt(pos.getX(), pos.getZ()).load();
    }

    @Override
    public boolean loadChunk(final int x, final int z, final boolean generate)
    {
        return this.getChunkAt(x, z).load(generate);
    }

    @Override
    public boolean unloadChunk(final Chunk chunk)
    {
        return chunk.unload();
    }

    @Override
    public boolean unloadChunk(final int x, final int z)
    {
        return this.unloadChunk(x, z, true);
    }

    @Override
    public boolean unloadChunk(final int x, final int z, final boolean save)
    {
        return this.unloadChunk(x, z, save, true);
    }

    @Override
    public boolean unloadChunk(final int x, final int z, final boolean save, final boolean safe)
    {
        return ! this.isChunkLoaded(x, z) || this.getChunkAt(x, z).unload(save, safe);
    }

    @Override
    public boolean regenerateChunk(final int x, final int z)
    {
        if (! this.chunkManager.forceRegeneration(x, z))
        {
            return false;
        }
        this.refreshChunk(x, z);
        return true;
    }

    @Override
    public boolean refreshChunk(final int x, final int z)
    {
//        if (! this.isChunkLoaded(x, z))
//        {
//            return false;
//        }

//        final long key = IntsToLong.pack(x, z);
//        final boolean result = false;

        // TODO: re-send chunk

        return false;
    }


    @Override
    public boolean isChunkLoaded(final Chunk chunk)
    {
        return chunk.isLoaded();
    }

    @Override
    public boolean isChunkLoaded(final int x, final int z)
    {
        return this.chunkManager.isChunkLoaded(x, z);
    }

    @Override
    public boolean isChunkInUse(final int x, final int z)
    {
        return this.chunkManager.isChunkInUse(x, z);
    }

    @Override
    public File getWorldFile()
    {
        return this.chunkManager.getService().getWorldDataFolder();
    }

    @Override
    public WorldGroupImpl getWorldGroup()
    {
        return this.worldGroup;
    }

    @Override
    public DioriteRandom getRandom()
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
    public ChunkImpl getChunkAt(final int x, final int z)
    {
        return this.chunkManager.getChunk(x, z);
    }

    @Override
    public ChunkImpl getChunkAt(final ChunkPos pos)
    {
        return this.chunkManager.getChunk(pos.getX(), pos.getZ());
    }

    @Override
    public Block getBlock(final int x, final int y, final int z)
    {
        if ((y >= Chunk.CHUNK_FULL_HEIGHT) || (y < 0))
        {
            throw new IllegalArgumentException("Y must be in range 0-" + (Chunk.CHUNK_FULL_HEIGHT - 1));
        }
        return this.getChunkAt(x >> 4, z >> 4).getBlock((x & CHUNK_FLAG), y, (z & CHUNK_FLAG));
    }

    @Override
    public int getHighestBlockY(final int x, final int z)
    {
        return this.getChunkAt(x >> 4, z >> 4).getHighestBlockY(x & CHUNK_FLAG, z & CHUNK_FLAG);
    }

    @Override
    public Block getHighestBlock(final int x, final int z)
    {
        return this.getChunkAt(x >> 4, z >> 4).getHighestBlock(x & CHUNK_FLAG, z & CHUNK_FLAG);
    }

    @Override
    public void setBlock(final int x, final int y, final int z, final BlockMaterialData material)
    {
        if (y > this.maxHeight)
        {
            return;
        }
        this.getChunkAt(x >> 4, z >> 4).setBlock((x & CHUNK_FLAG), y, (z & CHUNK_FLAG), material.ordinal(), material.getType());
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

    @Override
    public Biome getBiome(final int x, final int y, final int z) // y is ignored, added for future possible changes.
    {
        return this.getChunkAt(x >> 4, z >> 4).getBiome(x & CHUNK_FLAG, y, z & CHUNK_FLAG);
    }

    @Override
    public void setBiome(final int x, final int y, final int z, final Biome biome) // y is ignored, added for future possible changes.
    {
        this.getChunkAt(x >> 4, z >> 4).setBiome(x & CHUNK_FLAG, y, z & CHUNK_FLAG, biome);
    }

    @Override
    public boolean hasSkyLight()
    {
        return this.dimension.hasSkyLight();
    }

    @Override
    public boolean isNoUpdateMode()
    {
        return this.noUpdateMode;
    }

    @Override
    public void setNoUpdateMode(final boolean noUpdateMode)
    {
        this.noUpdateMode = noUpdateMode;
    }

    @Override
    public byte getForceLoadedRadius()
    {
        return this.forceLoadedRadius;
    }

    @Override
    public void setForceLoadedRadius(final byte forceLoadedRadius)
    {
        this.forceLoadedRadius = forceLoadedRadius;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).toString();
    }

    @SuppressWarnings("ObjectEquality")
    @Override
    public void doTick(final int tps)
    {
        this.entityTrackers.doTick(tps);
        this.activeChunks.clear();
        for (final Player entity : this.getPlayersInWorld())
        {
            // build a set of chunks around each player in this world, the
            // server view distance is taken here
            final int radius = entity.getRenderDistance();
            final ImmutableLocation playerLocation = entity.getLocation();
            if (playerLocation.getWorld() == this)
            {
                final ChunkPos cp = playerLocation.getChunkPos();
                final int cx = cp.getX();
                final int cz = cp.getZ();
                for (int x = cx - radius, rx = cx + radius; x <= rx; x++)
                {
                    for (int z = cz - radius, rz = cz + radius; z <= rz; z++)
                    {
                        if (this.isChunkLoaded(cx, cz))
                        {
                            this.activeChunks.add(BigEndianUtils.toLong(x, z));
                        }
                    }
                }
            }
            this.chunkManager.doTick(tps);
        }

        if (this.saveTimer-- <= 0)
        {
            this.saveTimer = DEFAULT_AUTOSAVE_TIME;
            this.chunkManager.unloadOldChunks();
            if (this.autosave)
            {
                this.save(true);
            }
        }
    }

    @Override
    public boolean isAutoSave()
    {
        return this.autosave;
    }

    @Override
    public void setAutoSave(final boolean value)
    {
        this.autosave = value;
    }

    @Override
    public void save()
    {
        this.save(false);
    }

    @Override
    public void save(final boolean async)
    {
        //TODO use pipeline
        //TODO do it right...
        if (async) // temp code
        {
            this.chunkManager.getLoadedChunks().forEach(c -> this.chunkManager.save(c, ChunkIOService.HIGH_PRIORITY - 1));
        }
        else
        {
            int p = ChunkIOService.HIGH_PRIORITY - 1;
            final ChunkIOService io = this.chunkManager.getService();
            Request<Void> request = null;
            for (final ChunkImpl chunk : this.chunkManager.getLoadedChunks())
            {
                request = io.queueChunkSave(chunk, p--);
            }
            if (request != null)
            {
                request.await();
            }
        }
    }

    public void addEntity(final EntityImpl entity)
    {
        final BaseTracker<?> tracker;
        if (entity instanceof PlayerImpl)
        {
            tracker = this.entityTrackers.addTracked((PlayerImpl) entity);
        }
        else
        {
            tracker = this.entityTrackers.addTracked(entity);
        }
        entity.updateChunk(null, this.getChunkAt(entity.getLocation().getChunkPos()));
        entity.onSpawn(tracker);
    }

    public void removeEntity(final EntityImpl entity)
    {
        this.entityTrackers.removeTracked(entity);
        entity.remove(false);
    }
}
