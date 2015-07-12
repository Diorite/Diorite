package org.diorite.impl.world.chunk;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.Tickable;
import org.diorite.impl.world.WorldImpl;
import org.diorite.impl.world.generator.ChunkBuilderImpl;
import org.diorite.impl.world.io.ChunkIoService;
import org.diorite.event.EventType;
import org.diorite.event.chunk.ChunkGenerateEvent;
import org.diorite.event.chunk.ChunkLoadEvent;
import org.diorite.event.chunk.ChunkPopulateEvent;
import org.diorite.utils.math.pack.IntsToLong;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkManager;
import org.diorite.world.chunk.ChunkPos;
import org.diorite.world.generator.WorldGenerator;
import org.diorite.world.generator.maplayer.MapLayer;

public class ChunkManagerImpl implements ChunkManager, Tickable
{

    /**
     * The world this ChunkManager is managing.
     */
    private final WorldImpl world;

    /**
     * The chunk I/O service used to read chunks from the disk and write them to
     * the disk.
     */
    private final ChunkIoService service;

    /**
     * The chunk generator used to generate new chunks.
     */
    private final WorldGenerator generator;

    /**
     * The biome maps used to fill chunks biome grid and terrain generation.
     */
    private final MapLayer[] biomeGrid;

    /**
     * A map of chunks currently loaded in memory.
     */
    private final ConcurrentMap<Long, ChunkImpl> chunks = new ConcurrentHashMap<>(1000, .25f, 8);

    /**
     * A map of chunks which are being kept loaded by players or other factors.
     */
    private final ConcurrentMap<Long, Set<ChunkLock>> locks = new ConcurrentHashMap<>(1000, .25f, 8);

    public ChunkManagerImpl(final WorldImpl world, final ChunkIoService service, final WorldGenerator generator)
    {
        this.world = world;
        this.service = service;
        this.generator = generator;
        this.biomeGrid = MapLayer.initialize(world.getSeed(), world.getDimension(), world.getWorldType());
    }

    /**
     * Get the chunk generator.
     */
    public WorldGenerator getGenerator()
    {
        return this.generator;
    }

    public ChunkIoService getService()
    {
        return this.service;
    }

    public MapLayer[] getBiomeGrid()
    {
        return this.biomeGrid;
    }

    public WorldImpl getWorld()
    {
        return this.world;
    }

    @Override
    public ChunkImpl getChunk(final ChunkPos pos)
    {
        return this.getChunk(pos.getX(), pos.getZ());
    }

    /**
     * Gets a chunk object representing the specified coordinates, which might
     * not yet be loaded.
     *
     * @param x The X coordinate.
     * @param z The Z coordinate.
     *
     * @return The chunk.
     */
    @Override
    public ChunkImpl getChunk(final int x, final int z)
    {
        final Long key = IntsToLong.pack(x, z);
        if (this.chunks.containsKey(key))
        {
            return this.chunks.get(key);
        }
        else
        {
            // only create chunk if it's not in the map already
            final ChunkImpl chunk = new ChunkImpl(new ChunkPos(x, z, this.world));
            final ChunkImpl prev = this.chunks.putIfAbsent(key, chunk);
            // if it was created in the intervening time, the earlier one wins
            return (prev == null) ? chunk : prev;
        }
    }

    /**
     * Checks if the Chunk at the specified coordinates is loaded.
     *
     * @param x The X coordinate.
     * @param z The Z coordinate.
     *
     * @return true if the chunk is loaded, otherwise false.
     */
    @Override
    public boolean isChunkLoaded(final int x, final int z)
    {
        final Long key = IntsToLong.pack(x, z);
        final ChunkImpl chunk = this.chunks.get(key);
        return (chunk != null) && chunk.isLoaded();
    }

