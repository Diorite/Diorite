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

package org.diorite.impl.connection.packets.status.server;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.connection.EnumProtocol;
import org.diorite.impl.connection.EnumProtocolDirection;
import org.diorite.impl.connection.packets.PacketClass;
import org.diorite.impl.connection.packets.PacketDataSerializer;
import org.diorite.impl.connection.packets.status.PacketStatusServerListener;
import org.diorite.impl.connection.ping.ServerPing;
import org.diorite.impl.connection.ping.ServerPingPlayerSample;
import org.diorite.impl.connection.ping.ServerPingServerData;
import org.diorite.chat.component.BaseComponent;
import org.diorite.chat.component.TextComponent;
import org.diorite.chat.component.TranslatableComponent;
import org.diorite.chat.component.serialize.ComponentSerializer;
import org.diorite.chat.component.serialize.TextComponentSerializer;
import org.diorite.chat.component.serialize.TranslatableComponentSerializer;

@PacketClass(id = 0x00, protocol = EnumProtocol.STATUS, direction = EnumProtocolDirection.CLIENTBOUND, size = 512)
public class PacketStatusServerServerInfo extends PacketStatusServer
{
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(ServerPingServerData.class, new ServerPingServerData.Serializer()).registerTypeAdapter(ServerPingPlayerSample.class, new ServerPingPlayerSample.Serializer()).registerTypeAdapter(ServerPing.class, new ServerPing.Serializer()).registerTypeAdapter(BaseComponent.class, new ComponentSerializer()).registerTypeAdapter(TextComponent.class, new TextComponentSerializer()).registerTypeAdapter(TranslatableComponent.class, new TranslatableComponentSerializer()).create();
    private ServerPing serverPing;

    public PacketStatusServerServerInfo()
    {
    }

    public PacketStatusServerServerInfo(final ServerPing serverPing)
    {
        this.serverPing = serverPing;
    }

    @Override
    public void readPacket(final PacketDataSerializer data) throws IOException
    {
        this.serverPing = GSON.fromJson(data.readText(Short.MAX_VALUE), ServerPing.class);
    }

    @Override
    public void writeFields(final PacketDataSerializer data) throws IOException
    {
        data.writeText(GSON.toJson(this.serverPing));
    }

    @Override
    public void handle(final PacketStatusServerListener listener)
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
