package com.mojang.authlib.legacy;

import java.io.IOException;
import java.net.URL;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.HttpAuthenticationService;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.minecraft.HttpMinecraftSessionService;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;

public class LegacyMinecraftSessionService extends HttpMinecraftSessionService
{
    private static final String BASE_URL  = "http://session.minecraft.net/game/";
    private static final URL    JOIN_URL  = HttpAuthenticationService.constantURL(BASE_URL + "joinserver.jsp");
    private static final URL    CHECK_URL = HttpAuthenticationService.constantURL(BASE_URL + "checkserver.jsp");

    protected LegacyMinecraftSessionService(final AuthenticationService authenticationService)
    {
        super(authenticationService);
    }

    @Override
    public void joinServer(final GameProfile profile, final String authenticationToken, final String serverId)
            throws AuthenticationException
    {
        final Map<String, Object> arguments = new HashMap<>(3);

        arguments.put("user", profile.getName());
        arguments.put("sessionId", authenticationToken);
        arguments.put("serverId", serverId);

        final URL url = HttpAuthenticationService.concatenateURL(JOIN_URL, HttpAuthenticationService.buildQuery(arguments));
        try
        {
            final String response = this.getAuthenticationService().performGetRequest(url);
            if (! response.equals("OK"))
            {
                throw new AuthenticationException(response);
            }
        } catch (final IOException e)
        {
            throw new AuthenticationUnavailableException(e);
        }
    }

    @Override
    public GameProfile hasJoinedServer(final GameProfile user, final String serverId)
            throws AuthenticationUnavailableException
    {
        final Map<String, Object> arguments = new HashMap<>(2);

        arguments.put("user", user.getName());
        arguments.put("serverId", serverId);

        final URL url = HttpAuthenticationService.concatenateURL(CHECK_URL, HttpAuthenticationService.buildQuery(arguments));
        try
        {
            final String response = this.getAuthenticationService().performGetRequest(url);

            return response.equals("YES") ? user : null;
        } catch (final IOException e)
        {
            throw new AuthenticationUnavailableException(e);
        }
    }

    @Override
    public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> getTextures(final GameProfile profile, final boolean requireSecure)
    {
        return new EnumMap<>(MinecraftProfileTexture.Type.class);
    }

    @Override
    public GameProfile fillProfileProperties(final GameProfile profile, final boolean requireSecure)
    {
        return profile;
    }

    @Override
    public LegacyAuthenticationService getAuthenticationService()
    {
        return (LegacyAuthenticationService) super.getAuthenticationService();
    }
}
