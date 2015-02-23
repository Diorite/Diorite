package com.mojang.authlib.yggdrasil.response;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.mojang.authlib.properties.PropertyMap;

public class MinecraftProfilePropertiesResponse extends Response
{
    private UUID        id;
    private String      name;
    private PropertyMap properties;

    public UUID getId()
    {
        return this.id;
    }

    public void setId(final UUID id)
    {
        this.id = id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    public PropertyMap getProperties()
    {
        return this.properties;
    }

    public void setProperties(final PropertyMap properties)
    {
        this.properties = properties;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("id", this.id).append("name", this.name).append("properties", this.properties).toString();
    }
}
