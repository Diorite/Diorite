package org.diorite.impl.auth.exceptions;

public class AuthenticationException extends Exception
{
    private static final long serialVersionUID = - 7527046395102474172L;

    public AuthenticationException()
    {
    }

    public AuthenticationException(final String message)
    {
        super(message);
    }

    public AuthenticationException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    public AuthenticationException(final Throwable cause)
    {
        super(cause);
    }
}
