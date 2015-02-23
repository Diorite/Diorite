package com.mojang.authlib;

@SuppressWarnings("InterfaceMayBeAnnotatedFunctional")
public abstract interface GameProfileRepository
{
    public abstract void findProfilesByNames(String[] paramArrayOfString, Agent paramAgent, ProfileLookupCallback paramProfileLookupCallback);
}
