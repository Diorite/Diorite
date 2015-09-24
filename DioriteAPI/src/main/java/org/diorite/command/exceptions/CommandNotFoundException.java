package org.diorite.command.exceptions;

public class CommandNotFoundException extends CommandException
{
    private static final long serialVersionUID = 0;

    public CommandNotFoundException()
    {
    }

    public CommandNotFoundException(final String message)
    {
        super(message);
    }

    public CommandNotFoundException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    public CommandNotFoundException(final Throwable cause)
    {
        super(cause);
    }
}
