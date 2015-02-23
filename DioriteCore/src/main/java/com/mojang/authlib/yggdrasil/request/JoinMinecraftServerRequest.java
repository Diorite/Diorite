package com.mojang.authlib.yggdrasil.request;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class JoinMinecraftServerRequest
{
    public String accessToken;
    public UUID   selectedProfile;
    public String serverId;

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("accessToken", this.accessToken).append("selectedProfile", this.selectedProfile).append("serverId", this.serverId).toString();
    }
}
