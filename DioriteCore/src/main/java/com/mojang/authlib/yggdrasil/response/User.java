package com.mojang.authlib.yggdrasil.response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.mojang.authlib.properties.PropertyMap;

public class User
{
    private String      id;
    private PropertyMap properties;

    public String getId()
    {
        return this.id;
    }

    public void setId(final String id)
    {
        this.id = id;
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("id", this.id).append("properties", this.properties).toString();
    }
}
