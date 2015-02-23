package com.mojang.authlib;

import com.mojang.authlib.minecraft.MinecraftSessionService;

public abstract interface AuthenticationService
{
    public abstract UserAuthentication createUserAuthentication(Agent paramAgent);

    public abstract MinecraftSessionService createMinecraftSessionService();

    public abstract GameProfileRepository createProfileRepository();
}
