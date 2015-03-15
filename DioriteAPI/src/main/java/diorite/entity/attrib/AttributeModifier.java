package diorite.entity.attrib;

import java.util.UUID;

public interface AttributeModifier
{
    byte OPERATION_ADD_NUMBER          = 0;
    byte OPERATION_MULTIPLY_PERCENTAGE = 1;
    byte OPERATION_ADD_PERCENTAGE      = 2;

    UUID getUuid();

    double getValue();

    ModifierOperation getOperation();

    boolean isSerialize();

    AttributeModifier setSerialize(boolean serialize);
}
