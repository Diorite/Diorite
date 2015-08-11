package org.diorite.impl.connection.ping;

import java.lang.reflect.Type;
import java.util.UUID;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.auth.GameProfile;

public class ServerPingPlayerSample
{
    private int           maxPlayers;
    private int           onlinePlayers;
    private GameProfile[] profiles;

    public ServerPingPlayerSample(final int maxPlayers, final int onlinePlayers, final GameProfile... profiles)
    {
        this.maxPlayers = maxPlayers;
        this.onlinePlayers = onlinePlayers;
        this.profiles = profiles;
    }

    public ServerPingPlayerSample(final int maxPlayers, final int onlinePlayers)
    {
        this.maxPlayers = maxPlayers;
        this.onlinePlayers = onlinePlayers;
    }

    public int getMaxPlayers()
    {
        return this.maxPlayers;
    }

    public void setMaxPlayers(final int maxPlayers)
    {
        this.maxPlayers = maxPlayers;
    }

    public int getOnlinePlayers()
    {
        return this.onlinePlayers;
    }

    public void setOnlinePlayers(final int onlinePlayers)
    {
        this.onlinePlayers = onlinePlayers;
    }

    public GameProfile[] getProfiles()
    {
        return this.profiles;
    }

    public void setProfiles(final GameProfile[] profiles)
    {
        this.profiles = profiles;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("maxPlayers", this.maxPlayers).append("onlinePlayers", this.onlinePlayers).append("profiles", this.profiles).toString();
    }

    public static class Serializer implements JsonDeserializer<ServerPingPlayerSample>, JsonSerializer<ServerPingPlayerSample>
    {
        @Override
        public ServerPingPlayerSample deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext context)
        {
            final JsonObject jsonObject = jsonElement.getAsJsonObject();
            final ServerPingPlayerSample playerSample = new ServerPingPlayerSample(jsonObject.get("max").getAsInt(), jsonObject.get("online").getAsInt());
            if (jsonObject.has("sample"))
            {
                final JsonArray sample = jsonObject.get("sample").getAsJsonArray();
                if (sample.size() > 0)
                {
                    final GameProfile[] gameProfiles = new GameProfile[sample.size()];
                    for (int i = 0; i < gameProfiles.length; i++)
                    {
                        final JsonObject profile = sample.get(i).getAsJsonObject();
                        final String str = profile.get("id").getAsString();
                        gameProfiles[i] = new GameProfile(UUID.fromString(str), profile.get("name").getAsString());
                    }
                    playerSample.setProfiles(gameProfiles);
                }
            }
            return playerSample;
        }

        @Override
        public JsonElement serialize(final ServerPingPlayerSample playerSample, final Type type, final JsonSerializationContext context)
        {
            final JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("max", playerSample.getMaxPlayers());
            jsonObject.addProperty("online", playerSample.getOnlinePlayers());
            if ((playerSample.getProfiles() != null) && (playerSample.getProfiles().length > 0))
            {
                final JsonArray jsonElements = new JsonArray();
                for (int i = 0; i < playerSample.getProfiles().length; i++)
                {
                    final JsonObject jsonObjectProfile = new JsonObject();
                    final UUID uuid = playerSample.getProfiles()[i].getId();
                    jsonObjectProfile.addProperty("id", (uuid == null) ? "" : uuid.toString());
                    jsonObjectProfile.addProperty("name", playerSample.getProfiles()[i].getName());
                    jsonElements.add(jsonObjectProfile);
                }
                jsonObject.add("sample", jsonElements);
            }
            return jsonObject;
        }
    }
}