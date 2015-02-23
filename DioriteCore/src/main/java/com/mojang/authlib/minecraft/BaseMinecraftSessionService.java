package com.mojang.authlib.minecraft;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.mojang.authlib.AuthenticationService;

public abstract class BaseMinecraftSessionService implements MinecraftSessionService
{
    private final AuthenticationService authenticationService;

    protected BaseMinecraftSessionService(final AuthenticationService authenticationService)
    {
        this.authenticationService = authenticationService;
    }

    public AuthenticationService getAuthenticationService()
    {
        return this.authenticationService;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("authenticationService", this.authenticationService).toString();
    }
}
