package org.diorite.chat.component;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represent click event in Chat component API.
 */
public class ClickEvent extends ReplacableComponent
{
    /**
     * Action on click.
     */
    protected final Action action;
    /**
     * Value of action, like url.
     */
    protected       String value;

    /**
     * Construct new click event with given action.
     *
     * @param action action on click.
     * @param value  value of action, like url.
     */
    public ClickEvent(final Action action, final String value)
    {
        this.action = action;
        this.value = value;
    }

    @Override
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

    @Override
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

    /**
     * Returns click action of this event.
     *
     * @return click action of this event.
     */
    public Action getAction()
    {
        return this.action;
    }

    /**
     * Returns value of this event, like url.
     *
     * @return value of this event, like url.
     */
    public String getValue()
    {
        return this.value;
    }

    @Override
    public ClickEvent duplicate()
    {
        return new ClickEvent(this.action, this.value);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("action", this.action).append("value", this.value).toString();
    }

    /**
     * Enum of possible actions.
     */
    public enum Action
    {
        /**
         * Try open url in client internet browser.
         */
        OPEN_URL,
        /**
         * Try open file on client side.
         */
        OPEN_FILE,
        /**
         * Run selected command.
         */
        RUN_COMMAND,
        /**
         * Append selected string to player chat input box.
         */
        SUGGEST_COMMAND;

        Action()
        {
        }
    }
}
