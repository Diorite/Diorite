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

package org.diorite.config.impl.proxy;

import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.Tag;

import org.diorite.config.Config;
import org.diorite.config.ConfigManager;
import org.diorite.config.SimpleConfig;
import org.diorite.config.impl.ConfigPropertyValueImpl;
import org.diorite.config.serialization.DeserializationData;
import org.diorite.config.serialization.Serialization;
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

    @Override
    public void serialize(T object, SerializationData abstractData)
    {
        if (! (abstractData instanceof YamlSerializationData))
        {
            throw new IllegalStateException("Diorite configs can be only serialized from YAML!");
        }
        YamlSerializationData data = (YamlSerializationData) abstractData;
        ConfigInvocationHandler handler = (ConfigInvocationHandler) Proxy.getInvocationHandler(object);
        for (Entry<String, ConfigPropertyValueImpl<Object>> entry : handler.predefinedValues.entrySet())
        {
            entry.getValue().serialize(data);
        }
        for (Entry<String, SimpleConfig> entry : handler.dynamicValues.entrySet())
        {
            data.add(entry.getKey(), entry.getValue(), SimpleConfig.class);
        }
        for (Entry<String, Node> entry : handler.simpleDynamicValues.entrySet())
        {
            Object fromYamlNode = Serialization.getInstance().fromYamlNode(entry.getValue());
            if (fromYamlNode instanceof Collection)
            {
                data.addCollection(entry.getKey(), (Collection<?>) fromYamlNode, Object.class);
            }
            else if ((fromYamlNode instanceof Map) && ! (fromYamlNode instanceof Config))
            {
                data.addMap(entry.getKey(), (Map<?, ?>) fromYamlNode, Object.class);
            }
            else
            {
                data.addRaw(entry.getKey(), fromYamlNode);
            }
        }
    }

    private static final Set<Tag> sectionTags = Set.of(Tag.MAP, Tag.OMAP, new Tag(SimpleConfig.class));

    @Override
    public T deserialize(DeserializationData abstractData)
    {
        if (! (abstractData instanceof YamlDeserializationData))
        {
            throw new IllegalStateException("Diorite configs can be only deserialized from YAML!");
        }
        YamlDeserializationData data = ((YamlDeserializationData) abstractData);
        T t = ConfigManager.get().getConfigFile(this.clazz).create();
        ConfigInvocationHandler handler = (ConfigInvocationHandler) Proxy.getInvocationHandler(t);

        for (String key : data.getKeys())
        {
            ConfigPropertyValueImpl<Object> propertyValue = handler.predefinedValues.get(key);
            if (propertyValue != null)
            {
                propertyValue.deserialize(data);
                continue;
            }
            if (sectionTags.contains(data.getTag(key)))
            {
                SimpleConfig simpleConfig = data.get(key, SimpleConfig.class);
                handler.dynamicValues.put(key, simpleConfig);
            }
            else
            {
                t.set(key, data.get(key, Object.class));
            }
        }
        return t;
    }
}
