package org.diorite.cfg.system.elements.primitives;

import java.io.IOException;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.system.CfgEntryData;
import org.diorite.cfg.system.elements.TemplateElement;

/**
 * Template used by primitive values.
 */
public abstract class SimpleTemplateElement<T> extends TemplateElement<T>
{
    /**
     * construct new template for given class, convert function and class type checking function.
     *
     * @param fieldType          type of supproted template element.
     * @param function       function used to convert other types to this type (may throw errors)
     * @param classPredicate returns true for classes that can be converted into supproted type.
     */
    public SimpleTemplateElement(final Class<T> fieldType, final Function<Object, T> function, final Predicate<Class<?>> classPredicate)
    {
        super(fieldType, function, classPredicate);
    }

    /**
     * construct new template for given class.
     *
     * @param clazz type of supproted template element.
     */
    public SimpleTemplateElement(final Class<T> clazz)
    {
        super(clazz, obj -> {
            throw new UnsupportedOperationException("Can't convert object to " + clazz.getSimpleName() + ": " + obj);
        }, c -> false);
    }

    @Override
    protected void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object object, final int level, final ElementPlace elementPlace) throws IOException
    {
        writer.append(object.toString());
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("function", this.function).toString();
    }
}
