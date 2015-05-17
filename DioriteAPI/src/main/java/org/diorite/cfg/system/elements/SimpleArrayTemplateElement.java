package org.diorite.cfg.system.elements;

import java.io.IOException;
import java.util.function.Function;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.system.CfgEntryData;
import org.diorite.utils.collections.ReflectArrayIterator;

public class SimpleArrayTemplateElement<T> extends TemplateElement<T>
{
    public SimpleArrayTemplateElement(final Class<T> clazz, final Function<Object, T> function)
    {
        super(clazz, function);
    }

    @Override
    protected void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final Object object, final int level, final ElementPlace elementPlace) throws IOException
    {
        IterableTemplateElement.INSTANCE.appendValue(writer, field, source, new ReflectArrayIterator(object), level, elementPlace);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("function", this.function).toString();
    }
}
