package org.diorite.impl.auth.exceptions;

public class InvalidCredentialsException extends AuthenticationException
{
    private static final long serialVersionUID = 0;

    public InvalidCredentialsException()
    {
    }

    public InvalidCredentialsException(final String message)
    {
        super(message);
    }

    public InvalidCredentialsException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    public InvalidCredentialsException(final Throwable cause)
    {
        super(cause);
    }
}
