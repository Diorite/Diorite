/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.impl.protocol;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.diorite.impl.protocol.any.serverbound.H00Handshake;
import org.diorite.DioriteConfig.ProtocolSettings;
import org.diorite.core.protocol.ProtocolVersion;
import org.diorite.core.protocol.connection.ServerConnection;
import org.diorite.core.protocol.connection.internal.Packet;
import org.diorite.core.protocol.connection.internal.PacketType;
import org.diorite.core.protocol.connection.internal.Packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public abstract class PCProtocolVersion extends ProtocolVersion<PCProtocol>
{
    protected final boolean stable;
    protected final Set<String> aliases = new HashSet<>(3);
    protected final PacketsImpl packets;

    public PCProtocolVersion(PCProtocol protocol, int version, String versionName, boolean stable, ProtocolSettings settings)
    {
        super(protocol, version, versionName, settings);
        this.stable = stable;
        this.aliases.add(versionName);
        this.packets = new PacketsImpl(this);
        this.packets.addType(H00Handshake.class, H00Handshake::new);
    }

    @Override
    public PCProtocol getProtocol()
    {
        return super.getProtocol();
    }

    @Override
    public Packets getPackets()
    {
        return this.packets;
    }

    @Override
    public boolean isPacket(Class<? extends Packet> packet)
    {
        return this.packets.isRegistered(packet);
    }

    @Override
    public Set<String> getAliases()
    {
        return Collections.unmodifiableSet(this.aliases);
    }

    @Override
    public boolean isStable()
    {
        return this.stable;
    }

    @Override
    public void decode(ChannelHandlerContext context, ByteBuf byteBuf, List<Object> packets) throws Exception
    {
        if (byteBuf.readableBytes() == 0)
        {
            return;
        }
        int id = AbstractPacketDataSerializer.readVarInt(byteBuf);
        PacketType packetType = this.packets.getServerboundType(context.channel().attr(ServerConnection.protocolKey).get(), id);
        if (byteBuf.readableBytes() < packetType.getMinSize())
        {
            context.close();
            byteBuf.clear();
            throw new IOException("Packet is smaller than expected, found " + byteBuf.readableBytes() + " but expected min of: " + packetType.getMinSize() +
                                  " in packet type: " + packetType);
        }
        if (byteBuf.readableBytes() > packetType.getMaxSize())
        {
            context.close();
            byteBuf.clear();
            throw new IOException("Packet is larger than expected, found " + byteBuf.readableBytes() + " but expected max of: " + packetType.getMaxSize() +
                                  " in packet type: " + packetType);
        }
        Packet packet = packetType.createPacket();
        packet.decode(context, byteBuf);
        if (byteBuf.readableBytes() > 0)
        {
            throw new IOException("Packet was larger than expected, found " + byteBuf.readableBytes() + " bytes extra whilst reading packet: " + packetType);
        }
        packets.add(packet);
    }
}
