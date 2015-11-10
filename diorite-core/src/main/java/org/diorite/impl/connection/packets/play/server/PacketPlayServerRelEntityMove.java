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

@PacketClass(id = 0x15, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 9)
public class PacketPlayServerRelEntityMove extends PacketPlayServer
{
    private int     entityId; // ~5 bytes
    private byte    deltaX; // 1 byte
    private byte    deltaY; // 1 byte
    private byte    deltaZ; // 1 byte
    private boolean onGround; // 1 byte

    public PacketPlayServerRelEntityMove()
    {
    }

    public PacketPlayServerRelEntityMove(final int entityId, final byte deltaX, final byte deltaY, final byte deltaZ, final boolean onGround)
    {
        this.entityId = entityId;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaZ = deltaZ;
        this.onGround = onGround;
    }

    @SuppressWarnings("MagicNumber")
    public PacketPlayServerRelEntityMove(final int entityId, final double deltaX, final double deltaY, final double deltaZ, final boolean onGround)
    {
        this.entityId = entityId;
        this.deltaX = (byte) (deltaX * 32);
        this.deltaY = (byte) (deltaY * 32);
        this.deltaZ = (byte) (deltaZ * 32);
        this.onGround = onGround;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.entityId = data.readVarInt();
        this.deltaX = data.readByte();
        this.deltaY = data.readByte();
        this.deltaZ = data.readByte();
        this.onGround = data.readBoolean();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.entityId);
        data.writeByte(this.deltaX);
        data.writeByte(this.deltaY);
        data.writeByte(this.deltaZ);
        data.writeBoolean(this.onGround);
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

    public byte getDeltaX()
    {
        return this.deltaX;
    }

    public void setDeltaX(final byte deltaX)
    {
        this.deltaX = deltaX;
    }

    public byte getDeltaY()
    {
        return this.deltaY;
    }

    public void setDeltaY(final byte deltaY)
    {
        this.deltaY = deltaY;
    }

    public byte getDeltaZ()
    {
        return this.deltaZ;
    }

    public void setDeltaZ(final byte deltaZ)
    {
        this.deltaZ = deltaZ;
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("entityId", this.entityId).append("deltaX", this.deltaX).append("deltaY", this.deltaY).append("deltaZ", this.deltaZ).append("onGround", this.onGround).toString();
    }
}
