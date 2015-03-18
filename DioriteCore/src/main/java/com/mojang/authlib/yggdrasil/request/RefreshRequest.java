package com.mojang.authlib.yggdrasil.request;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RefreshRequest
{
    private final String      clientToken;
    private final String      accessToken;
    private final GameProfile selectedProfile;
    private final boolean     requestUser;

    public RefreshRequest(final YggdrasilUserAuthentication authenticationService)
    {
        this(authenticationService, null);
    }

    public RefreshRequest(final YggdrasilUserAuthentication authenticationService, final GameProfile profile)
    {
        this.clientToken = authenticationService.getAuthenticationService().getClientToken();
        this.accessToken = authenticationService.getAuthenticatedToken();
        this.selectedProfile = profile;
        this.requestUser = true;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("clientToken", this.clientToken).append("accessToken", this.accessToken).append("selectedProfile", this.selectedProfile).append("requestUser", this.requestUser).toString();
    }
}
