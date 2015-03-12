package diorite.impl.entity;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.ImmutableLocation;
import diorite.entity.AttributableEntity;
import diorite.entity.attrib.AttributeModifer;
import diorite.entity.attrib.AttributeProperty;
import diorite.entity.attrib.AttributeStorage;
import diorite.entity.attrib.AttributeType;
import diorite.impl.ServerImpl;
import diorite.impl.entity.attrib.AttributeStorageImpl;

public abstract class AttributableEntityImpl extends EntityImpl implements AttributableEntity
{
    protected AttributeStorage attributes = new AttributeStorageImpl(this);

    public AttributableEntityImpl(final ServerImpl server, final int id, final ImmutableLocation location)
    {
        super(server, id, location);
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
    public void removeModifer(final AttributeType type, final UUID uuid)
    {
        this.attributes.removeModifer(type, uuid);
    }

    @Override
    public void addAttributeProperty(final AttributeProperty property)
    {
        this.attributes.addAttributeProperty(property);
    }

    @Override
    public Collection<AttributeModifer> getModifers(final AttributeType type)
    {
        return this.attributes.getModifers(type);
    }

    @Override
    public Optional<AttributeProperty> getProperty(final AttributeType type)
    {
        return this.attributes.getProperty(type);
    }

    @Override
    public void addModifer(final AttributeType type, final AttributeModifer modifer)
    {
        this.attributes.addModifer(type, modifer);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("attributes", this.attributes).toString();
    }
}
