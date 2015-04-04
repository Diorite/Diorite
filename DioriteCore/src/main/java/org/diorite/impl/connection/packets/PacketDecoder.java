package org.diorite.impl.connection.packets;


import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.ServerConnection;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class PacketDecoder extends ByteToMessageDecoder
{
    private final EnumProtocolDirection protocolDirection;
    private final ServerConnection      serverConnection;

    public PacketDecoder(final EnumProtocolDirection protocolDirection, final ServerConnection serverConnection)
    {
        this.protocolDirection = protocolDirection;
        this.serverConnection = serverConnection;
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
        final Packet<?> packet = context.channel().attr(this.serverConnection.protocolKey).get().createPacket(this.protocolDirection, i);
        if (packet == null)
        {
            throw new IOException("Bad packet id " + i + " (" + Integer.toHexString(i) + ")");
        }
        packet.readPacket(dataSerializer);
        if (dataSerializer.readableBytes() > 0)
        {
            //noinspection HardcodedFileSeparator
            throw new IOException("Packet " + context.channel().attr(this.serverConnection.protocolKey).get().getStatus() + "/" + i + " (" + packet.getClass().getSimpleName() + ") was larger than I expected, found " + dataSerializer.readableBytes() + " bytes extra whilst reading packet " + i);
        }
        packets.add(packet);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("protocolDirection", this.protocolDirection).append("serverConnection", this.serverConnection).toString();
    }
}