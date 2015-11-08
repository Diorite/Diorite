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
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.DioriteCore;

class PluginsMetricsGraph extends DynamicMetricsGraph
{

    private final DioriteCore core;
    private final boolean     withVersions;

    PluginsMetricsGraph(final String name, final DioriteCore core, final boolean withVersions)
    {
        super(name);
        this.core = core;
        this.withVersions = withVersions;
    }

    @Override
    public void resetPlotters()
    {
        // ignore
    }

    @Override
    public Set<MetricsPlotter> getPlotters()
    {
        if (this.withVersions)
        {
            return this.core.getPluginManager().getPlugins().stream().map(p -> new BooleanMetricsPlotter(p.getName() + Metrics.GRAPH_SEPARATOR + p.getVersion())).collect(Collectors.toSet());
        }
        return this.core.getPluginManager().getPlugins().stream().map(p -> new BooleanMetricsPlotter(p.getName())).collect(Collectors.toSet());
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("withVersions", this.withVersions).toString();
    }
}
