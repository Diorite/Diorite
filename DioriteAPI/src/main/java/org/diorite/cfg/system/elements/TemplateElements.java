package org.diorite.cfg.system.elements;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import org.diorite.cfg.system.CfgEntryData;
import org.diorite.cfg.system.ConfigField;
import org.diorite.utils.pipeline.BasePipeline;
import org.diorite.utils.pipeline.Pipeline;
import org.diorite.utils.reflections.DioriteReflectionUtils;

/**
 * Manager class for element templates.
 * <p>
 * It contains {@link Pipeline} {@link #elements} that contains templates for default java object types.
 * Method {@link #getElement(Class)} is trying to get element from pipleline using few methods:
 * (using next only if prev method fail and returns null)
 * 1. By name using: {@link Class#getName()}
 * 2. By name using: {@link Class#getSimpleName()}, if that also fail then it will use first
 * 3. By type: template with {@link TemplateElement#fieldType} that is {@link Class#isAssignableFrom(Class)} to given class
 * 4. By type: template which {@link TemplateElement#canBeConverted(Class)} method will return true.
 * 5. If there is still no matching template, {@link #getDefaultTemplatesHandler()} is used.
 * <p>
 * Elements in pipeline should be in valid order,
 * object types that are 'hard' to convert, should be first,
 * and types that can be easly convert should be last.
 * <p>
 * In default pipeline, first element is {@link Map}, only classes implementing that interface can be used with this template,
 * if you want add map impelementation that should use other template you MUST add it BEFORE this element, by:
 * {@link #getElements()} and {@link Pipeline#addBefore(String, String, Object)}
 * <p>
 * Second element is {@link Iterable}, if object implementing map interface will be passed to
 * iterable template, it will convert it to {@link Map#entrySet()}
 * <p>
 * Next elements are all primitives arrays: boolean, char, long, int, short. byte, double, float,
 * then Object[], all primitives and {@link String} at the end.
 */
@SuppressWarnings("rawtypes")
public final class TemplateElements
{
    private static final Pipeline<TemplateElement<?>> elements = new BasePipeline<>();

    private TemplateElements()
    {
    }

    public static Pipeline<TemplateElement<?>> getElements()
    {
        return elements;
    }

