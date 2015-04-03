package org.diorite.impl.auth.exceptions;

public class AuthenticationUnavailableException extends AuthenticationException
{
    public AuthenticationUnavailableException()
    {
    }

    public AuthenticationUnavailableException(final String message)
    {
        super(message);
    }

    public AuthenticationUnavailableException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    public AuthenticationUnavailableException(final Throwable cause)
    {
        super(cause);
    }
}
