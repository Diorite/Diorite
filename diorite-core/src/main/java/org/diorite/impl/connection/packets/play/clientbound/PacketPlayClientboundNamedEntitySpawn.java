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

package org.diorite.impl.connection.packets.play.clientbound;


import java.io.IOException;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientboundListener;
import org.diorite.impl.entity.IHuman;
import org.diorite.impl.entity.meta.entry.EntityMetadataEntry;

@PacketClass(id = 0x05, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 140)
public class PacketPlayClientboundNamedEntitySpawn extends PacketPlayClientbound
{
    private int                              entityId; // ~5 bytes
    private UUID                             uuid; // 16 bytes
    private double                           x; // 4 bytes, WARNING! This is 'fixed-point' number
    private double                           y; // 4 bytes, WARNING! This is 'fixed-point' number
    private double                           z; // 4 bytes, WARNING! This is 'fixed-point' number
    private byte                             yaw; // 1 byte
    private byte                             pitch; // 1 byte
    private Iterable<EntityMetadataEntry<?>> metadata; // ~not more than 128 bytes

    public PacketPlayClientboundNamedEntitySpawn()
    {
    }

    @SuppressWarnings("MagicNumber")
    public PacketPlayClientboundNamedEntitySpawn(final IHuman entity)
    {
        this.entityId = entity.getId();
        this.uuid = entity.getUniqueID();
        this.x = entity.getX();
        this.y = entity.getY();
        this.z = entity.getZ();
        this.yaw = (byte) ((entity.getYaw() * 256.0F) / 360.0F);
        this.pitch = (byte) ((entity.getPitch() * 256.0F) / 360.0F);

        // TODO pasre metadata from entity, or get it if possible
        this.metadata = entity.getMetadata().getEntries();
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.entityId = data.readVarInt();
        this.uuid = data.readUUID();
        this.x = data.readDouble();
        this.y = data.readDouble();
        this.z = data.readDouble();
        this.yaw = data.readByte();
        this.pitch = data.readByte();
        this.metadata = data.readEntityMetadata();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.entityId);
        data.writeUUID(this.uuid);
        data.writeDouble(this.x);
        data.writeDouble(this.y);
        data.writeDouble(this.z);
        data.writeByte(this.yaw);
        data.writeByte(this.pitch);
        data.writeEntityMetadata(this.metadata);
    }

    @Override
    public void handle(final PacketPlayClientboundListener listener)
    {
        listener.handle(this);
    }

    public int getEntityId()
    {
        return this.entityId;
    }

    public void setEntityId(final int entityId)
    {
        this.entityId = entityId;
    }

    public UUID getUuid()
    {
        return this.uuid;
    }

    public void setUuid(final UUID uuid)
    {
        this.uuid = uuid;
    }

    public double getX()
    {
        return this.x;
    }

    public void setX(final double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return this.y;
    }

    public void setY(final double y)
    {
        this.y = y;
    }

    public double getZ()
    {
        return this.z;
    }

    public void setZ(final double z)
    {
        this.z = z;
    }

    public byte getYaw()
    {
        return this.yaw;
    }

    public void setYaw(final byte yaw)
    {
        this.yaw = yaw;
    }

    public byte getPitch()
    {
        return this.pitch;
    }

    public void setPitch(final byte pitch)
    {
        this.pitch = pitch;
    }

    public Iterable<EntityMetadataEntry<?>> getMetadata()
    {
        return this.metadata;
    }

    public void setMetadata(final Iterable<EntityMetadataEntry<?>> metadata)
    {
        this.metadata = metadata;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("entityId", this.entityId).append("uuid", this.uuid).append("x", this.x).append("y", this.y).append("z", this.z).append("pitch", this.pitch).append("yaw", this.yaw).append("metadata", this.metadata).toString();
    }
}
