/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.gameprofile.internal.yggdrasil;

import javax.annotation.Nullable;

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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import org.apache.commons.io.IOUtils;

import org.diorite.gameprofile.GameProfile;
import org.diorite.gameprofile.GameProfiles;
import org.diorite.gameprofile.PropertyMap;
import org.diorite.gameprofile.SessionService;
import org.diorite.gameprofile.internal.GameProfileImpl;
import org.diorite.gameprofile.internal.JsonUUIDAdapter;
import org.diorite.gameprofile.internal.URLUtils;
import org.diorite.gameprofile.internal.exceptions.AuthenticationException;
import org.diorite.gameprofile.internal.exceptions.AuthenticationUnavailableException;
import org.diorite.gameprofile.internal.exceptions.InvalidCredentialsException;
import org.diorite.gameprofile.internal.exceptions.TooManyRequestsException;
import org.diorite.gameprofile.internal.exceptions.UserMigratedException;
import org.diorite.gameprofile.internal.properties.PropertyMapSerializer;
import org.diorite.gameprofile.internal.yggdrasil.request.JoinServerRequest;
import org.diorite.gameprofile.internal.yggdrasil.request.ProfileSearchRequest;
import org.diorite.gameprofile.internal.yggdrasil.response.HasJoinedResponse;
import org.diorite.gameprofile.internal.yggdrasil.response.ProfileResponse;
import org.diorite.gameprofile.internal.yggdrasil.response.ProfileSearchResultsResponse;
import org.diorite.gameprofile.internal.yggdrasil.response.Response;

public class YggdrasilSessionService implements SessionService
{
    public static final int CONNECT_TIMEOUT = 15000;

    private static final String BASE_URL       = "https://sessionserver.mojang.com/session/minecraft/";
    private static final URL    JOIN_URL       = URLUtils.createURL(BASE_URL + "join");
    private static final URL    CHECK_URL      = URLUtils.createURL(BASE_URL + "hasJoined");
    private static final URL    PROFILE_URL    = URLUtils.createURL(BASE_URL + "profile/");
    private static final String API_BASE_URL   = "https://api.mojang.com/";
    private static final URL    NAMES_TO_UUIDS = URLUtils.createURL(API_BASE_URL + "profiles/minecraft");

    private final Gson gson = new GsonBuilder().registerTypeAdapter(UUID.class, new JsonUUIDAdapter(false))
                                               .registerTypeAdapter(ProfileSearchRequest.class, new ProfileSearchRequest.Serializer())
                                               .registerTypeAdapter(GameProfileImpl.class, new GameProfileImpl.Serializer())
                                               .registerTypeAdapter(PropertyMap.class, new PropertyMapSerializer())
                                               .registerTypeAdapter(ProfileSearchResultsResponse.class, new ProfileSearchResultsResponse.Serializer()).create();
    private final Proxy        proxy;
    private final String       clientToken;
    private final PublicKey    publicKey;
    private final GameProfiles gameProfiles;

    public YggdrasilSessionService(Proxy proxy, String clientToken)
    {
        this.proxy = proxy;
        this.clientToken = clientToken;
        try
        {
            //noinspection HardcodedFileSeparator
            KeySpec spec = new X509EncodedKeySpec(IOUtils.toByteArray(YggdrasilSessionService.class.getResourceAsStream("/yggdrasil_session_pubkey.der")));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            this.publicKey = keyFactory.generatePublic(spec);
        }
        catch (Exception e)
        {
            //noinspection HardcodedFileSeparator
            throw new Error("Missing/invalid yggdrasil public key!");
        }
        this.gameProfiles = new GameProfiles(this);
    }

    public YggdrasilSessionService(Proxy proxy)
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
    public GameProfiles getCache()
    {
        return this.gameProfiles;
    }

    @Override
    public Map<String, GameProfile> getUUIDsFromUsernames(String... names) throws AuthenticationException
    {
        ProfileSearchResultsResponse response = this.makeRequest(NAMES_TO_UUIDS, new ProfileSearchRequest(names), ProfileSearchResultsResponse.class);
        if ((response != null) && (response.getProfiles() != null))
        {
            GameProfileImpl[] results = response.getProfiles();
            if (results.length == 0)
            {
                return Collections.emptyMap();
            }
            Map<String, GameProfile> resultMap = Collections.unmodifiableMap(new TreeMap<>(String.CASE_INSENSITIVE_ORDER));
            for (GameProfileImpl result : results)
            {
                if (result == null)
                {
                    continue;
                }
                resultMap.put(result.getName(), result);
            }
            return resultMap;
        }
        return Collections.emptyMap();
    }