    private static TemplateElement<Object> defaultTemplatesHandler = new TemplateElement<Object>(Object.class, obj -> obj, c -> true)
    {
        @Override
        protected void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object element, final int level) throws IOException
        {
            StringTemplateElement.INSTANCE.appendValue(writer, field, source, StringTemplateElement.INSTANCE.validateType(element), level);
        }
    };

    /**
     * Default template handler for all data that don't match in other handlers.
     *
     * @return default template handler.
     */
    public static TemplateElement<Object> getDefaultTemplatesHandler()
    {
        return defaultTemplatesHandler;
    }

    /**
     * change default template handler for all data that don't match in other handlers.
     *
     * @param defaultTemplatesHandler new template hanlder
     */
    public static void setDefaultTemplatesHandler(final TemplateElement<Object> defaultTemplatesHandler)
    {
        Validate.notNull(defaultTemplatesHandler, "unhandled handler can't be null");
        TemplateElements.defaultTemplatesHandler = defaultTemplatesHandler;
    }

    /**
     * This method is trying to get element from template pipleline using few methods:
     * (using next only if prev method fail and returns null)
     * 1. By name using: {@link Class#getName()}
     * 2. By name using: {@link Class#getSimpleName()}, if that also fail then it will use first
     * 3. By type: template with {@link TemplateElement#fieldType} that is {@link Class#isAssignableFrom(Class)} to given class
     * 4. By type: template which {@link TemplateElement#canBeConverted(Class)} method will return true.
     * 5. If there is still no matching template, {@link #getDefaultTemplatesHandler()} is used.
     *
     * @param clazz class to find template for it.
     *
     * @return template element.
     */
    public static TemplateElement<?> getElement(Class<?> clazz)
    {
        clazz = DioriteReflectionUtils.getPrimitive(clazz);
        TemplateElement<?> element = elements.get(clazz.getName());
        if (element != null)
        {
            return element;
        }
        element = elements.get(clazz.getSimpleName());
        if (element != null)
        {
            return element;
        }
        for (final TemplateElement<?> e : elements)
        {
            if (e.isValidType(clazz))
            {
                return e;
            }
        }
        for (final TemplateElement<?> e : elements)
        {
            if (e.canBeConverted(clazz))
            {
                return e;
            }
        }
        return defaultTemplatesHandler;
    }

    static
    {
        elements.addLast(Map.class.getName(), new MapTemplateElement());
        elements.addLast(Iterable.class.getName(), new IterableTemplateElement());

        elements.addLast(boolean.class.getName(), new SimpleTemplateElement<>(boolean.class, obj -> {
            throw new UnsupportedOperationException("Can't convert object to Boolean: " + obj);
        }));
        elements.addLast(char.class.getName(), new SimpleTemplateElement<>(char.class, obj -> {
            throw new UnsupportedOperationException("Can't convert object to Char: " + obj);
        }));
        elements.addLast(long.class.getName(), new SimpleTemplateElement<>(long.class, obj -> {
            if (obj instanceof Number)
            {
                return ((Number) obj).longValue();
            }
            throw new UnsupportedOperationException("Can't convert object to Long: " + obj);
        }));
        elements.addLast(int.class.getName(), new SimpleTemplateElement<>(int.class, obj -> {
            if (obj instanceof Number)
            {
                return ((Number) obj).intValue();
            }
            throw new UnsupportedOperationException("Can't convert object to Int: " + obj);
        }));
        elements.addLast(short.class.getName(), new SimpleTemplateElement<>(short.class, obj -> {
            if (obj instanceof Number)
            {
                return ((Number) obj).shortValue();
            }
            throw new UnsupportedOperationException("Can't convert object to Short: " + obj);
        }));
        elements.addLast(byte.class.getName(), new SimpleTemplateElement<>(byte.class, obj -> {
            if (obj instanceof Number)
            {
                return ((Number) obj).byteValue();
            }
            throw new UnsupportedOperationException("Can't convert object to Byte: " + obj);
        }));
        elements.addLast(double.class.getName(), new SimpleTemplateElement<>(double.class, obj -> {
            if (obj instanceof Number)
            {
                return ((Number) obj).doubleValue();
            }
            throw new UnsupportedOperationException("Can't convert object to Double: " + obj);
        }));
        elements.addLast(float.class.getName(), new SimpleTemplateElement<>(float.class, obj -> {
            if (obj instanceof Number)
            {
                return ((Number) obj).floatValue();
            }
            throw new UnsupportedOperationException("Can't convert object to Float: " + obj);
        }));

        elements.addLast(String.class.getName(), new StringTemplateElement());
    }

    public static void main(String[] args) throws IOException
    {
        Test t = new Test();
        StringBuilder builder = new StringBuilder(200);
        {
            TemplateElement element = getElement(boolean.class);
            element.appendValue(builder, new ConfigField(DioriteReflectionUtils.getField(Test.class, "b1").getField(), 0), t, DioriteReflectionUtils.getReflectGetter("b1", t.getClass()), 0);
        }
        builder.append(", ");
        {
            TemplateElement element = getElement(Boolean.class);
            element.appendValue(builder, new ConfigField(DioriteReflectionUtils.getField(Test.class, "b1").getField(), 0), t, DioriteReflectionUtils.getReflectGetter("b1", t.getClass()), 0);
        }
        builder.append(", ");
        {

            TemplateElement element = getElement(boolean.class);
            element.appendValue(builder, new ConfigField(DioriteReflectionUtils.getField(Test.class, "b2").getField(), 0), t, DioriteReflectionUtils.getReflectGetter("b2", t.getClass()), 0);
        }
        builder.append(", ");
        {

            TemplateElement element = getElement(Boolean.class);
            element.appendValue(builder, new ConfigField(DioriteReflectionUtils.getField(Test.class, "b2").getField(), 0), t, DioriteReflectionUtils.getReflectGetter("b2", t.getClass()), 0);
        }
        builder.append(";");
        System.out.println(builder);
    }

    public static class Test
    {
        boolean b1 = false;
        Boolean b2 = true;
    }
}
