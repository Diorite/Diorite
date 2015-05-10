package org.diorite.cfg;

public class InvalidConfigurationException extends RuntimeException
{

    public InvalidConfigurationException()
    {
    }

    public InvalidConfigurationException(final String msg)
    {
        super(msg);
    }

    public InvalidConfigurationException(final Throwable cause)
    {
        super(cause);
    }

    public InvalidConfigurationException(final String msg, final Throwable cause)
    {
        super(msg, cause);
    }
}
