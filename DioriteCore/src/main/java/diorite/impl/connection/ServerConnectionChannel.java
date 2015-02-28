package diorite.impl.connection;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.impl.connection.listeners.HandshakeListener;
import diorite.impl.connection.packets.PacketDecoder;
import diorite.impl.connection.packets.PacketEncoder;
import diorite.impl.connection.packets.PacketPrepender;
import diorite.impl.connection.packets.PacketSplitter;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class ServerConnectionChannel extends ChannelInitializer<Channel>
{
    public static final int IP_TOS          = 24;
    public static final int TIMEOUT_SECONDS = 30;
    final ServerConnection serverConnection;

    ServerConnectionChannel(final ServerConnection serverConnection)
    {
        this.serverConnection = serverConnection;
    }

    @Override
    protected void initChannel(final Channel channel) throws Exception
    {
        try
        {
            channel.config().setOption(ChannelOption.IP_TOS, IP_TOS);
        } catch (final ChannelException ignored)
        {
        }
        try
        {
            channel.config().setOption(ChannelOption.TCP_NODELAY, false);
        } catch (final ChannelException ignored)
        {
        }
        channel.pipeline().addLast("timeout", new ReadTimeoutHandler(TIMEOUT_SECONDS)).addLast("splitter", new PacketSplitter()).addLast("decoder", new PacketDecoder(EnumProtocolDirection.SERVERBOUND, this.serverConnection)).addLast("prepender", new PacketPrepender()).addLast("encoder", new PacketEncoder(EnumProtocolDirection.CLIENTBOUND, this.serverConnection)); //.addLast("legacy_query", new LegacyPingHandler(this.serverConnection)).addLast("splitter", new PacketSplitter()).addLast("decoder", new PacketDecoder(EnumProtocolDirection.SERVERBOUND)).addLast("prepender", new PacketPrepender()).addLast("encoder", new PacketEncoder(EnumProtocolDirection.CLIENTBOUND));
        final NetworkManager networkmanager = new NetworkManager(this.serverConnection.getServer());

        this.serverConnection.getConnections().add(networkmanager);
        channel.pipeline().addLast("packet_handler", networkmanager);
        networkmanager.setPacketListener(new HandshakeListener(this.serverConnection.getServer(), networkmanager));
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("serverConnection", this.serverConnection).toString();
    }
}
