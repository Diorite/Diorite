package org.diorite.entity.attrib;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface AttributeProperty
{
    AttributeType getType();

    double getBaseValue();

    double getFinalValue();

    double recalculateFinalValue();

    Collection<AttributeModifier> getModifiersCollection();

    Map<UUID, AttributeModifier> getModifiers();

    Optional<AttributeModifier> getModifier(UUID uuid);

    AttributeModifier removeModifier(UUID uuid);

    void addModifier(AttributeModifier mod);
}
