package org.diorite.impl.entity;

import java.util.Collection;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.DioriteCore;
import org.diorite.impl.entity.attrib.AttributeStorageImpl;
import org.diorite.ImmutableLocation;
import org.diorite.entity.AttributableEntity;
import org.diorite.entity.attrib.AttributeModifier;
import org.diorite.entity.attrib.AttributeProperty;
import org.diorite.entity.attrib.AttributeStorage;
import org.diorite.entity.attrib.AttributeType;

public abstract class AttributableEntityImpl extends EntityImpl implements AttributableEntity
{
    protected final AttributeStorage attributes = new AttributeStorageImpl(this);

    public AttributableEntityImpl(final UUID uuid, final DioriteCore server, final int id, final ImmutableLocation location)
    {
        super(uuid, server, id, location);
    }

    @Override
    public AttributeStorage getAttributes()
    {
        return this.attributes;
    }

    @Override
    public void removeAttributeProperty(final AttributeType type)
    {
        this.attributes.removeAttributeProperty(type);
    }

    @Override
    public void removeModifier(final AttributeType type, final UUID uuid)
    {
        this.attributes.removeModifier(type, uuid);
    }

    @Override
    public void addAttributeProperty(final AttributeProperty property)
    {
        this.attributes.addAttributeProperty(property);
    }

    @Override
    public Collection<AttributeModifier> getModifiers(final AttributeType type)
    {
        return this.attributes.getModifiers(type);
    }

    @Override
    public AttributeProperty getProperty(final AttributeType type)
    {
        return this.attributes.getProperty(type);
    }

    @Override
    public AttributeProperty getProperty(final AttributeType type, final double def)
    {
        return this.attributes.getProperty(type, def);
    }

    @Override
    public void addModifier(final AttributeType type, final AttributeModifier modifer)
    {
        this.attributes.addModifier(type, modifer);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).toString();
    }
}
