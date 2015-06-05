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

    public static DioriteYaml getInstance()
    {
        return new DioriteYaml(new TemplateYamlConstructor());
    }
}
