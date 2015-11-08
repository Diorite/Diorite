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

package org.diorite.impl.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.GameObjectImpl;
import org.diorite.impl.Tickable;
import org.diorite.impl.connection.packets.play.server.PacketPlayServer;
import org.diorite.impl.entity.meta.EntityMetadata;
import org.diorite.impl.entity.meta.entry.EntityMetadataByteEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataStringEntry;
import org.diorite.impl.entity.tracker.BaseTracker;
import org.diorite.impl.entity.tracker.Trackable;
import org.diorite.impl.world.WorldImpl;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.ImmutableLocation;
import org.diorite.entity.Entity;
import org.diorite.entity.EntityType;
import org.diorite.utils.lazy.BooleanLazyValue;
import org.diorite.utils.math.DioriteMathUtils;
import org.diorite.utils.math.geometry.BoundingBox;
import org.diorite.utils.math.geometry.EntityBoundingBox;
import org.diorite.utils.others.Resetable;
import org.diorite.world.chunk.Chunk;

public abstract class EntityImpl extends GameObjectImpl implements Entity, Tickable, Trackable
{
    protected static final double PHYSIC_GRAVITY_CONST_1 = 0.98D;
    protected static final double PHYSIC_GRAVITY_CONST_2 = 0.08D;

    private static final AtomicInteger ENTITY_ID = new AtomicInteger();

    public static int getNextEntityID()
    {
        return ENTITY_ID.getAndIncrement();
    }

    public static final int MAX_AIR_LEVEL = 300;

    /**
     * byte entry, with flags. {@link org.diorite.impl.entity.EntityImpl.BasicFlags}
     */
    protected static final byte META_KEY_BASIC_FLAGS = 0;

    /**
     * short entry, air level
     */
    protected static final byte META_KEY_AIR = 1;

    /**
     * String entry, name/name tag
     */
    protected static final byte META_KEY_NAME_TAG = 2;

    /**
     * byte/bool entry, if name tag should be visible
     */
    protected static final byte META_KEY_ALWAYS_SHOW_NAME_TAG = 3;

    /**
     * byte/bool entry, if entity should make sound.
     */
    protected static final byte META_KEY_SILENT = 4;

    /**
     * Contains mask for basic flags used in matadata at index 0
     * http://wiki.vg/Entities#Entity
     */
    public static final class BasicFlags
    {
        public static final byte ON_FIRE   = 0x01;
        public static final byte CROUCHED  = 0x01;
        public static final byte SPRINTING = 0x01;
        public static final byte ACTION    = 0x01;
        public static final byte INVISIBLE = 0x01;

        private BasicFlags()
        {
        }
    }

    protected final Set<Resetable> values = new HashSet<>(10);

    protected final    DioriteCore       core;
    protected final    WorldImpl         world;
    protected volatile Thread            lastTickThread;
    protected          EntityBoundingBox aabb;
    private            int               id;
    protected          double            x;
    protected          double            y;
    protected          double            z;
    protected          float             yaw;
    protected          float             pitch;
    protected          float             velX;
    protected          float             velY;
    protected          float             velZ;
    protected          EntityMetadata    metadata;
    protected          BaseTracker<?>    tracker;

    protected boolean aiEnabled = true; // don't do any actions if AI is disabled

    protected final BooleanLazyValue lazyOnGround = new BooleanLazyValue(this.values, () -> (this.y >= 0) && (this.y < Chunk.CHUNK_FULL_HEIGHT) && this.getLocation().toBlockLocation().getBlock().getType().isSolid()); // TODO: maybe something better?

    protected EntityImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
    {
        super(uuid);
        this.core = core;
        this.id = id;
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        this.world = (WorldImpl) location.getWorld();
        this.metadata = new EntityMetadata();
        this.initMetadata();
    }

    public void initMetadata()
    {
        this.metadata.add(new EntityMetadataByteEntry(META_KEY_BASIC_FLAGS, 0));
        this.metadata.add(new EntityMetadataByteEntry(META_KEY_AIR, MAX_AIR_LEVEL));
        this.metadata.add(new EntityMetadataByteEntry(META_KEY_SILENT, 0));
        this.metadata.add(new EntityMetadataByteEntry(META_KEY_ALWAYS_SHOW_NAME_TAG, 0));
        this.metadata.add(new EntityMetadataStringEntry(META_KEY_NAME_TAG, ""));


        // test TODO: remove
//        this.metadata.add(new EntityMetadataByteEntry(META_KEY_ALWAYS_SHOW_NAME_TAG, 1));
//        this.metadata.add(new EntityMetadataStringEntry(META_KEY_NAME_TAG, ChatColor.translateAlternateColorCodesInString("&a#&3OnlyDiorite")));

    }

