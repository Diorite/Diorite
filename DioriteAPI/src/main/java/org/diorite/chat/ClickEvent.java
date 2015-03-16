package org.diorite.chat;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public final class ClickEvent
{
    private final Action action;
    private final String value;

    public ClickEvent(final Action action, final String value)
    {
        this.action = action;
        this.value = value;
    }

    public Action getAction()
    {
        return this.action;
    }

    public String getValue()
    {
        return this.value;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("action", this.action).append("value", this.value).toString();
    }

    public static enum Action
    {
        OPEN_URL,
        OPEN_FILE,
        RUN_COMMAND,
        SUGGEST_COMMAND;

        private Action()
        {
        }
    }
}
