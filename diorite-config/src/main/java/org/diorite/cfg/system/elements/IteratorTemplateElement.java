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
import java.util.Iterator;
import java.util.Map;

import org.diorite.cfg.annotations.CfgCollectionStyle.CollectionStyle;
import org.diorite.cfg.annotations.CfgCollectionType.CollectionType;
import org.diorite.cfg.system.CfgEntryData;
import org.diorite.cfg.system.FieldOptions;
import org.diorite.utils.collections.arrays.ReflectArrayIterator;
import org.diorite.utils.reflections.DioriteReflectionUtils;

/**
 * Template handler for all iterator-based objects.
 *
 * @see Iterator
 */
@SuppressWarnings({"rawtypes"})
public class IteratorTemplateElement extends TemplateElement<Iterator>
{
    /**
     * Instance of template to direct-use.
     */
    public static final IteratorTemplateElement INSTANCE = new IteratorTemplateElement();

    /**
     * Construct new iterator template handler.
     */
    public IteratorTemplateElement()
    {
        super(Iterator.class);
    }

    @Override
    protected boolean canBeConverted0(final Class<?> c)
    {
        return Map.class.isAssignableFrom(c);
    }

    @Override
    protected Iterator convertObject0(final Object obj) throws UnsupportedOperationException
    {
        if (obj instanceof Iterable)
        {
            return ((Iterable) obj).iterator();
        }
        throw new UnsupportedOperationException("Can't convert object (" + obj.getClass().getName() + ") to Iterator: " + obj);
    }

    @Override
    protected Iterator convertDefault0(final Object obj, final Class<?> fieldType)
    {
        if (obj instanceof Iterator)
        {
            return (Iterator) obj;
        }
        final Class<?> c = obj.getClass();
        if (c.isArray())
        {
            return new ReflectArrayIterator(obj);
        }
        if (obj instanceof Iterable)
        {
            return ((Iterable) obj).iterator();
        }
        throw new UnsupportedOperationException("Can't convert default value (" + c.getName() + "): " + obj);
    }

    @Override
    public void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object elementRaw, final int level, final ElementPlace elementPlace) throws IOException
    {
        final CollectionStyle style = field.getOption(FieldOptions.COLLECTION_STYLE, CollectionStyle.DEFAULT);
        final CollectionType type = field.getOption(FieldOptions.COLLECTION_TYPE, CollectionType.UNKNOWN);
        final boolean commentEveryElement = field.getOption(FieldOptions.OTHERS_COMMENT_EVERY_ELEMENT, false);

        final Iterator element = (elementRaw instanceof Iterator) ? ((Iterator) elementRaw) : this.validateType(elementRaw);
        if (! element.hasNext()) // empty
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
        if (((type == CollectionType.PRIMITIVES) && (style == CollectionStyle.SIMPLE_IF_PRIMITIVES)) || (((type == CollectionType.STRINGS) || (type == CollectionType.STRINGS_AND_PRIMITIVES)) && (style == CollectionStyle.SIMPLE_IF_PRIMITIVES_OR_STRINGS)) || ((style == CollectionStyle.SIMPLE_IF_PRIMITIVES) && IterableTemplateElement.isPrimitive(element)) || ((style == CollectionStyle.SIMPLE_IF_PRIMITIVES_OR_STRINGS) && (isPrimitiveOrStrings = IterableTemplateElement.isPrimitiveOrStrings(element))))
        {
            this.writePrimitivesSimple(writer, field, element);
            return;
        }

        if (type == CollectionType.STRINGS)
        {
            final int threshold = field.getOption(FieldOptions.STRING_ARRAY_MULTILINE_THRESHOLD, IterableTemplateElement.DEFAULT_STRING_ARRAY_MULTILINE_THRESHOLD);
            if (! IterableTemplateElement.isAnyStringBigger(element, threshold))
            {
                this.writePrimitivesSimple(writer, field, element);
                return;
            }
            writeNewLines(writer, field, element, level, false);
            return;
        }

        if (((isPrimitiveOrStrings == null) && ((isPrimitiveOrStrings = IterableTemplateElement.isPrimitiveOrStrings(element)))) || isPrimitiveOrStrings)
        {
            this.writePrimitivesSimple(writer, field, element);
            return;
        }

        writeNewLines(writer, field, element, level, commentEveryElement);
    }

    private void writeSimple(final Appendable writer, final CfgEntryData field, final Iterator element) throws IOException
    {
        writer.append('[');
        if (element.hasNext())
        {
            while (true)
            {
                final Object o = element.next();
                final Class<?> oc = o.getClass();
                if (oc.isArray())
                {
                    this.appendValue(writer, field, element, new ReflectArrayIterator(o), - 1, ElementPlace.SIMPLE_LIST_OR_MAP);
                }
                else
                {
                    TemplateElements.getElement(oc).writeValue(writer, field, element, o, - 1, false, ElementPlace.SIMPLE_LIST_OR_MAP);
                }

                if (element.hasNext())
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

    private static void writeNewLines(final Appendable writer, final CfgEntryData field, final Iterator element, final int level, final boolean commentEveryElement) throws IOException
    {
        boolean isFirst = true;
        writer.append('\n');
        while (element.hasNext())
        {
            final Object o = element.next();
            appendElement(writer, level, "- ");
            TemplateElements.getElement(o.getClass()).writeValue(writer, field, element, o, level + 1, commentEveryElement || isFirst, ElementPlace.LIST);
            isFirst = false;
            if (element.hasNext())
            {
                writer.append('\n');
            }
        }
    }

    private void writePrimitivesSimple(final Appendable writer, final CfgEntryData field, final Iterator element) throws IOException
    {
        writer.append('[');
        if (element.hasNext())
        {
            while (true)
            {
                final Object o = element.next();
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
                if (element.hasNext())
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
}
