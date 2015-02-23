package diorite.impl.connection.packets;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.impl.Main;
import diorite.impl.connection.EnumProtocolDirection;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import diorite.impl.connection.ServerConnection;

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
    protected void encode(final ChannelHandlerContext context, final Packet<?> paramPacket, final ByteBuf paramByteBuf) throws IOException
    {
        Main.debug("encode packet: " + context + ", " + paramPacket + ", " + paramByteBuf);
        final Integer localInteger = context.channel().attr(this.serverConnection.protocolKey).get().getPacketID(this.protocolDirection, paramPacket);
        if (localInteger == null)
        {
            throw new IOException("Can't serialize unregistered packet");
        }
        final PacketDataSerializer dataSerializer = new PacketDataSerializer(paramByteBuf);
        dataSerializer.writeVarInt(localInteger);
        try
        {
            paramPacket.writePacket(dataSerializer);
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