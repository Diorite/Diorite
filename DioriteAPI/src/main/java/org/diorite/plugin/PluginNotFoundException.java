package org.diorite.plugin;

public class PluginNotFoundException extends PluginException
{
    private static final long serialVersionUID = 0;

    public PluginNotFoundException()
    {
        super();
    }

    public PluginNotFoundException(final String message)
    {
        super(message);
    }

    public PluginNotFoundException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    public PluginNotFoundException(final Throwable cause)
    {
        super(cause);
    }
}
