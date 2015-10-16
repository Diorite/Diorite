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

package org.diorite.impl.connection.packets;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.ByteToMessageCodec.PacketByteToMessageCodec;
import org.diorite.impl.connection.ConnectionHandler;
import org.diorite.impl.connection.EnumProtocolDirection;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class PacketCodec extends PacketByteToMessageCodec
{
    private final ConnectionHandler connectionHandler;

    public PacketCodec(final ConnectionHandler connectionHandler)
    {
        this.connectionHandler = connectionHandler;
    }

    @Override
    protected void encode(final ChannelHandlerContext context, final Packet<?> packet, final ByteBuf byteBuf) throws IOException
    {
        final Integer localInteger = context.channel().attr(this.connectionHandler.getProtocolKey()).get().getPacketID(EnumProtocolDirection.CLIENTBOUND, packet);
        if (localInteger == null)
        {
            throw new IOException("Can't serialize unregistered packet, " + packet);
        }
        final PacketDataSerializer dataSerializer = new PacketDataSerializer(byteBuf);
        dataSerializer.writeVarInt(localInteger);
        try
        {
            packet.writePacket(dataSerializer);
        } catch (final Throwable throwable)
        {
            throwable.printStackTrace();
        }
    }

    @Override
    protected void decode(final ChannelHandlerContext context, final ByteBuf byteBuf, final List<Object> packets) throws InstantiationException, IllegalAccessException, IOException
    {
        if (byteBuf.readableBytes() == 0)
        {
            return;
        }
        final PacketDataSerializer dataSerializer = new PacketDataSerializer(byteBuf);
        final int i = dataSerializer.readVarInt();
        final Packet<?> packet = context.channel().attr(this.connectionHandler.getProtocolKey()).get().createPacket(EnumProtocolDirection.SERVERBOUND, i);
        if (packet == null)
        {
            throw new IOException("Bad packet id " + i + " (" + Integer.toHexString(i) + ")");
        }
        packet.readPacket(dataSerializer);
        if (dataSerializer.readableBytes() > 0)
        {
            //noinspection HardcodedFileSeparator
            throw new IOException("Packet " + context.channel().attr(this.connectionHandler.getProtocolKey()).get().getStatus() + "/" + i + " (" + packet.getClass().getSimpleName() + ") was larger than I expected, found " + dataSerializer.readableBytes() + " bytes extra whilst reading packet " + i);
        }
        packets.add(packet);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("connectionHandler", this.connectionHandler).toString();
    }
}