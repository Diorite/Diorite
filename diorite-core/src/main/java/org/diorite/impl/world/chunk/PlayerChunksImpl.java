/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.Tickable;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundChunkUnload;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundMapChunk;
import org.diorite.impl.entity.IPlayer;
import org.diorite.impl.world.chunk.ChunkManagerImpl.ChunkLock;
import org.diorite.utils.math.geometry.LookupShape;
import org.diorite.utils.collections.sets.ConcurrentSet;
import org.diorite.utils.math.endian.BigEndianUtils;
import org.diorite.world.chunk.ChunkPos;

import it.unimi.dsi.fastutil.longs.LongCollection;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;

public class PlayerChunksImpl implements Tickable
{
    public static final int CHUNK_BULK_SIZE = 4;

    private final IPlayer player;
    private final LongSet visibleChunks = new LongOpenHashSet(400);
    private final ChunkLock chunkLock;
    private       boolean   logout;
    private       ChunkPos  lastUpdate;
    private       byte      lastUpdateR;
    private long lastUnload = System.currentTimeMillis();

    public PlayerChunksImpl(final IPlayer player)
    {
        this.player = player;
        this.chunkLock = player.getWorld().createLock(player.getName());
    }

    public byte getRenderDistance()
    {
        return this.player.getRenderDistance();
    }

    public boolean isVisible(final int x, final int z)
    {
        return this.visibleChunks.contains(BigEndianUtils.toLong(x, z));
    }

    public byte getViewDistance()
    {
        return this.player.getViewDistance();
    }

    public IPlayer getPlayer()
    {
        return this.player;
    }

    public void logout()
    {
        this.logout = true;
        for (final LongIterator it = this.visibleChunks.iterator(); it.hasNext(); )
        {
            final long key = it.next();
            this.chunkLock.release(key);
        }
        this.visibleChunks.clear();
    }

    private void checkOld()
    {
        final long curr = System.currentTimeMillis();
        if ((curr - this.lastUnload) < TimeUnit.SECONDS.toMillis(5))
        {
            return;
        }
        this.lastUnload = curr;
        final byte render = this.getRenderDistance();

        for (final LongIterator it = this.visibleChunks.iterator(); it.hasNext(); )
        {
            final long key = it.next();
            final ChunkPos chunkPos = ChunkPos.fromLong(key);
            if (chunkPos.isInAABB(this.lastUpdate.add(- render, - render), this.lastUpdate.add(render, render)))
            {
                continue;
            }
            it.remove();
            this.chunkLock.release(key);
            this.player.getNetworkManager().sendPacket(new PacketPlayClientboundChunkUnload(chunkPos));
        }
    }

    private void continueUpdate()
    {
        final byte render = this.getRenderDistance();
        final byte view = this.getViewDistance();
        if ((this.lastUpdateR >= view) || (this.lastUpdateR >= render))
        {
            return;
        }
        final LongCollection oldChunks = new LongOpenHashSet(this.visibleChunks);
        final Collection<ChunkImpl> chunksToSent = new ConcurrentSet<>();
        final int r = this.lastUpdateR++;
        final ChunkManagerImpl impl = this.player.getWorld().getChunkManager();

        final int cx = this.lastUpdate.getX();
        final int cz = this.lastUpdate.getZ();

        {
            final Deque<ChunkImpl> toProcess = this.toProcess;
            ChunkImpl chunk;
            while ((chunk = toProcess.poll()) != null)
            {
                this.processChunk(chunk, cx, cz, view, impl, oldChunks, chunksToSent);
            }
        }

        if (r == 0)
        {
            this.processChunk(cx, cz, cx, cz, view, impl, oldChunks, chunksToSent);
        }
        else
        {
            for (int x = - r; x <= r; x++)
            {
                if ((x == r) || (x == - r))
                {
                    for (int z = - r; z <= r; z++)
                    {
                        this.processChunk(cx + x, cz + z, cx, cz, view, impl, oldChunks, chunksToSent);
                    }
                }
                this.processChunk(cx + x, cz + r, cx, cz, view, impl, oldChunks, chunksToSent);
                this.processChunk(cx + x, cz - r, cx, cz, view, impl, oldChunks, chunksToSent);
            }
        }

        if (chunksToSent.isEmpty() /*&& oldChunks.isEmpty()*/)
        {
            return;
        }

        final PacketPlayClientboundMapChunk[] packets = new PacketPlayClientboundMapChunk[chunksToSent.size()];
        int i = 0;
        for (final ChunkImpl chunk : chunksToSent)
        {
            packets[i++] = new PacketPlayClientboundMapChunk(true, chunk);
        }
        this.player.getNetworkManager().sendPackets(packets);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("player", this.player).toString();
    }

    public void reRun(final ChunkPos center)
    {
        this.lastUpdateR = 0;
        this.lastUpdate = center;
        this.continueUpdate();
        this.checkOld();
    }

    @Override
    public void doTick(final int tps)
    {
        if (this.logout)
        {
            return;
        }
        final ChunkPos center = this.player.getLocation().getChunkPos();
        if (center.equals(this.lastUpdate))
        {
            this.continueUpdate();
            return;
        }
        this.reRun(center);
    }

    private final Deque<ChunkImpl> toProcess = new ConcurrentLinkedDeque<>();

    private void processChunk(final ChunkImpl chunk, final int centerX, final int centerZ, final int radius, final ChunkManagerImpl impl, final LongCollection oldChunks, final Collection<ChunkImpl> chunksToSent)
    {
        final int cx = chunk.getX();
        final int cz = chunk.getZ();
        if (! LookupShape.RECTANGLE.isNotOutside(centerX, 0, centerZ, radius, cx, 0, cz))
        {
            if (chunk.isLoaded() && ! impl.isChunkInUse(cx, cz))
            {
                chunk.unload();
            }
            return;
        }
        if (chunk.isLoaded())
        {
            impl.populateChunk(cx, cz, false);
            final long key = BigEndianUtils.toLong(cx, cz);
            if (this.visibleChunks.contains(key))
            {
                oldChunks.remove(key);
            }
            else
            {
                this.visibleChunks.add(key);
                this.chunkLock.acquire(key);
                chunksToSent.add(chunk);
            }
        }
        else
        {
            impl.loadChunkAsync(cx, cz, true, (loadedChunk, s) -> {
                if (s && (loadedChunk != null))
                {
                    this.toProcess.offer(loadedChunk);
                }
            });
        }
    }

    private void processChunk(final int cx, final int cz, final int centerX, final int centerZ, final int radius, final ChunkManagerImpl impl, final LongCollection oldChunks, final Collection<ChunkImpl> chunksToSent)
    {
        if (! LookupShape.RECTANGLE.isNotOutside(centerX, 0, centerZ, radius, cx, 0, cz))
        {
            return;
        }
        if (impl.isChunkLoaded(cx, cz))
        {
            impl.populateChunk(cx, cz, false);
            final long key = BigEndianUtils.toLong(cx, cz);
            if (this.visibleChunks.contains(key))
            {
                oldChunks.remove(key);
            }
            else
            {
                this.visibleChunks.add(key);
                this.chunkLock.acquire(key);
                chunksToSent.add(impl.getChunk(cx, cz));
            }
        }
        else
        {
            // TODO: fix chunk load event.
            impl.loadChunkAsync(cx, cz, true, (chunk, s) -> {
                if (s && (chunk != null))
                {
                    this.toProcess.offer(chunk);
                }
            });
        }
    }
}
