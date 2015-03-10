package diorite.entity.attrib;

import java.util.Collection;

public interface AttributeProperty
{
    AttributeType getType();

    double getValue();

    Collection<AttributeModifer> getModifers();
}
