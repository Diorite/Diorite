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

package org.diorite.impl.entity.diorite;

import javax.vecmath.Vector3f;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.CoreMain;
import org.diorite.impl.DioriteCore;
import org.diorite.impl.GameObjectImpl;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientbound;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientboundEntityMetadata;
import org.diorite.impl.entity.IEntity;
import org.diorite.impl.entity.meta.EntityMetadata;
import org.diorite.impl.entity.meta.entry.EntityMetadataBooleanEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataByteEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataIntEntry;
import org.diorite.impl.entity.meta.entry.EntityMetadataStringEntry;
import org.diorite.impl.entity.pathfinder.EntityControllerImpl;
import org.diorite.impl.entity.tracker.BaseTracker;
import org.diorite.impl.world.WorldImpl;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.ILocation;
import org.diorite.ImmutableLocation;
import org.diorite.block.BlockLocation;
import org.diorite.entity.Entity;
import org.diorite.entity.EntityType;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.nbt.NbtTagDouble;
import org.diorite.nbt.NbtTagFloat;
import org.diorite.utils.lazy.BooleanLazyValue;
import org.diorite.utils.math.DioriteMathUtils;
import org.diorite.utils.math.DioriteRandom;
import org.diorite.utils.math.DioriteRandomUtils;
import org.diorite.utils.math.geometry.EntityBoundingBox;
import org.diorite.utils.math.geometry.LookupShape;
import org.diorite.utils.others.Resetable;
import org.diorite.world.chunk.Chunk;

abstract class EntityImpl extends GameObjectImpl implements IEntity
{
    static final double PHYSIC_GRAVITY_CONST_1 = 0.98D;
    static final double PHYSIC_GRAVITY_CONST_2 = 0.08D;

    private final Set<Resetable> values = new HashSet<>(10);

    private final BooleanLazyValue     lazyOnGround;
    private final EntityControllerImpl controller;
    private boolean       aiEnabled = true; // don't do any actions if AI is disabled
    final   DioriteRandom random    = DioriteRandomUtils.getRandom();
    final            DioriteCore       core;
    private          WorldImpl         world;
    private volatile Thread            lastTickThread;
    private          EntityBoundingBox aabb;
    private          int               id;
    protected        EntityMetadata    metadata;
    private          BaseTracker<?>    tracker;
    protected        int               age; // in 1/100th of second
    private          double            health;

    private double x;
    private double y;
    private double z;
    private float  yaw;
    private float  pitch;
    float velocityX;
    float velocityY;
    float velocityZ;

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
        this.controller = new EntityControllerImpl(this);
        this.initMetadata();

