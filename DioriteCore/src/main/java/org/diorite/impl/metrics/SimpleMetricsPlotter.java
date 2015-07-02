package org.diorite.impl.metrics;

class SimpleMetricsPlotter extends MetricsPlotter
{
    /**
     * Construct a plotter with a specific plot name
     *
     * @param name the name of the plotter to use, which will show up on the website
     */
    SimpleMetricsPlotter(final String name)
    {
        super(name);
    }

    @Override
    public int getValue()
    {
        return 1;
    }
}
