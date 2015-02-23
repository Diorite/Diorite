package com.mojang.authlib.yggdrasil.request;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

public class AuthenticationRequest
{
    private final Agent   agent;
    private final String  username;
    private final String  password;
    private final String  clientToken;
    private final boolean requestUser;

    public AuthenticationRequest(final YggdrasilUserAuthentication authenticationService, final String username, final String password)
    {
        this.agent = authenticationService.getAgent();
        this.username = username;
        this.clientToken = authenticationService.getAuthenticationService().getClientToken();
        this.password = password;
        this.requestUser = true;
    }

    public Agent getAgent()
    {
        return this.agent;
    }

    public boolean isRequestUser()
    {
        return this.requestUser;
    }

    public String getClientToken()
    {
        return this.clientToken;
    }

    public String getPassword()
    {
        return this.password;
    }

    public String getUsername()
    {
        return this.username;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("agent", this.agent).append("username", this.username).append("password", this.password).append("clientToken", this.clientToken).append("requestUser", this.requestUser).toString();
    }
}
