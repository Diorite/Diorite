package com.mojang.authlib.yggdrasil;

public class ProfileNotFoundException extends RuntimeException
{
    public ProfileNotFoundException()
    {
    }

    public ProfileNotFoundException(final String message)
    {
        super(message);
    }

    public ProfileNotFoundException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    public ProfileNotFoundException(final Throwable cause)
    {
        super(cause);
    }
}
