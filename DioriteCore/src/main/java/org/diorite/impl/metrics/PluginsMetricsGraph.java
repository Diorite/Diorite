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
            return this.core.getPluginManager().getPlugins().stream().map(p -> new SimpleMetricsPlotter(p.getName() + Metrics.GRAPH_SEPARATOR + p.getVersion())).collect(Collectors.toSet());
        }
        return this.core.getPluginManager().getPlugins().stream().map(p -> new SimpleMetricsPlotter(p.getName())).collect(Collectors.toSet());
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("withVersions", this.withVersions).toString();
    }
}
