package org.diorite.impl.metrics;

import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represents a custom graph on the website
 */
abstract class DynamicMetricsGraph
{

    /**
     * The graph's name, alphanumeric and spaces only :) If it does not comply to the above when submitted, it is
     * rejected
     */
    private final String name;

    DynamicMetricsGraph(final String name)
    {
        this.name = name;
    }

    /**
     * Gets the graph's name
     *
     * @return the Graph's name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Gets an <b>unmodifiable</b> set of the plotter objects in the graph
     *
     * @return an unmodifiable {@link Set} of the plotter objects
     */
    public abstract Set<MetricsPlotter> getPlotters();

    public void resetPlotters()
    {
        this.getPlotters().forEach(MetricsPlotter::reset);
    }

    @Override
    public int hashCode()
    {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(final Object object)
    {
        if (! (object instanceof DynamicMetricsGraph))
        {
            return false;
        }

        final DynamicMetricsGraph graph = (DynamicMetricsGraph) object;
        return graph.name.equals(this.name);
    }

    /**
     * Called when the server owner decides to opt-out of BukkitMetrics while the server is running.
     */
    protected void onOptOut()
    {
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("name", this.name).toString();
    }
}
