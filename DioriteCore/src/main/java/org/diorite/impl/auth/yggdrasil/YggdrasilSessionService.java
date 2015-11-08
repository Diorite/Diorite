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

package org.diorite.impl.auth.yggdrasil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.auth.GameProfileImpl;
import org.diorite.impl.auth.GameProfiles;
import org.diorite.impl.auth.SessionService;
import org.diorite.impl.auth.exceptions.AuthenticationException;
import org.diorite.impl.auth.exceptions.AuthenticationUnavailableException;
import org.diorite.impl.auth.exceptions.InvalidCredentialsException;
import org.diorite.impl.auth.exceptions.TooManyRequestsException;
import org.diorite.impl.auth.exceptions.UserMigratedException;
import org.diorite.impl.auth.properties.PropertyMapSerializer;
import org.diorite.impl.auth.yggdrasil.request.JoinServerRequest;
import org.diorite.impl.auth.yggdrasil.request.ProfileSearchRequest;
import org.diorite.impl.auth.yggdrasil.response.HasJoinedResponse;
import org.diorite.impl.auth.yggdrasil.response.ProfileResponse;
import org.diorite.impl.auth.yggdrasil.response.ProfileSearchResultsResponse;
import org.diorite.impl.auth.yggdrasil.response.Response;
import org.diorite.auth.GameProfile;
import org.diorite.auth.PropertyMap;
import org.diorite.utils.DioriteUtils;
import org.diorite.utils.collections.maps.CaseInsensitiveMap;
import org.diorite.utils.json.adapters.JsonUUIDAdapter;
import org.diorite.utils.network.DioriteURLUtils;

@SuppressWarnings("HardcodedFileSeparator")
public class YggdrasilSessionService implements SessionService
{
    public static final int CONNECT_TIMEOUT = 15000;

    private static final String BASE_URL       = "https://sessionserver.mojang.com/session/minecraft/";
    private static final URL    JOIN_URL       = DioriteURLUtils.createURL(BASE_URL + "join");
    private static final URL    CHECK_URL      = DioriteURLUtils.createURL(BASE_URL + "hasJoined");
    private static final URL    PROFILE_URL    = DioriteURLUtils.createURL(BASE_URL + "profile/");
    private static final String API_BASE_URL   = "https://api.mojang.com/";
    private static final URL    NAMES_TO_UUIDS = DioriteURLUtils.createURL(API_BASE_URL + "profiles/minecraft");

    private final Gson gson = new GsonBuilder().registerTypeAdapter(UUID.class, new JsonUUIDAdapter(false)).registerTypeAdapter(ProfileSearchRequest.class, new ProfileSearchRequest.Serializer()).registerTypeAdapter(GameProfileImpl.class, new GameProfileImpl.Serializer()).registerTypeAdapter(PropertyMap.class, new PropertyMapSerializer()).registerTypeAdapter(ProfileSearchResultsResponse.class, new ProfileSearchResultsResponse.Serializer()).create();
    private final Proxy     proxy;
    private final String    clientToken;
    private final PublicKey publicKey;

    public YggdrasilSessionService(final Proxy proxy, final String clientToken)
    {
        this.proxy = proxy;
        this.clientToken = clientToken;
        try
        {
            //noinspection HardcodedFileSeparator
            final KeySpec spec = new X509EncodedKeySpec(IOUtils.toByteArray(YggdrasilSessionService.class.getResourceAsStream("/yggdrasil_session_pubkey.der")));
            final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.publicKey = keyFactory.generatePublic(spec);
        } catch (final Exception e)
        {
            //noinspection HardcodedFileSeparator
            throw new Error("Missing/invalid yggdrasil public key!");
        }
    }

    public YggdrasilSessionService(final Proxy proxy)
    {
        this(proxy, UUID.randomUUID().toString());
    }

    public Proxy getProxy()
    {
        return this.proxy;
    }

    public String getClientToken()
    {
        return this.clientToken;
    }

    public PublicKey getPublicKey()
    {
        return this.publicKey;
    }

    @Override
    public CaseInsensitiveMap<GameProfile> getUUIDsFromUsernames(final String... names) throws AuthenticationException
    {
        final ProfileSearchResultsResponse response = this.makeRequest(NAMES_TO_UUIDS, new ProfileSearchRequest(names), ProfileSearchResultsResponse.class);
        if ((response != null) && (response.getProfiles() != null))
        {
            final GameProfileImpl[] results = response.getProfiles();
            if (results.length == 0)
            {
                return new CaseInsensitiveMap<>(1);
            }
            final CaseInsensitiveMap<GameProfile> resultMap = new CaseInsensitiveMap<>(results.length);
            for (final GameProfileImpl result : results)
            {
                if (result == null)
                {
                    continue;
                }
                resultMap.put(result.getName(), result);
            }
            return resultMap;
        }
        return new CaseInsensitiveMap<>(1);
    }

