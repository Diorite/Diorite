package diorite.nbt;

import java.io.IOException;

public class TagNotFoundException extends IOException
{

    public TagNotFoundException()
    {
        super("The tag does not exist");
    }

    public TagNotFoundException(final String message)
    {
        super(message);
    }

    public TagNotFoundException(final Throwable cause)
    {
        super(cause);
    }

    public TagNotFoundException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
