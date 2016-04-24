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

import javax.vecmath.Vector3f;

import java.io.IOException;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientboundListener;
import org.diorite.impl.entity.EntityObject;
import org.diorite.impl.entity.IEntity;
import org.diorite.impl.entity.IEntityFactory;

@PacketClass(id = 0x00, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 58)
public class PacketPlayClientboundSpawnEntity extends PacketPlayClientbound
{
    private int    entityId; // ~5 bytes
    private UUID   entityUUID; // 16 bytes
    private byte   entityTypeId; // 1 byte
    private double x; // 8 bytes
    private double y; // 8 bytes
    private double z; // 8 bytes
    private byte   pitch; // 1 byte
    private byte   yaw; // 1 byte
    private int    objectData; // 4 bytes
    private short  movX; // 2 bytes
    private short  movY; // 2 bytes
    private short  movZ; // 2 bytes

    public PacketPlayClientboundSpawnEntity()
    {
    }

    private static final IEntityFactory factory = DioriteCore.getInstance().getServerManager().getEntityFactory();

    @SuppressWarnings("MagicNumber")
    public <T extends IEntity & EntityObject> PacketPlayClientboundSpawnEntity(final T entity)
    {
        this.entityId = entity.getId();
        this.entityUUID = entity.getUniqueID();
        this.entityTypeId = (byte) factory.getEntityNetworkID(entity.getType());
//        if (entity.getType().isLiving())
//        {
//            throw new IllegalArgumentException();
//        }
        this.x = entity.getX();
        this.y = entity.getY();
        this.z = entity.getZ();
        this.pitch = (byte) ((entity.getPitch() * 256.0F) / 360.0F);
        this.yaw = (byte) ((entity.getYaw() * 256.0F) / 360.0F);

        this.objectData = entity.getEntityObjectData();

        final Vector3f vel = entity.getVelocity();
        this.movX = (short) (vel.x * 8000); // IDK why 8000
        this.movY = (short) (vel.y * 8000);
        this.movZ = (short) (vel.z * 8000);
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.entityId = data.readVarInt();
        this.entityUUID = data.readUUID();
        this.entityTypeId = data.readByte();
        this.x = data.readDouble();
        this.y = data.readDouble();
        this.z = data.readDouble();
        this.pitch = data.readByte();
        this.yaw = data.readByte();
        this.objectData = data.readInt();
        this.movX = data.readShort();
        this.movY = data.readShort();
        this.movZ = data.readShort();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
//        System.out.println("write packet...2");
        data.writeVarInt(this.entityId);
        data.writeUUID(this.entityUUID);
        data.writeByte(this.entityTypeId);
        data.writeDouble(this.x);
        data.writeDouble(this.y);
        data.writeDouble(this.z);
        data.writeByte(this.pitch);
        data.writeByte(this.yaw);
        data.writeInt(this.objectData);
        data.writeShort(this.movX);
        data.writeShort(this.movY);
        data.writeShort(this.movZ);
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

    public UUID getEntityUUID()
    {
        return this.entityUUID;
    }

    public void setEntityUUID(final UUID entityUUID)
    {
        this.entityUUID = entityUUID;
    }

    public byte getEntityTypeId()
    {
        return this.entityTypeId;
    }

    public void setEntityTypeId(final byte entityTypeId)
    {
        this.entityTypeId = entityTypeId;
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

    public byte getPitch()
    {
        return this.pitch;
    }

    public void setPitch(final byte pitch)
    {
        this.pitch = pitch;
    }

    public byte getYaw()
    {
        return this.yaw;
    }

    public void setYaw(final byte yaw)
    {
        this.yaw = yaw;
    }

    public int getObjectData()
    {
        return this.objectData;
    }

    public void setObjectData(final int objectData)
    {
        this.objectData = objectData;
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

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("entityId", this.entityId).append("entityUUID", this.entityUUID).append("entityTypeId", this.entityTypeId).append("x", this.x).append("y", this.y).append("z", this.z).append("pitch", this.pitch).append("yaw", this.yaw).append("objectData", this.objectData).append("movX", this.movX).append("movY", this.movY).append("movZ", this.movZ).toString();
    }
}
