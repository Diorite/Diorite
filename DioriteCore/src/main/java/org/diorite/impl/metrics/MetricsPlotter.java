package org.diorite.impl.metrics;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Interface used to collect custom data for a plugin
 */
abstract class MetricsPlotter
{

    /**
     * The plot's name
     */
    private final String name;

    /**
     * Construct a plotter with the default plot name
     */
    MetricsPlotter()
    {
        this("Default");
    }

    /**
     * Construct a plotter with a specific plot name
     *
     * @param name the name of the plotter to use, which will show up on the website
     */
    MetricsPlotter(final String name)
    {
        this.name = name;
    }

    /**
     * Get the current value for the plotted point. Since this function defers to an external function it may or may
     * not return immediately thus cannot be guaranteed to be thread friendly or safe. This function can be called
     * from any thread so care should be taken when accessing resources that need to be synchronized.
     *
     * @return the current value for the point to be plotted.
     */
    public abstract int getValue();

    /**
     * Get the column name for the plotted point
     *
     * @return the plotted point's column name
     */
    public String getColumnName()
    {
        return this.name;
    }

    /**
     * Called after the website graphs have been updated
     */
    public void reset()
    {
    }

    @Override
    public int hashCode()
    {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(final Object object)
    {
        if (! (object instanceof MetricsPlotter))
        {
            return false;
        }

        final MetricsPlotter plotter = (MetricsPlotter) object;
        return plotter.name.equals(this.name) && (plotter.getValue() == this.getValue());
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).toString();
    }
}
