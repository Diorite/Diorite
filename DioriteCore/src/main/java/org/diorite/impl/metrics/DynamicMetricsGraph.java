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
