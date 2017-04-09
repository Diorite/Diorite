/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.config.serialization;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Node;

import org.diorite.commons.reflections.DioriteReflectionUtils;
import org.diorite.config.serialization.snakeyaml.AbstractRepresent;
import org.diorite.config.serialization.snakeyaml.Representer;
import org.diorite.config.serialization.snakeyaml.YamlConstructor;

class YamlSerializerImpl<T> extends AbstractRepresent implements Construct
{
    private final YamlConstructor constructor;
    private final Serializer<T>   serializer;
    private final Serialization   serialization;

    YamlSerializerImpl(Representer representer, YamlConstructor constructor, Serializer<T> serializer, Serialization serialization)
    {
        super(representer);
        this.constructor = constructor;
        this.serializer = serializer;
        this.serialization = serialization;
    }

    public Class<? super T> getType()
    {
        return this.serializer.getType();
    }

    @Override
    public Object construct(Node node)
    {
        DeserializationData data = new YamlDeserializationData(this.serialization, node, this.representer, this.constructor, this.serializer.getType());
        try
        {
            Class<?> type;
            if ((node.getType() == null) || Object.class.equals(node.getType()))
            {
                try
                {
                    type = DioriteReflectionUtils.tryGetCanonicalClass(node.getTag().getClassName());
                }
                catch (YAMLException e)
                {
                    type = null;
                }
            }
            else
            {
                type = node.getType();
            }
            return this.serializer.deserialize(data, type);
        }
        catch (DeserializationException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new DeserializationException(this.serializer.getType(), data, e.getMessage(), e);
        }
    }

    @Override
    public void construct2ndStep(Node node, Object object)
    {
// skip
    }

    @SuppressWarnings("unchecked")
    @Override
    public Node representData(Object object)
    {
        try
        {
            SimpleSerializationData data =
                    (SimpleSerializationData) SerializationData.create(SerializationType.YAML, this.serialization, this.serializer.getType());
            this.serializer.serialize((T) object, data);
            Object rawValue = data.rawValue();
            if (rawValue instanceof Map)
            {
                return this.representMap((Map<?, ?>) rawValue);
            }
            if (rawValue instanceof List)
            {
                return this.representList((List<?>) rawValue);
            }
            if (rawValue instanceof Set)
            {
                return this.representSet((Set<?>) rawValue);
            }
            if (rawValue instanceof Collection)
            {
                return this.representCollection((Collection<?>) rawValue);
            }
            throw new SerializationException(this.serializer.getType(), "can't represent " + rawValue + " as yaml node!");
        }
        catch (SerializationException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new SerializationException(this.serializer.getType(), e.getMessage(), e);
        }
    }
}