    /**
     * Check whether a chunk has locks on it preventing it from being unloaded.
     *
     * @param x The X coordinate.
     * @param z The Z coordinate.
     *
     * @return Whether the chunk is in use.
     */
    @Override
    public boolean isChunkInUse(final int x, final int z)
    {
        final Long key = IntsToLong.pack(x, z);
        final Set<ChunkLock> lockSet = this.locks.get(key);
        return (lockSet != null) && ! lockSet.isEmpty();
    }

    /**
     * Call the ChunkIoService to load a chunk, optionally generating the chunk.
     *
     * @param x        The X coordinate of the chunk to load.
     * @param z        The Y coordinate of the chunk to load.
     * @param generate Whether to generate the chunk if needed.
     *
     * @return True on success, false on failure.
     */
    @Override
    public boolean loadChunk(final int x, final int z, final boolean generate)
    {
        final ChunkLoadEvent loadEvt = new ChunkLoadEvent(new ChunkPos(x, z, this.world));
        EventType.callEvent(loadEvt);
        if (loadEvt.isCancelled())
        {
            return false;
        }
        final ChunkImpl chunk = (ChunkImpl) loadEvt.getLoadedChunk();

        if (chunk == null)
        {
            throw new NullPointerException("Loaded null chunk from: " + x + ", " + z);
        }
        // stop here if we can't generate
        if (! generate || ! loadEvt.isNeedBeGenerated())
        {
            return false;
        }

        // get generating
        final ChunkGenerateEvent genEvt = new ChunkGenerateEvent(chunk);
        EventType.callEvent(genEvt);
        return ! genEvt.isCancelled();
    }

    /**
     * Unload chunks with no locks on them.
     */
    @Override
    public void unloadOldChunks()
    {
        for (final Map.Entry<Long, ChunkImpl> entry : this.chunks.entrySet())
        {
            final Set<ChunkLock> lockSet = this.locks.get(entry.getKey());
            if ((lockSet == null) || lockSet.isEmpty())
            {
                if (! entry.getValue().unload(true, true))
                {
                    System.err.println("[ChunkIO] Failed to unload chunk " + this.world.getName() + ":" + entry.getKey());
                }
            }
            // cannot remove old chunks from cache - Block and BlockState keep references.
            // they must either be changed to look up the chunk again all the time, or this code left out.
            /*if (!entry.getValue().isLoaded()) {
                chunks.entrySet().remove(entry);
                locks.remove(entry.getKey());
            }*/
        }
    }

    /**
     * Populate a single chunk if needed.
     */
    @Override
    public void populateChunk(final int x, final int z, final boolean force)
    {
        final ChunkImpl chunk = this.getChunk(x, z);
        final ChunkPopulateEvent popEvt = new ChunkPopulateEvent(chunk, force);
        EventType.callEvent(popEvt);
    }

    /**
     * Force a chunk to be populated by loading the chunks in an area around it. Used when streaming chunks to players
     * so that they do not have to watch chunks being populated.
     *
     * @param x The X coordinate.
     * @param z The Z coordinate.
     */
    @Override
    public void forcePopulation(final int x, final int z)
    {
        try
        {
            final ChunkImpl chunk = this.getChunk(x, z);
            final ChunkPopulateEvent popEvt = new ChunkPopulateEvent(chunk, true);
            EventType.callEvent(popEvt);
        } catch (final Throwable e)
        {
            System.err.println("[ChunkIO] Error while populating chunk (" + x + "," + z + ")");
            e.printStackTrace();
        }
    }

    /**
     * Initialize a single chunk from the chunk generator.
     */
    @Override
    public void generateChunk(final Chunk chunk, final int x, final int z)
    {
        final ChunkPos pos = new ChunkPos(x, z, this.world);
        this.generator.generate(this.generator.generateBiomes(new ChunkBuilderImpl(this.biomeGrid), pos), pos).init(chunk);
    }

