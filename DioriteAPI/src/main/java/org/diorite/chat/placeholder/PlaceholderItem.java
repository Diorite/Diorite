package org.diorite.chat.placeholder;

import java.util.function.Function;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.chat.component.BaseComponent;

public class PlaceholderItem<T>
{
    private final PlaceholderType<T>         type;
    private final String                     id;
    private final Function<T, BaseComponent> func;

    public PlaceholderItem(final PlaceholderType<T> type, final String id, final Function<T, BaseComponent> func)
    {
        this.type = type;
        this.id = id;
        this.func = func;
    }

    public PlaceholderType<T> getType()
    {
        return this.type;
    }

    public String getId()
    {
        return this.id;
    }

    public BaseComponent apply(final T obj)
    {
        return this.func.apply(obj);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("type", this.type).append("id", this.id).toString();
    }
}
