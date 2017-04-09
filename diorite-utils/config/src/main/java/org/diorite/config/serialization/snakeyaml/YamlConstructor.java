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

package org.diorite.config.serialization.snakeyaml;

import javax.annotation.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;

import org.diorite.commons.arrays.DioriteArrayUtils;
import org.diorite.commons.reflections.DioriteReflectionUtils;

public class YamlConstructor extends Constructor
{
    public YamlConstructor()
    {
        YamlCollectionCreator.createCollection(List.class, 1); // ensure fully loaded.
        this.yamlClassConstructors.put(NodeId.mapping, new YamlConstructMapping(this));
        this.yamlClassConstructors.put(NodeId.sequence, new YamlConstructSequence(this));
    }

    public void addConstruct(Class<?> type, Construct construct)
    {
        this.yamlConstructors.put(new Tag(type), construct);
    }

    public Map<Class<?>, TypeDescription> getTypeDefinitions()
    {
        return this.typeDefinitions;
    }

    @Nullable
    public Object constructFromNode(Node node)
    {
        return this.constructDocument(node);
    }

    @Override
    public void constructMapping2ndStep(MappingNode node, Map<Object, Object> mapping)
    {
        super.constructMapping2ndStep(node, mapping);
    }

    @Override
    public void constructSet2ndStep(MappingNode node, Set<Object> set)
    {
        super.constructSet2ndStep(node, set);
    }

    /**
     * Construct object from the specified Node. Return existing instance if the
     * node is already constructed.
     *
     * @param node
     *         Node to be constructed
     *
     * @return Java instance
     */
    @Override
    public Object constructObject(Node node)
    {
        return super.constructObject(node);
    }

    @Override
    public Class<?> getClassForNode(Node node)
    {
        return super.getClassForNode(node);
    }

    @Override
    public Class<?> getClassForName(String name) throws ClassNotFoundException
    {
        return super.getClassForName(name);
    }

    @Override
    public List<Object> createDefaultList(int initSize)
    {
        return YamlCollectionCreator.createCollection(List.class, 10);
    }

    @Override
    public Set<Object> createDefaultSet(int initSize)
    {
        return YamlCollectionCreator.createCollection(Set.class, 10);
    }

    @Override
    public Object createArray(Class<?> type, int size)
    {
        if (size == 0)
        {
            return DioriteArrayUtils.getEmptyObjectArray(type);
        }
        return super.createArray(type, size);
    }

    @Override
    public Map<Object, Object> createDefaultMap()
    {
        return YamlCollectionCreator.createCollection(Map.class, 10);
    }

    @Override
    public Set<Object> createDefaultSet()
    {
        return YamlCollectionCreator.createCollection(Set.class, 10);
    }

    /**
     * Get the constructor to construct the Node. For implicit tags if the
     * runtime class is known a dedicated Construct implementation is used.
     * Otherwise the constructor is chosen by the tag.
     *
     * @param node
     *         Node to be constructed
     *
     * @return Construct implementation for the specified node
     */
    @Override
    public Construct getConstructor(Node node)
    {
        if (node.useClassConstructor())
        {
            return this.yamlClassConstructors.get(node.getNodeId());
        }
        else
        {
            Construct constructor = this.yamlConstructors.get(node.getTag());
            if (constructor == null)
            {
                Class<?> nodeType;
                if ((node.getType() == null) || Object.class.equals(node.getType()))
                {
                    try
                    {
                        nodeType = DioriteReflectionUtils.tryGetCanonicalClass(node.getTag().getClassName());
                    }
                    catch (YAMLException e)
                    {
                        nodeType = null;
                    }
                }
                else
                {
                    nodeType = node.getType();
                }
                for (Entry<String, Construct> stringConstructEntry : this.yamlMultiConstructors.entrySet())
                {
                    if (node.getTag().startsWith(stringConstructEntry.getKey()))
                    {
                        return this.yamlMultiConstructors.get(stringConstructEntry.getKey());
                    }
                }
                if (nodeType != null)
                {
                    Construct bestMatching = null;
                    int score = - 1;
                    for (Entry<Tag, Construct> entry : this.yamlConstructors.entrySet())
                    {
                        try
                        {
                            Tag key = entry.getKey();
                            Construct value = entry.getValue();
                            if ((key == null) || (value == null))
                            {
                                continue;
                            }
                            Class<?> type = Class.forName(key.getClassName());
                            if (type.isAssignableFrom(nodeType))
                            {
                                int classScore = this.getClassScore(type, new HashSet<>(5));
                                if (classScore > score)
                                {
                                    score = classScore;
                                    bestMatching = value;
                                }
                            }
                        }
                        catch (ClassNotFoundException | YAMLException e)
                        {
                            // skip
                        }
                    }
                    if (bestMatching != null)
                    {
                        this.yamlConstructors.putIfAbsent(new Tag(nodeType), bestMatching);
                        return bestMatching;
                    }
                }
                return this.yamlConstructors.get(null);
            }
            return constructor;
        }
    }

