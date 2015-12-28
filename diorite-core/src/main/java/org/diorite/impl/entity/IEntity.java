package org.diorite.impl.entity;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import org.diorite.impl.Tickable;
import org.diorite.impl.connection.packets.play.server.PacketPlayServer;
import org.diorite.impl.entity.meta.EntityMetadata;
import org.diorite.impl.entity.tracker.BaseTracker;
import org.diorite.impl.entity.tracker.Trackable;
import org.diorite.impl.world.WorldImpl;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.entity.Entity;
import org.diorite.entity.EntityType;
import org.diorite.utils.math.geometry.EntityBoundingBox;

public interface IEntity extends Entity, Tickable, Trackable
{
    int           MAX_AIR_LEVEL                 = 300;
    AtomicInteger ENTITY_ID                     = new AtomicInteger();
    /**
     * byte entry, with flags. {@link EntityImpl.BasicFlags}
     */
    byte          META_KEY_BASIC_FLAGS          = 0;
    /**
     * short entry, air level
     */
    byte          META_KEY_AIR                  = 1;
    /**
     * String entry, name/name tag
     */
    byte          META_KEY_NAME_TAG             = 2;
    /**
     * byte/bool entry, if name tag should be visible
     */
    byte          META_KEY_ALWAYS_SHOW_NAME_TAG = 3;
    /**
     * byte/bool entry, if entity should make sound.
     */
    byte          META_KEY_SILENT               = 4;

    static int getNextEntityID()
    {
        return ENTITY_ID.getAndIncrement();
    }

    BaseTracker<?> getTracker();

    boolean isOnGround();

    EntityMetadata getMetadata();

    ChunkImpl getChunk();

    boolean hasGravity();

    void remove(boolean full);

    @Override
    WorldImpl getWorld();

    void onSpawn(BaseTracker<?> tracker);

    void move(double modX, double modY, double modZ, float modYaw, float modPitch);

    void move(double modX, double modY, double modZ);

    void setPositionAndRotation(double newX, double newY, double newZ, float newYaw, float newPitch);

    void setPosition(double newX, double newY, double newZ);

    void updateChunk(ChunkImpl chunk, ChunkImpl newChunk);

    void setRotation(float newYaw, float newPitch);

    @Override
    Collection<? extends IEntity> getNearbyEntities(double x, double y, double z);

    @Override
    Collection<? extends IEntity> getNearbyEntities(double x, double y, double z, EntityType type);

    /**
     * @return Packet need to spawn entity
     */
    PacketPlayServer getSpawnPacket();

    /**
     * Need return array of packet in valid order needed to spawn entity with all data. <br>
     * Like base spawn packet, potion effects, metadata, passanger etc...
     *
     * @return array of packets.
     */
    PacketPlayServer[] getSpawnPackets();

    EntityBoundingBox getBoundingBox();

    void setBoundingBox(EntityBoundingBox box);

    // TODO

    boolean isOnFire();

    void setOnFire(boolean onFire);

    boolean isCrouching();

    void setCrouching(boolean crouching);

    boolean isSprinting();

    void setSprinting(boolean sprinting);

    boolean hasActionFlag();

    void setActionFlag(boolean eating);

    boolean isInvisible();

    void setInvisible(boolean invisible);
}
