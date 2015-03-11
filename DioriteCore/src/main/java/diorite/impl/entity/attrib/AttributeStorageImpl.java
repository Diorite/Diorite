package diorite.impl.entity.attrib;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import diorite.GameObject;
import diorite.entity.attrib.AttributeModifer;
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
    public GameObject getGameObject()
    {
        return this.gameObject;
    }

    @Override
    public void addAttributeProperty(final AttributeProperty property)
    {
        this.attributes.put(property.getType(), property);
    }

    @Override
    public Optional<AttributeProperty> getProperty(final AttributeType type)
    {
        return Optional.ofNullable(this.attributes.get(type));
    }

    @Override
    public Collection<AttributeModifer> getModifers(final AttributeType type)
    {
        final AttributeProperty prop = this.attributes.get(type);
        if (prop == null)
        {
            return new HashSet<>(1);
        }
        return new HashSet<>(prop.getModifers());
    }

    @Override
    public void addModifer(final AttributeType type, final AttributeModifer modifer)
    {
        AttributeProperty prop = this.attributes.get(type);
        if (prop == null)
        {
            prop = new AttributePropertyImpl(type, type.getDefaultValue());
            this.attributes.put(type, prop);
        }
        prop.getModifers().add(modifer);
    }

    @Override
    public void removeModifer(final AttributeType type, final UUID uuid)
    {
        final AttributeProperty prop = this.attributes.get(type);
        if (prop == null)
        {
            return;
        }
        prop.getModifers().removeIf(mod -> mod.getUuid().equals(uuid));
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("attributes", this.attributes).toString();
    }
}
