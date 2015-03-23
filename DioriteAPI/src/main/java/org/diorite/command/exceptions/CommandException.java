package org.diorite.command.exceptions;

public class CommandException extends RuntimeException
{
    public CommandException()
    {
    }

    public CommandException(final String message)
    {
        super(message);
    }

    public CommandException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    public CommandException(final Throwable cause)
    {
        super(cause);
    }
}
