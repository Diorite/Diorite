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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientboundListener;
import org.diorite.impl.entity.IEntity;

@PacketClass(id = 0x26, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 14)
public class PacketPlayClientboundRelEntityMoveLook extends PacketPlayClientbound
{
    private int     entityId; // ~5 bytes
    private short   deltaX; // 2 bytes
    private short   deltaY; // 2 bytes
    private short   deltaZ; // 2 bytes
    private byte    yaw; // 1 byte
    private byte    pitch; // 1 byte
    private boolean onGround; // 1 byte

    public PacketPlayClientboundRelEntityMoveLook()
    {
    }

    public PacketPlayClientboundRelEntityMoveLook(final int entityId, final short deltaX, final short deltaY, final short deltaZ, final byte yaw, final byte pitch, final boolean onGround)
    {
        this.entityId = entityId;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaZ = deltaZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    @SuppressWarnings("MagicNumber")
    public PacketPlayClientboundRelEntityMoveLook(final int entityId, final double deltaX, final double deltaY, final double deltaZ, final float yaw, final float pitch, final boolean onGround)
    {
        this.entityId = entityId;
        this.deltaX = (short) (deltaX * 32 * 128);
        this.deltaY = (short) (deltaY * 32 * 128);
        this.deltaZ = (short) (deltaZ * 32 * 128);
        this.yaw = (byte) ((yaw * 256.0F) / 360.0F);
        this.pitch = (byte) ((pitch * 256.0F) / 360.0F);
        this.onGround = onGround;
    }

    @SuppressWarnings("MagicNumber")
    public PacketPlayClientboundRelEntityMoveLook(final IEntity entity, final double deltaX, final double deltaY, final double deltaZ)
    {
        this.entityId = entity.getId();
        this.deltaX = (short) (deltaX * 32 * 128);
        this.deltaY = (short) (deltaY * 32 * 128);
        this.deltaZ = (short) (deltaZ * 32 * 128);
        this.yaw = (byte) ((entity.getYaw() * 256.0F) / 360.0F);
        this.pitch = (byte) ((entity.getPitch() * 256.0F) / 360.0F);
        this.onGround = entity.isOnGround();
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.entityId = data.readVarInt();
        this.deltaX = data.readShort();
        this.deltaY = data.readShort();
        this.deltaZ = data.readShort();
        this.yaw = data.readByte();
        this.pitch = data.readByte();
        this.onGround = data.readBoolean();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.entityId);
        data.writeShort(this.deltaX);
        data.writeShort(this.deltaY);
        data.writeShort(this.deltaZ);
        data.writeByte(this.yaw);
        data.writeByte(this.pitch);
        data.writeBoolean(this.onGround);
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

    public short getDeltaX()
    {
        return this.deltaX;
    }

    public void setDeltaX(final short deltaX)
    {
        this.deltaX = deltaX;
    }

    public short getDeltaY()
    {
        return this.deltaY;
    }

    public void setDeltaY(final short deltaY)
    {
        this.deltaY = deltaY;
    }

    public short getDeltaZ()
    {
        return this.deltaZ;
    }

    public void setDeltaZ(final short deltaZ)
    {
        this.deltaZ = deltaZ;
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

    public boolean isOnGround()
    {
        return this.onGround;
    }

    public void setOnGround(final boolean onGround)
    {
        this.onGround = onGround;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("entityId", this.entityId).append("deltaX", this.deltaX).append("deltaY", this.deltaY).append("deltaZ", this.deltaZ).append("yaw", this.yaw).append("pitch", this.pitch).append("onGround", this.onGround).toString();
    }
}
