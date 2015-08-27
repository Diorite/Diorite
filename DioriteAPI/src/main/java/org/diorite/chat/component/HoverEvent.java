package org.diorite.chat.component;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class HoverEvent
{
    protected final Action          action;
    protected final BaseComponent[] value;

    public HoverEvent(final Action action, final BaseComponent[] value)
    {
        this.action = action;
        this.value = value;
    }

    public void replace(final String text, final BaseComponent component, final int limit)
    {
        this.replace_(text, component, limit);
    }

    protected int replace_(final String text, final BaseComponent component, int limit)
    {
        if (this.value != null)
        {
            for (final BaseComponent bs : this.value)
            {
                limit = bs.replace_(text, component, limit);
                if (limit == 0)
                {
                    return 0;
                }
            }
        }
        return limit;
    }

    public void replace(final String text, final BaseComponent component)
    {
        this.replace(text, component, - 1);
    }

    public void replaceOnce(final String text, final BaseComponent component)
    {
        this.replace(text, component, 1);
    }

    public Action getAction()
    {
        return this.action;
    }

    public BaseComponent[] getValue()
    {
        return this.value;
    }

    public HoverEvent duplicate()
    {
        if (this.value == null)
        {
            return new HoverEvent(this.action, null);
        }
        final BaseComponent[] valueCpy = new BaseComponent[this.value.length];
        for (int i = 0; i < this.value.length; i++)
        {
            valueCpy[i] = this.value[i].duplicate();
        }
        return new HoverEvent(this.action, valueCpy);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("action", this.action).append("value", this.value).toString();
    }

    public enum Action
    {
        SHOW_TEXT,
        SHOW_ACHIEVEMENT,
        SHOW_ITEM;

        Action()
        {
        }
    }
}
