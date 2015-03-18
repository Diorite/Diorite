package com.mojang.authlib.yggdrasil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.net.URL;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.HttpAuthenticationService;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.exceptions.UserMigratedException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.response.ProfileSearchResultsResponse;
import com.mojang.authlib.yggdrasil.response.Response;
import com.mojang.util.UUIDTypeAdapter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class YggdrasilAuthenticationService extends HttpAuthenticationService
{
    private final String clientToken;
    private final Gson   gson;

    public YggdrasilAuthenticationService(final Proxy proxy, final String clientToken)
    {
        super(proxy);
        this.clientToken = clientToken;
        final GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(GameProfile.class, new GameProfileSerializer());
        builder.registerTypeAdapter(PropertyMap.class, new PropertyMap.Serializer());
        builder.registerTypeAdapter(UUID.class, new UUIDTypeAdapter());
        builder.registerTypeAdapter(ProfileSearchResultsResponse.class, new ProfileSearchResultsResponse.Serializer());
        this.gson = builder.create();
    }

    @Override
    public UserAuthentication createUserAuthentication(final Agent agent)
    {
        return new YggdrasilUserAuthentication(this, agent);
    }

    @Override
    public MinecraftSessionService createMinecraftSessionService()
    {
        return new YggdrasilMinecraftSessionService(this);
    }

    @Override
    public GameProfileRepository createProfileRepository()
    {
        return new YggdrasilGameProfileRepository(this);
    }

    protected <T extends Response> T makeRequest(final URL url, final Object input, final Class<T> classOfT) throws AuthenticationException
    {
        try
        {
            final String jsonResult = (input == null) ? this.performGetRequest(url) : this.performPostRequest(url, this.gson.toJson(input), "application/json");
            final T result = this.gson.fromJson(jsonResult, classOfT);
            if (result == null)
            {
                return null;
            }
            if (StringUtils.isNotBlank(result.getError()))
            {
                if ("UserMigratedException".equals(result.getCause()))
                {
                    throw new UserMigratedException(result.getErrorMessage());
                }
                if (result.getError().equals("ForbiddenOperationException"))
                {
                    throw new InvalidCredentialsException(result.getErrorMessage());
                }
                throw new AuthenticationException(result.getErrorMessage());
            }
            return result;
        } catch (final IOException | JsonParseException | IllegalStateException e)
        {
            throw new AuthenticationUnavailableException("Cannot contact authentication server", e);
        }
    }

    public String getClientToken()
    {
        return this.clientToken;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("clientToken", this.clientToken).append("gson", this.gson).toString();
    }

    private static class GameProfileSerializer
            implements JsonSerializer<GameProfile>, JsonDeserializer<GameProfile>
    {
        @Override
        public GameProfile deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
                throws JsonParseException
        {
            final JsonObject object = (JsonObject) json;
            final UUID id = object.has("id") ? (UUID) context.deserialize(object.get("id"), UUID.class) : null;
            final String name = object.has("name") ? object.getAsJsonPrimitive("name").getAsString() : null;
            return new GameProfile(id, name);
        }

        @Override
        public JsonElement serialize(final GameProfile src, final Type typeOfSrc, final JsonSerializationContext context)
        {
            final JsonObject result = new JsonObject();
            if (src.getId() != null)
            {
                result.add("id", context.serialize(src.getId()));
            }
            if (src.getName() != null)
            {
                result.addProperty("name", src.getName());
            }
            return result;
        }
    }
}
