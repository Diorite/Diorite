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