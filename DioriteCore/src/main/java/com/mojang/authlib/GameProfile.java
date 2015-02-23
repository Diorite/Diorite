package com.mojang.authlib;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.mojang.authlib.properties.PropertyMap;

public class GameProfile
{
    private final UUID   id;
    private final String name;
    private final PropertyMap properties = new PropertyMap();
    @SuppressWarnings("UnusedDeclaration")
    private boolean legacy;

    public GameProfile(final UUID id, final String name)
    {
        if ((id == null) && (StringUtils.isBlank(name)))
        {
            throw new IllegalArgumentException("Name and ID cannot both be blank");
        }
        this.id = id;
        this.name = name;
    }

    public UUID getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public PropertyMap getProperties()
    {
        return this.properties;
    }

    public boolean isComplete()
    {
        return (this.id != null) && (StringUtils.isNotBlank(this.name));
    }

    public int hashCode()
    {
        int result = (this.id != null) ? this.id.hashCode() : 0;
        result = (31 * result) + ((this.name != null) ? this.name.hashCode() : 0);
        return result;
    }

    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if ((o == null) || (this.getClass() != o.getClass()))
        {
            return false;
        }
        final GameProfile that = (GameProfile) o;
        return ! ((this.id != null) ? ! this.id.equals(that.id) : (that.id != null)) && ! ((this.name != null) ? ! this.name.equals(that.name) : (that.name != null));
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("id", this.id).append("name", this.name).append("properties", this.properties).append("legacy", this.legacy).toString();
    }

    public boolean isLegacy()
    {
        return this.legacy;
    }
}
