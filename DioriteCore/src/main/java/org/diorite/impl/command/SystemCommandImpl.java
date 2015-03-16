package org.diorite.impl.command;

import java.util.Collection;
import java.util.regex.Pattern;

public class SystemCommandImpl extends MainCommandImpl
{
    public SystemCommandImpl(final String name, final Collection<String> aliases, final byte priority)
    {
        super(name, aliases, priority);
    }

    public SystemCommandImpl(final String name, final Pattern pattern, final byte priority)
    {
        super(name, pattern, priority);
    }
}