    private int getClassScore(Class<?> type, Set<Class<?>> dup)
    {
        int result = 0;
        Class<?> current = type;
        while ((current != null) && ! current.equals(Object.class))
        {
            result += 1;
            for (Class<?> aClass : current.getInterfaces())
            {
                if (! dup.add(aClass))
                {
                    continue;
                }
                result += this.getClassScore(aClass, dup);
            }
            current = current.getSuperclass();
        }
        return result;
    }

    @Override
    public Object constructScalar(ScalarNode node)
    {
        return super.constructScalar(node);
    }

    @Override
    public List<?> constructSequence(SequenceNode node)
    {
        Collection<Object> collection;
        if (List.class.isAssignableFrom(node.getType()))
        {
            collection = YamlCollectionCreator.createCollection(node.getType(), node.getValue().size());
        }
        else
        {
            collection = YamlCollectionCreator.createCollection(List.class, node.getValue().size());
        }
        this.constructSequenceStep2(node, collection);
        return (List<?>) collection;
    }

    @Override
    public Set<?> constructSet(SequenceNode node)
    {
        Collection<Object> collection;
        if (Set.class.isAssignableFrom(node.getType()))
        {
            collection = YamlCollectionCreator.createCollection(node.getType(), node.getValue().size());
        }
        else
        {
            collection = YamlCollectionCreator.createCollection(Set.class, node.getValue().size());
        }
        this.constructSequenceStep2(node, collection);
        return (Set<?>) collection;
    }

    @Override
    public Object constructArray(SequenceNode node)
    {
        return super.constructArray(node);
    }

    @Override
    public void constructSequenceStep2(SequenceNode node, Collection<Object> collection)
    {
        super.constructSequenceStep2(node, collection);
    }

    @Override
    public Object constructArrayStep2(SequenceNode node, Object array)
    {
        return super.constructArrayStep2(node, array);
    }

    @Override
    public Set<Object> constructSet(MappingNode node)
    {
        Collection<Object> collection;
        if (Set.class.isAssignableFrom(node.getType()))
        {
            collection = YamlCollectionCreator.createCollection(node.getType(), node.getValue().size());
        }
        else
        {
            collection = YamlCollectionCreator.createCollection(Set.class, node.getValue().size());
        }
        this.constructSet2ndStep(node, (Set<Object>) collection);
        return (Set<Object>) collection;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<Object, Object> constructMapping(MappingNode node)
    {
        Object collection;
        if (Map.class.isAssignableFrom(node.getType()))
        {
            collection = YamlCollectionCreator.createCollection(node.getType(), node.getValue().size());
        }
        else
        {
            collection = YamlCollectionCreator.createCollection(Map.class, node.getValue().size());
        }
        this.constructMapping2ndStep(node, (Map<Object, Object>) collection);
        return super.constructMapping(node);
    }

    @Override
    public void flattenMapping(MappingNode node)
    {
        super.flattenMapping(node);
    }

    public static class ConstructorException extends org.yaml.snakeyaml.constructor.ConstructorException
    {
        private static final long serialVersionUID = 0;

        protected ConstructorException(String context, Mark contextMark, String problem, Mark problemMark, Throwable cause)
        {
            super(context, contextMark, problem, problemMark, cause);
        }

        protected ConstructorException(String context, Mark contextMark, String problem, Mark problemMark)
        {
            super(context, contextMark, problem, problemMark);
        }
    }
}
