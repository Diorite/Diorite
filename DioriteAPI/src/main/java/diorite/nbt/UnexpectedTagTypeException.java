package diorite.nbt;

public class UnexpectedTagTypeException extends RuntimeException
{
    public UnexpectedTagTypeException()
    {
        super("The tag is not of the expected type");
    }

    public UnexpectedTagTypeException(final String message)
    {
        super(message);
    }

    public UnexpectedTagTypeException(final Throwable cause)
    {
        super(cause);
    }

    public UnexpectedTagTypeException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
