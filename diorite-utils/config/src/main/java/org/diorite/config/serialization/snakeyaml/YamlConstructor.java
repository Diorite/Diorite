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

import java.beans.IntrospectionException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;

import org.diorite.commons.reflections.DioriteReflectionUtils;

public class YamlConstructor extends Constructor
{
    public YamlConstructor()
    {
        YamlCollectionCreator.createCollection(List.class, 1); // ensure fully loaded.
        this.yamlClassConstructors.put(NodeId.mapping, new ConstructMapping());
        this.yamlClassConstructors.put(NodeId.sequence, new ConstructSequence());
    }

    public void addConstruct(Class<?> type, Construct construct)
    {
        this.yamlConstructors.put(new Tag(type), construct);
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
        return super.getConstructor(node);
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

    /**
     * Construct sequence (List, Array, or immutable object) when the runtime
     * class is known.
     */
    protected class ConstructSequence implements Construct
    {
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
                        YamlConstructor.this.constructSequenceStep2(snode, (Collection<Object>) collection);
                        return collection;
                    }
                }
            }
            if (nodeType.isArray())
            {
                if (node.isTwoStepsConstruction())
                {
                    return YamlConstructor.this.createArray(nodeType, snode.getValue().size());
                }
                else
                {
                    return YamlConstructor.this.constructArray(snode);
                }
            }
            else
            {
                // create immutable object
                List<java.lang.reflect.Constructor<?>> possibleConstructors = new ArrayList<>(snode.getValue().size());
                for (java.lang.reflect.Constructor<?> constructor : nodeType.getDeclaredConstructors())
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
                        java.lang.reflect.Constructor<?> c = possibleConstructors.get(0);
                        int index = 0;
                        for (Node argumentNode : snode.getValue())
                        {
                            Class<?> type = c.getParameterTypes()[index];
                            // set runtime classes for arguments
                            argumentNode.setType(type);
                            argumentList[index++] = YamlConstructor.this.constructObject(argumentNode);
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
                    List<Object> argumentList = (List<Object>) YamlConstructor.this.constructSequence(snode);
                    Class<?>[] parameterTypes = new Class[argumentList.size()];
                    int index = 0;
                    for (Object parameter : argumentList)
                    {
                        parameterTypes[index] = parameter.getClass();
                        index++;
                    }

                    for (java.lang.reflect.Constructor<?> c : possibleConstructors)
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
                YamlConstructor.this.constructSequenceStep2(snode, list);
            }
            else if (node.getType().isArray())
            {
                YamlConstructor.this.constructArrayStep2(snode, object);
            }
            else
            {
                throw new YAMLException("Immutable objects cannot be recursive.");
            }
        }
    }

    /**
     * Construct mapping instance (Map, JavaBean) when the runtime class is
     * known.
     */
    protected class ConstructMapping implements Construct
    {
        /**
         * Construct JavaBean. If type safe collections are used please look at
         * <code>TypeDescription</code>.
         *
         * @param node
         *         node where the keys are property names (they can only be <code>String</code>s) and values are objects to be created
         *
         * @return constructed JavaBean
         */
        @SuppressWarnings("unchecked")
        @Override
        public Object construct(Node node)
        {
            MappingNode mnode = (MappingNode) node;
            Class<?> nodeType = node.getType();
            if (Map.class.isAssignableFrom(nodeType) || Collection.class.isAssignableFrom(nodeType))
            {
                Object created = YamlCollectionCreator.createCollection(nodeType, mnode.getValue().size());
                if (Properties.class.isAssignableFrom(nodeType))
                {
                    if (node.isTwoStepsConstruction())
                    {
                        throw new YAMLException("Properties must not be recursive.");
                    }
                    YamlConstructor.this.constructMapping2ndStep(mnode, (Map<Object, Object>) created);
                    return created;
                }
                if (Map.class.isAssignableFrom(nodeType))
                {
                    if (node.isTwoStepsConstruction())
                    {
                        return created;
                    }
                    YamlConstructor.this.constructMapping2ndStep(mnode, (Map<Object, Object>) created);
                    return created;
                }
                if (Set.class.isAssignableFrom(nodeType))
                {
                    if (node.isTwoStepsConstruction())
                    {
                        return created;
                    }
                    YamlConstructor.this.constructSet2ndStep(mnode, (Set<Object>) created);
                    return created;
                }
                if (Collection.class.isAssignableFrom(nodeType))
                {
                    Set<Object> collection = new LinkedHashSet<>((Collection<?>) created);
                    if (node.isTwoStepsConstruction())
                    {
                        return collection;
                    }
                    YamlConstructor.this.constructSet2ndStep(mnode, collection);
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
                YamlConstructor.this.constructMapping2ndStep((MappingNode) node, (Map<Object, Object>) object);
            }
            else if (Set.class.isAssignableFrom(node.getType()))
            {
                YamlConstructor.this.constructSet2ndStep((MappingNode) node, (Set<Object>) object);
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
            YamlConstructor.this.flattenMapping(node);
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
                String key = (String) YamlConstructor.this.constructObject(keyNode);
                try
                {
                    Property property = this.getProperty(beanType, key);
                    valueNode.setType(property.getType());
                    TypeDescription memberDescription = YamlConstructor.this.typeDefinitions.get(beanType);
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

                    Object value = YamlConstructor.this.constructObject(valueNode);
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
                                                   valueNode.getStartMark(), e) {};
                }
            }
            return object;
        }

        protected Property getProperty(Class<?> type, String name)
                throws IntrospectionException
        {
            return YamlConstructor.this.getPropertyUtils().getProperty(type, name);
        }
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
