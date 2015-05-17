package org.diorite.cfg.system.elements;

import java.io.IOException;
import java.util.Collection;

import org.diorite.cfg.system.CfgEntryData;
import org.diorite.utils.collections.arrays.ObjectArrayIterator;

/**
 * Template handler for all object arrays objects.
 * It just warp array into iterable and use {@link IterableTemplateElement}
 * @see IterableTemplateElement
 */
@SuppressWarnings("rawtypes")
public class ObjectArrayTemplateElement extends SimpleArrayTemplateElement<Object[]>
{
    /**
     * Instance of template to direct-use.
     */
    public static final ObjectArrayTemplateElement INSTANCE = new ObjectArrayTemplateElement();

    /**
     * Construct new object array template handler.
     */
    public ObjectArrayTemplateElement()
    {
        super(Object[].class, obj -> {
            if (obj instanceof Collection)
            {
                return ((Collection) obj).toArray();
            }
            throw new UnsupportedOperationException("Can't convert object to Object[]: " + obj);
        }, Collection.class::isAssignableFrom);
    }

    @Override
    protected void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object elementRaw, final int level, final ElementPlace elementPlace) throws IOException
    {
        IterableTemplateElement.INSTANCE.appendValue(writer, field, source, new ObjectArrayIterator((elementRaw instanceof Object[]) ? ((Object[]) elementRaw) : this.validateType(elementRaw)), level, elementPlace);
    }
}
