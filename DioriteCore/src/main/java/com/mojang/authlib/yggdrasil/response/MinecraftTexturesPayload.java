package com.mojang.authlib.yggdrasil.response;

import java.util.Map;
import java.util.UUID;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MinecraftTexturesPayload
{
    private long                                                       timestamp;
    private UUID                                                       profileId;
    private String                                                     profileName;
    private boolean                                                    isPublic;
    private Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> textures;

    public long getTimestamp()
    {
        return this.timestamp;
    }

    public void setTimestamp(final long timestamp)
    {
        this.timestamp = timestamp;
    }

    public UUID getProfileId()
    {
        return this.profileId;
    }

    public void setProfileId(final UUID profileId)
    {
        this.profileId = profileId;
    }

    public String getProfileName()
    {
        return this.profileName;
    }

    public void setProfileName(final String profileName)
    {
        this.profileName = profileName;
    }

    public boolean isPublic()
    {
        return this.isPublic;
    }

    public void setPublic(final boolean isPublic)
    {
        this.isPublic = isPublic;
    }

    public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> getTextures()
    {
        return this.textures;
    }

    public void setTextures(final Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> textures)
    {
        this.textures = textures;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("timestamp", this.timestamp).append("profileId", this.profileId).append("profileName", this.profileName).append("isPublic", this.isPublic).append("textures", this.textures).toString();
    }
}
