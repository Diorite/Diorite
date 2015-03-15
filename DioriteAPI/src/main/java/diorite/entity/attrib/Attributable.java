package diorite.entity.attrib;

import java.util.Collection;
import java.util.UUID;

public interface Attributable
{
    AttributeStorage getAttributes();

    void removeAttributeProperty(AttributeType type);

    void removeModifier(AttributeType type, UUID uuid);

    void addAttributeProperty(AttributeProperty property);

    Collection<AttributeModifier> getModifiers(AttributeType type);

    AttributeProperty getProperty(AttributeType type);

    AttributeProperty getProperty(AttributeType type, double value);

    void addModifier(AttributeType type, AttributeModifier modifer);
}
