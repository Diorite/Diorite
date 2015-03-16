package org.diorite.impl.connection.ping;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ServerPingServerDataSerializer implements JsonDeserializer<ServerPingServerData>, JsonSerializer<ServerPingServerData>
{
    @Override
    public ServerPingServerData deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext context)
    {
        final JsonObject localJsonObject = SmallGsonUtils.getAsObject(jsonElement, "version");
        return new ServerPingServerData(SmallGsonUtils.getAsString(localJsonObject, "name"), SmallGsonUtils.getAsInt(localJsonObject, "protocol"));
    }

    @Override
    public JsonElement serialize(final ServerPingServerData serverPingServerData, final Type type, final JsonSerializationContext context)
    {
        final JsonObject localJsonObject = new JsonObject();
        localJsonObject.addProperty("name", serverPingServerData.getName());
        localJsonObject.addProperty("protocol", serverPingServerData.getProtocol());
        return localJsonObject;
    }
}
