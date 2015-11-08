/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
