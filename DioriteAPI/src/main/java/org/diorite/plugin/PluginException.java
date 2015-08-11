package org.diorite.plugin;

public class PluginException extends Exception
{
    private static final long serialVersionUID = 0;

    public PluginException()
    {
        super();
    }

    public PluginException(final String message)
    {
        super(message);
    }

    public PluginException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    public PluginException(final Throwable cause)
    {
        super(cause);
    }
}
