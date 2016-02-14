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

package org.diorite.cfg.system;

import java.beans.IntrospectionException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.yaml.snakeyaml.constructor.Construct;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.nodes.SequenceNode;

import org.diorite.cfg.system.deserializers.TemplateDeserializer;
import org.diorite.cfg.system.deserializers.TemplateDeserializers;
import org.diorite.cfg.yaml.DioriteYaml;
import org.diorite.cfg.yaml.DioriteYamlConstructor;
import org.diorite.utils.reflections.ReflectElement;

public class TemplateYamlConstructor extends DioriteYamlConstructor
{
    private final ClassLoader customClassLoader;

    /**
     * Construct new TemplateYamlConstructor using custom class loader.ł
     *
     * @param customClassLoader other class loader than normal.
     */
    public TemplateYamlConstructor(final ClassLoader customClassLoader)
    {
        this.customClassLoader = customClassLoader;
    }

    /**
     * Construct new default TemplateYamlConstructor.
     */
    public TemplateYamlConstructor()
    {
        this.customClassLoader = null;
    }

    private static class FieldCounter
    {
        private final Template<?>              template;
        private final Map<String, ConfigField> fields;

        private FieldCounter(final Template<?> template)
        {
            this.template = template;
            this.fields = template.getFieldsNameMap();
        }

        @Override
        public String toString()
        {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("template", this.template).append("fields", this.fields.keySet()).toString();
        }
    }

    @Override
    protected Class<?> getClassForName(final String name) throws ClassNotFoundException
    {
        if (this.customClassLoader != null)
        {
            return Class.forName(name, true, this.customClassLoader);
        }
        return Class.forName(name);
    }

    {
        this.yamlClassConstructors.put(NodeId.mapping, new TemplateConstructMapping());
    }

    /**
     * Template construct mapping, used to implement template defaults and deserializers.
     */
    public class TemplateConstructMapping extends DioriteConstructMapping
    {
        /**
         * Returns TemplateYamlConstructor related to this object.
         *
         * @return TemplateYamlConstructor related to this object.
         */
        public TemplateYamlConstructor getTemplateYamlConstructor()
        {
            return TemplateYamlConstructor.this;
        }

        @Override
        public Property getProperty(final Class<?> type, final String name) throws IntrospectionException
        {
            return super.getProperty(type, name);
        }

        @Override
        public Object construct(final Node node)
        {
            return super.construct(node);
        }

        @Override
        public void construct2ndStep(final Node node, final Object object)
        {
            super.construct2ndStep(node, object);
        }

        @Override
        public Object createEmptyJavaBean(final MappingNode node)
        {
            final TemplateDeserializer<?> deserializer = TemplateDeserializers.getElement(node.getType());
            if (deserializer != null)
            {
                return deserializer.construct(this, node);
            }
            return super.createEmptyJavaBean(node);
        }

        @Override
        public Object constructJavaBean2ndStep(final MappingNode node, Object object)
        {
            final Class<?> beanType = node.getType();
            final Template<?> template = TemplateCreator.getTemplate(beanType, false);
            final FieldCounter counter;
            if (template != null)
            {
                counter = new FieldCounter(template);
                node.getValue().stream().filter(nodeTuple -> nodeTuple.getKeyNode() instanceof ScalarNode).forEach(nodeTuple -> counter.fields.remove(((ScalarNode) nodeTuple.getKeyNode()).getValue()));
            }
            else
            {
                counter = null;
            }
            object = super.constructJavaBean2ndStep(node, object);
            if (counter != null)
            {
                final Map<ConfigField, ReflectElement<?>> fields = template.getFields();
                for (final ConfigField field : counter.fields.values())
                {
                    if (! field.hasDefaultValue())
                    {
                        continue;
                    }
                    final ReflectElement<?> element = fields.get(field);
                    if (element != null)
                    {
                        element.set(object, field.getDefaultValue());
                    }
                }
            }
            return object;
        }
    }

    @Override
    public Class<?> getClassForNode(final Node node)
    {
        return super.getClassForNode(node);
    }

    @Override
    public void flattenMapping(final MappingNode node)
    {
        super.flattenMapping(node);
    }

    @Override
    public void constructMapping2ndStep(final MappingNode node, final Map<Object, Object> mapping)
    {
        super.constructMapping2ndStep(node, mapping);
    }

    @Override
    public void constructSet2ndStep(final MappingNode node, final Set<Object> set)
    {
        super.constructSet2ndStep(node, set);
    }

    /**
     * Unsafe version of {@link #constructObject(Node)}
     *
     * @param node Node to be constructed
     *
     * @return Java instance
     */
    public Object unsafeConstructObject(final Node node)
    {
        final Construct constructor = this.getConstructor(node);
        final Object data = constructor.construct(node);
        if (node.isTwoStepsConstruction())
        {
            constructor.construct2ndStep(node, data);
        }
        return data;
    }

    @Override
    public Object constructObject(final Node node)
    {
        return super.constructObject(node);
    }

    @Override
    public Construct getConstructor(final Node node)
    {
        return super.getConstructor(node);
    }

    @Override
    public Object constructScalar(final ScalarNode node)
    {
        return super.constructScalar(node);
    }

    @Override
    public List<Object> createDefaultList(final int initSize)
    {
        return super.createDefaultList(initSize);
    }

    @Override
    public Set<Object> createDefaultSet(final int initSize)
    {
        return super.createDefaultSet(initSize);
    }

    @Override
    public Object createArray(final Class<?> type, final int size)
    {
        return super.createArray(type, size);
    }

    @Override
    public List<?> constructSequence(final SequenceNode node)
    {
        return super.constructSequence(node);
    }

    @Override
    public Set<?> constructSet(final SequenceNode node)
    {
        return super.constructSet(node);
    }

    @Override
    public Object constructArray(final SequenceNode node)
    {
        return super.constructArray(node);
    }

    @Override
    public void constructSequenceStep2(final SequenceNode node, final Collection<Object> collection)
    {
        super.constructSequenceStep2(node, collection);
    }

    @Override
    public Object constructArrayStep2(final SequenceNode node, final Object array)
    {
        return super.constructArrayStep2(node, array);
    }

    @Override
    public Map<Object, Object> createDefaultMap()
    {
        return super.createDefaultMap();
    }

    @Override
    public Set<Object> constructSet(final MappingNode node)
    {
        return super.constructSet(node);
    }

    @Override
    public Set<Object> createDefaultSet()
    {
        return super.createDefaultSet();
    }

    @Override
    public Map<Object, Object> constructMapping(final MappingNode node)
    {
        return super.constructMapping(node);
    }

    /**
     * @return snake yaml instance using DioriteYaml constructor extension, used by all templates.
     */
    public static DioriteYaml getInstance()
    {
        return new DioriteYaml(new TemplateYamlConstructor());
    }

    /**
     * @param customClassLoader other class loader than normal.
     *
     * @return snake yaml instance using DioriteYaml constructor extension, used by all templates.
     */
    public static DioriteYaml getInstance(final ClassLoader customClassLoader)
    {
        return new DioriteYaml(new TemplateYamlConstructor(customClassLoader));
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("customClassLoader", this.customClassLoader).toString();
    }
}
