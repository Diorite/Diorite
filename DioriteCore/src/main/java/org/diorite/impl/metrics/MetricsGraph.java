package org.diorite.impl.metrics;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Represents a custom graph on the website
 */
class MetricsGraph extends DynamicMetricsGraph
{

    /**
     * The set of plotters that are contained within this graph
     */
    private final Set<MetricsPlotter> plotters = new LinkedHashSet<>(5);

    MetricsGraph(final String name)
    {
        super(name);
    }

    /**
     * Add a plotter to the graph, which will be used to plot entries
     *
     * @param plotter the plotter to add to the graph
     */
    public void addPlotter(final MetricsPlotter plotter)
    {
        this.plotters.add(plotter);
    }

    /**
     * Remove a plotter from the graph
     *
     * @param plotter the plotter to remove from the graph
     */
    public void removePlotter(final MetricsPlotter plotter)
    {
        this.plotters.remove(plotter);
    }

    /**
     * Gets an <b>unmodifiable</b> set of the plotter objects in the graph
     *
     * @return an unmodifiable {@link Set} of the plotter objects
     */
    @Override
    public Set<MetricsPlotter> getPlotters()
    {
        return Collections.unmodifiableSet(this.plotters);
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("plotters", this.plotters).toString();
    }
}
