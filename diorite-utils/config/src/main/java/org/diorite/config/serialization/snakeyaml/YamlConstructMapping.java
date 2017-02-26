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

import java.beans.IntrospectionException;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;

import org.diorite.config.Config;
import org.diorite.config.serialization.snakeyaml.YamlConstructor.ConstructorException;

/**
 * Construct mapping instance (Map, JavaBean) when the runtime class is
 * known.
 */
class YamlConstructMapping implements Construct
{
    private final YamlConstructor yamlConstructor;

    YamlConstructMapping(YamlConstructor yamlConstructor)
    {
        this.yamlConstructor = yamlConstructor;
    }

    /**
     * Construct JavaBean. If type safe collections are used please look at
     * <code>TypeDescription</code>.
     *
     * @param node
     *         node where the keys are property names (they can only be <code>String</code>s) and values are objects to be created
     *
     * @return constructed JavaBean
     */
    @SuppressWarnings({"unchecked"})
    @Nullable
    @Override
    public Object construct(Node node)
    {
        MappingNode mnode = (MappingNode) node;
        Class<?> nodeType = node.getType();
        if (Map.class.isAssignableFrom(nodeType) || Collection.class.isAssignableFrom(nodeType))
        {
            if (Config.class.isAssignableFrom(nodeType))
            {
                node.setUseClassConstructor(false);
                node.setTag(new Tag(nodeType));
                Construct constructor = this.yamlConstructor.getConstructor(node);
                Object construct = constructor.construct(node);
                if (node.isTwoStepsConstruction())
                {
                    constructor.construct2ndStep(node, construct);
                }
                return construct;
            }
            Object created = YamlCollectionCreator.createCollection(nodeType, mnode.getValue().size());
            if (Properties.class.isAssignableFrom(nodeType))
            {
                if (node.isTwoStepsConstruction())
                {
                    throw new YAMLException("Properties must not be recursive.");
                }
                this.yamlConstructor.constructMapping2ndStep(mnode, (Map<Object, Object>) created);
                return created;
            }
            if (Map.class.isAssignableFrom(nodeType))
            {
                if (node.isTwoStepsConstruction())
                {
                    return created;
                }
                this.yamlConstructor.constructMapping2ndStep(mnode, (Map<Object, Object>) created);
                return created;
            }
            if (Set.class.isAssignableFrom(nodeType))
            {
                if (node.isTwoStepsConstruction())
                {
                    return created;
                }
                this.yamlConstructor.constructSet2ndStep(mnode, (Set<Object>) created);
                return created;
            }
            if (Collection.class.isAssignableFrom(nodeType))
            {
                Set<Object> collection = new LinkedHashSet<>((Collection<?>) created);
                if (node.isTwoStepsConstruction())
                {
                    return collection;
                }
                this.yamlConstructor.constructSet2ndStep(mnode, collection);
                return collection;
            }
            else
            {
                throw new YAMLException("Unknown type: " + nodeType);
            }
        }
        else
        {
            if (node.isTwoStepsConstruction())
            {
                return this.createEmptyJavaBean(mnode);
            }
            else
            {
                return this.constructJavaBean2ndStep(mnode, this.createEmptyJavaBean(mnode));
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void construct2ndStep(Node node, Object object)
    {
        if (Map.class.isAssignableFrom(node.getType()))
        {
            this.yamlConstructor.constructMapping2ndStep((MappingNode) node, (Map<Object, Object>) object);
        }
        else if (Set.class.isAssignableFrom(node.getType()))
        {
            this.yamlConstructor.constructSet2ndStep((MappingNode) node, (Set<Object>) object);
        }
        else
        {
            this.constructJavaBean2ndStep((MappingNode) node, object);
        }
    }

    protected Object createEmptyJavaBean(MappingNode node)
    {
        try
        {
            /*
             * Using only default constructor. Everything else will be
             * initialized on 2nd step. If we do here some partial
             * initialization, how do we then track what need to be done on
             * 2nd step? I think it is better to get only object here (to
             * have it as reference for recursion) and do all other thing on
             * 2nd step.
             */
            java.lang.reflect.Constructor<?> c = node.getType().getDeclaredConstructor();
            c.setAccessible(true);
            return c.newInstance();
        }
        catch (Exception e)
        {
            throw new YAMLException(e);
        }
    }

    protected Object constructJavaBean2ndStep(MappingNode node, Object object)
    {
        this.yamlConstructor.flattenMapping(node);
        Class<?> beanType = node.getType();
        List<NodeTuple> nodeValue = node.getValue();
        for (NodeTuple tuple : nodeValue)
        {
            ScalarNode keyNode;
            if (tuple.getKeyNode() instanceof ScalarNode)
            {
                // key must be scalar
                keyNode = (ScalarNode) tuple.getKeyNode();
            }
            else
            {
                throw new YAMLException("Keys must be scalars but found: " + tuple.getKeyNode());
            }
            Node valueNode = tuple.getValueNode();
            // keys can only be Strings
            keyNode.setType(String.class);
            String key = (String) this.yamlConstructor.constructObject(keyNode);
            try
            {
                Property property = this.getProperty(beanType, key);
                valueNode.setType(property.getType());
                TypeDescription memberDescription = this.yamlConstructor.getTypeDefinitions().get(beanType);
                boolean typeDetected = false;
                if (memberDescription != null)
                {
                    switch (valueNode.getNodeId())
                    {
                        case sequence:
                            SequenceNode snode = (SequenceNode) valueNode;
                            Class<?> memberType = memberDescription
                                                          .getListPropertyType(key);
                            if (memberType != null)
                            {
                                snode.setListType(memberType);
                                typeDetected = true;
                            }
                            else if (property.getType().isArray())
                            {
                                snode.setListType(property.getType().getComponentType());
                                typeDetected = true;
                            }
                            break;
                        case mapping:
                            MappingNode mnode = (MappingNode) valueNode;
                            Class<?> keyType = memberDescription.getMapKeyType(key);
                            if (keyType != null)
                            {
                                mnode.setTypes(keyType, memberDescription.getMapValueType(key));
                                typeDetected = true;
                            }
                            break;
                        default: // scalar
                    }
                }
                if (! typeDetected && (valueNode.getNodeId() != NodeId.scalar))
                {
                    // only if there is no explicit TypeDescription
                    Class<?>[] arguments = property.getActualTypeArguments();
                    if ((arguments != null) && (arguments.length > 0))
                    {
                        // type safe (generic) collection may contain the
                        // proper class
                        if (valueNode.getNodeId() == NodeId.sequence)
                        {
                            Class<?> t = arguments[0];
                            //noinspection ConstantConditions safe to cast
                            SequenceNode snode = (SequenceNode) valueNode;
                            snode.setListType(t);
                        }
                        else if (valueNode.getTag().equals(Tag.SET))
                        {
                            Class<?> t = arguments[0];
                            //noinspection ConstantConditions safe to cast
                            MappingNode mnode = (MappingNode) valueNode;
                            mnode.setOnlyKeyType(t);
                            mnode.setUseClassConstructor(true);
                        }
//                            else if (property.getType().isAssignableFrom(Map.class))
                        else if (Map.class.isAssignableFrom(property.getType()))
                        {
                            Class<?> ketType = arguments[0];
                            Class<?> valueType = arguments[1];
                            //noinspection ConstantConditions safe to cast
                            MappingNode mnode = (MappingNode) valueNode;
                            mnode.setTypes(ketType, valueType);
                            mnode.setUseClassConstructor(true);
                        }
//                            else
                        {
                            // the type for collection entries cannot be
                            // detected
                        }
                    }
                }

                Object value = this.yamlConstructor.constructObject(valueNode);
                // Correct when the property expects float but double was
                // constructed
                if ((property.getType() == Float.TYPE) || (property.getType() == Float.class))
                {
                    if (value instanceof Double)
                    {
                        value = ((Double) value).floatValue();
                    }
                }
                // Correct when the property a String but the value is binary
                if ((property.getType() == String.class) && Tag.BINARY.equals(valueNode.getTag()) && (value instanceof byte[]))
                {
                    value = new String((byte[]) value);
                }
                property.set(object, value);
            }
            catch (Exception e)
            {
                throw new ConstructorException("Cannot create property=" + key + " for JavaBean=" + object, node.getStartMark(), e.getMessage(),
                                               valueNode.getStartMark(), e);
            }
        }
        return object;
    }

    protected Property getProperty(Class<?> type, String name)
            throws IntrospectionException
    {
        return this.yamlConstructor.getPropertyUtils().getProperty(type, name);
    }
}
