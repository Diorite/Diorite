package org.diorite.cfg.simple.serialization;

import java.util.Map;

public interface ConfigurationSerializable
{
    Map<String, Object> serialize();
}
