package com.mojang.authlib.yggdrasil.request;

import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class InvalidateRequest
{
    private final String accessToken;
    private final String clientToken;

    public InvalidateRequest(final YggdrasilUserAuthentication authenticationService)
    {
        this.accessToken = authenticationService.getAuthenticatedToken();
        this.clientToken = authenticationService.getAuthenticationService().getClientToken();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("accessToken", this.accessToken).append("clientToken", this.clientToken).toString();
    }
}
