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

package org.diorite.impl.entity;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.Tickable;
import org.diorite.impl.connection.packets.play.clientbound.PacketPlayClientbound;
import org.diorite.impl.entity.meta.EntityMetadata;
import org.diorite.impl.entity.tracker.BaseTracker;
import org.diorite.impl.entity.tracker.Trackable;
import org.diorite.impl.world.WorldImpl;
import org.diorite.impl.world.chunk.ChunkImpl;
import org.diorite.entity.Entity;
import org.diorite.entity.EntityType;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.utils.math.geometry.EntityBoundingBox;
import org.diorite.utils.math.geometry.LookupShape;

public interface IEntity extends Entity, Tickable, Trackable
{
    int           MAX_AIR_LEVEL = 300;
    AtomicInteger ENTITY_ID     = new AtomicInteger();

    /**
     * Size of metadata.
     */
    byte META_KEYS                            = 6;
    /**
     * byte entry, with flags.<br>
     * OnFire, Crouched, Sprinting, Action, Invisible, Glowing (outline effect)
     */
    byte META_KEY_ENTITY_BASIC_FLAGS          = 0;
    /**
     * short entry, air level
     */
    byte META_KEY_ENTITY_AIR                  = 1;
    /**
     * String entry, name/name tag
     */
    byte META_KEY_ENTITY_NAME_TAG             = 2;
    /**
     * byte/bool entry, if name tag should be visible
     */
    byte META_KEY_ENTITY_ALWAYS_SHOW_NAME_TAG = 3;
    /**
     * byte/bool entry, if entity should make sound.
     */
    byte META_KEY_ENTITY_SILENT               = 4;
    /**
     * byte/bool entry, if entity doesn't has gravity
     */
    byte META_KEY_ENTITY_NO_GRAVITY           = 5;

    /**
     * Contains basic flags used in matadata.
     */
    interface EntityBasicFlags
    {
        byte ON_FIRE   = 0;
        byte CROUCHED  = 1;
        byte SPRINTING = 2;
        byte ACTION    = 3;
        byte INVISIBLE = 4;
        byte GLOWING   = 5;
        // TODO flying with elytra? (0x80)
    }

    static int getNextEntityID()
    {
        return ENTITY_ID.getAndIncrement();
    }

    BaseTracker<?> getTracker();

    boolean isOnGround();

    EntityMetadata getMetadata();

    void setMetadata(EntityMetadata metadata);

    ChunkImpl getChunk();

    boolean hasGravity();

    void remove(boolean full);

    @Override
    WorldImpl getWorld();

    @Override
    DioriteCore getCore();

    /**
     * This method is invoked when entity is loaded from chunk files
     * and when is spawned
     *
     * @param tracker tracker related with this entity
     */
    void onLoad(BaseTracker<?> tracker);

    /**
     * This method invokes only when entity is spawned (by Command/API Call/Naturally/etc.)
     */
    void onSpawn();

    void move(double modX, double modY, double modZ, float modYaw, float modPitch);

    void move(double modX, double modY, double modZ);

    void setPositionAndRotation(double newX, double newY, double newZ, float newYaw, float newPitch);

    void setPosition(double newX, double newY, double newZ);

    void updateChunk(ChunkImpl chunk, ChunkImpl newChunk);

    void setRotation(float newYaw, float newPitch);

    @Override
    Collection<? extends IEntity> getNearbyEntities(double x, double y, double z, LookupShape shape);

    @Override
    Collection<? extends IEntity> getNearbyEntities(double x, double y, double z, EntityType type, LookupShape shape);

    /**
     * @return Packet need to spawn entity
     */
    PacketPlayClientbound getSpawnPacket();

    /**
     * Need return array of packet in valid order needed to spawn entity with all data. <br>
     * Like base spawn packet, potion effects, metadata, passanger etc...
     *
     * @return array of packets.
     */
    PacketPlayClientbound[] getSpawnPackets();

    EntityBoundingBox getBoundingBox();

    void setBoundingBox(EntityBoundingBox box);

    void loadFromNbt(NbtTagCompound nbtEntity);

    void saveToNbt(NbtTagCompound nbtEntity);
}
