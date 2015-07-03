package org.diorite.cfg.simple.serialization;

import java.util.Map;

@FunctionalInterface
public interface ConfigurationSerializable
{
    Map<String, Object> serialize();
}
