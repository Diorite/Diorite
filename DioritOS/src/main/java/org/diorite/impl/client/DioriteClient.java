package org.diorite.impl.client;

import java.net.Proxy;

import org.diorite.impl.DioriteCore;

import joptsimple.OptionSet;

public class DioriteClient extends DioriteCore
{
    public DioriteClient(final Proxy proxy, final OptionSet options)
    {
        super(proxy, options, true);
    }

    @Override
    protected void start(final OptionSet options)
    {
        super.start(options);
    }
}
