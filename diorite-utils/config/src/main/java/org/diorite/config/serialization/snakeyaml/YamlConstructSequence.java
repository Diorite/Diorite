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

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.SequenceNode;

import org.diorite.commons.reflections.DioriteReflectionUtils;

/**
 * Construct sequence (List, Array, or immutable object) when the runtime
 * class is known.
 */
class YamlConstructSequence implements Construct
{
    private final YamlConstructor yamlConstructor;

    YamlConstructSequence(YamlConstructor yamlConstructor)
    {
        this.yamlConstructor = yamlConstructor;
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Object construct(Node node)
    {
        SequenceNode snode = (SequenceNode) node;
        Class<?> nodeType = node.getType();
        if (Collection.class.isAssignableFrom(nodeType))
        {
            Object collection = YamlCollectionCreator.createCollection(nodeType, snode.getValue().size());
            if (Set.class.isAssignableFrom(nodeType))
            {
                if (node.isTwoStepsConstruction())
                {
                    throw new YAMLException("Set cannot be recursive.");
                }
            }
            if (Collection.class.isAssignableFrom(nodeType))
            {
                if (node.isTwoStepsConstruction())
                {
                    return collection;
                }
                else
                {
                    this.yamlConstructor.constructSequenceStep2(snode, (Collection<Object>) collection);
                    return collection;
                }
            }
        }
        if (nodeType.isArray())
        {
            if (node.isTwoStepsConstruction())
            {
                return this.yamlConstructor.createArray(nodeType, snode.getValue().size());
            }
            else
            {
                return this.yamlConstructor.constructArray(snode);
            }
        }
        else
        {
            // create immutable object
            List<Constructor<?>> possibleConstructors = new ArrayList<>(snode.getValue().size());
            for (Constructor<?> constructor : nodeType.getDeclaredConstructors())
            {
                if (snode.getValue().size() == constructor.getParameterTypes().length)
                {
                    possibleConstructors.add(constructor);
                }
            }
            if (! possibleConstructors.isEmpty())
            {
                if (possibleConstructors.size() == 1)
                {
                    Object[] argumentList = new Object[snode.getValue().size()];
                    Constructor<?> c = possibleConstructors.get(0);
                    int index = 0;
                    for (Node argumentNode : snode.getValue())
                    {
                        Class<?> type = c.getParameterTypes()[index];
                        // set runtime classes for arguments
                        argumentNode.setType(type);
                        argumentList[index++] = this.yamlConstructor.constructObject(argumentNode);
                    }

                    try
                    {
                        c.setAccessible(true);
                        return c.newInstance(argumentList);
                    }
                    catch (Exception e)
                    {
                        throw new YAMLException(e);
                    }
                }

                // use BaseConstructor
                List<Object> argumentList = (List<Object>) this.yamlConstructor.constructSequence(snode);
                Class<?>[] parameterTypes = new Class[argumentList.size()];
                int index = 0;
                for (Object parameter : argumentList)
                {
                    parameterTypes[index] = parameter.getClass();
                    index++;
                }

                for (Constructor<?> c : possibleConstructors)
                {
                    Class<?>[] argTypes = c.getParameterTypes();
                    boolean foundConstructor = true;
                    for (int i = 0; i < argTypes.length; i++)
                    {
                        if (! this.wrapIfPrimitive(argTypes[i]).isAssignableFrom(parameterTypes[i]))
                        {
                            foundConstructor = false;
                            break;
                        }
                    }
                    if (foundConstructor)
                    {
                        try
                        {
                            c.setAccessible(true);
                            return c.newInstance(argumentList.toArray());
                        }
                        catch (Exception e)
                        {
                            throw new YAMLException(e);
                        }
                    }
                }
            }
            throw new YAMLException("No suitable constructor with " + String.valueOf(snode.getValue().size()) + " arguments found for " + nodeType);

        }
    }

    private Class<?> wrapIfPrimitive(Class<?> clazz)
    {
        return DioriteReflectionUtils.getWrapperClass(clazz);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void construct2ndStep(Node node, Object object)
    {
        SequenceNode snode = (SequenceNode) node;
        if (List.class.isAssignableFrom(node.getType()))
        {
            List<Object> list = (List<Object>) object;
            this.yamlConstructor.constructSequenceStep2(snode, list);
        }
        else if (node.getType().isArray())
        {
            this.yamlConstructor.constructArrayStep2(snode, object);
        }
        else
        {
            throw new YAMLException("Immutable objects cannot be recursive.");
        }
    }
}
