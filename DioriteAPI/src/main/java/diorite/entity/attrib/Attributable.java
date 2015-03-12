package diorite.entity.attrib;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface Attributable
{
    AttributeStorage getAttributes();

    void removeAttributeProperty(AttributeType type);

    void removeModifer(AttributeType type, UUID uuid);

    void addAttributeProperty(AttributeProperty property);

    Collection<AttributeModifer> getModifers(AttributeType type);

    Optional<AttributeProperty> getProperty(AttributeType type);

    void addModifer(AttributeType type, AttributeModifer modifer);
}
