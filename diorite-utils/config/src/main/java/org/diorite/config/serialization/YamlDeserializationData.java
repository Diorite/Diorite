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

import javax.annotation.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.yaml.snakeyaml.nodes.AnchorNode;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;

import org.diorite.commons.math.DioriteMathUtils;
import org.diorite.commons.reflections.DioriteReflectionUtils;
import org.diorite.config.serialization.snakeyaml.Representer;
import org.diorite.config.serialization.snakeyaml.YamlConstructor;

class YamlDeserializationData extends AbstractDeserializationData
{
    private final Node            node;
    private final Representer     representer;
    private final YamlConstructor constructor;

    YamlDeserializationData(Serialization serialization, Node node, Representer representer, YamlConstructor constructor, Class<?> type)
    {
        super(type, serialization);
        this.constructor = constructor;

        while (node instanceof AnchorNode)
        {
            node = ((AnchorNode) node).getRealNode();
        }

        this.node = node;
        this.representer = representer;
    }

    @Override
    public boolean containsKey(String key)
    {
        if (this.node instanceof MappingNode)
        {
            return this.getNode((MappingNode) this.node, key) != null;
        }
        if (this.node instanceof SequenceNode)
        {
            int i = DioriteMathUtils.asInt(key, - 1);
            if (i == - 1)
            {
                return false;
            }
            return i < ((SequenceNode) this.node).getValue().size();
        }
        return false;
    }

    @Nullable
    private Node getNode(Node node, String key)
    {
        if (key.isEmpty())
        {
            return node;
        }
        if (this.node instanceof SequenceNode)
        {
            SequenceNode sequenceNode = (SequenceNode) this.node;
            List<Node> sequenceNodeValue = sequenceNode.getValue();
            int i = DioriteMathUtils.asInt(key, - 1);
            if ((i == - 1) || (i < sequenceNodeValue.size()))
            {
                return null;
            }
            return sequenceNodeValue.get(i);
        }
        if (this.node instanceof MappingNode)
        {
            return this.getNode((MappingNode) this.node, key);
        }
        return null;
    }

    @Nullable
    private Node getNode(MappingNode node, String key)
    {
        return this.getNode(node, key, false);
    }

    @Nullable
    private Node getNode(MappingNode node, String key, boolean remove)
    {
        for (Iterator<NodeTuple> iterator = node.getValue().iterator(); iterator.hasNext(); )
        {
            NodeTuple tuple = iterator.next();
            Node keyNode = tuple.getKeyNode();
            if (! (keyNode instanceof ScalarNode))
            {
                return null;
            }
            if (key.equals(((ScalarNode) keyNode).getValue()))
            {
                return tuple.getValueNode();
            }
            if (remove)
            {
                iterator.remove();
            }
        }
        return null;
    }

    @Nullable
    @SuppressWarnings({"unchecked", "rawtypes"})
    private <T> T deserializeSpecial(Class<T> type, Node node, @Nullable T def)
    {
        if (DioriteReflectionUtils.getWrapperClass(type).equals(Boolean.class))
        {
            node.setTag(Tag.STR);
            T t = (T) this.toBool(this.constructor.constructObject(node).toString());
            if (t == null)
            {
                return def;
            }
            return t;
        }
        if (Enum.class.isAssignableFrom(type))
        {
            node.setTag(Tag.STR);
            Enum valueSafe = DioriteReflectionUtils.getEnumValueSafe(this.constructor.constructObject(node).toString(), - 1, (Class) type);
            if (valueSafe == null)
            {
                return def;
            }
            return (T) valueSafe;
        }
        node.setTag(new Tag(type));
        return (T) this.constructor.constructObject(node);
    }

