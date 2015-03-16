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
import com.mojang.authlib.GameProfile;

public class ServerPingPlayerSampleSerializer implements JsonDeserializer<ServerPingPlayerSample>, JsonSerializer<ServerPingPlayerSample>
{
    @Override
    public ServerPingPlayerSample deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext context)
    {
        final JsonObject jsonObject = SmallGsonUtils.getAsObject(jsonElement, "players");
        final ServerPingPlayerSample playerSample = new ServerPingPlayerSample(SmallGsonUtils.getAsInt(jsonObject, "max"), SmallGsonUtils.getAsInt(jsonObject, "online"));
        if (SmallGsonUtils.isArray(jsonObject, "sample"))
        {
            final JsonArray jsonElements = SmallGsonUtils.getAsArray(jsonObject, "sample");
            if (jsonElements.size() > 0)
            {
                final GameProfile[] gameProfiles = new GameProfile[jsonElements.size()];
                for (int i = 0; i < gameProfiles.length; i++)
                {
                    final JsonObject jsonObjectProfile = SmallGsonUtils.getAsObject(jsonElements.get(i), "player[" + i + "]");
                    final String str = SmallGsonUtils.getAsString(jsonObjectProfile, "id");
                    gameProfiles[i] = new GameProfile(UUID.fromString(str), SmallGsonUtils.getAsString(jsonObjectProfile, "name"));
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