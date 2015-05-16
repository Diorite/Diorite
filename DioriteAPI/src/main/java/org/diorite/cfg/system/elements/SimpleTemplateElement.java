package org.diorite.cfg.system.elements;

import java.io.IOException;
import java.util.function.Function;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.cfg.system.CfgEntryData;

public class SimpleTemplateElement<T> extends TemplateElement<T>
{
    public SimpleTemplateElement(final Class<T> clazz, final Function<Object, T> function)
    {
        super(clazz, function, c -> true);
    }

    @Override
    protected void appendValue(final Appendable writer, final CfgEntryData field, final Object source, final T object, final int level) throws IOException
    {
        writer.append(object.toString());
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("function", this.function).toString();
    }
}
