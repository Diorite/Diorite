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

@PacketClass(id = 0x0D, protocol = EnumProtocol.PLAY, direction = EnumProtocolDirection.CLIENTBOUND, size = 10)
public class PacketPlayServerCollect extends PacketPlayServer
{
    private int collectedEntityId; // ~5 bytes
    private int collecterEntityId; // ~5 bytes

    public PacketPlayServerCollect()
    {
    }

    public PacketPlayServerCollect(final int collectedEntityId, final int collecterEntityId)
    {
        this.collectedEntityId = collectedEntityId;
        this.collecterEntityId = collecterEntityId;
    }

    public int getCollectedEntityId()
    {
        return this.collectedEntityId;
    }

    public void setCollectedEntityId(final int collectedEntityId)
    {
        this.collectedEntityId = collectedEntityId;
    }

    public int getCollecterEntityId()
    {
        return this.collecterEntityId;
    }

    public void setCollecterEntityId(final int collecterEntityId)
    {
        this.collecterEntityId = collecterEntityId;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.collectedEntityId = data.readVarInt();
        this.collecterEntityId = data.readVarInt();
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeVarInt(this.collectedEntityId);
        data.writeVarInt(this.collecterEntityId);
    }

    @Override
    public void handle(final PacketPlayServerListener listener)
    {
        listener.handle(this);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("collectedEntityId", this.collectedEntityId).append("collecterEntityId", this.collecterEntityId).toString();
    }
}
