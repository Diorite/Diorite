package com.mojang.authlib.legacy;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.HttpAuthenticationService;
import com.mojang.authlib.HttpUserAuthentication;
import com.mojang.authlib.UserType;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.util.UUIDTypeAdapter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class LegacyUserAuthentication extends HttpUserAuthentication
{
    public static final  GameProfile[] EMPTY_GAME_PROFILES         = new GameProfile[0];
    private static final URL           AUTHENTICATION_URL          = HttpAuthenticationService.constantURL("https://login.minecraft.net");
    private static final int           AUTHENTICATION_VERSION      = 14;
    private static final int           RESPONSE_PART_PROFILE_NAME  = 2;
    private static final int           RESPONSE_PART_SESSION_TOKEN = 3;
    private static final int           RESPONSE_PART_PROFILE_ID    = 4;
    private String sessionToken;

    protected LegacyUserAuthentication(final AuthenticationService authenticationService)
    {
        super(authenticationService);
    }

    @Override
    public void logIn()
            throws AuthenticationException
    {
        if (StringUtils.isBlank(this.getUsername()))
        {
            throw new InvalidCredentialsException("Invalid username");
        }
        if (StringUtils.isBlank(this.getPassword()))
        {
            throw new InvalidCredentialsException("Invalid password");
        }
        final Map<String, Object> args = new HashMap<>(3);
        args.put("user", this.getUsername());
        args.put("password", this.getPassword());
        args.put("version", AUTHENTICATION_VERSION);
        final String response;
        try
        {
            //noinspection HardcodedFileSeparator
            response = this.getAuthenticationService().performPostRequest(AUTHENTICATION_URL, HttpAuthenticationService.buildQuery(args), "application/x-www-form-urlencoded").trim();
        } catch (final IOException e)
        {
            throw new AuthenticationException("Authentication server is not responding", e);
        }
        final String[] split = response.split(":");
        if (split.length == 5)
        {
            final String profileId = split[RESPONSE_PART_PROFILE_ID];
            final String profileName = split[RESPONSE_PART_PROFILE_NAME];
            final String sessionToken = split[RESPONSE_PART_SESSION_TOKEN];
            if ((StringUtils.isBlank(profileId)) || (StringUtils.isBlank(profileName)) || (StringUtils.isBlank(sessionToken)))
            {
                throw new AuthenticationException("Unknown response from authentication server: " + response);
            }
            this.setSelectedProfile(new GameProfile(UUIDTypeAdapter.fromString(profileId), profileName));
            this.sessionToken = sessionToken;
            this.setUserType(UserType.LEGACY);
        }
        else
        {
            throw new InvalidCredentialsException(response);
        }
    }

    @Override
    public boolean canPlayOnline()
    {
        return (this.isLoggedIn()) && (this.getSelectedProfile() != null) && (this.sessionToken != null);
    }

    @Override
    public GameProfile[] getAvailableProfiles()
    {
        if (this.getSelectedProfile() != null)
        {
            return new GameProfile[]{this.getSelectedProfile()};
        }
        return EMPTY_GAME_PROFILES;
    }

    @Override
    public void selectGameProfile(final GameProfile profile)
            throws AuthenticationException
    {
        throw new UnsupportedOperationException("Game profiles cannot be changed in the legacy authentication service");
    }

    @Override
    public String getAuthenticatedToken()
    {
        return this.sessionToken;
    }

    @Override
    public void logOut()
    {
        super.logOut();
        this.sessionToken = null;
    }

    @Override
    public String getUserID()
    {
        return this.getUsername();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("sessionToken", this.sessionToken).toString();
    }

    @Override
    public LegacyAuthenticationService getAuthenticationService()
    {
        return (LegacyAuthenticationService) super.getAuthenticationService();
    }
}
