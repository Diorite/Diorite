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

package org.diorite.impl.connection.ping;


import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.chat.component.BaseComponent;

public class ServerPing
{
    private BaseComponent          motd;
    private ServerPingPlayerSample playerData;
    private ServerPingServerData   serverData;
    private String                 favicon;

    public ServerPing()
    {
    }

    public ServerPing(final BaseComponent motd, final String favicon, final ServerPingServerData serverData, final ServerPingPlayerSample playerData)
    {
        this.motd = motd;
        this.favicon = favicon;
        this.serverData = serverData;
        this.playerData = playerData;
    }

    public BaseComponent getMotd()
    {
        return this.motd;
    }

    public void setMotd(final BaseComponent motd)
    {
        this.motd = motd;
    }

    public String getFavicon()
    {
        return this.favicon;
    }

    public void setFavicon(final String favicon)
    {
        this.favicon = favicon;
    }

    public ServerPingServerData getServerData()
    {
        return this.serverData;
    }

    public void setServerData(final ServerPingServerData serverData)
    {
        this.serverData = serverData;
    }

    public ServerPingPlayerSample getPlayerData()
    {
        return this.playerData;
    }

    public void setPlayerData(final ServerPingPlayerSample playerData)
    {
        this.playerData = playerData;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("motd", this.motd).append("playerData", this.playerData).append("serverData", this.serverData).append("favicon", this.favicon).toString();
    }

    public static class Serializer implements JsonDeserializer<ServerPing>, JsonSerializer<ServerPing>
    {
        @Override
        public ServerPing deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext context)
        {
            final JsonObject status = jsonElement.getAsJsonObject();
            final ServerPing serverPing = new ServerPing();
            if (status.has("description"))
            {
                serverPing.setMotd(context.deserialize(status.get("description"), BaseComponent.class));
            }
            if (status.has("players"))
            {
                serverPing.setPlayerData(context.deserialize(status.get("players"), ServerPingPlayerSample.class));
            }
            if (status.has("version"))
            {
                serverPing.setServerData(context.deserialize(status.get("version"), ServerPingServerData.class));
            }
            if (status.has("favicon"))
            {
                serverPing.setFavicon(status.get("favicon").getAsString());
            }
            return serverPing;
        }

        @Override
        public JsonElement serialize(final ServerPing serverPing, final Type type, final JsonSerializationContext context)
        {
            final JsonObject jsonObject = new JsonObject();
            if (serverPing.getMotd() != null)
            {
                jsonObject.add("description", context.serialize(serverPing.getMotd()));
            }
            if (serverPing.getPlayerData() != null)
            {
                jsonObject.add("players", context.serialize(serverPing.getPlayerData()));
            }
            if (serverPing.getPlayerData() != null)
            {
                jsonObject.add("version", context.serialize(serverPing.getServerData()));
            }
            if (serverPing.getFavicon() != null)
            {
                jsonObject.addProperty("favicon", serverPing.getFavicon());
            }
            return jsonObject;
        }
    }
}