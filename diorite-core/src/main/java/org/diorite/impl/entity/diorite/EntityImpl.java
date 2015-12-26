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

package org.diorite.impl.entity.diorite;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.GameObjectImpl;
import org.diorite.impl.entity.IEntity;
import org.diorite.impl.entity.meta.EntityMetadata;
import org.diorite.impl.entity.meta.entry.EntityMetadataByteEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataStringEntry;
import org.diorite.impl.entity.tracker.BaseTracker;
import org.diorite.impl.world.WorldImpl;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.ImmutableLocation;
import org.diorite.entity.Entity;
import org.diorite.entity.EntityType;
import org.diorite.utils.lazy.BooleanLazyValue;
import org.diorite.utils.math.DioriteMathUtils;
import org.diorite.utils.math.DioriteRandom;
import org.diorite.utils.math.DioriteRandomUtils;
import org.diorite.utils.math.geometry.BoundingBox;
import org.diorite.utils.math.geometry.EntityBoundingBox;
import org.diorite.utils.others.Resetable;
import org.diorite.world.chunk.Chunk;

abstract class EntityImpl extends GameObjectImpl implements IEntity
{
    static final double PHYSIC_GRAVITY_CONST_1 = 0.98D;
    static final double PHYSIC_GRAVITY_CONST_2 = 0.08D;

    /**
     * Contains mask for basic flags used in matadata at index 0
     * http://wiki.vg/Entities#Entity
     */
    static final class BasicFlags
    {
        static final byte ON_FIRE   = 0x01;
        static final byte CROUCHED  = 0x01;
        static final byte SPRINTING = 0x01;
        static final byte ACTION    = 0x01;
        static final byte INVISIBLE = 0x01;

        private BasicFlags()
        {
        }
    }

    private final Set<Resetable> values = new HashSet<>(10);

    final    DioriteCore core;
    final    WorldImpl   world;
    volatile Thread      lastTickThread;
    EntityBoundingBox aabb;
    private int id;
    double x;
    double y;
    double z;
    float  yaw;
    float  pitch;
    float  velX;
    float  velY;
    float  velZ;
    final EntityMetadata metadata;
    BaseTracker<?> tracker;

    boolean       aiEnabled = true; // don't do any actions if AI is disabled
    DioriteRandom random    = DioriteRandomUtils.getRandom();

    final BooleanLazyValue lazyOnGround = new BooleanLazyValue(this.values, () -> (this.y >= 0) && (this.y < Chunk.CHUNK_FULL_HEIGHT) && this.getLocation().toBlockLocation().getBlock().getType().isSolid()); // TODO: maybe something better?

    EntityImpl(final UUID uuid, final DioriteCore core, final int id, final ImmutableLocation location)
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

    @Override
    public void initMetadata()
    {
        this.metadata.add(new EntityMetadataByteEntry(IEntity.META_KEY_BASIC_FLAGS, 0));
        this.metadata.add(new EntityMetadataByteEntry(IEntity.META_KEY_AIR, EntityImpl.MAX_AIR_LEVEL));
        this.metadata.add(new EntityMetadataByteEntry(IEntity.META_KEY_SILENT, 0));
        this.metadata.add(new EntityMetadataByteEntry(IEntity.META_KEY_ALWAYS_SHOW_NAME_TAG, 0));
        this.metadata.add(new EntityMetadataStringEntry(IEntity.META_KEY_NAME_TAG, ""));


        // test TODO: remove
//        this.metadata.add(new EntityMetadataByteEntry(META_KEY_ALWAYS_SHOW_NAME_TAG, 1));
//        this.metadata.add(new EntityMetadataStringEntry(META_KEY_NAME_TAG, ChatColor.translateAlternateColorCodesInString("&a#&3OnlyDiorite")));

    }

    @Override
    public boolean isOnGround()
    {
        return this.lazyOnGround.get();
    }

    @Override
    public EntityMetadata getMetadata()
    {
        return this.metadata;
    }

    @Override
    public ChunkImpl getChunk()
    {
        return this.world.getChunkAt(((int) this.x) >> 4, ((int) this.z) >> 4);
    }

