package org.diorite.cfg.system.elements;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.diorite.cfg.annotations.CfgCollectionStyle.CollectionStyle;
import org.diorite.cfg.annotations.CfgCollectionType.CollectionType;
import org.diorite.cfg.system.CfgEntryData;
import org.diorite.cfg.system.FieldOptions;
import org.diorite.utils.collections.arrays.ReflectArrayIterator;
import org.diorite.utils.reflections.DioriteReflectionUtils;

/**
 * Template handler for all iterable-based objects.
 * @see Map
 */
@SuppressWarnings({"rawtypes", "unchecked", "ObjectEquality"})
public class MapTemplateElement extends TemplateElement<Map>
{
    /**
     * Instance of template to direct-use.
     */
    public static final MapTemplateElement INSTANCE = new MapTemplateElement();

    /**
     * Construct new map template handler.
     */
    public MapTemplateElement()
    {
        super(Map.class, obj -> {
            throw new UnsupportedOperationException("Can't convert object (" + obj.getClass().getName() + ") to Map: " + obj);
        }, c -> false);
    }

    @Override
    protected Map convertDefault0(final Object def, final Class<?> fieldType)
    {
        if (def instanceof Map)
        {
            return (Map) def;
        }
        throw new UnsupportedOperationException("Can't convert default value (" + def.getClass().getName() + "): " + def);
    }

