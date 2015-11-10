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

package org.diorite.impl.connection.packets.play.server;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayServerListener;
import org.diorite.impl.entity.EntityImpl;
import org.diorite.impl.entity.meta.entry.EntityMetadataEntry;

@PacketClass(id = 0x0F, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 150)
public class PacketPlayServerSpawnEntityLiving extends PacketPlayServer
{
    private int                              entityId; // ~5 bytes
    private byte                             entityTypeId; // 1 byte
    private int                              x; // 4 bytes, WARNING! This is 'fixed-point' number
    private int                              y; // 4 bytes, WARNING! This is 'fixed-point' number
    private int                              z; // 4 bytes, WARNING! This is 'fixed-point' number
    private byte                             yaw; // 1 byte
    private byte                             pitch; // 1 byte
    private byte                             headPitch; // 1 byte
    private short                            movX; // 2 bytes
    private short                            movY; // 2 bytes
    private short                            movZ; // 2 bytes
    private Iterable<EntityMetadataEntry<?>> metadata; // ~not more than 128 bytes

    public PacketPlayServerSpawnEntityLiving()
    {
    }

    @SuppressWarnings("MagicNumber")
    public PacketPlayServerSpawnEntityLiving(final EntityImpl entity)
    {
        this.entityId = entity.getId();
        this.entityTypeId = (byte) entity.getMcId();
        this.x = (int) (entity.getX() * 32);
        this.y = (int) (entity.getY() * 32);
        this.z = (int) (entity.getZ() * 32);
        this.yaw = (byte) ((entity.getYaw() * 256.0F) / 360.0F);
        this.pitch = (byte) ((entity.getPitch() * 256.0F) / 360.0F);
        this.headPitch = (byte) ((entity.getHeadPitch() * 256.0F) / 360.0F);

        this.movX = (short) (entity.getVelocityX() * 8000); // IDK why 8000
        this.movY = (short) (entity.getVelocityY() * 8000);
        this.movZ = (short) (entity.getVelocityZ() * 8000);
        // TODO pasre metadata from entity, or get it if possible
        this.metadata = entity.getMetadata().getEntries();
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.entityId = data.readVarInt();
        this.entityTypeId = data.readByte();
        this.x = data.readInt();
        this.y = data.readInt();
        this.z = data.readInt();
        this.yaw = data.readByte();
        this.pitch = data.readByte();
        this.headPitch = data.readByte();
        this.movX = data.readShort();
        this.movY = data.readShort();
        this.movZ = data.readShort();
        this.metadata = data.readEntityMetadata();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
//        System.out.println("write packet...3");
        data.writeVarInt(this.entityId);
        data.writeByte(this.entityTypeId);
        data.writeInt(this.x);
        data.writeInt(this.y);
        data.writeInt(this.z);
        data.writeByte(this.yaw);
        data.writeByte(this.pitch);
        data.writeByte(this.headPitch);
        data.writeShort(this.movX);
        data.writeShort(this.movY);
        data.writeShort(this.movZ);
        data.writeEntityMetadata(this.metadata);
    }

    @Override
    public void handle(final PacketPlayServerListener listener)
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

    public byte getEntityTypeId()
    {
        return this.entityTypeId;
    }

    public void setEntityTypeId(final byte entityTypeId)
    {
        this.entityTypeId = entityTypeId;
    }

    public int getX()
    {
        return this.x;
    }

    public void setX(final int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return this.y;
    }

    public void setY(final int y)
    {
        this.y = y;
    }

    public int getZ()
    {
        return this.z;
    }

    public void setZ(final int z)
    {
        this.z = z;
    }

    public byte getPitch()
    {
        return this.pitch;
    }

    public byte getYaw()
    {
        return this.yaw;
    }

    public void setYaw(final byte yaw)
    {
        this.yaw = yaw;
    }

    public void setPitch(final byte pitch)
    {
        this.pitch = pitch;
    }

    public byte getHeadPitch()
    {
        return this.headPitch;
    }

    public void setHeadPitch(final byte headPitch)
    {
        this.headPitch = headPitch;
    }

    public short getMovX()
    {
        return this.movX;
    }

    public void setMovX(final short movX)
    {
        this.movX = movX;
    }

    public short getMovY()
    {
        return this.movY;
    }

    public void setMovY(final short movY)
    {
        this.movY = movY;
    }

    public short getMovZ()
    {
        return this.movZ;
    }

    public void setMovZ(final short movZ)
    {
        this.movZ = movZ;
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("entityId", this.entityId).append("entityTypeId", this.entityTypeId).append("x", this.x).append("y", this.y).append("z", this.z).append("yaw", this.yaw).append("pitch", this.pitch).append("headPitch", this.headPitch).append("movX", this.movX).append("movY", this.movY).append("movZ", this.movZ).append("metadata", this.metadata).toString();
    }
}
