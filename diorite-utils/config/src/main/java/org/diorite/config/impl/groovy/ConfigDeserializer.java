/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017. Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.config.impl.groovy;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.yaml.snakeyaml.nodes.Tag;

import org.diorite.config.Config;
import org.diorite.config.ConfigManager;
import org.diorite.config.SimpleConfig;
import org.diorite.config.impl.ConfigPropertyValueImpl;
import org.diorite.config.serialization.DeserializationData;
import org.diorite.config.serialization.SerializationData;
import org.diorite.config.serialization.Serializer;
import org.diorite.config.serialization.YamlDeserializationData;
import org.diorite.config.serialization.YamlSerializationData;

public class ConfigDeserializer<T extends Config> implements Serializer<T>
{
    private final Class<T> clazz;

    public ConfigDeserializer(Class<T> clazz)
    {
        this.clazz = clazz;
    }

    @Override
    public Class<? super T> getType()
    {
        return this.clazz;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void serialize(T object, SerializationData abstractData)
    {
        if (! (abstractData instanceof YamlSerializationData))
        {
            throw new IllegalStateException("Diorite configs can be only serialized from YAML!");
        }
        YamlSerializationData data = (YamlSerializationData) abstractData;
        if (object instanceof ConfigBaseGroovy)
        {
            ConfigBaseGroovy cfg = (ConfigBaseGroovy) object;
            AbstractConfigGroovy internalCfg = (AbstractConfigGroovy) cfg;
            for (Entry<String, ConfigPropertyValueImpl<Object>> entry : internalCfg.predefinedValues$Internal$().entrySet())
            {
                entry.getValue().serialize(data);
            }
            for (Entry<String, Object> entry : internalCfg.dynamicValues$Internal$().entrySet())
            {
                Object v = entry.getValue();
                if (v instanceof SimpleConfig)
                {
                    data.add(entry.getKey(), (SimpleConfig) v, SimpleConfig.class);
                    continue;
                }
                if (v instanceof Collection)
                {
                    data.addCollection(entry.getKey(), (Collection<?>) v, Object.class);
                }
                else if ((v instanceof Map) && ! (v instanceof Config))
                {
                    data.addMap(entry.getKey(), (Map<?, ?>) v, Object.class);
                }
                else
                {
                    data.addRaw(entry.getKey(), v);
                }
            }
        }
        else if (object instanceof AbstractConfigGroovy)
        {
            AbstractConfigGroovy cfg = (AbstractConfigGroovy) object;
            for (Entry<String, Object> entry : cfg.dynamicValues$Internal$().entrySet())
            {
                Object v = entry.getValue();
                if (v instanceof SimpleConfig)
                {
                    data.add(entry.getKey(), (SimpleConfig) v, SimpleConfig.class);
                    continue;
                }
                if (v instanceof Collection)
                {
                    data.addCollection(entry.getKey(), (Collection<?>) v, Object.class);
                }
                else if ((v instanceof Map) && ! (v instanceof Config))
                {
                    data.addMap(entry.getKey(), (Map<?, ?>) v, Object.class);
                }
                else
                {
                    data.addRaw(entry.getKey(), v);
                }
            }
        }
        else
        {
            // should never happen?
            for (Entry<String, Object> entry : object.entries())
            {
                Object v = entry.getValue();
                if (v instanceof SimpleConfig)
                {
                    data.add(entry.getKey(), (SimpleConfig) v, SimpleConfig.class);
                    continue;
                }
                data.add(entry.getKey(), entry.getValue());
            }
        }
    }

    private static final Set<Tag> sectionTags = Set.of(Tag.MAP, Tag.OMAP, new Tag(SimpleConfig.class));

    @SuppressWarnings("unchecked")
    @Override
    public T deserialize(DeserializationData abstractData)
    {
        if (! (abstractData instanceof YamlDeserializationData))
        {
            throw new IllegalStateException("Diorite configs can be only deserialized from YAML!");
        }
        YamlDeserializationData data = ((YamlDeserializationData) abstractData);
        T object = ConfigManager.get().getConfigFile(this.clazz).create();

        if (object instanceof ConfigBaseGroovy)
        {
            ConfigBaseGroovy cfg = (ConfigBaseGroovy) object;
            AbstractConfigGroovy internalCfg = (AbstractConfigGroovy) cfg;
            for (String key : data.getKeys())
            {
                ConfigPropertyValueImpl<Object> propertyValue = internalCfg.predefinedValues$Internal$().get(key);
                if (propertyValue != null)
                {
                    propertyValue.deserialize(data);
                    continue;
                }
                if (sectionTags.contains(data.getTag(key)))
                {
                    SimpleConfig simpleConfig = data.get(key, SimpleConfig.class);
                    object.set(key, simpleConfig);
                }
                else
                {
                    object.set(key, data.get(key, Object.class));
                }
            }
        }
        else
        {
            for (String key : data.getKeys())
            {
                if (sectionTags.contains(data.getTag(key)))
                {
                    SimpleConfig simpleConfig = data.get(key, SimpleConfig.class);
                    object.set(key, simpleConfig);
                }
                else
                {
                    object.set(key, data.get(key, Object.class));
                }
            }
        }

        return object;
    }
}
