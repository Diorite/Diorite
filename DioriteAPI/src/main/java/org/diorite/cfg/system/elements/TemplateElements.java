package org.diorite.cfg.system.elements;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import org.diorite.cfg.system.ConfigField;
import org.diorite.cfg.system.elements.primitives.BooleanTemplateElement;
import org.diorite.cfg.system.elements.primitives.ByteTemplateElement;
import org.diorite.cfg.system.elements.primitives.CharTemplateElement;
import org.diorite.cfg.system.elements.primitives.DoubleTemplateElement;
import org.diorite.cfg.system.elements.primitives.FloatTemplateElement;
import org.diorite.cfg.system.elements.primitives.IntTemplateElement;
import org.diorite.cfg.system.elements.primitives.LongTemplateElement;
import org.diorite.cfg.system.elements.primitives.ShortTemplateElement;
import org.diorite.chat.component.BaseComponent;
import org.diorite.utils.SimpleEnum;
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
 * In default pipeline, first elements are {@link Enum}, {@link java.net.URI}, {@link java.net.URL}, {@link File} and {@link java.nio.file.Path} handlers.
 * <p>
 * Next element is {@link Map}, only classes implementing that interface can be used with this template,
 * if you want add map impelementation that should use other template you MUST add it BEFORE this element, by:
 * {@link #getElements()} and {@link Pipeline#addBefore(String, String, Object)}
 * <p>
 * Next element is {@link Iterable}, if object implementing map interface will be passed to
 * iterable template, it will convert it using {@link Map#entrySet()}
 * <p>
 * Next one is {@link Iterator}, if object implementing {@link Iterable} interface will be passed to
 * iterator template, it will convert it using {@link Iterable#iterator()}
 * <p>
 * Next elements are all primitives arrays: boolean, char, long, int, short. byte, double, float,
 * then Object[], all primitives and {@link String} at the end.
 */
public final class TemplateElements
{
    private static final Pipeline<TemplateElement<?>> elements = new BasePipeline<>();

    private TemplateElements()
    {
    }

    /**
     * @return editable pipeline with template elements.
     *
     * @see TemplateElements
     */
    public static Pipeline<TemplateElement<?>> getElements()
    {
        return elements;
    }

    private static TemplateElement<Object> defaultTemplatesHandler = new DefaultTemplateElement();

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

    /**
     * This method is trying to get element from template pipleline using few methods:
     * (using next only if prev method fail and returns null)
     * 1. By name using: {@link Class#getName()}
     * 2. By name using: {@link Class#getSimpleName()}, if that also fail then it will use first
     * 3. By type: template with {@link TemplateElement#fieldType} that is {@link Class#isAssignableFrom(Class)} to given class
     * 4. By type: template which {@link TemplateElement#canBeConverted(Class)} method will return true.
     * 5. If there is still no matching template, {@link #getDefaultTemplatesHandler()} is used.
     *
     * @param field field to find template for it.
     *
     * @return template element.
     */
    public static TemplateElement<?> getElement(final ConfigField field)
    {
        return getElement(field.getField().getType());
    }

    private static <T> void addPrimitiveArray(final Class<T> clazz, final SimpleArrayTemplateElement<T> templateElement)
    {
        elements.addLast(clazz.getName(), templateElement);
        elements.addLast(clazz.getSimpleName(), templateElement);
    }

    static
    {
        elements.addLast(Enum.class.getName(), EnumTemplateElement.INSTANCE);
        elements.addLast(SimpleEnum.class.getName(), SimpleEnumTemplateElement.INSTANCE);
        elements.addLast(URI.class.getName(), URITemplateElement.INSTANCE);
        elements.addLast(URL.class.getName(), URLTemplateElement.INSTANCE);
        elements.addLast(File.class.getName(), FileTemplateElement.INSTANCE);
        elements.addLast(Path.class.getName(), PathTemplateElement.INSTANCE);

        elements.addLast(Locale.class.getName(), LocaleTemplateElement.INSTANCE);
        elements.addLast(BaseComponent.class.getName(), BaseComponentTemplateElement.INSTANCE);

        elements.addLast(Map.class.getName(), MapTemplateElement.INSTANCE);
        elements.addLast(Iterable.class.getName(), IterableTemplateElement.INSTANCE);
        elements.addLast(Iterator.class.getName(), IteratorTemplateElement.INSTANCE);

        elements.addLast(boolean.class.getName(), BooleanTemplateElement.INSTANCE);
        addPrimitiveArray(boolean[].class, SimpleArrayTemplateElement.INSTANCE_BOOLEANS);

        elements.addLast(char.class.getName(), CharTemplateElement.INSTANCE);
        addPrimitiveArray(char[].class, SimpleArrayTemplateElement.INSTANCE_CHARS);

        elements.addLast(long.class.getName(), LongTemplateElement.INSTANCE);
        addPrimitiveArray(long[].class, SimpleArrayTemplateElement.INSTANCE_LONGS);

        elements.addLast(int.class.getName(), IntTemplateElement.INSTANCE);
        addPrimitiveArray(int[].class, SimpleArrayTemplateElement.INSTANCE_INTS);

        elements.addLast(short.class.getName(), ShortTemplateElement.INSTANCE);
        addPrimitiveArray(short[].class, SimpleArrayTemplateElement.INSTANCE_SHORTS);

        elements.addLast(byte.class.getName(), ByteTemplateElement.INSTANCE);
        addPrimitiveArray(byte[].class, SimpleArrayTemplateElement.INSTANCE_BYTES);

        elements.addLast(double.class.getName(), DoubleTemplateElement.INSTANCE);
        addPrimitiveArray(double[].class, SimpleArrayTemplateElement.INSTANCE_DOUBLES);

        elements.addLast(float.class.getName(), FloatTemplateElement.INSTANCE);
        addPrimitiveArray(float[].class, SimpleArrayTemplateElement.INSTANCE_FLOATS);

        elements.addLast(Object[].class.getName(), ObjectArrayTemplateElement.INSTANCE);

        elements.addLast(String.class.getName(), StringTemplateElement.INSTANCE);
    }
}
