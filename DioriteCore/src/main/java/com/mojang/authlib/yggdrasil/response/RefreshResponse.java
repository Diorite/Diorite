package com.mojang.authlib.yggdrasil.response;

import com.mojang.authlib.GameProfile;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RefreshResponse extends Response
{
    private String        accessToken;
    private String        clientToken;
    private GameProfile   selectedProfile;
    private GameProfile[] availableProfiles;
    private User          user;

    public String getAccessToken()
    {
        return this.accessToken;
    }

    public void setAccessToken(final String accessToken)
    {
        this.accessToken = accessToken;
    }

    public String getClientToken()
    {
        return this.clientToken;
    }

    public void setClientToken(final String clientToken)
    {
        this.clientToken = clientToken;
    }

    public GameProfile[] getAvailableProfiles()
    {
        return this.availableProfiles;
    }

    public void setAvailableProfiles(final GameProfile[] availableProfiles)
    {
        this.availableProfiles = availableProfiles;
    }

    public GameProfile getSelectedProfile()
    {
        return this.selectedProfile;
    }

    public void setSelectedProfile(final GameProfile selectedProfile)
    {
        this.selectedProfile = selectedProfile;
    }

    public User getUser()
    {
        return this.user;
    }

    public void setUser(final User user)
    {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("accessToken", this.accessToken).append("clientToken", this.clientToken).append("selectedProfile", this.selectedProfile).append("availableProfiles", this.availableProfiles).append("user", this.user).toString();
    }
}