        this.lazyOnGround = new BooleanLazyValue(this.values, () ->
        {
            if ((this.y <= 0) || (this.y > Chunk.CHUNK_FULL_HEIGHT))
            {
                return false; // we're outside world
            }

            final int yRounded = (int) this.y;
            final int toRemove = (yRounded == this.y) ? 1 : 0;
            final BlockLocation blockUnderFoot = this.getLocation().setY(this.y - toRemove).toBlockLocation();
            if (blockUnderFoot.getY() <= 0)
            {
                return false;
            }

            return blockUnderFoot.getBlock().getType().isSolid();
        });
    }

    @Override
    public void setVelocity(final Vector3f velocity)
    {
        this.velocityX = velocity.x;
        this.velocityY = velocity.y;
        this.velocityZ = velocity.z;
    }

    @Override
    public double getHealth()
    {
        return this.health;
    }

    @Override
    public void setHealth(final double health)
    {
        this.health = health;
    }

    @Override
    public boolean isOnFire()
    {
        return this.metadata.getBoolean(META_KEY_ENTITY_BASIC_FLAGS, EntityBasicFlags.ON_FIRE);
    }

    @Override
    public void setOnFire(final boolean onFire)
    {
        this.metadata.setBoolean(META_KEY_ENTITY_BASIC_FLAGS, EntityBasicFlags.ON_FIRE, onFire);
    }

    @Override
    public boolean isCrouching()
    {
        return this.metadata.getBoolean(META_KEY_ENTITY_BASIC_FLAGS, EntityBasicFlags.CROUCHED);
    }

    @Override
    public void setCrouching(final boolean crouching)
    {
        this.metadata.setBoolean(META_KEY_ENTITY_BASIC_FLAGS, EntityBasicFlags.CROUCHED, crouching);
    }

    @Override
    public boolean isSprinting()
    {
        return this.metadata.getBoolean(META_KEY_ENTITY_BASIC_FLAGS, EntityBasicFlags.SPRINTING);
    }

    @Override
    public void setSprinting(final boolean sprinting)
    {
        this.metadata.setBoolean(META_KEY_ENTITY_BASIC_FLAGS, EntityBasicFlags.SPRINTING, sprinting);
    }

    @Override
    public boolean hasActionFlag()
    {
        return this.metadata.getBoolean(META_KEY_ENTITY_BASIC_FLAGS, EntityBasicFlags.ACTION);
    }

    @Override
    public void setActionFlag(final boolean flag)
    {
        this.metadata.setBoolean(META_KEY_ENTITY_BASIC_FLAGS, EntityBasicFlags.ACTION, flag);
    }

    @Override
    public boolean isInvisible()
    {
        return this.metadata.getBoolean(META_KEY_ENTITY_BASIC_FLAGS, EntityBasicFlags.INVISIBLE);
    }

    @Override
    public void setInvisible(final boolean invisible)
    {
        this.metadata.setBoolean(META_KEY_ENTITY_BASIC_FLAGS, EntityBasicFlags.INVISIBLE, invisible);
    }

    @Override
    public boolean isGlowing()
    {
        return this.metadata.getBoolean(META_KEY_ENTITY_BASIC_FLAGS, EntityBasicFlags.GLOWING);
    }

    @Override
    public void setGlowing(final boolean glowing)
    {
        this.metadata.setBoolean(META_KEY_ENTITY_BASIC_FLAGS, EntityBasicFlags.GLOWING, glowing);
    }

    @Override
    public int getAir()
    {
        return this.metadata.getInt(META_KEY_ENTITY_AIR);
    }

    @Override
    public void setAir(final int air)
    {
        this.metadata.setInt(META_KEY_ENTITY_AIR, air);
    }

    @Override
    public String getCustomName()
    {
        return this.metadata.getString(META_KEY_ENTITY_NAME_TAG);
    }

    @Override
    public void setCustomName(final String name)
    {
        this.metadata.setString(META_KEY_ENTITY_NAME_TAG, name);
    }

    @Override
    public boolean isCustomNameVisible()
    {
        return this.metadata.getBoolean(META_KEY_ENTITY_ALWAYS_SHOW_NAME_TAG);
    }

    @Override
    public void setCustomNameVisible(final boolean visible)
    {
        this.metadata.setBoolean(META_KEY_ENTITY_ALWAYS_SHOW_NAME_TAG, visible);
    }

    @Override
    public boolean isSilent()
    {
        return this.metadata.getBoolean(META_KEY_ENTITY_SILENT);
    }

    @Override
    public void setSilent(final boolean silent)
    {
        this.metadata.setBoolean(META_KEY_ENTITY_SILENT, silent);
    }

    @Override
    public Vector3f getVelocity()
    {
        return new Vector3f(this.velocityX, this.velocityY, this.velocityZ);
    }

    protected void createMetadata()
    {
        this.metadata = new EntityMetadata(META_KEYS);
    }

    public void initMetadata()
    {
        this.createMetadata();
        this.metadata.add(new EntityMetadataByteEntry(IEntity.META_KEY_ENTITY_BASIC_FLAGS, 0));
        this.metadata.add(new EntityMetadataIntEntry(IEntity.META_KEY_ENTITY_AIR, IEntity.MAX_AIR_LEVEL));
        this.metadata.add(new EntityMetadataStringEntry(IEntity.META_KEY_ENTITY_NAME_TAG, ""));
        this.metadata.add(new EntityMetadataBooleanEntry(IEntity.META_KEY_ENTITY_SILENT, false));
        this.metadata.add(new EntityMetadataBooleanEntry(IEntity.META_KEY_ENTITY_ALWAYS_SHOW_NAME_TAG, false));
        this.metadata.add(new EntityMetadataBooleanEntry(IEntity.META_KEY_ENTITY_NO_GRAVITY, false));
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
    public void setMetadata(final EntityMetadata metadata)
    {
        this.metadata = metadata;
        metadata.getEntries().forEach(EntityMetadataEntry::setDirty);
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
        if (! this.exist())
        {
            return;
        }
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
        if (this.velocityY == 0)
        {
            if (! this.isOnGround()) // velocityY is 0, and we're not on ground. Apply gravitation
            {
                this.velocityY = - 0.6F; // this is my deduced value.
            }
        }
        else
        {
            if (this.isOnGround()) // we're on ground and velocity isn't 0. Set it to 0
            {
                this.velocityY = 0;
                this.y = this.getLocation().toBlockLocation().getY() + 1;
            }
            else // we're still falling...
            {
                this.velocityY *= 1.05F; // speed up falling?
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
    public void onLoad(final BaseTracker<?> tracker)
    {
        if (this.tracker != null)
        {
            throw new IllegalArgumentException("Entity was already spawned.");
        }
        this.tracker = tracker;
        this.updateChunk(null, this.getWorld().getChunkAt(this.getLocation().getChunkPos()));
    }

    @Override
    public void onSpawn()
    {
    }

    @Override
    public PacketPlayClientbound[] getSpawnPackets()
    {
        return new PacketPlayClientbound[]{this.getSpawnPacket(), new PacketPlayClientboundEntityMetadata(this, true)};
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
        this.age += (100 / tps);
        this.lastTickThread = Thread.currentThread();
        this.values.forEach(Resetable::reset);
        if (this.aabb != null) // TODO
        {
            this.aabb.setCenter(this);
        }

        if (this.hasGravity())
        {
            this.doPhysics();
        }

        if (this.aiEnabled)
        {
            this.controller.tick(tps);
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
    public int getAge()
    {
        return this.age;
    }

    @Override
    public void setAge(final int age)
    {
        this.age = age;
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
    public <T extends Entity> Collection<? extends T> getNearbyEntities(final double x, final double y, final double z, final Predicate<Entity> predicate)
    {
        final Vector3f entitySize = this.aabb.getEntitySize();
        final Set<T> entities = new HashSet<>(25);

        final int cx = this.getChunk().getX(); // Chunk X location
        final int cz = this.getChunk().getZ(); // Chunk Z location


        final int chunkScanSizeX = 1 + DioriteMathUtils.ceil((x + entitySize.x) / Chunk.CHUNK_SIZE);
        final int chunkScanSizeZ = 1 + DioriteMathUtils.ceil((z + entitySize.z) / Chunk.CHUNK_SIZE);

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
                chunk.getEntities().stream().filter(e -> (e != this) && predicate.test(e)).forEach(e -> entities.add((T) e));
            }
        }

        return entities;
    }

    @Override
    public Collection<? extends IEntity> getNearbyEntities(final double x, final double y, final double z, final LookupShape shape)
    {
        final Predicate<Entity> entityPredicate;
        final Vector3f entitySize = this.aabb.getEntitySize();
        if ((x == y) && (y == z) && (entitySize.x == entitySize.y))
        {
            entityPredicate = e -> shape.isNotOutside(this.x, this.y, this.z, (entitySize.x / 1) + x, e.getX(), e.getY(), e.getZ());
        }
        else if ((x == z) && (entitySize.x == entitySize.y))
        {
            entityPredicate = e -> shape.isNotOutside(this.x, this.y, this.z, (entitySize.x / 1) + x, (entitySize.y / 1) + y, e.getX(), e.getY(), e.getZ());
        }
        else
        {
            entityPredicate = e -> shape.isNotOutside(this.x, this.y, this.z, (entitySize.x / 1) + x, (entitySize.y / 1) + y, (entitySize.z / 1) + z, e.getX(), e.getY(), e.getZ());
        }
        return this.getNearbyEntities(x, y, z, entityPredicate);
    }

    @SuppressWarnings("ObjectEquality")
    @Override
    public Collection<? extends IEntity> getNearbyEntities(final double x, final double y, final double z, final EntityType type, final LookupShape shape)
    {
        final Vector3f entitySize = this.aabb.getEntitySize();
        final Set<IEntity> entities = new HashSet<>(25);

        final int cx = this.getChunk().getX(); // Chunk X location
        final int cz = this.getChunk().getZ(); // Chunk Z location


        final int chunkScanSizeX = 1 + DioriteMathUtils.ceil((x + entitySize.x) / Chunk.CHUNK_SIZE);
        final int chunkScanSizeZ = 1 + DioriteMathUtils.ceil((z + entitySize.z) / Chunk.CHUNK_SIZE);

        final int cxBeginScan = cx - chunkScanSizeX;
        final int czBeginScan = cz - chunkScanSizeZ;

        final int cxEndScan = cx + chunkScanSizeX;
        final int czEndScan = cz + chunkScanSizeZ;

        final Predicate<Entity> entityPredicate;
        final Class<? extends Entity> typeClass = type.getDioriteEntityClass();
        if ((x == y) && (y == z))
        {
            entityPredicate = e -> (e != this) && typeClass.isAssignableFrom(e.getClass()) && shape.isNotOutside(this.x, this.y, this.z, (entitySize.x / 2) + x, e.getX(), e.getY(), e.getZ());
        }
        else if (x == z)
        {
            entityPredicate = e -> (e != this) && typeClass.isAssignableFrom(e.getClass()) && shape.isNotOutside(this.x, this.y, this.z, (entitySize.x / 2) + x, (entitySize.y / 2) + y, e.getX(), e.getY(), e.getZ());
        }
        else
        {
            entityPredicate = e -> (e != this) && typeClass.isAssignableFrom(e.getClass()) && shape.isNotOutside(this.x, this.y, this.z, (entitySize.x / 2) + x, (entitySize.y / 2) + y, (entitySize.z / 2) + z, e.getX(), e.getY(), e.getZ());
        }
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
                chunk.getEntities().stream().filter(entityPredicate).forEach(entities::add);
            }
        }

        return entities;
    }

    @Override
    public <T extends Entity> Collection<? extends T> getNearbyEntities(final double x, final double y, final double z, final Class<? extends T> type, final LookupShape shape)
    {
        final Predicate<Entity> entityPredicate;
        final Vector3f entitySize = this.aabb.getEntitySize();
        if ((x == y) && (y == z) && (entitySize.x == entitySize.y))
        {
            entityPredicate = e -> type.isAssignableFrom(e.getClass()) && shape.isNotOutside(this.x, this.y, this.z, (entitySize.x / 2) + x, e.getX(), e.getY(), e.getZ());
        }
        else if ((x == z) && (entitySize.x == entitySize.y))
        {
            entityPredicate = e -> type.isAssignableFrom(e.getClass()) && shape.isNotOutside(this.x, this.y, this.z, (entitySize.x / 2) + x, (entitySize.y / 2) + y, e.getX(), e.getY(), e.getZ());
        }
        else
        {
            entityPredicate = e -> type.isAssignableFrom(e.getClass()) && shape.isNotOutside(this.x, this.y, this.z, (entitySize.x / 2) + x, (entitySize.y / 2) + y, (entitySize.z / 2) + z, e.getX(), e.getY(), e.getZ());
        }
        return this.getNearbyEntities(x, y, z, entityPredicate);
    }

    @Override
    public <T extends Entity> Collection<? extends T> getNearbyEntities(final double x, final double y, final double z, final Class<? extends T> type, final Predicate<Entity> predicate)
    {
        return this.getNearbyEntities(x, y, z, e -> type.isAssignableFrom(e.getClass()) && predicate.test(e));
    }

    @Override
    public EntityControllerImpl getEntityController()
    {
        return this.controller;
    }

    @Override
    public DioriteCore getCore()
    {
        return this.core;
    }

    @Override
    public void teleport(final ILocation location)
    {
        final ChunkImpl chunk = this.getChunk();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();

        final WorldImpl newWorld = (WorldImpl) location.getWorld();
        if (newWorld != null && ! Objects.equals(this.world, newWorld))
        {
            this.worldChange(this.world, this.world = newWorld);
        }

        this.updateChunk(chunk, this.getChunk());
        this.tracker.forceLocationUpdate();
    }

    protected void worldChange(final WorldImpl oldW, final WorldImpl newW)
    {
        CoreMain.debug("Entity " + this + " moved from " + oldW + " to " + newW);
        this.remove(true);
        this.tracker = null; // TODO better way?
        newW.addEntity(this, false);
        // TODO add event?
    }

    @Override
    public void loadFromNbt(final NbtTagCompound nbtEntity)
    {
        this.setAir(nbtEntity.getShort("Air", 300));
        // TODO
    }

    @Override
    public void saveToNbt(final NbtTagCompound nbtEntity)
    {
        nbtEntity.setString("id", this.getType().getName());
        nbtEntity.setList("Pos", Arrays.asList(new NbtTagDouble("x", this.x), new NbtTagDouble("y", this.y), new NbtTagDouble("z", this.z)));
        nbtEntity.setList("Rotation", Arrays.asList(new NbtTagFloat("yaw", this.yaw), new NbtTagFloat("pitch", this.pitch)));
        nbtEntity.setShort("Air", this.getAir());
        // TODO
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("id", this.id).append("world", this.world).append("x", this.x).append("y", this.y).append("z", this.z).toString();
    }
}
