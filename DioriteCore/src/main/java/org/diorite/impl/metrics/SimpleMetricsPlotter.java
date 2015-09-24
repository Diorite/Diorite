package org.diorite.impl.metrics;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

class SimpleMetricsPlotter extends MetricsPlotter
{
    private final int value;

    /**
     * Construct a plotter with a specific plot name
     *
     * @param name  the name of the plotter to use, which will show up on the website
     * @param value final value of simple plotter.
     */
    SimpleMetricsPlotter(final String name, final int value)
    {
        super(name);
        this.value = value;
    }

    @Override
    public int getValue()
    {
        return this.value;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("value", this.value).toString();
    }
}
