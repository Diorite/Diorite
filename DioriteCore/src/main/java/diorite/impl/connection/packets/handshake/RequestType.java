package diorite.impl.connection.packets.handshake;

public enum RequestType
{
    STATUS(1),
    LOGIN(2);
    private final int value;

    RequestType(final int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return this.value;
    }

    @SuppressWarnings("MagicNumber")
    public static RequestType getByInt(final int i)
    {
        switch (i)
        {
            case 0xdd:
            case 1:
                return STATUS;
            case 0x15d:
            case 2:
                return LOGIN;
            default:
                throw new IllegalArgumentException("No request type for " + i);
        }
    }
}
