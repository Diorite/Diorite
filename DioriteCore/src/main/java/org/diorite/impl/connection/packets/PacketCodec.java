package org.diorite.impl.connection.packets;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.ConnectionHandler;
import org.diorite.impl.connection.EnumProtocolDirection;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

public class PacketCodec extends ByteToMessageCodec<Packet<?>>
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