package org.diorite.cfg.system.elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.diorite.cfg.annotations.CfgCollectionStyle.CollectionStyle;
import org.diorite.cfg.system.CfgEntryData;
import org.diorite.cfg.system.FieldOptions;

@SuppressWarnings("rawtypes")
public class IterableTemplateElement extends TemplateElement<Iterable>
{
    public static final IterableTemplateElement INSTANCE = new IterableTemplateElement();

    public IterableTemplateElement()
    {
        super(Iterable.class, obj -> {
            if (obj instanceof Map)
            {
                return ((Map) obj).entrySet();
            }
            throw new UnsupportedOperationException("Can't convert object to Iterable: " + obj);
        }, Map.class::isAssignableFrom);
    }

    @Override
    protected void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Iterable element, final int level) throws IOException
    {
        final CollectionStyle style = field.getOption(FieldOptions.COLLECTION_STYLE, CollectionStyle.DEFAULT);

        final boolean commentEveryElement = field.getOption(FieldOptions.OTHERS_COMMENT_EVERY_ELEMENT, false);
        boolean isFirst = true;
        if (((element instanceof Collection) && ((Collection) element).isEmpty()) || (! element.iterator().hasNext())) // empty
        {
            writer.append("[]");
            return;
        }
        if ((style == CollectionStyle.SIMPLE_IF_PRIMITIVES) || (style == CollectionStyle.SIMPLE_IF_PRIMITIVES_OR_STRINGS))
        {
            boolean withStrings = style == CollectionStyle.SIMPLE_IF_PRIMITIVES_OR_STRINGS;
            final int size = (element instanceof Collection) ? ((Collection) element).size() : 10;
            final Collection<Object> objects = new ArrayList<>(size);
            for (Object o : element)
            {

            }
        }
        switch (style)
        {
            case SIMPLE_IF_PRIMITIVES:
            {
            }
            break;
            case SIMPLE_IF_PRIMITIVES_OR_STRINGS:
                break;
            case ALWAYS_SIMPLE:
                break;
            case ALWAYS_NEW_LINE:
                break;
            default:
                writer.append('\n');
                for (final Object obj : element)
                {
                    final TemplateElement tempElement = TemplateElements.getElement(obj.getClass());
                    tempElement.writeValue(writer, field, element, obj, level + 1, commentEveryElement || isFirst);
                    isFirst = false;
                }
                break;
        }
    }
}