    /**
     * Forces generation of the given chunk.
     *
     * @param x The X coordinate.
     * @param z The Z coordinate.
     *
     * @return Whether the chunk was successfully regenerated.
     */
    @Override
    public boolean forceRegeneration(final int x, final int z)
    {
        final ChunkImpl chunk = this.getChunk(x, z);

        if ((chunk == null) || ! chunk.unload(false, false))
        {
            return false;
        }
        chunk.setPopulated(false);
        try
        {
            final ChunkGenerateEvent genEvt = new ChunkGenerateEvent(chunk);
            EventType.callEvent(genEvt);
            if (genEvt.isCancelled())
            {
                return false;
            }
            final ChunkPopulateEvent popEvt = new ChunkPopulateEvent(chunk, false); // should this be forced?
            EventType.callEvent(popEvt);
            if (popEvt.isCancelled())
            {
                return false;
            }
        } catch (final Throwable e)
        {
            System.err.println("[ChunkIO] Error while regenerating chunk (" + x + "," + z + ")");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Gets a list of loaded chunks.
     *
     * @return The currently loaded chunks.
     */
    @Override
    public List<ChunkImpl> getLoadedChunks()
    {
        return this.chunks.values().stream().filter(ChunkImpl::isLoaded).collect(Collectors.toList());
    }

    /**
     * Performs the save for the given chunk using the storage provider.
     *
     * @param chunk The chunk to save.
     */
    @Override
    public boolean save(final Chunk chunk)
    {
        if (chunk.isLoaded())
        {
            try
            {
                this.service.write((ChunkImpl) chunk);
                return true;
            } catch (final IOException e)
            {
                System.err.println("[ChunkIO] Error while saving " + chunk);
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public int[] getBiomeGridAtLowerRes(final int x, final int z, final int sizeX, final int sizeZ)
    {
        return this.biomeGrid[1].generateValues(x, z, sizeX, sizeZ);
    }

    @Override
    public void doTick(final int tps)
    {
        this.chunks.values().stream().filter(ChunkImpl::isLoaded).forEach(c -> {
            c.getTileEntities().forEachValue(t -> {
                t.doTick(tps);
                return true;
            });
        });
    }

    /**
     * Look up the set of locks on a given chunk.
     *
     * @param key The chunk key.
     *
     * @return The set of locks for that chunk.
     */
    private Collection<ChunkLock> getLockSet(final Long key)
    {
        if (this.locks.containsKey(key))
        {
            return this.locks.get(key);
        }
        else
        {
            // only create chunk if it's not in the map already
            final Set<ChunkLock> set = new HashSet<>(5);
            final Set<ChunkLock> prev = this.locks.putIfAbsent(key, set);
            // if it was created in the intervening time, the earlier one wins
            return (prev == null) ? set : prev;
        }
    }

    /**
     * A group of locks on chunks to prevent them from being unloaded while in use.
     */
    public static class ChunkLock implements Iterable<Long>
    {
        private final ChunkManagerImpl cm;
        private final String           desc;
        private final Collection<Long> keys = new HashSet<>(5);

        public ChunkLock(final ChunkManagerImpl cm, final String desc)
        {
            this.cm = cm;
            this.desc = desc;
        }

        public void acquire(final Long key)
        {
            if (this.keys.contains(key))
            {
                return;
            }
            this.keys.add(key);
            this.cm.getLockSet(key).add(this);
        }

        public void release(final Long key)
        {
            if (! this.keys.contains(key))
            {
                return;
            }
            this.keys.remove(key);
            this.cm.getLockSet(key).remove(this);
        }

        public void clear()
        {
            for (final Long key : this.keys)
            {
                this.cm.getLockSet(key).remove(this);
            }
            this.keys.clear();
        }

        @Override
        public Iterator<Long> iterator()
        {
            return this.keys.iterator();
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("cm", this.cm).append("desc", this.desc).append("keys", this.keys).toString();
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("world", this.world).append("generator", this.generator).toString();
    }
}
