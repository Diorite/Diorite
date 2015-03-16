package org.diorite.impl.connection.packets.status.out;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.diorite.chat.BaseComponent;
import org.diorite.chat.TextComponent;
import org.diorite.chat.TranslatableComponent;
import org.diorite.chat.serialize.ComponentSerializer;
import org.diorite.chat.serialize.TextComponentSerializer;
import org.diorite.chat.serialize.TranslatableComponentSerializer;
import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.status.PacketStatusOutListener;
import org.diorite.impl.connection.ping.ServerPing;
import org.diorite.impl.connection.ping.ServerPingPlayerSample;
import org.diorite.impl.connection.ping.ServerPingPlayerSampleSerializer;
import org.diorite.impl.connection.ping.ServerPingSerializer;
import org.diorite.impl.connection.ping.ServerPingServerData;
import org.diorite.impl.connection.ping.ServerPingServerDataSerializer;

@PacketClass(id = 0x00, protocol = EnumProtocol.STATUS, direction = EnumProtocolDirection.CLIENTBOUND)
public class PacketStatusOutServerInfo implements PacketStatusOut
{
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(ServerPingServerData.class, new ServerPingServerDataSerializer()).registerTypeAdapter(ServerPingPlayerSample.class, new ServerPingPlayerSampleSerializer()).registerTypeAdapter(ServerPing.class, new ServerPingSerializer()).registerTypeAdapter(BaseComponent.class, new ComponentSerializer()).registerTypeAdapter(TextComponent.class, new TextComponentSerializer()).registerTypeAdapter(TranslatableComponent.class, new TranslatableComponentSerializer()).create();
    private ServerPing serverPing;

    public PacketStatusOutServerInfo()
    {
    }

    public PacketStatusOutServerInfo(final ServerPing serverPing)
    {
        this.serverPing = serverPing;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.serverPing = GSON.fromJson(data.readText(Short.MAX_VALUE), ServerPing.class);
    }

    @Override
    public void writePacket(final PacketDataSerializer data) throws IOException
    {
        data.writeText(GSON.toJson(this.serverPing));
    }

    @Override
    public void handle(final PacketStatusOutListener listener)
    {
        listener.handle(this);
    }

    public ServerPing getServerPing()
    {
        return this.serverPing;
    }

    public void setServerPing(final ServerPing serverPing)
    {
        this.serverPing = serverPing;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("serverPing", this.serverPing).toString();
    }
}
