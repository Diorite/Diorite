package org.diorite.impl.auth.yggdrasil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
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

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.auth.GameProfile;
import org.diorite.impl.auth.SessionService;
import org.diorite.impl.auth.exceptions.AuthenticationException;
import org.diorite.impl.auth.exceptions.AuthenticationUnavailableException;
import org.diorite.impl.auth.exceptions.InvalidCredentialsException;
import org.diorite.impl.auth.exceptions.UserMigratedException;
import org.diorite.impl.auth.properties.PropertyMap;
import org.diorite.impl.auth.yggdrasil.request.JoinServerRequest;
import org.diorite.impl.auth.yggdrasil.response.HasJoinedResponse;
import org.diorite.impl.auth.yggdrasil.response.ProfileSearchResultsResponse;
import org.diorite.impl.auth.yggdrasil.response.Response;
import org.diorite.utils.json.adapters.JsonUUIDAdapter;
import org.diorite.utils.network.DioriteURLUtils;

public class YggdrasilSessionService implements SessionService
{
    public static final int CONNECT_TIMEOUT = 15000;

    private static final String BASE_URL    = "https://sessionserver.mojang.com/session/minecraft/";
    private static final URL    JOIN_URL    = DioriteURLUtils.createURL(BASE_URL + "join");
    private static final URL    CHECK_URL   = DioriteURLUtils.createURL(BASE_URL + "hasJoined");
  //  private static final URL    PROFILE_URL = DioriteURLUtils.createURL(BASE_URL + "profile");

    private final Gson gson = new GsonBuilder().registerTypeAdapter(UUID.class, new JsonUUIDAdapter(false)).registerTypeAdapter(GameProfile.class, new GameProfile.Serializer()).registerTypeAdapter(PropertyMap.class, new PropertyMap.Serializer()).registerTypeAdapter(ProfileSearchResultsResponse.class, new ProfileSearchResultsResponse.Serializer()).create();
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
    public void joinServer(final GameProfile gameProfile, final String authenticationToken, final String serverId) throws AuthenticationException
    {
        this.makeRequest(JOIN_URL, new JoinServerRequest(authenticationToken, gameProfile.getId(), serverId), Response.class);
    }

    @Override
    public GameProfile hasJoinedServer(final GameProfile gameProfile, final String serverID) throws AuthenticationUnavailableException
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
                final GameProfile result = new GameProfile(response.getId(), gameProfile.getName());
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
        final byte[] bytes = post.getBytes(Charsets.UTF_8);
        connection.setRequestProperty("Content-Type", contentType + "; charset=utf-8");
        connection.setRequestProperty("Content-Length", "" + bytes.length);
        connection.setDoOutput(true);
        try (final OutputStream outputStream = connection.getOutputStream())
        {
            IOUtils.write(bytes, outputStream);
        }
        try (InputStream inputStream = connection.getInputStream())
        {
            return IOUtils.toString(inputStream, Charsets.UTF_8);
        }
    }

    public String performGetRequest(final URL url) throws IOException
    {
        try (final InputStream inputStream = this.createUrlConnection(url).getInputStream())
        {
            return IOUtils.toString(inputStream, Charsets.UTF_8);
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

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("proxy", this.proxy).append("clientToken", this.clientToken).append("publicKey", this.publicKey).toString();
    }
}