    @Override
    public boolean hasGravity()
    {
        return true;
    }

    @Override
    public boolean isAiEnabled()
    {
        return this.aiEnabled;
    }

    @Override
    public void setAiEnabled(final boolean aiEnabled)
    {
        this.aiEnabled = aiEnabled;
    }

    @Override
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
    public void doPhysics()
    {
        final double multi = DioriteCore.getInstance().getSpeedMutli();

        double x = 0;
        double y = 0;
        double z = 0;
        if (this.velX != 0)
        {
            x += (this.velX * multi);
        }
        if ((this.velY != 0))
        {
            if (this.isOnGround())
            {
                this.velY = 0;
                this.y = (this.getLocation().toBlockLocation().getY() + (1));
            }
            else
            {
                y = (this.velY * multi);
            }
        }
        if (this.velZ != 0)
        {
            z = (this.velZ * multi);
        }
        this.move(x, y, z);
    }

    @Override
    public WorldImpl getWorld()
    {
        return this.world;
    }

    @Override
    public void onSpawn(final BaseTracker<?> tracker)
    {
        if (this.tracker != null)
        {
            throw new IllegalArgumentException("Entity was already spawned.");
        }
        this.tracker = tracker;
    }

    @Override
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

    @Override
    public void move(final double modX, final double modY, final double modZ)
    {
        final ChunkImpl chunk = this.getChunk();

        this.x += modX;
        this.y += modY;
        this.z += modZ;

        this.updateChunk(chunk, this.getChunk());
    }

    @Override
    public void setPositionAndRotation(final double newX, final double newY, final double newZ, final float newYaw, final float newPitch)
    {
        this.setPosition(newX, newY, newZ);
        this.setRotation(newYaw, newPitch);
    }

    @Override
    public void setPosition(final double newX, final double newY, final double newZ)
    {
        final ChunkImpl chunk = this.getChunk();

        this.x = newX;
        this.y = newY;
        this.z = newZ;

        this.updateChunk(chunk, this.getChunk());
    }

    @Override
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

    @Override
    public void setRotation(final float newYaw, final float newPitch)
    {
        this.yaw = newYaw;
        this.pitch = newPitch;
    }

    @Override
    public int getTrackRange()
    {
        //noinspection MagicNumber temp
        return 64;
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

    @Override
    public float getHeadPitch()
    {
        return 0.0F;
    }

    @Override
    public boolean exist()
    {
        return this.id != - 1;
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

    @Override
    public EntityBoundingBox getBoundingBox()
    {
        return this.aabb;
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

    @Override
    public float getYaw()
    {
        return this.yaw;
    }

    @Override
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
                chunk.getEntities().stream().filter(entity -> (entity != this) && type.isAssignableFrom(entity.getClass()) && BoundingBox.intersects(bb, entity.getBoundingBox())).forEach(e -> entities.add((T) e));
            }
        }

        return entities;
    }

    @Override
    public Collection<? extends IEntity> getNearbyEntities(final double x, final double y, final double z, final EntityType type)
    {
        final BoundingBox bb = this.aabb.grow(x, y, z);

        final Set<IEntity> entities = new HashSet<>(25);

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
                chunk.getEntities().stream().filter(entity -> (entity != this) && type.equals(entity.getType()) && BoundingBox.intersects(bb, entity.getBoundingBox())).forEach(entities::add);
            }
        }

        return entities;
    }

    @Override
    public Collection<IEntity> getNearbyEntities(final double x, final double y, final double z)
    {
        final BoundingBox bb = this.aabb.grow(x, y, z);

        final Set<IEntity> entities = new HashSet<>(25);

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
                chunk.getEntities().stream().filter(entity -> (entity != this) && BoundingBox.intersects(bb, entity.getBoundingBox())).forEach(entities::add);
            }
        }

        return entities;
    }

    @Override
    public DioriteCore getCore()
    {
        return this.core;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("id", this.id).append("world", this.world).append("x", this.x).append("y", this.y).append("z", this.z).toString();
    }
}