    @Override
    public GameProfileImpl getGameProfile(String name) throws AuthenticationException
    {
        final GameProfile gp = this.getUUIDsFromUsernames(name).get(name);
        final UUID uuid = (gp == null) ? DioriteUtils.getCrackedUuid(name) : gp.getId();
        if (gp != null)
        {
            name = gp.getName();
        }

        final GameProfileImpl mgp = this.getGameProfile0(uuid);
        if (mgp == null)
        {
            GameProfiles.addEmptyEntry(name, uuid);
        }
        else
        {
            GameProfiles.addToCache(mgp);
        }
        return mgp;
    }

    private GameProfileImpl getGameProfile0(final UUID uuid) throws AuthenticationException
    {
        try
        {
            final URL url = new URL(PROFILE_URL, StringUtils.remove(uuid.toString(), '-') + "?unsigned=false");
            final ProfileResponse response = this.makeRequest(url, null, ProfileResponse.class);
            if ((response != null) && (response.getId() != null))
            {
                final GameProfileImpl result = new GameProfileImpl(response.getId(), response.getName());
                if (response.getProperties() != null)
                {
                    result.getProperties().putAll(response.getProperties());
                }
                return result;
            }
        } catch (final MalformedURLException e)
        {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public GameProfileImpl getGameProfile(final UUID uuid) throws AuthenticationException
    {
        final GameProfileImpl gp = this.getGameProfile0(uuid);
        if (gp == null)
        {
            GameProfiles.addEmptyEntry(null, uuid);
        }
        else
        {
            GameProfiles.addToCache(gp);
        }
        return gp;
    }

    @Override
    public void joinServer(final GameProfileImpl gameProfile, final String authenticationToken, final String serverId) throws AuthenticationException
    {
        this.makeRequest(JOIN_URL, new JoinServerRequest(authenticationToken, gameProfile.getId(), serverId), Response.class);
    }

    @Override
    public GameProfileImpl hasJoinedServer(final GameProfileImpl gameProfile, final String serverID) throws AuthenticationUnavailableException
    {
        final Map<String, Object> arguments = new HashMap<>(2);

        arguments.put("username", gameProfile.getName());
        arguments.put("serverId", serverID);

        final URL url = DioriteURLUtils.createURL(CHECK_URL, DioriteURLUtils.buildQuery(arguments));
        try
        {
            final HasJoinedResponse response = this.makeRequest(url, null, HasJoinedResponse.class);
            if ((response != null) && (response.getId() != null))
            {
                final GameProfileImpl result = new GameProfileImpl(response.getId(), gameProfile.getName());
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

    protected HttpURLConnection createUrlConnection(final URL url) throws IOException
    {
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection(this.proxy);
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setReadTimeout(CONNECT_TIMEOUT);
        connection.setUseCaches(false);
        return connection;
    }

    public String performPostRequest(final URL url, final String post, final String contentType) throws IOException
    {
        final HttpURLConnection connection = this.createUrlConnection(url);
        final byte[] bytes = post.getBytes(StandardCharsets.UTF_8);
        connection.setRequestProperty("Content-Type", contentType + "; charset=utf-8");
        connection.setRequestProperty("Content-Length", "" + bytes.length);
        connection.setDoOutput(true);
        try (final OutputStream outputStream = connection.getOutputStream())
        {
            IOUtils.write(bytes, outputStream);
        }
        try (InputStream inputStream = connection.getInputStream())
        {
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        }
    }

    public String performGetRequest(final URL url) throws IOException
    {
        try (final InputStream inputStream = this.createUrlConnection(url).getInputStream())
        {
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        }
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
                if ("Not Found".equals(result.getCause()))
                {
                    return null;
                }
                if ("ForbiddenOperationException".equals(result.getError()))
                {
                    throw new InvalidCredentialsException(result.getErrorMessage());
                }
                if ("TooManyRequestsException".equals(result.getError()))
                {
                    throw new TooManyRequestsException(result.getErrorMessage());
                }
                throw new AuthenticationException(result.getErrorMessage());
            }
            return result;
        } catch (final IOException e)
        {
            if (e.getMessage().contains("429 for URL"))
            {
                throw new TooManyRequestsException("Too many requests!", e);
            }
            throw new AuthenticationUnavailableException("Cannot contact authentication server", e);
        } catch (final JsonParseException | IllegalStateException e)
        {
            throw new AuthenticationUnavailableException("Cannot contact authentication server", e);
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("proxy", this.proxy).append("clientToken", this.clientToken).append("publicKey", this.publicKey).toString();
    }
}
