package org.diorite.command.exceptions;

public class InvalidCommandArgumentException extends CommandException
{
    private static final long serialVersionUID = 6131409074456680451L;

    public InvalidCommandArgumentException()
    {
    }

    public InvalidCommandArgumentException(final String message)
    {
        super(message);
    }

    public InvalidCommandArgumentException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    public InvalidCommandArgumentException(final Throwable cause)
    {
        super(cause);
    }
}
