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

@PacketClass(id = 0x25, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 12)
public class PacketPlayClientboundRelEntityMove extends PacketPlayClientbound
{
    private int     entityId; // ~5 bytes
    private short   deltaX; // 2 bytes
    private short   deltaY; // 2 bytes
    private short   deltaZ; // 2 bytes
    private boolean onGround; // 1 byte

    public PacketPlayClientboundRelEntityMove()
    {
    }

    public PacketPlayClientboundRelEntityMove(final int entityId, final short deltaX, final short deltaY, final short deltaZ, final boolean onGround)
    {
        this.entityId = entityId;
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.deltaZ = deltaZ;
        this.onGround = onGround;
    }

    @SuppressWarnings("MagicNumber")
    public PacketPlayClientboundRelEntityMove(final int entityId, final double deltaX, final double deltaY, final double deltaZ, final boolean onGround)
    {
        this.entityId = entityId;
        this.deltaX = (short) (deltaX * 32);
        this.deltaY = (short) (deltaY * 32);
        this.deltaZ = (short) (deltaZ * 32);
        this.onGround = onGround;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.entityId = data.readVarInt();
        this.deltaX = data.readShort();
        this.deltaY = data.readShort();
        this.deltaZ = data.readShort();
        this.onGround = data.readBoolean();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.entityId);
        data.writeShort(this.deltaX);
        data.writeShort(this.deltaY);
        data.writeShort(this.deltaZ);
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
