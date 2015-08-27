package org.diorite.chat.component;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ClickEvent
{
    protected final Action action;
    protected       String value;

    public ClickEvent(final Action action, final String value)
    {
        this.action = action;
        this.value = value;
    }

    public void replace(final String text, final String repl, final int limit)
    {
        this.replace_(text, repl, limit);
    }

    protected int replace_(final String text, final String repl, int limit)
    {
        final int startIndex = this.value.indexOf(text);
        if (startIndex != - 1)
        {
            final int endIndex = startIndex + text.length();
            this.value = this.value.substring(0, startIndex) + repl + this.value.substring(endIndex);
            if (-- limit == 0)
            {
                return 0;
            }
        }
        return limit;
    }

    public void replace(final String text, final String repl)
    {
        this.replace(text, repl, - 1);
    }

    public void replaceOnce(final String text, final String repl)
    {
        this.replace(text, repl, 1);
    }

    public void replace(final String text, final BaseComponent component, final int limit)
    {
        this.replace_(text, component, limit);
    }

    protected int replace_(final String text, final BaseComponent component, int limit)
    {
        final int startIndex = this.value.indexOf(text);
        if (startIndex != - 1)
        {
            final int endIndex = startIndex + text.length();
            this.value = this.value.substring(0, startIndex) + component.toLegacyText() + this.value.substring(endIndex);
            if (-- limit == 0)
            {
                return 0;
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

    public String getValue()
    {
        return this.value;
    }

    public ClickEvent duplicate()
    {
        return new ClickEvent(this.action, this.value);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("action", this.action).append("value", this.value).toString();
    }

    public enum Action
    {
        OPEN_URL,
        OPEN_FILE,
        RUN_COMMAND,
        SUGGEST_COMMAND;

        Action()
        {
        }
    }
}
