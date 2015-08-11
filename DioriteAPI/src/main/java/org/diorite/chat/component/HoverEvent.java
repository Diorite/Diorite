package org.diorite.chat.component;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public final class HoverEvent
{
    private final Action          action;
    private final BaseComponent[] value;

    public HoverEvent(final Action action, final BaseComponent[] value)
    {
        this.action = action;
        this.value = value;
    }

    public Action getAction()
    {
        return this.action;
    }

    public BaseComponent[] getValue()
    {
        return this.value;
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