    @Override
    public GameProfileImpl getGameProfile(String name) throws AuthenticationException
    {
        GameProfile gp = this.getUUIDsFromUsernames(name).get(name);
        UUID uuid = ((gp == null) || (gp.getId() == null)) ? GameProfile.getCrackedUuid(name) : gp.getId();
        if (gp != null)
        {
            name = gp.getName();
        }

        GameProfileImpl mgp = this.getGameProfile0(uuid);
        if (mgp == null)
        {
            this.gameProfiles.addEmptyEntry(name, uuid);
        }
        else
        {
            this.gameProfiles.addToCache(mgp);
        }
        return mgp;
    }

    @SuppressWarnings("MagicNumber")
    private static String uuidToSimpleString(UUID uuid)
    {
        String toString = uuid.toString();
        char[] result = new char[32];
        int k = 0;
        for (int i = 0; i < 36; i++)
        {
            char c = toString.charAt(i);
            if (c != '-')
            {
                result[k++] = c;
            }
        }
        return new String(result);
    }

    @Nullable
    private GameProfileImpl getGameProfile0(UUID uuid) throws AuthenticationException
    {
        try
        {
            URL url = new URL(PROFILE_URL, uuidToSimpleString(uuid) + "?unsigned=false");
            ProfileResponse response = this.makeRequest(url, null, ProfileResponse.class);
            if ((response != null) && (response.getId() != null))
            {
                GameProfileImpl result = new GameProfileImpl(response.getId(), response.getName());
                if (response.getProperties() != null)
                {
                    result.getProperties().putAll(response.getProperties());
                }
                return result;
            }
        }
        catch (MalformedURLException e)
        {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public GameProfileImpl getGameProfile(UUID uuid) throws AuthenticationException
    {
        GameProfileImpl gp = this.getGameProfile0(uuid);
        if (gp == null)
        {
            this.gameProfiles.addEmptyEntry(null, uuid);
        }
        else
        {
            this.gameProfiles.addToCache(gp);
        }
        return gp;
    }

    @Override
    public void joinServer(GameProfile gameProfile, String authenticationToken, String serverId) throws AuthenticationException
    {
        this.makeRequest(JOIN_URL, new JoinServerRequest(authenticationToken, gameProfile.getId(), serverId), Response.class);
    }

    @Override
    @Nullable
    public GameProfile hasJoinedServer(GameProfile gameProfile, String serverID) throws AuthenticationUnavailableException
    {
        Map<String, Object> arguments = new HashMap<>(2);

        arguments.put("username", gameProfile.getName());
        arguments.put("serverId", serverID);

        URL url = URLUtils.createURL(CHECK_URL, URLUtils.buildQuery(arguments));
        try
        {
            HasJoinedResponse response = this.makeRequest(url, null, HasJoinedResponse.class);
            if ((response != null) && (response.getId() != null))
            {
                GameProfileImpl result = new GameProfileImpl(response.getId(), gameProfile.getName());
                if (response.getProperties() != null)
                {
                    result.getProperties().putAll(response.getProperties());
                }
                return result;
            }
            return null;
        }
        catch (AuthenticationUnavailableException e)
        {
            throw e;
        }
        catch (AuthenticationException ignored)
        {
        }
        return null;
    }

    protected HttpURLConnection createUrlConnection(URL url) throws IOException
    {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(this.proxy);
        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setReadTimeout(CONNECT_TIMEOUT);
        connection.setUseCaches(false);
        return connection;
    }

    public String performPostRequest(URL url, String post, String contentType) throws IOException
    {
        HttpURLConnection connection = this.createUrlConnection(url);
        byte[] bytes = post.getBytes(StandardCharsets.UTF_8);
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

    public String performGetRequest(URL url) throws IOException
    {
        try (final InputStream inputStream = this.createUrlConnection(url).getInputStream())
        {
            return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
        }
    }

    @Nullable
    protected <T extends Response> T makeRequest(URL url, @Nullable Object input, Class<T> classOfT) throws AuthenticationException
    {
        try
        {
            String jsonResult = (input == null) ? this.performGetRequest(url) : this.performPostRequest(url, this.gson.toJson(input), "application/json");
            T result = this.gson.fromJson(jsonResult, classOfT);
            if (result == null)
            {
                return null;
            }
            if (! Strings.isNullOrEmpty(result.getError()))
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
        }
        catch (IOException e)
        {
            if (e.getMessage().contains("429 for URL"))
            {
                throw new TooManyRequestsException("Too many requests!", e);
            }
            throw new AuthenticationUnavailableException("Cannot contact authentication server", e);
        }
        catch (JsonParseException | IllegalStateException e)
        {
            throw new AuthenticationUnavailableException("Cannot contact authentication server", e);
        }
    }
}
