package com.mojang.authlib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.util.UUIDTypeAdapter;

public abstract class BaseUserAuthentication
        implements UserAuthentication
{
    protected static final String STORAGE_KEY_PROFILE_NAME       = "displayName";
    protected static final String STORAGE_KEY_PROFILE_ID         = "uuid";
    protected static final String STORAGE_KEY_PROFILE_PROPERTIES = "profileProperties";
    protected static final String STORAGE_KEY_USER_NAME          = "username";
    protected static final String STORAGE_KEY_USER_ID            = "userid";
    protected static final String STORAGE_KEY_USER_PROPERTIES    = "userProperties";
    private static final   Logger LOGGER                         = LogManager.getLogger();
    private final AuthenticationService authenticationService;
    private final PropertyMap userProperties = new PropertyMap();
    private String      userid;
    private String      username;
    private String      password;
    private GameProfile selectedProfile;
    private UserType    userType;

    protected BaseUserAuthentication(final AuthenticationService authenticationService)
    {
        Validate.notNull(authenticationService);
        this.authenticationService = authenticationService;
    }

    @Override
    public boolean canLogIn()
    {
        return (! this.canPlayOnline()) && (StringUtils.isNotBlank(this.username)) && (StringUtils.isNotBlank(this.password));
    }

    @Override
    public void logOut()
    {
        this.password = null;
        this.userid = null;
        this.selectedProfile = null;
        this.userProperties.clear();
        this.userType = null;
    }

    @Override
    public boolean isLoggedIn()
    {
        return this.selectedProfile != null;
    }

    @Override
    public GameProfile getSelectedProfile()
    {
        return this.selectedProfile;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadFromStorage(final Map<String, Object> credentials)
    {
        this.logOut();

        this.setUsername(String.valueOf(credentials.get(STORAGE_KEY_USER_NAME)));
        if (credentials.containsKey(STORAGE_KEY_USER_ID))
        {
            this.userid = String.valueOf(credentials.get(STORAGE_KEY_USER_ID));
        }
        else
        {
            this.userid = this.username;
        }
        if (credentials.containsKey(STORAGE_KEY_USER_PROPERTIES))
        {
            try
            {
                final Iterable<Map<String, String>> list = (Iterable<Map<String, String>>) credentials.get(STORAGE_KEY_USER_PROPERTIES);
                for (final Map<String, String> propertyMap : list)
                {
                    final String name = propertyMap.get("name");
                    final String value = propertyMap.get("value");
                    final String signature = propertyMap.get("signature");
                    if (signature == null)
                    {
                        this.userProperties.put(name, new Property(name, value));
                    }
                    else
                    {
                        this.userProperties.put(name, new Property(name, value, signature));
                    }
                }
            } catch (final Throwable t)
            {
                LOGGER.warn("Couldn't deserialize user properties", t);
            }
        }
        if ((credentials.containsKey(STORAGE_KEY_PROFILE_NAME)) && (credentials.containsKey(STORAGE_KEY_PROFILE_ID)))
        {
            final GameProfile profile = new GameProfile(UUIDTypeAdapter.fromString(String.valueOf(credentials.get(STORAGE_KEY_PROFILE_ID))), String.valueOf(credentials.get(STORAGE_KEY_PROFILE_NAME)));
            if (credentials.containsKey(STORAGE_KEY_PROFILE_PROPERTIES))
            {
                try
                {
                    final Iterable<Map<String, String>> list = (Iterable<Map<String, String>>) credentials.get(STORAGE_KEY_PROFILE_PROPERTIES);
                    for (final Map<String, String> propertyMap : list)
                    {
                        final String name = propertyMap.get("name");
                        final String value = propertyMap.get("value");
                        final String signature = propertyMap.get("signature");
                        if (signature == null)
                        {
                            profile.getProperties().put(name, new Property(name, value));
                        }
                        else
                        {
                            profile.getProperties().put(name, new Property(name, value, signature));
                        }
                    }
                } catch (final Throwable t)
                {
                    LOGGER.warn("Couldn't deserialize profile properties", t);
                }
            }
            this.selectedProfile = profile;
        }
    }

    @Override
    public Map<String, Object> saveForStorage()
    {
        final Map<String, Object> result = new HashMap<>(10);
        if (this.username != null)
        {
            result.put(STORAGE_KEY_USER_NAME, this.username);
        }
        if (this.getUserID() != null)
        {
            result.put(STORAGE_KEY_USER_ID, this.getUserID());
        }
        else if (this.username != null)
        {
            result.put(STORAGE_KEY_USER_NAME, this.username);
        }
        if (! this.getUserProperties().isEmpty())
        {
            final Collection<Map<String, String>> properties = new ArrayList<>(this.getUserProperties().values().size());
            for (final Property userProperty : this.getUserProperties().values())
            {
                final Map<String, String> property = new HashMap<>(3);
                property.put("name", userProperty.getName());
                property.put("value", userProperty.getValue());
                property.put("signature", userProperty.getSignature());
                properties.add(property);
            }
            result.put(STORAGE_KEY_USER_PROPERTIES, properties);
        }
        final GameProfile selectedProfile = this.selectedProfile;
        if (selectedProfile != null)
        {
            result.put(STORAGE_KEY_PROFILE_NAME, selectedProfile.getName());
            result.put(STORAGE_KEY_PROFILE_ID, selectedProfile.getId());

            final Collection<Map<String, String>> properties = new ArrayList<>(selectedProfile.getProperties().values().size());
            for (final Property profileProperty : selectedProfile.getProperties().values())
            {
                final Map<String, String> property = new HashMap<>(3);
                property.put("name", profileProperty.getName());
                property.put("value", profileProperty.getValue());
                property.put("signature", profileProperty.getSignature());
                properties.add(property);
            }
            if (! properties.isEmpty())
            {
                result.put(STORAGE_KEY_PROFILE_PROPERTIES, properties);
            }
        }
        return result;
    }

    protected void setSelectedProfile(final GameProfile selectedProfile)
    {
        this.selectedProfile = selectedProfile;
    }

    protected String getUsername()
    {
        return this.username;
    }

    @Override
    public void setUsername(final String username)
    {
        if ((this.isLoggedIn()) && (this.canPlayOnline()))
        {
            throw new IllegalStateException("Cannot change username whilst logged in & online");
        }
        this.username = username;
    }

    protected String getPassword()
    {
        return this.password;
    }

    @Override
    public void setPassword(final String password)
    {
        if ((this.isLoggedIn()) && (this.canPlayOnline()) && (StringUtils.isNotBlank(password)))
        {
            throw new IllegalStateException("Cannot set password whilst logged in & online");
        }
        this.password = password;
    }

    @Override
    public String getUserID()
    {
        return this.userid;
    }

    @Override
    public PropertyMap getUserProperties()
    {
        if (this.isLoggedIn())
        {
            final PropertyMap result = new PropertyMap();
            result.putAll(this.userProperties);
            return result;
        }
        return new PropertyMap();
    }

    @Override
    public UserType getUserType()
    {
        if (this.isLoggedIn())
        {
            return (this.userType == null) ? UserType.LEGACY : this.userType;
        }
        return null;
    }

    protected void setUserType(final UserType userType)
    {
        this.userType = userType;
    }

    public String toString()
    {
        final StringBuilder result = new StringBuilder();

        result.append(this.getClass().getSimpleName());
        result.append("{");
        if (this.isLoggedIn())
        {
            result.append("Logged in as ");
            result.append(this.username);
            if (this.selectedProfile != null)
            {
                //noinspection HardcodedFileSeparator
                result.append(" / ");
                result.append(this.selectedProfile);
                result.append(" - ");
                if (this.canPlayOnline())
                {
                    result.append("Online");
                }
                else
                {
                    result.append("Offline");
                }
            }
        }
        else
        {
            result.append("Not logged in");
        }
        result.append("}");

        return result.toString();
    }

    public AuthenticationService getAuthenticationService()
    {
        return this.authenticationService;
    }

    protected PropertyMap getModifiableUserProperties()
    {
        return this.userProperties;
    }

    protected void setUserid(final String userid)
    {
        this.userid = userid;
    }
}
