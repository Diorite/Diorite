/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.ScalarNode;

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

    protected class TemplateConstructMapping extends DioriteConstructMapping
    {

        @Override
        protected Object constructJavaBean2ndStep(final MappingNode node, Object object)
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
