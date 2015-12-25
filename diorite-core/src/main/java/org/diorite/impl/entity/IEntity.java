package org.diorite.impl.entity;

import java.util.concurrent.atomic.AtomicInteger;

import org.diorite.impl.Tickable;
import org.diorite.impl.connection.packets.play.server.PacketPlayServer;
import org.diorite.impl.entity.meta.EntityMetadata;
import org.diorite.impl.entity.tracker.BaseTracker;
import org.diorite.impl.entity.tracker.Trackable;
import org.diorite.impl.world.WorldImpl;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.entity.Entity;
import org.diorite.utils.math.geometry.EntityBoundingBox;

public interface IEntity extends Entity, Tickable, Trackable
{
    AtomicInteger ENTITY_ID = new AtomicInteger();
    void initMetadata();

    boolean isOnGround();

    EntityMetadata getMetadata();

    ChunkImpl getChunk();

    boolean hasGravity();

    void remove(boolean full);

    void doPhysics();

    @Override
    WorldImpl getWorld();

    void onSpawn(BaseTracker<?> tracker);

    void move(double modX, double modY, double modZ, float modYaw, float modPitch);

    void move(double modX, double modY, double modZ);

    void setPositionAndRotation(double newX, double newY, double newZ, float newYaw, float newPitch);

    void setPosition(double newX, double newY, double newZ);

    void updateChunk(ChunkImpl chunk, ChunkImpl newChunk);

    void setRotation(float newYaw, float newPitch);

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

    static int getNextEntityID()
    {
        return EntityImpl.ENTITY_ID.getAndIncrement();
    }

    EntityBoundingBox getBoundingBox();
}
