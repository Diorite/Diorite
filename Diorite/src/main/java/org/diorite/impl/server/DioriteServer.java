package org.diorite.impl.server;

import java.net.Proxy;

import org.diorite.impl.DioriteCore;

import joptsimple.OptionSet;

public class DioriteServer extends DioriteCore
{
    public DioriteServer(final Proxy proxy, final OptionSet options)
    {
        super(proxy, options, false);
    }

    @Override
    protected void start(final OptionSet options)
    {
        super.start(options);
    }
}
