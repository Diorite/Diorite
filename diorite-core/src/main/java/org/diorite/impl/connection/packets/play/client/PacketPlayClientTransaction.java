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

package org.diorite.impl.connection.packets.play.client;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.play.PacketPlayClientListener;

@PacketClass(id = 0x0F, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.SERVERBOUND, size = 4)
public class PacketPlayClientTransaction extends PacketPlayClient
{
    private int     windowId; // 1 byte
    private short   actionNumber; // 2 bytes
    private boolean accepted;// 1 byte

    public PacketPlayClientTransaction()
    {
    }

    public PacketPlayClientTransaction(final int windowId, final short actionNumber, final boolean accepted)
    {
        this.windowId = windowId;
        this.actionNumber = actionNumber;
        this.accepted = accepted;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.windowId = data.readUnsignedByte();
        this.actionNumber = data.readShort();
        this.accepted = data.readBoolean();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeByte(this.windowId);
        data.writeShort(this.actionNumber);
        data.writeBoolean(this.accepted);
    }

    @Override
    public void handle(final PacketPlayClientListener listener)
    {
        listener.handle(this);
    }

    public int getWindowId()
    {
        return this.windowId;
    }

    public void setWindowId(final int windowId)
    {
        this.windowId = windowId;
    }

    public short getActionNumber()
    {
        return this.actionNumber;
    }

    public void setActionNumber(final short actionNumber)
    {
        this.actionNumber = actionNumber;
    }

    public boolean isAccepted()
    {
        return this.accepted;
    }

    public void setAccepted(final boolean accepted)
    {
        this.accepted = accepted;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("windowId", this.windowId).append("actionNumber", this.actionNumber).append("accepted", this.accepted).toString();
    }
}
