package com.mojang.authlib;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Agent
{
    public static final Agent MINECRAFT = new Agent("Minecraft", 1);
    private final String name;
    private final int    version;

    public Agent(final String name, final int version)
    {
        this.name = name;
        this.version = version;
    }

    public String getName()
    {
        return this.name;
    }

    public int getVersion()
    {
        return this.version;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).append("version", this.version).toString();
    }
}