    @Override
    protected void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object elementRaw, final int level, final ElementPlace elementPlace) throws IOException
    {
        final CollectionStyle style = field.getOption(FieldOptions.COLLECTION_STYLE, CollectionStyle.DEFAULT);
        final CollectionType type = field.getOption(FieldOptions.COLLECTION_TYPE, CollectionType.UNKNOWN);
        final boolean commentEveryElement = field.getOption(FieldOptions.OTHERS_COMMENT_EVERY_ELEMENT, false);

        final Map element = (elementRaw instanceof Map) ? ((Map) elementRaw) : this.validateType(elementRaw);
        if (element.isEmpty())
        {
            writer.append("{}");
            return;
        }

        if (style == CollectionStyle.ALWAYS_SIMPLE)
        {
            this.writeSimple(writer, field, element, level);
            return;
        }

        if ((style == CollectionStyle.ALWAYS_NEW_LINE) || (type == CollectionType.OBJECTS))
        {
            writeNewLines(writer, field, element, level, (type == CollectionType.OBJECTS) && commentEveryElement);
            return;
        }

        if (((type == CollectionType.PRIMITIVES) && (style == CollectionStyle.SIMPLE_IF_PRIMITIVES)) || (((type == CollectionType.STRINGS) || (type == CollectionType.STRINGS_AND_PRIMITIVES)) && (style == CollectionStyle.SIMPLE_IF_PRIMITIVES_OR_STRINGS)) || ((style == CollectionStyle.SIMPLE_IF_PRIMITIVES) && isPrimitive(element)) || ((style == CollectionStyle.SIMPLE_IF_PRIMITIVES_OR_STRINGS) && (isPrimitiveOrStrings(element))))
        {
            this.writePrimitivesSimple(writer, field, element, level);
            return;
        }

        // map should be always line be line by default, simple format make it ugly.

//        if (type == CollectionType.STRINGS)
//        {
//            final int threshold = field.getOption(FieldOptions.STRING_ARRAY_MULTILINE_THRESHOLD, IterableTemplateElement.DEFAULT_STRING_ARRAY_MULTILINE_THRESHOLD);
//            if (! isAnyStringBigger(element, threshold))
//            {
//                this.writePrimitivesSimple(writer, field, element, level);
//                return;
//            }
//            writeNewLines(writer, field, element, level, false);
//            return;
//        }
//
//        if (((isPrimitiveOrStrings == null) && ((isPrimitiveOrStrings = isPrimitiveOrStrings(element)))) || isPrimitiveOrStrings)
//        {
//            this.writePrimitivesSimple(writer, field, element, level);
//            return;
//        }

        writeNewLines(writer, field, element, level, commentEveryElement);
    }

    private void writeSimple(final Appendable writer, final CfgEntryData field, final Map<?, ?> element, final int level) throws IOException
    {
        writer.append('{');
        final Iterator<? extends Entry<?, ?>> iterator = element.entrySet().iterator();
        while (true)
        {
            final Entry<?, ?> entry = iterator.next();
            Object key = entry.getKey();
            final Class<?> kc;
            if (key == null)
            {
                key = "";
            }
            if (DioriteReflectionUtils.getPrimitive(kc = key.getClass()).isPrimitive() || (String.class.isAssignableFrom(kc) && (key.toString().indexOf('\n') == - 1)))
            {
                TemplateElements.getElement(String.class).writeValue(writer, field, entry, key, - 1, false, ElementPlace.SIMPLE_LIST_OR_MAP);
                writer.append(": ");
            }
            else
            {
                writer.append("? ");
                TemplateElements.getElement(kc).writeValue(writer, field, entry, key, level + 1, false, ElementPlace.SIMPLE_LIST_OR_MAP);
                writer.append(" : ");
            }

            final Object value = entry.getValue();
            if (value == null)
            {
                writer.append("null");
                if (! iterator.hasNext())
                {
                    break;
                }
                writer.append(", ");
                continue;
            }
            final Class<?> oc = value.getClass();
            if (oc.isArray())
            {
                IterableTemplateElement.INSTANCE.appendValue(writer, field, element, new ReflectArrayIterator(value), - 1, ElementPlace.SIMPLE_LIST_OR_MAP);
            }
            else
            {
                TemplateElements.getElement(oc).writeValue(writer, field, element, value, - 1, false, ElementPlace.SIMPLE_LIST_OR_MAP);
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
        writer.append('}');
    }

    private static void writeNewLines(final Appendable writer, final CfgEntryData field, final Map element, final int level, final boolean commentEveryElement) throws IOException
    {
        boolean isFirst = true;
        writer.append('\n');
        final Iterator<? extends Entry<?, ?>> iterator = element.entrySet().iterator();
        while (true)
        {
            final Entry<?, ?> entry = iterator.next();
            Object key = entry.getKey();
            final Class<?> kc;
            if (key == null)
            {
                key = "";
            }
            if (DioriteReflectionUtils.getPrimitive(kc = key.getClass()).isPrimitive() || (String.class.isAssignableFrom(kc) && (key.toString().indexOf('\n') == - 1)))
            {
                spaces(writer, level + 1);
                TemplateElements.getElement(key.getClass()).appendValue(writer, field, entry, key, level + 1, ElementPlace.NORMAL);
                writer.append(": ");
            }
            else
            {
                appendElement(writer, level + 1, "? ");
                TemplateElements.getElement(key.getClass()).writeValue(writer, field, entry, key, level + 1, false, ElementPlace.NORMAL);
                writer.append('\n');
                appendElement(writer, level + 1, ": ");
            }

            final Object value = entry.getValue();
            if (value == null)
            {
                writer.append("null\n");
                if (! iterator.hasNext())
                {
                    break;
                }
                continue;
            }
            TemplateElements.getElement(value.getClass()).writeValue(writer, field, element, value, level + 1, commentEveryElement || isFirst, ElementPlace.NORMAL);

            isFirst = false;
            if (! iterator.hasNext())
            {
                break;
            }
            else
            {
                writer.append('\n');
            }
        }
    }

    private void writePrimitivesSimple(final Appendable writer, final CfgEntryData field, final Map<?, ?> element, final int level) throws IOException
    {
        writer.append('{');
        final Iterator<? extends Entry<?, ?>> iterator = element.entrySet().iterator();
        while (true)
        {
            final Entry<?, ?> entry = iterator.next();
            Object key = entry.getKey();
            final Class<?> kc;
            if (key == null)
            {
                key = "";
            }
            if (DioriteReflectionUtils.getPrimitive(kc = key.getClass()).isPrimitive() || (String.class.isAssignableFrom(kc) && (key.toString().indexOf('\n') == - 1)))
            {
                TemplateElements.getElement(String.class).writeValue(writer, field, entry, key, - 1, false, ElementPlace.SIMPLE_LIST_OR_MAP);
                writer.append(": ");
            }
            else
            {
                writer.append("? ");
                TemplateElements.getElement(kc).writeValue(writer, field, entry, key, level + 1, false, ElementPlace.SIMPLE_LIST_OR_MAP);
                writer.append(" : ");
            }

            final Object value = entry.getValue();
            if (value == null)
            {
                writer.append("null");
                if (! iterator.hasNext())
                {
                    break;
                }
                writer.append(", ");
                continue;
            }
            final Class<?> oc = value.getClass();
            if (DioriteReflectionUtils.getPrimitive(oc).isPrimitive() || String.class.isAssignableFrom(oc) || Map.class.isAssignableFrom(oc))
            {
                TemplateElements.getElement(value.getClass()).writeValue(writer, field, element, value, - 1, false, ElementPlace.SIMPLE_LIST_OR_MAP);
            }
            else
            {
                Class<?> arrayClass = oc;
                while (arrayClass.isArray())
                {
                    arrayClass = arrayClass.getComponentType();
                    if (DioriteReflectionUtils.getPrimitive(arrayClass).isPrimitive() || String.class.isAssignableFrom(arrayClass))
                    {
                        IterableTemplateElement.INSTANCE.appendValue(writer, field, element, new ReflectArrayIterator(value), - 1, ElementPlace.SIMPLE_LIST_OR_MAP);
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
        writer.append('}');
    }

    private static boolean isPrimitiveSimple(final Object object, final boolean withString)
    {
        if (object == null)
        {
            return true;
        }
        final Class<?> c = object.getClass();
        return DioriteReflectionUtils.getPrimitive(c).isPrimitive() || ((! withString || String.class.isAssignableFrom(c)) && ! IterableTemplateElement.containsMultilineStrings(object));
    }

    static boolean isPrimitive(final Object object)
    {
        if (object == null)
        {
            return true;
        }
        if (object instanceof Map)
        {
            final Map<?, ?> map = (Map) object;
            for (final Entry<?, ?> entry : map.entrySet())
            {
                if (! isPrimitive(entry))
                {
                    return false;
                }
            }
            return true;
        }
        if (object instanceof Entry)
        {
            return isPrimitiveSimple(((Entry) object).getKey(), false) && isPrimitive(((Entry) object).getValue());
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
        if (object instanceof Map)
        {
            final Map<?, ?> map = (Map) object;
            for (final Entry<?, ?> entry : map.entrySet())
            {
                if (! isPrimitiveOrStrings(entry))
                {
                    return false;
                }
            }
            return true;
        }
        if (object instanceof Entry)
        {
            return isPrimitiveSimple(((Entry) object).getKey(), true) && isPrimitiveOrStrings(((Entry) object).getValue());
        }
        final Class<?> c = object.getClass();
        if (c.isArray())
        {
            if (Object[].class.isAssignableFrom(c.getComponentType()))
            {
                return isPrimitiveOrStrings(new ReflectArrayIterator(object));
            }
            return DioriteReflectionUtils.getPrimitive(c.getComponentType()).isPrimitive() || (String.class.isAssignableFrom(c.getComponentType()) && ! IterableTemplateElement.containsMultilineStrings(object));
        }
        return DioriteReflectionUtils.getPrimitive(c).isPrimitive() || (String.class.isAssignableFrom(c) && ! IterableTemplateElement.containsMultilineStrings(object));
    }

    static boolean isAnyStringBigger(final Object object, final int maxLength)
    {
        if (object == null)
        {
            return false;
        }
        if (object instanceof Map)
        {
            final Map<?, ?> map = (Map) object;
            for (final Entry<?, ?> entry : map.entrySet())
            {
                if (isAnyStringBigger(entry, maxLength))
                {
                    return true;
                }
            }
            return false;
        }
        if (object instanceof Entry)
        {
            return isAnyStringBigger(((Entry) object).getKey(), maxLength) || isAnyStringBigger(((Entry) object).getValue(), maxLength);
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
            return (((CharSequence) object).length() > maxLength) || IterableTemplateElement.containsMultilineStrings(object);
        }
        else if (object instanceof Number)
        {
            return object.toString().length() > maxLength;
        }
        else
        {
            throw new RuntimeException("Expected CharSequence, but found: " + c.getName());
        }
    }
}
