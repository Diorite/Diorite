package com.mojang.authlib.legacy;

import java.net.Proxy;
import java.util.Objects;

import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.HttpAuthenticationService;

import org.apache.commons.lang3.Validate;

public class LegacyAuthenticationService extends HttpAuthenticationService
{
    protected LegacyAuthenticationService(final Proxy proxy)
    {
        super(proxy);
    }

    @Override
    public LegacyUserAuthentication createUserAuthentication(final Agent agent)
    {
        Validate.notNull(agent);
        if (! Objects.equals(agent, Agent.MINECRAFT))
        {
            throw new IllegalArgumentException("Legacy authentication cannot handle anything but Minecraft");
        }
        return new LegacyUserAuthentication(this);
    }

    @Override
    public LegacyMinecraftSessionService createMinecraftSessionService()
    {
        return new LegacyMinecraftSessionService(this);
    }

    @Override
    public GameProfileRepository createProfileRepository()
    {
        throw new UnsupportedOperationException("Legacy authentication service has no profile repository");
    }
}
