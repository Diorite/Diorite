package diorite.impl.entity.attrib;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.GameObject;
import diorite.entity.attrib.AttributeModifier;
import diorite.entity.attrib.AttributeProperty;
import diorite.entity.attrib.AttributeStorage;
import diorite.entity.attrib.AttributeType;

public class AttributeStorageImpl implements AttributeStorage
{
    private final GameObject gameObject;
    private final Map<AttributeType, AttributeProperty> attributes = new ConcurrentHashMap<>(4, 0.3f, 8);

    public AttributeStorageImpl(final GameObject gameObject)
    {
        this.gameObject = gameObject;
    }

    @Override
    public void removeAttributeProperty(final AttributeType type)
    {
        this.attributes.remove(type);
    }

    @Override
    public void removeModifier(final AttributeType type, final UUID uuid)
    {
        final AttributeProperty prop = this.attributes.get(type);
        if (prop == null)
        {
            return;
        }
        prop.removeModifier(uuid);
    }

    @Override
    public void addAttributeProperty(final AttributeProperty property)
    {
        this.attributes.put(property.getType(), property);
    }

    @Override
    public Collection<AttributeProperty> getProperties()
    {
        return this.attributes.values();
    }

    @Override
    public Collection<AttributeModifier> getModifiers(final AttributeType type)
    {
        final AttributeProperty prop = this.attributes.get(type);
        if (prop == null)
        {
            return new HashSet<>(1);
        }
        return new HashSet<>(prop.getModifiersCollection());
    }

    @Override
    public AttributeProperty getProperty(final AttributeType type)
    {
        AttributeProperty attrib = this.attributes.get(type);
        if (attrib == null)
        {
            this.attributes.put(type, attrib = new AttributePropertyImpl(type));
        }
        return attrib;
    }

    @Override
    public AttributeProperty getProperty(final AttributeType type, final double value)
    {
        AttributeProperty attrib = this.attributes.get(type);
        if (attrib == null)
        {
            this.attributes.put(type, attrib = new AttributePropertyImpl(type, value));
        }
        return attrib;
    }

    @Override
    public void addModifier(final AttributeType type, final AttributeModifier modifer)
    {
        AttributeProperty prop = this.attributes.get(type);
        if (prop == null)
        {
            prop = new AttributePropertyImpl(type, type.getDefaultValue());
            this.attributes.put(type, prop);
        }
        prop.addModifier(modifer);
    }

    @Override
    public GameObject getGameObject()
    {
        return this.gameObject;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("attributes", this.attributes).toString();
    }
}
