package diorite.impl.connection.packets;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.impl.connection.EnumProtocolDirection;
import diorite.impl.connection.ServerConnection;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet<?>>
{
    private final EnumProtocolDirection protocolDirection;
    private final ServerConnection      serverConnection;

    public PacketEncoder(final EnumProtocolDirection protocolDirection, final ServerConnection serverConnection)
    {
        this.protocolDirection = protocolDirection;
        this.serverConnection = serverConnection;
    }

    @Override
    protected void encode(final ChannelHandlerContext context, final Packet<?> packet, final ByteBuf byteBuf) throws IOException
    {
        final Integer localInteger = context.channel().attr(this.serverConnection.protocolKey).get().getPacketID(this.protocolDirection, packet);
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
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("protocolDirection", this.protocolDirection).append("serverConnection", this.serverConnection).toString();
    }
}