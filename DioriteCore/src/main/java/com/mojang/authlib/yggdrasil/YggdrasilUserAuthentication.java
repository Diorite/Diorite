package com.mojang.authlib.yggdrasil;

import java.net.URL;
import java.util.Map;

import com.mojang.authlib.Agent;
import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.HttpAuthenticationService;
import com.mojang.authlib.HttpUserAuthentication;
import com.mojang.authlib.UserType;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.yggdrasil.request.AuthenticationRequest;
import com.mojang.authlib.yggdrasil.request.RefreshRequest;
import com.mojang.authlib.yggdrasil.response.AuthenticationResponse;
import com.mojang.authlib.yggdrasil.response.RefreshResponse;
import com.mojang.authlib.yggdrasil.response.User;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class YggdrasilUserAuthentication extends HttpUserAuthentication
{
    private static final Logger LOGGER                   = LogManager.getLogger();
    private static final String BASE_URL                 = "https://authserver.mojang.com/";
    private static final URL    ROUTE_AUTHENTICATE       = HttpAuthenticationService.constantURL(BASE_URL + "authenticate");
    private static final URL    ROUTE_REFRESH            = HttpAuthenticationService.constantURL(BASE_URL + "refresh");
    //    private static final URL    ROUTE_VALIDATE           = HttpAuthenticationService.constantURL(BASE_URL + "validate");
//    private static final URL    ROUTE_INVALIDATE         = HttpAuthenticationService.constantURL(BASE_URL + "invalidate");
//    private static final URL    ROUTE_SIGNOUT            = HttpAuthenticationService.constantURL(BASE_URL + "signout");
    private static final String STORAGE_KEY_ACCESS_TOKEN = "accessToken";
    private final Agent         agent;
    private       GameProfile[] profiles;
    private       String        accessToken;
    private       boolean       isOnline;

    public YggdrasilUserAuthentication(final AuthenticationService authenticationService, final Agent agent)
    {
        super(authenticationService);
        this.agent = agent;
    }

    @Override
    public boolean canLogIn()
    {
        return (! this.canPlayOnline()) && (StringUtils.isNotBlank(this.getUsername())) && ((StringUtils.isNotBlank(this.getPassword())) || (StringUtils.isNotBlank(this.accessToken)));
    }

    @Override
    public void logOut()
    {
        super.logOut();

        this.accessToken = null;
        this.profiles = null;
        this.isOnline = false;
    }

    @Override
    public boolean isLoggedIn()
    {
        return StringUtils.isNotBlank(this.accessToken);
    }

    @Override
    public void loadFromStorage(final Map<String, Object> credentials)
    {
        super.loadFromStorage(credentials);

        this.accessToken = String.valueOf(credentials.get(STORAGE_KEY_ACCESS_TOKEN));
    }

    @Override
    public Map<String, Object> saveForStorage()
    {
        final Map<String, Object> result = super.saveForStorage();
        if (StringUtils.isNotBlank(this.accessToken))
        {
            result.put(STORAGE_KEY_ACCESS_TOKEN, this.accessToken);
        }
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("agent", this.agent).append("profiles", this.profiles).append("accessToken", this.accessToken).append("isOnline", this.isOnline).toString();
    }

    @Override
    public void logIn()
            throws AuthenticationException
    {
        if (StringUtils.isBlank(this.getUsername()))
        {
            throw new InvalidCredentialsException("Invalid username");
        }
        if (StringUtils.isNotBlank(this.accessToken))
        {
            this.logInWithToken();
        }
        else if (StringUtils.isNotBlank(this.getPassword()))
        {
            this.logInWithPassword();
        }
        else
        {
            throw new InvalidCredentialsException("Invalid password");
        }
    }

    @Override
    public boolean canPlayOnline()
    {
        return (this.isLoggedIn()) && (this.getSelectedProfile() != null) && (this.isOnline);
    }

    @Override
    public GameProfile[] getAvailableProfiles()
    {
        return this.profiles;
    }

    @Override
    public void selectGameProfile(final GameProfile profile)
            throws AuthenticationException
    {
        if (! this.isLoggedIn())
        {
            throw new AuthenticationException("Cannot change game profile whilst not logged in");
        }
        if (this.getSelectedProfile() != null)
        {
            throw new AuthenticationException("Cannot change game profile. You must log out and back in.");
        }
        if ((profile == null) || (! ArrayUtils.contains(this.profiles, profile)))
        {
            throw new IllegalArgumentException("Invalid profile '" + profile + "'");
        }
        final RefreshRequest request = new RefreshRequest(this, profile);
        final RefreshResponse response = this.getAuthenticationService().makeRequest(ROUTE_REFRESH, request, RefreshResponse.class);
        if (! response.getClientToken().equals(this.getAuthenticationService().getClientToken()))
        {
            throw new AuthenticationException("Server requested we change our client token. Don't know how to handle this!");
        }
        this.isOnline = true;
        this.accessToken = response.getAccessToken();
        this.setSelectedProfile(response.getSelectedProfile());
    }

    @Override
    public String getAuthenticatedToken()
    {
        return this.accessToken;
    }

    protected void logInWithPassword()
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
        LOGGER.info("Logging in with username & password");

        final AuthenticationRequest request = new AuthenticationRequest(this, this.getUsername(), this.getPassword());
        final AuthenticationResponse response = this.getAuthenticationService().makeRequest(ROUTE_AUTHENTICATE, request, AuthenticationResponse.class);
        if (! response.getClientToken().equals(this.getAuthenticationService().getClientToken()))
        {
            throw new AuthenticationException("Server requested we change our client token. Don't know how to handle this!");
        }
        if (response.getSelectedProfile() != null)
        {
            this.setUserType(response.getSelectedProfile().isLegacy() ? UserType.LEGACY : UserType.MOJANG);
        }
        else if (ArrayUtils.isNotEmpty(response.getAvailableProfiles()))
        {
            this.setUserType(response.getAvailableProfiles()[0].isLegacy() ? UserType.LEGACY : UserType.MOJANG);
        }
        final User user = response.getUser();
        if ((user != null) && (user.getId() != null))
        {
            this.setUserid(user.getId());
        }
        else
        {
            this.setUserid(this.getUsername());
        }
        this.isOnline = true;
        this.accessToken = response.getAccessToken();
        this.profiles = response.getAvailableProfiles();
        this.setSelectedProfile(response.getSelectedProfile());
        this.getModifiableUserProperties().clear();

        this.updateUserProperties(user);
    }

    protected void updateUserProperties(final User user)
    {
        if (user == null)
        {
            return;
        }
        if (user.getProperties() != null)
        {
            this.getModifiableUserProperties().putAll(user.getProperties());
        }
    }

    protected void logInWithToken()
            throws AuthenticationException
    {
        if (StringUtils.isBlank(this.getUserID()))
        {
            if (StringUtils.isBlank(this.getUsername()))
            {
                this.setUserid(this.getUsername());
            }
            else
            {
                throw new InvalidCredentialsException("Invalid uuid & username");
            }
        }
        if (StringUtils.isBlank(this.accessToken))
        {
            throw new InvalidCredentialsException("Invalid access token");
        }
        LOGGER.info("Logging in with access token");

        final RefreshRequest request = new RefreshRequest(this);
        final RefreshResponse response = this.getAuthenticationService().makeRequest(ROUTE_REFRESH, request, RefreshResponse.class);
        if (! response.getClientToken().equals(this.getAuthenticationService().getClientToken()))
        {
            throw new AuthenticationException("Server requested we change our client token. Don't know how to handle this!");
        }
        if (response.getSelectedProfile() != null)
        {
            this.setUserType(response.getSelectedProfile().isLegacy() ? UserType.LEGACY : UserType.MOJANG);
        }
        else if (ArrayUtils.isNotEmpty(response.getAvailableProfiles()))
        {
            this.setUserType(response.getAvailableProfiles()[0].isLegacy() ? UserType.LEGACY : UserType.MOJANG);
        }
        if ((response.getUser() != null) && (response.getUser().getId() != null))
        {
            this.setUserid(response.getUser().getId());
        }
        else
        {
            this.setUserid(this.getUsername());
        }
        this.isOnline = true;
        this.accessToken = response.getAccessToken();
        this.profiles = response.getAvailableProfiles();
        this.setSelectedProfile(response.getSelectedProfile());
        this.getModifiableUserProperties().clear();

        this.updateUserProperties(response.getUser());
    }

    @Deprecated
    public String getSessionToken()
    {
        if ((this.isLoggedIn()) && (this.getSelectedProfile() != null) && (this.canPlayOnline()))
        {
            return String.format("token:%s:%s", this.accessToken, this.getSelectedProfile().getId());
        }
        return null;
    }

    public Agent getAgent()
    {
        return this.agent;
    }

    @Override
    public YggdrasilAuthenticationService getAuthenticationService()
    {
        return (YggdrasilAuthenticationService) super.getAuthenticationService();
    }
}
