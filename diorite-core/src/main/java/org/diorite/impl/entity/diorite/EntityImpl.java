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

import javax.vecmath.Vector3f;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.GameObjectImpl;
import org.diorite.impl.connection.packets.play.server.PacketPlayServer;
import org.diorite.impl.entity.IEntity;
import org.diorite.impl.entity.meta.EntityMetadata;
import org.diorite.impl.entity.meta.entry.EntityMetadataBooleanEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataByteEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataIntEntry;
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

    final            DioriteCore       core;
    private final    WorldImpl         world;
    private volatile Thread            lastTickThread;
    private          EntityBoundingBox aabb;
    private          int               id;
    private final    EntityMetadata    metadata;
    private          BaseTracker<?>    tracker;

    private double x;
    private double y;
    private double z;
    private float  yaw;
    private float  pitch;
    float velocityX;
    float velocityY;
    float velocityZ;

    private boolean aiEnabled = true; // don't do any actions if AI is disabled

    final         DioriteRandom    random       = DioriteRandomUtils.getRandom();
    private final BooleanLazyValue lazyOnGround = new BooleanLazyValue(this.values, () -> (this.y >= 0) && (this.y < Chunk.CHUNK_FULL_HEIGHT) && this.getLocation().toBlockLocation().getBlock().getType().isSolid()); // TODO: maybe something better?

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
    public void setVelocity(final Vector3f velocity)
    {
        this.velocityX = velocity.x;
        this.velocityY = velocity.y;
        this.velocityZ = velocity.z;
    }

    @Override
    public boolean isOnFire()
    {
        return this.metadata.getBoolean(META_KEY_BASIC_FLAGS, BasicFlags.ON_FIRE);
    }

    @Override
    public void setOnFire(final boolean onFire)
    {
        this.metadata.setBoolean(META_KEY_BASIC_FLAGS, BasicFlags.ON_FIRE, onFire);
    }

    @Override
    public boolean isCrouching()
    {
        return this.metadata.getBoolean(META_KEY_BASIC_FLAGS, BasicFlags.CROUCHED);
    }

    @Override
    public void setCrouching(final boolean crouching)
    {
        this.metadata.setBoolean(META_KEY_BASIC_FLAGS, BasicFlags.CROUCHED, crouching);
    }

    @Override
    public boolean isSprinting()
    {
        return this.metadata.getBoolean(META_KEY_BASIC_FLAGS, BasicFlags.SPRINTING);
    }

    @Override
    public void setSprinting(final boolean sprinting)
    {
        this.metadata.setBoolean(META_KEY_BASIC_FLAGS, BasicFlags.SPRINTING, sprinting);
    }

    @Override
    public boolean hasActionFlag()
    {
        return this.metadata.getBoolean(META_KEY_BASIC_FLAGS, BasicFlags.ACTION);
    }

    @Override
    public void setActionFlag(final boolean flag)
    {
        this.metadata.setBoolean(META_KEY_BASIC_FLAGS, BasicFlags.ACTION, flag);
    }

    @Override
    public boolean isInvisible()
    {
        return this.metadata.getBoolean(META_KEY_BASIC_FLAGS, BasicFlags.INVISIBLE);
    }

    @Override
    public void setInvisible(final boolean invisible)
    {
        this.metadata.setBoolean(META_KEY_BASIC_FLAGS, BasicFlags.INVISIBLE, invisible);
    }

    @Override
    public int getAir()
    {
        return this.metadata.getInt(META_KEY_AIR);
    }

    @Override
    public void setAir(final int air)
    {
        this.metadata.setInt(META_KEY_AIR, air);
    }

    @Override
    public String getCustomName()
    {
        return this.metadata.getString(META_KEY_NAME_TAG);
    }

    @Override
    public void setCustomName(final String name)
    {
        this.metadata.setString(META_KEY_NAME_TAG, name);
    }

    @Override
    public boolean isCustomNameVisible()
    {
        return this.metadata.getBoolean(META_KEY_ALWAYS_SHOW_NAME_TAG);
    }

    @Override
    public void setCustomNameVisible(final boolean visible)
    {
        this.metadata.setBoolean(META_KEY_ALWAYS_SHOW_NAME_TAG, visible);
    }

    @Override
    public boolean isSilent()
    {
        return this.metadata.getBoolean(META_KEY_SILENT);
    }

    @Override
    public void setSilent(final boolean silent)
    {
        this.metadata.setBoolean(META_KEY_SILENT, silent);
    }

    @Override
    public Vector3f getVelocity()
    {
        return new Vector3f(this.velocityX, this.velocityY, this.velocityZ);
    }

    public void initMetadata()
    {
        this.metadata.add(new EntityMetadataByteEntry(IEntity.META_KEY_BASIC_FLAGS, 0));
        this.metadata.add(new EntityMetadataIntEntry(IEntity.META_KEY_AIR, IEntity.MAX_AIR_LEVEL));
        this.metadata.add(new EntityMetadataStringEntry(IEntity.META_KEY_NAME_TAG, ""));
        this.metadata.add(new EntityMetadataBooleanEntry(IEntity.META_KEY_SILENT, false));
        this.metadata.add(new EntityMetadataBooleanEntry(IEntity.META_KEY_ALWAYS_SHOW_NAME_TAG, false));


        // test TODO: remove
//        this.metadata.add(new EntityMetadataByteEntry(META_KEY_ALWAYS_SHOW_NAME_TAG, 1));
//        this.metadata.add(new EntityMetadataStringEntry(META_KEY_NAME_TAG, ChatColor.translateAlternateColorCodesInString("&a#&3OnlyDiorite")));

    }


    public float getVelocityX()
    {
        return this.velocityX;
    }

    public float getVelocityY()
    {
        return this.velocityY;
    }

    public float getVelocityZ()
    {
        return this.velocityZ;
    }

    public void setVelocityX(final float velocityX)
    {
        this.velocityX = velocityX;
    }

    public void setVelocityY(final float velocityY)
    {
        this.velocityY = velocityY;
    }

    public void setVelocityZ(final float velocityZ)
    {
        this.velocityZ = velocityZ;
    }

    @Override
    public BaseTracker<?> getTracker()
    {
        return this.tracker;
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

    public void doPhysics()
    {
        final double multi = DioriteCore.getInstance().getSpeedMutli();

        double x = 0;
        double y = 0;
        double z = 0;
        if (this.velocityX != 0)
        {
            x += (this.velocityX * multi);
        }
        if ((this.velocityY != 0))
        {
            if (this.isOnGround())
            {
                this.velocityY = 0;
                this.y = (this.getLocation().toBlockLocation().getY() + (1));
            }
            else
            {
                y = (this.velocityY * multi);
            }
        }
        if (this.velocityZ != 0)
        {
            z = (this.velocityZ * multi);
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
    public PacketPlayServer[] getSpawnPackets()
    {
        return new PacketPlayServer[]{this.getSpawnPacket()};
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
    public void setBoundingBox(final EntityBoundingBox aabb)
    {
        this.aabb = aabb;
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