    @SuppressWarnings("unchecked")
    private <T> T deserializeSpecialOrThrow(Class<T> type, Node node)
    {
        try
        {
            if (DioriteReflectionUtils.getWrapperClass(type).equals(Boolean.class))
            {
                node.setTag(Tag.STR);
                T t = (T) this.toBool(this.constructor.constructObject(node).toString());
                if (t == null)
                {
                    throw new DeserializationException(this.type, this, "Can't deserialize boolean value: (" + type.getName() + ") -> " + node);
                }
                return t;
            }
            node.setTag(new Tag(type));
            return (T) this.constructor.constructObject(node);
        }
        catch (DeserializationException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw new DeserializationException(this.type, this, "Can't deserialize boolean value: (" + type.getName() + ") -> " + node, e);
        }
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T get(String key, Class<T> type, @Nullable T def)
    {
        Node node = this.getNode(this.node, key);
        if (node == null)
        {
            return def;
        }
        return this.deserializeSpecial(type, node, def);
    }

    @Override
    public <T, C extends Collection<T>> void getAsCollection(String key, Class<T> type, C collection)
    {
        Node node = this.getNode(this.node, key);
        if (node == null)
        {
            throw new DeserializationException(type, this, "Can't find valid value for key: " + key);
        }
        if (this.node instanceof SequenceNode)
        {
            SequenceNode sequenceNode = (SequenceNode) this.node;
            for (Node nodeValue : sequenceNode.getValue())
            {
                collection.add(this.deserializeSpecial(type, nodeValue, null));
            }
        }
        else if (this.node instanceof MappingNode)
        {
            MappingNode mappingNode = (MappingNode) this.node;
            for (NodeTuple tuple : mappingNode.getValue())
            {
                collection.add(this.deserializeSpecial(type, tuple.getValueNode(), null));
            }
        }
        else
        {
            throw new DeserializationException(type, this, "Can't find valid value for key: " + key);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T, M extends Map<String, T>> void getAsMap(String key, Class<T> type, Function<T, String> keyMapper, M map)
    {
        Node node = this.getNode(this.node, key);
        if (node == null)
        {
            throw new DeserializationException(type, this, "Can't find valid value for key: " + key);
        }
        Tag tag = new Tag(type);
        if (this.node instanceof SequenceNode)
        {
            SequenceNode sequenceNode = (SequenceNode) this.node;
            for (Node nodeValue : sequenceNode.getValue())
            {
                nodeValue.setTag(tag);
                T deserialize = (T) this.constructor.constructObject(nodeValue);
                map.put(keyMapper.apply(deserialize), deserialize);
            }
        }
        else if (this.node instanceof MappingNode)
        {
            MappingNode mappingNode = (MappingNode) this.node;
            for (NodeTuple tuple : mappingNode.getValue())
            {
                Node valueNode = tuple.getValueNode();
                valueNode.setTag(tag);
                T deserialize = (T) this.constructor.constructObject(valueNode);
                map.put(keyMapper.apply(deserialize), deserialize);
            }
        }
        else
        {
            throw new DeserializationException(type, this, "Can't find valid value for key: " + key);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <K, T, M extends Map<K, T>> void getAsMapWithKeys(String key, Class<K> keyType, Class<T> type, String keyPropertyName, M map)
    {
        Node node = this.getNode(this.node, key);
        if (node == null)
        {
            throw new DeserializationException(type, this, "Can't find valid value for key: " + key);
        }
        Tag keyTag = new Tag(keyType);
        Tag typeTag = new Tag(type);
        if (node instanceof SequenceNode)
        {
            SequenceNode sequenceNode = (SequenceNode) node;
            for (Node nodeValue : sequenceNode.getValue())
            {
                if (! (nodeValue instanceof MappingNode))
                {
                    throw new DeserializationException(type, this, "Expected MappingNode in array list on '" + key + "' but found: " + nodeValue);
                }
                MappingNode mappingNode = (MappingNode) nodeValue;
                Node propKeyNode = this.getNode(mappingNode, keyPropertyName, true);
                if (propKeyNode == null)
                {
                    throw new DeserializationException(type, this, "Missing property '" + keyPropertyName + "' in " + mappingNode + ". Key: " + key);
                }

                mappingNode.setTag(typeTag);
                propKeyNode.setTag(keyTag);

                map.put((K) this.constructor.constructObject(propKeyNode), (T) this.constructor.constructObject(mappingNode));
            }
        }
        else if (node instanceof MappingNode)
        {
            MappingNode mappingNode = (MappingNode) node;
            for (NodeTuple tuple : mappingNode.getValue())
            {
                Node valueNode = tuple.getValueNode();
                if (! (valueNode instanceof MappingNode))
                {
                    throw new DeserializationException(type, this, "Expected MappingNode as values of '" + key + "' but found: " + valueNode);
                }
                MappingNode valueMappingNode = (MappingNode) valueNode;

                Node keyNode = this.getNode(valueMappingNode, keyPropertyName, true);
                if (keyNode == null)
                {
                    keyNode = tuple.getKeyNode();
                }
                keyNode.setTag(keyTag);
                K mapKey = (K) this.constructor.constructObject(keyNode);

                valueNode.setTag(typeTag);
                map.put(mapKey, (T) this.constructor.constructObject(valueNode));
            }
        }
        else
        {
            throw new DeserializationException(type, this, "Can't find valid value for key: " + key);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <K, T, M extends Map<K, T>> void getMap(String key, Class<K> keyType, Class<T> type, M map)
    {
        Node node = this.getNode(this.node, key);
        if (! (node instanceof MappingNode))
        {
            throw new DeserializationException(type, this, "Can't find valid value for key: " + key);
        }

        MappingNode mappingNode = (MappingNode) node;
        Tag keyTag = new Tag(keyType);
        Tag typeTag = new Tag(type);
        for (NodeTuple tuple : mappingNode.getValue())
        {
            K keyObj;
            Node keyNode = tuple.getKeyNode();
            if (Serialization.isSimpleType(keyType))
            {
                keyNode.setTag(keyTag);
                keyObj = (K) this.constructor.constructObject(keyNode);
            }
            else if (this.serialization.isStringSerializable(keyType))
            {
                keyNode.setTag(Tag.STR);
                keyObj = this.serialization.deserializeFromString(keyType, this.constructor.constructObject(keyNode).toString());
            }
            else
            {
                throw new DeserializationException(type, this, "Can't deserialize given node to key: (" + type.getName() + ") -> " + keyNode);
            }

            Node valueNode = tuple.getValueNode();
            valueNode.setTag(typeTag);
            map.put(keyObj, (T) this.constructor.constructObject(valueNode));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <K, T, M extends Map<K, T>> void getMap(String key, Function<String, K> keyMapper, Class<T> type, M map)
    {
        Node node = this.getNode(this.node, key);
        if (! (node instanceof MappingNode))
        {
            throw new DeserializationException(type, this, "Can't find valid value for key: " + key);
        }
        MappingNode mappingNode = (MappingNode) node;
        Tag typeTag = new Tag(type);
        for (NodeTuple tuple : mappingNode.getValue())
        {
            Node keyNode = tuple.getKeyNode();
            keyNode.setTag(Tag.STR);
            K keyObj = keyMapper.apply(this.constructor.constructObject(keyNode).toString());

            Node valueNode = tuple.getValueNode();
            valueNode.setTag(typeTag);

            map.put(keyObj, (T) this.constructor.constructObject(valueNode));
        }
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).appendSuper(super.toString()).append("node", this.node).toString();
    }
}