    public boolean isOnGround()
    {
        return this.lazyOnGround.get();
    }

    @Override
    public int getTrackRange()
    {
        //noinspection MagicNumber temp
        return 64;
    }

    public EntityMetadata getMetadata()
    {
        return this.metadata;
    }

    @Override
    public float getVelocityX()
    {
        return this.velX;
    }

    @Override
    public float getVelocityY()
    {
        return this.velY;
    }

    @Override
    public float getVelocityZ()
    {
        return this.velZ;
    }

    public float getHeadPitch()
    {
        return 0.0F;
    }

    public ChunkImpl getChunk()
    {
        return this.world.getChunkAt(((int) this.x) >> 4, ((int) this.z) >> 4);
    }

    public boolean hasGravity()
    {
        return true;
    }

    public boolean isAiEnabled()
    {
        return this.aiEnabled;
    }

    public void setAiEnabled(final boolean aiEnabled)
    {
        this.aiEnabled = aiEnabled;
    }

    @Override
    public boolean exist()
    {
        return this.id != - 1;
    }

    public void remove(final boolean full)
    {
        if (full)
        {
            this.world.removeEntity(this);
            return;
        }
        this.id = - 1;
        this.getChunk().removeEntity(this);
    }

    @Override
    public boolean isValidSynchronizable()
    {
        return this.getChunk().getEntities().contains(this) || this.world.getChunkAt(((int) this.x) >> 4, ((int) this.z) >> 4).getEntities().contains(this);
    }

    @Override
    public Thread getLastTickThread()
    {
        return this.lastTickThread;
    }

    @Override
    public void doTick(final int tps)
    {
        this.lastTickThread = Thread.currentThread();
        this.values.forEach(Resetable::reset);
        this.aabb.setCenter(this);

        if (this.hasGravity())
        {
            this.doPhysics();
        }
        // TODO
    }

    protected void doPhysics()
    {
        final double multi = DioriteCore.getInstance().getSpeedMutli();
        if (this.velX != 0)
        {
            this.x += (this.velX * multi);
        }
        if ((this.velY != 0))
        {
            if (this.isOnGround())
            {
                this.velY = 0;
            }
            else
            {
                this.y += (this.velY * multi);
            }
        }
        if (this.velZ != 0)
        {
            this.z += (this.velZ * multi);
        }
    }

    public WorldImpl getWorld()
    {
        return this.world;
    }

    @Override
    public int getId()
    {
        return this.id;
    }

    @Override
    public double getX()
    {
        return this.x;
    }

    @Override
    public double getZ()
    {
        return this.z;
    }

    @Override
    public double getY()
    {
        return this.y;
    }

    public float getYaw()
    {
        return this.yaw;
    }

    public float getPitch()
    {
        return this.pitch;
    }

    @Override
    public ImmutableLocation getLocation()
    {
        return new ImmutableLocation(this.x, this.y, this.z, this.yaw, this.pitch, this.world);
    }

    @Override
    public <T extends Entity> Collection<? extends T> getNearbyEntities(final double x, final double y, final double z, final Class<? extends T> type)
    {
        final BoundingBox bb = this.aabb.grow(x, y, z);

        final Set<T> entities = new HashSet<>(25);

        final int cx = this.getChunk().getX(); // Chunk X location
        final int cz = this.getChunk().getZ(); // Chunk Z location


        final int chunkScanSizeX = 1 + DioriteMathUtils.ceil(x / Chunk.CHUNK_SIZE);
        final int chunkScanSizeZ = 1 + DioriteMathUtils.ceil(z / Chunk.CHUNK_SIZE);

        final int cxBeginScan = cx - chunkScanSizeX;
        final int czBeginScan = cz - chunkScanSizeZ;

        final int cxEndScan = cx + chunkScanSizeX;
        final int czEndScan = cz + chunkScanSizeZ;

        for (int i = cxBeginScan; i <= cxEndScan; i++)
        {
            for (int j = czBeginScan; j <= czEndScan; j++)
            {
                final ChunkImpl chunk = this.world.getChunkAt(i, j);
                if (! chunk.isLoaded())
                {
                    continue;
                }

                //noinspection unchecked,ObjectEquality
                chunk.getEntities().stream().filter(entity -> (entity != this) && type.isAssignableFrom(entity.getClass()) && BoundingBox.intersects(bb, entity.aabb)).forEach(e -> entities.add((T) e));
            }
        }

        return entities;
    }

