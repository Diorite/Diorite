package com.mojang.authlib.yggdrasil;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Iterables;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.HttpAuthenticationService;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.minecraft.HttpMinecraftSessionService;
import com.mojang.authlib.minecraft.InsecureTextureException;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.yggdrasil.request.JoinMinecraftServerRequest;
import com.mojang.authlib.yggdrasil.response.HasJoinedMinecraftServerResponse;
import com.mojang.authlib.yggdrasil.response.MinecraftProfilePropertiesResponse;
import com.mojang.authlib.yggdrasil.response.MinecraftTexturesPayload;
import com.mojang.authlib.yggdrasil.response.Response;
import com.mojang.util.UUIDTypeAdapter;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class YggdrasilMinecraftSessionService extends HttpMinecraftSessionService
{
    public static final  int    EXPIRE_TIME = 6;
    private static final Logger LOGGER      = LogManager.getLogger();
    private static final String BASE_URL    = "https://sessionserver.mojang.com/session/minecraft/";
    private static final URL    JOIN_URL    = HttpAuthenticationService.constantURL(BASE_URL + "join");
    private static final URL    CHECK_URL   = HttpAuthenticationService.constantURL(BASE_URL + "hasJoined");
    private static final URL    PROFILE_URL = HttpAuthenticationService.constantURL(BASE_URL + "profile");
    private final PublicKey publicKey;
    private final Gson                                   gson             = new GsonBuilder().registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).create();
    private final LoadingCache<GameProfile, GameProfile> insecureProfiles = CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_TIME, TimeUnit.HOURS).build(new CacheLoader<GameProfile, GameProfile>()
    {

        @Override
        public GameProfile load(final GameProfile key)
                throws Exception
        {
            return YggdrasilMinecraftSessionService.this.fillGameProfile(key, false);
        }
    });

    protected YggdrasilMinecraftSessionService(final AuthenticationService authenticationService)
    {
        super(authenticationService);
        try
        {
            //noinspection HardcodedFileSeparator
            final KeySpec spec = new X509EncodedKeySpec(IOUtils.toByteArray(YggdrasilMinecraftSessionService.class.getResourceAsStream("/yggdrasil_session_pubkey.der")));
            final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.publicKey = keyFactory.generatePublic(spec);
        } catch (final Exception e)
        {
            //noinspection HardcodedFileSeparator
            throw new Error("Missing/invalid yggdrasil public key!");
        }
    }

    @Override
    public void joinServer(final GameProfile profile, final String authenticationToken, final String serverId)
            throws AuthenticationException
    {
        final JoinMinecraftServerRequest request = new JoinMinecraftServerRequest();
        request.accessToken = authenticationToken;
        request.selectedProfile = profile.getId();
        request.serverId = serverId;

        this.getAuthenticationService().makeRequest(JOIN_URL, request, Response.class);
    }

    @Override
    public GameProfile hasJoinedServer(final GameProfile user, final String serverId)
            throws AuthenticationUnavailableException
    {
        final Map<String, Object> arguments = new HashMap<>(2);

        arguments.put("username", user.getName());
        arguments.put("serverId", serverId);

        final URL url = HttpAuthenticationService.concatenateURL(CHECK_URL, HttpAuthenticationService.buildQuery(arguments));
        try
        {
            final HasJoinedMinecraftServerResponse response = this.getAuthenticationService().makeRequest(url, null, HasJoinedMinecraftServerResponse.class);
            if ((response != null) && (response.getId() != null))
            {
                final GameProfile result = new GameProfile(response.getId(), user.getName());
                if (response.getProperties() != null)
                {
                    result.getProperties().putAll(response.getProperties());
                }
                return result;
            }
            return null;
        } catch (final AuthenticationUnavailableException e)
        {
            throw e;
        } catch (final AuthenticationException ignored)
        {
        }
        return null;
    }

    @Override
    public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> getTextures(final GameProfile profile, final boolean requireSecure)
    {
        final Property textureProperty = Iterables.getFirst(profile.getProperties().get("textures"), null);
        if (textureProperty == null)
        {
            return new EnumMap<>(MinecraftProfileTexture.Type.class);
        }
        if (requireSecure)
        {
            if (! textureProperty.hasSignature())
            {
                LOGGER.error("Signature is missing from textures payload");
                throw new InsecureTextureException("Signature is missing from textures payload");
            }
            if (! textureProperty.isSignatureValid(this.publicKey))
            {
                LOGGER.error("Textures payload has been tampered with (signature invalid)");
                throw new InsecureTextureException("Textures payload has been tampered with (signature invalid)");
            }
        }
        final MinecraftTexturesPayload result;
        try
        {
            final String json = new String(Base64.decodeBase64(textureProperty.getValue()), StandardCharsets.UTF_8);
            result = this.gson.fromJson(json, MinecraftTexturesPayload.class);
        } catch (final JsonParseException e)
        {
            LOGGER.error("Could not decode textures payload", e);
            return new EnumMap<>(MinecraftProfileTexture.Type.class);
        }
        return (result.getTextures() == null) ? new EnumMap<>(MinecraftProfileTexture.Type.class) : result.getTextures();
    }

    @Override
    public GameProfile fillProfileProperties(final GameProfile profile, final boolean requireSecure)
    {
        if (profile.getId() == null)
        {
            return profile;
        }
        if (! requireSecure)
        {
            return this.insecureProfiles.getUnchecked(profile);
        }
        return this.fillGameProfile(profile, true);
    }

    protected GameProfile fillGameProfile(final GameProfile profile, final boolean requireSecure)
    {
        try
        {
            URL url = HttpAuthenticationService.constantURL(PROFILE_URL + UUIDTypeAdapter.fromUUID(profile.getId()));
            url = HttpAuthenticationService.concatenateURL(url, "unsigned=" + (! requireSecure));
            final MinecraftProfilePropertiesResponse response = this.getAuthenticationService().makeRequest(url, null, MinecraftProfilePropertiesResponse.class);
            if (response == null)
            {
                LOGGER.debug("Couldn't fetch profile properties for " + profile + " as the profile does not exist");
                return profile;
            }
            final GameProfile result = new GameProfile(response.getId(), response.getName());
            result.getProperties().putAll(response.getProperties());
            profile.getProperties().putAll(response.getProperties());
            LOGGER.debug("Successfully fetched profile properties for " + profile);
            return result;
        } catch (final AuthenticationException e)
        {
            LOGGER.warn("Couldn't look up profile properties for " + profile, e);
        }
        return profile;
    }

    @Override
    public YggdrasilAuthenticationService getAuthenticationService()
    {
        return (YggdrasilAuthenticationService) super.getAuthenticationService();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("publicKey", this.publicKey).append("gson", this.gson).append("insecureProfiles", this.insecureProfiles).toString();
    }
}
