package diorite.entity.attrib;

import java.util.Collection;
import java.util.UUID;

import diorite.GameObject;

public interface AttributeStorage
{
    void removeAttributeProperty(AttributeType type);

    void removeModifier(AttributeType type, UUID uuid);

    void addAttributeProperty(AttributeProperty property);

    Collection<AttributeProperty> getProperties();

    Collection<AttributeModifier> getModifiers(AttributeType type);

    AttributeProperty getProperty(AttributeType type);

    AttributeProperty getProperty(AttributeType type, double value);

    void addModifier(AttributeType type, AttributeModifier modifer);

    GameObject getGameObject();
}