    @Override
    public <T extends Entity> Collection<? extends T> getNearbyEntities(final double x, final double y, final double z, final EntityType type)
    {
        final BoundingBox bb = this.aabb.grow(x, y, z);

        final Set<T> entities = new HashSet<>(25);

        final int cx = this.getChunk().getX(); // Chunk X location
        final int cz = this.getChunk().getZ(); // Chunk Z location


        final int chunkScanSizeX = 1 + DioriteMathUtils.ceil(x / Chunk.CHUNK_SIZE);
        final int chunkScanSizeZ = 1 + DioriteMathUtils.ceil(z / Chunk.CHUNK_SIZE);

        final int cxBeginScan = cx - chunkScanSizeX;
        final int czBeginScan = cz - chunkScanSizeZ;

        final int cxEndScan = cx + chunkScanSizeX;
        final int czEndScan = cz + chunkScanSizeZ;

        for (int i = cxBeginScan; i <= cxEndScan; i++)
        {
            for (int j = czBeginScan; j <= czEndScan; j++)
            {
                final ChunkImpl chunk = this.world.getChunkAt(i, j);
                if (! chunk.isLoaded())
                {
                    continue;
                }

                //noinspection unchecked,ObjectEquality
                chunk.getEntities().stream().filter(entity -> (entity != this) && type.equals(entity.getType()) && BoundingBox.intersects(bb, entity.aabb)).forEach(e -> entities.add((T) e));
            }
        }

        return entities;
    }

    @Override
    public Collection<EntityImpl> getNearbyEntities(final double x, final double y, final double z)
    {
        final BoundingBox bb = this.aabb.grow(x, y, z);

        final Set<EntityImpl> entities = new HashSet<>(25);

        final int cx = this.getChunk().getX(); // Chunk X location
        final int cz = this.getChunk().getZ(); // Chunk Z location


        final int chunkScanSizeX = 1 + DioriteMathUtils.ceil(x / Chunk.CHUNK_SIZE);
        final int chunkScanSizeZ = 1 + DioriteMathUtils.ceil(z / Chunk.CHUNK_SIZE);

        final int cxBeginScan = cx - chunkScanSizeX;
        final int czBeginScan = cz - chunkScanSizeZ;

        final int cxEndScan = cx + chunkScanSizeX;
        final int czEndScan = cz + chunkScanSizeZ;

        for (int i = cxBeginScan; i <= cxEndScan; i++)
        {
            for (int j = czBeginScan; j <= czEndScan; j++)
            {
                final ChunkImpl chunk = this.world.getChunkAt(i, j);
                if (! chunk.isLoaded())
                {
                    continue;
                }

                //noinspection ObjectEquality
                chunk.getEntities().stream().filter(entity -> (entity != this) && BoundingBox.intersects(bb, entity.aabb)).forEach(entities::add);
            }
        }

        return entities;
    }

    @Override
    public DioriteCore getCore()
    {
        return this.core;
    }

    public void onSpawn(final BaseTracker<?> tracker)
    {
        if (this.tracker != null)
        {
            throw new IllegalArgumentException("Entity was already spawned.");
        }
        this.tracker = tracker;
    }

    public void move(final double modX, final double modY, final double modZ, final float modYaw, final float modPitch)
    {
        final ChunkImpl chunk = this.getChunk();

        this.x += modX;
        this.y += modY;
        this.z += modZ;
        this.yaw += modYaw;
        this.pitch += modPitch;

        this.updateChunk(chunk, this.getChunk());
    }

    public void setPositionAndRotation(final double newX, final double newY, final double newZ, final float newYaw, final float newPitch)
    {
        this.setPosition(newX, newY, newZ);
        this.setRotation(newYaw, newPitch);
    }

    public void setPosition(final double newX, final double newY, final double newZ)
    {
        final ChunkImpl chunk = this.getChunk();

        this.x = newX;
        this.y = newY;
        this.z = newZ;

        this.updateChunk(chunk, this.getChunk());
    }

    @SuppressWarnings("ObjectEquality")
    public void updateChunk(final ChunkImpl chunk, final ChunkImpl newChunk)
    {
        if (chunk == null)
        {
            newChunk.addEntity(this);
        }
        else if (chunk != newChunk)
        {
            synchronized (this)
            {
                chunk.removeEntity(this);
                newChunk.addEntity(this);
            }
        }
    }

    public void setRotation(final float newYaw, final float newPitch)
    {
        this.yaw = newYaw;
        this.pitch = newPitch;
    }

    /**
     * @return Packet need to spawn entity
     */
    public abstract PacketPlayServer getSpawnPacket();

    /**
     * Need return array of packet in valid order needed to spawn entity with all data. <br>
     * Like base spawn packet, potion effects, metadata, passanger etc...
     *
     * @return array of packets.
     */
    public abstract PacketPlayServer[] getSpawnPackets();

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("id", this.id).append("world", this.world).append("x", this.x).append("y", this.y).append("z", this.z).toString();
    }
}
