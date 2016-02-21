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

package org.diorite.cfg.system.elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.diorite.cfg.annotations.CfgCollectionStyle.CollectionStyle;
import org.diorite.cfg.annotations.CfgCollectionType.CollectionType;
import org.diorite.cfg.system.CfgEntryData;
import org.diorite.cfg.system.ConfigField;
import org.diorite.cfg.system.FieldOptions;
import org.diorite.cfg.system.Template;
import org.diorite.cfg.system.TemplateCreator;
import org.diorite.utils.collections.arrays.ReflectArrayIterator;
import org.diorite.utils.reflections.DioriteReflectionUtils;
import org.diorite.utils.reflections.ReflectElement;

/**
 * Template handler for all iterable-based objects.
 *
 * @see Iterable
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class IterableTemplateElement extends TemplateElement<Iterable>
{
    /**
     * Instance of template to direct-use.
     */
    public static final IterableTemplateElement INSTANCE                                 = new IterableTemplateElement();
    static final        int                     DEFAULT_STRING_ARRAY_MULTILINE_THRESHOLD = 25;

    /**
     * Construct new iterable template handler.
     */
    public IterableTemplateElement()
    {
        super(Iterable.class);
    }

    @Override
    protected boolean canBeConverted0(final Class<?> c)
    {
        return Map.class.isAssignableFrom(c);
    }

    @Override
    protected Iterable convertObject0(final Object obj) throws UnsupportedOperationException
    {
        if (obj instanceof Map)
        {
            return ((Map) obj).entrySet();
        }
        throw this.getException(obj);
    }

    @Override
    protected Iterable convertDefault0(final Object obj, final Class<?> fieldType)
    {
        if (obj instanceof Iterable)
        {
            return (Iterable) obj;
        }
        final Class<?> c = obj.getClass();
        if (c.isArray())
        {
            return new ReflectArrayIterator(obj);
        }
        if (obj instanceof Iterator)
        {
            final Collection col = new ArrayList<>(10);
            ((Iterator) obj).forEachRemaining(col::add);
            return col;
        }
        throw this.getException(obj);
    }

    @Override
    public void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object elementRaw, final int level, final ElementPlace elementPlace) throws IOException
    {
        final CollectionStyle style = field.getOption(FieldOptions.COLLECTION_STYLE, CollectionStyle.DEFAULT);
        final CollectionType type = field.getOption(FieldOptions.COLLECTION_TYPE, CollectionType.UNKNOWN);
        final boolean commentEveryElement = field.getOption(FieldOptions.OTHERS_COMMENT_EVERY_ELEMENT, false);

        final Iterable element = (elementRaw instanceof Iterable) ? ((Iterable) elementRaw) : this.validateType(elementRaw);
        if (((element instanceof Collection) && ((Collection) element).isEmpty()) || (! element.iterator().hasNext())) // empty
        {
            writer.append("[]");
            return;
        }

        if (style == CollectionStyle.ALWAYS_SIMPLE)
        {
            this.writeSimple(writer, field, element);
            return;
        }

        if ((style == CollectionStyle.ALWAYS_NEW_LINE) || (type == CollectionType.OBJECTS))
        {
            writeNewLines(writer, field, element, level, (type == CollectionType.OBJECTS) && commentEveryElement);
            return;
        }

        Boolean isPrimitiveOrStrings = null;
        if (((type == CollectionType.PRIMITIVES) && (style == CollectionStyle.SIMPLE_IF_PRIMITIVES)) || (((type == CollectionType.STRINGS) || (type == CollectionType.STRINGS_AND_PRIMITIVES)) && (style == CollectionStyle.SIMPLE_IF_PRIMITIVES_OR_STRINGS)) || ((style == CollectionStyle.SIMPLE_IF_PRIMITIVES) && isPrimitive(element)) || ((style == CollectionStyle.SIMPLE_IF_PRIMITIVES_OR_STRINGS) && (isPrimitiveOrStrings = isPrimitiveOrStrings(element))))
        {
            this.writePrimitivesSimple(writer, field, element);
            return;
        }

        if (type == CollectionType.STRINGS)
        {
            final int threshold = field.getOption(FieldOptions.STRING_ARRAY_MULTILINE_THRESHOLD, DEFAULT_STRING_ARRAY_MULTILINE_THRESHOLD);
            if (! isAnyStringBigger(element, threshold))
            {
                this.writePrimitivesSimple(writer, field, element);
                return;
            }
            writeNewLines(writer, field, element, level, false);
            return;
        }

        if (((isPrimitiveOrStrings == null) && ((isPrimitiveOrStrings = isPrimitiveOrStrings(element)))) || isPrimitiveOrStrings)
        {
            this.writePrimitivesSimple(writer, field, element);
            return;
        }

        writeNewLines(writer, field, element, level, commentEveryElement);
    }

    private void writeSimple(final Appendable writer, final CfgEntryData field, final Iterable element) throws IOException
    {
        writer.append('[');
        final Iterator iterator = element.iterator();
        if (iterator.hasNext())
        {
            while (true)
            {
                final Object o = iterator.next();
                final Class<?> oc = o.getClass();
                if (oc.isArray())
                {
                    this.appendValue(writer, field, element, new ReflectArrayIterator(o), - 1, ElementPlace.SIMPLE_LIST_OR_MAP);

                }
                else
                {
                    TemplateElements.getElement(oc).writeValue(writer, field, element, o, - 1, false, ElementPlace.SIMPLE_LIST_OR_MAP);
                }

                if (iterator.hasNext())
                {
                    writer.append(", ");
                }
                else
                {
                    break;
                }
            }
        }
        writer.append(']');
    }

    private static void writeNewLines(final Appendable writer, final CfgEntryData field, final Iterable element, final int level, final boolean commentEveryElement) throws IOException
    {
        boolean isFirst = true;
        writer.append('\n');
        for (final Iterator iterator = element.iterator(); iterator.hasNext(); )
        {
            final Object o = iterator.next();
            appendElement(writer, level, "- ");
            TemplateElements.getElement(o.getClass()).writeValue(writer, field, element, o, level, commentEveryElement || isFirst, ElementPlace.LIST);
            isFirst = false;
            if (iterator.hasNext())
            {
                writer.append('\n');
            }
        }
    }

    private void writePrimitivesSimple(final Appendable writer, final CfgEntryData field, final Iterable element) throws IOException
    {
        writer.append('[');
        final Iterator iterator = element.iterator();
        if (iterator.hasNext())
        {
            while (true)
            {
                final Object o = iterator.next();
                final Class<?> oc = o.getClass();
                if (DioriteReflectionUtils.getPrimitive(oc).isPrimitive() || String.class.isAssignableFrom(oc))
                {
                    TemplateElements.getElement(o.getClass()).writeValue(writer, field, element, o, - 1, false, ElementPlace.SIMPLE_LIST_OR_MAP);
                }
                else
                {
                    Class<?> arrayClass = oc;
                    while (arrayClass.isArray())
                    {
                        arrayClass = arrayClass.getComponentType();
                        if (DioriteReflectionUtils.getPrimitive(arrayClass).isPrimitive() || String.class.isAssignableFrom(arrayClass))
                        {
                            this.appendValue(writer, field, element, new ReflectArrayIterator(o), - 1, ElementPlace.SIMPLE_LIST_OR_MAP);
                        }
                    }
                }
                if (iterator.hasNext())
                {
                    writer.append(", ");
                }
                else
                {
                    break;
                }
            }
        }
        writer.append(']');
    }

    static boolean isPrimitive(final Object object)
    {
        if (object == null)
        {
            return true;
        }
        if (object instanceof Iterator)
        {
            for (final Iterator iterator = (Iterator) object; iterator.hasNext(); )
            {
                final Object o = iterator.next();
                if (! isPrimitive(o))
                {
                    return false;
                }
            }
            return true;
        }
        if (object instanceof Iterable)
        {
            for (final Object o : (Iterable) object)
            {
                if (! isPrimitive(o))
                {
                    return false;
                }
            }
            return true;
        }
        final Class<?> c = object.getClass();
        if (c.isArray())
        {
            if (Object[].class.isAssignableFrom(c.getComponentType()))
            {
                return isPrimitive(new ReflectArrayIterator(object));
            }
            return DioriteReflectionUtils.getPrimitive(c.getComponentType()).isPrimitive();
        }
        return DioriteReflectionUtils.getPrimitive(c).isPrimitive();
    }

    static boolean isPrimitiveOrStrings(final Object object)
    {
        if (object == null)
        {
            return true;
        }
        if (object instanceof Iterator)
        {
            for (final Iterator iterator = (Iterator) object; iterator.hasNext(); )
            {
                final Object o = iterator.next();
                if (! isPrimitiveOrStrings(o))
                {
                    return false;
                }
            }
            return true;
        }
        if (object instanceof Iterable)
        {
            for (final Object o : (Iterable) object)
            {
                if (! isPrimitiveOrStrings(o))
                {
                    return false;
                }
            }
            return true;
        }
        final Class<?> c = object.getClass();
        if (c.isArray())
        {
            if (Object[].class.isAssignableFrom(c.getComponentType()))
            {
                return isPrimitiveOrStrings(new ReflectArrayIterator(object));
            }
            return DioriteReflectionUtils.getPrimitive(c.getComponentType()).isPrimitive() || (String.class.isAssignableFrom(c.getComponentType()) && ! containsMultilineStrings(object));
        }
        return DioriteReflectionUtils.getPrimitive(c).isPrimitive() || (String.class.isAssignableFrom(c) && ! containsMultilineStrings(object));
    }

    static boolean isAnyStringBigger(final Object object, final int maxLength)
    {
        if (object == null)
        {
            return false;
        }
        if (object instanceof Iterator)
        {
            for (final Iterator iterator = (Iterator) object; iterator.hasNext(); )
            {
                final Object o = iterator.next();
                if (isAnyStringBigger(o, maxLength))
                {
                    return true;
                }
            }
            return false;
        }
        if (object instanceof Iterable)
        {
            for (final Object o : (Iterable) object)
            {
                if (isAnyStringBigger(o, maxLength))
                {
                    return true;
                }
            }
            return false;
        }
        final Class<?> c = object.getClass();
        if (c.isArray())
        {
            if (Object[].class.isAssignableFrom(c.getComponentType()))
            {
                return isAnyStringBigger(new ReflectArrayIterator(object), maxLength);
            }
            throw new RuntimeException("Expected CharSequence, but found: " + c.getName());
        }
        else if (object instanceof CharSequence)
        {
            return (((CharSequence) object).length() > maxLength) || containsMultilineStrings(object);
        }
        else
        {
            throw new RuntimeException("Expected CharSequence, but found: " + c.getName());
        }
    }

    static boolean containsMultilineStrings(final Object object)
    {
        if (object == null)
        {
            return false;
        }
        if (object instanceof Iterator)
        {
            for (final Iterator iterator = (Iterator) object; iterator.hasNext(); )
            {
                final Object o = iterator.next();
                if (containsMultilineStrings(o))
                {
                    return true;
                }
            }
            return false;
        }
        if (object instanceof Iterable)
        {
            for (final Object o : (Iterable) object)
            {
                if (containsMultilineStrings(o))
                {
                    return true;
                }
            }
            return false;
        }
        if (object instanceof Map)
        {
            for (final Object entry : ((Map) object).entrySet())
            {
                if (containsMultilineStrings(entry))
                {
                    return true;
                }
            }
            return false;
        }
        if (object instanceof Entry)
        {
            final Entry entry = (Entry) object;
            return containsMultilineStrings(entry.getKey()) || containsMultilineStrings(entry.getValue());
        }
        final Class<?> c = object.getClass();
        if (c.isArray())
        {
            return Object[].class.isAssignableFrom(c.getComponentType()) && containsMultilineStrings(new ReflectArrayIterator(object));
        }
        else
        {
            if (object instanceof CharSequence)
            {
                if (object.toString().indexOf('\n') != - 1)
                {
                    return true;
                }
            }
            else
            {
                final Template<?> template = TemplateCreator.getTemplate(c, false);
                if (template == null)
                {
                    return false;
                }
                for (final Entry<ConfigField, ReflectElement<?>> entry : template.getFields().entrySet())
                {
                    if (containsMultilineStrings(entry.getValue().get(object)))
                    {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
