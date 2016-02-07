package org.diorite.cfg.simple.deserializers;

import org.diorite.cfg.system.ConfigField;
import org.diorite.utils.pipeline.BasePipeline;
import org.diorite.utils.pipeline.Pipeline;
import org.diorite.utils.reflections.DioriteReflectionUtils;

public final class TemplateDeserializers
{
    private static final Pipeline<TemplateDeserializer<?>> elements = new BasePipeline<>();

    private TemplateDeserializers()
    {
    }

    /**
     * @return editable pipeline with template elements.
     *
     * @see TemplateElements
     */
    public static Pipeline<TemplateDeserializer<?>> getElements()
    {
        return elements;
    }

    /**
     * This method is trying to get element deserializer from template pipeline using few methods:
     * (using next only if prev method fail and returns null)
     * 1. By name using: {@link Class#getName()}
     * 2. By name using: {@link Class#getSimpleName()}, if that also fail then it will use first
     * 3. By type: template which {@link TemplateDeserializer#isValidType(Class)} method will return true.
     *
     * @param clazz class to find template deserializer for it.
     *
     * @return template element deserializer.
     */
    public static TemplateDeserializer<?> getElement(Class<?> clazz)
    {
        clazz = DioriteReflectionUtils.getPrimitive(clazz);
        TemplateDeserializer<?> element = elements.get(clazz.getName());
        if (element != null)
        {
            return element;
        }
        element = elements.get(clazz.getSimpleName());
        if (element != null)
        {
            return element;
        }
        for (final TemplateDeserializer<?> e : elements)
        {
            if (e.isValidType(clazz))
            {
                return e;
            }
        }
        return null;
    }

    /**
     * This method is trying to get element deserializer from template pipeline using few methods:
     * (using next only if prev method fail and returns null)
     * 1. By name using: {@link Class#getName()}
     * 2. By name using: {@link Class#getSimpleName()}, if that also fail then it will use first
     * 3. By type: template which {@link TemplateDeserializer#isValidType(Class)} method will return true.
     *
     * @param field field to find template deserializer for it.
     *
     * @return template element deserializer.
     */
    public static TemplateDeserializer<?> getElement(final ConfigField field)
    {
        return getElement(field.getField().getType());
    }

    static
    {
        // TODO?
    }
}
