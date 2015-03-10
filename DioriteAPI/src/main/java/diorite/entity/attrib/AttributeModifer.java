package diorite.entity.attrib;

import java.util.UUID;

public interface AttributeModifer
{
    UUID getUuid();

    double getValue();

    byte getOperation();
}
