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

public class ServerPingServerData
{
    private String name;
    private int    protocol;

    public ServerPingServerData(final String paramString, final int paramInt)
    {
        this.name = paramString;
        this.protocol = paramInt;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public int getProtocol()
    {
        return this.protocol;
    }

    public void setProtocol(final int protocol)
    {
        this.protocol = protocol;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).append("protocol", this.protocol).toString();
    }

    public static class Serializer implements JsonDeserializer<ServerPingServerData>, JsonSerializer<ServerPingServerData>
    {
        @Override
        public ServerPingServerData deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext context)
        {
            final JsonObject jsonObject = jsonElement.getAsJsonObject();
            return new ServerPingServerData(jsonObject.get("name").getAsString(), jsonObject.get("protocol").getAsInt());
        }

        @Override
        public JsonElement serialize(final ServerPingServerData serverPingServerData, final Type type, final JsonSerializationContext context)
        {
            final JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", serverPingServerData.getName());
            jsonObject.addProperty("protocol", serverPingServerData.getProtocol());
            return jsonObject;
        }
    }
}