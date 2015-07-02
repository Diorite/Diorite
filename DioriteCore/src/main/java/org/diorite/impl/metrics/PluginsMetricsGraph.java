package org.diorite.impl.metrics;

import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.impl.ServerImpl;

class PluginsMetricsGraph extends DynamicMetricsGraph
{

    private final ServerImpl srv;
    private final boolean    withVersions;

    PluginsMetricsGraph(final String name, final ServerImpl srv, final boolean withVersions)
    {
        super(name);
        this.srv = srv;
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
            return this.srv.getPluginManager().getPlugins().stream().map(p -> new SimpleMetricsPlotter(p.getName() + Metrics.GRAPH_SEPARATOR + p.getVersion())).collect(Collectors.toSet());
        }
        return this.srv.getPluginManager().getPlugins().stream().map(p -> new SimpleMetricsPlotter(p.getName())).collect(Collectors.toSet());
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("withVersions", this.withVersions).toString();
    }
}
