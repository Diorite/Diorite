package diorite.entity.attrib;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import diorite.GameObject;

public interface AttributeStorage
{
    void removeAttributeProperty(AttributeType type);

    GameObject getGameObject();

    void addAttributeProperty(AttributeProperty property);

    Optional<AttributeProperty> getProperty(AttributeType type);

    Collection<AttributeModifer> getModifers(AttributeType type);

    void addModifer(AttributeType type, AttributeModifer modifer);

    void removeModifer(AttributeType type, UUID uuid);
}
