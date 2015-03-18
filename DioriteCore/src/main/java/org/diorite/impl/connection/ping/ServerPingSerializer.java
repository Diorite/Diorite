package org.diorite.impl.connection.ping;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.diorite.chat.component.BaseComponent;

public class ServerPingSerializer
        implements JsonDeserializer<ServerPing>, JsonSerializer<ServerPing>
{
    @Override
    public ServerPing deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext context)
    {
        final JsonObject status = SmallGsonUtils.getAsObject(jsonElement, "status");
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
            serverPing.setFavicon(SmallGsonUtils.getAsString(status, "favicon"));
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