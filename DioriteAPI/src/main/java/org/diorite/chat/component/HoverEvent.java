package org.diorite.chat.component;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represent hover event in Chat component API.
 */
public class HoverEvent extends ReplacableComponent
{
    /**
     * Action on hover.
     */
    protected final Action          action;
    /**
     * Value of action, array of base components.
     */
    protected final BaseComponent[] value;

    /**
     * Construct new hover event with given action.
     *
     * @param action action on click.
     * @param value  value of action.
     */
    public HoverEvent(final Action action, final BaseComponent[] value)
    {
        this.action = action;
        this.value = value;
    }

    @Override
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

    @Override
    protected int replace_(final String text, final String repl, int limit)
    {
        if (this.value != null)
        {
            for (final BaseComponent bs : this.value)
            {
                limit = bs.replace_(text, repl, limit);
                if (limit == 0)
                {
                    return 0;
                }
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
     * Returns value of this event.
     *
     * @return value of this event.
     */
    public BaseComponent[] getValue()
    {
        return this.value;
    }

    @Override
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("action", this.action).append("value", this.value).toString();
    }

    /**
     * Enum of possible actions.
     */
    public enum Action
    {
        /**
         * Show text in special box when hovered.
         */
        SHOW_TEXT,
        /**
         * Show achievment-like box when hovered.
         */
        SHOW_ACHIEVEMENT,
        /**
         * Show special box with item in it when hovered.
         */
        SHOW_ITEM;

        Action()
        {
        }
    }
}
