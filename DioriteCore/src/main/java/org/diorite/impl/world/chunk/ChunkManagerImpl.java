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

package org.diorite.impl.world.chunk;

import java.util.Collection;
import java.util.HashSet;
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
import org.diorite.impl.world.io.ChunkIOService;
import org.diorite.event.EventType;
import org.diorite.event.chunk.ChunkGenerateEvent;
import org.diorite.event.chunk.ChunkLoadEvent;
import org.diorite.event.chunk.ChunkPopulateEvent;
import org.diorite.utils.math.endian.BigEndianUtils;
import org.diorite.world.chunk.Chunk;
import org.diorite.world.chunk.ChunkManager;
import org.diorite.world.chunk.ChunkPos;
import org.diorite.world.generator.WorldGenerator;
import org.diorite.world.generator.maplayer.MapLayer;

import gnu.trove.TLongCollection;
import gnu.trove.set.hash.TLongHashSet;

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
    private final ChunkIOService service;

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

    public ChunkManagerImpl(final WorldImpl world, final ChunkIOService service, final WorldGenerator generator)
    {
        this.world = world;
        this.service = service;
        this.generator = generator;
        this.biomeGrid = MapLayer.initialize(world.getSeed(), world.getDimension(), world.getWorldType());
    }

    public WorldGenerator getGenerator()
    {
        return this.generator;
    }

    public ChunkIOService getService()
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

    @Override
    public ChunkImpl getChunk(final int x, final int z)
    {
        final Long key = BigEndianUtils.toLong(x, z);
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

    @Override
    public boolean isChunkLoaded(final int x, final int z)
    {
        final Long key = BigEndianUtils.toLong(x, z);
        final ChunkImpl chunk = this.chunks.get(key);
        return (chunk != null) && chunk.isLoaded();
    }

    @Override
    public boolean isChunkInUse(final int x, final int z)
    {
        final Long key = BigEndianUtils.toLong(x, z);
        final Set<ChunkLock> lockSet = this.locks.get(key);
        return (lockSet != null) && ! lockSet.isEmpty();
    }

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

    @Override
    public void populateChunk(final int x, final int z, final boolean force)
    {
        final ChunkImpl chunk = this.getChunk(x, z);
        final ChunkPopulateEvent popEvt = new ChunkPopulateEvent(chunk, force);
        EventType.callEvent(popEvt);
    }

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

    @Override
    public void generateChunk(final Chunk chunk, final int x, final int z)
    {
        final ChunkPos pos = new ChunkPos(x, z, this.world);
        this.generator.generate(this.generator.generateBiomes(new ChunkBuilderImpl(this.biomeGrid), pos), pos).init(chunk);
    }

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

    @Override
    public List<ChunkImpl> getLoadedChunks()
    {
        return this.chunks.values().stream().filter(ChunkImpl::isLoaded).collect(Collectors.toList());
    }

    @Override
    public boolean save(final Chunk chunk)
    {
        if (chunk.isLoaded())
        {
            this.service.queueChunkSave((ChunkImpl) chunk, ChunkIOService.MEDIUM_PRIORITY);
            return true;
        }
        return false;
    }

    @Override
    public boolean save(final Chunk chunk, final int priority)
    {
        if (chunk.isLoaded())
        {
            this.service.queueChunkSave((ChunkImpl) chunk, priority);
            return true;
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
        this.chunks.values().stream().filter(ChunkImpl::isLoaded).forEach(c -> c.getTileEntities().forEachValue(t -> {
            t.doTick(tps);
            return true;
        }));
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
    public static class ChunkLock
    {
        private final ChunkManagerImpl cm;
        private final String           desc;
        private final TLongCollection keys = new TLongHashSet(3);

        public ChunkLock(final ChunkManagerImpl cm, final String desc)
        {
            this.cm = cm;
            this.desc = desc;
        }

        public synchronized void acquire(final long key)
        {
            if (this.keys.contains(key))
            {
                return;
            }
            this.keys.add(key);
            this.cm.getLockSet(key).add(this);
        }

        public synchronized void release(final long key)
        {
            if (! this.keys.contains(key))
            {
                return;
            }
            this.keys.remove(key);
            this.cm.getLockSet(key).remove(this);
        }

        public synchronized void clear()
        {
            this.keys.forEach(key -> {
                this.cm.getLockSet(key).remove(this);
                return true;
            });
            this.keys.clear();
        }

//        public TLongIterator iterator()
//        {
//            return this.keys.iterator();
//        }

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
